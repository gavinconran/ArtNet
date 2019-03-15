import pandas as pd
import numpy as np
import scipy.linalg as la
import scipy as sc
import matplotlib.pyplot as plt
import matplotlib as mpl
mpl.style.use('classic')

from scipy.linalg import solve

### prepare data
# read data
USAhousing = pd.read_csv('./Data/USA_Housing.csv')

# independent variables
data = USAhousing[['Avg. Area Income', 'Avg. Area House Age', 'Avg. Area Number of Rooms',
               'Avg. Area Number of Bedrooms', 'Area Population']]
# dependent variable
price = USAhousing['Price']

######################################
################# 10 test set
# create train and test data sets
x = np.arange(1, 11)

A_train = sc.matrix(data[10:])
A_test = sc.matrix(data[:10])

b_train = sc.matrix(price[10:])
b_test = sc.matrix(price[:10])

b_train = sc.transpose(b_train)
b_test = sc.transpose(b_test)

### prepare A_t * A * x_hat = A_T * b
#A and A transpose are rectangular matrices
A_t = sc.transpose(A_train)
# A_t*A is a symmetric square matrix
A = A_t*A_train
# A_t * b
b = A_t*b_train
# solve for x_hat (Ax_hat = b)
x_hat = solve(A,b)
# A is symmetric but not orthonormal, so have to use the inverse of A - is this correct? yes!!!
# x_hat1 = 1/A * b
x_hat1 = sc.linalg.inv(A) * b

# compute difference between real prices and predicted from test data set
# x_hat contains the coefficients, one for each of the 5 features
# using solve
pred = A_test * x_hat
diff = b_test - pred 
# using inverse A
pred1 = A_test * x_hat1
diff1 = b_test - pred1 

# sorted lists of predicted and actual prices
predList = [p[0]/1000000. for p in pred.tolist()]
predList.sort()
actualList = [b[0]/1000000. for b in b_test.tolist()]
actualList.sort()

# plot graph
plt.figure(1)
plt.title('House Prices: Predicted vs Actual'
	  "\n"
          "Size of Test Dataset = 10")
# error lines between actual and predicted prices
plt.plot( [x[0], x[0]], [predList[0],actualList[0]], 'r-', label='Prediction Error') 
for i in range(1, len(x)):
    plt.plot( [x[i], x[i]], [predList[i],actualList[i]], 'r-') 
# actual prices
plt.scatter(x, actualList, color='k', label='Actual Price')
# predicted prices
plt.scatter(x, predList, color='b', label='Predicted Price') 
plt.xlabel('x')
plt.ylabel('Price ($Million)')
plt.legend(loc=0)
plt.show()

#########################################
############ 1000 test set
# create train and test data sets
x = np.arange(1, 1001)

A_train = sc.matrix(data[1000:])
A_test = sc.matrix(data[:1000])

b_train = sc.matrix(price[1000:])
b_test = sc.matrix(price[:1000])

b_train = sc.transpose(b_train)
b_test = sc.transpose(b_test)

### prepare A_t * A * x_hat = A_T * b
#A and A transpose are rectangular matrices
A_t = sc.transpose(A_train)

# A_t*A is a symmetric square matrix
A = A_t*A_train

# A_t * b
b = A_t*b_train


# solve for x_hat (Ax_hat = b)
x_hat = solve(A,b)
# A is symmetric but not orthonormal, so have to use the inverse of A - is this correct? yes!!!
# x_hat1 = 1/A * b
x_hat1 = sc.linalg.inv(A) * b

# compute difference between real prices and predicted from test data set
# x_hat contains the coefficients, one for each of the 5 features
# using solve
pred = A_test * x_hat
diff = b_test - pred 
# using inverse A
pred1 = A_test * x_hat1
diff1 = b_test - pred1 

# sorted lists of predicted and actual prices
predList = [p[0]/1000000. for p in pred.tolist()]
predList.sort()
actualList = [b[0]/1000000. for b in b_test.tolist()]
actualList.sort()

# plot graph
plt.figure(2)
plt.title('House Prices: Predicted vs Actual'
	  "\n"
          "Size of Test Dataset = 1000")
# error lines between actual and predicted prices
# actual prices
plt.scatter(x[0], actualList[0], color='k', s=0.7, label='Actual Price')
plt.scatter(x[1:], actualList[1:], s=0.5, color='k')
# predicted prices
plt.scatter(x[0], predList[0], color='b', s=0.7, label='Predicted Price') 
plt.scatter(x[1:], predList[1:], s=0.5, color='b') 
plt.xlabel('x')
plt.ylabel('Price ($Million)')
plt.xlim(0,1000)
plt.legend(loc=0)
plt.show()
#########################################################
# print results

print('')
print('Results using (1/A)*b:')
# print results
predicted = sorted(pred.tolist())
actual = sorted(b_test.tolist())
error = [actual[i][0] - predicted[i][0] for i in range(len(actual))]
for i in range(10):
    print('b = $' + str(int(actual[i][0])) +  \
          ', p = $' + str(int(predicted[i][0])) + \
          ', e = $' + str(int(error[i]))) 

#############################################################
## Positive Definite
# create the squre, symmetric matrix, R = A_t * A
A_t = np.transpose(A_train)
R = A_t * A_train
print('Positive Definiteness tests:')
# all eigenvalues of A satisfy lambda_i > 0
R_eigvals, Q = la.eig(R)
R_eigvals = [round(np.abs(eig)) for eig in R_eigvals] 
print('Test3: eigen values = ' + str(R_eigvals) + ' > 0: ' + str(R_eigvals > 0.0))

# x_t * A * x > 0 for all non zero real vectors x
x = x_hat
x_t = np.transpose(x)
R_pd = x_t * R * x
R_pd = R_pd.tolist()[0][0]
print('Test 4a: x_t * A * x = ' + str(R_pd) + ' > 0: ' + str(R_pd > 0.0))
R_pd2 = np.transpose(A_train * x) * (A_train * x)
R_pd2 = R_pd2.tolist()[0][0]
print('Test 4a: (A*x)_t * (A*x) = ' + str(R_pd2) + ' > 0: ' + str(R_pd2 > 0.0))

# Factorisation: A = Q * Lambda * Q_t
Lambda = np.diag(R_eigvals)
Q_t = np.transpose(Q)
R_pd_fact = x_t * Q*Lambda*Q_t * x
R_pd_fact = round(np.abs(R_pd_fact.tolist()[0][0]))
print('Test 4b: x_t * Q * Lambda * Q_t * x = ' + str(R_pd_fact) + ' > 0: ' + str(R_pd_fact > 0.0))

