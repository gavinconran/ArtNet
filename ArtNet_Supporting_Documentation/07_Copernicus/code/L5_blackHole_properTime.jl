### compute proper time for particle to reach horizon ###

# set constants
G = 1 # Gravational Constant #6.674^(-11) # units: m3⋅kg−1⋅s−2
M = 1 # mass of Black Hole #units
r0 = 3 # distance from symmetry center
rf = 1 # final distance
c = 1 # spped of light # 299792458 # units m⋅s-1
Rs = 2*G*M/c^2 # Schwarzschild Radius of horizon

# proper time function
function τ(rf)
    first_term = sqrt(r0 / 2*G*M)
    second_term = r0 * atan(sqrt((r0-rf)/rf) + sqrt(rf*(r0-rf)))
    return first_term * second_term
end

# computer proper time, τ
rf_list_τ = range(r0, stop=Rs, length = 100)
τ_list = [τ(rf) for rf in rf_list_τ]


## Plots
using LaTeXStrings, Plots

# proper time vs rf
plot(rf_list_τ, τ_list,
        color=:blue,
        title="Time to reach Horizon (Proper Time)",
        xlabel=L"rf",
        ylabel=L"\tau",
        xflip=true,
        ylims = (0,5),
        label=L"\tau (rf)",
        legend=:topleft,
        framestyle = :box)

plot!([Rs], seriestype = :vline,
        color=:black,
        linestyle = :dash,
        label = L"Rs")
