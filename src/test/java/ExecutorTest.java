import br.com.biancarosa.producer.Executor;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ExecutorTest extends TestCase {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

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

    @Test
    public void testInputWithWrongNumberOfArgs(){
        String[] arr = new String[]{};
        exit.expectSystemExitWithStatus(1);
        Executor.main(arr);

        assertEquals("Passe como argumento o número de threads, o host do buffer e a porta\n", errContent.toString());
    }

    @Test
    public void testInputWithText() {
        String[] arr =  { "A" };

        exit.expectSystemExitWithStatus(1);

        Executor.main(arr);

        assertEquals("Número de threads precisa ser um número\n", errContent.toString());
    }

    @Test
    public void testInput(){
        String[] arr = { "0", "127.0.0.1", "9000"};

        Executor.main(arr);

        assertEquals("", errContent.toString());
    }


    @Test
    public void testPortIsNotANumber(){
        String[] arr = { "0", "127.0.0.1", "asassa"};

        Executor.main(arr);

        assertEquals("Porta precisa ser um número\n", errContent.toString());
    }
}
