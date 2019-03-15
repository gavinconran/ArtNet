import numpy as np
import scipy.linalg as la
import scipy as sc
import matplotlib.pyplot as plt

######## Read and plot the image
XX = plt.imread('./Data/moonlanding.png').astype(float)

######## diagonalisation using svd
# compute data size
m, n = np.shape(XX)
# perform the svd
u,s,vt = la.svd(XX) # / np.sqrt(n-1))
# produce diagonal variances
lamb_svd = np.diag(s)**2
# compute variance percentage
variance_percentage_svd = np.diagonal(lamb_svd) / np.sum(np.diagonal(lamb_svd))

######## compress image
# Compress u, s and vt matrices:
pc_num = 50 # number of principle components
# u
u_comp = u[:, range(pc_num)]
# s
s_comp = s[:pc_num]
# vt
vt_comp = vt[range(pc_num), :]

#### compute size of image and compressed image
# image
r, c = np.shape(XX)
image_size = r * c
# compressed u
ru, cu = np.shape(u_comp)
u_comp_size = ru * cu
# compressed s
s_comp_size = len(s_comp)
# compressed vt
rv, cv = np.shape(vt_comp)
vt_comp_size = rv * cv 
# compressed image
image_compress_size = u_comp_size + vt_comp_size + len(s_comp)
# print image sizes
print('Normal image size: ' + str(image_size) + ' pixels')
print('Compressed image size: ' + str(image_compress_size) + ' pixels')
comp_percent = (float(image_compress_size) / float(image_size)) * 100
print('Compressed image is ' + str(round(comp_percent, 2)) + '% of the size of the original image')


######## Reconstruct image
# recocer matrix s
s_recover = np.diag(s_comp)
# recover image A = U * E * VT
XX_recover = sc.matrix(u_comp) * sc.matrix(s_recover) * sc.matrix(vt_comp)

######## apply Gaussian filter to recovered image
from scipy import ndimage
from PIL import Image
XX_recover_im = Image.fromarray(XX_recover)
XX_recover_im_blur = ndimage.gaussian_filter(XX_recover_im, 4)


###################################
########## plots
# plot image
plt.figure(1)
plt.imshow(XX, plt.cm.gray)
plt.title('Original image')
plt.show()

# Plot SVD Principle Components
plt.figure(2)
objects = ['SVD-PC-1', 'SVD-PC-2', 'SVD-PC-3', 'SVD-PC-4', 'SVD-PC-5', 'SVD-PC-6']
y_pos = np.arange(len(objects))
plt.bar(y_pos, variance_percentage_svd[:6], align='center', alpha=0.5, color='cyan')
plt.xticks(y_pos, objects)
plt.ylabel('Explained variance in percent')
plt.title('Explained variance by different SVD principle components')
plt.show() 

# plot recovered image
plt.figure(3)
plt.imshow(XX_recover, plt.cm.gray)
plt.title('Recovered image')
plt.show()

# plot filtered image
plt.figure(4)
plt.imshow(XX_recover_im_blur, plt.cm.gray)
plt.title('Blurred image')
plt.show()
















