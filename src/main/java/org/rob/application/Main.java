package org.rob.application;

import org.rob.application.filemanager.FileManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world! welcome to our file management program =)");

        FileManager fm = new FileManager();

        fm.echoFileTestInPath();

        System.out.println("The answer of calling the methon in main is "+ fm.currentPathIsEmpty());
    }
}