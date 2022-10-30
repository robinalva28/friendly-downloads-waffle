package org.rob.application;

import lombok.Getter;
import org.rob.application.filemanager.FileManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class App {
    private final int SLEEP_TIME = 5000;
    private FileManager fileManager;
    private List<Path> filesToMove;

    public App() {
        this.fileManager = new FileManager();
        this.filesToMove = new ArrayList<>();
    }

    public void run() throws IOException, InterruptedException {
        //There are any files in the source path?
        if (fileManager.sourcePathIsEmpty()) {

            // If the answer is no, wait for X time
            Thread.sleep(SLEEP_TIME);
            System.out.println("We're awake!");
        }

        // If is yes, then set all paths in a hash with their section by format {"C:Downloads/anyVideo.mp4" "videos"}
        this.filesToMove.addAll(fileManager.getAllFilePaths());

        // Then move all files to their target path
        HashMap<Path, String> filesAndFormatMap = fileManager.filesAndFormatToHashMap(this.filesToMove);
        //fileManager.moveAllFiles(filesAndFormatMap);
        System.out.println(filesAndFormatMap);
        // finally move all folders to software's folder

    }
}
