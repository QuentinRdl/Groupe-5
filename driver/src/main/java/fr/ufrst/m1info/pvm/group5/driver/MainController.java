package fr.ufrst.m1info.pvm.group5.driver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * Controller class for managing interactions in the main application interface
 */
public class MainController {
    @FXML
    private Label fileLabel;

    @FXML
    private ListView<CodeLine> codeListView;

    @FXML
    private TextArea output;

    private ObservableList<CodeLine> codeLines;

    @FXML
    private SplitPane splitPane;

    private File currentFile;

    /**
     * Initializes the controller components after the FXML has been loaded.
     */
    @FXML
    public void initialize(){
        splitPane.setDividerPositions(0.75);
        splitPane.setOrientation(Orientation.VERTICAL);

        // initialize listView
        codeLines = FXCollections.observableArrayList();
        codeListView.setItems(codeLines);

        // allows ListView to know how to create its cells
        codeListView.setCellFactory(lv -> new CodeLineCell());

    }

    /**
     * This method is called when the user clicks the Open button
     * It opens a file chooser that allows the user to select a MiniJaja or JajaCode file
     */
    @FXML
    protected void selectFileButton() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("MiniJaja and JajaCode file", "*.mjj", "*.jcc")
        );
        File selectedFile = fc.showOpenDialog(splitPane.getScene().getWindow());
        loadFile(selectedFile);
    }


    /**
     * Load, display the file code line by line,
     * and update the ListView to display each numbered line
     *
     * @param selectedFile the file to be loaded
     * @return true if the file was successfully loaded, false otherwise
     */
    public boolean loadFile(File selectedFile){
        if(selectedFile == null){
            System.out.println("No file selected");
            return false;
        }

        if(!selectedFile.exists()){
            System.err.println("File doesn't exist : " + selectedFile.getName());
            return false;
        }

        try {
            // Delete old cells
            codeLines.clear();

            //Read file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))) {
                int nbLines = 1;
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    codeLines.add(new CodeLine(nbLines++, line));
                }
            }

            fileLabel.setText(selectedFile.getName());
            currentFile = selectedFile;

            if (output != null) {
                output.appendText("File loaded : " + selectedFile.getName() + "\n");
            }
            return true;

        } catch (IOException e){
            System.err.println("Error reading file : " + e.getMessage());
            if (output != null) {
                output.appendText("Error : " + e.getMessage() + "\n");
            }
            return false;
        }
    }

    /**
     * Returns the label that displays the name of the currently loaded file
     *
     * @return the label showing the selected file name
     */
    public Label getFileLabel(){
        return fileLabel;
    }

    /**
     * Returns the observable list containing all lines of code currently loaded
     *
     * @return an ObservableList of Codeline objects
     */
    public ObservableList<CodeLine> getCodeLines(){
        return codeLines;
    }

    /**
     * Returns the ListView component that display the lines of code in the user interface
     * @return the ListView showing CodeLine elements
     */
    public ListView<CodeLine> getCodeListView(){
        return codeListView;
    }

    public String getModifiedCode(){
        return codeLines.stream().map(CodeLine::getCode).collect(Collectors.joining("\n"));
    }

    public void saveButton(){
        if(currentFile != null){
            saveToFile(currentFile);
        } else {
            saveAsButton();
        }
    }

    public void saveAsButton(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file as");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter( "MiniJaja file", "*.mjj"),
                new ExtensionFilter("JajaCode file", "*.jcc")
        );

        // Default name if file exists
        if (currentFile != null){
            fc.setInitialFileName(currentFile.getName());
            fc.setInitialDirectory(currentFile.getParentFile());
        }

        File fileToSave = fc.showSaveDialog(splitPane.getScene().getWindow());
        if(fileToSave != null){
            saveToFile(fileToSave);
            currentFile = fileToSave;
            fileLabel.setText(currentFile.getName());
        }
    }

    private void saveToFile(File file){
        try {
            List<String> lines = codeLines.stream().map(CodeLine::getCode).toList();
            Files.write(file.toPath(), lines , StandardCharsets.UTF_8);

            if (output != null) {
                output.appendText("File saved : " + currentFile.getName() + "\n");
            }

        } catch (IOException e){
            System.err.println("Error during saving : " + e.getMessage());
            if (output != null) {
                output.appendText("Error saving file\n");
            }
        }
    }

}