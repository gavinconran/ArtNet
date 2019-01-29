# PDF and CDF

# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import norm
from scipy import special

# Boltzmann distribution - various a values
def boltzmann(x, a):
    factor = np.sqrt(2. / np.pi)
    distribution = (x**2 * np.exp(-x**2/(2*a**2))) / a**3
    return factor * distribution  

# generate range of x values from 0 to 20
x = np.linspace(0, 20.0, 100)
# a variable
a1, a2, a5 = 1, 2, 5
# generate pdf
boltz0 = boltzmann(x, a1)
boltz1 = boltzmann(x, a2)
boltz2 = boltzmann(x, a5)

plt.figure(1)
plt.plot(x, boltz0, color='blue', label = 'a = 1')
plt.plot(x, boltz1, color='red', label = 'a = 2')
plt.plot(x, boltz2, color='green', label = 'a = 5')
plt.title("Probability Density Function \n for Boltzmann Distributions")
plt.ylabel("PDF")
plt.xlabel("x")
plt.ylim(0.0, 0.6)
plt.xlim(0, 20)
plt.legend()
plt.show()











