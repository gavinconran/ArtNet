### compute minimum value of radial coordinate, r,  ###
### of circular orbits around black hole ###

# set constants
G = 1 # Gravational Constant #6.674^(-11) # units: m3⋅kg−1⋅s−2
M = 1 # mass of Black Hole #units
r0 = 3 # distance from symmetry center
rf = 1 # final distance
c = 1 # spped of light # 299792458 # units m⋅s-1
Rs = 2*G*M/c^2 # Schwarzschild Radius of horizon

upper_bound = 3/2 * Rs
lower_bound = 3 * Rs

# compute cordinates
ϕ_list = range(0, stop= 2π, length = 100)

x_unit = [cos(ϕ) for ϕ in ϕ_list]
y_unit = [sin(ϕ) for ϕ in ϕ_list]

# upper bound
x_coord_upper = x_unit * 3*Rs
y_coord_upper = y_unit * 3*Rs

# lower bound
x_coord_lower = x_unit * (3/2)*Rs
y_coord_lower = y_unit * (3/2)*Rs

# horixon
x_coord_horizon = x_unit * Rs
y_coord_horizon = y_unit * Rs

## Plots
using LaTeXStrings, Plots

# upper, lower circular orbits and event Horizon
plot(x_coord_upper, y_coord_upper,
        color=:yellow,
        title="Circular Orbits Around Black Hole",
        xlabel=L"x",
        ylabel=L"y",
        xlims = (-10,10),
        ylims = (-10,10),
        label=L"orbit",
        fill=(0, :yellow),
        fillalpha=0.5,
        legend=:topleft,
        framestyle = :box)

plot!(x_coord_lower, y_coord_lower,
        color=:gray,
        fill=(0, :gray),
        fillalpha=0.5,
        label=L"shadow")

plot!(x_coord_horizon, y_coord_horizon,
        color=:black,
        fill=(0, :black),
        fillalpha=0.5,
        label=L"horizon")
