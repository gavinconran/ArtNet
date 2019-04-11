# Brownian motion simulation
using Random

function bpath(T,N)
    #srand(100)
    Random.seed!(0)
    dt = T/N
    dW = zeros(10,N)
    W = zeros(10,N)

    dW[:,1] = sqrt(dt)*randn(10)
    W[:,1] = dW[:,1]

    for j = 2:N
        dW[:,j] .= sqrt(dt)*randn(10)
        W[:,j] .= W[:,j-1] .+ dW[:,j]
    end
    [0:dt:T-dt],W
end # Translation took < 1 minute
@time t,W = bpath(1,100000) # Timing is slower in Jupyter!
@time t,W = bpath(1,100000) # Timing is slower in Jupyter!
# 10x speedup over MATLAB
using Plots
plot(t,W',color=:red,xlabel="t",ylabel="W(t)")
