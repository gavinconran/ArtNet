import scipy.signal
import numpy
import dtsp
import control
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

## The interpolation system upsamples the input signal by a factor of L = 128.
L = 128
## Passband Ripple
# Frequency band <0.45fs
# Value = 0.1 dB

## Stopband Attenuation
# Frequency band > 0.55fs
# Value = 50 dB

# where fs is the input sampling frequency

# edge frequency (normalised from 0 to 1)
wp = 0.45
# Stopband edge frequency (normalised from 0 to 1)
ws = 0.55
# passband ripple (dB) i.e the max loss in the passband (dB)
Rp = -0.1
# Minimum stopband attenuation (dB)
Rs = -50

Rp_IIR = 10**(-1*Rp/20.0) -1
Rs_IIR = 10**(Rs/20.0)
print("Rp_IIR: ", Rp_IIR)
print("Rs_IIR: ", Rs_IIR)

ripples_pm = [Rp_IIR, Rs_IIR] #[0.01,0.01]
delta_ka = min(Rp_IIR, Rs_IIR)
print("delta_ka: ", delta_ka)


dev_Rp = numpy.exp(Rp/20.0)
dev_Rs = numpy.exp(Rs/20.0)

delta = min(dev_Rp, dev_Rs)

# ripple: Upper bound for the deviation (in dB) of the magnitude of the filter's frequency response
# from that of the desired filter (not including frequencies in any transition intervals)
ripple_ka = -20.0*numpy.log10(delta)
N_ka, beta_ka = scipy.signal.kaiserord(ripple_ka, ws-wp)

#  use this tool to design a Kaiser lowpass filter using pre-specified design parameters
wc = wp
b_ka = scipy.signal.firwin(N_ka, wc, window=('kaiser', beta_ka))
a_ka = numpy.zeros(len(b_ka))

N_pm, bands_pm, amps_pm, weights_pm = dtsp.remezord([wp/2.0, ws/2.0], [1, 0], ripples_pm, Hz=1.0)
bands_pm *= 2.0    # above function outputs frequencies normalized from 0.0 to 0.5


#  use this tool to design a Parks-McClellan lowpass filter using pre-specified design parameters
b_nm = scipy.signal.remez(N_pm, bands_pm, amps_pm, weights_pm, Hz=2.0)
a_nm = numpy.zeros(len(b_ka))

plt.figure(1)
from plot_zplane import zplane
zplane(b_nm,a_nm)

# plot frequency response
w, h = scipy.signal.freqz(b_nm)
plt.figure(2)
plt.title('Parks-McClellan Frequency Response')
plt.plot(w, 20 * numpy.log10(abs(h)), 'b')
plt.ylabel('Amplitude [dB]', color='b')
plt.xlabel('Frequency [rad/sample]')
plt.show()

