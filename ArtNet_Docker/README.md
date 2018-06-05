# Instructions for building artnet container_container and the artnet_serving image


## Run container
'''
mkdir artnet
cd artnet
docker build --pull -t $USER/tensorflow-serving-devel -f /home/gavin/ResearchProject/Engineering/serving/tensorflow_serving/tools/docker/Dockerfile.devel .
docker run --name=artnet_container -it $USER/tensorflow-serving-devel
'''

## Inside container, 
### install the tensorflow-serving-api PIP package using:
'''
pip install tensorflow-serving-api
'''
 
### install the ModelServer
Add TensorFlow Serving distribution URI as a package source (one time setup):
'''
echo "deb [arch=amd64] http://storage.googleapis.com/tensorflow-serving-apt stable tensorflow-model-server tensorflow-model-server-universal" | tee /etc/apt/sources.list.d/tensorflow-serving.list

curl https://storage.googleapis.com/tensorflow-serving-apt/tensorflow-serving.release.pub.gpg | apt-key add -
'''

### Run these commands to overcome missing libraries issue:
'''
add-apt-repository ppa:ubuntu-toolchain-r/test 
apt-get update
apt-get upgrade
apt-get dist-upgrade
'''

### Install tensorflow_hub
'''
#pip install ipykernel
pip install tensorflow_hub
'''

### Install and update TensorFlow ModelServer
'''
apt-get update && apt-get install tensorflow-model-server
'''

### Download from github and create ArtNet model
'''
git clone https://github.com/gavinconran/ArtNet.git
cd ArtNet
./retrainHub_InceptionV4.sh

[Ctrl-p] + [Ctrl-q]
'''

## Outside Container
### Commit image for deployment
Note that we detach from the container at the end of above instructions instead of terminating it, 
as we want to commit all changes to a new image $USER/artnet_serving for Kubernetes deployment.
'''
docker commit artnet_container $USER/artnet_serving
docker stop artnet_container
'''

## Run in local Docker container
Let's test the serving workflow locally using the built image.
'''
docker run -it $USER/artnet_serving
'''

## Inside Container
### Start the server
Run the gRPC tensorflow_model_server in the container.
'''
tensorflow_model_server --port=9000 --model_name=artnet --model_base_path=/ArtNet/models &> artnet_log &
'''

### Query the server
'''
cd /ArtNet/ArtNet_Clients
python ArtNet_Client.py --server=localhost:9000 \
--image=./dataPaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg 
'''

## Outside Container
### Start Server
'''
docker run -p 9000:9000 -it $USER/artnet_serving
'''

### In a new terminal, query the server
'''
cd /home/gavin/ResearchProject/ImageTestBed/Applications/LearningTransfer/Inception/retrained_models/LR_art_retraining/label_image
python ArtNet_Client.py --server=localhost:9000 \
--image=./data/PaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg
'''











