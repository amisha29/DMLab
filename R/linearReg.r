library(datasets)
car_data = cars
ind_cardata <- sample(1:nrow(cars), 0.8*nrow(cars))
train_cardata <- car_data[ind_cardata,]
test_cardata <- car_data[-ind_cardata,]
head(car_data)
model <- lm(dist ~ speed , train_cardata)
# predicting for speed =25
df <- data.frame(speed = 25)
pred <- predict.lm(model,df)
print(pred)
plot(car_data$speed,car_data$dist,xlab = "speed",ylab = "Distance")
abline(model)
  