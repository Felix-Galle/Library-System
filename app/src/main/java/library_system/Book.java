package library_system;



    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.nio.file.DirectoryStream;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.LinkedList;
    import java.util.List;

    import javax.swing.JOptionPane;

    public class Book {
        private String bookid;
        private String title;
        private String author;
        private String genre;
        private List<Boolean> type;
        private String description;
        private String curBorrower;
        private LinkedList<String> allBorrowers;

        public Book(String bookid, String title, String author, String genre, List<Boolean> type, String description, String CurBorrower, LinkedList<String> allBorrowers) {
            this.bookid = bookid;
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.type = type;
            this.description = description;
            this.curBorrower = CurBorrower;
            this.allBorrowers = new LinkedList<>();
        }

        public static void addBook(String bookid, String title, String author, String genre, List<Boolean> type, String description) {
            // Create a new file for the book
            Path bookDataDir = Paths.get("data", "bookdata", bookid + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookDataDir.toFile()))) {
                writer.write("title=\"" + title + "\"\n");
                writer.write("author=\"" + author + "\"\n");
                writer.write("genre=\"" + genre + "\"\n");
                writer.write("type=[");
                for (int i = 0; i < type.size(); i++) {
                    writer.write(type.get(i).toString());
                    if (i < type.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("]\n");
                writer.write("description=\"" + description + "\"\n");
                writer.write("cur_borrower=\"\"\n");
                writer.write("all_borrower=[\"\"]\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
            }
        }
        // Output: Creates a new book file with the given details.
        // Input: bookid (String), title (String), author (String), genre (String), type (List<Boolean>), description (String)


        public static void removeBook(String id) {
            // Delete the file for the book
            Path bookDataDir = Paths.get("data", "bookdata", id + ".txt");
            try {
                Files.delete(bookDataDir);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error removing book: " + e.getMessage());
            }
        }
        // Output: Deletes the book file with the given ID.
        // Input: id (String)


        public static List<BookInfo> getBooks() {
            List<BookInfo> books = new ArrayList<>();
            Path bookDataDir = Paths.get("data", "bookdata");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(bookDataDir)) {
                for (Path filePath : stream) {
                    if (Files.isRegularFile(filePath)) {
                        String bookId = filePath.getFileName().toString().split("\\.")[0];
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                            String line;
                            String title = null;
                            String author = null;
                            String genre = null;
                            List<Boolean> type = new ArrayList<>();
                            String description = null;
                            String cur_borrower = null;
                            LinkedList<String> all_borrower = null;
                            while ((line = reader.readLine()) != null) {
                                if (line.startsWith("title=\"")) {
                                    title = line.substring(7, line.length() - 1);
                                } else if (line.startsWith("author=")) {
                                    author = line.substring(7, line.length() - 1);
                                } else if (line.startsWith("genre=")) {
                                    genre = line.substring(6, line.length() - 1);
                                } else if (line.startsWith("type=[")) {
                                    String[] typeStr = line.substring(6, line.length() - 1).split(",");
                                    for (String s : typeStr) {
                                        type.add(Boolean.parseBoolean(s.trim()));
                                    }
                                } else if (line.startsWith("description=")) {
                                    description = line.substring(12, line.length() - 1);
                                } else if (line.startsWith("cur_borrower=")) {
                                    cur_borrower = line.substring(12, line.length() - 1);
                                } else if (line.startsWith("all_borrower=")) {
                                    all_borrower = new LinkedList<>();
                                }
                                if (title != null && author != null && genre != null && type != null && description != null && cur_borrower != null && all_borrower != null) {
                                    break;
                                }
                            }
                            books.add(new BookInfo(bookId, title, author, genre, type, description, cur_borrower, all_borrower));
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading book data: " + e.getMessage());
            }
            return books;
        }
            // Output: Returns a list of all book information.
        // Input: None
        
        public static Book getBook(String bookID) {
            List<BookInfo> books = Book.getBooks();
            for (BookInfo book : books) {
                if (book.getBookID().equals(bookID)) {
                    return new Book(book.getBookID(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getType(), book.getDescription(), book.getCur_borrower(), book.getAll_borrowers());
                }
            }
            return null;
        }
        // Output: Returns the book object with the given ID.
        // Input: bookID (String)

        public static void editBook(String bookid, String newTitle, String author, String genre, List<Boolean> type, String description) {
            // Read the book file
            Path bookDataDir = Paths.get("data", "bookdata", bookid + ".txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(bookDataDir.toString()))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("title=\"")) {
                        content.append("title=\"").append(newTitle).append("\"\n");
                    } else if (line.startsWith("author=")) {
                        content.append("author=\"").append(author).append("\"\n");
                    } else if (line.startsWith("genre=")) {
                        content.append("genre=\"").append(genre).append("\"\n");
                    } else if (line.startsWith("type=[")) {
                        content.append("type=[");
                        for (Boolean b : type) {
                            content.append(b.toString()).append(",");
                        }
                        content.deleteCharAt(content.length() - 1); // remove the last comma
                        content.append("]\n");
                    } else if (line.startsWith("description=")) {
                        content.append("description=\"").append(description).append("\"\n");
                    } else {
                        content.append(line).append("\n");
                    }
                }
                // Write the updated content back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookDataDir.toFile()))) {
                    writer.write(content.toString());
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error editing book: " + e.getMessage());
            }
        }
            // Output: Updates the book file with the given details.
        // Input: bookid (String), newTitle (String), author (String), genre (String), type (List<Boolean>), description (String)



        //Loaning System

        public void loanBook(String borrowerId) {
            if (curBorrower.isEmpty()) {
                curBorrower = borrowerId;
                allBorrowers.add(borrowerId);
                editBook(bookid, title, author, genre, type, description);
            } else {
                JOptionPane.showMessageDialog(null, "Book is already borrowed by " + curBorrower);
            }
        }
        // Output: Loans the book to the given borrower.
        // Input: borrowerId (String)
        
        public static void returnBook(String userID, String bookID) {
            List<User.UserInfo> users = User.getUsers();
            for (User.UserInfo user : users) {
                if (user.getUserID().equals(userID)) {
                    if (user.getCur_books().contains(bookID)) {
                        BookInfo bookInfo = getBookInfo(bookID);
                        if (bookInfo != null) {
                            bookInfo.setCur_borrower("");
                            bookInfo.getAll_borrowers().add(userID);
                            editBook(bookID, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getGenre(), bookInfo.getType(), bookInfo.getDescription());
                            user.getCur_books().remove(bookID);
                            User.editUser(userID, user.getLname(), user.getFname(), user.getGrade());
                            TerminalLog.InfoLog("Book returned successfully.");
                        } else {
                            TerminalLog.ErrorLog("Book not found.");
                        }
                    } else {
                        TerminalLog.ErrorLog("User does not have this book.");
                    }
                    return;
                }
            }
            TerminalLog.ErrorLog("User not found.");
        }
        // Output: Returns the book with the given ID from the given user.
        // Input: userID (String), bookID (String)
        
        public static BookInfo getBookInfo(String bookID) {
            List<BookInfo> books = getBooks();
            for (BookInfo book : books) {
                if (book.getBookID().equals(bookID)) {
                    return book;
                }
            }
            return null;
        }
        // Output: Returns the book information with the given ID.
        // Input: bookID (String)
        

        
        
        public static void returnBooks(String userID, List<String> bookIDs) {
            for (String bookID : bookIDs) {
                returnBook(userID, bookID);
            }
        }
        

        public static class BookInfo {
            private String bookID;
            private String title;
            private String author;
            private String genre;
            private List<Boolean> type;
            private String description;
            private String cur_borrower;
            private LinkedList<String> all_borrowers;
        
            public BookInfo(String bookID, String title, String author, String genre, List<Boolean> type, String description, String cur_borrower, LinkedList<String> all_borrowers) {
                this.bookID = bookID;
                this.title = title;
                this.author = author;
                this.genre = genre;
                this.type = type;
                this.description = description;
                this.cur_borrower = cur_borrower;
                this.all_borrowers = all_borrowers;
            }
        
            public String getBookID() {
                return bookID;
            }
        
            public String getTitle() {
                return title;
            }
        
            public String getAuthor() {
                return author;
            }
        
            public String getGenre() {
                return genre;
            }
        
            public List<Boolean> getType() {
                return type;
            }
        
            public String getDescription() {
                return description;
            }
        
            public String getCur_borrower() {
                return cur_borrower;
            }
        
            public LinkedList<String> getAll_borrowers() {
                return all_borrowers;
            }

            public void setCur_borrower(String cur_borrower) {
                this.cur_borrower = cur_borrower;
            }

            public void setAll_borrowers(LinkedList<String> all_borrowers) {
                this.all_borrowers = all_borrowers;
            }
        }
    }