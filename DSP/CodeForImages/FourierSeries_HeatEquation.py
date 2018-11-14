# Plotting Code for Solution to Heat Equation
# Note: having trouble with Fourier coefficients

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')

def decay(n, x, alpha, t, L):
    '''
    return exponential decay factor
    '''
    return np.exp(-n**2 * x**2 * alpha * t / L**2)

def Dn_coeff(L, n, x):
    '''
    return Dn (Fourier Coefficient) 
    which is equal to 1 for n==1 and 0 otherwise due to how the integral plays out
    IC: f(x) = 100*sin(pi* x /L)
    '''
    if (n==1):
        return 1.0
    else:
        return 0.0

def heat(n, x, alpha, t, L):
    '''
    n is max size of n in summation
    x is the distance along rod from 0
    alpha is the thermal diffusivity
    t is time t
    L is the length of the rod
    return u(x, t)
    '''
    nn = np.arange(1, n, 1) # used for summation from n-1 to n = infinity
    return np.sum([Dn_coeff(L, n, x) * np.sin(n*np.pi*x/L) * decay(n, x, alpha, t, L) for n in nn])

# set parameters
alpha = 1.22*10**(-3) # thermal diffusivity
L = 1.0 # length of rod
u0 = 100.0 # initial max. temp.

dx = 0.01
xx= np.arange(0.00, L+dx, dx)   # distance values
tt = np.arange(0, 10001, 2000)  # time values

# plot initial conditions
ic = u0*np.sin(xx *np.pi/L)
plt.figure(1)
plt.plot(xx, ic, 'b')
plt.title("Initial Conditions: u(x,0) = 100*sin(pi*x/L)")
plt.ylabel("u(x,0)")
plt.xlabel("Distance, x")
plt.ylim(0, 110)
plt.show()

# run simulation
n = 11 # going towards infinity
simulation11 = [[u0 * heat(n, x, alpha, t, L) for x in xx] for t in tt]

# plot results
plt.figure(2)
plt.ylim(0, 110)
plt.plot(xx, simulation11[0], 'b', label='t = 1')
plt.plot(xx, simulation11[1], 'r', label='t = 2')
plt.plot(xx, simulation11[2], 'g', label='t = 3')
plt.plot(xx, simulation11[3], 'm', label='t = 4')
plt.plot(xx, simulation11[4], 'c', label='t = 5')
plt.legend()
plt.title("Solution to Heat Equation over Time")
plt.ylabel("u(x,t)")
plt.xlabel("Distance, x")
plt.show()








 
