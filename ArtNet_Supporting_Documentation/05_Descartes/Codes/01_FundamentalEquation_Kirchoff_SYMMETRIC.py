import numpy as np
import scipy as sc

# A captures the connections and direction of current between Nodes using KVL current law
A_T = sc.matrix('-1  0 -1  0 -1; \
                  1 -1  0  0  0; \
                  0  1  1 -1  0')

A = sc.transpose(A_T)

# C is the Conductance matrix
# R is the Resistance matrix
Conductance = [1,2,3,4,5]
Resistance = [1.0 / c for c in Conductance]

C = np.diag(Conductance)
C_inv = np.diag(Resistance)

# b captures additional battery sources
b = sc.transpose(sc.matrix('0,0,3,0,0'))

# f captures additional current sources
f = sc.transpose(sc.matrix('0,5,0'))

## construct operational matrices
# left hand side
LHS_S = A_T*C*A  # symmetric matrix - the goal!
# if matrix was orthogonal (orthonormal) we can use A_t=A_inv but not here
LHS_S_T = sc.transpose(LHS_S)

# must compute the inverse rather tan use A_t
LHS_S_inv = np.linalg.inv(LHS_S) 

# right hand side
RHS_S = A_T*C*b

# solve for x, ,the potentials, using the Fundamental Equation
x = LHS_S_inv * RHS_S - f

# solve for y, the currents by applying Ohm's law
y = C*(b - A*x)

# potentials
x = x.tolist()
print('Potentials at the Nodes')
print('x1 = ' + str(x[0][0]) + ' volts')
print('x2 = ' + str(x[1][0]) + ' volts')
print('x3 = ' + str(x[2][0]) + ' volts')
print('')

# currents
y = y.tolist()
print('Currents on the Edges')
print('y1 = ' + str(y[0][0]) + ' amps')
print('y2 = ' + str(y[1][0]) + ' amps')
print('y3 = ' + str(y[2][0]) + ' amps')
print('y4 = ' + str(y[3][0]) + ' amps')
print('y5 = ' + str(y[4][0]) + ' amps')









 
