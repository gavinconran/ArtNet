# Jacobi iterative method to solve Laplace equation

from matplotlib import pyplot as plt
import numpy
import matplotlib as mpl
mpl.style.use('classic')

# For two successive iterations, the relative L2 norm is then calculated as
def L2_error(p, pn):
    return numpy.sqrt(numpy.sum((p - pn)**2)/numpy.sum(pn**2))

# analytic solution to laplace equation
def p_analytical(x, y):
    X, Y = numpy.meshgrid(x,y)
    
    p_an = numpy.sinh(1.5*numpy.pi*Y / x[-1]) /\
    (numpy.sinh(1.5*numpy.pi*y[-1]/x[-1]))*numpy.sin(1.5*numpy.pi*X/x[-1])
    
    return p_an

# Jacobi method for Laplace's equation
def laplace2d_neumann(p, l2_target):
    '''Iteratively solves the Laplace equation using the Jacobi method
    with second-order Neumann boundary conditions
    
    Parameters:
    ----------
    p: 2D array of float
        Initial potential distribution
    l2_target: float
        target for the difference between consecutive solutions
        
    Returns:
    -------
    p: 2D array of float
        Potential distribution after relaxation
    '''
    
    l2norm = 1
    pn = numpy.empty_like(p)
    iterations = 0
    while l2norm > l2_target:
        pn = p.copy()
        p[1:-1,1:-1] = .25 * (pn[1:-1,2:] + pn[1:-1, :-2] \
                              + pn[2:, 1:-1] + pn[:-2, 1:-1])
        
        ##2nd-order Neumann B.C. along x = L
        p[1:-1,-1] = .25 * (2*pn[1:-1,-2] + pn[2:, -1] + pn[:-2, -1])
        
        l2norm = L2_error(p, pn)
     
    return p



# Test Convergence
def laplace_IG(nx):
    '''Generates initial guess for Laplace 2D problem for a 
    given number of grid points (nx) within the domain [0,1]x[0,1]
    
    Parameters:
    ----------
    nx: int
        number of grid points in x (and implicitly y) direction
        
    Returns:
    -------
    p: 2D array of float
        Pressure distribution after relaxation
    x: array of float
        linspace coordinates in x
    y: array of float
        linspace coordinates in y
    '''

    ##initial conditions
    p = numpy.zeros((nx,nx)) ##create a XxY vector of 0's

    ##plotting aids
    x = numpy.linspace(0,1,nx)
    y = x

    ##Dirichlet boundary conditions
    p[:,0] = 0
    p[0,:] = 0
    p[-1,:] = numpy.sin(1.5*numpy.pi*x/x[-1])
    
    return p, x, y

# Run tests with changing grid size
nx_values = [11, 21, 41, 81]
l2_target = 1e-8

error = numpy.empty_like(nx_values, dtype=numpy.float)


for i, nx in enumerate(nx_values):
    p, x, y = laplace_IG(nx)
    
    p = laplace2d_neumann(p.copy(), l2_target)
    
    p_an = p_analytical(x, y)
    
    error[i] = L2_error(p, p_an)


# plot error convergence
plt.figure(1)
plt.grid(True)
plt.xlabel(r'$n_x$', fontsize=18)
plt.ylabel(r'$L_2$-norm of the error', fontsize=18)
plt.loglog(nx_values, error, color='k', ls='--', lw=2, marker='o')
plt.axis('equal');
plt.show()





