import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

# Function
N=100
xmin=0; xmax=2.0*np.pi # 2 pi range
step = (xmax-xmin)/(N) # step size
xdata = np.linspace(step, xmax, N) # x_axis data

# Exact derivative
v = np.exp(np.sin(xdata))    # function to be differentiated
derv = np.cos(xdata)*v   # exact derivative

# Estimated derivative through derivative relationship
vhat = np.fft.fft(v)     # FT of function

# 
what = 1j*np.zeros(N)
what[0:N/2] = 1j*np.arange(0, N/2, 1)
what[N/2+1:] = 1j*np.arange(-N/2 + 1, 0, 1)
what = what*vhat 
w = np.real(np.fft.ifft(what))

# plot function
plt.figure(1)
plt.title('Derivative Relationship'
          "\n"
          "Function")
plt.xlabel('x');
plt.ylabel('f(x)')
plt.plot(xdata, v,'b')
plt.axhline(y=0, color = 'black')
plt.axvline(x=0, color = 'black')

plt.show()

# plot exact and estimated derivative
plt.figure(2)
plt.title('Derivative Relationship'
          "\n"
          "Exact and Estimated")
plt.xlabel('x');
plt.ylabel('df/dx')
plt.plot(xdata, derv,'g',xdata, w, 'mo')
plt.axhline(y=0, color = 'black')
plt.axvline(x=0, color = 'black')
plt.show()




