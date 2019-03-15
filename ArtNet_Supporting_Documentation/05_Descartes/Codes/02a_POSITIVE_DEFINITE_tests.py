# positive definite

import numpy as np
import scipy as sc
import scipy.linalg as la
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

# create tridiagonal matrix, A
def create_A(N):
    d = 2*np.diag(np.ones(N))          # diagonal
    ud = np.diag(np.ones(N-1)*-1, 1)   # upper diagonal
    ld = np.diag(np.ones(N-1)*-1,-1)   # lower diagonal
    return d + ud + ld

A3 = create_A(3)


## positive definite tests
print('Positive Definiteness tests:')
# all the pivots (without row exchanges) satisfy d_k > 0
# lu uses gaussian elimination to compute the pivots
A3_p, A3_l, A3_u = la.lu(A3)
A3_pivots = np.diagonal(A3_u)
print('Test 1: pivots = ' + str(A3_pivots) + ' > 0: ' + str(A3_pivots > 0.0))

# all the upper left submatrices A_k have positive determinants
A3_det = la.det(A3)
A2 = A3[:2, :2]
A2_det = la.det(A2)
A1_det = A3[0, 0]
A3_dets = np.array([A1_det, A2_det, A3_det])
print('Test 2: determinants = ' + str(A3_dets) + ' > 0: ' + str(A3_dets > 0.0))

# all eigenvalues of A satisfy lambda_i > 0
A3_eigvals, Q = la.eig(A3)
print('Test 3: eigen values = ' + str(np.abs(A3_eigvals)) + ' > 0: ' + str(A3_eigvals > 0.0))

# x_t * A * x > 0 for all non zero real vectors x
x_t = sc.matrix('1 2 3')
x = np.transpose(x_t)
A3_pd = x_t * A3 * x
A3_pd = A3_pd.tolist()[0][0]
print('Test 4a: x_t * A * x = ' + str(A3_pd) + ' > 0: ' + str(A3_pd > 0.0))

# Factorisation: A = Q * Lambda * Q_t
Lambda = np.diag(A3_eigvals)
Q_t = np.transpose(Q)
A3_pd_fact = x_t * Q*Lambda*Q_t * x
A3_pd_fact = round(np.abs(A3_pd_fact.tolist()[0][0]))
print('Test 4b: x_t * Q * Lambda * Q_t * x = ' + str(A3_pd_fact) + ' > 0: ' + str(A3_pd_fact > 0.0))






