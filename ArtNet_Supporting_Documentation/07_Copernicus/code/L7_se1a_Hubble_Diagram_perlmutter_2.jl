# se1a (from Perlmutter et al)
# data from http://arxiv.org/abs/astro-ph/9812133

### observation
## construct data lists from dataset
using CSV
file_calan = "./data/1988_se1a_perlmutter_calan.csv"
table_calan = CSV.read(file_calan; header=false, delim=',')
se1a_list_calan = table_calan[1];
z_list_calan = table_calan[2];
z_uncertain_list_calan = table_calan[3];
mag_list_calan = table_calan[4];
mag_uncertain_list_calan = table_calan[5];

file_super = "./data/1988_se1a_perlmutter_supernova.csv"
table_super = CSV.read(file_super; header=false, delim=',')
se1a_list_super = table_super[1];
z_list_super = table_super[2];
z_uncertain_list_super = table_super[3];
mag_list_super = table_super[4];
mag_uncertain_list_super = table_super[5];


### Theory
## Experimental Estimates (Panck 2018)
H0 = 67.7 # Hubble constant present: Units: km.s-1 . Mpc-1 (Planck 2018 value)

## using formua from ryden p.121
function magnitude(z, q0)
        return 23.8 - 5*log10(H0/70) + 5*log10(z) + 1.086(1-q0)*z
end

## compute magnitude lists (from theory)
z_list = range(0.01, stop=2, length=100)
#from ryden, flat, matter dominated universe
Ωm = 1; ΩΛ=0;
q0 = Ωm/2 - ΩΛ # from liddle
model_list_CDM = [magnitude(z,q0) for z in z_list]

# from ryden: negatively curved, matter dominated universe
Ωm = 0.2; ΩΛ=0; #flat, matter dominated universe
q0 = Ωm/2 - ΩΛ # from liddle
model_list_OPEN = [magnitude(z,q0) for z in z_list]

# from ryden: curved, matter and cosmological constant
Ωm = 0.24; ΩΛ=0.76; #flat, matter dominated universe
q0 = Ωm/2 - ΩΛ # from liddle
model_list_ΛCDM = [magnitude(z,q0) for z in z_list]

## compute difference between models
ΛCDM = model_list_ΛCDM - model_list_OPEN
CDM = model_list_CDM - model_list_OPEN
OPEN = model_list_OPEN - model_list_OPEN

## compute difference between open model and data
# compute model using redshit data
# from ryden: negatively curved, matter dominated universe
Ωm = 0.2; ΩΛ=0; #flat, matter dominated universe
q0 = Ωm/2 - ΩΛ # from liddle
model_list_15_from_calan = [magnitude(z,q0) for z in z_list_calan]
model_list_15_from_super = [magnitude(z,q0) for z in z_list_super]

# compute differences between data and model
calan_diff = mag_list_calan - model_list_15_from_calan
super_diff = mag_list_super - model_list_15_from_super


### Plots
using LaTeXStrings, Plots

### Difference
## Models
# ΛCDM
plot(z_list, ΛCDM,
        color=:black,
        xlims = (0.01,1),
        ylims = (-1.5,1.5),
        xaxis=:log,
        title="Hubble Diagram (Supernona Cosmology Project)",
        xlabel=L"z",
        ylabel=L"\Delta (m - M)",
        label=L"\Lambda CDM",
        legend=:bottomleft,
        framestyle = :box)

# Open
plot!(z_list, OPEN,
        color=:black,
        linestyle=:dot,
        label = L"OPEN")

# CDM
plot!(z_list, CDM,
        color=:black,
        linestyle = :dash,
        label=L"CDM")

## Data
## Plot magnitude m vs. redshift z (supernova observed)
# Calan
plot!(z_list_calan, calan_diff,
        xerror=z_uncertain_list_calan,
        yerror=mag_uncertain_list_calan,
        seriestype=:scatter,
        color=:yellow,
        markersize=3,
        label="Calan/Tolodo")

plot!(z_list_super, super_diff,
        xerror=z_uncertain_list_super,
        yerror=mag_uncertain_list_super,
        seriestype=:scatter,
        color=:red,
        markersize=3,
        label="SCG")
