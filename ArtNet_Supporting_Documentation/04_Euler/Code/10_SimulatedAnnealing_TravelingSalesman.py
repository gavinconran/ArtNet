# Travelling salesman problem
# simulated annealing

import random, math, pylab

# helper functions
def dist(x, y):
    return math.sqrt((x[0] - y[0]) ** 2 + (x[1] - y[1]) ** 2)

def tour_length(cities, N):
    return sum(dist(cities[k + 1], cities[k]) for k in range(N - 1)) + dist(cities[0], cities[N - 1])

N = 50 #20 #10 # number of cities
random.seed(54321)
cities = [(random.uniform(0.0, 1.0), random.uniform(0.0, 1.0)) for i in range(N)]

# Plot cities
pylab.figure(1)
x = [city[0] for city in cities]
y = [city[1] for city in cities]
pylab.scatter(x, y)
pylab.title('Cities')
pylab.axis('scaled')
pylab.axis([0.0, 1.0, 0.0, 1.0])
pylab.savefig('Figure_4a_' + str(N) + '_cities' + '.png')
pylab.show()

# run simulation
random.seed()
random.shuffle(cities)
beta = 1.0
n_accept = 0
best_energy = float('inf')
energy =  tour_length(cities, N)
for step in xrange(1000000):
    if n_accept == 100:
        beta *=  1.005
        n_accept = 0
    p = random.uniform(0.0, 1.0)
    if p  < 0.2:
        i = random.randint(0, N / 2)
        cities = cities[i:] + cities[:i]
        i = random.randint(0, N / 2)
        a = cities[:i]
        a.reverse()
        new_cities =  a + cities[i:]
    elif p < 0.6:
        new_cities = cities[:]
        i = random.randint(1, N - 1)
        a = new_cities.pop(i)
        j = random.randint(1, N - 2)
        new_cities.insert(j, a)
    else:
        new_cities = cities[:]
        i = random.randint(1, N - 1)
        j = random.randint(1, N - 1)
        new_cities[i] = cities[j]
        new_cities[j] = cities[i]
    new_energy =  tour_length(new_cities, N)
    if random.uniform(0.0, 1.0) < math.exp(- beta * (new_energy - energy)):
        n_accept += 1
        energy = new_energy
        cities = new_cities[:]
        if energy < best_energy:
           best_energy = energy
           best_tour = cities[:]
    if step % 100000 == 0:
        print energy, step, 1.0 / beta

cities = best_tour[:]

# Plot optimum path
pylab.figure(2)
for i in range(1, N):
    pylab.plot([cities[i][0], cities[i - 1][0]], [cities[i][1], cities[i - 1][1]], 'bo-')
pylab.plot([cities[0][0], cities[N - 1][0]], [cities[0][1], cities[N - 1][1]], 'bo-')
pylab.title('Optimum Path Linking Cities')
pylab.axis('scaled')
pylab.axis([0.0, 1.0, 0.0, 1.0])
pylab.savefig('Figure_4b_' + str(N) + '_cities_optimum_path' + '.png')
pylab.show()
