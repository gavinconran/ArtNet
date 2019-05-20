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

# from ryden: curved, matter and cosmological constant
Ωm = 0.24; ΩΛ=0.76; #flat, matter dominated universe
q0 = Ωm/2 - ΩΛ # from liddle
model_list_neg55 = [magnitude(z,q0) for z in z_list]

# from ryden: negatively curved, matter dominated universe
Ωm = 0.2; ΩΛ=0; #flat, matter dominated universe
q0 = Ωm/2 - ΩΛ # from liddle
model_list_15 = [magnitude(z,q0) for z in z_list]

#from ryden, flat, matter dominated universe
Ωm = 1; ΩΛ=0;
q0 = Ωm/2 - ΩΛ # from liddle
q0 = 0.5
model_list_50 = [magnitude(z,q0) for z in z_list]


### Plots
using LaTeXStrings, Plots
## Plot magnitude m vs. redshift z (calan observed)
plot(z_list_calan, mag_list_calan,
        xerror=z_uncertain_list_calan,
        yerror=mag_uncertain_list_calan,
        seriestype=:scatter,
        color=:yellow,
        title="Hubble Diagram (Supernova Cosmology Project)",
        xlabel=L"z",
        ylabel=L"m - M",
        xaxis=:log,
        xlims = (0.01,1),
        ylims = (14,26),
        markersize=3,
        label="Calan/Tolodo",
        legend=:topleft,
        framestyle = :box)

## Plot magnitude m vs. redshift z (supernova observed)
plot!(z_list_super, mag_list_super,
        xerror=z_uncertain_list_super,
        yerror=mag_uncertain_list_super,
        seriestype=:scatter,
        color=:red,
        markersize=3,
        label="Supernova Cosmology Project")

## Plot magnitude m vs. redshift z (theory)
plot!(z_list, model_list_neg55,
        color=:black,
        label=label=L"\Omega_m=0.24, \Omega_\Lambda = 0.76")

plot!(z_list, model_list_15,
        linestyle = :dash,
        color=:black,
        label=label=L"\Omega_m=0.2, \Omega_\Lambda = 0")

plot!(z_list, model_list_50,
        linestyle = :dot,
        color=:black,
        label=L"\Omega_m=1, \Omega_\Lambda = 0")
