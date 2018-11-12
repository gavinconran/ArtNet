import numpy

nx = 21
n = 5

num_blocks = 30
randx = numpy.random.randint(1, nx-1, num_blocks)
randy = numpy.random.randint(1, nx-1, num_blocks)
U = numpy.ones((n,n))
V = numpy.zeros((n,n))

r = 10
U[:,:] = 1.0

# set initial conditions
for i, j in zip(randx, randy):
    U[i-r:i+r,j-r:j+r] = 0.50
    V[i-r:i+r,j-r:j+r] = 0.25

# mix it up a bit
U += 0.05*numpy.random.random((n,n))
V += 0.05*numpy.random.random((n,n))
