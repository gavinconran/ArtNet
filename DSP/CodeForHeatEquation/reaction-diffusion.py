import numpy
from matplotlib import pyplot
from matplotlib import rcParams, cm
rcParams['font.family'] = 'serif'
rcParams['font.size'] = 16

def ftcs(U, V, Du, Dv, F, k, dt, dh, nt):

    for n in range(nt):
        Un = U.copy();
        Vn = V.copy();  
        U[1:-1,1:-1] = Un[1:-1,1:-1] + Du *\
            (dt/dh**2 * (Un[2:,1:-1] - 2*Un[1:-1,1:-1] + Un[:-2,1:-1]) +\
             dt/dh**2 * (Un[1:-1,2:] - 2*Un[1:-1,1:-1] + Un[1:-1,:-2])) -\
             dt*Un[1:-1,1:-1]*Vn[1:-1,1:-1]**2 +\
             dt*F*(1- Un[1:-1,1:-1])

        # Enforce Neumann BCs
        U[-1,:] = U[-2,:]
        U[0,:] = U[1,:]
        U[:,-1] = U[:,-2]
        U[:,0] = U[:,1]
        
        V[1:-1,1:-1] = Vn[1:-1,1:-1] + Dv *\
            (dt/dh**2 * (Vn[2:,1:-1] - 2*Vn[1:-1,1:-1] + Vn[:-2,1:-1]) +\
             dt/dh**2 * (Vn[1:-1,2:] - 2*Vn[1:-1,1:-1] + Vn[1:-1,:-2])) +\
             dt*Un[1:-1,1:-1]*Vn[1:-1,1:-1]**2 -\
             dt*(F+k)*Vn[1:-1,1:-1]

        # Enforce Neumann BCs
        V[-1,:] = V[-2,:]
        V[0,:] = V[1,:]
        V[:,-1] = V[:,-2]
        V[:,0] = V[:,1]


    return U, V


# set parameters
n = 192
Du, Dv, F, k = 0.00016, 0.00008, 0.035, 0.065 # Bacteria 1 
dh = 5/(n-1)
T = 8000
dt = .9 * dh**2 / (4*max(Du,Dv))
nt = int(T/dt)


# Initial conditions
uvinitial = numpy.load('./data/uvinitial.npz')
Ui = uvinitial['U']
Vi = uvinitial['V']

# Plot initial conditions
fig = pyplot.figure(figsize=(8,5))
pyplot.subplot(121)
pyplot.imshow(Ui, cmap = cm.RdBu)
pyplot.xticks([]), pyplot.yticks([]);
pyplot.subplot(122)
pyplot.imshow(Vi, cmap = cm.RdBu)
pyplot.xticks([]), pyplot.yticks([]);
fig.show()

# run simulation
Uans, Vans = ftcs(Ui.copy(), Vi.copy(), Du, Dv, F, k, dt, dh, nt)

# Plot results
fig = pyplot.figure(figsize=(8,5))
pyplot.subplot(121)
pyplot.imshow(Uans, cmap = cm.RdBu)
pyplot.xticks([]), pyplot.yticks([]);
pyplot.subplot(122)
pyplot.imshow(Vans, cmap = cm.RdBu)
pyplot.xticks([]), pyplot.yticks([]);
fig.show()



