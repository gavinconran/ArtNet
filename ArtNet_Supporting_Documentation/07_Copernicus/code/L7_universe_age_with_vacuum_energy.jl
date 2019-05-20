### Age of a universe with only matter and vacuum energy

## Experimental Estimates (Panck 2018)
H0 = 67.7 # Hubble constant present: Units: km.s-1 . Mpc-1 (Planck 2018 value)
age0 = 13.79 # Age of universe : Units: billion years
Ω0 = 0.311 # latest Planck estimate

## Age piecewise function
function ageUniverse(H, Ωm)
    if Ω==1
        return  1000 * (2/ (3*H))
    else
        return 1000*(2/(3*H)) *
                (log(sqrt(1-Ωm)+1)-log(sqrt(Ωm)))/sqrt(1-Ωm)
    end

end

## compute age
H_list = range(30,stop=100,length=100)

Ω = 0.1
age_01 = [ageUniverse(H, Ω) for H in H_list]

Ω = 0.2
age_02 = [ageUniverse(H, Ω) for H in H_list]

Ω = 0.3 # latest Planck estimate
age_03 = [ageUniverse(H, Ω) for H in H_list]

Ω = 0.5
age_05 = [ageUniverse(H, Ω) for H in H_list]

Ω = 1.0
age_1 = [ageUniverse(H, Ω) for H in H_list]


## Plots
using LaTeXStrings, Plots

# Age of universe
plot(H_list, age_01,
        color=:red,
        title="Age of Universe (with Vacuum Energy)",
        xlabel="H (Kilometers per Second per Megaparsec)",
        ylabel="Age (Billions of Years)",
        xlims = (30,100),
        ylims = (5,25),
        label=L"\Omega m = 0.1",
        framestyle = :box)

plot!(H_list, age_02,
        color=:orange,
        label=L"\Omega m = 0.2")

plot!(H_list, age_03,
        color=:black,
        label=L"\Omega m = 0.3")

plot!(H_list, age_05,
        color=:cyan,
        label=L"\Omega m = 0.5")

plot!(H_list, age_1,
        color=:blue,
        label=L"\Omega m = 1.0")

plot!([H0], [age0],
        seriestype=:scatter,
        color=:red,
        markersize=5,
        label="Planck 2018")
