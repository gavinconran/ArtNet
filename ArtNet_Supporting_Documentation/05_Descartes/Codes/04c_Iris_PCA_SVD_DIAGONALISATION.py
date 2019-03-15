"""
=========================================================
PCA example with Iris Data-set
=========================================================

Principal Component Analysis applied to the Iris dataset.

See `here <https://en.wikipedia.org/wiki/Iris_flower_data_set>`_ for more
information on this dataset.

"""
print(__doc__)

import numpy as np
import scipy.linalg as la
import matplotlib.pyplot as plt
import scipy as sc
from mpl_toolkits.mplot3d import Axes3D

from sklearn import decomposition
from sklearn import datasets

np.random.seed(5)
# load data
iris = datasets.load_iris()
XX = iris.data
y = iris.target

# prepare data for svd
# compute data size
m, n = np.shape(XX)
# subtract mean
#XX_norm = XX-np.matlib.repmat(mn, m, 1)
XX_mean = XX - XX.mean(0)

##### diagonalisation using svd
# perform the svd
u,s,v = la.svd(XX_mean) # / np.sqrt(n-1))
# produce diagonal variances
lamb_svd = np.diag(s)**2
# compute variance percentage
variance_percentage_svd = np.diagonal(lamb_svd) / np.sum(np.diagonal(lamb_svd))

# Plot SVD Principle Components
plt.figure(1)
objects = ['SVD-PC-1', 'SVD-PC-2', 'SVD-PC-3']
y_pos = np.arange(len(objects))
plt.bar(y_pos, variance_percentage_svd[:-1], align='center', alpha=0.5, color='cyan')
plt.xticks(y_pos, objects)
plt.ylabel('Explained variance in percent')
plt.title('Explained variance by different SVD principle components')
plt.show() 
# produce the principle components projection
k = 3
XX_proj = (u[:, :k]*s[:k]).round(2)

plt.figure(2)
pc1_svd = [x[0] for x in XX_proj]
pc2_svd = [-x[1] for x in XX_proj] # not sure why -ive sign
y = np.choose(y, [1, 2, 0]).astype(np.float)
plt.scatter(pc1_svd, pc2_svd, c=y, cmap=plt.cm.cool)
plt.title('SVD Projection onto new Feature Space')
plt.ylabel('Principle Component 2 (PC-2)')
plt.xlabel('Principle Component 1 (PC-1)')
plt.show()


##### run PCA
pca = decomposition.PCA(n_components=3)
pca.fit(XX)
X = pca.transform(XX)

# Plot Principle Components
plt.figure(3)
objects = ['PC-1', 'PC-2', 'PC-3']
variance_percentage = pca.explained_variance_ratio_
y_pos = np.arange(len(objects))
plt.bar(y_pos, variance_percentage, align='center', alpha=0.5)
plt.xticks(y_pos, objects)
plt.ylabel('Explained variance in percent')
plt.title('Explained variance by different principle components')
plt.show() 

# Plot Projection onto new (2-D) Feature Space
plt.figure(4)
pc1 = [x[0] for x in X]
pc2 = [x[1] for x in X]
#y = np.choose(y, [1, 2, 0]).astype(np.float)
plt.scatter(pc1, pc2, c=y, cmap=plt.cm.cool)
plt.title('PCA Projection onto new Feature Space')
plt.ylabel('Principle Component 2 (PC-2)')
plt.xlabel('Principle Component 1 (PC-1)')
plt.show()

### compare pca and svd
k=3
# singular values
pca.singular_values_.round(2)
s.round(2)

#components
pca.components_.round(2)
v[:k].round(2)

# transformed data
pca.transform(XX).round(2)
(u[:, :k]*s[:k]).round(2)






