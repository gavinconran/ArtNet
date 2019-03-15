# housing data least squares with svd pre-processing
import pandas as pd
import numpy as np
import scipy.linalg as la
import scipy as sc
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

### prepare data
# read data
USAhousing = pd.read_csv('./Data/USA_Housing.csv')

# independent variables
data = USAhousing[['Avg. Area Income', 'Avg. Area House Age', 'Avg. Area Number of Rooms',
               'Avg. Area Number of Bedrooms', 'Area Population']]
# dependent variable
price = USAhousing['Price']


#########################################################
# with Regularisation (Weights)
# create train and test data sets
x = np.arange(1, 1001)

# compute data size
m, n = np.shape(data)
# subtract mean
data_mean = data - data.mean(0)
# compute svd
ud, sd, vd = la.svd(data)

A_train = sc.matrix(data[1000:])
A_train_proj = np.transpose(ud[1000:]) * A_train
A_test = sc.matrix(data[:1000])
A_test_proj = np.transpose(ud[:1000]) * A_test

b_train = sc.matrix(price[1000:])
b_test = sc.matrix(price[:1000])

b_train = sc.transpose(b_train)
b_test = sc.transpose(b_test)


##### diagonalisation using svd
# produce diagonal variances
lamb_svd = np.diag(sd)**2
# compute variance percentage
variance_percentage_svd = np.diagonal(lamb_svd) / np.sum(np.diagonal(lamb_svd))


# Plot SVD Principle Components
plt.figure(1)
objects = ['SVD-PC-1', 'SVD-PC-2', 'SVD-PC-3', 'SVD-PC-4', 'SVD-PC-5']
y_pos = np.arange(len(objects))
plt.bar(y_pos, variance_percentage_svd, align='center', alpha=0.5, color='cyan')
plt.xticks(y_pos, objects)
plt.ylabel('Explained variance in percent')
plt.title('Explained variance by different SVD principle components')
plt.show() 


# produce the principle components projection
k = 1
# training data
XX_train_proj = (ud[1000:, :k]*sd[:k]).round(2)
XX_train_proj_mat = sc.matrix(XX_train_proj)

# test data
XX_test_proj = (ud[:1000, :k]*sd[:k]).round(2)
XX_test_proj_mat = sc.matrix(XX_test_proj)


### linear algebra formulae from Columbia edx course ###
# compute Weighted least square (will equal x_hat)
I = np.identity(k)    # Identity matrix
lamb_da = 1           # regularisation = 1 is equivalent to Least Squares
W_ls = la.inv(lamb_da*I + np.transpose(XX_train_proj_mat) * XX_train_proj_mat) * np.transpose(XX_train_proj_mat) * b_train

# make predictions
pred_wls = XX_test_proj_mat * W_ls # (least squares)

# sorted lists of predicted prices
pred_wls_List = [p[0]/1000000. for p in pred_wls.tolist()]
pred_wls_List.sort()
# sorted lists of actual prices
actualList = [b[0]/1000000. for b in b_test.tolist()]
actualList.sort()


# plot graph
plt.figure(2)
plt.title('Predicted vs Actual Prices'
	  "\n"
          "Size of Test Dataset = 1000")
# error lines between actual and predicted prices
# actual prices
plt.scatter(x[0], actualList[0], color='k', s=0.7, label='Actual Price')
plt.scatter(x[1:], actualList[1:], s=0.5, color='k')
# predicted prices
plt.scatter(x[0], pred_wls_List[0], color='b', s=0.7, label='Least Squares Price') 
plt.scatter(x[1:], pred_wls_List[1:], s=0.5, color='b') 
plt.xlabel('x')
plt.ylabel('Price ($Million)')
plt.xlim(0,1000)
plt.legend(loc=0)
plt.show()




