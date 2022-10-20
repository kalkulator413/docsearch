import static org.junit.Assert.*;
import org.junit.*;
import java.net.URI;
import java.nio.file.Path;

public class DocSearchServerTest {

    @Test
    public static void unitTest() {
        Handler handler = new Handler("./");
        // int numFiles = FileHelpers.getFiles(new Path("/")).size();
        // assertEquals(handler.handleRequest(new URI('/'), "There are " + numFiles + " files to search."));

    }
}