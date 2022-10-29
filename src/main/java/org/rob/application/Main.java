package org.rob.application;

import org.rob.application.filemanager.FileManager;


public class Main {

    public static void main(String[] args) {
        try {

            FileManager fm = new FileManager();

            fm.echoFileTestInPath();

            //Verifying if the source folder is empty
            if (fm.currentPathIsEmpty()) {
                //sleep for 0.5 minutes
                Thread.sleep(30000);
                System.out.println("We're awake!");
            }
            //fm.moveAFolderToAnotherFolder("unaCarpeta", DOCUMENTS_PATH);

        } catch (Exception e) {
            System.out.println("Exception cached in Main class: " + e);
        }
    }
}