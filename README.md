# ArtNet
## Introduction
Out of a general interest in the **Arts & Humanities** I found myself studying [Ancient Greek and Latin](http://www.open.edu/openlearn/history-the-arts/discovering-ancient-greek-and-latin/content-section-0?active-tab=description-tab), [Greek and Roman Mythology](https://www.coursera.org/learn/mythology), [Ancient Greek History](https://www.coursera.org/learn/digital), [Ancient Philosophy](https://www.coursera.org/learn/plato), [Arab-Islamic History](https://www.edx.org/course/arab-islamic-history-from-tribes-to-empires), [Japanese](https://www.edx.org/course/japanese-pronunciation-for-communication) and [Classical Music](https://www.coursera.org/learn/introclassicalmusic) in addition to following the BBC's multitude of excellent arts and history programs. To further my knowledge of **Artificial Intelligence** I took courses, such as [Digital Signal Processing](https://www.coursera.org/learn/dsp), [Image & Video Processing](https://www.coursera.org/learn/digital), [Advanced Software Construction in Java](https://www.edx.org/course/advanced-software-construction-java-mitx-6-005-2x), [Advanced C++](https://www.edx.org/course/advanced-c-0), [Machine Learning](https://www.edx.org/course/machine-learning-columbiax-csmm-102x-0), [Robotics](https://www.edx.org/course/robotics-kinematics-mathematical-pennx-robo1x), [Cloud Computing](https://www.edx.org/micromasters/cloud-computing), [Analytics in Python](https://www.edx.org/course/analytics-python-columbiax-bamm-101x) and [Data Modelling](https://www.edx.org/course/data-models-decisions-business-analytics-columbiax-bamm-102x).  

ArtNet successfully combines my interests in the arts & sciences, in particular Art & Artificial Intelligence, and brings together my study and work of the last couple of years into a single piece of research, marking a personal learning milestone.

## Create the ArtNet Model using TensorFlow
This repository contains the paper "**ArtNet:An Art Image Classifier fine-tuned from pre-trained Convolutional Neural Networks**" together with supporting documentation and code.

To replicate the best performing model, as described in the paper, you must first install TensorFlow, preferably in a virtual environment [https://www.tensorflow.org/install/install_linux]. 

Once TensorFlow is installed and the virtual environment activated, run the shell script:
```
(tensorflow) $ ./retrainHub_InceptionV4.sh
```
at the command line, which in turn calls the python program, retrain.py. 

The above script trains, validates and tests an ArtNet model and the test images discussed in the paper can be replicated by executing the following Jupyter Notebook, ArtNet_Classification.ipynb, found in the ArtNet_Clients directory, along with the test data folder, dataTestPaper. 

N.B.: The results will be different from the paper due to the use of the 'Hub' version of retrain.py. I used the pre-Hub version of retrain.py when writing the paper. That said, the generated ArtNet model is valid and should have an accuracy very close to that described in the paper.

Training and validation progress can be visualised by using tensorboard by executing the following command
```
(tensorflow) $ tensorboard --logdir=./models
```
## Deploy a Model Server using TensorFlow
To setup a Model Server I refer the reader to the online tutorial [https://www.tensorflow.org/serving/setup]. It is possible to intall the prerequisites using two options:
1. by pip    (install pre-compiled binaries)
2. by bazel  (compile from source)

The following instructions will use pip.

To start the server execute the following command at the command line:
```
(tensorflow) $ tensorflow_model_server --port=9000 --model_name=artnet --model_base_path=/path/to/model/
```
N.B.: /path/to/model/ is the absolute path to the model directory (which in my case is the models directory, as above)

I have provided a script, ArtNet_Client.py, for users to test the ArtNet server. It can be found in the directory ArtNet_Clients. To operate:
```
(tensorflow) $ python ArtNet_Client.py --server=localhost:9000 \
--image=./dataPaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg 
```

## Deploy the ArtNet Server using TensorFlow in a Docker Container
To setup an ArtNet Docker Image please refer to ArtNet_Docker/README.md.

Once you have created the docker image, $USER/artnet_serving, you can run the associated container with the following command:

```
$ docker run -p 9000:9000 -it $USER/artnet_serving
root@854459658fb4:/# tensorflow_model_server --port=9000 --model_name=artnet --model_base_path=/ArtNet/models &> artnet_log &
```

In a new terminal, query the ArtNet server
```
cd ArtNet_Clients
$ python ArtNet_Client.py --server=localhost:9000 \
--image=./dataPaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg 
```

The image, $USER/artnet_serving, can be deployed to a serving cluster with Kubernetes in the Google Cloud Platform as described in [https://www.tensorflow.org/serving/serving_inception]. 



## Extend TensorFlow by creating a new Op
As a pedagogical exercise I added a new Op, called CopyOfInputOp, by following the instructions in [https://www.tensorflow.org/extend/adding_an_op]. The new op is a very simple operation that just returns the input. The files can be found in the directory TensorFlow_Extend. The source code must be compiled on your own machine and can be done by executing the following script in the TensorFlow_Extend directory:
```
(tensorflow) $  ./compileCopyOfInputOp.sh
```
To create an ArtNet model using this new Op, run the shell script:
```
(tensorflow) $ ./retrainHub_InceptionV4_Extend.sh
```
at the command line, which in turn calls the python program, retrain_Extend.py. 

Training and validation progress can be visualised by using tensorboard by executing the following command
```
(tensorflow) $ tensorboard --logdir=./models/CopyOfInputOp
```
### Note:
When running ArtNet_Classification.ipynb with the ArtNet model created with the new op, the following lines of code must be included in the notebook:

```python
model_file = "../models/CopyOfInputOp/output_graph.pb"
label_file = "../models/CopyOfInputOp/output_labels.txt"
tf.load_op_library('../TensorFlow_Extend/copy_of_input.so')
```




