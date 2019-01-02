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

N_el, wp1_el = scipy.signal.ellipord(wp, ws, Rp, Rs)
#ord, wp = scipy.signal.ellipord(wp, ws, gpass, gstop)

b_el, a_el = scipy.signal.ellip(N_el, Rp*-1, Rs*-1, wp1_el, output='ba')

# plot unit circle
def Circle(x,y):
    return (x*x+y*y)

plt.figure(1)
xx=numpy.linspace(-2,2,400)
yy=numpy.linspace(-2,2,400)
[X,Y]=numpy.meshgrid(xx,yy)

Z=Circle(X,Y)
plt.contour(X,Y,Z,[1])

# zero / pole plot
tf_el = control.TransferFunction(b_el, a_el)
control.pzmap(tf_el)
plt.show()

# plot frequency response
w, h = scipy.signal.freqz(b_el, a_el)
plt.figure(2)
plt.title('Chebyshev Type II Frequency Response')
plt.plot(w, 20 * numpy.log10(abs(h)), 'b')
plt.ylabel('Amplitude [dB]', color='b')
plt.ylim(-120, 10)
plt.xlabel('Frequency [rad/sample]')
plt.show()
