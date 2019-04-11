using Gadfly, RDatasets
# read dataset
iris = dataset("datasets", "iris")
# Plot DataFrame
plot(iris, x=:SepalLength, y=:SepalWidth, Geom.point)
#img1a = SVG("iris_plot.svg", 14cm, 8cm)
#draw(img1a, fig1a)
# Plot Arrays
SepalLength = iris[:SepalLength]
SepalWidth = iris[:SepalWidth]
plot(x=SepalLength, y=SepalWidth, Geom.point,
     Guide.xlabel("SepalLength"), Guide.ylabel("SepalWidth"))

# Colour / Rendering
plot(iris, x=:SepalLength, y=:SepalWidth, color=:Species, Geom.point)
#img1c = SVG("iris_plot.svg", 14cm, 8cm)
#draw(img1c, fig1c)

plot(iris, x=:SepalWidth, Geom.bar)
#img2a = SVG("sepalWidth_hist.svg", 14cm, 8cm)
#draw(img2a, fig2a)
#hstack(fig1c, fig2a)
#img3 = SVG("iris_sepal_hstack.svg", 14cm, 8cm)
#draw(img3, fig3)
