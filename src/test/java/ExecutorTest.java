import br.com.biancarosa.producer.Executor;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ExecutorTest extends TestCase {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Override
    public void setUp() throws Exception {
        super.setUp();

        System.setErr(new PrintStream(errContent));
        System.setOut(new PrintStream(outContent));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }

    public void testInputWithWrongNumberOfArgs(){
        String[] arr = new String[]{};

        Executor.main(arr);

        assertEquals("Wrong number of args provided\n", errContent.toString());
    }

    public void testInputWithText() {
        String[] arr =  { "A" };

        Executor.main(arr);

        assertEquals("Number of threads must be a number\n", errContent.toString());
    }


    public void testInputWithNumber(){
        String[] arr = { "1" };

        Executor.main(arr);

        assertEquals("", errContent.toString());
    }

}
