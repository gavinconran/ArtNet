### An Efficient Portfolio with the SD of Boeing
# To clean up the memory of your current R session run the following line
rm(list=ls(all=TRUE))

# Load relevant packages
library("PerformanceAnalytics")
library("zoo")
# local package
source('09b_MPT_Functions.R')

# Load the data
data <- url("http://s3.amazonaws.com/assets.datacamp.com/course/compfin/lab8.RData")
load(data)

## The CER model
# Estimate the parameters: multivariate
mu_hat_annual <- apply(returns_df,2,mean)*12   
sigma2_annual <- apply(returns_df,2,var)*12 
sigma_annual <- sqrt(sigma2_annual)  
cov_mat_annual <- cov(returns_df)*12 
cov_hat_annual <- cov(returns_df)[1,2]*12    
rho_hat_annual <- cor(returns_df)[1,2]

# The annual estimates of the CER model parameters for Boeing and Microsoft
mu_boeing <- mu_hat_annual["rboeing"]
mu_msft <- mu_hat_annual["rmsft"]
sigma2_boeing <-  sigma2_annual["rboeing"]
sigma2_msft <- sigma2_annual["rmsft"]
sigma_boeing <- sigma_annual["rboeing"]
sigma_msft <- sigma_annual["rmsft"]
sigma_boeing_msft <- cov_hat_annual
rho_boeing_msft <- rho_hat_annual

# The ratio Boeing stock vs Microsoft stock (adds up to 1)
boeing_weights <- seq(from=-1, to=2, by=0.1)
msft_weights <- 1- boeing_weights
  
# Portfolio parameters
mu_portfolio <- boeing_weights*mu_boeing + msft_weights*mu_msft
sigma2_portfolio <- boeing_weights^2 * sigma2_boeing + msft_weights^2 * sigma2_msft + 2 * boeing_weights * msft_weights * sigma_boeing_msft
sigma_portfolio <- sqrt(sigma2_portfolio)

## Tangent Portfolio
# Annual risk-free rate of 3% per year for the T-bill
t_bill_rate <- 0.03

# Set of tangency portfolio weights
tangency_weights <- seq(from=0, to=2, by=0.1)

# Portfolio parameters
tangency_portfolio <-  tangency.portfolio(mu_hat_annual, cov_mat_annual, risk.free=0.03)

mu_portfolio_tangency_bill <- (1 -tangency_weights) * t_bill_rate + tangency_weights * tangency_portfolio$er
sigma_portfolio_tangency_bill <- tangency_weights * tangency_portfolio$sd

## An Efficient Portfolio with the SD of Boeing
tangency_weight = sigma_boeing / tangency_portfolio$sd
t_bill_weight = 1 - tangency_weight

# Calculate the portfolio parameters
mu_portfolio_efficient <- t_bill_rate * t_bill_weight + tangency_weight * tangency_portfolio$er
sd_portfolio_efficient <- tangency_weight * tangency_portfolio$sd    #sigma_boeing

# Plot previous exercises
jpeg('14_Efficient Portfolio.jpg', width = 800, height = 600)
plot(sigma_portfolio, mu_portfolio,bg="NA", type="b", pch=16, ylim=c(0, max(mu_portfolio)), xlim=c(0, max(sigma_portfolio)), xlab=expression(sigma[p]), ylab=expression(mu[p]),col=c(rep("green", 18), rep("red", 13)))
text(x=sigma_boeing, y=mu_boeing, labels="Boeing", pos=4)
text(x=sigma_msft, y=mu_msft, labels="MSFT", pos=4)
text(x=tangency_portfolio$sd, y=tangency_portfolio$er, labels="Tangency", pos=2)
points(sigma_portfolio_tangency_bill, mu_portfolio_tangency_bill, type="b", col="blue", pch=16)

# Plot Efficient Portfolio with the same risk as Boeing
points(sd_portfolio_efficient, mu_portfolio_efficient, type="p", col="orange", pch=16, cex=2)
text(x=sd_portfolio_efficient, y=mu_portfolio_efficient, labels="Efficient Portfolio with same risk as Boeing", pos=2, cex=0.75)
dev.off()








