import numpy as np
def DFT(x):
  x = np.array(x, dtype=float)
  N = x.shape[0]
  n = np.arange(N)
  k = n.reshape((N, 1))
  M = np.exp(-2j * np.pi * k * n/N)
  return np.dot(M,x)
