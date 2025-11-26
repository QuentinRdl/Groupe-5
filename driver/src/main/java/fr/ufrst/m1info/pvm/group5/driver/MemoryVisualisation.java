package fr.ufrst.m1info.pvm.group5.driver;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class MemoryVisualisation extends HBox {

    private VBox stackContainer;
    private VBox heapContainer;

    private TextArea stackView;
    private TextArea heapView;
    private Label stackLabel;
    private Label heapLabel;


    public MemoryVisualisation() {
        super(20);
        setPadding(new Insets(15));

        // Stack
        VBox stackSection = new VBox(10);

        stackLabel = new Label("Stack (empty)");
        stackLabel.setFont(Font.font("System", 14));
        stackLabel.setStyle("-fx-font-weight: bold;");

        stackContainer = new VBox(10);
        stackContainer.setPadding(new Insets(10));
        stackContainer.setStyle("-fx-background-color: #f5f2f2; -fx-border-color: #1a1a1a; -fx-border-radius: 6; -fx-background-radius: 6;");

        ScrollPane stackScrollPane = new ScrollPane(stackContainer);
        stackScrollPane.setFitToWidth(true);
        stackScrollPane.setFitToHeight(true);
        stackScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stackScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        stackView = new TextArea("(empty)");
        stackView.setEditable(false);
        stackView.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        stackView.setWrapText(false);
        VBox.setVgrow(stackView, Priority.ALWAYS);

        stackSection.getChildren().addAll(stackLabel, stackScrollPane);

        // Heap
        VBox heapSection = new VBox(10);

        heapLabel = new Label("Heap (empty)");
        heapLabel.setFont(Font.font("System", 14));
        heapLabel.setStyle("-fx-font-weight: bold;");

        heapContainer = new VBox(10);
        heapContainer.setPadding(new Insets(10));

        heapView = new TextArea("(empty)");
        heapView.setEditable(false);
        heapView.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        heapView.setWrapText(true);
        VBox.setVgrow(heapView, Priority.ALWAYS);

        heapSection.getChildren().addAll(heapLabel, heapContainer);

        getChildren().addAll(stackSection, heapSection);
    }

    /**
     * Updates the memory visualization with current state
     * @param memory array
     */
    public void updateMemory(String[] memory){
        if(memory == null || memory.length < 2) return;

        Platform.runLater(() -> {
            String heap = memory[0];
            String stack = memory[1];

            String stackExample = "Stack{scopeDepth=0, size=1, contents=\n" +
                    "  [0] arr_0 \tkind=VARIABLE \tdataType=INT \tvalue=Integer(1)\n" +
                    "  [1] alias_0\tkind=VARIABLE \tdataType=INT\tvalue=Integer(10)\n" +
                    "  [2] arr_1 \tkind=VARIABLE \tdataType=INT \tvalue=Integer(1)\n" +
                    "  [1] alias_1\tkind=VARIABLE \tdataType=INT\tvalue=Integer(10)\n" +
                    "}";

            String heapExample = "Heap(total=16, available=11)\n" +
                    "  ext@1 int@0 size=5 Allocated(INT) refs=1\n" +
                    "    bytes: [0, 0, 0, 0, 0]\n" +
                    "* ext@0 int@5 size=11 Free refs=0\n" +
                    "    bytes: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
            updateStack(stackExample);
            updateHeap(heapExample);

            if(heap != null && !heap.isEmpty()){
                heapLabel.setText("Heap");
                heapView.setText(heap);
            } else {
                heapLabel.setText("Heap (empty)");
                heapView.setText("(empty)");
            }

            if(stack != null && !stack.isEmpty()){
                stackLabel.setText("Stack");
                stackView.setText(stack);
            } else {
                stackLabel.setText("Stack ! (empty)");
                stackView.setText("(empty)");
            }

        });
    }

    private void updateStack(String stack){
        stackContainer.getChildren().clear();

        if(stack == null || stack.trim().isEmpty()){
            stackContainer.getChildren().add(new Label("Stack is empty"));
            return;
        }

        String[] lines = stack.split("\n");
        for(int i = 0; i < lines.length; i++){
            System.out.println(lines[i]);
        }

        int contentsIndex = -1;
        for(int i = 0; i < lines.length; i++){
            if(lines[i].contains("contents=")){
                contentsIndex = i;
                break;
            }
        }

        //TODO: modify to display in the console ?
        if(contentsIndex == -1){
            stackContainer.getChildren().add(new Label("Stack format error"));
            return;
        }

        if(isStackContentEmpty(lines, contentsIndex)){
            stackContainer.getChildren().add(new Label("Stack is empty"));
            return;
        }

        for(String line : lines){
            if(line.contains("kind=")){
                String name = extract(line, "]", "kind=");
                String kind = extract(line, "kind=", "dataType=");
                String type = extract(line, "dataType=", "value=");
                String value = extract(line, "value=", "");

                stackContainer.getChildren().addFirst(new StackBlockView(name, kind, type, value));
            }
        }
    }

    private void updateHeap(String heap){
        heapContainer.getChildren().clear();

        if(heap == null || heap.trim().isEmpty()){
            heapContainer.getChildren().add(new Label("Heap is empty"));
            return;
        }

        String[] lines = heap.split("\n");
        for(int i = 0; i < lines.length; i++){
            System.out.println(lines[i]);
        }

        // Ignore the first line (Heap(total=..., available=...))
        for(int i = 1; i < lines.length; i++){
            String line = lines[i].trim();
            if(line.isEmpty()) continue;

            if(line.contains("bytes:")){
                String bytes = line.replace("bytes:", "").trim();
                HeapBlockView lastBlock = (HeapBlockView) heapContainer.getChildren().getLast();
                lastBlock.setBytesLabel(bytes);
            } else {
                int address = extractInt(line, "ext@", "int@");
                int size = extractInt(line, "size=", (line.contains("Allocated") ? "Allocated" : "Free"));
                boolean allocated = line.contains("Allocated");
                int refs = extractInt(line, "refs=", "");

                heapContainer.getChildren().add(new HeapBlockView(address, size, allocated, refs, ""));
            }
        }
    }

    //TODO: make comments
    private String extract(String text, String start, String end){
        if(text == null || start == null || end == null) return "";
        
        int startIndex = text.indexOf(start);
        if(startIndex == -1) return "";

        startIndex += start.length();

        int endIndex = end.isEmpty() ? -1 : text.indexOf(end, startIndex);
        if(endIndex == -1) return text.substring(startIndex).trim();

        return text.substring(startIndex,endIndex).trim();
    }

    private int extractInt(String text, String start, String end){
        try{
            return Integer.parseInt(extract(text, start, end));
        }catch (Exception e){
            return 0;
        }
    }

    private boolean isStackContentEmpty(String[] lines, int contentLineIndex){
        for(int i = contentLineIndex + 1; i < lines.length; i++){
            String line = lines[i].trim();
            if(line.equals("}")){
                return true;
            }
            if(!line.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
