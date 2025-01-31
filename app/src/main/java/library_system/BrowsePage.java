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
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BrowsePage {
    private JPanel browsePage;
    private JTextField searchField;
    private JButton goButton;
    private JButton fictionButton;
    private JButton genreButton;
    private JButton authorButton;
    private JButton formatButton;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BrowsePage(JFrame mainWindow) {
        browsePage = new JPanel();
        browsePage.setLayout(new BorderLayout());

        // Create the spacer bar
        JPanel spacerBar = new JPanel();
        spacerBar.setBackground(Color.decode("#ffafff"));
        spacerBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
        browsePage.add(spacerBar, BorderLayout.NORTH);

        // Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Create the search bar
        JPanel searchBar = new JPanel();
        searchBar.setBackground(Color.WHITE);
        searchBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));

        searchField = new JTextField("Search for books here", 20);
        searchField.setPreferredSize(new Dimension(1000, 30));
        searchField.setFont(new Font("Arial", Font.BOLD, 18));
        searchField.setBackground(Color.decode("#aaaaaa"));
        searchField.setForeground(Color.BLACK);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search for books here")) {
                    searchField.setText("");
                }
            }
        });

        goButton = new JButton("Go");
        goButton.setPreferredSize(new Dimension(100, 30));
        goButton.setFont(new Font("Arial", Font.BOLD, 18));
        goButton.setBackground(Color.decode("#729FCF"));
        goButton.setForeground(Color.BLACK);
        goButton.addActionListener((ActionEvent e) -> {
            searchBooks();
        });

        searchBar.add(searchField);
        searchBar.add(goButton);

        centerPanel.add(searchBar);

        // Create the filter bar
        JPanel filterBar = new JPanel();
        filterBar.setBackground(Color.WHITE);
        filterBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));

        fictionButton = new JButton("Fiction");
        genreButton = new JButton("Genre");
        authorButton = new JButton("Author");
        formatButton = new JButton("Format");

        setupFilterButton(fictionButton, "Fiction");
        setupFilterButton(genreButton, "Genre");
        setupFilterButton(authorButton, "Author");
        setupFilterButton(formatButton, "Paperback/Hardcover");

        filterBar.add(fictionButton);
        filterBar.add(genreButton);
        filterBar.add(authorButton);
        filterBar.add(formatButton);

        centerPanel.add(filterBar);

        // Create the book table
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Book ID", "Title", "Author", "Genre", "Type", "Actions"});
        bookTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };
        bookTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        bookTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        centerPanel.add(scrollPane);

        browsePage.add(centerPanel, BorderLayout.CENTER);

        // Load initial books
        loadBooks(Book.getBooks());

        // Create the books bar
        JPanel booksBar = new JPanel();
        booksBar.setBackground(Color.decode("#afffff"));
        booksBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));
        booksBar.setPreferredSize(new Dimension(0, 300));
        browsePage.add(booksBar, BorderLayout.SOUTH);
    }

    private void setupFilterButton(JButton button, String filterType) {
        button.setPreferredSize(new Dimension(200, 30));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.decode("#729FCF"));
        button.setForeground(Color.BLACK);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterBooks(filterType);
            }
        });
    }

    private void loadBooks(List<Book.BookInfo> books) {
        for (Book.BookInfo book : books) {
            tableModel.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getType(), "View"});
        }
    }

    private void searchBooks() {
        String query = searchField.getText();
        List<Book.BookInfo> filteredBooks = Book.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(query.toLowerCase())
                        || book.getGenre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        tableModel.setRowCount(0);
        loadBooks(filteredBooks);
    }

    private void filterBooks(String filterType) {
        List<Book.BookInfo> filteredBooks = Book.getBooks().stream()
                .filter(book -> {
                    switch (filterType) {
                        case "Fiction":
                            return book.getType().get(0);
                        case "Genre":
                            return book.getGenre().equals(filterType);
                        case "Author":
                            return book.getAuthor().equals(filterType);
                        case "Paperback/Hardcover":
                            return book.getType().get(1);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
        tableModel.setRowCount(0);
        loadBooks(filteredBooks);
    }

    public JPanel getBrowsePage() {
        return browsePage;
    }
}