# Strong Law of Large Numbers

# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
import random

# Generate 1000 random numbers to represent the role of a dice
rolls = []
avg_observed=[]

trails = np.arange(0, 2001)

for x in trails:
  roll = random.randint(1,6)
  rolls.append(roll)
  avg_observed.append(np.mean(rolls))

avg_theoretical = np.mean(range(1,7))

plt.figure(1)
plt.plot(trails, avg_observed, color='green', label = 'observed averages')
plt.axhline(avg_theoretical, color='blue', label = "theoretical average")
plt.title(r"Average dice roll by number of rolls")
plt.ylabel("Average")
plt.xlabel("Number of trails")
plt.ylim(1, 6)
plt.xlim(1, 2001)
plt.legend()
plt.show()


     

