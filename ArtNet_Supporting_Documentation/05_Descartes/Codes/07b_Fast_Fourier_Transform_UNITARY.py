import numpy as np
import scipy as sc
import random
import time

# Discrete Fourier Transform
def DFT(x):
  # vectorise x
  x = np.array(x, dtype=float)
  # we are working with the Nth Complex Roots of Unity
  N = x.shape[0]
  # matrix column indices from 0 to N-1
  n = np.arange(N)
  # k is transpose of n
  k = n.reshape((N, 1))
  # Create Fourier Matrix where W_kn = exp(-2j*pi*k*n/N)
  M = np.exp(-2j * np.pi * k * n/N)
  # return M * x = y
  return np.dot(M,x)

# Fast Fourier Transform
def FFT(x):
  # vectorise x
  x = np.array(x, dtype=float)
  # we are working with the Nth Complex Roots of Unity
  N = x.shape[0]
  # make sure x is of a suitable size
  if N%2 > 0:
    print("Error")
  # if x is small just use the DFT
  elif N <= 32:
    return DFT(x)
  else:  # run FFT
    # step 1: recursively run FFT on even and odd vectors of x
    X_even = FFT(x[::2])
    X_odd  = FFT(x[1::2])
    # compute W_kn for a single row k=1
    factor = np.exp(-2j * np.pi * np.arange(N) / N)
    # Reconstruct y from:
    # y_j =   y_j' + w_n**j* y_j''
    # y_j+m = y_j' - w_n**j* y_j''
    # This constructs the famous butterfly diagram
    return np.concatenate([X_even + factor[:N/2] * X_odd, \
                           X_even + factor[N/2:] * X_odd])

###### Tests ######

# create x
x = [10 * random.random() for _ in xrange(1024*10)]

# DFT test
print('##### Timing Tests Beginning #####')
print('##### DFT Test')
start1 = time.time()
y_DFT = DFT(x)
end1 = time.time()

print('##### FFT Test')
start2 = time.time()
y_FFT = FFT(x)
end2 = time.time()

print('##### fft Test')
start3 = time.time()
y_FFT = np.fft.fft(x)
end3 = time.time()

print('##### Times to transform an array with len(x) = '+ str(1024*10) + ' #####')
print('DFT: ' + str(end1 - start1) + ' seconds')
print('FFT: ' + str(end2 - start2) + ' seconds')
print('fft: ' + str(end3 - start3) + ' seconds')
print('##### Timing Tests End #####')
print('')

# Test FFT matrix factorisation
# example taken fron Strang's Linear Algebra, p. 195 (4th edition)
print('##### FFT Matrix Factorisation Test #####')
# input vector
x = sc.matrix('1;2;3;4')
# W consists of W_n values
W = sc.matrix('1 0 1 0; 0 1 0 1j; 1 0 -1 0; 0 1 0 -1j') 
# F consists of two copies of F_2
F = sc.matrix('1 1 0 0; 1 -1 0 0; 0 0 1 1; 0 0 1 -1') 
# P is Permutation matrix that divides x into even and odd vectors
P = sc.matrix('1 0 0 0; 0 0 1 0; 0 1 0 0; 0 0 0 1') 
# Factorised Fourier Matrix 
y = W*F*P*x
print('FFT Matrix Factorisation: ')
print(y)
print('fft: ')
print(np.fft.fft([1,2,3,4]))
### do not completely match!!! ###



