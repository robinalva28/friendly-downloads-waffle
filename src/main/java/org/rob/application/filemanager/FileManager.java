package org.rob.application.filemanager;


import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Getter
public class FileManager {

    private static final String ENTIRE_SOURCE_PATH = "C:/Users/robin/Downloads/tests";
    private static final String MUSIC_PATH = "C:/Users/robin/Documents/teststarget/music";
    private static final String VIDEO_PATH = "C:/Users/robin/Documents/teststarget/videos";
    private static final String IMAGES_PATH = "C:/Users/robin/Documents/teststarget/images";
    private static final String DOCUMENTS_PATH = "C:/Users/robin/Documents/teststarget/documents";
    private static final String SOFTWARE_PATH = "C:/Users/robin/Documents/teststarget/software";

    //  Extensions definition
    private static final List<String> IMAGES_EXT = Arrays.asList("png", "jpeg", "jpg", "giff", "webp", "tiff", "psd", "raw", "bmp", "heif", "indd");
    private static final List<String> MUSIC_EXT = Arrays.asList("pmc", "wav", "mp3", "aiff", "aac", "ogg", "wma", "flac", "alac", "m3u");

    private static final List<String> DOCUMENTS_EXT = Arrays.asList("xml", "sfv", "rtf", "odt", "html", "swf", "pdf",
            "otf", "mid", "doc", "docx", "ppt", "pptx", "xls", "json", "txt", "flv", "css", "iso", "ai", "tsv", "log", "sqlite", "sql", "epub", "cbz");

    private static final List<String> VIDEO_EXT = Arrays.asList("vtt", "webm", "ogv", "md5", "mkv", "m4v", "mpeg", "mov", "mpeg4", "mpg",
            "mp4", "srt", "avi", "wmv", "h264", "264", "");

    private static final List<String> SOFTWARE_EXT = Arrays.asList("apk", "exe", "rar", "tar", "gz", "7z", "dmg", "ipa", "adf", "scr", "zip");


    private final Yaml yaml;
    private InputStream inputStream;
    private org.rob.domain.Paths paths;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public FileManager() {
        this.yaml = new Yaml(new Constructor(org.rob.domain.Paths.class));
        this.inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("applicationpaths.yaml");
        this.paths = yaml.load(inputStream);
    }

    public void echoFileTestInPath() {
        try {
            File testFile = new File(paths.getEntireSourcePath() + "/test.txt");
            FileUtils.touch(testFile);
        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }

    public boolean currentPathIsEmpty() {
        LOGGER.info("Verifying if there are any file in the source path...");
        Path dir = Paths.get(ENTIRE_SOURCE_PATH);
        int count = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                System.out.println("Current file in the path: " + file);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
        return count == 0;
    }

    public void moveAFolderToAnotherFolder(String folderName, String destPath) {
        try {
            File folderToMove = new File(paths.getEntireSourcePath() + "/" + folderName);
            File targetPath = new File(destPath + "/" + folderName);

            if (!folderToMove.isDirectory()) {
                throw new IOException("File "+folderToMove+" is actually not a valid directory");
            }

            FileUtils.moveDirectory(folderToMove, targetPath);

        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }


}
