# Instructions for building artnet_serving image
The instructions are partly derived from [https://www.tensorflow.org/serving/serving_inception]. You may find this docker tutorial useful [https://docs.docker.com/get-started/].


## Create artnet_container from pwdtensorflow-serving-devel container
```
cd ArtNet_Docker
docker build --pull -t $USER/tensorflow-serving-devel -f /path/to/ArtNet/ArtNet_Docker/Dockerfile.devel .
docker run --name=artnet_container -it $USER/tensorflow-serving-devel
```

## From inside the container: 
1) install the tensorflow-serving-api PIP package using:
```
pip install tensorflow-serving-api
```
 
2) install the ModelServer
Add TensorFlow Serving distribution URI as a package source (one time setup):
```
echo "deb [arch=amd64] http://storage.googleapis.com/tensorflow-serving-apt stable tensorflow-model-server tensorflow-model-server-universal" | tee /etc/apt/sources.list.d/tensorflow-serving.list

curl https://storage.googleapis.com/tensorflow-serving-apt/tensorflow-serving.release.pub.gpg | apt-key add -
```

3) Run these commands to overcome missing libraries issue:
```
add-apt-repository ppa:ubuntu-toolchain-r/test 
apt-get update
apt-get upgrade
apt-get dist-upgrade
```

4) Install tensorflow_hub
```
pip install tensorflow_hub
```

5) Install and update TensorFlow ModelServer
```
apt-get update && apt-get install tensorflow-model-server
```

6) Download from github and create ArtNet model
```
git clone https://github.com/gavinconran/ArtNet.git
cd ArtNet
./retrainHub_InceptionV4.sh

[Ctrl-p] + [Ctrl-q]
```

## From Outside Container
1) Commit image for deployment

Note that we detach from the container at the end of above instructions instead of terminating it, 
as we want to commit all changes to a new image $USER/artnet_serving for Kubernetes deployment.
```
docker commit artnet_container $USER/artnet_serving
docker stop artnet_container
```

2) Let's test the serving workflow locally using the built image.
```
docker run -it $USER/artnet_serving
```

## From inside Container
1) Run the gRPC tensorflow_model_server in the container.
```
tensorflow_model_server --port=9000 --model_name=artnet --model_base_path=/ArtNet/models &> artnet_log &
```

2) Query the server
```
cd /ArtNet/ArtNet_Clients
python ArtNet_Client.py --server=localhost:9000 \
--image=./dataPaperTest/1454_VirginAndChild_Rogier_van_der_Weyden.jpeg 
```














