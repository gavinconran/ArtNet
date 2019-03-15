import numpy as np
import scipy as sc
import scipy.linalg as la
import time

## Recursion
def fib_rec(num):
    if (num==0 or num==1):
        return num
    return fib_rec(num-1) + fib_rec(num-2)

# test
print('Fibonacci test')
print('1: ' + str(fib_rec(1)))
print('2: ' + str(fib_rec(2)))
print('3: ' + str(fib_rec(3)))
print('4: ' + str(fib_rec(4)))
print('5: ' + str(fib_rec(5)))
print('10: ' + str(fib_rec(10)))
print('20: ' + str(fib_rec(20)))
start0 = time.time()
print('30: ' + str(fib_rec(30)))
end0 = time.time()
print('time to execute fib 30 using recursion: ' + str(end0 - start0) + ' seconds')

#print('40: ', fib_rec(40))
# fib_rec(40) = 102334155 and takes 40.8 seconds to execute)

## Linear Algebra
def fib_eig(k):
    # initial values, i.e. Fibonacci numbers
    u0 = sc.matrix('1;0') 
    
    # matrix A (A = A_T so A is symmetric - GOOD!)
    A = np.array([[1,1],[1,0]])

    # eigenvalues & Q of A
    eigvals, Q = la.eig(A) # A is symmetric as A == A_t. Q is orthogonal Q_t * Q == I (Q_t == Q_inv)

    # transpose of Q / inverse of S
    Q_T = np.transpose(Q)
    Q_inv = la.inv(Q)
    #print(np.allclose(Q_T, Q_inv))

    # LAMBDA_k matrix
    LAMBDA_k = exp_k_mat(eigvals, k)
    u = Q * LAMBDA_k * Q_T * u0
    return u[0].tolist()[0][0] + u[1].tolist()[0][0]


# create exp(k) matrix
def exp_k_mat(eigvals, k):
    lambda1 = np.real(eigvals[0]); lambda2 = np.real(eigvals[1])
    return np.diag((lambda1**k , lambda2**k))


print('Eigen test')
print('1: ' + str(int(round(fib_eig(0)))))
print('2: ' + str(int(round(fib_eig(1)))))
print('3: ' + str(int(round(fib_eig(2)))))
print('4: ' + str(int(round(fib_eig(3)))))
print('5: ' + str(int(round(fib_eig(4)))))
print('10: ' + str(int(round(fib_eig(9)))))
print('20: ' + str(int(round(fib_eig(19)))))

start1 = time.time()
print('30: ' + str(int(round(fib_eig(29)))))
end1 = time.time()
print('time to execute fib 30 using eigenvalues: ' + str(end1 - start1) + ' seconds')

start2 = time.time()
print('1000')
end2 = time.time()
print('time to execute fib 1000 using eigenvalues: '+ str(end2 - start2) + ' seconds')

# regardless of the number, execution is in the region of hundrends on MICRO-seconds!!!
