import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JComponent implements Runnable {
    JButton searchingButton;
    JLabel weatherLabel;
    JTextField locationTextField;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            CurrentWeatherStatus cws = new CurrentWeatherStatus();
            if (e.getSource() == searchingButton) {
                String[] weather = cws.currentWeather(locationTextField.getText());
                if (weather[0].equals("connection")){
                    weatherLabel.setText("Connection Error");
                } else if (weather[0].equals("failed")){
                    weatherLabel.setText("Invalid Location");
                } else {
                    weatherLabel.setText("In " + weather[0] + ", the temperature in F is " + weather[1] +
                            " and the condition outside is " + weather[2]);
                }
            }
        }
    };
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }
    public void run() {
        JPanel panel = new JPanel();
        weatherLabel = new JLabel("Test");
        weatherLabel.setText("Name a Location");
        weatherLabel.setVerticalAlignment(JLabel.TOP);
        weatherLabel.setHorizontalAlignment(JLabel.CENTER);
        locationTextField = new JTextField(15);
        locationTextField.setText("Enter Location");
        JFrame frame = new JFrame();
        searchingButton = new JButton("Search");
        searchingButton.addActionListener(actionListener);
        Container content = frame.getContentPane();
        content.add(panel,BorderLayout.SOUTH);
        frame.add(weatherLabel);
        panel.add(locationTextField);
        panel.add(searchingButton);
        frame.setTitle("Current Weather");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(550,100);
        frame.setVisible(true);
    }
}
