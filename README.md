# ArtNet
## Create a Model using TensorFlow
This repository contains the paper "ArtNet:An Art Image Classifier fine-tuned from pre-trained Convolutional Neural Networks" and supporting code.

To replicate the best performing model, as described in the paper, you must first install TensorFlow, preferably in a virtual environment [https://www.tensorflow.org/install/install_linux]. 

Once TensorFlow is installed and the virtual environment activated, run the shell script:
```
(tensorflow) $ ./retrainHub_InceptionV4.sh
```
at the command line, which in turn calls the python program, retrain.py. 

The above script trains, validates and tests an ArtNet model and the test images discussed in the paper can be replicated by executing the following Jupyter Notebook, ArtNet_Classification.ipynb, found in the ArtNet_Clients directory, along with the test data folder, dataTestPaper. The results may differ from the paper due to the random nature of retrain.py but the generated ArtNet model is valid and should have an accuracy close to that described in the paper.

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
N.B.: /path/to/server/ is the absolute path to the model directory (which in my case is the models directory, as above)

I have provided a script, ArtNet_Client.py, for users to test the ArtNet server. It can be found in the directory ArtNet_Clients. To opearte:
```
(tensorflow) $ python ArtNet_Client.py --server=localhost:9000 \
--image=./dataPaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg 
```

## Extend TensorFlow by creating a new Op
As a pedagogical exercise I added a new Op, called CopyOfInputOp, by following the instructions in [https://www.tensorflow.org/extend/adding_an_op]. The new op is a very simple operation that just returns the input. The files can be found in the directory CopyOfInputOp. The source code must be compiled on your own machine and can be done by executing the following script in the CopyOfInputOp directory:
```
(tensorflow) $  ./compileCopyOfInputOp.sh
```
To create an ArtNet model using this new Op, run the shell script:
```
(tensorflow) $ ./retrainHub_InceptionV4_CopyOfInputOp.sh
```
at the command line, which in turn calls the python program, retrainWithCopyOfInputOp.py. 

Training and validation progress can be visualised by using tensorboard by executing the following command
```
(tensorflow) $ tensorboard --logdir=./models/CopyOfInputOp
```
### Note:
When running ArtNet_Classification.ipynb with the ArtNet model created with the new op, the following lines of code must be included in the notebook:

```python
model_file = "../models/CopyOfInputOp/output_graph.pb"
label_file = "../models/CopyOfInputOp/output_labels.txt"
tf.load_op_library('../CopyOfInputOp/copy_of_input.so')
```




