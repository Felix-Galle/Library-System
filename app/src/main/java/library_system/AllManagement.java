package library_system;



import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AllManagement {
    public static JFrame userManageFrame;

    public static void showAllManage() {
        userManageFrame = new JFrame("Library Management Window");
        userManageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userManageFrame.setSize(800, 700);
        userManageFrame.setResizable(false);
        userManageFrame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("User");
        JMenuItem addUserItem = new JMenuItem("Add User");
        JMenuItem editUserItem = new JMenuItem("Edit User");
        JMenuItem removeUserItem = new JMenuItem("Remove User");
        userMenu.add(addUserItem);
        userMenu.add(editUserItem);
        userMenu.add(removeUserItem);

        JMenu bookMenu = new JMenu("Book");
        JMenuItem addBookItem = new JMenuItem("Add Book");
        JMenuItem removeBookItem = new JMenuItem("Remove Book");
        bookMenu.add(addBookItem);
        bookMenu.add(removeBookItem);

        menuBar.add(userMenu);
        menuBar.add(bookMenu);
        userManageFrame.setJMenuBar(menuBar);

        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.add(new JLabel("Please select an option from the menu", SwingConstants.CENTER));

        userManageFrame.add(centerPane, BorderLayout.CENTER);

        addUserItem.addActionListener(event -> addUserInputFields(centerPane));
        editUserItem.addActionListener(event -> editUserInputFields(centerPane));
        removeUserItem.addActionListener(event -> removeUserInputFields(centerPane));

        addBookItem.addActionListener(event -> addBookInputFields(centerPane));
        removeBookItem.addActionListener(event -> removeBookInputFields(centerPane));

        userManageFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    userManageFrame.dispose();
                }
            }
        });

        userManageFrame.setVisible(true);
    }

    private static void addUserInputFields(JPanel centerPane) {
        centerPane.removeAll();

        JLabel userIdLabel = new JLabel("User ID: (keycard)");
        JTextField userIdField = new JTextField();
        JLabel lnameLabel = new JLabel("Last Name: ");
        JTextField lnameField = new JTextField();
        JLabel fnameLabel = new JLabel("First Name:");
        JTextField fnameField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade: (Number)");
        JTextField gradeField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");

        centerPane.add(userIdLabel);
        centerPane.add(userIdField);
        centerPane.add(lnameLabel);
        centerPane.add(lnameField);
        centerPane.add(fnameLabel);
        centerPane.add(fnameField);
        centerPane.add(gradeLabel);
        centerPane.add(gradeField);
        centerPane.add(saveButton);
        centerPane.add(closeButton);

        saveButton.addActionListener(event -> {
            String userId = userIdField.getText();
            String lname = lnameField.getText();
            String fname = fnameField.getText();
            String grade = gradeField.getText();

            User.addUser(userId, lname, fname, grade);

            userIdField.setText("");
            lnameField.setText("");
            fnameField.setText("");
            gradeField.setText("");
        });

        closeButton.addActionListener(event -> centerPane.removeAll());
    }

    private static void editUserInputFields(JPanel centerPane) {
        centerPane.removeAll();

        // Get the list of users
        List<User.UserInfo> users = User.getUsers();

        // Create a list of user names to display in the JComboBox
        List<String> userNames = new ArrayList<>();
        for (User.UserInfo userInfo : users) {
            userNames.add(userInfo.getLname() + ", " + userInfo.getFname());
        }

        // Create a JComboBox to display the list of users
        JComboBox<String> userComboBox = new JComboBox<>(userNames.toArray(new String[0]));

        // Create the rest of the input fields
        JLabel lnameLabel = new JLabel("Last Name:");
        JTextField lnameField = new JTextField();
        JLabel fnameLabel = new JLabel("First Name: (Middle Names go here)");
        JTextField fnameField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");

        centerPane.add(userComboBox);
        centerPane.add(lnameLabel);
        centerPane.add(lnameField);
        centerPane.add(fnameLabel);
        centerPane.add(fnameField);
        centerPane.add(gradeLabel);
        centerPane.add(gradeField);
        centerPane.add(saveButton);
        centerPane.add(closeButton);

        // Add event handler to populate the input fields when a user is selected
        userComboBox.addActionListener(event -> {
            String selectedUser = (String) userComboBox.getSelectedItem();
            for (User.UserInfo userInfo : users) {
                if (selectedUser.equals(userInfo.getLname() + ", " + userInfo.getFname())) {
                    lnameField.setText(userInfo.getLname());
                    fnameField.setText(userInfo.getFname());
                    gradeField.setText(userInfo.getGrade());
                    break;
                }
            }
        });

        saveButton.addActionListener(event -> {
            String selectedUser = (String) userComboBox.getSelectedItem();
            String lname = lnameField.getText();
            String fname = fnameField.getText();
            String grade = gradeField.getText();

            for (User.UserInfo userInfo : users) {
                if (selectedUser.equals(userInfo.getLname() + ", " + userInfo.getFname())) {
                    User.editUser(userInfo.getFileName(), lname, fname, grade);
                    break;
                }
            }

            lnameField.setText("");
            fnameField.setText("");
            gradeField.setText("");
        });

        closeButton.addActionListener(event -> centerPane.removeAll());
    }

    private static void removeUserInputFields(JPanel centerPane) {
        centerPane.removeAll();

        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField();

        JButton removeButton = new JButton("Remove");
        JButton closeButton = new JButton("Close");

        centerPane.add(userIdLabel);
        centerPane.add(userIdField);
        centerPane.add(removeButton);
        centerPane.add(closeButton);

        removeButton.addActionListener(event -> {
            String userId = userIdField.getText();

            User.removeUser(userId);

            userIdField.setText("");
        });

        closeButton.addActionListener(event -> centerPane.removeAll());
    }

    private static void addBookInputFields(JPanel centerPane) {
        centerPane.removeAll();

        JLabel idLabel = new JLabel("Book ID:");
        JTextField idField = new JTextField();
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();
        JLabel typeLabel = new JLabel("Type: (Fiction/Non Fiction & Hardcover/Paperback)");
        JTextField typeField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea();

        JButton addButton = new JButton("Add Book");
        JButton closeButton = new JButton("Close");

        centerPane.add(idLabel);
        centerPane.add(idField);
        centerPane.add(titleLabel);
        centerPane.add(titleField);
        centerPane.add(authorLabel);
        centerPane.add(authorField);
        centerPane.add(genreLabel);
        centerPane.add(genreField);
        centerPane.add(typeLabel);
        centerPane.add(typeField);
        centerPane.add(descriptionLabel);
        centerPane.add(new JScrollPane(descriptionArea));
        centerPane.add(addButton);
        centerPane.add(closeButton);

        addButton.addActionListener(event -> {
            String bookID = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            List<Boolean> type = new ArrayList<>();
            for (String t : typeField.getText().split(",")) {
                if (t.trim().toLowerCase().equals("fiction")) {
                    type.add(true);
                } else if (t.trim().toLowerCase().equals("non fiction")) {
                    type.add(false);
                } else if (t.trim().toLowerCase().equals("hardcover")) {
                    type.add(true);
                } else if (t.trim().toLowerCase().equals("paperback")) {
                    type.add(false);
                }
            }
            String description = descriptionArea.getText();

            Book.addBook(bookID, title, author, genre, type, description);

            idField.setText("");
            titleField.setText("");
            authorField.setText("");
            genreField.setText("");
            typeField.setText("");
            descriptionArea.setText("");
        });

        closeButton.addActionListener(event -> centerPane.removeAll());
    }
        
    private static void removeBookInputFields(JPanel centerPane) {
        centerPane.removeAll();
    
        JLabel idLabel = new JLabel("Book ID:");
        JTextField idField = new JTextField();
    
        JButton removeButton = new JButton("Remove Book");
        JButton closeButton = new JButton("Close");
    
        centerPane.add(idLabel);
        centerPane.add(idField);
        centerPane.add(removeButton);
        centerPane.add(closeButton);
    
        removeButton.addActionListener(event -> {
            String id = idField.getText();
            Book.removeBook(id);
            idField.setText("");
        });
    
        closeButton.addActionListener(event -> centerPane.removeAll());
    }
}