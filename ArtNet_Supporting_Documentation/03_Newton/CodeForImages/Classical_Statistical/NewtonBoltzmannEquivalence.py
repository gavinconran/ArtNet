import random, pylab

def markov_disks_box(N, sigma, delta, L):    
    sigma_sq = sigma ** 2
    for k in range(N):
        # key element: random choice of one disk whose coorditaes we alter slightly
        a = random.choice(L)
        b = [a[0] + random.uniform(-delta, delta), a[1] + random.uniform(-delta, delta)]
        min_dist = min((b[0] - c[0]) ** 2 + (b[1] - c[1]) ** 2 for c in L if c != a)
        box_cond = min(b[0], b[1]) < sigma or max(b[0], b[1]) > 1.0 - sigma
        if not (box_cond or min_dist < 4.0 * sigma ** 2):
            a[:] = b
        
    return L


N = 4
sigma = 0.1197
delta = 0.1
n_runs = 2000000
L = [[0.25, 0.25], [0.75, 0.25], [0.25, 0.75], [0.75, 0.75]]
histo_data = []
for run in range(n_runs):
    pos = markov_disks_box(N, sigma, delta, L)
    for k in range(N):
        histo_data.append(pos[k][0])
pylab.hist(histo_data, bins=100, normed=True)
pylab.xlabel('x')
pylab.ylabel('frequency')
#pylab.title('Markov-chain sampling: x coordinate histogram (density eta=0.18)')
pylab.title('Newton and Boltzmann Mechanics Equivalence: ' 
             '\n'
             'x coordinate histogram')
pylab.grid()
pylab.savefig('markov_chain_disks_histo.png')
pylab.show()
