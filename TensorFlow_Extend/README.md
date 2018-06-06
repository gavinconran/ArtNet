# README
compileCopyOfInputOp.sh, copy_of_input_op_test.py and copy_of_input.cc files are here after following instructions in:
[https://www.tensorflow.org/extend/adding_an_op]
copy_of_input.so was created upon compilation of copy_of_input.cc
copy_of_input_op_test.py contains test cases for copy_of_input.c

# Note on compiling:
Note on gcc version >=5: gcc uses the new C++ ABI since version 5. The binary pip packages available on the TensorFlow website are built with gcc4 that uses the older ABI. If you compile your op library with gcc>=5, add -D_GLIBCXX_USE_CXX11_ABI=0 to the command line to make the library compatible with the older abi. Furthermore if you are using TensorFlow package created from source remember to add --cxxopt="-D_GLIBCXX_USE_CXX11_ABI=0" as bazel command to compile the Python package.


