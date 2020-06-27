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

    # new code
    vw = []
    for i in range(1, items+1):
        line = lines[i]
        parts = line.split()
        values.append(int(parts[0]))
        weights.append(int(parts[1]))
        vw.append([int(parts[0]), int(parts[1])])


    # Build Binary Tree
    treeTest = buildDTree([], vw)
    # Run DFSDTree
    foobar = DFSDTree(treeTest, sumValues, WeightsBelowCap, capacity)
    result = foobar.getValue()

            
    # Ctraete the correct output for printing
    resultvw = [0] * items
    value = 0
    for i in range(0, len(result)):
        resultvw[vw.index(result[i])] = 1
        value += result[i][0]

    # prepare the solution in the specified output format
    outputData = str(value) + ' ' + str(0) + '\n'
    #outputData += ' '.join(map(str, taken))

    # new code
    outputData += ' '.join(map(str, resultvw))
    return outputData


import sys

if __name__ == '__main__':
    print ''
    print 'DFS decision tree'
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

