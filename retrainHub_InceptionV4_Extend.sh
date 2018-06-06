python retrain_Extend.py \
--image_dir ./data \
--how_many_training_steps=1000 \
--output_graph=./models/CopyOfInputOp/output_graph.pb \
--intermediate_output_graphs_dir=./models/CopyOfInputOp/intermediate_graph/ \
--intermediate_store_frequency=0 \
--output_labels=./models/CopyOfInputOp/output_labels.txt \
--summaries_dir=./models/CopyOfInputOp/retrain_logs \
--learning_rate=0.01 \
--testing_percentage=10 \
--validation_percentage=10 \
--eval_step_interval=10 \
--train_batch_size=100 \
--test_batch_size=-1 \
--validation_batch_size=100 \
--model_dir=./models/CopyOfInputOp/imagenet \
--bottleneck_dir=./models/CopyOfInputOp/bottleneck \
--final_tensor_name=final_result \
--tfhub_module https://tfhub.dev/google/imagenet/resnet_v2_50/feature_vector/1




