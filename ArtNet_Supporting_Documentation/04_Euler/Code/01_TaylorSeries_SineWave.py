import matplotlib.pyplot as plt
import numpy as np
import math


x = np.arange(-10.0, 10.0, 0.01)
sinx = np.sin(x)
sinx_0 = x
sinx_1 = sinx_0 - (x**3 / math.factorial(3))
sinx_2 = sinx_1 + (x**5 / math.factorial(5))
sinx_3 = sinx_2 - (x**7 / math.factorial(7))
sinx_4 = sinx_3 + (x**9 / math.factorial(9))
sinx_5 = sinx_4 - (x**11 / math.factorial(11))
sinx_6 = sinx_5 + (x**13 / math.factorial(13))
sinx_7 = sinx_6 - (x**15 / math.factorial(15))

plt.figure(1)
plt.title("Sine Wave"
          "\n"
          "Taylor Series")
plt.ylabel("Magnitude")
plt.xlabel("Time")
plt.plot(x, sinx, 'black')
plt.plot(x, sinx_0)
plt.plot(x, sinx_1)
plt.plot(x, sinx_2)
plt.plot(x, sinx_3)
plt.plot(x, sinx_4)
plt.plot(x, sinx_5)
plt.plot(x, sinx_6)
plt.plot(x, sinx_7)
plt.xlabel('Radians')
plt.ylabel('Amplitude')
plt.axhline(y=0, color='gray', linestyle='--')
plt.axvline(x=0, color='gray', linestyle='--')
plt.ylim(-10,10)
plt.xlim(-10, 10)
plt.show()
