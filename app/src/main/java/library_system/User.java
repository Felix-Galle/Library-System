package library_system;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class User {

public static void addUser(String userID, String Lname, String Fname, String grade) {
    
    

    String hashedID = Pass.hashPass(userID);
    String filePath = "data\\userdata\\" + hashedID + ".txt";
    File file = new File(filePath);
    File dir = file.getParentFile();
    if (!dir.exists()) {
        dir.mkdirs();
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write("Lname = " + Lname + "\n");
        writer.write("Fname = " + Fname + "\n");
        writer.write("grade = " + grade + "\n");
        writer.write("cur_loanList = \n");
        writer.write("all_LoanList = \n");
    } catch (IOException e) {
        System.out.println("Error adding user: " + e.getMessage());
    }
}

    public static void editUser(String userID, String Lname, String Fname, String grade) {
        
        System.out.println("data\\userdata\\" + userID + ""); // TODO Remove, just for testing.
        try (BufferedReader reader = new BufferedReader(new FileReader("data\\userdata\\" + userID + ""))) {
            
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Lname = ")) {
                    content.append("Lname = ").append(Lname).append("\n");
                } else if (line.startsWith("Fname = ")) {
                    content.append("Fname = ").append(Fname).append("\n");
                } else if (line.startsWith("grade = ")) {
                    content.append("grade = ").append(grade).append("\n");
                } else {
                    content.append(line).append("\n");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data\\userdata\\" + userID + ""))) {
                writer.write(content.toString());
            }

            TerminalLog.InfoLog("User data edit successful.");
        } catch (IOException e) {
            System.out.println("Error editing user: " + e.getMessage());
        }
    }

    public static void removeUser(String userID) {
        String hashedID = Pass.hashPass(userID);
        Path filePath = Paths.get("data\\userdata\\" + hashedID + ".txt");
        try {
            java.nio.file.Files.delete(filePath);
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public static List<UserInfo> getUsers() {
        List<UserInfo> users = new ArrayList<>();
        Path userDataDir = Paths.get("data\\userdata");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(userDataDir)) {
            for (Path filePath : stream) {
                if (Files.isRegularFile(filePath)) {
                    String fileName = filePath.getFileName().toString();
                    String userID = fileName.substring(0, fileName.length() - 4); // Remove the ".txt" extension
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                        String line;
                        String lname = null;
                        String fname = null;
                        String grade = null;
                        ArrayList<String> cur_books = new ArrayList<>();
                        LinkedList<String> all_books = new LinkedList<>();
    
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Lname = ")) {
                                lname = line.substring(7).trim();
                            } else if (line.startsWith("Fname = ")) {
                                fname = line.substring(7).trim();
                            } else if (line.startsWith("grade = ")) {
                                grade = line.substring(7).trim();
                            } else if (line.startsWith("cur_loanList = ")) {
                                while ((line = reader.readLine()) != null) {
                                    if (line.trim().isEmpty()) {
                                        break;
                                    }
                                    cur_books.add(line.trim());
                                }
                            } else if (line.startsWith("all_LoanList = ")) {
                                while ((line = reader.readLine()) != null) {
                                    if (line.trim().isEmpty()) {
                                        break;
                                    }
                                    all_books.add(line.trim());
                                }
                            }
                            if (lname != null && fname != null && grade != null) {
                                break;
                            }
                        }
                        users.add(new UserInfo(userID, lname, fname, fileName, grade, cur_books, all_books));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error getting users: " + e.getMessage());
        }
        return users;
    }

    public static void viewUser(String userID) {
        String hashedID = Pass.hashPass(userID);
        List<UserInfo> users = getUsers();
        for (UserInfo user : users) {
            if (user.getUserID().equals(hashedID)) {
                System.out.println("User ID: " + user.getUserID());
                System.out.println("Last Name: " + user.getLname());
                System.out.println("First Name: " + user.getFname());
                System.out.println("Grade: " + user.getGrade());
                System.out.println("Current Loans: " + user.getCur_books());
                System.out.println("All Loans: " + user.getAll_books());
                return;
            }
        }
        System.out.println("User not found.");
    }

    public static void loanBook(String userID, String bookID) {
        List<UserInfo> users = getUsers();
        for (UserInfo user : users) {
            if (user.getUserID().equals(userID)) {
                if (user.getCur_books().size() < 5) {
                    Book book = Book.getBook(bookID);
                    if (book != null) {
                        book.loanBook(userID);
                        user.getCur_books().add(bookID);
                        editUser(userID, user.getLname(), user.getFname(), user.getGrade());
                        TerminalLog.InfoLog("Book loaned successfully.");
                    } else {
                        TerminalLog.ErrorLog("Book not found.");
                    }
                } else {
                    TerminalLog.ErrorLog("User has already borrowed 5 books.");
                }
                return;
            }
        }
        TerminalLog.ErrorLog("User not found.");
    }

    public static class UserInfo {
        private String userID;
        private String lname;
        private String fname;
        private String fileName;
        private String grade;
        private ArrayList<String> cur_books;
        private LinkedList<String> all_books;
    
        public UserInfo(String userID, String lname, String fname, String fileName, String grade, ArrayList<String> cur_books, LinkedList<String> all_books) {
            this.userID = userID;
            this.lname = lname;
            this.fname = fname;
            this.fileName = fileName;
            this.grade = grade;
            this.cur_books = cur_books;
            this.all_books = all_books;
        }
    
        public String getUserID() {
            return userID;
        }
    
        public String getLname() {
            return lname;
        }
    
        public String getFname() {
            return fname;
        }
    
        public String getFileName() {
            return fileName;
        }
    
        public String getGrade() {
            return grade;
        }
    
        public ArrayList<String> getCur_books() {
            return cur_books;
        }
    
        public LinkedList<String> getAll_books() {
            return all_books;
        }
    }
}