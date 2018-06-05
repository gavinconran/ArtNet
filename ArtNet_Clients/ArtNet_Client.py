
# Copyright 2016 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================

#!/usr/bin/env python2.7

"""Send JPEG image to tensorflow_model_server loaded with ArtNet model.
"""

from __future__ import print_function

# This is a placeholder for a Google-internal import.

from grpc.beta import implementations
import tensorflow as tf
import numpy as np

from tensorflow_serving.apis import predict_pb2
from tensorflow_serving.apis import prediction_service_pb2


tf.app.flags.DEFINE_string('server', 'localhost:9000',
                           'PredictionService host:port')
tf.app.flags.DEFINE_string('image', '', 'path to image in JPEG format')
FLAGS = tf.app.flags.FLAGS

def read_tensor_from_image_file(file_name,
                                input_height=224,
                                input_width=224,
                                input_mean=0,
                                input_std=255):
  input_name = "file_reader"
  output_name = "normalized"
  file_reader = tf.read_file(file_name, input_name)
  if file_name.endswith(".png"):
    image_reader = tf.image.decode_png(
        file_reader, channels=3, name="png_reader")
  elif file_name.endswith(".gif"):
    image_reader = tf.squeeze(
        tf.image.decode_gif(file_reader, name="gif_reader"))
  elif file_name.endswith(".bmp"):
    image_reader = tf.image.decode_bmp(file_reader, name="bmp_reader")
  else:
    image_reader = tf.image.decode_jpeg(
        file_reader, channels=3, name="jpeg_reader")
  float_caster = tf.cast(image_reader, tf.float32)
  dims_expander = tf.expand_dims(float_caster, 0)
  resized = tf.image.resize_bilinear(dims_expander, [input_height, input_width])
  normalized = tf.divide(tf.subtract(resized, [input_mean]), [input_std])
  sess = tf.Session()
  result = sess.run(normalized)

  return result

def load_labels(label_file):
  label = []
  proto_as_ascii_lines = tf.gfile.GFile(label_file).readlines()
  for l in proto_as_ascii_lines:
    label.append(l.rstrip())
  return label


def main(_):
  # set up communications channel with server
  host, port = FLAGS.server.split(':')
  channel = implementations.insecure_channel(host, int(port))
  stub = prediction_service_pb2.beta_create_PredictionService_stub(channel)
  # Send request (See prediction_service.proto for gRPC request/response details)
  # set request parameters
  input_height = 224; input_width = 224
  input_mean = 0; input_std = 255
  # create request tensor
  data_t = read_tensor_from_image_file(
          FLAGS.image,
          input_height=input_height,
          input_width=input_width,
          input_mean=input_mean,
          input_std=input_std)
  # create request
  request = predict_pb2.PredictRequest()
  request.model_spec.name = 'artnet'
  #request.model_spec.signature_name = 'serving_default' #'predict_images' # not needed as serving_default is the default
  request.inputs['image'].CopyFrom(
        tf.contrib.util.make_tensor_proto(data_t))
  # send request
  result = stub.Predict(request, 5.0)  # 10 secs timeout

  # debugging print statements
  '''
  print('returned from server: ', result)
  # result is an object of type tensorflow_serving.apis.predict_pb2.PredictResponse
  print('type(result): ', type(result))
  # result.outputs is an object of type google.protobuf.pyext._message.MessageMapContainer
  print('type(result.outputs): ', type(result.outputs))
  '''

  # convert result (type: tensorflow_serving.apis.predict_pb2.PredictResponse) to resultNumpy (type: numpy)
  floats = result.outputs['prediction'].float_val
  resultNumpy = []
  for value in floats:
    resultNumpy.append(float(value))
  resultNumpy = np.array(resultNumpy)

  # get top 3 indices of the highest predictions
  top_k = resultNumpy.argsort()[-3:][::-1]  ## -9 was changed from -5 \n",
  # get category labels
  label_file ='./output_labels.txt'
  labels = load_labels(label_file)
  #print(labels)
  print("###############################################")
  print('Image Name: ' + FLAGS.image)
  for i in top_k:
    percent = resultNumpy[i] * 100
    percent = '%05.2f' % (percent,)
    print(labels[i].title() + ": " + str(percent) + "%")
    

if __name__ == '__main__':
  tf.app.run()
