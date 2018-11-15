# Plotting Code for Solution to Heat Equation

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
import scipy.fftpack
mpl.style.use('classic')

def decay(n, alpha, t, L):
    '''
    return exponential decay factor
    '''
    return np.exp(-n**2 * np.pi**2 * alpha * t / L**2)

def Dn_coeff(L, n, x):
    '''
    return Dn (Fourier Coefficient) 
    which is equal to 1 for n==1 and 0 otherwise due to how the integral plays out
    IC: f(x) = 100*sin(pi* x /L)
    The integral from 0 to L of sin(m * pi * x / L) * sin(n pi x /L) = 1 when m==n and 0 when m!=n (orthogonal)
    '''
    if (n==1): # (when m == n)
        return 1.0
    else: # (when m != n)
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
    nn = np.arange(1, n+1)
    return np.sum([Dn_coeff(L, n, x) * np.sin(np.pi*x/L) * decay(n, alpha, t, L) for n in nn]) 

# set parameters
alpha = 1.22*10**(-3) # thermal diffusivity
L = 1.0 # length of rod
u_max = 100.0 # initial max. temp.

dx = 0.01 # distance step
xx= np.arange(0.00, L+dx, dx)   # distance values

T = 200 # duration of simulation
dt = 40 # time step
tt = np.arange(0, T+dt, dt)  # time values

# plot initial conditions
ic = u_max*np.sin(xx *np.pi/L)
plt.figure(1)
plt.plot(xx, ic, 'b', label='f(x) at t = 0')
plt.title("Initial Conditions: u(x,0) = f(x) = 100*sin(pi*x/L)"
          "\n"
          "Boundary Conditions: u(0,t)=u(L,t) = 0")
plt.ylabel("u(x,0)")
plt.xlabel("Distance, x")
plt.ylim(0, 110)
plt.legend()
plt.show()

# run simulation
n = 1 # can go to infinity
simulation = [[u_max * heat(n, x, alpha, t, L) for x in xx] for t in tt]

# plot results
plt.figure(2)
plt.ylim(0, 110)
plt.plot(xx, simulation[0], 'b', label='t = 0')
plt.plot(xx, simulation[1], 'r', label='t = 1')
plt.plot(xx, simulation[2], 'g', label='t = 2')
plt.plot(xx, simulation[3], 'm', label='t = 3')
plt.plot(xx, simulation[4], 'c', label='t = 4')
plt.legend()
plt.title("Solution to Heat Equation over Time"
          "\n"
          "Number of Component Waves, n = 1")
plt.ylabel("u(x,t)")
plt.xlabel("Distance, x")
plt.show()








 
