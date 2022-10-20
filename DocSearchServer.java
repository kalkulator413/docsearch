import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileHelpers {
    static List<File> getFiles(Path start) throws IOException {
        File f = start.toFile();
        List<File> result = new ArrayList<>();
        if(f.isDirectory()) {
            // System.out.println("It's a folder");
            File[] paths = f.listFiles();
            for(File subFile: paths) {
                result.addAll(getFiles(subFile.toPath()));
            }
        }
        else {
            result.add(start.toFile());
        }
        return result;
    }
    static String readFile(File f) throws IOException {
        // System.out.println(f.toString());
        return new String(Files.readAllBytes(f.toPath()));
    }
}

class Handler implements URLHandler {
    List<File> files;
    Handler(String directory) throws IOException {
      this.files = FileHelpers.getFiles(Paths.get(directory));
    }
    public String handleRequest(URI url) throws IOException {
      if (url.getPath().equals("/")) {
            int size = 0;
            for (File f : this.files) {
                if (!f.isDirectory()) {
                    size += 1;
                }
            }
            return "There are " + size + " files to search.";
        } else {
            // /search?q=search-term
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("q")) {
                    String term = parameters[1];
                    String res = "";
                    int total = 0;
                    for (File f : files) {
                        if (f.isDirectory()) {
                            continue;
                        }
                        String contents = FileHelpers.readFile(f);
                        if (contents.contains(term)) {
                            total += 1;
                            res += f.getPath() + '\n';
                        }
                    }
                    return "There were " + total + " files found:\n" + res;
                }
            }
           
        }
        return "404 Not Found!";
    }
}

class DocSearchServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler("./technical/"));
    }
}

