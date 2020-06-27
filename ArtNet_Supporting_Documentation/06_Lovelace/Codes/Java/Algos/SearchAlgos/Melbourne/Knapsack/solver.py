#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
#import numpy as np
import time

class Timer:    
    def __enter__(self):
        self.start = time.clock()
        return self

    def __exit__(self, *args):
        self.end = time.clock()
        self.interval = self.end - self.start

class data:
    def __init__(self, value, room, estimate):
        self.value = value
        self.room = room
        self.estimate = estimate

    def __str__(self):
      return "value = " + str(self.value) + ", room = " + str(self.room) + ", estimate = " + str(self.estimate) #return as string

    def get_value(self):
        return self.value
    
    def get_room(self):
        return self.room

    def get_estimate(self):
        return self.estimate


class Node:
      def __init__(self,info): #constructor of class
          self.info = info  #information for node
          self.left = None  #left leef
          self.right = None #right leef
          self.level = None #level none defined
          self.path = [] #path

      def __str__(self):
          #return str(self.info) #return as string
          return str(self.path)

      def add_to_path(self, val):
          self.path.append(val)


class searchtree:
      def __init__(self): #constructor of class
          self.root = None

      def create(self,val):  #create binary search tree nodes
          if self.root == None:
             self.root = Node(val)
          else:
             current = self.root
             while 1:
                 if val < current.info:
                   current.add_to_path(1)
                   if current.left:
                      current = current.left
                   else:
                      current.left = Node(val)
                      break;      
                 elif val > current.info:
                    current.add_to_path(0)
                    if current.right:
                       current = current.right
                    else:
                       current.right = Node(val)
                       break;      
                 else:
                    break 

      def bft(self): #Breadth-First Traversal

          self.root.level = 0 
          queue = [self.root]
          out = []
          current_level = self.root.level

          while len(queue) > 0:
             current_node = queue.pop(0)
 
             if current_node.level > current_level:
                current_level += 1
                out.append("\n")

             out.append(str(current_node.info) + " ")

             if current_node.left:

                current_node.left.level = current_level + 1
                queue.append(current_node.left)

             if current_node.right:

                current_node.right.level = current_level + 1
                queue.append(current_node.right)
                      
          print "".join(out) 


      def inorder(self,node):
            
           if node is not None:
              
              self.inorder(node.left)
              #print node.info.value
              print node.path
              self.inorder(node.right)


      def preorder(self,node):
            
           if node is not None:
              
              print node.info
              self.preorder(node.left)
              self.preorder(node.right)


      def postorder(self,node):
            
           if node is not None:
              
              self.postorder(node.left)
              self.postorder(node.right)
              print node.info



def solveIt(inputData):
    # Modify this code to run your optimization algorithm
    # parse the input
    lines = inputData.split('\n')
    
    firstLine = lines[0].split()
    items = int(firstLine[0])
    capacity = int(firstLine[1])

    values = []
    weights = []

    for i in range(1, items+1):
        line = lines[i]
        parts = line.split()

        values.append(int(parts[0]))
        weights.append(int(parts[1]))
    items = len(values)

    # a trivial greedy algorithm for filling the knapsack
    # it takes items in-order until the knapsack is full
    tree = searchtree()     

    arr = [8,3,1,6,4,7,10,14,13]
    for i in arr:
        tree.create(i)
    
    print 'Breadth-First Traversal'
    tree.bft()
    print 'Inorder Traversal'
    tree.inorder(tree.root) 
    #print 'Preorder Traversal'
    #tree.preorder(tree.root) 
    #print 'Postorder Traversal'
    #tree.postorder(tree.root) 
    
    
    value = 20
    
    taken = []
    
    # prepare the solution in the specified output format
    outputData = str(value) + ' ' + str(1) + '\n'
    outputData += ' '.join(map(str, taken))
    return outputData

if __name__ == '__main__':
    print ''
    print 'BFS decision tree'
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

'''
if __name__ == '__main__':
    if len(sys.argv) > 1:
        fileLocation = sys.argv[1].strip()
        inputDataFile = open(fileLocation, 'r')
        inputData = ''.join(inputDataFile.readlines())
        inputDataFile.close()
        print solveIt(inputData)
    else:
        print 'This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/ks_4_0)'
'''
