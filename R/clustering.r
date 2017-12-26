library(datasets)
library(cluster)
install.packages("factoextra")
library(factoextra) #install.packages("factoextra")
data <- iris
data$Species<-NULL 
d<- scale(dist(data,method = "euclidian"))
kfit <- kmeans(d,3)
hfit <- hkmeans(d,3)
fviz_cluster(hfit, data, geom = "point")
fviz_cluster(kfit, data, geom = "point")
