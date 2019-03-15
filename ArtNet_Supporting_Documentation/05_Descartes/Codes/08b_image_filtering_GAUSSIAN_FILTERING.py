import numpy as np
import matplotlib.pyplot as plt

# Read and plot the image
im = plt.imread('./Data/moonlanding.png').astype(float)

plt.figure(1)
plt.imshow(im, plt.cm.gray)
plt.title('Original image')
plt.show()

# Filter using Gaussian filtering
from scipy import ndimage
im_blur = ndimage.gaussian_filter(im, 4)

plt.figure(2)
plt.imshow(im_blur, plt.cm.gray)
plt.title('Blurred image')

plt.show()

