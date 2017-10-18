# get data into R
data <- read.table("/Users/qianying/Desktop/JOB/walmart/project/compdata.txt")
attach(data)
data2 <- read.table("/Users/qianying/Desktop/JOB/walmart/project/compdata_true_errors.txt")
attach(data2)

# view data using scatter plots
plot(V1, V2, main="Scatterplot", xlab="CPU Temp", ylab="Read Error")
boxplot(V1, main = "CPU boxplot")
x1 = data$V1
x2 = data$V2
x3 = data2$V1

# install neural network in R
install.packages("neuralnet")
library(MASS)
library(neuralnet)
require(neuralnet)

# combine data together
total = cbind(x1,x2,x3)
# split dataset into "training" (80%) and "validation" (20%)
ind <- sample(2, nrow(total), replace=TRUE, prob = c(0.8,0.2))
tdata <- total[ind==1,]
vdata <- total[ind==2,]
# train neural net
nn = neuralnet(x3~x1+x2,data = tdata,hidden = 2,err.fct = "sse", linear.output = FALSE)
#NeuralNet backprop
nn.bp = neuralnet(x3~x1+x2,data = tdata,hidden = 2,learningrate = 0.001, algorithm = "backprop", err.fct = "sse", linear.output = FALSE)
# test on the validation dataset
vdatat <- subset(vdata, select=c("x1","x2"))
vdatav <- subset(vdata, select=c("x3"))
new.output = compute(nn,covariate = vdatat)
new.output$net.result
#Assign output> 0.5 to output 1.0; otherwise 0
nn1 = ifelse(new.output$net.result>0.5,1,0)
misClassificationError = mean(vdatav!=nn1)
plot(nn)

# using all data for training
nnt = neuralnet(x3~x1+x2,data = data,hidden = 2,err.fct = "ce", linear.output = FALSE)
plot(nnt)
nn2 = ifelse(nnt$net.result[[1]]>0.5,1,0)
misClassificationError2 = mean(x3!=nn2)
OutPutVsPred = cbind(x3,nn2)
nnt.bp = neuralnet(x3~x1+x2,data = data,hidden = 2,learningrate = 0.001, algorithm = "backprop", err.fct = "sse", linear.output = FALSE)
nnt.bp


