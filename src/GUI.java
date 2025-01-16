import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class GUI extends JComponent implements Runnable {
    JLabel timeLabel;
    JButton searchingButton;
    JButton refreshButton;
    JLabel weatherLabel;
    JLabel temperatureLabel;
    JLabel conditionLabel;
    JLabel conditionImageLable;
    JTextField locationTextField;

    public void updateStatus(CurrentWeatherStatus cws){
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
            URL url;
            try {
                url = new URL("https://" + weather[4]);
            } catch (MalformedURLException et) {
                throw new RuntimeException(et);
            }
            Image image;
            try {
                image = ImageIO.read(url).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            } catch (IOException et) {
                throw new RuntimeException(et);
            }
            conditionImageLable.setIcon(new ImageIcon(image));
            try {
                BufferedWriter file = new BufferedWriter(new FileWriter("lastlocation.txt"));
                file.write(locationTextField.getText());
                file.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == searchingButton || e.getSource() == refreshButton) {
                updateStatus(new CurrentWeatherStatus());
            }

        }
    };
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }
    public void run() {
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        locationTextField = new JTextField(10);
        locationTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        locationTextField.setMaximumSize(new Dimension(50,10));
        locationTextField.setOpaque(false);
        searchingButton = new JButton("Search");
        searchingButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        searchingButton.addActionListener(actionListener);
        searchingButton.setMaximumSize(new Dimension(20,10));
        searchingButton.setOpaque(false);
        searchingButton.setContentAreaFilled(false);
        searchingButton.setBorderPainted(false);
        Image image = null;
        try {
            image = ImageIO.read(new File("7022719.png")).getScaledInstance(15,15, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refreshButton = new JButton(new ImageIcon(image));
        refreshButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        refreshButton.addActionListener(actionListener);
        refreshButton.setMaximumSize(new Dimension(10,10));
        refreshButton.setOpaque(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setBorderPainted(false);
        conditionImageLable = new JLabel();
        conditionLabel = new JLabel("");
        conditionLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        temperatureLabel = new JLabel("");
        temperatureLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        weatherLabel = new JLabel("");
        weatherLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        JFrame frame = new JFrame();
        frame.setTitle("Current Weather");
        frame.setSize(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setResizable(true);
        frame.setVisible(true);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        frame.add(timeLabel,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridx = 1;
        frame.add(locationTextField,gbc);
        gbc.gridx = 2;
        frame.add(searchingButton,gbc);
        gbc.gridx = 3;
        gbc.weightx = 0;
        frame.add(refreshButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        frame.add(conditionImageLable,gbc);
        gbc.gridy = 2;
        frame.add(conditionLabel,gbc);
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.PAGE_END;
        frame.add(temperatureLabel,gbc);
        gbc.gridy = 4;
        frame.add(weatherLabel,gbc);
        frame.getContentPane().setBackground(Color.cyan);
        try {
            BufferedReader file = new BufferedReader(new FileReader("lastlocation.txt"));
            locationTextField.setText(file.readLine());
            updateStatus(new CurrentWeatherStatus());
        } catch (FileNotFoundException ignored){

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
