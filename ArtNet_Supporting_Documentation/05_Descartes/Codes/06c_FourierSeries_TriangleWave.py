# Plotting Code for Triangle Wave

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')


def FS_Triangle(k, tt):
    '''
    k, waveNumbers, is a list of integer wave numbers
    tt is a list of time stamps
    returns the aggregated triangle wave
    '''
    Cn = np.pi / 2.0 # constant term
    return Cn + np.sum([(4.0 / np.pi) * (np.cos(k*t) / k**2 ) for t in tt], axis=1) # sum of waves

tt = np.arange(0, 2*np.pi + 0.01, 0.01) # time scale  
tt_fs = np.arange(-np.pi, np.pi + 0.01, 0.01) # time scale 

# Plot saw triangle wave
k_max = 10001    # max wave number
k_odd = np.arange(1.0,k_max,2) # list of odd wave numbers

y_tri10001 = FS_Triangle(k_odd, tt_fs)
plt.figure(1)
plt.title("Triangle Wave")
plt.ylabel("Magnitude")
plt.xlabel("Time (seconds)")
plt.plot(tt_fs, y_tri10001, 'black')
plt.xlim(-4, 4)
plt.show()

# Fourier Series
k_max3 = 3
k_odd3 = np.arange(1.0,k_max3,2) # list of odd wave numbers
y_tri3 = FS_Triangle(k_odd3, tt_fs)

k_max5 = 5
k_odd5 = np.arange(1.0,k_max5,2) # list of odd wave numbers
y_tri5 = FS_Triangle(k_odd5, tt_fs)

k_max7 = 7
k_odd7 = np.arange(1.0,k_max7,2) # list of odd wave numbers
y_tri7 = FS_Triangle(k_odd7, tt_fs)

k_max9 = 9
k_odd9 = np.arange(1.0,k_max9,2) # list of odd wave numbers
y_tri9 = FS_Triangle(k_odd9, tt_fs)


plt.figure(2)
plt.title("Triangle Wave"
          "\n"
          "Fourier Series")
plt.ylabel("Magnitude")
plt.xlabel("Time")
plt.plot(tt_fs, y_tri3)
plt.plot(tt_fs, y_tri5)
plt.plot(tt_fs, y_tri7)
plt.plot(tt_fs, y_tri9)
plt.show()

# Plot Fourier Transform
# Number of sample points
N = 600
# sample spacing
T = 1.0 / 800.0
x = np.linspace(0.0, N*T, N)

y = signal.sawtooth(2 * np.pi * 1.33 * x, width=.5)
yf = scipy.fftpack.fft(y)
xf = np.linspace(-1.0 / (T), 1.0 / (T), N//2)

plt.figure(6)
plt.title("Triangle Wave"
          "\n"
          "Fourier Transform")
plt.ylabel("Magnitude")
plt.xlabel("Frequency (Hz)")
plt.plot(xf, 2.0/N * np.abs(yf[0:N//2]))
plt.ylim(0., 0.6)
plt.show() 

