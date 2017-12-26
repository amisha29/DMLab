data <- iris

#View the dataset
View(data)

#See dimensions, row names, head and tail records
dim(data)
names(data)
head(data)
tail(data)
str(data) #Species labels converted to factors


#Check for unique values
sapply(data, function(x) length(unique(x)))

#Check for missing values
sapply(data,function(x) sum(is.na(x)))

#See statistics
summary(data)
mean(data$Sepal.Length)
median(data$Sepal.Width)
range(data$Petal.Length)
var(data$Sepal.Length) #variance

#covarience and correlation
cov(data$Sepal.Length, data$Petal.Length)
cor(data$Sepal.Length, data$Petal.Length)

#Plots
hist(data$Sepal.Length)
plot(density(data$Sepal.Length))
table(data$Species)
pie(table(data$Petal.Width))
barplot(table(data$Sepal.Width))

#scatter plot: drawn for 2 numeric values. col = colours in the plot, pch = shapes for plot
plot(data$Sepal.Length, data$Sepal.Width, col=data$Species, pch=c(15,16,17) )
legend("topleft", legend=levels(data$Species), pch=c(15,16,17))


