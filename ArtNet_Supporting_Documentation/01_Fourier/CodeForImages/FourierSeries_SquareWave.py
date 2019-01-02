# Plotting Code for Square Wave

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')

# plot aggreagation of waves
def FS_SquareWave(k, tt):
    '''
    k, waveNumbers, is a list of odd wave numbers
    tt is an array of time stamps
    returns the summed Square wave
    '''
    Cn = 0 # constant term
    return [(4.0/np.pi) * np.sum(np.sin(k*t)/k, axis=0) for t in tt] 

tt = np.arange(-np.pi, np.pi + 0.01, 0.01) # time scale

# Plot square wave
y_square = signal.square(tt)
plt.figure(1)
plt.title("Square Wave")
plt.ylabel("Magnitude")
plt.xlabel("Time (seconds)")
plt.plot(tt, y_square, 'black')
plt.ylim(-2, 2)
plt.show()


# Plot Fourier Series

k_max3 = 3
k_odd3 = np.arange(1,k_max3, 2) # list of odd wave numbers
y_max3 = FS_SquareWave(k_odd3, tt)

k_max5 = 5
k_odd5 = np.arange(1,k_max5, 2) # list of odd wave numbers
y_max5 = FS_SquareWave(k_odd5, tt)

k_max7 = 7
k_odd7 = np.arange(1,k_max7, 2) # list of odd wave numbers
y_max7 = FS_SquareWave(k_odd7, tt)

k_max9 = 9
k_odd9 = np.arange(1,k_max9, 2) # list of odd wave numbers
y_max9 = FS_SquareWave(k_odd9, tt)


k_max = 10001
k_odd = np.arange(1,k_max,2) # list of odd wave numbers
y_max = FS_SquareWave(k_odd, tt)

plt.figure(1)
plt.title("Square Wave"
          "\n"
          "Fourier Series")
plt.ylabel("Magnitude")
plt.xlabel("Time")
plt.plot(tt, y_max3)
plt.plot(tt, y_max5)
plt.plot(tt, y_max7)
plt.plot(tt, y_max9)
plt.plot(tt, y_max, 'black')
plt.show()

# Plot Fourier Transform
# Number of samplepoints
N = 600
# sample spacing
T = 1.0 / 800.0
x = np.linspace(0.0, N*T, N)
y = np.sin(50.0 * 2.0*np.pi*x) + 0.5*np.sin(80.0 * 2.0*np.pi*x)
yf = scipy.fftpack.fft(y_square)
xf = np.linspace(0.0, 1.0/(2.0*T), N/2)

plt.figure(3)
plt.title("Square Wave"
          "\n"
          "Fourier Transform")
plt.ylabel("Magnitude")
plt.xlabel("Frequency (Hz)")
plt.plot(xf, 2.0/N * np.abs(yf[:N//2]))
plt.ylim(0, 0.6)
plt.show()



