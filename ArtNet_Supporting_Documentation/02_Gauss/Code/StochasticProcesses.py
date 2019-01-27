# Stochastic process
# weiner/brownian motion process

import matplotlib.pyplot as plt
import numpy as np

# constants
T = 2 # total time
mu = 0.1 # mean
sigma = 0.1 # standard deviation
S0 = 1 # initial value
dt = 0.01 # time interval
N = int(round(T/dt)) # number of time steps
t = np.linspace(0, T, N) # time steps
W = np.random.standard_normal(size = N) # array of independent random variables drawn from normal dist.
W = np.cumsum(W)*np.sqrt(dt) ### array of independent standard brownian motion variables###
drift = (mu-0.5*sigma**2)*t # drift
diffusion = sigma*W         # diffusion
ito = drift + diffusion # Ito process
weiner = diffusion     # Weiner / Brownian process
X_ito = S0*np.exp(ito) ### geometric brownian motion with drift - ito process###
X_weiner = S0*np.exp(weiner) ### geometric brownian motion without drift###

plt.figure(1)
plt.title("Stochastic Processes")
plt.ylabel("X")
plt.xlabel("t")
plt.plot(t, X_weiner, 'b-', label = 'Weiner process: diffusion only')
plt.plot(t, X_ito, 'r-', label = 'Ito process: drift and diffusion')
plt.legend()
plt.show()
