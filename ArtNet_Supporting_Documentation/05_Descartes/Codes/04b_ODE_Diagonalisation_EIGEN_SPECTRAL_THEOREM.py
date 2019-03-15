import numpy as np
import scipy as sc
import scipy.linalg as la
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

# create exp(lambda*t) matrix
def expLambda(eigvals, t_array):
    lambda1 = np.real(eigvals[0]); lambda2 = np.real(eigvals[1])
    return [np.diag(np.exp((lambda1*t , lambda2*t))) for t in t_array]

# initial values
u0 = sc.matrix('1;1') 

# matrix A (A = A_T so A is symmetric - GOOD!)
A = np.array([[-2,1],[1,-2]])

# eigenvalues & Q (and S) of A
eigvals, Q = la.eig(A) #  A is symmetric but A is not orthogonal

# transpose of Q == inverse of Q
Q_T = np.transpose(Q)
Q_inv = la.inv(Q)

#print(np.allclose(Q_T, Q_inv))

# create an array of exp(lambda) matrices, one for each t
t_array = np.arange(0, 6, 0.1)
ExpLAMBDA = expLambda(eigvals, t_array)  

# run simulation
u_orthogonal1 = Q*ExpLAMBDA*Q_T*u0 # spectral theorem
u_orthogonal2 = Q*ExpLAMBDA*Q_inv*u0

# extract pure components
u1 = [x.tolist()[0][0] for x in u_orthogonal1]
u2 = [x.tolist()[0][1] for x in u_orthogonal1]

# combine components to form general solution
ut = [x.tolist()[0][0] + x.tolist()[0][1] for x in u_orthogonal1]

# plot results
plt.figure(1)
plt.title('First Pure Solution')
plt.xlabel('time, t')
plt.ylabel('u_1(t)')
plt.xlim(0,6)
plt.plot(t_array, u1, 'blue', label = 'component 1')
plt.show()

plt.figure(2)
plt.title('Second Pure Solution')
plt.xlabel('time, t')
plt.ylabel('u_2(t)')
plt.xlim(0,6)
plt.plot(t_array, u2, 'red', label = 'component 2')
plt.show()

plt.figure(3)
plt.title('General Solution')
plt.xlabel('time, t')
plt.ylabel('u(t)')
plt.xlim(0,6)
plt.plot(t_array, u1, 'blue', label = 'component 1')
plt.plot(t_array, u2, 'red', label = 'component 2')
plt.plot(t_array, ut, 'black', label='combined')
plt.legend()
plt.show()











