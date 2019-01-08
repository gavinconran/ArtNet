# b1.py
# computes density matrix
# Plots chart with approximate and exact probability distribution function (PDF)

import math, numpy, pylab

# Free off-diagonal density matrix
def rho_free(x, xp, beta):
    return (math.exp(-(x - xp) ** 2 / (2.0 * beta)) /
            math.sqrt(2.0 * math.pi * beta))

# Harmonic density matrix in the Trotter approximation (returns the full matrix)
def rho_harmonic_trotter(grid, beta):
    return numpy.array([[rho_free(x, xp, beta) * \
                         numpy.exp(-0.5 * beta * 0.5 * (x ** 2 + xp ** 2)) \
                         for x in grid] for xp in grid])

# Compute Exact Quantum Probability Distribution
def prob_quant(x, Beta):
    return math.sqrt(math.tanh(Beta/float(2)) / math.pi) * math.exp(- x**2 * math.tanh(Beta/float(2))) 

x_max = 5 # 5 #50.0
nx = 100
dx = 2.0 * x_max / (nx - 1)
x = [i * dx for i in range(-(nx - 1) / 2, nx / 2 + 1)] 
beta_tmp = 2.0 ** (-8)                   # initial value of beta (power of 2)
beta     = 2.0 ** 2                           # actual value of beta (power of 2)
rho = rho_harmonic_trotter(x, beta_tmp)  # density matrix at initial beta
while beta_tmp < beta:
    rho = numpy.dot(rho, rho)
    rho *= dx
    beta_tmp *= 2.0

# write density matrix to file
Z = sum(rho[j, j] for j in range(nx + 1)) * dx
pi_of_x = [rho[j, j] / Z for j in range(nx + 1)]
f = open('data_harm_matrixsquaring_beta' + str(beta) + '.dat', 'w')
for j in range(nx + 1):
    f.write(str(x[j]) + ' ' + str(rho[j, j] / Z) + '\n')
f.close()

# plot probability distribution (approx and exact)
pylab.plot(x, pi_of_x, c='blue', linewidth=2.0, label='Estimate')

# Plot exact Quantum Probability Distribution
y_quant = [(prob_quant(a, beta)) for a in x]  
pylab.plot(x, y_quant, 'ro', c='red', linewidth=2.0, label='Analytic')

pylab.title('Matrix Multiplication normalized histogram \
        \nfor Beta = ' +str(beta), fontsize = 18)


pylab.title('Matrix Multiplication Normalized histogram\
            \nfor Beta = ' +str(beta), fontsize = 18)
pylab.xlabel('$x$', fontsize = 20)
pylab.ylabel('$\pi(x)$', fontsize = 20)
pylab.xlim(-2.0, 2.0)
pylab.legend(loc='best')
pylab.savefig('plot_finite_Temp_Beta=%d.png' % beta) # % beta)
pylab.show()

"""
# graphics output
pylab.imshow(rho, extent=[-x_max, x_max, -x_max, x_max], origin='lower')
pylab.colorbar()
pylab.title('$\\beta = 2^{%i}$' % math.log(beta, 2))
pylab.xlabel('$x$', fontsize=18)
pylab.ylabel('$x\'$', fontsize=18)
pylab.savefig('plot-harmonic-rho.png')
"""
