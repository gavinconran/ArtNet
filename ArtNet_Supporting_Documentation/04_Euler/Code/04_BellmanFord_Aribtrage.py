import networkx as nx
import matplotlib.pyplot as plt
import math
import numpy as np


def bellman_ford(G, source, currDict):
    '''
    This implementation takes in a graph and fills
    two arrays (distance and predecessor) about the
    shortest path from the source to each node/vertex
    '''

    # Step 1: initialize graph
    dist = []
    pred = []
    for v in list(G.nodes()):
        dist.append(np.inf) # Initialize the distance to all vertices to infinity
        pred.append(None)   # And having a null predecessor

    dist[source] = 0        # The distance from the source to itself is, of course, zero

    # Step 2: relax edges repeatedly   
    weights = nx.get_edge_attributes(G, 'weight')
    for i in np.arange(1, len(list(G.nodes))):
        for edge in G.edges:
            u = edge[0]; v = edge[1]; w = weights[(u, v)]
            if dist[u] + w < dist[v]:
                dist[v] =  dist[u] + w
                pred[v] = u

    # Step 3: check for negative-weight cycles
    for edge in G.edges:
        u = edge[0]; v = edge[1]; w = weights[(u, v)]
        if dist[u] + w < dist[v]:
            U, V = currDict[u], currDict[v]
            print("Graph contains a negative-weight cycle")
    
    # print out an arbitrage loop        
    stake = 1000.0
    stake1 = stake * np.exp(-weights[(0,1)])
    print(str(round(stake, 5)) + " " + currDict[0] + " = " + str(round(stake1,5)) + " " + currDict[1] )
    stake2 = stake1 * np.exp(-weights[(1,4)])
    print(str(round(stake1,5)) + " " + currDict[1] + " = " + str(round(stake2,5)) + " " + currDict[4] )
    stake3 = stake2 * np.exp(-weights[(4,0)])
    print(str(round(stake2, 5)) + " " + currDict[4] + " = " + str(round(stake3, 5)) + " " + currDict[0] )

    return dist, pred

   

# create graph 1
G=nx.DiGraph()

# currency codes
USD = 0; EUR = 1; GBP = 2; CHF = 3; CAD = 4; 
# create a currency dict
currDict = {}
currDict[0] = 'USD'; currDict[1] = 'EUR'; currDict[2] = 'GBP'; currDict[3] = 'CHF'; currDict[4] = 'CAD';

currDict_inv = {}
currDict_inv['USD']=0; currDict['EUR']=1; currDict['GBP']=2; currDict['CHF']=3; currDict['CAD']=4;

# add edges to graph 1
# USD
G.add_edge(USD, EUR, weight=-math.log(0.741))
G.add_edge(USD, GBP, weight=-math.log(0.657))
G.add_edge(USD, CHF, weight=-math.log(1.061))
G.add_edge(USD, CAD, weight=-math.log(1.005))
# EUR
G.add_edge(EUR, GBP, weight=-math.log(0.888))
G.add_edge(EUR, CHF, weight=-math.log(1.433))
G.add_edge(EUR, CAD, weight=-math.log(1.366))
G.add_edge(EUR, USD, weight=-math.log(1.349))
# GBP
G.add_edge(GBP, CHF, weight=-math.log(1.614))
G.add_edge(GBP, CAD, weight=-math.log(1.538))
G.add_edge(GBP, USD, weight=-math.log(1.521))
G.add_edge(GBP, EUR, weight=-math.log(1.126))
# CHF
G.add_edge(CHF, USD, weight=-math.log(0.942))
G.add_edge(CHF, EUR, weight=-math.log(0.698))
G.add_edge(CHF, GBP, weight=-math.log(0.619))
G.add_edge(CHF, CAD, weight=-math.log(0.953))
# CAD
G.add_edge(CAD, CHF, weight=-math.log(1.049))
G.add_edge(CAD, USD, weight=-math.log(0.995))
G.add_edge(CAD, EUR, weight=-math.log(0.732))
G.add_edge(CAD, GBP, weight=-math.log(0.650))

# use bellman-ford to detect arbitrage
pred, dist = bellman_ford(G, USD, currDict)



