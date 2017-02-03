package Typing_test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private final JButton start_button, stop_button;

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
        speed_label = new JLabel("0 ��������/���");
        time_label = new JLabel("�����: 0 ���");
        mistake_label = new JLabel("������: 0");
        start_button = new JButton("������");
        stop_button = new JButton("����������");
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
                task_panel.setTime((task_panel.getTime() + 1));
                time_label.setText("�����: " + (int) task_panel.getTime() + " ���");
                speed_label.setText((int) task_panel.getSpeed() + " ��������/���");

                if (task_panel.getTime() == 60) {
                    try {
                        task_panel.setStarted(false);
                        timer.stop();
                        task_panel.showInformationDialog();
                        task_panel.reset();
                    } catch (BadLocationException ex) {
                    }
                }
            }
        });

        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    task_panel.reset();
                    mistake_label.setText("������: " + task_panel.getMistakes_count());

                    task_panel.setStarted(true);
                    timer.start();
                    main_panel.requestFocus();
                } catch (BadLocationException ex) {
                }
            }
        });

        stop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    timer.stop();
                    if (task_panel.isStarted()) {
                        task_panel.showInformationDialog();
                    }
                    task_panel.setStarted(false);
                    task_panel.reset();
                } catch (BadLocationException ex) {
                }
            }
        });

        main_panel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                try {
                    task_panel.typeDown(e);
                    mistake_label.setText("������: " + task_panel.getMistakes_count());
                } catch (BadLocationException ex) {
                }
            }
        });

        top_panel.add(scrollpane);
        center_panel.add(start_button);
        center_panel.add(stop_button);
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
