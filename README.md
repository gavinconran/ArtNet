# ArtNet
This directory contains the paper "ArtNet:An Art Image Classifier fine-tuned from pre-trained Convolutional Neural Networks" and supporting code.

To replicate the model described in the paper, you must first install TensorFlow, preferably in a virtual environment [https://www.tensorflow.org/install/install_linux]. Once TensorFlow is installed, run the shell script, ./retrainHub_InceptionV4.sh, at the command line, which in turn calls the python program, retrain.py. 

The above script trains, validates and tests an ArtNet model but the test results discussed in the paper can be replicated by executing the following Jupyter Notebook, ArtNet_Classification.ipynb. The test data can be found in the dataTestPaper folder.



