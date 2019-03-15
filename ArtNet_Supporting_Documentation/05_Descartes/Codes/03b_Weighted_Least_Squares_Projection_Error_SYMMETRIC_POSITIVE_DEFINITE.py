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

A_train = sc.matrix(data[1000:])
A_test = sc.matrix(data[:1000])

b_train = sc.matrix(price[1000:])
b_test = sc.matrix(price[:1000])

b_train = sc.transpose(b_train)
b_test = sc.transpose(b_test)

### linear algebra formulae from Columbia edx course ###
# compute Weighted least square (will equal x_hat)
I = np.identity(5)    # Identity matrix
lamb_da = 1           # regularisation = 1 is equivalent to Least Squares
W_ls = la.inv(lamb_da*I + np.transpose(A_train) * A_train) * np.transpose(A_train) * b_train
# compute W ridge regression
lamb_da = 1000000     # regularisation
W_rr = la.inv(lamb_da*I + np.transpose(A_train) * A_train) * np.transpose(A_train) * b_train

# make predictions
pred_wls = A_test * W_ls # (least squares)
pred_wrr = A_test * W_rr # (weighted least squares)

# sorted lists of predicted prices (ls)
pred_wls_List = [p[0]/1000000. for p in pred_wls.tolist()]
pred_wls_List.sort()
# sorted lists of predicted prices (wls)
pred_wrr_List = [p[0]/1000000. for p in pred_wrr.tolist()]
pred_wrr_List.sort()
# sorted lists of actual prices
actualList = [b[0]/1000000. for b in b_test.tolist()]
actualList.sort()

# plot graph
plt.figure(1)
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
plt.scatter(x[0], pred_wrr_List[0], color='r', s=0.7, label='Weighted Least Squares Price') 
plt.scatter(x[1:], pred_wrr_List[1:], s=0.5, color='r') 
plt.xlabel('x')
plt.ylabel('Price ($Million)')
plt.xlim(0,1000)
plt.legend(loc=0)
plt.show()

#########################################################
# print results
# warning: paper results used a test set of only 10 !!!!

print('')
print('Results using (1/A)*b:')
# print results
predicted = sorted(pred_wrr.tolist())
actual = sorted(b_test.tolist())
error = [actual[i][0] - predicted[i][0] for i in range(len(actual))]
for i in range(10):
    print('b = $' + str(int(actual[i][0])) +  \
          ', p = $' + str(int(predicted[i][0])) + \
          ', e = $' + str(int(error[i]))) 

######################################################
# plot the error distributions (Gaussian)
error_wls = [a - p for a, p in zip(actualList, pred_wls_List)] 
error_wrr = [a - p for a, p in zip(actualList, pred_wrr_List)] 

## Plot wls prediction error disribution
# fixed bin size
bins = np.arange(-0.9, 0.9, 0.05) # fixed bin size
plt.figure(2)
plt.xlim([-1, 1])

plt.hist(error_wls, bins=bins,  color='r')
plt.title('Least Squares Prediction Error')
plt.xlabel('Error $million')
plt.ylabel('count')

plt.show()

## Plot wrr prediction error disribution
plt.figure(3)
plt.xlim([-1, 1])

plt.hist(error_wrr, bins=bins, color='r')
plt.title('Weighted Least Squares Prediction Error')
plt.xlabel('Error $million')
plt.ylabel('count')

plt.show()

##########################################################
# plot the prediction distributions (Gaussian)
## maximum likelihood estimator, theta =(mu_ml, var_ml)
import scipy.stats

n=len(b_test)
# mean
mu_ml = np.sum(b_test) / (float(n)*1000000)
# variance
sigma2 = np.sum([(xi/1000000 - mu_ml)**2 for xi in b_test]) / float(n)
factor = (float(n-1)/float(n))
var_ml = factor * sigma2

theta = (mu_ml, var_ml)

print('')
print('Maximum Likelihood Estimators: mu = ' + str(round(mu_ml, 3)) + ', var = ' + str(round(var_ml,3)))
## Plot wls prediction error disribution


bins = np.arange(0.7, 1.7, 0.05) # fixed bin size
plt.figure(4)
plt.xlim([0.5, 2.0])
# plot histogram
plt.hist(pred_wls_List, bins=bins)
# plot pdf for maximum likelihood
x = np.arange(0.5, 2.0, 0.01)
pdf = scipy.stats.norm.pdf(x, mu_ml, var_ml)
plt.plot(x, pdf*49, color='r', label ='Estimated PDF')
plt.title('Least Squares Price Prediction')
plt.xlabel('Price Prediction $million')
plt.ylabel('count')
plt.legend()
plt.show()


## Plot wrr prediction error disribution
plt.figure(5)
plt.xlim([0.5, 2.0])

plt.hist(pred_wrr_List, bins=bins)
plt.title('Weighted Least Squares Price Prediction')
plt.xlabel('Price Prediction $million')
plt.ylabel('count')

plt.show()

