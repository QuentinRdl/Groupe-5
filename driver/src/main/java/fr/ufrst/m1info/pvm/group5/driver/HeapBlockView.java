package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HeapBlockView extends VBox {
    private Label bytesLabel;
    public HeapBlockView(int address, int size, boolean allocated, int refs, String bytes){
        super();
        setPadding(new Insets(8));
        setSpacing(4);

        setStyle("-fx-background-color: #EBEFF9; -fx-border-color: #516DC9; -fx-border-radius: 6; -fx-background-radius: 6;");

        Label addressLabel = new Label("Block @" + address + (allocated ? " - ALLOCATED" : " - FREE"));
        addressLabel.setStyle("-fx-font-weight: bold;");

        Label sizeLabel = new Label("Size : " + size);

        Label refsLabel = new Label("Refs : " + refs);

        bytesLabel = new Label();
        bytesLabel.setWrapText(true);
        bytesLabel.setMaxWidth(260);

        getChildren().addAll(addressLabel, sizeLabel, refsLabel, bytesLabel);
    }

    public void setBytesLabel(String bytes){
        bytesLabel.setText("Bytes : " + bytes);
    }
}
