package Typing_test;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TaskPanel extends JTextPane {

    private String text = "Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать.";

    private int typed_symbols_count, mistakes_count;
    private float time;
    private boolean started;

    public TaskPanel() throws BadLocationException {
        super();
        started = false;
        setEditable(false);
        setFocusable(false);
    }

    public void insertText(int pos, String inserted_char, Color c) throws BadLocationException {
        StyledDocument doc = getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        setCharacterAttributes(aset, false);
        doc.insertString(pos, inserted_char, aset);
    }

    public String getSymbolAtCurrentPosintion() throws BadLocationException {
        return getText(typed_symbols_count, 1);
    }

    public void markCurrentSymbolAsTyped() throws BadLocationException {
        String character = getSymbolAtCurrentPosintion();
        getDocument().remove(typed_symbols_count, 1);
        insertText(typed_symbols_count, character, Color.BLACK);
    }

    public void clearText() {
        setText(null);
    }

    public void reset() throws BadLocationException {
        clearText();
        time = 0;
        typed_symbols_count = 0;
        mistakes_count = 0;
        insertText(0, text, Color.RED);
    }

    public float getSpeed() {
        return typed_symbols_count / (time / 60);
    }

    void typedCorrect() {
        typed_symbols_count++;
    }

    void typedMistake() {
        mistakes_count++;
    }

    public int getTyped_symbols_count() {
        return typed_symbols_count;
    }

    public void setTyped_symbols_count(int typed_symbols_count) {
        this.typed_symbols_count = typed_symbols_count;
    }

    public int getMistakes_count() {
        return mistakes_count;
    }

    public void setMistakes_count(int mistakes_count) {
        this.mistakes_count = mistakes_count;
    }

    boolean isStarted() {
        return started;
    }

    void setStarted(boolean b) {
        started = b;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void showInformationDialog() {
        JOptionPane.showMessageDialog(null, "Время: " + (int) time
                + System.lineSeparator() + "Скорость набора текста: "
                + (int) getSpeed() + System.lineSeparator()
                + "Ошибок: " + mistakes_count
        );
    }

    public boolean isTextOutOfLength() {
        return typed_symbols_count >= getDocument().getLength();
    }

    public void onKeyDown(KeyEvent e) throws BadLocationException {
        if (started) {
            if (getSymbolAtCurrentPosintion().charAt(0) == (e.getKeyChar())) {
                markCurrentSymbolAsTyped();
                typed_symbols_count++;
            } else {
                mistakes_count++;
            }
        }
    }

    public void setDownloadedText(String text) throws BadLocationException {
        this.text = text;
        reset();

    }
}
