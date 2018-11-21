import numpy as np
def FFT(x):
  x = np.array(x, dtype=float)
  N = x.shape[0]
  if N%2 > 0:
    print("Error")
  elif N <= 32:
    return DFT(x)
  else:
    X_even = FFT(x[::2])
    X_odd  = FFT(x[1::2])
    factor = np.exp(-2j * np.pi * np.arange(N) / N)
    return np.concatenate([X_even + factor[:N/2] * X_odd, \
                           X_even + factor[N/2:] * X_odd])
