# B2.py
# uses Path Integral Monte Carlo to compute Density Matrix
# Also plots Density Matrix computed using Matrix Multiplication from B1.py


import math, random, pylab

def rho_free(x, y, beta):    # free off-diagonal density matrix
    return math.exp(-(x - y) ** 2 / (2.0 * beta)) 

def read_file(filename):
    list_x = []
    list_y = []
    with open(filename) as f:
        for line in f:
            x, y = line.split()
            list_x.append(float(x))
            list_y.append(float(y))
    f.close()
    return list_x, list_y

#Metroplois-Hastings Markov Chain Monte Carlo Algorithm
beta = 4.0
N = 10                                             # number of slices
dtau = beta / N
delta = 1.0                                       # maximum displacement on one slice
n_steps = 10000000                                 # number of Monte Carlo steps
x = [0.0] * N                                     # initial path
data = []
for step in range(n_steps):
    k = random.randint(0, N - 1)                  # random slice
    knext, kprev = (k + 1) % N, (k - 1) % N       # next/previous slices
    x_new = x[k] + random.uniform(-delta, delta)  # new position at slice k
    old_weight  = (rho_free(x[knext], x[k], dtau) *
                   rho_free(x[k], x[kprev], dtau) *
                   math.exp(-0.5 * dtau * x[k] ** 2))
    new_weight  = (rho_free(x[knext], x_new, dtau) *
                   rho_free(x_new, x[kprev], dtau) *
                   math.exp(-0.5 * dtau * x_new ** 2))
    if random.uniform(0.0, 1.0) < new_weight / old_weight:
        x[k] = x_new

    if step % 10 == 0:
        data.append(x[0])
        #for i in range(N):
        #    data.append(x[i])
    
## Create Graphic
pylab.figure()
# Histogram of formed from path-Integral Monte Carlo
pylab.hist(data, 100, normed = 'True', label = "Path Integral MC")

# PDF of formed from matrix-multiplication (convolution)
filename="data_harm_matrixsquaring_beta4.0.dat"
list_x, list_y = read_file(filename)
pylab.plot(list_x, list_y, c='red', linewidth=2.0, label='Matrix Mult')
pylab.title('Path Integral normalized histogram \
        \nfor '+str(len(data)), fontsize = 18)

pylab.xlabel('$x$', fontsize = 20)
pylab.ylabel('$\pi(x)$', fontsize = 20)
pylab.xlim(-2.0, 2.0)
pylab.legend(loc='best')
pylab.savefig('x0_%d_plot_Path_integral_MC_B=%d_N=%d.png' % (n_steps, beta, N))
pylab.show()
