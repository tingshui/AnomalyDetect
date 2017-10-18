Qianying Lin 

1 How to run with the jar file:
(1) if you want to run the auto-generated test (randomly generated input dataset, size = 1000, temperature range from 0 to 80, read error range from 0 to 200), command: 
java -jar AnomDetect.jar 0 ""

(2) if you want to read in a specific file and test:
java -jar AnomDetect.jar 1 [file_address]
input file format:
- should not have header
- data in each line: machine_name, temperature, error. The data should be separated by “,”
- you can check the file: test.txt


2 Brief procedures:

(1) Build neural network:
- I use R to plot and explore the raw data

- I use R neural net package to train a neural network. I set 2 hidden layers. I divide the training dataset: validation dataset = 8: 2. The result is:
        Error Reached Threshold Steps
1 6.808349806     0.00998286098   754
Result is not so good due to the limited data, I also try backpropagation, not much better.

- As result in previous step is not so good, so I just train with all data. The result is:            Error Reached Threshold Steps
1 0.0003650824569    0.009635058654 28154
It may not be good to do this, I did this just for simple modeling. The I obtain the parameters of neural network model. The diagram nn.pdf is shown.

> nn$weights
[[1]]
[[1]][[1]]
               [,1]             [,2]
[1,] 375.6784985336  6.9969995575113
[2,]   0.3767668675 -0.0831000625456
[3,]  -3.5024616772 -0.0001834465553

[[1]][[2]]
             [,1]
[1,]  286.3411224
[2,] -144.4132258
[3,] -277.8035594

(2) Building alert system:
In general, I implemented  the “producer-consumer” model.
AnomDetect is the main program. It first reads test data from given file, or generates data randomly. Then it builds a detector with a pool of 10 threads and a blocking FIFO queue with size of 100. The detector will run the neural net model and make a judgement of the input data. If the input data is predicted to be an anomaly, the detector will add it to the queue and add the timestamp to it. The queue is shared by the detector and alert. The alert is to alert the anomaly. If the queue is not empty, the alert will take the anomaly and print the alert. 





  

