# ArtNet
This repository contains the paper "ArtNet:An Art Image Classifier fine-tuned from pre-trained Convolutional Neural Networks" and supporting code.

To replicate the best performing model, as described in the paper, you must first install TensorFlow, preferably in a virtual environment [https://www.tensorflow.org/install/install_linux]. 

Once TensorFlow is installed and the virtual environment activated, run the shell script:

(tensorflow) $ ./retrainHub_InceptionV4.sh

at the command line, which in turn calls the python program, retrain.py. 

The above script trains, validates and tests an ArtNet model and the test images discussed in the paper can be replicated by executing the following Jupyter Notebook, ArtNet_Classification.ipynb. The test data can be found in the dataTestPaper folder. The results may differ from the paper due to the random nature of retrain.py but the generated ArtNet model is valid and should have an accuracy close to that described in the paper.

Training and validation progress can be visualised by using tensorboard by executing the following command

(tensorflow) $ tensorboard --logdir=./models

# New Op
As a pedagogical exercise I added a new Op, called CopyOfInputOp, by following the instructions in [https://www.tensorflow.org/extend/adding_an_op]. The new op very simple operation that just returns the input. The files can be found in the directory CopyOfInputOp. The source code must be compiled on your own machine.

To create a model using this new Op, run the shell script:

(tensorflow) $ ./retrainHub_CopyOfInputOp_InceptionV4.sh

at the command line, which in turn calls the python program, retrainWithCopyOfInputOp.py. 

Training and validation progress can be visualised by using tensorboard by executing the following command

(tensorflow) $ tensorboard --logdir=./models/CopyOfInputOp

 






