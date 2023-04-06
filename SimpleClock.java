//package SimpleClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class SimpleClock extends JFrame {

    Calendar calendar;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;

    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    String time;
    String day;
    String date;

    JButton switchFormatButton;
    JButton switchTimeZoneButton;

    boolean use24HourFormat;
    boolean useLocalTime;

    SimpleClock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Digital Clock");
        this.setLayout(new FlowLayout());
        this.setSize(500, 300);
        this.setResizable(false);

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.PAGE_AXIS));
        clockPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat=new SimpleDateFormat("EEEE");
        dateFormat=new SimpleDateFormat("dd MMMMM, yyyy");

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 59));
        timeLabel.setBackground(Color.WHITE);
        timeLabel.setOpaque(true);
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Ink Free",Font.BOLD,34));
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dateLabel=new JLabel();
        dateLabel.setFont(new Font("Ink Free",Font.BOLD,30));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clockPanel.add(timeLabel);
        clockPanel.add(Box.createRigidArea(new Dimension(0,15)));
        clockPanel.add(dayLabel);
        clockPanel.add(Box.createRigidArea(new Dimension(0,15)));
        clockPanel.add(dateLabel);

        switchFormatButton = new JButton("24Hr");
        switchFormatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                use24HourFormat = !use24HourFormat;
                switchFormatButton.setText(use24HourFormat ? "12Hr" : "24Hr");
            }
        });

        switchTimeZoneButton = new JButton("GMT");
        switchTimeZoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useLocalTime = !useLocalTime;
                switchTimeZoneButton.setText(useLocalTime ? "GMT" : "Local");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(switchFormatButton);
        buttonPanel.add(switchTimeZoneButton);

        this.add(clockPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.setVisible(true);

        setTimer();
    }

    public void setTimer() {
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    calendar = useLocalTime ? Calendar.getInstance() : Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    dayFormat.setTimeZone(useLocalTime ? TimeZone.getDefault() : TimeZone.getTimeZone("GMT"));
                    dateFormat.setTimeZone(useLocalTime ? TimeZone.getDefault() : TimeZone.getTimeZone("GMT"));
                    String hourFormat = use24HourFormat ? "HH" : "hh";
                    timeFormat = new SimpleDateFormat(hourFormat + ":mm:ss" + (use24HourFormat? "" : " a"));
                    timeFormat.setTimeZone(useLocalTime ? TimeZone.getDefault() : TimeZone.getTimeZone("GMT"));

                    time = timeFormat.format(calendar.getTime());
                    timeLabel.setText(time);

                    day = dayFormat.format(calendar.getTime());
                    dayLabel.setText(day);

                    date = dateFormat.format(calendar.getTime());
                    dateLabel.setText(date);

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        timerThread.start();
    }

    public static void main(String[] args) {
        new SimpleClock();
    }
}
