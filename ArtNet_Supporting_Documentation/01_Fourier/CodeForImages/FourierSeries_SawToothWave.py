# Plotting Code for Saw Tooth Wave

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')



def FS_SawTooth(k, tt):
    '''
    k, waveNumbers, is a list of integer wave numbers
    tt is a list of time stamps
    returns the aggregated saw tooth wave
    '''
    Cn = 0 # constant term
    return Cn + np.sum([((2.0 * (-1)**(k+1)) / k) * np.sin(k*t) for t in tt], axis=1) # sum of waves

tt = np.arange(0, 2*np.pi + 0.01, 0.01) # time scale  
tt_fs = np.arange(-np.pi, np.pi + 0.01, 0.01) # time scale  

# Plot saw tooth wave
y_saw = signal.sawtooth(tt)    #(tt)
y_square = signal.square(tt)
plt.figure(1)
plt.title("Saw Tooth Wave")
plt.ylabel("Magnitude")
plt.xlabel("Time (seconds)")
plt.plot(tt, y_saw, 'black')
plt.ylim(-2, 2)
plt.xlim(-1, 7)
plt.show()

# Fourier Series
k_max3 = 3
k_all3 = np.arange(1,k_max3+1) # list of consecutive wave numbers
y_saw3 = FS_SawTooth(k_all3, tt_fs)

k_max5 = 5
k_all5 = np.arange(1,k_max5+1) # list of consecutive wave numbers
y_saw5 = FS_SawTooth(k_all5, tt_fs)

k_max7 = 7
k_all7 = np.arange(1,k_max7+1) # list of consecutive wave numbers
y_saw7 = FS_SawTooth(k_all7, tt_fs)

k_max9 = 9
k_all9 = np.arange(1,k_max9+1) # list of consecutive wave numbers
y_saw9 = FS_SawTooth(k_all9, tt_fs)



plt.figure(2)
plt.title("Saw Tooth Wave"
          "\n"
          "Fourier Series")
plt.ylabel("Magnitude")
plt.xlabel("Time")
plt.plot(tt, [y/np.pi for y in y_saw3])
plt.plot(tt, [y/np.pi for y in y_saw7])
plt.plot(tt, [y/np.pi for y in y_saw9])
plt.plot(tt, y_saw, 'black')
plt.ylim(-2, 2)
plt.xlim(-1, 7)
plt.show()

# Plot Fourier Transform
# Number of samplepoints
N = 600
# sample spacing
T = 1.0 / 800.0
x = np.linspace(0.0, N*T, N)
yf = scipy.fftpack.fft(y_saw)
xf = np.linspace(0.0, 1.0/(2.0*T), N/2)

plt.figure(3)
plt.title("Saw Tooth Wave"
          "\n"
          "Fourier Transform")
plt.ylabel("Magnitude")
plt.xlabel("Frequency (Hz)")
plt.plot(xf, 2.0/N * np.abs(yf[:N//2]))
plt.ylim(0, 0.6)
plt.show()


