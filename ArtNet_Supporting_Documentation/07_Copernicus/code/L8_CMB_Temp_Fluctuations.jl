# CMB Temperature Fluctuations

### theoretical results from CLASS
## construct data lists from dataset
using CSV
file = "./data/base_2018_plikHM_TTTEEE_lowl_lowE_lensing_cl_raw.dat"

table = CSV.read(file,header=false, delim=' ', ignorerepeated=true)
l_list = table[2];
TT_list = table[3];

### Plots
using LaTeXStrings, Plots
plot(l_list, TT_list,
        color=:blue,
        xlims = (10,2500),
        #ylims = (-1.5,1.5),
        xaxis=:log,
        title="Planck 2018 Temperature Power Spectrum",
        xlabel="multipole, l",
        ylabel="Temperature Fluctuations",
        label="Inflation with dark energy",
        legend=:topleft,
        framestyle = :box)
