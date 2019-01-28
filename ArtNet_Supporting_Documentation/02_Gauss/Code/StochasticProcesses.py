# Stochastic process
# weiner/brownian motion process

import matplotlib.pyplot as plt
import numpy as np

# constants
T = 2 # total time
mu = 2.0 #1.0 #0.1 # mean
sigma = 100 #0.1 # standard deviation
dt = 0.001 # time interval
N = int(round(T/dt)) # number of time steps
time = np.linspace(0, T, N) # time steps array

# Plot Graph
plt.figure(1)
plt.title("Stochastic Processes \n dX = drift*dt + diffusion*dW(t)")
plt.ylabel("X")
plt.xlabel("t")

# Process 1:Ito process (drift and diffusion)
np.random.seed(2)
x=0 # initial value, x0
# dummy plot for label
plt.plot([x, x], [time[0], time[0]], 'r-', linewidth=0.5, label = 'Ito process: drift and diffusion' )
for t in time:
   x_new = x + mu*dt + sigma*np.random.normal(0, dt) 
   plt.plot([t, t+dt], [x, x_new], 'r-', linewidth=0.5)
   x = x_new

# Process2 :Weiner process (diffusion only)
np.random.seed(2)
x=0 # initial value x0
# dummy plot for label
plt.plot([x, x], [time[0], time[0]], 'b-', linewidth=0.5, label = 'Weiner process: diffusion only')
for t in time:
   x_new = x + sigma*np.random.normal(0, dt) 
   plt.plot([t, t+dt], [x, x_new], 'b-', linewidth=0.5)
   x = x_new

plt.xlim(0, T)
plt.legend()
plt.show()
