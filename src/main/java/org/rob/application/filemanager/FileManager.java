package org.rob.application.filemanager;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.rob.common.utils.YamlReader;
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
import java.util.*;

@Getter
public class FileManager {

    //Init paths and file formats from yaml
    private YamlReader<org.rob.domain.Paths> paths = new YamlReader<>("applicationpaths.yaml", new Yaml(new Constructor(org.rob.domain.Paths.class)));
    private YamlReader<org.rob.domain.Formats> formats = new YamlReader<>("applicationformats.yaml", new Yaml(new Constructor(org.rob.domain.Formats.class)));
    private final List<String> DOC_FORMATS = formats.getResource().getDocumentsExt();
    private final List<String> IMG_FORMATS = formats.getResource().getImagesExt();
    private final List<String> VID_FORMATS = formats.getResource().getVideoExt();
    private final List<String> SOFT_FORMATS = formats.getResource().getSoftwareExt();
    private final List<String> MUSIC_FORMATS = formats.getResource().getMusicExt();

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
            System.out.println("Exception cached in getAllFilePaths(): " + e);
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
            filesFormat.put(file, split[split.length - 1]);
        }
        return filesFormat;
    }

    public void moveAllFiles(Map<Path, String> filesAndPath) {
        filesAndPath.forEach(this::selectFolderAndMove);
    }

    private void selectFolderAndMove(Path path, String format) {
        File fileToMove = new File(paths.getResource().getEntireSourcePath() + "/" + path.getFileName());

        if (MUSIC_FORMATS.contains(format.toLowerCase())) {
            moveFileToAnotherFolder(fileToMove, Path.of(paths.getResource().getMusicPath()));
            return;
        }
        if (DOC_FORMATS.contains(format.toLowerCase())) {
            moveFileToAnotherFolder(fileToMove, Path.of(paths.getResource().getDocumentsPath()));
            return;
        }

        if (VID_FORMATS.contains(format.toLowerCase())) {
            moveFileToAnotherFolder(fileToMove, Path.of(paths.getResource().getVideoPath()));
            return;
        }
        if (IMG_FORMATS.contains(format.toLowerCase())) {
            moveFileToAnotherFolder(fileToMove, Path.of(paths.getResource().getImagesPath()));
            return;
        }
        if (SOFT_FORMATS.contains(format.toLowerCase())) {
            moveFileToAnotherFolder(fileToMove, Path.of(paths.getResource().getSoftwarePath()));
        }
    }

    private void moveFileToAnotherFolder(File fileName, Path destPath) {
        try {
            File fileToMove = fileName;
            File targetPath = new File(destPath.toUri());

            if (fileToMove.isDirectory()) {
                throw new IOException("File " + fileToMove + " is not a File");
            }

            if (checkIfFileExists(new File(destPath + "/" + fileToMove.getName()))) {

                String[] split = fileToMove.toString().split("\\.");
                split[split.length - 1] = "-duplicated." + split[split.length - 1];
                String renamedString = Arrays.stream(split).reduce((a, b) -> a + b).get();

                File fileRenamed = new File(renamedString);

                if (fileToMove.renameTo(fileRenamed)) {
                    fileName = fileRenamed;
                }
            }

            FileUtils.moveFileToDirectory(fileName, targetPath, true);

        } catch (IOException e) {
            System.out.println("Exception cached in moveFileToAnotherFolder: " + e);
        }
    }

    public List<Path> getAllFoldersPaths() {
        Path dir = Paths.get(paths.getResource().getEntireSourcePath());
        List<Path> pathList = new ArrayList<>();
        File tempFile;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                tempFile = file.toFile();
                if (tempFile.isDirectory()) {
                    pathList.add(file);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception cached in getAllFilePaths(): " + e);
        }
        return pathList;
    }

    public void moveAllFolders(List<Path> folders) {
        folders.forEach(this::moveAFolderToAnotherFolder);
    }

    public void moveAFolderToAnotherFolder(Path folderSource) {
        try {

            if (!folderSource.toFile().isDirectory()) {
                throw new IOException("File " + folderSource + " is actually not a directory");
            }

            FileUtils.moveDirectory(folderSource.toFile(), new File(paths.getResource().getSoftwarePath() + "/" + folderSource.getFileName()));

        } catch (IOException e) {
            System.out.println("Exception cached in moveAFolderToAnotherFolder(): " + e);
        }
    }

    private boolean checkIfFileExists(File file) {
        return file.exists();
    }

}
