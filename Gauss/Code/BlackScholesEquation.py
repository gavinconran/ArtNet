from math import *

# Cumulative Normal Distribution function
def CND(X):
    '''
    X is a random variable
    returns the Cumulative Normal Distribution for X
    '''
    (a1,a2,a3,a4,a5) = (0.31938153, -0.356563782, 1.781477937, -1.821255978, 1.330274429)
    L = abs(X)
    K = 1.0/(1.0+0.2316419*L)
    w = 1.0-1.0/sqrt(2.0*pi)*exp(-L*L/2.0)*(a1*K+a2*K*K+a3*pow(K,3)+a4*pow(K,4)+a5*pow(K,5))
    if X<0:
        w = 1.0-w
    return w
   
    
#### Black Scholes Helper Function
def BlackScholesCore(CallPutFlag,DF,F,K,T,v):
    vsqrt=v*sqrt(T)
    d1 = (log(F/K)+(vsqrt*vsqrt/2.))/vsqrt
    d2 = d1-vsqrt
    if CallPutFlag: # Call
        return DF*(F*CND(d1)-K*CND(d2))
    else: # Put
        return DF*(K*CND(-d2)-F*CND(-d1))

#### Black Sholes Function	
def BlackScholes(CallPutFlag,S,K,T,r,d,v):
    return BlackScholesCore(CallPutFlag,exp(-r*T),exp((r-d)*T)*S,K,T,v)


if __name__=="__main__":
	S=100. # Stock Price
	K=100. # Strike Price
	r=0.   # riskless rate of return
	v=0.2  # Volatility
	d=0.   # Time when pricing the option
	T=1.   # Time to from Strike date.
        CallPutFlag = True  #PUT = False

	print BlackScholes(CallPutFlag,S,K,T,r,d,v)

