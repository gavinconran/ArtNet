import networkx as nx
import matplotlib.pyplot as plt
import math
import importlib


# create graph 1
G=nx.DiGraph()

# add edges to graph 1
G.add_edge('USD', 'EUR', weight=round(-math.log(0.741),4))
G.add_edge('USD', 'GBP', weight=round(-math.log(0.657),4))
G.add_edge('USD', 'CHF', weight=round(-math.log(1.061),4))
G.add_edge('USD', 'CAD', weight=round(-math.log(1.005),4))

G.add_edge('EUR', 'USD', weight=round(-math.log(1.349),4))
G.add_edge('EUR', 'GBP', weight=round(-math.log(0.888),4))
G.add_edge('EUR', 'CHF', weight=round(-math.log(1.433),4))
G.add_edge('EUR', 'CAD', weight=round(-math.log(1.366),4))

G.add_edge('GBP', 'USD', weight=round(-math.log(1.521),4))
G.add_edge('GBP', 'EUR', weight=round(-math.log(1.126),4))
G.add_edge('GBP', 'CHF', weight=round(-math.log(1.614),4))
G.add_edge('GBP', 'CAD', weight=round(-math.log(1.538),4))

G.add_edge('CHF', 'USD', weight=round(-math.log(0.942),4))
G.add_edge('CHF', 'EUR', weight=round(-math.log(0.698),4))
G.add_edge('CHF', 'GBP', weight=round(-math.log(0.619),4))
G.add_edge('CHF', 'CAD', weight=round(-math.log(0.953),4))

G.add_edge('CAD', 'USD', weight=round(-math.log(0.995),4))
G.add_edge('CAD', 'EUR', weight=round(-math.log(0.732),4))
G.add_edge('CAD', 'GBP', weight=round(-math.log(0.650),4))
G.add_edge('CAD', 'CHF', weight=round(-math.log(1.049),4))


## draw graph1
# positions for all nodes
pos = nx.circular_layout(G)  
# nodes
nx.draw_networkx_nodes(G, pos, node_size=0)
# edges
nx.draw_networkx_edges(G, pos, edgelist=G.edges(), width=1, arrows=True, alpha =0.1)
# arbitrage edges
arbitragelist = [('USD','EUR'), ('EUR', 'CAD'), ('CAD', 'USD')]  
nx.draw_networkx_edges(G, pos, edgelist=arbitragelist, width=1, arrows=True, alpha =1.0)
# draw labels
weights = nx.get_edge_attributes(G, 'weight')
nx.draw_networkx_edge_labels(G, pos, edge_labels=weights, label_pos=0.8, font_size=10, font_family='sans-serif')
nx.draw_networkx_labels(G, pos, font_size=15, font_color='r', font_family='sans-serif')

plt.axis('off')
plt.show()





