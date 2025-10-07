package fr.ufrst.m1info.gl.compgl.driver;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
* The CodeLineCell class defines how each CodeLine is displayed inside the ListView.
* It is responsible for creating and updating the visual cells that contain code lines.
* Each cell allows the user to view and edit the code for a specific line in the program.
* */
public class CodeLineCell extends ListCell<CodeLine> {
    private HBox container;
    private Label lineNumberLabel;
    private TextField codeField;

    public CodeLineCell(){

    }

    @Override
    protected void updateItem(CodeLine item, boolean empty){

    }


}
