import numpy as np
import math
import matplotlib.pyplot as plt
import matplotlib as mpl
from scipy import signal
mpl.style.use('classic')


# voltage across the capacitor
def voltage_RLC_Cap(VI, omega0, omegad, alpha, t):
    '''
    VI is the input voltage of rising step in volts
    t is time in seconds 
    omega0 is the undamped natural frequency
    omegad is the damped natural frequency (omegad < omega0)
    returns the voltage at angle, rad in volts
    '''
    phi = np.arctan(alpha/omegad) # phase
    return VI - VI * (omega0 / omegad) * np.exp(-alpha*t)  * np.cos(omegad*t - phi) 

## OVER-DAMPED, Driven Second Order RLC Series Circuit
### Real Roots of Characteristic Equation (alpha > omega0)
### No Sinusoids
R = 1900.0 # ohms
C = 10*10**(-12) # farads
L = 10*10**-6 # henrys
VI = 5.0 # input voltage in volts

omega0 = 1.0 / np.sqrt(L*C) # undamped natural (resonant) frequency of circuit (= 1/sqrt(LC))
alpha = R / (2*L) # does alpha have a name? damping constant

omegad = np.sqrt(omega0**2 - alpha**2)  # damped natural frequency
# omegad < omega0

t = np.arange(0, 0.0000003, 0.000000001)
voltage_OD = voltage_RLC_Cap(VI, omega0, omegad, alpha, t)
#decay = 2* VI + np.exp(-alpha*t)

plt.figure(1)
plt.title("Voltage over Capacitor"
         "\n"
         "Over damped")
plt.ylabel("Voltage (volts)")
plt.xlabel("Time")
plt.plot(t, voltage_OD, 'b')
plt.show()

## UNDER-DAMPED, Driven Second Order RLC Series Circuit
### Complex Roots of Characteristic Equation (alpha < omega0)
### Decay Rate is -alpha
R = 300.0 # ohms
C = 10*10**(-12) # farads
L = 10*10**-6 # henrys
VI = 5.0 # input voltage in volts

omega0 = 1.0 / np.sqrt(L*C) # undamped natural (resonant) frequency of circuit (= 1/sqrt(LC))
alpha = R / (2*L) # does alpha have a name? damping constant

omegad = np.sqrt(omega0**2 - alpha**2)  # damped natural frequency
# omegad < omega0

t = np.arange(0, 0.0000003, 0.000000001)
voltage_UD = voltage_RLC_Cap(VI, omega0, omegad, alpha, t)
#decay = 2* VI + np.exp(-alpha*t)
plt.figure(2)
plt.title("Voltage over Capacitor"
         "\n"
         "Under damped")
plt.ylabel("Voltage (volts)")
plt.xlabel("Time")
plt.plot(t, voltage_UD)
plt.show()

## CRITICALLY-DAMPED, Driven Second Order RLC Series Circuit
### Repeated Real Root of Characteristic Equation (alpha = omega0)
### Decay Rate governed by s

R = 1000.0 # ohms
C = 10*10**(-12) #-12 # farads
L = 10*10**-6 # henrys
vi = 3.0 #5.0 # input voltage in volts

w0 = 1.0 / np.sqrt(L*C) # undamped natural (resonant) frequency of circuit (= 1/sqrt(LC))
a = R / (2*L) # does alpha have a name? Damping Factor

wd = np.sqrt(w0**2 - a**2)  # damped natural frequency
# omegad < omega0

t = np.arange(0, 0.0000003, 0.000000001)
volt_UD = voltage_RLC_Cap(vi, w0, wd, a, t)


Q = w0/(2*a) # Quality Factor: number of cycles of 'ringing'
A = -vi * (w0 / wd)
phi = -np.arctan(a/wd)
period = 2*np.pi / wd # period of oscillation

plt.figure(3)
plt.title("Voltage over Capacitor"
         "\n"
         "Critically damped")
plt.ylabel("Voltage (volts)")
plt.xlabel("Time")
plt.plot(t, volt_UD)
plt.show()











