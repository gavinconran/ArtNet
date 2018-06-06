import tensorflow as tf

class ZeroOutTest(tf.test.TestCase):
  def test1ZeroOut(self):
    copy_of_input_module = tf.load_op_library('./copy_of_input.so')
    with self.test_session():
      result = copy_of_input_module.copy_of_input([5, 4, 3, 2, 1])
      self.assertAllEqual(result.eval(), [5, 4, 3, 2, 1])

  def test2ZeroOut(self):
    copy_of_input_module = tf.load_op_library('./copy_of_input.so')
    with self.test_session():
      result = copy_of_input_module.copy_of_input([5])
      self.assertAllEqual(result.eval(), [5])

  def test3ZeroOut(self):
    copy_of_input_module = tf.load_op_library('./copy_of_input.so')
    with self.test_session():
      result = copy_of_input_module.copy_of_input([])
      self.assertAllEqual(result.eval(), [])

  def test4ZeroOut(self):
    copy_of_input_module = tf.load_op_library('./copy_of_input.so')
    with self.test_session():
      result = copy_of_input_module.copy_of_input([5, 5])
      self.assertAllEqual(result.eval(), [5, 5])

if __name__ == "__main__":
  tf.test.main()
