import numpy as np
import scipy as sc
import random
import time
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

N=6
W_6 = [np.exp(1j * 2*np.pi / 6)**n for n in np.arange(N)] 
sum_roots = np.sum(W_6)
print('sum of 6th roots = ' + str(round(np.abs(sum_roots)))) 


# plot 6th roots of unity
Re_W_6 = np.real(W_6) # real values
Im_W_6 = np.imag(W_6) # imaginary values

plt.figure(1)
plt.title('The six solutions to z**6 = 1')
unitCircle = plt.Circle((0, 0), radius=1, edgecolor='b', facecolor='None')
plt.gcf().gca().add_artist(unitCircle)
plt.scatter(Re_W_6[0], Im_W_6[0], color='blue', label='Sixth Roots of Unity')
plt.scatter(Re_W_6[1:], Im_W_6[1:], color='blue')
plt.plot([0, Re_W_6[1]], [0, Im_W_6[1]], 'black') 
plt.axhline(y=0, color='gray')
plt.axvline(x=0, color='gray') 
plt.xlim(-1.5,1.5)
plt.ylim(-1.5,1.5)
plt.xlabel('Real Axis')
plt.ylabel('Imaginary Axis')
plt.legend()
plt.show()

N=4
W_4 = [np.exp(1j * 2*np.pi / N)**n for n in np.arange(N)] 
sum_4roots = np.sum(W_4)
print('sum of 4th roots = ' + str(round(np.abs(sum_4roots)))) 


# plot 4th roots of unity
Re_W_4 = np.real(W_4) # real values
Im_W_4 = np.imag(W_4) # imaginary values

plt.figure(2)
plt.title('The four solutions to z**4 = 1')
unitCircle = plt.Circle((0, 0), radius=1, edgecolor='b', facecolor='None')
plt.gcf().gca().add_artist(unitCircle)
plt.scatter(Re_W_4[0], Im_W_4[0], color='blue', label='Fourth Roots of Unity')
plt.scatter(Re_W_4[1:], Im_W_4[1:], color='blue')
plt.plot([0, Re_W_4[1]], [0, Im_W_4[1]], 'black') 
plt.axhline(y=0, color='gray')
plt.axvline(x=0, color='gray') 
plt.xlim(-1.5,1.5)
plt.ylim(-1.5,1.5)
plt.xlabel('Real Axis')
plt.ylabel('Imaginary Axis')
plt.legend()
plt.show()



