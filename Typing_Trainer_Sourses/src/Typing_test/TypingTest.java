package Typing_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private float time = 0;
    private TypeSpeedCaster speed_caster;
    private JOptionPane pane;

    public TypingTest() throws BadLocationException {
        super("Typing test");

        setSize(500, 400);

        speed_caster = new TypeSpeedCaster();
        pane = new JOptionPane();

        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setFocusable(true);

        top_panel = new JPanel(new FlowLayout());
        center_panel = new JPanel(new FlowLayout());
        south_panel = new JPanel();
        task_panel = new TaskPanel();
        speed_label = new JLabel("0 Символов/мин");
        time_label = new JLabel("0 Сек");
        mistake_label = new JLabel("Ошибок: 0");
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
                speed_caster.setTime((speed_caster.getTime() + 1));
                time_label.setText((int) speed_caster.getTime() + " Сек");
                speed_label.setText((int) speed_caster.getSpeed() + " Символов/мин");

                if (speed_caster.getTime() == 60) {
                    speed_caster.setStarted(false);
                    timer.stop();
                    pane.showMessageDialog(null, "Время: " + (int) speed_caster.getTime()
                            + System.lineSeparator() + "Скорость набора текста: "
                            + (int) speed_caster.getSpeed() + System.lineSeparator()
                            + "Ошибок: " + speed_caster.getMistake()
                    );
                    pane.requestFocus();
                    speed_caster.reset();
                }
            }
        });

        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    task_panel.resetText();
                    speed_caster.setStarted(true);
                    timer.start();
                    main_panel.requestFocus();
                } catch (BadLocationException ex) {
                }
            }
        });
        stop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                if (speed_caster.isStarted()) {
                    pane.showMessageDialog(null, "Время: " + (int) speed_caster.getTime()
                            + System.lineSeparator() + "Скорость набора текста: "
                            + (int) speed_caster.getSpeed() + System.lineSeparator()
                            + "Ошибок: " + speed_caster.getMistake()
                    );
                    pane.requestFocus();
                }
                speed_caster.setStarted(false);
                speed_caster.reset();
            }
        });

        main_panel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                try {
                    if (speed_caster.isStarted()) {
                        String character = task_panel.getText(speed_caster.getTyped_symbols(), 1);
                        if (character.charAt(0) == (e.getKeyChar())) {

                            task_panel.getDocument().remove(speed_caster.getTyped_symbols(), 1);
                            task_panel.appendToPane(speed_caster.getTyped_symbols(), character, Color.BLACK);
                            speed_caster.typedCorrect();

                            if (speed_caster.getTyped_symbols() >= task_panel.getDocument().getLength()) {
                                task_panel.resetText();

                            }
                        } else {
                            speed_caster.typedMistake();
                            mistake_label.setText("Ошибок: " + speed_caster.getMistake());
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(TypingTest.class.getName()).log(Level.SEVERE, null, ex);
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
