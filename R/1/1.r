data <- read.csv('input.csv')
print(data)
print(ncol(data))
print(nrow(data))

ItPeople <- subset(data, Department == "IT")
print(ItPeople)

write.csv(ItPeople, 'output.csv')
