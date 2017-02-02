package Typing_test;

import java.awt.Color;
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

    public TaskPanel() throws BadLocationException {
        super();
        setEditable(false);
    }

    public void appendToPane(int pos, String msg, Color c) throws BadLocationException {
        StyledDocument doc = getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        setCharacterAttributes(aset, false);
        doc.insertString(pos, msg, aset);
    }

    public void clear() {
        setText(null);
    }

    public void resetText() throws BadLocationException {
        setText(null);
        this.appendToPane(0, text, Color.RED);
    }
}
