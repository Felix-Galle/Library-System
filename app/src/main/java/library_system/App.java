package library_system;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class App extends JFrame {

    private JPanel topBar;
    private JPanel contentPanel;
    private JButton loanButton;
    private JButton browseButton;
    private JButton profileButton;
    private CardLayout cardLayout;

    public App() {
        super("Main_Window");
        setDefaultLookAndFeelDecorated(true);
        setTitle("Main_Window");
        setSize(800, 600);
        setLocation(100, 100);
        setResizable(false);

        //try to set an app_icon
        /*
        try {
            // Set the app icon
            String currentDir = System.getProperty("user.dir");
            // Construct the relative path to the docs folder
            String folderPath = currentDir + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "library" + File.separator + "systemv07" + File.separator + "library_systemv07";
            ImageIcon icon = new ImageIcon(folderPath + "free-book-4573596-3802605.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error: Couldn't retrieve app_icon.ico");
        }*/

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the top bar
        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10)); // spacing and padding
        topBar.setBackground(new Color(114, 159, 207)); // #729FCF
        topBar.setPreferredSize(new Dimension(0, 80)); // height

        //Button settings
        loanButton = new JButton("Loan");
        loanButton.setBackground(new Color(42, 96, 153)); // #2A6099
        loanButton.setForeground(Color.WHITE);
        loanButton.setPreferredSize(new Dimension(200, 60)); // width and height
        loanButton.setFont(new Font("Arial", Font.BOLD, 32)); // font size
        loanButton.addActionListener((ActionEvent e) -> {
            showLoanPage();
        });
        topBar.add(loanButton);

        browseButton = new JButton("Browse");
        browseButton.setBackground(new Color(42, 96, 153)); // #2A6099
        browseButton.setForeground(Color.WHITE);
        browseButton.setPreferredSize(new Dimension(200, 60)); // width and height
        browseButton.setFont(new Font("Arial", Font.BOLD, 32)); // font size
        browseButton.addActionListener((ActionEvent e) -> {
            showBrowsePage();
        });
        topBar.add(browseButton);

        profileButton = new JButton("Profile");
        profileButton.setBackground(new Color(42, 96, 153)); // #2A6099
        profileButton.setForeground(Color.WHITE);
        profileButton.setPreferredSize(new Dimension(200, 60)); // width and height
        profileButton.setFont(new Font("Arial", Font.BOLD, 32)); // font size
        profileButton.addActionListener((ActionEvent e) -> {
            showProfilePage();
        });
        topBar.add(profileButton);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // Create the content panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Create a home page
        HomePage homePage = new HomePage(this);
        contentPanel.add(homePage.getHomePage(), "default");
        
        // Create a loan Page
        LoanPage loanPage = new LoanPage(this);
        contentPanel.add(loanPage.getLoanPage(), "loan");

        // Create a browse page
        BrowsePage browsePage = new BrowsePage(this);
        contentPanel.add(browsePage.getBrowsePage(), "browse");

        ProfilePage profilePage = new ProfilePage(this);
        contentPanel.add(profilePage.getProfilePage(), "profile");

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);



        //This section is for the shortcut keys
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "F3");
        getRootPane().getActionMap().put("F3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setExtendedState(JFrame.NORMAL);
                TerminalLog.showTerminalLog();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "F11");
        getRootPane().getActionMap().put("F11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.NORMAL);
                } else {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
                setAlwaysOnTop(true);
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "F9");
        getRootPane().getActionMap().put("F9", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TerminalLog.InfoLog("Pass Window opened");
                //Pass.showPass_win();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "F8");
        getRootPane().getActionMap().put("F8", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TerminalLog.InfoLog("Management Window opened");
                AllManagement.showAllManage();
            }
        });

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to be maximized
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Make the frame visible
        setVisible(true);

        // Show the home page by default
        cardLayout.show(contentPanel, "default");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }


    // These methods are required to get TerminalLog goto method to work (main)

/**
     * Public method to show a specific page by name.
     */
    public void showPage(String pageName) {
        cardLayout.show(contentPanel, pageName);
    }

    /**
     * Public method to show the home page.
     */
    public void showHomePage() {
        showPage("default");
    }

    /**
     * Public method to show the loan page.
     */
    public void showLoanPage() {
        showPage("loan");
    }

    /**
     * Public method to show the browse page.
     */
    public void showBrowsePage() {
        showPage("browse");
    }

    /**
     * Public method to show the profile page.
     */
    public void showProfilePage() {
        showPage("profile");
    }
}