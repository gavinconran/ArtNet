# Plotting Code for Square Wave

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')


# Plot square wave
Fs = 150.0;  # sampling rate
Ts = 1.0/Fs; # sampling interval
t = np.arange(0,1,Ts) # time vector

ff = 5;   # frequency of the signal
y_square = signal.square(2*np.pi*ff*t)

plt.figure(1)
plt.title("Square Wave")
plt.ylabel("Magnitude")
plt.xlabel("Time (seconds)")
plt.plot(t, y_square, 'b')
plt.ylim(-2, 2)
plt.show()

# Plot Fourier Transform
# frequency domain: magnitude
n = len(y_square) # length of the signal
k = np.arange(n)
T = n/Fs
frq = k/T # two sides frequency range
frq = frq[range(n/2)] # one side frequency range

Y = np.fft.fft(y_square)/n # fft computing and normalization
Y = Y[range(n/2)]

plt.figure(2)
plt.title("Square Wave"
          "\n"
          "Frequency Domain: Magnitude")
plt.plot(frq,abs(Y),'g') # plotting the spectrum
plt.xlabel('Freq (Hz)')
plt.ylabel('|Y(freq)|')
plt.show()

# frequency domain: phase
plt.figure(3)
plt.title("Square Wave"
          "\n"
          "Frequency Domain: Phase")
plt.plot(frq, np.angle(Y),'g') # plotting the spectrum
plt.xlabel('Freq (Hz)')
plt.ylabel('Phase (rads)')
plt.show()

# fft on the unit circle
def Circle(x,y):
    return (x*x+y*y)

real = np.cos(np.real(Y))
im   = np.sin(np.imag(Y))

xx=np.linspace(-2,2,400)
yy=np.linspace(-2,2,400)
[X,Y]=np.meshgrid(xx,yy)

Z=Circle(X,Y)

plt.figure(4)
plt.contour(X,Y,Z,[1])

plt.title("Square Wave"
          "\n"
          "Fourier Coefficients on Unit Circle")
plt.ylabel("Im")
plt.xlabel("Real")
plt.axhline(y=0, color='k')
plt.axvline(x=0, color='k')
plt.plot(real, im, 'go')
plt.show()





