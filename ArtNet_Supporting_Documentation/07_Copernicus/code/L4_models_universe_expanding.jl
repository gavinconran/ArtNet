### Evolution of Matter-Dominated Universe ###

## Expansion functions
# Flat universe
function flatUniverse(con, t_array)
    return  [con * t^(2/3) for t in t_array] # a(t) proportional to t^(2/3)
end

# open universe
function openUniverse(α, Θ)
    t_open = [α*(sinh(th) - th) for th in θ]
    a_open = [α*(cosh(th) - 1) for th in Θ]
    return t_open, a_open
end

# closed universe
function closedUniverse(α, θ)
    t_closed = [α*(th - sin(th)) for th in θ]
    a_closed = [α*(1 - cos(th)) for th in θ]
    return t_closed, a_closed
end

## compute expansions
# Flat universe
con = 1.65 # arbitrary constant
t_flat= range(0,stop=4,length=100) # time
expansion_flat = flatUniverse(con, t_flat) # expansion ∝ t ^(2/3)

# closed universe
θ = range(0, stop=2*π, length=100) # grid values of θ
α = 1 # α parameter
t_closed, expansion_closed = closedUniverse(α, θ)
# open universe
t_open, expansion_open = openUniverse(α, θ)


## Plots
## Combined Plot of Expansion of universe
using LaTeXStrings, Plots
# Flat universe
plot(t_flat,expansion_flat,
        color=:red,
        title="Evolution of Matter-Dominated Universe",
        xlabel=L"ct / \alpha",
        ylabel=L"a / \alpha \sqrt(∣ k ∣)",
        label="Flat",
        xlims = (0,6.5),
        ylims = (0,4),
        framestyle = :box)
# open universe
plot!(t_open,expansion_open,
        color=:green,
        label="Open")
# closed univserse
plot!(t_closed,expansion_closed,
        color=:blue,
        label="Closed")
