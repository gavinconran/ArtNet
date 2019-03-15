import matplotlib.pyplot as plt
from matplotlib import cm
import numpy as np
import scipy as sc
import scipy.linalg as la
import matplotlib as mpl
mpl.style.use('classic')

def sumOfSquares(a, b, c, x, y):
    X, Y = np.meshgrid(x,y)
    return a*X**2 + 2*b*X*Y + c*Y**2

# create x, y points
nx = 41
ny = 41

x = np.linspace(-1,1,nx)
y = np.linspace(-1,1,ny)



## positive definite
# a > 0, ac - b**2 > 0
a = 2; b = 6; c = 200;
sos_pd = sumOfSquares(a, b, c, x, y)

fig1, ax1 = plt.subplots()
ax1.set_title('Positive Definite')
ax1.set_xlabel('x')
ax1.set_ylabel('y')
im1 = ax1.imshow(sos_pd, origin='lower', interpolation='none', cmap=plt.cm.jet)
fig1.colorbar(im1, ax=ax1)
plt.show()

## positive semi-definite
# a > 0, ac - b**2 = 0
a = 2; b = 6; c = 18;
sos_psd = sumOfSquares(a, b, c, x, y)

fig2, ax2 = plt.subplots()
ax2.set_title('Positive Semi-Definite')
ax2.set_xlabel('x')
ax2.set_ylabel('y')
im2 = ax2.imshow(sos_psd, origin='lower', interpolation='none', cmap=plt.cm.jet)
fig2.colorbar(im2, ax=ax2)
plt.show()

## saddle point
# a > 0, ac - b**2 < 0
a = 2; b = 6; c = 0;
sos_sp = sumOfSquares(a, b, c, x, y)

fig3, ax3 = plt.subplots()
ax3.set_title('Saddle Point')
ax3.set_xlabel('x')
ax3.set_ylabel('y')
im3 = ax3.imshow(sos_sp, origin='lower', interpolation='none', cmap=plt.cm.jet)
fig3.colorbar(im3, ax=ax3)
plt.show()

# TESTS
# create matrices
Apd_mat = sc.matrix('2 6; 6 200') # positive definite matrix
Apsd_mat = sc.matrix('2 6; 6 18') # positive semi-definite matrix 
Asp_mat = sc.matrix('2 6; 6 0')   # saddle point matrix

# create x_vector
xx_t = sc.matrix('3 -1'); xx = np.transpose(xx_t)

# test 1: positive definite
# x_t * A * x > 0 for all non zero real vectors x
Apd = xx_t * Apd_mat * xx
Apd = Apd.tolist()[0][0]
print('Positive Definite Test:')
print(' x_t * A * x = ' + str(Apd) + ' > 0: ' + str(Apd > 0.0))

Apd_eigvals, Q = la.eig(Apd_mat)
print('eigen values = ' + str(np.abs(Apd_eigvals)) + ' > 0: ' + str(all(Apd_eigvals > 0.0)))
print('')

# test 2: positive semi-definite
# one eigenvalue of A satisfy lambda_i == 0
Apsd_eigvals, Q = la.eig(Apsd_mat)
print('Positive semi-definite Test:')
print('eigen values = ' + str(np.abs(Apsd_eigvals)) + ' == 0: ' + str(any(Apsd_eigvals == 0.0)))

Apsd = xx_t * Apsd_mat * xx
Apsd = Apsd.tolist()[0][0]
print(' x_t * A * x = ' + str(Apsd) + ' = 0: ' + str(Apsd == 0.0))
print('')

# test 3: saddle point
Asp = xx_t * Asp_mat * xx
Asp = Asp.tolist()[0][0]
print('Saddle Point Test:')
print(' x_t * A * x = ' + str(Asp) + ' < 0: ' + str(Asp < 0.0))





