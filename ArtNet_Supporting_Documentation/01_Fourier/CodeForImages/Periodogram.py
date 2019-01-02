from scipy import signal
import matplotlib.pyplot as plt
import numpy as np
np.random.seed(1234)

# time domain
fs = 10e3
N = 1e5
amp = 2*np.sqrt(2)
freq = 1234.0
noise_power = 0.001 * fs / 2
time = np.arange(N) / fs
x = amp*np.sin(2*np.pi*freq*time)
x += np.random.normal(scale=np.sqrt(noise_power), size=time.shape)

## freq domain / 
#Periodogram
f, Pxx_den = signal.periodogram(x, fs)

fw, Pxx_den_w = signal.welch(x, fs)


# Plots

plt.figure(1)
plt.plot(time, x)
plt.title('Stochastic Signal: Time Domain')
plt.xlabel('Time')
plt.ylabel('Amplitude')
plt.show()

plt.figure(2)
plt.semilogy(f, Pxx_den)
plt.title('Periodogram')
plt.ylim([1e-7, 1e2])
plt.xlabel('frequency [Hz]')
plt.ylabel('PSD [V**2/Hz]')
plt.show()

plt.figure(3)
plt.semilogy(fw, Pxx_den_w)
plt.title('Periodogram: Welch Method')
plt.ylim([1e-7, 1e2])
plt.xlabel('frequency [Hz]')
plt.ylabel('PSD [V**2/Hz]')
plt.show()

