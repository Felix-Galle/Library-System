package library_system;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage {
    private JPanel homePage;
            
    public HomePage(JFrame main_window) {
        homePage = new JPanel();
        homePage.setLayout(new BorderLayout());

        // Create and set up the label for the title
        JLabel homeLabel = new JLabel("Library System");
        homeLabel.setHorizontalAlignment(JLabel.CENTER);
        homeLabel.setVerticalAlignment(JLabel.CENTER);
        homeLabel.setForeground(new Color(0xdddddd));
        homeLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 64));
        homePage.add(homeLabel, BorderLayout.CENTER);

        // Load the image
        ImageIcon imageIcon = new ImageIcon("src\\\\main\\\\resources\\\\appdata\\\\HomePage_book.png");
        // Create a JLabel to hold the image
        JLabel imageLabel = new JLabel(imageIcon);
        // Add the image label to the homePage panel
        homePage.add(imageLabel, BorderLayout.CENTER); // You can change the position as needed


        // Revalidate and repaint the panel
        homePage.revalidate();
        homePage.repaint();
    }

    public JPanel getHomePage() {
        return homePage;
    }
}