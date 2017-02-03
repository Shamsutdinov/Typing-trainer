package Typing_test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

public class TypingTest extends JFrame {

    private final JScrollPane scrollpane;
    private final JPanel main_panel, top_panel, center_panel, south_panel;
    private TaskPanel task_panel;
    private JLabel speed_label, time_label, mistake_label;
    private Timer timer;
    private final JButton start_button, stop_button, dowload_text_button;

    public TypingTest() throws BadLocationException {
        super("Typing test");

        setSize(500, 400);

        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setFocusable(true);

        top_panel = new JPanel(new FlowLayout());
        center_panel = new JPanel(new FlowLayout());
        south_panel = new JPanel();
        task_panel = new TaskPanel();
        speed_label = new JLabel("0 Символов/мин");
        time_label = new JLabel("Время: 0 Сек");
        mistake_label = new JLabel("Ошибок: 0");
        start_button = new JButton("Начать");
        stop_button = new JButton("Остановить");
        dowload_text_button = new JButton("Выбрать текст");
        scrollpane = new JScrollPane(task_panel);

        south_panel.setPreferredSize(new Dimension(300, 150));
        south_panel.setLayout(new BoxLayout(south_panel, BoxLayout.Y_AXIS));

        south_panel.setPreferredSize(new Dimension(300, 150));
        scrollpane.setPreferredSize(new Dimension(400, 150));
        start_button.setPreferredSize(new Dimension(100, 20));
        stop_button.setPreferredSize(new Dimension(105, 20));
        dowload_text_button.setPreferredSize(new Dimension(118, 20));

        timer = new Timer(1000, (ActionEvent e) -> {
            task_panel.setTime((task_panel.getTime() + 1));
            time_label.setText("Время: " + (int) task_panel.getTime() + " Сек");
            speed_label.setText((int) task_panel.getSpeed() + " Символов/мин");

            if (task_panel.getTime() == 60) {
                try {
                    task_panel.setStarted(false);
                    timer.stop();
                    task_panel.showInformationDialog();
                    task_panel.reset();
                } catch (BadLocationException ex) {
                }
            }
        });

        start_button.addActionListener((ActionEvent e) -> {
            try {
                task_panel.reset();
                mistake_label.setText("Ошибок: " + task_panel.getMistakes_count());

                task_panel.setStarted(true);
                timer.start();
                main_panel.requestFocus();
            } catch (BadLocationException ex) {
            }
        });

        stop_button.addActionListener((ActionEvent e) -> {
            try {
                timer.stop();
                if (task_panel.isStarted()) {
                    task_panel.showInformationDialog();
                    task_panel.reset();
                }
                task_panel.setStarted(false);

            } catch (BadLocationException ex) {
            }
        });

        dowload_text_button.addActionListener((ActionEvent e) -> {
            try {
                task_panel.selectText();
            } catch (UnsupportedEncodingException | FileNotFoundException | BadLocationException ex) {
            }
        });

        main_panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    task_panel.typeKey(e);
                    if (task_panel.textIsOutOfLength()) {
                        timer.stop();
                        task_panel.setStarted(false);
                        task_panel.showInformationDialog();
                        task_panel.reset();
                    }
                    mistake_label.setText("Ошибок: " + task_panel.getMistakes_count());
                } catch (BadLocationException ex) {
                }
            }
        });

        top_panel.add(scrollpane);
        center_panel.add(start_button);
        center_panel.add(stop_button);
        center_panel.add(dowload_text_button);
        south_panel.add(speed_label);
        south_panel.add(time_label);
        south_panel.add(mistake_label);

        main_panel.add(top_panel, "North");
        main_panel.add(center_panel, "Center");
        main_panel.add(south_panel, "South");

        setContentPane(main_panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) throws BadLocationException {
        TypingTest test = new TypingTest();
    }
}
