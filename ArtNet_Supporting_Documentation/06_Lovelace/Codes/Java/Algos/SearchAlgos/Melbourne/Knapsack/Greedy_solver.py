#!/usr/bin/python
# -*- coding: utf-8 -*-

from treeSearch import *
import time

class Timer:    
    def __enter__(self):
        self.start = time.clock()
        return self

    def __exit__(self, *args):
        self.end = time.clock()
        self.interval = self.end - self.start



def solveIt(inputData):
    # Modify this code to run your optimization algorithm
    # parse the input
    lines = inputData.split('\n')
    firstLine = lines[0].split()
    items = int(firstLine[0])
    capacity = int(firstLine[1])
    print "items = ", items
    print "capacity = ", capacity

    values = []
    weights = []

    # a trivial greedy algorithm for filling the knapsack
    # it takes items in-order until the knapsack is full
    value = 0
    weight = 0
    taken = []

    # new code
    vw = []
    for i in range(1, items+1):
        line = lines[i]
        parts = line.split()
        values.append(int(parts[0]))
        weights.append(int(parts[1]))

    for i in range(0, items):
        if weight + weights[i] <= capacity:
            taken.append(1)
            value += values[i]
            weight += weights[i]
        else:
            taken.append(0)

    # prepare the solution in the specified output format
    outputData = str(value) + ' ' + str(0) + '\n'
    outputData += ' '.join(map(str, taken))

    return outputData


import sys

if __name__ == '__main__':
    print ''
    print 'Greedy Algorithm'
    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_4_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_4_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_19_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_19_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_30_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_30_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_40_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_40_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)
    

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_45_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_45_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)
    

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_50_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_50_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_50_1'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_50_1"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_60_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_60_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_100_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_100_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_100_1'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_100_1"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_100_2'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_100_2"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_200_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_200_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_200_1'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_200_1"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_300_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_300_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_400_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_400_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_500_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_500_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_1000_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_1000_0"
    with Timer() as t:
        print solveIt(inputData)    
    print('Request took %.03f sec.\n' % t.interval)

    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
    else:
        fileLocation = 'data/ks_10000_0'
    inputDataFile = open(fileLocation, 'r')
    inputData = ''.join(inputDataFile.readlines())
    inputDataFile.close()
    # timer code
    print "ks_10000_0"
    with Timer() as t:
        print solveIt(inputData)
    print('Request took %.03f sec.\n' % t.interval)

    
    #else:
        #print 'This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/ks_4_0)'

