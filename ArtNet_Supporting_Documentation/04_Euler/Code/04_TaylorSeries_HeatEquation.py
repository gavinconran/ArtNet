# Plotting Code for Solution to Heat Equation using Taylor Series

import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
from scipy.linalg import solve
mpl.style.use('classic')

def generateMatrix(N, sigma):
    """ Computes the matrix for the diffusion equation with Crank-Nicolson
        Dirichlet condition at i=0, Neumann at i=-1
    
    Parameters:
    ----------
    N: int
        Number of discretization points
    sigma: float 
        alpha*dt/dx^2
    
    Returns:
    -------
    A: 2D numpy array of float
        Matrix for diffusion equation
    """
   
    # Setup the diagonal
    d = 2*np.diag(np.ones(N-2)*(1+1./sigma))
    
    # Consider Neumann BC
    d[-1,-1] = 1+2./sigma
    
    # Setup upper diagonal
    ud = np.diag(np.ones(N-3)*-1, 1)
    
    # Setup lower diagonal
    ld = np.diag(np.ones(N-3)*-1, -1)
    
    A = d + ud + ld
    
    return A

def generateRHS(T, sigma):
    """ Computes right-hand side of linear system for diffusion equation
        with backward Euler
    
    Parameters:
    ----------
    T: array of float
        Temperature at current time step
    sigma: float
        alpha*dt/dx^2
    
    Returns:
    -------
    b: array of float
        Right-hand side of diffusion equation with backward Euler
    """
    
    b = T[1:-1]*2*(1./sigma-1) + T[:-2] + T[2:]
    # Consider Dirichlet BC
    b[0] += T[0]
    #b[-1] += T[-1]
    
    return b

def CrankNicolson(T, A, nt, sigma):
    """ Advances diffusion equation in time with Crank-Nicolson
   
    Parameters:
    ----------
    T: array of float
        initial temperature profile
    A: 2D array of float
        Matrix with discretized diffusion equation
    nt: int
        number of time steps
    sigma: float
        alpha*td/dx^2
        
    Returns:
    -------
    T: array of floats
        temperature profile after nt time steps
    """
    
    for t in range(nt):
        Tn = T.copy()
        b = generateRHS(Tn, sigma)
        # Use numpy.linalg.solve
        T_interior = solve(A,b)
        T[1:-1] = T_interior
        # Enforce Neumann BC (Dirichlet is enforced automatically)
        T[-1] = T[-2]
        if (t%(nt/5)==0) and (t>0):
            plt.plot(xx, T)

    return T

## set parameters
alpha = 1.22*10**(-3) # thermal diffusivity
u_max = 100.0 # initial max. temp.
sigma = 3.0 #0.5

# x dimension
L = 1.0 # length of rod
nx = 51
dx = L/(nx-1)

xx= np.arange(0.00, L+dx, dx)   # distance values
#t dimension
T = 200 # duration of simulation

# initial conditions
Ti = np.array([100 * np.sin(np.pi * x / L) for x in xx])
A = generateMatrix(nx, sigma)

## run and plot simulation
plt.figure(1)
plt.title("Taylor Series"
          "\n"
          "Dirichlet & Neumann Boundary Conditions")
plt.ylabel("Temperature, u(x,t)")
plt.xlabel('Length of Rod')
plt.ylim(0, 110)
plt.plot(xx, Ti)
T_CN = CrankNicolson(Ti.copy(), A, T, sigma)
plt.show()




