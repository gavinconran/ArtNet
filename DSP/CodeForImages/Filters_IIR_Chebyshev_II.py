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

# wn is the frequency at which the gain drops to 1/sqrt(2) that of the passband (the "-3dB point")
N_c1, wp1_c1 = scipy.signal.cheb1ord(wp, ws, Rp*-1, Rs*-1)

# Outputs polynomial coefficients, using the bilinear transform automatically
b_c1, a_c1 = scipy.signal.cheby1(N_c1, Rp*-1, wp1_c1, output='ba')

# plot unit circle
def Circle(x,y):
    return (x*x+y*y)

## 2.b) Chebyshev Type II filter design
N_c2, wp1_c2 = scipy.signal.cheb2ord(wp, ws, Rp*-1, Rs*-1)

# Outputs polynomial coefficients, using the bilinear transform automatically
b_c2, a_c2 = scipy.signal.cheby2(N_c2, Rp*-1, wp1_c2, output='ba')

plt.figure(1)
xx=numpy.linspace(-2,2,400)
yy=numpy.linspace(-2,2,400)
[X,Y]=numpy.meshgrid(xx,yy)

Z=Circle(X,Y)
plt.contour(X,Y,Z,[1])

# zero / pole plot
tf_c2 = control.TransferFunction(b_c2, a_c2)
control.pzmap(tf_c2)
plt.show()

# plot frequency response
w, h = scipy.signal.freqz(b_c2, a_c2)
plt.figure(2)
plt.title('Chebyshev Type II Frequency Response')
plt.plot(w, 20 * numpy.log10(abs(h)), 'b')
plt.ylabel('Amplitude [dB]', color='b')
plt.ylim(-30, 10)
plt.xlabel('Frequency [rad/sample]')
plt.show()


