# PDF and CDF

# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import norm
from scipy import special

# Normal Distribution (mu, sigma = 0, 1)
plt.figure(1)
# generate range of x values from -2.3 to 2.3
x = np.linspace(norm.ppf(0.01), norm.ppf(0.99), 100)
# plot normal distribution
plt.plot(x, norm.pdf(x),'r-', lw=5, alpha=0.6, label='norm pdf')

# Generate random numbers
r = norm.rvs(size=1000)

# compare histogram
plt.hist(r, density=True, histtype='stepfilled', alpha=0.2)
plt.legend(loc='best', frameon=False)

plt.show()

# Gaussian - Normal
mu, sigma = 0, 0.1 # mean and standard deviation
s = np.random.normal(mu, sigma, 1000)

plt.figure(2)
count, bins, ignored = plt.hist(s, 30, density=True, histtype='stepfilled', alpha=0.2)
plt.plot(bins, 1/(sigma * np.sqrt(2 * np.pi)) *
                np.exp( - (bins - mu)**2 / (2 * sigma**2) ),
          linewidth=2, color='r')

plt.show()

# Gaussian - various mu and sigma
mu0, mu1 = 0, -2
sigma0, sigma1, sigma2, sigma3 = 0.4, 1.0, 2.2, 0.7

def gaussian(x, mu, sigma):
    normal = (1.0 / np.sqrt(2 * np.pi * sigma**2))
    distribution = np.exp(-(x - mu)**2 / (2 * sigma**2))
    return normal  * distribution  

# generate range of x values from -2.3 to 2.3
x = np.linspace(norm.ppf(0.01) * 3, norm.ppf(0.99) * 3, 100)
# generate pdf
pdf0 = gaussian(x, mu0, sigma0)
pdf1 = gaussian(x, mu0, sigma1)
pdf2 = gaussian(x, mu0, sigma2)
pdf3 = gaussian(x, mu1, sigma3)

plt.figure(3)
plt.plot(x, pdf0, label = 'mu = 0, sigma = 0.4')
plt.plot(x, pdf1, label = 'mu = 0, sigma = 1.0')
plt.plot(x, pdf2, label = 'mu = 0, sigma = 2.2')
plt.plot(x, pdf3, label = 'mu = -2.0, sigma = 0.7')
plt.title(r"Gaussian (Normal) Distributions or PDF")
plt.ylabel("Probability")
plt.xlabel("x")
plt.legend()
plt.show()

# CDF
def cdf(x, mu, sigma):
    return 0.5 * (1 + special.erf((x - mu) / (sigma *np.sqrt(2))))

# generate range of x values from -2.3 to 2.3
x = np.linspace(norm.ppf(0.01) * 3, norm.ppf(0.99) * 3, 100)
# generate pdf
cdf0 = cdf(x, mu0, sigma0)
cdf1 = cdf(x, mu0, sigma1)
cdf2 = cdf(x, mu0, sigma2)
cdf3 = cdf(x, mu1, sigma3)

plt.figure(4)
plt.plot(x, cdf0, label = 'mu = 0, sigma = 0.4')
plt.plot(x, cdf1, label = 'mu = 0, sigma = 1.0')
plt.plot(x, cdf2, label = 'mu = 0, sigma = 2.2')
plt.plot(x, cdf3, label = 'mu = -2.0, sigma = 0.7')
plt.title(r"Cumulatative Distribution Function (CDF)")
plt.ylabel("Cumulatative Probability")
plt.xlabel("x")
plt.legend()
plt.show()










