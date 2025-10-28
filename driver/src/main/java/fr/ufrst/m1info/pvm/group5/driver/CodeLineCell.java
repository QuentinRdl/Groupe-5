package fr.ufrst.m1info.pvm.group5.driver;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * The CodeLineCell class defines how each CodeLine is displayed inside the ListView.
 * It is responsible for creating and updating the visual cells that contain code lines.
 * Each cell allows the user to view and edit the code for a specific line in the program.
 */
public class CodeLineCell extends ListCell<CodeLine> {
    private HBox container;
    private StackPane lineNumberContainer;
    private Label lineNumberLabel;
    private TextField codeField;
    private Circle breakpointCircle;

    private CodeLineCellListener listener;
    private boolean wasEmptyOnLastBackspace = false;

    /**
     * Creates a new CodeLineCell and initializes its layout.
     * The cell contains a label for the line number and a text field for the code.
     */
    public CodeLineCell(){
        super();

        lineNumberLabel = new Label();
        lineNumberLabel.setPrefWidth(50);
        lineNumberLabel.setAlignment(Pos.CENTER);
        lineNumberLabel.getStyleClass().add("line-number");

        breakpointCircle = new Circle(6);
        breakpointCircle.setFill(Color.web("#850606"));
        breakpointCircle.setVisible(false);

        lineNumberContainer = new StackPane();
        lineNumberContainer.setPrefWidth(50);
        lineNumberContainer.setAlignment(Pos.CENTER);
        lineNumberContainer.getStyleClass().add("line-number-container");
        lineNumberContainer.getChildren().addAll(breakpointCircle, lineNumberLabel);

        codeField = new TextField();
        codeField.getStyleClass().add("code-field");
        HBox.setHgrow(codeField, Priority.ALWAYS);

        // listens for changes in the text field to synchronise the changes
        codeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(getItem() != null){
                getItem().setCode(newValue);
            }
        });

        // if the Enter key is pressed, a new line is added to the list view
        // when Backspace is pressed, remove the line only if it's empty and was already empty before
        // this reproduces the typical IDE behavior when deleting empty lines
        codeField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && listener != null){
                listener.onEnterPressed(getItem());
            } else if(event.getCode() == KeyCode.BACK_SPACE && listener != null){
                String currentText = codeField.getText();
                int caretPos = codeField.getCaretPosition();

                System.out.println("BACKSPACE index "+ getIndex() +"- texte actuel: '" + currentText + "' | caret: " + caretPos + " | wasEmpty: " + wasEmptyOnLastBackspace);

                if (currentText.isEmpty() && caretPos == 0 && wasEmptyOnLastBackspace){
                    listener.onDeletePressed(getItem());
                    event.consume();
                    wasEmptyOnLastBackspace = false;
                }
                else if (currentText.isEmpty() && caretPos == 0){
                    wasEmptyOnLastBackspace = true;
                    event.consume();
                }
                else if (currentText.length() == 1 && caretPos == 1){
                    wasEmptyOnLastBackspace = false;
                }
                else {
                    wasEmptyOnLastBackspace = false;
                }

                /*
                if(codeField.getText().isEmpty() && codeField.getCaretPosition() == 0){
                    listener.onDeletePressed(getItem());
                }
                */
            }
        });


        container = new HBox();
        container.getStyleClass().add("code-line");
        container.getChildren().addAll(lineNumberContainer, codeField);

        lineNumberContainer.setOnMouseClicked(event -> handleBreakpointClick());
    }

    public Label getLineNumberLabel() {
        return lineNumberLabel;
    }

    public StackPane getLineNumberContainer(){
        return lineNumberContainer;
    }

    public TextField getCodeField(){
        return codeField;
    }

    private void handleBreakpointClick(){
        CodeLine item = getItem();
        if (item != null){
            item.setBreakpoint(!item.isBreakpoint());
            updateItem(item, false);
        }
    }

    public void setListener(CodeLineCellListener listener){
        this.listener = listener;
    }


    /**
     * Updates the content of this cell to display a specific CodeLine.
     * If the cell is empty or the provided item is null, the cell content is cleared.
     * Otherwise, it displays the line number and the corresponding code text.
     *
     * @param  item  the CodeLine object to display in this cell
     * @param empty true if the cell should be empty, false otherwise
     */
    @Override
    protected void updateItem(CodeLine item, boolean empty){
        super.updateItem(item, empty);

        if (empty || item == null){
            setGraphic(null);
        } else {
            codeField.setText(item.getCode());

            // Display breakpoint
            if (item.isBreakpoint()){
                lineNumberLabel.setVisible(false);
                breakpointCircle.setVisible(true);

            } else {
                lineNumberLabel.setText(String.valueOf(item.getLineNumber()));
                lineNumberLabel.setVisible(true);
                breakpointCircle.setVisible(false);
            }

            setGraphic(container);
        }
    }

    public void focusTextField(){
        if (codeField != null){
            codeField.requestFocus();
            Platform.runLater(() -> {
                codeField.end();
                if (codeField.getText().isEmpty()){
                    wasEmptyOnLastBackspace = true;
                }
            });

        }
    }


}
