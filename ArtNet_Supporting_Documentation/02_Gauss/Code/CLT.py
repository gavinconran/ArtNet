# Central Limit Theorem

import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import norm
from scipy import special

# Standard (Normal) Gaussian
mu, sigma = 0, 1

def gaussian(x, mu, sigma):
    normal = (1.0 / np.sqrt(2 * np.pi * sigma**2))
    distribution = np.exp(-(x - mu)**2 / (2 * sigma**2))
    return normal  * distribution

# generate range of x values from -2.3 to 2.3
x = np.linspace(norm.ppf(0.01)*2, norm.ppf(0.99)*2, 100)
# generate pdf (standard normal distribution)
pdf = gaussian(x, mu, sigma)

# Central Limit Theorem
# Generate Samples from a Normal Distribution
mu_original, sigma_original = 5, 10
sampleSizes = [100, 1000, 10000]
fig = 1
for sample in sampleSizes:
    trails = np.arange(1, sample)
    # need to generate CLT statistic
    statistic = []
    for n in trails:
        # s can be generated from any distribution
        s = (np.sum(np.random.normal(mu_original, sigma_original, n)) - n*mu_original)  / \
			(sigma_original * np.sqrt(n))
        statistic.append(s)
        
        
    # plot distribution
    plt.figure(fig)
    count, bins, ignored = plt.hist(statistic, 30, density=True, histtype='stepfilled', alpha=0.2)
    plt.plot(x, pdf)
    plt.title("Central Limit Theorem, Samples = %s" %sample)
    plt.ylabel("Probability")
    plt.xlabel("x")
    plt.show()
    fig += 1

# plot originating distribution
# generate range of x values from -2.3 to 2.3
x = np.linspace(-25, 35, 100)
# generate pdf
pdf_original = gaussian(x, mu_original, sigma_original)
plt.figure(fig)
plt.plot(x, pdf_original)
plt.title("Sampled Normal Distribution \n with mean=%s and sd=%s" %(mu_original, sigma_original))
plt.ylabel("Probability")
plt.xlabel("x")
plt.show()
    
