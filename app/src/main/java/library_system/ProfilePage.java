package library_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * author galle_f24
 */
public class ProfilePage {
    private JPanel profilePage;
    private JPanel headerPanel;
    //private JLabel pic;
    private JPanel footerPanel;
    private JFrame profileFrame;

    public ProfilePage(JFrame mainWindow) {
        profilePage = new JPanel();
        profilePage.setLayout(new BorderLayout());

        // Create the spacer bar
        JPanel spacerBar = new JPanel();
        spacerBar.setBackground(Color.decode("#ffafff"));
        spacerBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
        profilePage.add(spacerBar, BorderLayout.NORTH);

        // Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel profileBar = new JPanel();
        profileBar.setBackground(Color.decode("#dddddd"));
        profileBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
/*
        try {
           BufferedImage profileImg = ImageIO.read(new File("./userdata/thong.png"));
           JLabel pic = new JLabel(new ImageIcon(profileImg));
        } catch (IOException e) {
            TerminalLog.ErrorLog(e.toString()); // Handle the exception (e.g., log the error or show a message)
        }

        profileBar.add(pic); */

        // Create the footer panel
        footerPanel = new JPanel();
        footerPanel.setBackground(Color.decode("#afffff"));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));
        

        centerPanel.add(profileBar);

        profilePage.add(centerPanel, BorderLayout.CENTER);
        profilePage.add(footerPanel, BorderLayout.SOUTH);
    }


    public JPanel getProfilePage() {
        return profilePage;
    }
    
}