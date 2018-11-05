#!/usr/bin/python


# The following functions are copyright (c) 2013-2014, Thomas A. Baran and Massachusetts Institute of Technology
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met: 
#
# 1. Redistributions of source code must retain the above copyright notice, this
#    list of conditions and the following disclaimer. 
# 2. Redistributions in binary form must reproduce the above copyright notice,
#    this list of conditions and the following disclaimer in the documentation
#    and/or other materials provided with the distribution. 
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
# ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
# ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
# ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


# Note: the functions remezord, remlplen_herrmann, remlplen_kaiser, and
# remlplen_ichige below are Copyright (c) 2009-2014, Lev Givon.  The associated
# license is reproduced inline below.


import numpy as np
import scipy.signal as signal


def bilinearZPK(z,p,k,fs,cplxGainEps=1e-12):
    zd = []
    pd = []
    kd = k

    # Append appropriate poles or zeros at z=-1,
    # corresponding to poles or zeros at s=infinity.
    nz = len(z)
    np = len(p)
    if np>nz:
        nM1Zeros = np-nz
        zd = zd + [-1.0]*nM1Zeros
    if nz>np:
        nM1Poles = nz-np    
        pd = pd + [-1.0]*nM1Poles

    # Append transformed zeros
    for thisZero in z:
        kd = kd*(2.0*fs - thisZero)
        zd = zd + [(2.0*fs + thisZero)/(2.0*fs - thisZero)]

    # Append transformed poles
    for thisPole in p:
        kd = kd/(2.0*fs - thisPole)
        pd = pd + [(2.0*fs + thisPole)/(2.0*fs - thisPole)]

    if abs(kd.imag)/abs(kd)<cplxGainEps:
        kd = kd.real

    return (zd, pd, kd)


def ratFilter(z,p,x):
    y = x
    zLen = len(z)
    pLen = len(p)
    zIdx = 0
    pIdx = 0
    for index in range(0,max(zLen,pLen)):

        if zIdx<zLen:
            thisb = np.poly([ z[zIdx] ])
            zIdx += 1
        else:
            thisb = [ 1 ]

        if pIdx<pLen:
            thisa = np.poly([ p[pIdx] ])
            pIdx += 1
        else:
            thisa = [ 1 ]

        y = signal.lfilter(thisb, thisa, y)

    return y.real


def ratMagResponse(z,p,w):
    H = np.ones_like(w)
    for thisZero in z:
        r = np.abs(thisZero)
        ang = np.angle(thisZero)
        tmp = 1 + r**2 - 2*r*np.cos(w-ang)
        tmp = np.sqrt(tmp)
        H = H * tmp
    for thisPole in p:
        r = np.abs(thisPole)
        ang = np.angle(thisPole)
        tmp = 1 + r**2 - 2*r*np.cos(w-ang)
        tmp = np.sqrt(tmp)
        H = H / tmp
    return H


def ratGroupDelay(z,p,w):
    grd = np.zeros_like(w)
    for thisZero in z:
        r = np.abs(thisZero)
        ang = np.angle(thisZero)
        tmp = r**2 - r*np.cos(w-ang)
        tmp = tmp / (1 + r**2 - 2*r*np.cos(w-ang))
        grd = grd + tmp
    for thisPole in p:
        r = np.abs(thisPole)
        ang = np.angle(thisPole)
        tmp = r**2 - r*np.cos(w-ang)
        tmp = tmp / (1 + r**2 - 2*r*np.cos(w-ang))
        grd = grd - tmp
    return grd



# The following copyright and license apply excluslively to the remainder of the
# functions in this file, which are reproduced from a part of the Columbia
# University Bionet toolkit.
# https://github.com/bionet/ted.python/blob/master/bionet/utils/signal_extras.py
# Minor modifications to the namespacing of these functions have been made
# by Tom.
#
# The following functions are copyright (c) 2009-2014, Lev Givon.
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#
# * Redistributions of source code must retain the above copyright
#   notice, this list of conditions and the following disclaimer.
# * Redistributions in binary form must reproduce the above
#   copyright notice, this list of conditions and the following
#   disclaimer in the documentation and/or other materials provided
#   with the distribution.
# * Neither the name of Lev Givon nor the names of any
#   contributors may be used to endorse or promote products derived
#   from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

def remlplen_herrmann(fp, fs, dp, ds):
    """Determine the length of the low pass filter with passband frequency
    fp, stopband frequency fs, passband ripple dp, and stopband ripple ds.
    fp and fs must be normalized with respect to the sampling frequency.
    Note that the filter order is one less than the filter length.

    References
    ----------
    O. Herrmann, L.R. Raviner, and D.S.K. Chan, Practical Design Rules for
    Optimum Finite Impulse Response Low-Pass Digital Filters, Bell Syst. Tech.
    Jour., 52(6):769-799, Jul./Aug. 1973.

    """

    dF = fs-fp
    a = [5.309e-3,7.114e-2,-4.761e-1,-2.66e-3,-5.941e-1,-4.278e-1]
    b = [11.01217, 0.51244]
    Dinf = np.log10(ds)*(a[0]*np.log10(dp)**2+a[1]*np.log10(dp)+a[2])+ \
           a[3]*np.log10(dp)**2+a[4]*np.log10(dp)+a[5]
    f = b[0]+b[1]*(np.log10(dp)-np.log10(ds))
    N1 = Dinf/dF-f*dF+1

    return int(oddround(N1))

def remlplen_kaiser(fp, fs, dp, ds):
    """Determine the length of the low pass filter with passband frequency
    fp, stopband frequency fs, passband ripple dp, and stopband ripple ds.
    fp and fs must be normalized with respect to the sampling frequency.
    Note that the filter order is one less than the filter length.

    References
    ----------
    J.F. Kaiser, Nonrecursive Digital Filter Design Using I_0-sinh Window
    function, Proc. IEEE Int. Symp. Circuits and Systems, 20-23, April 1974.

    """

    dF = fs-fp
    N2 = (-20*np.log10(sqrt(dp*ds))-13.0)/(14.6*dF)+1.0

    return int(oddceil(N2))

def remlplen_ichige(fp, fs, dp, ds):
    """Determine the length of the low pass filter with passband frequency
    fp, stopband frequency fs, passband ripple dp, and stopband ripple ds.
    fp and fs must be normalized with respect to the sampling frequency.
    Note that the filter order is one less than the filter length.

    References
    ----------
    K. Ichige, M. Iwaki, and R. Ishii, Accurate Estimation of Minimum
    Filter Length for Optimum FIR Digital Filters, IEEE Transactions on
    Circuits and Systems, 47(10):1008-1017, October 2000.

    """
    
    dF = fs-fp
    v = lambda dF,dp:2.325*((-np.log10(dp))**-0.445)*dF**(-1.39)
    g = lambda fp,dF,d:(2.0/np.pi)*np.arctan(v(dF,dp)*(1.0/fp-1.0/(0.5-dF)))
    h = lambda fp,dF,c:(2.0/np.pi)*np.arctan((c/dF)*(1.0/fp-1.0/(0.5-dF)))
    Nc = np.ceil(1.0+(1.101/dF)*(-np.log10(2.0*dp))**1.1)
    Nm = (0.52/dF)*np.log10(dp/ds)*(-np.log10(dp))**0.17
    N3 = np.ceil(Nc*(g(fp,dF,dp)+g(0.5-dF-fp,dF,dp)+1.0)/3.0)
    DN = np.ceil(Nm*(h(fp,dF,1.1)-(h(0.5-dF-fp,dF,0.29)-1.0)/2.0))
    N4 = N3+DN

    return int(N4)

def remezord(freqs, amps, rips, Hz=1, alg='ichige'):
    """Calculate the parameters required by the Remez exchange algorithm to
    construct a finite impulse response (FIR) filter that approximately
    meets the specified design.

    Parameters
    ----------
    freqs : array_like of floats
        A monotonic sequence of band edges specified in Hertz. All
        elements must be non-negative and less than 1/2 the
        sampling frequency as given by the Hz parameter.
    amps : array_like of floats
        A sequence containing the amplitudes of the signal to be
        filtered over the various bands.
    rips : array_like of floats
        A sequence specifying the maximum ripples of each band.
    alg : {'herrmann', 'kaiser', 'ichige'}
        Filter length approximation algorithm.

    Returns
    -------
    numtaps : int
        Desired number of filter taps.
    bands : ndarray of floats
        A monotonic sequence containing the band edges.
    amps : ndarray of floats
        Desired gain for each band region.
    weights : ndarray of floats
        Filter design weights.

    See Also
    --------
    scipy.signal.remez

    """

    # Make sure the parameters are floating point numpy arrays:
    freqs = np.asarray(freqs, 'd')
    amps = np.asarray(amps, 'd')
    rips = np.asarray(rips, 'd')

    # Scale ripples with respect to band amplitudes:
    rips /= (amps+(amps==0.0))

    # Normalize input frequencies with respect to sampling frequency:
    freqs /= Hz

    # Select filter length approximation algorithm:
    if alg == 'herrmann':
        remlplen = remlplen_herrmann
    elif alg == 'kaiser':
        remlplen = remlplen_kaiser
    elif alg == 'ichige':
        remlplen = remlplen_ichige
    else:
        raise ValueError('Unknown filter length approximation algorithm.')

    # Validate inputs:
    if any(freqs > 0.5):
        raise ValueError('Frequency band edges must not exceed the Nyquist frequency.')
    if any(freqs < 0.0):
        raise ValueError('Frequency band edges must be nonnegative.')
    if any(rips < 0.0):
        raise ValueError('Ripples must be nonnegative.')
    if len(amps) != len(rips):
        raise ValueError('Number of amplitudes must equal number of ripples.')
    if len(freqs) != 2*(len(amps)-1):
        raise ValueError('Number of band edges must equal 2*((number of amplitudes)-1)')

    # Find the longest filter length needed to implement any of the
    # low-pass or high-pass filters with the specified edges:
    f1 = freqs[0:-1:2]
    f2 = freqs[1::2]
    L = 0
    for i in range(len(amps)-1):
        L = max((L,
                 remlplen(f1[i], f2[i], rips[i], rips[i+1]),
                 remlplen(0.5-f2[i], 0.5-f1[i], rips[i+1], rips[i])))

    # Cap the sequence of band edges with the limits of the digital frequency
    # range:
    bands = np.hstack((0.0, freqs, 0.5))

    # The filter design weights correspond to the ratios between the maximum
    # ripple and all of the other ripples:
    weight = max(rips)/rips

    return [L, bands, amps, weight]
