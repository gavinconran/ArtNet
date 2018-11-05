import matplotlib.pyplot as plt
import numpy as np
from scipy.fftpack import fft, fftshift

## create signal to be analysed

# build a signal in continuous time
Fs1_1 = (2*np.pi/6.0)*10**4; # big omega 1
Fs1_2 = (4*np.pi/6.0)*10**4; # big omega 2

Fs2_1 = (2*np.pi/14.0)*10**4; # big omega 1
Fs2_2 = (4*np.pi/25.0)*10**4; # big omega 2

## IMPORTANT: SAMPLING RATE MUST ALLOW WINDOW TO CAPTURE ALL FREQUENCY CONTENT
SR = 100 # sampling rate  
T = 1.0/SR; # delta t
# time axis
t = np.arange(0, 1, T)
N = len(t);

# convert to discrete time
# convert to omega, radian / sec, 
w1_1 = T*Fs1_1; w1_2 = T*Fs1_2; # small omegas for signal 1
w2_1 = T*Fs2_1; w2_2 = T*Fs2_2; # small omegas for signal 2
# phase 
th1 = th2 = 0.0;
# amplitudes
A1 = 1.0; A2 = 0.75;

# construct time-domain signal
# large frequency difference
x1_1 = A1*np.cos(w1_1*t + th1); x1_2 = A2*np.cos(w1_2*t + th2); 
x2_1 = A1*np.cos(w2_1*t + th1); x2_2 = A2*np.cos(w2_2*t + th2); 
# superposition of time-domain signals
# small frequency difference
signal1 = x1_1 + x1_2
signal2 = x2_1 + x2_2

## Fourier Transform corresponding to above windows
def getFFT(window, size=2048):
    '''
    window is any 1D data
    returns the fft of the data
    '''
    A = fft(window, size) / (len(window)/2.0)
    return (np.abs(fftshift(A / abs(A).max())))


## fft of input signal
#DFT size of 2048
# SIZE OF DFT IS IMPORTANT
signal1_Response_2048 = getFFT(signal1)
signal2_Response_2048 = getFFT(signal2)
freq_signal = np.linspace(-np.pi, np.pi, len(signal1_Response_2048))
#DFT size of 4096
signal1_Response_4096 = getFFT(signal1, 4096)
signal2_Response_4096 = getFFT(signal2, 4096)
freq_signal_4096 = np.linspace(-np.pi, np.pi, len(signal1_Response_4096))


from scipy import signal
from scipy.signal import convolve as sig_convolve

## create window
# SIZE OF WINDOW IS IMPORTANT
window_Beta0_L64 = signal.kaiser(64, beta=0) 
 
window_Beta0_L64_Response = getFFT(window_Beta0_L64)  
window_Beta0_L64_Response_4096 = getFFT(window_Beta0_L64)       
freq_win = np.linspace(-np.pi, np.pi, len(window_Beta0_L64_Response))

## convolve in freq domain the fft of the signal and fft of the window
# 2048
conv1_L64 = sig_convolve(signal1_Response_2048, window_Beta0_L64_Response)
conv2_L64 = sig_convolve(signal2_Response_2048, window_Beta0_L64_Response)
freq_conv_L64 = np.linspace(-np.pi, np.pi, len(conv1_L64))

#4096
conv1_L64_4096 = sig_convolve(signal1_Response_4096, window_Beta0_L64_Response_4096)
conv2_L64_4096 = sig_convolve(signal2_Response_4096, window_Beta0_L64_Response_4096)
freq_conv_L64_4096 = np.linspace(-np.pi, np.pi, len(conv1_L64_4096))

## Plots
# FFT of input signal
plt.figure(1)
plt.title('Input Signal: FFT')
plt.xlabel('omega');
plt.ylabel('Amplitude')
plt.plot(freq_signal, signal1_Response_2048, 'g')
plt.show()

plt.figure(2)
plt.title('Input Signal: FFT')
plt.xlabel('omega');
plt.ylabel('Amplitude')
plt.plot(freq_signal, signal2_Response_2048, 'g')
plt.show()

# FFT of window
plt.figure(3)
plt.title('Window: FFT')
plt.xlabel('omega');
plt.ylabel('Amplitude')
plt.plot(freq_win, window_Beta0_L64_Response, 'g')
plt.show()

## convolve in freq domain the fft of the signal and fft of the window
plt.figure(4)
plt.title('Convolution: Signal and Window')
plt.xlabel('omega');
plt.ylabel('Amplitude')
plt.plot(freq_conv_L64, conv1_L64, 'g')
#plt.plot(freq_conv_L64_4096, conv1_L64_4096, 'b')
plt.show()

## convolve in freq domain the fft of the signal and fft of the window
plt.figure(5)
plt.title('Convolution: Signal and Window')
plt.xlabel('omega');
plt.ylabel('Amplitude')
plt.plot(freq_conv_L64, conv2_L64, 'g')
#plt.plot(freq_conv_L64_4096, conv2_L64_4096, 'b')
plt.show()








