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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BrowsePage {
    private JPanel browsePage;
    private JTextField searchField;
    private JButton goButton;
    private JButton fictionButton;
    private JButton genreButton;
    private JButton authorButton;
    private JButton formatButton;
    private JPanel booksPanel;
    private JPanel bookDetailsPanel;
    private Book.BookInfo selectedBook;

    public BrowsePage(JFrame mainWindow) {
        browsePage = new JPanel();
        browsePage.setLayout(new BorderLayout());

        // Create the spacer bar
        JPanel spacerBar = new JPanel();
        spacerBar.setBackground(Color.decode("#ffafff"));
        spacerBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
        browsePage.add(spacerBar, BorderLayout.NORTH);

        // Create the center panel for search bar and filter buttons
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

        // Create the book display panel (books shown in horizontal layout)
        booksPanel = new JPanel();
        booksPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        centerPanel.add(booksPanel);

        browsePage.add(centerPanel, BorderLayout.CENTER);

        // Create the bottom panel for displaying book details (centered layout)
        bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // This centers the content horizontally
        bookDetailsPanel.setBackground(Color.decode("#f9f9f9"));
        bookDetailsPanel.setPreferredSize(new Dimension(0, 200));
        browsePage.add(bookDetailsPanel, BorderLayout.SOUTH);

        // Load initial books
        loadBooks(Book.getBooks());
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
        booksPanel.removeAll(); // Clear the panel before loading new books

        for (Book.BookInfo book : books) {
            JPanel bookPanel = new JPanel();
            bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            bookPanel.setPreferredSize(new Dimension(250, 120));

            JLabel titleLabel = new JLabel(book.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel authorLabel = new JLabel(book.getAuthor());
            authorLabel.setFont(new Font("Arial", Font.ITALIC, 12));

            JButton detailsButton = new JButton("View Details");
            detailsButton.setFont(new Font("Arial", Font.BOLD, 12));
            detailsButton.addActionListener((ActionEvent e) -> {
                showBookDetails(book);
            });

            bookPanel.add(titleLabel);
            bookPanel.add(authorLabel);
            bookPanel.add(detailsButton);
            booksPanel.add(bookPanel);
        }

        // Revalidate and repaint the panel to update the UI
        booksPanel.revalidate();
        booksPanel.repaint();
    }

    private void searchBooks() {
        String query = searchField.getText();
        List<Book.BookInfo> filteredBooks = Book.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(query.toLowerCase())
                        || book.getGenre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
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
        loadBooks(filteredBooks);
    }

    private void showBookDetails(Book.BookInfo book) {
    selectedBook = book;

    // Clear the current details panel
    bookDetailsPanel.removeAll();

    // Set the background color for better contrast
    bookDetailsPanel.setBackground(Color.decode("#f9f9f9"));

    // Add FlowLayout to center the components horizontally
    bookDetailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    // Display the book details (excluding the "Actions")
    JLabel genreLabel = new JLabel("Genre: " + book.getGenre());
    genreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    genreLabel.setForeground(Color.BLACK);

    JLabel bookIdLabel = new JLabel("Book Id:"+ (book.getBookID()));
    bookIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    bookIdLabel.setForeground(Color.BLACK);

    JLabel fictionNonFictionLabel = new JLabel("Fiction/Non-Fiction: " + (book.getType().get(0) ? "Fiction" : "Non-Fiction"));
    fictionNonFictionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    fictionNonFictionLabel.setForeground(Color.BLACK);

    JLabel paperbackHardcoverLabel = new JLabel("Paperback/Hardcover: " + (book.getType().get(1) ? "Paperback" : "Hardcover"));
    paperbackHardcoverLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    paperbackHardcoverLabel.setForeground(Color.BLACK);

    // Add spacing between each detail for better readability
    bookDetailsPanel.add(bookIdLabel);
    bookDetailsPanel.add(genreLabel);
    bookDetailsPanel.add(fictionNonFictionLabel);
    bookDetailsPanel.add(paperbackHardcoverLabel);

    // Add some vertical space between the text fields
    bookDetailsPanel.add(new JLabel(" "));

    // Revalidate and repaint the details panel to update the UI
    bookDetailsPanel.revalidate();
    bookDetailsPanel.repaint();
}

    public JPanel getBrowsePage() {
        return browsePage;
    }
}
