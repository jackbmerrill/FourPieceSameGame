import java.io.IOException;

/**
 * Mock of a failing appendable for testing IO exceptions in the controller.
 */
public class MockFailAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("IO failed");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("IO failed");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("IO failed");
  }

}
