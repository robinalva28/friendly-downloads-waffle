package org.rob.application;

import lombok.Getter;
import org.rob.application.filemanager.FileManager;

import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class App {
    private FileManager fileManager;
    private List<Path> filesToMove;
    private List<Path> foldersToMove;

    public App() {
        this.fileManager = new FileManager();
        this.filesToMove = new ArrayList<>();
        this.foldersToMove = new ArrayList<>();
    }

    public void run() {

        try {
            //There are any files in the source path?
            if (fileManager.sourcePathIsEmpty()) {
                // If the answer is no, return
                return;
            }

            // If is yes, then set all paths in a hash with their section by format {"C:Downloads/anyVideo.mp4" "videos"}
            this.filesToMove.addAll(fileManager.getAllFilePaths());

            // Then move all files to their target path
            HashMap<Path, String> filesAndFormatMap = fileManager.filesAndFormatToHashMap(this.filesToMove);
            fileManager.moveAllFiles(filesAndFormatMap);

            // finally move all folders to software's folder
            this.foldersToMove.addAll(fileManager.getAllFoldersPaths());
            fileManager.moveAllFolders(foldersToMove);

        } catch (Exception e) {
            System.out.println("Exception cached in Run method: " + e.getMessage());
        }
    }
}
