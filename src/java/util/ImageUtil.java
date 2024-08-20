package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ImageUtil {
    public static void uploadImage(String uploadPath, String filename, InputStream in) throws IOException {
        File file = new File(uploadPath, filename);
        Files.copy(in, file.toPath());
        in.close();
    }
    public static void deleteImage(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) return;
        file.delete();
    }
}
