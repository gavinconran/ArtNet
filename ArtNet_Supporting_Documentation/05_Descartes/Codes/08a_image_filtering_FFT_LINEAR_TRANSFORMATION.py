import numpy as np
import matplotlib.pyplot as plt

# Read and plot the image
im = plt.imread('./Data/moonlanding.png').astype(float)

plt.figure(1)
plt.imshow(im, plt.cm.gray)
plt.title('Original image')
plt.show()

# Compute the 2nd FFT of the input image
from scipy import fftpack
im_fft = fftpack.fft2(im)

# Plot results
def plot_spectrum(im_fft):
    from matplotlib.colors import LogNorm
    # A logarithmic colormap
    plt.imshow(np.abs(im_fft), norm=LogNorm(vmin=5))
    plt.colorbar()

plt.figure(2)
plot_spectrum(im_fft)
plt.title('Fourier Transform')
plt.show()


# Filter in FFT
# In the lines following, we'll make a copy of the original spectrum and
# truncate coefficients.

# Define the fraction of coefficients (in each direction) we keep
keep_fraction = 0.1

# Call ff a copy of the original transform. Numpy arrays have a copy
# method for this purpose.
im_fft2 = im_fft.copy()

# Set r and c to be the number of rows and columns of the array.
r, c = im_fft2.shape

# Set to zero all rows with indices between r*keep_fraction and
# r*(1-keep_fraction):
im_fft2[int(r*keep_fraction):int(r*(1-keep_fraction))] = 0

# Similarly with the columns:
im_fft2[:, int(c*keep_fraction):int(c*(1-keep_fraction))] = 0

plt.figure(3)
plot_spectrum(im_fft2)
plt.title('Filtered Spectrum')
plt.show()


# Reconstruct the final image
# Reconstruct the denoised image from the filtered spectrum, keep only the
# real part for display.
im_new = fftpack.ifft2(im_fft2).real

plt.figure(4)
plt.imshow(im_new, plt.cm.gray)
plt.title('Reconstructed Image')
plt.show()








