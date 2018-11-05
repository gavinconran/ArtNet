import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
mpl.style.use('classic')

# Frequency Response: Magnitude over Resistor
# Transfer Function
def FR_Mag_Res(w, R, C, L):
    '''
    Vi is the input voltage in volts
    w is angular frequency (or speed) of sneaky input voltage (s = jw) in radians/second
    R is the Resistance in ohms
    C is the Capicatance in farads
    L is the impedance in henrys    
    returns Frequency Response: Magnitude over Resistor
    '''
    numerator = w * R * C
    denominator = np.sqrt( (1 - w**2 * L * C)**2 + (w*R*C)**2) 
    return np.abs(numerator / denominator)

# Frequency Response: Magnitude over Capacitor
# Transfer Function
# see 06_TD_and_FD_Analysis_RLC_Series_Circuit.txt for equation derivations
def FR_Mag_Cap(w0, w, R, C, L):
    '''
    Vi is the input voltage in volts
    w is angular frequency (or speed) of sneaky input voltage (s = jw) in radians/second
    w0 is the resonant frequency in radians/second
    returns Frequency Response: Magnitude over Capacitor
    '''
    numerator = w0**2
    denominator = np.sqrt( (w**2 + w0**2)**2 + ((R/L)*w)**2 ) 
    return numerator / denominator

# Circuit Parameters
C = 10*10**(-12) # farads
L = 10*10**-6 # henrys
VI = 5.0 # input voltage in volts
w0 = 1.0 / np.sqrt(L*C) # undamped natural (resonant) angular frequency (or speed) of circuit (= 1/sqrt(LC))
f0 = w0 / (2.0*np.pi) # resonant frequency in Hz
t = np.arange(0, 0.0000003, 0.000000001) # time range in seconds

## Three scenaios:  
# 1) Over-damped: a > w0 or Q < 0.5 where Q = w0/(2*a) and 2a = R/L  
# 2) Under-damped: a < w0 or Q > 0.5  
# 3) Critically-damped: a = w0 or Q = 0.5

# ### Over-damped: a > w0 or Q < 0.5 where Q = w0/(2*a) and 2a = R/L 
R_OD = 2500.0 # ohms
a_OD = R_OD / (2*L) # does alpha have a name? damping constant
cutOff_OD = 1/(R_OD*C) # break frequency in radians/sec
w_OD = np.arange(0, 10000 * cutOff_OD, 100000) # range of angular frequencies (speeds) for over-damped
Q_OD = w0 / (2*a_OD) # Quality Factor
print("Q Over-damped: " + str(Q_OD) )

#compute frequency response across resistor over frequency
FR_Res_OD =  FR_Mag_Res(w_OD, R_OD, C, L)

plt.figure(1)
plt.title("Frequency Response of Resistor"
          "\n"
          "Over-damped")
plt.ylabel("Magnitude (Log Scale)")
plt.xlabel("Frequency")
plt.loglog(w_OD, FR_Res_OD)
plt.ylim(-10000, 2)
plt.show()

### Under-damped: a < w0 or Q > 0.5  
R_UD = 500.0 # ohms
a_UD = R_UD / (2*L) # does alpha have a name? damping constant
cutOff_UD = 1/(R_UD*C) # break (angular) frequency
w_UD = np.arange(0, 10000 * cutOff_UD, 100000) # range of angular frequencies (speeds) for under-damped
Q_UD = w0 / (2*a_UD) # Quality Factor
print("Q Under-damped: " + str(Q_UD) )
#compute frequency response across resistor over frequency
FR_Res_UD =  FR_Mag_Res(w_UD, R_UD, C, L)

plt.figure(2)
plt.title("Frequency Response of Resistor"
          "\n"
          "Under-damped")
plt.ylabel("Magnitude (Log Scale)")
plt.xlabel("Frequency")
plt.loglog(w_UD, FR_Res_UD)
plt.ylim(-10000, 2)
plt.show()

### Critically-damped: a = w0 or Q = 0.5
R_CD = 2000.0 # ohms
a_CD = R_CD / (2*L) # does alpha have a name? damping constant
cutOff_CD = 1/(R_CD*C) # break frequency
w_CD = np.arange(0, 10000 * cutOff_CD, 100000) # range of angular frequencies (speeds) for critically-damped
Q_CD = w0 / (2*a_CD) # Quality Factor
print("Q Critically-damped: " + str(Q_CD) )

#compute frequency response across resistor over frequency
FR_Res_CD =  FR_Mag_Res(w_CD, R_CD, C, L)

plt.figure(3)
plt.title("Frequency Response of Resistor"
          "\n"
          "Critically-damped")
plt.ylabel("Magnitude (Log Scale)")
plt.xlabel("Frequency")
plt.loglog(w_CD, FR_Res_CD)
plt.ylim(-10000, 2)
plt.show()

  



