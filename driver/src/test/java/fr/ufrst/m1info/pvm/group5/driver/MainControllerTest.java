package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

public class MainControllerTest extends ApplicationTest {

    @TempDir
    Path tempDir;

    private MainController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ufrst/m1info/pvm/group5/driver/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        controller = loader.getController();

        stage.setScene(scene);
        stage.show();
    }


    private File createTestFile(String filename, String... lines) throws IOException {
        File testFile = tempDir.resolve(filename).toFile();

        try (FileWriter writer = new FileWriter(testFile)){
            for (String line : lines){
                writer.write(line + "\n");
            }
        }
        return testFile;
    }

    /*
     * Verifies that the label with id fileLabel displays "No files selected" when the application starts.
     */
    @Test
    public void testInitialState(){
        FxAssert.verifyThat("#fileLabel", LabeledMatchers.hasText("No file selected"));

        assertTrue(controller.getCodeLines().isEmpty(), "The ListView should be empty at the start");
    }

    @Test
    public void testButtonsAreVisible(){
        FxAssert.verifyThat("#btnOpen", isVisible());
        FxAssert.verifyThat("#btnSave", isVisible());
        FxAssert.verifyThat("#btnRun", isVisible());
        FxAssert.verifyThat("#btnCompile", isVisible());
        FxAssert.verifyThat("#btnRunCompile", isVisible());
    }

    @Test
    public void testLoadFileSuccess() throws IOException{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals("test.mjj", controller.getFileLabel().getText());
        assertEquals(2, controller.getCodeLines().size());
        assertEquals("int x = 10;", controller.getCodeLines().getFirst().getCode());
        assertEquals("x = x + 1;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testLoadFileNull(){
        interact(() -> {
            boolean success = controller.loadFile(null);
            assertFalse(success);
        });

        assertEquals("No file selected", controller.getFileLabel().getText());
        assertTrue(controller.getCodeLines().isEmpty());
    }

    @Test
    public void testLoadFileNoExistent(){
        File nonExistent = tempDir.resolve("does_not_exist.mjj").toFile();

        assertFalse(nonExistent.exists());

        interact(() -> {
            boolean success = controller.loadFile(nonExistent);
            assertFalse(success);
        });
    }

    @Test
    public void testLoadEmptyFile() throws IOException{
        File emptyFile = createTestFile("empty.mjj");

        assertTrue(emptyFile.exists());

        interact(() -> {
          boolean success = controller.loadFile(emptyFile);
          assertTrue(success);
        });

        assertEquals("empty.mjj", controller.getFileLabel().getText());
        assertTrue(controller.getCodeLines().isEmpty());
    }

    @Test
    public void testLoadMultipleLines() throws IOException{
        File multipleLines = createTestFile("multiple.mjj", "line 1", "line 2", "line 3", "line 4", "line 5");

        interact(() -> {
            boolean success = controller.loadFile(multipleLines);
            assertTrue(success);
        });

        assertEquals("multiple.mjj", controller.getFileLabel().getText());
        assertEquals(5, controller.getCodeLines().size());
        for (int i = 0; i < 5; i++){
            assertEquals(i + 1, controller.getCodeLines().get(i).getLineNumber());
            assertEquals("line " + (i + 1), controller.getCodeLines().get(i).getCode());
        }
    }

    @Test
    public void testLoadFileWithSpecialCharacter() throws IOException{
        File testFile = createTestFile("special.mjj", "String message = \"L'été est reposant !\";", "// Ceci est un commentaire");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals("special.mjj", controller.getFileLabel().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("String message = \"L'été est reposant !\";", controller.getCodeLines().getFirst().getCode());
        assertEquals("// Ceci est un commentaire", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testLoadMultipleFilesSuccessively() throws IOException{
        File firstFile = createTestFile("first.mjj", "int x = 1;", "int y = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(firstFile);
            assertTrue(success);
        });

        assertEquals("first.mjj", controller.getFileLabel().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("int x = 1;", controller.getCodeLines().getFirst().getCode());
        assertEquals("int y = x + 1;", controller.getCodeLines().get(1).getCode());

        File secondFile = createTestFile("second.mjj", "final int a = 2000;");

        interact(() -> {
            boolean success = controller.loadFile(secondFile);
            assertTrue(success);
        });

        assertEquals("second.mjj", controller.getFileLabel().getText());
        assertEquals(1,controller.getCodeLines().size());
        assertEquals("final int a = 2000;", controller.getCodeLines().getFirst().getCode());
    }

}
