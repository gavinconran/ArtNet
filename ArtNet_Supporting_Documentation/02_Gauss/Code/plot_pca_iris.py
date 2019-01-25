#!/usr/bin/python
# -*- coding: utf-8 -*-

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
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


from sklearn import decomposition
from sklearn import datasets

np.random.seed(5)
# load data
iris = datasets.load_iris()
XX = iris.data
y = iris.target

# run PCA
pca = decomposition.PCA(n_components=3)
pca.fit(XX)
X = pca.transform(XX)

# Plot Principle Components
plt.figure(1)
objects = ['PC-1', 'PC-2', 'PC-3']
variance_percentage = pca.explained_variance_ratio_
y_pos = np.arange(len(objects))
plt.bar(y_pos, variance_percentage, align='center', alpha=0.5)
plt.xticks(y_pos, objects)
plt.ylabel('Explained variance in percent')
plt.title('Explained variance by different principle components')
plt.show() 

# Plot Projection onto new (2-D) Feature Space
plt.figure(2)
pc1 = [x[0] for x in X]
pc2 = [x[1] for x in X]
y = np.choose(y, [1, 2, 0]).astype(np.float)
plt.scatter(pc1, pc2, c=y, cmap=plt.cm.cool)
plt.title('Projection onto new Feature Space')
plt.ylabel('Principle Component 2 (PC-2)')
plt.xlabel('Principle Component 1 (PC-1)')
plt.show()


