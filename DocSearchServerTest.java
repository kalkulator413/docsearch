import static org.junit.Assert.*;
import org.junit.*;
import java.net.URI;
import java.nio.file.Path;
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class DocSearchServerTest {

    @Test
    public void unitTest() throws IOException, URISyntaxException{
        Handler h = new Handler("./");
        String s1 = h.handleRequest(new URI(""));
        assertEquals(s1, "404 Not Found!");

        String s2 = h.handleRequest(new URI("/"));
        List<File> files = FileHelpers.getFiles(Paths.get("."));
        int size = 0;
        for (File f : files) {
            if (!f.isDirectory()) {
                size += 1;
            }
        }
        assertEquals(s2, "There are " + size + " files to search.");
    }
}