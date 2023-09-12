package br.com.bertolucci.mtgtools.pngsvgtools.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CheckAndDeleteFileUtil {

    public static void checkAndDelete(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

}
