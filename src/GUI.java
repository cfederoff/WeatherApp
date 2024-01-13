import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GUI extends JComponent implements Runnable {
    JLabel timeLabel;
    JButton searchingButton;
    JLabel weatherLabel;
    JLabel temperatureLabel;
    JLabel conditionLabel;
    JLabel conditionImageLable;
    JTextField locationTextField;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            CurrentWeatherStatus cws = new CurrentWeatherStatus();
            if (e.getSource() == searchingButton) {
                String[] weather = cws.currentWeather(locationTextField.getText());
                if (weather[0].equals("connection")){
                    weatherLabel.setText("Connection Error");
                    temperatureLabel.setText("");
                    conditionLabel.setText("");
                    conditionImageLable.setIcon(null);
                } else if (weather[0].equals("failed")){
                    weatherLabel.setText("Invalid Location");
                    temperatureLabel.setText("");
                    conditionLabel.setText("");
                    conditionImageLable.setIcon(null);
                } else {
                    weatherLabel.setText(weather[0]);
                    timeLabel.setText(weather[1]);
                    temperatureLabel.setText(weather[2] + "°F");
                    conditionLabel.setText(weather[3]);
                    URL url = null;
                    try {
                        url = new URL("https://"+weather[4]);
                    } catch (MalformedURLException et) {
                        throw new RuntimeException(et);
                    }
                    Image image = null;
                    try {
                        image = ImageIO.read(url).getScaledInstance(150,150, Image.SCALE_SMOOTH);
                    } catch (IOException et) {
                        throw new RuntimeException(et);
                    }
                    conditionImageLable.setIcon(new ImageIcon(image));
                }
            }
        }
    };
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }
    public void run() {
        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setBounds(5,5,75,30);
        JPanel topPanel = new JPanel();
        topPanel.setBounds(70,0,150,35);
        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setBounds(220,0,75,30);
        JPanel temperaturePanel = new JPanel();
        temperaturePanel.setBounds(75,230,150,40);
        JPanel conditonPanel = new JPanel();
        conditonPanel.setBounds(75,185,150,40);
        JPanel condtionImagePanel = new JPanel();
        condtionImagePanel.setBounds(75,35,150,150);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0,270,300,60);
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        leftTopPanel.add(timeLabel);
        locationTextField = new JTextField(10);
        locationTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        topPanel.add(locationTextField);
        searchingButton = new JButton("Search");
        searchingButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        searchingButton.addActionListener(actionListener);
        rightTopPanel.add(searchingButton);
        conditionImageLable = new JLabel();
        condtionImagePanel.add(conditionImageLable);
        conditionLabel = new JLabel("");
        conditionLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        conditonPanel.add(conditionLabel);
        temperatureLabel = new JLabel("");
        temperatureLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        temperaturePanel.add(temperatureLabel);
        weatherLabel = new JLabel("");
        weatherLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        bottomPanel.add(weatherLabel);
        JFrame frame = new JFrame();
        frame.setTitle("Current Weather");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setSize(300,350);
        frame.setVisible(true);
        frame.add(leftTopPanel);
        frame.add(rightTopPanel);
        frame.add(topPanel);
        frame.add(conditonPanel);
        frame.add(condtionImagePanel);
        frame.add(temperaturePanel);
        frame.add(bottomPanel);
    }
}
