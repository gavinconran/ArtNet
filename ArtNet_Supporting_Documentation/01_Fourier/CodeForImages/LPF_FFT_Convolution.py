import numpy as np
import scipy.signal
from scipy.signal import fftconvolve, lfilter, firwin
from scipy.signal import convolve as sig_convolve
import dtsp
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

# build a signal
Fs = 1000
t = np.arange(0, 1, 1.0/Fs)
N = len(t);

# 2 Hz converted to radian frequency
f2 = 2 # Hz
w = 2*np.pi*f2; 
# amplitudes
a = 2.0; 

# construct time-domain signal
x1 = a*np.cos(w*t); 
# construct noisy signal
noise = np.random.normal(0,1,1000)

# superposition of time-domain and noisy signal
signal = x1 + noise 

### Parks McClellan Filter

## 1) Parks-McClellan filter order estimation
## estimate the parameters required to design a Parks-McClellan lowpass filter.

# Passband edge frequency (normalised from 0 to 1)
wp = 10.0 / 56.0 # = 3.0 / 56.0 #0.5
# Stopband edge frequency (normalised from 0 to 1)
ws = 14.0 / 56.0 # = 5.0 / 56.0

# passband ripple about unity (linear)
dpass = 0.001
# stopband ripple about zero (linear)
dstop = 0.001
# compute design parameters
numtaps, bands, amps, weights = dtsp.remezord([wp/2.0, ws/2.0], [1, 0], [dpass,dstop], Hz=1.0)
bands *= 2.0    # above function outputs frequencies normalized from 0.0 to 0.5
#  use this tool to design a Parks-McClellan lowpass filter using pre-specified design parameters
b = scipy.signal.remez(numtaps, bands, amps, weights, Hz=2.0)


# FREQUENCY DOMAIN
# Frequency Response of filter
w_axis, h = scipy.signal.freqz(b) 

# compute frequency domain convolutions
fftconv_result = fftconvolve(signal, b, mode='full')

# TIME DOMAIN
# compute time domain convolution
conv_result = sig_convolve(signal, b, mode='full')


### TIME DOMAIN PLOTS
# Plots input signal
plt.figure(1)
#plt.plot(t[:400], signal[:400], 'b');
plt.plot(t, signal, 'b');
plt.title('Input Signal')
plt.xlabel('Time');
plt.ylabel('Amplitude')
plt.ylim(min(signal), max(signal))
plt.show()

## Filter Plots
# plot impulse response of filter
plt.figure(2)
plt.title('Impulse Response'
          "\n"
          "Parks-McClellan FIR LPF")
plt.ylabel('Amplitude')
plt.xlabel('Time')
markerline, stemlines, baseline = plt.stem(b)
plt.setp(baseline, color='b', linewidth=2)
plt.show()

# plot frequency response
plt.figure(3)
plt.title('Frequency Response'
          "\n"
          "Parks-McClellan FIR LPF")
plt.plot(w_axis, 20 * np.log10(abs(h)), 'g')
plt.ylabel('Amplitude [dB]')
plt.xlabel('Frequency')
plt.show()

# output from time domain covolution
plt.figure(4)
x_axis = np.arange(0, len(fftconv_result)) / float(len(signal))
plt.plot(x_axis, conv_result, 'b');
plt.title('Output Signal'
          "\n"
          "Time Domain Convolved")
plt.xlabel('Time');
plt.ylim(min(signal),max(signal))
plt.ylabel('Amplitude')
plt.show()

# output from time domain covolution
plt.figure(5)
x_axis = np.arange(0, len(fftconv_result)) / float(len(signal))
plt.plot(x_axis, fftconv_result, 'b');
plt.title('Output Signal'
          "\n"
          "Frequency Domain Convolved")
plt.xlabel('Time');
plt.ylabel('Amplitude')
plt.ylim(min(signal), max(signal))
plt.show()


