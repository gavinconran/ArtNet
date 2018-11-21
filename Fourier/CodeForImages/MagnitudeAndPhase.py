import numpy
import matplotlib.pyplot as plt

# TIME DOMAIN
Fs = 150.0;                                                        # sampling rate
Ts = 1.0/Fs;                                                       # sampling interval
t = numpy.arange(0,1,Ts)                                  # time vector
ff = 5;                                                                # frequency of the signal
x = numpy.sin(2*numpy.pi*ff*t)                      # signal
plt.plot(t, x); plt.show()                                    # plot time against signal

# FREQUENCY DOMAIN
n = len(x)                                                          # length of the signal
k = numpy.arange(n)                                        # frequency vector
T = n/Fs
freq = k/T                                                          # two sides frequency range
freq = freq[range(n/2)]                                      # one side frequency range
time = t[range(n/2)]                                          # one side time range

# FAST FOURIER TRANSFORM
X = numpy.fft.fft(x)/n                                       # fft computing and normalization
X = X[range(n/2)]                                             # one side fft range 

X_abs  = abs(X)                                                # magnitude
plt.plot(freq, X_abs); plt.show()                       # plot Frequency Response: Magnitude
X_angle =  numpy.angle(X)                              # phase
plt.plot(freq, X_angle); plt.show()                    # plot Frequency Response: Phase
