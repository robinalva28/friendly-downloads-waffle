package org.rob.application.filemanager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private static final String ENTIRE_SOURCE_PATH = "C:/Users/robin/Downloads/tests";
    private static final String MUSIC_PATH = "C:/Users/robin/Documents/teststarget/music";
    private static final String VIDEO_PATH = "C:/Users/robin/Documents/teststarget/videos";
    private static final String IMAGES_PATH = "C:/Users/robin/Documents/teststarget/images";
    private static final String DOCUMENTS_PATH = "C:/Users/robin/Documents/teststarget/documents";
    private static final String SOFTWARE_PATH = "C:/Users/robin/Documents/teststarget/software";

    //  Extensions definition
    private static final List<String> IMAGES_EXT = Arrays.asList("png", "jpeg", "jpg", "giff", "webp", "tiff", "psd", "raw", "bmp", "heif", "indd");
    private static final List<String> MUSIC_EXT = Arrays.asList("pmc","wav","mp3","aiff","aac","ogg","wma","flac","alac","m3u");
    private static final List<String> DOCUMENTS_EXT = Arrays.asList("xml","sfv","zip","rtf","odt","html","swf","pdf",
            "otf","mid","doc","docx","ppt","pptx","xls","json","txt","flv","css","iso","ai","tsv");
    private static final List<String> VIDEO_EXT = Arrays.asList("vtt","webm","ogv","md5","mkv","m4v","mpeg","mov","mpeg4","mpg",
            "mp4","srt","avi","wmv","h264","264","");
    private static final List<String> SOFTWARE_EXT = Arrays.asList("apk","exe","rar","tar","gz","7z","dmg","ipa","adf","scr","");

    public FileManager() {
    }

    public void echoFileTestInPath() {
        try {
            File testFile = new File(ENTIRE_SOURCE_PATH + "/test.txt");
            FileUtils.touch(testFile);
        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }

    public boolean currentPathIsEmpty() {
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


}
