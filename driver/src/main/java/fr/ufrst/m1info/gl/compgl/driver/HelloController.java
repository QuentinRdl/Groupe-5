package fr.ufrst.m1info.gl.compgl.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label fileLabel;

    @FXML
    private TextArea viewer;

    @FXML
    protected void selectFileButton() {
        int nbLines = 0;
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Fichier MiniJaja et JajaCode", "*.mjj", "*.jcc")
        );
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            fileLabel.setText(selectedFile.getName());
            //Read file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))){

                StringBuilder builder = new StringBuilder(); //concatenates strings in an optimised manner
                String line;
                while((line = bufferedReader.readLine()) != null){
                    builder.append(nbLines++).append(". \t").append(line).append("\n");
                }
                viewer.setText(builder.toString());

            } catch (IOException e){
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File is invalid");
        }
    }
}