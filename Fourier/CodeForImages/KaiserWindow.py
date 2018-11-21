from scipy import signal
import matplotlib.pyplot as plt
import numpy as np
from scipy.fftpack import fft, fftshift

## Kaiser window for beta = 0, 3 and 6 with L = 21
window_Beta0_L21 = signal.kaiser(21, beta=0)   
window_Beta3_L21 = signal.kaiser(21, beta=3)   
window_Beta6_L21 = signal.kaiser(21, beta=6)   

plt.figure(1)
plt.plot(window_Beta0_L21, label="$\\beta$=0")
plt.plot(window_Beta3_L21, label="$\\beta$=3")
plt.plot(window_Beta6_L21, label="$\\beta$=6")
plt.axis([0, 20, 0, 1.2])

plt.title(r"Kaiser Window (L=21)")
plt.ylabel("Amplitude")
plt.xlabel("Sample")
plt.legend(loc=0)
plt.show()

## Fourier Transform corresponding to above windows
def getResponse(window):
    '''
    window is the Kaiser window
    returns the frequency response of the Kaiser window
    '''
    A = fft(window, 2048) / (len(window)/2.0)
    freq = np.linspace(-0.5, 0.5, len(A))
    return 20 * np.log10(np.abs(fftshift(A / abs(A).max())))


window_Beta0_L21_Response = getResponse(window_Beta0_L21)      
window_Beta3_L21_Response = getResponse(window_Beta3_L21)      
window_Beta6_L21_Response = getResponse(window_Beta6_L21)      

freq = np.linspace(-0.5, 0.5, len(window_Beta0_L21_Response))

plt.figure(2)
plt.plot(freq[len(freq)/2:], window_Beta0_L21_Response[len(freq)/2:], label="$\\beta$=0")
plt.plot(freq[len(freq)/2:], window_Beta3_L21_Response[len(freq)/2:], label="$\\beta$=3")
plt.plot(freq[len(freq)/2:], window_Beta6_L21_Response[len(freq)/2:], label="$\\beta$=6")

plt.axis([0, 0.5, -150, 20])
plt.title(r"Frequency response of the Kaiser window (L=21)")
plt.ylabel("Normalized magnitude [dB]")
plt.xlabel("Normalized frequency [cycles per sample]")
plt.legend(loc=1)
plt.show()

## FT of Kaiser windows with beta=6 and L=11, 21 and 41
### vary Length, L with Beta = 6
window_Len11 = signal.kaiser(11, beta=6)      # length = 11
window_Len21 = signal.kaiser(21, beta=6)      # length = 21
window_Len41 = signal.kaiser(41, beta=6)      # length = 41

### Frequency response
window_Len11_Response = getResponse(window_Len11)      # rectangle
window_Len21_Response = getResponse(window_Len21)      # Bartlett
window_Len42_Response = getResponse(window_Len41)      # Hanning

plt.figure(3)
plt.plot(freq[len(freq)/2:], window_Len11_Response[len(freq)/2:], label="L=11")
plt.plot(freq[len(freq)/2:], window_Len21_Response[len(freq)/2:], label="L=21")
plt.plot(freq[len(freq)/2:], window_Len42_Response[len(freq)/2:], label="L=41")

plt.axis([0, 0.5, -150, 20])
plt.title(r"Frequency response of the Kaiser window ($\beta$=6)")
plt.ylabel("Normalized magnitude [dB]")
plt.xlabel("Normalized frequency [cycles per sample]")
plt.legend(loc=1)
plt.show()


