import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class SlideShow extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private JLabel descriptionLabel;
    private JButton nextButton, backButton;
    private JLabel linkLabel;
    private int currentIndex = 0;

    private final String[] imageFiles = {
        "/resources/Wellness_Bali.jpg",
        "/resources/Wellness_CostaRica.jpg",
        "/resources/Wellness_Iceland.jpg",
        "/resources/Wellness_Sedona.jpg",
        "/resources/Wellness_Tulum.jpg"
    };

    private final String[] descriptions = {
        "Bali – Experience peaceful yoga retreats and detox spa treatments in lush surroundings.",
        "Costa Rica – Rejuvenate with eco-conscious wellness resorts near rainforests and beaches.",
        "Iceland – Cleanse your spirit in geothermal spas and glacial landscapes under northern lights.",
        "Sedona – Find balance with red rock vortex hikes and energy healing retreats.",
        "Tulum – Detox in beachfront yoga sanctuaries and ancient Mayan wellness rituals."
    };

    private final String[] links = {
        "https://travel.com/bali-detox",
        "https://travel.com/costa-rica-wellness",
        "https://travel.com/iceland-spa-retreat",
        "https://travel.com/sedona-healing",
        "https://travel.com/tulum-yoga"
    };

    public SlideShow() {
        setTitle("Detox & Wellness Destinations");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        imageLabel = new JLabel("", JLabel.CENTER);
        descriptionLabel = new JLabel("", JLabel.CENTER);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        linkLabel = new JLabel("", JLabel.CENTER);
        linkLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(links[currentIndex]));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SlideShow.this, "Could not open link.");
                }
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.add(descriptionLabel);
        bottomPanel.add(linkLabel);
        bottomPanel.add(buttonPanel);

        add(imageLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateSlide();
    }

    private void updateSlide() {
        if (currentIndex >= 0 && currentIndex < imageFiles.length) {
            java.net.URL imgURL = getClass().getResource(imageFiles[currentIndex]);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(700, 450, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
                descriptionLabel.setText(descriptions[currentIndex]);
                linkLabel.setText("<html><a href='#'>" + links[currentIndex] + "</a></html>");
            } else {
                imageLabel.setIcon(null);
                descriptionLabel.setText("Image not found: " + imageFiles[currentIndex]);
                linkLabel.setText("");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            currentIndex = (currentIndex + 1) % imageFiles.length;
        } else if (e.getSource() == backButton) {
            currentIndex = (currentIndex - 1 + imageFiles.length) % imageFiles.length;
        }
        updateSlide();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SlideShow().setVisible(true));
    }
}