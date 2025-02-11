package library_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LoanPage {
    private JPanel loanPage;
    private JTextField borrowerIdField;
    private JTextField bookIdField;
    private JButton loanButton;
    private JButton returnButton;
    private JTable loanTable;
    private DefaultTableModel tableModel;

    public LoanPage(JFrame main_window) {
        loanPage = new JPanel();
        loanPage.setLayout(new BorderLayout());

        // Create the spacer bar
        JPanel spacerBar = new JPanel();
        spacerBar.setBackground(Color.decode("#ffafff"));
        spacerBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
        loanPage.add(spacerBar, BorderLayout.NORTH);

        // Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Create the loan bar
        JPanel loanBar = new JPanel();
        loanBar.setBackground(Color.WHITE);
        loanBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));

        borrowerIdField = new JTextField("Enter borrower ID", 20);
        borrowerIdField.setPreferredSize(new Dimension(1000, 30));
        borrowerIdField.setFont(new Font("Arial", Font.BOLD, 18));
        borrowerIdField.setBackground(Color.decode("#aaaaaa"));
        borrowerIdField.setForeground(Color.BLACK);

        borrowerIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (borrowerIdField.getText().equals("Enter borrower ID")) {
                    borrowerIdField.setText("");
                }
            }
        });

        bookIdField = new JTextField("Enter book ID", 20);
        bookIdField.setPreferredSize(new Dimension(1000, 30));
        bookIdField.setFont(new Font("Arial", Font.BOLD, 18));
        bookIdField.setBackground(Color.decode("#aaaaaa"));
        bookIdField.setForeground(Color.BLACK);

        bookIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bookIdField.getText().equals("Enter book ID")) {
                    bookIdField.setText("");
                }
            }
        });

        loanButton = new JButton("Loan Book");
        loanButton.setPreferredSize(new Dimension(100, 30));
        loanButton.setFont(new Font("Arial", Font.BOLD, 18));
        loanButton.setBackground(Color.decode("#729FCF"));
        loanButton.setForeground(Color.BLACK);
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loanBook();
            }
        });

        returnButton = new JButton("Return Book");
        returnButton.setPreferredSize(new Dimension(100, 30));
        returnButton.setFont(new Font("Arial", Font.BOLD, 18));
        returnButton.setBackground(Color.decode("#729FCF"));
        returnButton.setForeground(Color.BLACK);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        loanBar.add(borrowerIdField);
        loanBar.add(bookIdField);
        loanBar.add(loanButton);
        loanBar.add(returnButton);

        centerPanel.add(loanBar);

        // Create the loan table
        tableModel = new DefaultTableModel(new String[]{"Borrower ID", "Book ID", "Loan Date", "Return Date"}, 0);
        loanTable = new JTable(tableModel);
        loanTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        loanTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(loanTable);
        centerPanel.add(scrollPane);

        loanPage.add(centerPanel, BorderLayout.CENTER);

        // Create the books bar
        JPanel booksBar = new JPanel();
        booksBar.setBackground(Color.decode("#afffff"));
        booksBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));
        booksBar.setPreferredSize(new Dimension(0, 300));
        loanPage.add(booksBar, BorderLayout.SOUTH);
    }
    
    // Loaning method
    private void loanBook() {
        String borrowerId = borrowerIdField.getText();
        String bookId = bookIdField.getText();

        if (!borrowerId.isEmpty() && !bookId.isEmpty()) {
            // Find the user by ID
            User.UserInfo user = null;
            for (User.UserInfo u : User.getUsers()) {
                if (u.getUserID().equals(borrowerId)) {
                    user = u;
                    break;
                }
            }

            if (user == null) {
                JOptionPane.showMessageDialog(null, "User not found.");
                return;
            }

            // Check if the user has already borrowed 5 books
            if (user.getCur_books().size() >= 5) {
                JOptionPane.showMessageDialog(null, "User has already borrowed 5 books.");
                return;
            }

            // Check if the book is available
            Book book = Book.getBook(bookId); // Assuming Book.getBook() returns a Book object
            if (book == null) {
                JOptionPane.showMessageDialog(null, "Book not found.");
                return;
            }

            // Loan the book to the user
            book.loanBook(borrowerId); // Call to loanBook method in Book.java
            user.getCur_books().add(bookId); // Add book to the user's current loan list

            // Save changes to the user data
            User.editUser(borrowerId, user.getLname(), user.getFname(), user.getGrade());

            // Update the table to reflect the loan
            tableModel.addRow(new Object[]{borrowerId, bookId, "Loan Date", "Return Date"});

            JOptionPane.showMessageDialog(null, "Book loaned successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter both borrower ID and book ID");
        }
    }

    // Returning method
    private void returnBook() {
        String borrowerId = borrowerIdField.getText();
        String bookId = bookIdField.getText();

        if (!borrowerId.isEmpty() && !bookId.isEmpty()) {
            // Find the user by ID
            User.UserInfo user = null;
            for (User.UserInfo u : User.getUsers()) {
                if (u.getUserID().equals(borrowerId)) {
                    user = u;
                    break;
                }
            }

            if (user == null) {
                JOptionPane.showMessageDialog(null, "User not found.");
                return;
            }

            // Check if the user has borrowed the book
            if (!user.getCur_books().contains(bookId)) {
                JOptionPane.showMessageDialog(null, "This book was not borrowed by the user.");
                return;
            }

            // Return the book
            Book book = Book.getBook(bookId); // Assuming Book.getBook() returns a Book object
            if (book != null) {
                book.returnBook(borrowerId,bookId); // Call to returnBook method in Book.java
            }

            // Remove the book from the user's current loans
            user.getCur_books().remove(bookId);

            // Save changes to the user data
            User.editUser(borrowerId, user.getLname(), user.getFname(), user.getGrade());

            // Update the table to reflect the return
            tableModel.addRow(new Object[]{borrowerId, bookId, "Loan Date", "Return Date"});

            JOptionPane.showMessageDialog(null, "Book returned successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter both borrower ID and book ID");
        }
    }

    // Return method to App.java (i.e., JFrame mainWindow)
    public JPanel getLoanPage() {
        return loanPage;
    }
}
