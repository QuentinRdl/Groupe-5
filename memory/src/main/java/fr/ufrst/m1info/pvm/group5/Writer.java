package fr.ufrst.m1info.pvm.group5;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Class interacting with memory allowing to have IO from the memory.
 * The class takes the role of publisher in a publish-subscribe like pattern.
 */
public class Writer {
    private String _innerText;
    private ConcurrentLinkedQueue<Consumer<TextData>> _subscribers;

    /**
     * Types of events that can be triggered from this class
     */
    public enum TextChangeEvent{
        /**
         * Text has been written
         */
        TEXT_ADDED,
        /**
         * Text has been removed
         */
        TEXT_REMOVED
    }

    /**
     * Record of data of a text update
     * @param oldText State of the text before the update
     * @param newText State of the text before the update
     * @param diff Text added between new and old text.
     *             Is empty if text have been removed
     * @param nbAdded number of characters added by the update. Is negative if characters have been removed
     * @param change Type of change that occurred
     */
    public record TextData(String oldText, String newText, String diff, int nbAdded, TextChangeEvent change) {}

    public Writer(){
        _subscribers = new ConcurrentLinkedQueue<>();
        _innerText = "";
    }

    /**
     * Asynchronously triggers all the subscribers, giving them the inputted data
     * @param data data to call the subscribers with
     */
    private void onTextChangeAsync(TextData data){
        for(Consumer<TextData> c : _subscribers){
            CompletableFuture.runAsync(() -> c.accept(data));
        }
    }

    /**
     * Append text at the end of the text
     * @param text text to append
     */
    public void Write(String text){
        String oldText = _innerText;
        _innerText += text;
        onTextChangeAsync(new TextData(
                oldText,
                _innerText,
                text,
                text.length(),
                TextChangeEvent.TEXT_ADDED
        ));
    }

    /**
     * Append a line of text at the end of the text
     * @param line line of text to append
     */
    public void WriteLine(String line){
        String oldText = _innerText;
        _innerText += "\n" + line;
        onTextChangeAsync(new TextData(
                oldText,
                _innerText,
                "\n"+line,
                line.length() + 1,
                TextChangeEvent.TEXT_ADDED
        ));
    }

    /**
     * Erase a given number of characters at the end of the text
     * @param nbChars number of characters to erase
     */
    public void Erase(int nbChars){
        String oldText = _innerText;
        int erased;
        if(nbChars<_innerText.length()){ // Special case
            _innerText = "";
            erased = oldText.length();
        }
        else {
            _innerText = _innerText.substring(0, oldText.length() - nbChars);
            erased = nbChars;
        }
        onTextChangeAsync(new TextData(
                oldText,
                _innerText,
                "",
                -erased,
                TextChangeEvent.TEXT_REMOVED
        ));
    }

    /**
     * Erase the last line of the text
     */
    public void EraseLine(){
        String oldText = _innerText;
        int lineIndex = _innerText.lastIndexOf("\n");
        int removed = 0;
        if(lineIndex == -1){
            _innerText = "";
            removed = oldText.length();
        }
        else{
            _innerText = _innerText.substring(0, lineIndex);
            removed = oldText.length() - lineIndex;
        }
        onTextChangeAsync(new TextData(
                oldText,
                _innerText,
                "",
                -removed,
                TextChangeEvent.TEXT_REMOVED
        ));
    }

    /**
     * Add a subscriber to the writer.
     * The function is called when something is written or erased from the writer.
     * @param consumer function to call when the event is triggered
     */
    public void subscribe(Consumer<TextData> consumer){
        _subscribers.add(consumer);
    }
}
