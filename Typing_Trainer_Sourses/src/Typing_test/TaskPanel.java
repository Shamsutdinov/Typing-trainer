package Typing_test;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TaskPanel extends JTextPane {

    private int caret_position;
    private boolean started = false;
    private String text = "Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать. Текст, который необходимо напечатать. Текст, который необходимо напечатать. "
            + "Текст, который необходимо напечатать.";

    public TaskPanel() throws BadLocationException {
        super();
        setEditable(false);

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                try {
                    if (started) {
                        String character = getText(caret_position, 1);
                        if (character.charAt(0) == (e.getKeyChar())) {
                            getDocument().remove(caret_position, 1);
                            appendToPane(caret_position, character, Color.BLACK);
                            caret_position++;
                            if (caret_position >= getDocument().getLength()) {
                                setText(null);
                                caret_position = 0;
                            }
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(TaskPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void appendToPane(int pos, String msg, Color c) throws BadLocationException {
        StyledDocument doc = getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        setCharacterAttributes(aset, false);
        doc.insertString(pos, msg, aset);
    }

    public int getCaret_position() {
        return caret_position;
    }

    public void clear() {
        caret_position = 0;
        setText(null);
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void resetText() throws BadLocationException {
        setText(null);
        this.appendToPane(0, text, Color.RED);
        caret_position = 0;
    }
}
