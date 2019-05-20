# CMB spectrum (from COBE)
# data from https://lambda.gsfc.nasa.gov/data/cobe/firas/monopole_spec/firas_monopole_spec_v1.txt

## construct frequency and intensity data lists from COBE dataset
ν_list = []; u_list = [];
open("./data/firas_monopole_soec_v1_raw.txt") do f
    for i in enumerate(eachline(f))
      # tokenise and parse line i
      floatlist = map(x->parse(Float64,x),split(i[2]))
      append!(ν_list, floatlist[1])
      append!(u_list, floatlist[2])
    end
end

## Planck's law of Black body spectrum
# constants

# formula from https://ncc.nesdis.noaa.gov/data/planck.html
function planck(ν, T)
        # constants
        c1 = 100*1.202^(-5)         #Units: mW/m2-sr-m-1 -> FUDGED
        c2 = 1.4387752              #Units K cm

        factor1 = c1*ν^3
        factor2 = 1 / (exp(c2*ν/T)-1)
        return  factor1 * factor2
end

# compute intensities
T = 2.725  #Black body Temperature: Units: kelvin, K
u_planck_list = [planck(ν, T) for ν in ν_list]


## Plots
## Combined Plot of CMB spectrum (Cobe data and black bodt spectrum)
using LaTeXStrings, Plots
# Observed spectrum
plot(ν_list, u_list,
        seriestype=:scatter,
        color=:red,
        title="Cosmic Microwave Background Spectrum (from COBE)",
        xlabel=L"Frequency [1/cm]",
        ylabel=L"Intensity [MJy/sr]",
        label="COBE/firas Data",
        xlims = (2,22),
        ylims = (0,400),
        framestyle = :box)

# Black body spectrum
plot!(ν_list, u_planck_list,
        color=:green,
        label="Black Body Spectrum")

#####################################################################
# Code used to compute intensities using Guth's Planck's formula    #
# Not working due to a problem of correctly computing the constants #
#####################################################################
# ## constants
# # constants from Ryden p. 239
# ħ = 6.582*10^(-16)          # reduced Planck const Units eV.s
# #ħ = 1.055*10^(-34)          # reduced Planck const Units J.s
# h = ħ * 2*π                 #Planck const: Units eV.s
# c = 100*2.998*10^(8)            #speed of light: Units m.s-1
# #k = 1.381*10^(-23)          # Boltzmann's const: J K-1
# k = 8.617*10^(-5)          # Boltzmann's const: eV K-1

# # Formula from problem set 7, problem 4
# function planck_guth(ν, T)
#        c1 = 100*1.202^(-5)  #2*h /c^2
#        factor1 = c1*ν^3
#
#        c2 = h/(k*T)
#        c2=1.4387752/T
#        factor2 = 1/(exp(c2*ν)-1)
#
#        return  factor1 * factor2
# end
#
# u_planck_list_guth = [planck_guth(ν, T) for ν in ν_list]
#
# # Black body spectrum
# plot!(ν_list, u_planck_list_guth,
#        color=:blue,
#        label="Black Body Spectrum")
