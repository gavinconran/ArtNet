# NETBP uses backpropagation to train a network
# Original Matlab code from
# Deep Learning: An Introduction for Applied Mathematicians by Higham et al
# arXiv: 1801:05894

using LinearAlgebra
using Random

### data ###
x1 = [0.1, 0.3, 0.1, 0.6, 0.4, 0.6, 0.5, 0.9, 0.4, 0.7]
x2 = [0.1, 0.4, 0.5, 0.9, 0.2, 0.3, 0.6, 0.2, 0.4, 0.6]
y = [ones(1,5) zeros(1,5); zeros(1,5) ones(1,5)]

## Activate Function
function activate(X, W, b)
# x is the input vector,
# y is the output vector
# W contains the weights
# b contains the shifts
# returns the output of the neuron, i.e the activation function
    return [1. / 1+exp(res) for res in -(W*X+b)]
end

# cost function
function cost(W2, W3, W4, b2, b3, b4)
    costvec = zeros(10,1)
    for i = 1:10
        x = [x1[i]; x2[i]]
        a2 = activate(x, W2, b2)
        a3 = activate(a2, W3, b3)
        a4 = activate(a3, W4, b4)
        costvec[i] = norm(y[:,i] - a4, 2)
    end
    return norm(costvec,2)^2
end

### Initialise weights and biases
rng=5000
Random.seed!(rng)

W2 = 0.5*randn(2,2); W3 = 0.5*randn(3,2); W4 = 0.5*randn(2,3)
b2 = 0.5*randn(2,1); b3 = 0.5*randn(3,1); b4 = 0.5*randn(2,1)

### Forward & Backward propagation
eta = 0.05      # learning rate
Niter = 100 #1*10^6  # Number of SG iterations
savecost = zeros(Niter, 1) # value of cost function at each iteration
for counter = 1:Niter
     k = rand(1:10)  # choose a training point at random
     x = [x1[k]; x2[k]]
     ## Forward pass
     a2 = activate(x,W2,b2)
     a3 = activate(a2,W3,b3)
     a4 = activate(a3,W4,b4)
     # ## Backward pass
     delta4 = a4.*(ones(size(a4)[1],1)-a4).*(a4-y[:,k])
     delta3 = a3.*(ones(size(a3)[1],1)-a3).*(W4'*delta4)
     delta2 = a2.*(ones(size(a2)[1],1)-a2).*(W3'*delta3)
     # # Gradient step
     W2 .= W2 - eta*delta2*x'
     W3 .= W3 - eta*delta3*a2'
     W4 .= W4 - eta*delta4*a3'
     b2 .= b2 - eta*delta2
     b3 .= b3 - eta*delta3
     b4 .= b4 - eta*delta4
     # # Monitor progress
     newcost = cost(W2,W3,W4,b2,b3,b4)
     savecost[counter] = newcost
end

### Plots
# Cost Function per Iteration
using LaTeXStrings, Plots
plot(1:Niter, savecost,
        color=:blue,
        title="Cost Function",
        xlabel="Iteration number",
        ylabel="Value of Cost Function",
        label="Cost",
        legend=:topright,
        framestyle = :box)
