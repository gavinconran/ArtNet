python retrain.py \
--image_dir ./data \
--how_many_training_steps=1000 \
--output_graph=./models/output_graph.pb \
--intermediate_output_graphs_dir=./models/intermediate_graph/ \
--intermediate_store_frequency=0 \
--output_labels=./models/output_labels.txt \
--summaries_dir=./models/retrain_logs \
--learning_rate=0.01 \
--testing_percentage=10 \
--validation_percentage=10 \
--eval_step_interval=10 \
--train_batch_size=100 \
--test_batch_size=-1 \
--validation_batch_size=100 \
--model_dir=./models/imagenet \
--bottleneck_dir=./models/bottleneck \
--final_tensor_name=final_result \
--saved_model_dir=./models/$(date +%s)/ \
--tfhub_module https://tfhub.dev/google/imagenet/resnet_v2_50/feature_vector/1





