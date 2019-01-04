# solving time evolution of Schrodinger's equation as an initial value problem
# uses Trotter decomposition to compute density matrix (without actually solving Schroinger equation)
# and simple (delta-t) time-steppting discritisation
# graphic shows Quantum tunnelling effect
# only works in 1-D

import numpy, pylab, os
from mpl_toolkits.mplot3d import Axes3D

# Fourier Transform
def fourier_x_to_p(phi_x, dx):
    phi_p = [(phi_x * numpy.exp(-1j * p * grid_x)).sum() * dx for p in grid_p]
    return numpy.array(phi_p)

# inverse Fourier Transform
def fourier_p_to_x(phi_p, dp):
    phi_x = [(phi_p * numpy.exp(1j * x * grid_p)).sum() for x in grid_x]
    return numpy.array(phi_x) /  (2.0 * numpy.pi)

# single time-step (Trotter formula)
def time_step_evolution(psi0, potential, grid_x, grid_p, dx, dp, delta_t):
    # step 1: multiply by factor 
    psi0 = numpy.exp(-1j * potential * delta_t / 2.0) * psi0
    # step 2: fourier transform to momemntum-space
    psi0 = fourier_x_to_p(psi0, dx)
    #step 3: multiply by une autre factor
    psi0 = numpy.exp(-1j * grid_p ** 2 * delta_t / 2.0) * psi0
    # step 4: return to real space with an inverse fourier transform
    psi0 = fourier_p_to_x(psi0, dp)
    psi0 = numpy.exp(-1j * potential * delta_t / 2.0) * psi0
    psi0 /= (numpy.absolute(psi0 ** 2).sum() * dx)
    return psi0

# helper function that eturns the potential depending on the position, x
def funct_potential(x):
    if x < -8.0:    return (x + 8.0) ** 2
    elif x <= -1.0: return 0.0
    elif x < 1.0:   return numpy.exp(-1.0 / (1.0 - x ** 2)) / numpy.exp(-1.0)
    else:           return 0.0

# create time-stamped "movie" graphics
output_dir = 'snapshots_time_evolution'
if not os.path.exists(output_dir): os.makedirs(output_dir)
def show(x, psi, pot, time, timestep):
    pylab.plot(x, psi, 'g', linewidth = 2.0, label = '$|\psi(x)|^2$')
    pylab.xlim(-10, 15)
    pylab.ylim(-0.1, 1.15)
    pylab.plot(x, pot, 'k', linewidth = 2.0, label = '$V(x)$')
    pylab.xlabel('$x$', fontsize = 20)
    pylab.title('time = %s' % time)
    pylab.legend(loc=1)
    pylab.savefig(output_dir + '/snapshot_%05i.png' % timestep)
    timestep += 1
    pylab.clf()

# Set up the problem to be solved
steps = 800
x_min = -12.0
x_max = 40.0
# create x axis points (real space)
grid_x = numpy.linspace(x_min, x_max, steps)
# crate p axis points (momentum space)
grid_p = numpy.linspace(x_min, x_max, steps)
# delta x
dx  = grid_x[1] - grid_x[0]
# delta p
dp  = grid_p[1] - grid_p[0]
# delta t
delta_t = 0.05
t_max = 16.0

# compute x grid potentials and place in a vector
potential = [funct_potential(x) for x in grid_x]
potential = numpy.array(potential)
# initial state:
x0 = -8.0
sigma = .5
# compute initial values for psi over all x points
psi = numpy.exp(-(grid_x - x0) ** 2 / (2.0 * sigma ** 2) )
psi /= numpy.sqrt( sigma * numpy.sqrt( numpy.pi ) )
# time evolution
time = 0.0
timestep = 0
#data = {}
while time < t_max:
    if timestep % 20 == 0:
        show(grid_x, numpy.absolute(psi) ** 2.0, potential, time, timestep)
    print time
    time += delta_t
    timestep += 1
    # solve for a single time-step by applying Trotter formula
    psi = time_step_evolution(psi, potential, grid_x, grid_p, dx, dp, delta_t)
    #data[time] = psi

"""
x = [t for t in data.keys()]
y = [psi for psi in data.values()]

pylab.plot(y, x)
pylab.show()
"""
