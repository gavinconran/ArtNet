### compute coordinate time for particle to reach horizon ###

# set constants
G = 1 # Gravational Constant #6.674^(-11) # units: m3⋅kg−1⋅s−2
M = 1 # mass of Black Hole #units
r0 = 3 # distance from symmetry center
rf = 1 # final distance
c = 1 # spped of light # 299792458 # units m⋅s-1
Rs = 2*G*M/c^2 # Schwarzschild Radius of horizon


# compute coordinate time function
function t(rf)
        return -(Rs / c) * log(rf - Rs)
end

## computer coordinate time, t
# create a list of ditances reflecting trajectory along radial
rf_list_t = range(r0, stop=Rs, length = 10000)
# compute corresponding time
t_list = [t(rf) for rf in rf_list_t]

## Plots
using LaTeXStrings, Plots

# proper time vs rf
plot(rf_list_t, t_list,
        color=:blue,
        title="Time to reach Horizon (Coordinate Time)",
        xlabel=L"rf",
        ylabel=L"t",
        xflip=true,
        #xlims = (0,10),
        ylims = (0,15),
        label=L"t(rf)",
        legend=:topleft,
        framestyle = :box)

plot!([Rs], seriestype = :vline,
        color=:black,
        linestyle = :dash,
        label = L"Rs")
