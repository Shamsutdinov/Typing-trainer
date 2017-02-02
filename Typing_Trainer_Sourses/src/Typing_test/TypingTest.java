package Typing_test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JPanel panel, top_panel, center_panel, south_panel;
    private TaskPanel task_panel;
    private JLabel speed_label, time_label;
    private Timer timer;
    private final JButton start_button, stop_button;
    private float time = 0;

    public TypingTest() throws BadLocationException {
        super("Typing test");

        setSize(500, 400);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        top_panel = new JPanel(new FlowLayout());
        center_panel = new JPanel(new FlowLayout());
        south_panel = new JPanel();
        task_panel = new TaskPanel();
        speed_label = new JLabel("0 символов/мин");
        time_label = new JLabel("0 сек");
        start_button = new JButton("Начать");
        stop_button = new JButton("Остановить");
        scrollpane = new JScrollPane(task_panel);

        south_panel.setPreferredSize(new Dimension(300, 150));
        south_panel.setLayout(new BoxLayout(south_panel, BoxLayout.Y_AXIS));

        south_panel.setPreferredSize(new Dimension(300, 150));
        scrollpane.setPreferredSize(new Dimension(400, 150));
        start_button.setPreferredSize(new Dimension(100, 20));
        stop_button.setPreferredSize(new Dimension(105, 20));

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += 1;
                time_label.setText(time + " сек");
                speed_label.setText((int) (task_panel.getCaret_position() / (time / 60)) + " символов/мин");

                if (time == 60) {
                    task_panel.setStarted(false);
                    timer.stop();
                    time = 0;
                }
            }
        });

        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    task_panel.resetText();
                    task_panel.setStarted(true);
                    timer.start();
                } catch (BadLocationException ex) {
                }
            }
        });
        stop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task_panel.setStarted(false);
                timer.stop();
                time = 0;
            }
        });

        top_panel.add(scrollpane);
        center_panel.add(start_button);
        center_panel.add(stop_button);
        south_panel.add(speed_label);
        south_panel.add(time_label);

        panel.add(top_panel, "North");
        panel.add(center_panel, "Center");
        panel.add(south_panel, "South");

        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) throws BadLocationException {
        TypingTest test = new TypingTest();
    }
}
