package org.rob.application.filemanager;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.rob.adapter.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class FileManager {

    //Init paths and file formats from yaml
    private YamlReader<org.rob.domain.Paths> paths = new YamlReader<>("applicationpaths.yaml", new Yaml(new Constructor(org.rob.domain.Paths.class)));
    private YamlReader<org.rob.domain.Formats> formats = new YamlReader<>("applicationformats.yaml", new Yaml(new Constructor(org.rob.domain.Formats.class)));

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public FileManager() {
    }

    public void echoFileTestInPath() {
        try {
            File testFile = new File(this.paths.getResource().getEntireSourcePath() + "/test.txt");
            FileUtils.touch(testFile);
        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }

    public boolean sourcePathIsEmpty() throws IOException {
        Path dir = Paths.get(paths.getResource().getEntireSourcePath());
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) {
            return !dirStream.iterator().hasNext();
        }
    }

    public List<Path> getAllFilePaths() {
        LOGGER.info("Verifying all files source path...");
        Path dir = Paths.get(paths.getResource().getEntireSourcePath());
        List<Path> pathList = new ArrayList<>();
        File tempFile;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                tempFile = file.toFile();
                if (!tempFile.isDirectory()) {
                    pathList.add(file);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
        return pathList;
    }

    public HashMap<Path, String> filesAndFormatToHashMap(List<Path> pathList) throws IOException {
        if (pathList.isEmpty()) {
            return new HashMap<>();
        }

        HashMap<Path, String> filesFormat = new HashMap<>();
        for (Path file : pathList) {

            if (file.toFile().isDirectory()) {
                throw new IOException("File " + file + " is not a File");
            }

            String[] split = file.getFileName().toString().split("\\.");
            filesFormat.put(file, split[split.length-1]);
            //System.out.println("format: " + filesFormat);

            //fileManager.moveFileToAnotherFolder(file.toFile(),);
        }
        return filesFormat;
    }

    public void moveAllFiles(Map filesAndPath){


    }

    private void moveFileToAnotherFolder(File fileName, File destPath) {
        try {
            File fileToMove = new File(paths.getResource().getEntireSourcePath() + "/" + fileName);
            File targetPath = new File(destPath + "/" + fileName);

            if (fileToMove.isDirectory()) {
                throw new IOException("File " + fileToMove + " is not a File");
            }

            FileUtils.moveFileToDirectory(fileToMove, targetPath, true);

        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }

    public void moveAFolderToAnotherFolder(String folderNam, String destPath) {
        try {
            File folderToMove = new File(paths.getResource().getEntireSourcePath() + "/" + folderNam);
            File targetPath = new File(destPath + "/" + folderNam);

            if (!folderToMove.isDirectory()) {
                throw new IOException("File " + folderToMove + " is actually not a valid directory");
            }

            FileUtils.moveDirectory(folderToMove, targetPath);

        } catch (IOException e) {
            System.out.println("Exception cached: " + e);
        }
    }


}
