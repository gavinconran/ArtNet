### Age of a matter-dominated universe

## Age piecewise function
function ageUniverse(Ω)
    if Ω == 0.0
        return 1.0
    elseif Ω < 1.0
        return abs((Ω/(2*(1-Ω)^(3/2))) * ( (2*sqrt(1-Ω)/Ω) - asinh(2*sqrt(1-Ω)/Ω) ))
    elseif Ω == 1.0
        return 2/3
    else # Ω > 1
        return abs( Ω/(2*(Ω-1)^(3/2)) * ( asin(2*sqrt(Ω-1)/Ω) - 2*sqrt(Ω-1)/Ω ) )
    end
end

## compute age
Ω_list = range(0,stop=2,length=100)
age = [ageUniverse(Ω) for Ω in Ω_list]

## Plots
using LaTeXStrings, Plots

# Age of universe
plot(Ω_list, age,
        color=:blue,
        title="Age of Matter-Dominated Universe",
        xlabel=L"\Omega",
        ylabel="H t",
        xlims = (0,2),
        ylims = (0,1),
        label="Age",
        framestyle = :box)
plot!([1], seriestype = :vline,
        color=:black,
        linestyle = :dash,
        label = L"\Omega = 1")
plot!([2/3], seriestype = :hline,
        color=:black,
        linestyle = :dot,
        label = L"Ht = 2/3")
