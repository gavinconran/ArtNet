from scipy import signal
import matplotlib.pyplot as plt
import numpy as np


## create chirp signal
fs = 10e3
N = 1e5
time = np.arange(N) / float(fs)
chirp_plot = np.cos(2*np.pi*0.25*time**2)
chirp = np.cos(2*np.pi*1e2*time**2)

plt.figure(1)
plt.title('Chirp Signal: Time Domain')
plt.ylabel('Amplitude')
plt.xlabel('Time')
plt.plot(time, chirp_plot)
plt.show()

# spectrogram
plt.figure(2)
f, t, Sxx = signal.spectrogram(chirp, 0.25)
plt.pcolormesh(t, f, Sxx)
plt.title('Chirp Signal: Spectrogram')
plt.ylabel('Frequency')
plt.xlabel('Sample number (n)')
plt.show()

