package library_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout; // Importing Book class
import java.awt.Color; // Importing BrowsePage class
import java.awt.Desktop; // Importing TemplatePage class
import java.awt.Font; // Importing Book class
import java.awt.event.ActionEvent; // Importing BrowsePage class
import java.awt.event.ActionListener; // Importing TemplatePage class
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TerminalLog {

    private static JFrame main_window;

    private static LinkedList<String> logMessages = new LinkedList<>(); //Log Linked List
    private static JTextArea logText;                                        //Log Text
    public static JFrame terminalLogStage;                               //Defines Stage as Terminal Log
    private static Map<String, Command> commands = new HashMap<>();     //Creates Hashmap for the commands
    private static List<String> commandHistory = new ArrayList<>();
    private static int commandHistoryIndex = 0;

    //key = input/syntax e.g. list & data = Command class e.g. ListCommand
    private static JTextField inputField;

    public static void showTerminalLog() {

        //Defines the Window settings. Cannot be changed (FINAL)
        terminalLogStage = new JFrame("Terminal Log");      //Creates a new stage/window
        terminalLogStage.setTitle("Terminal Log");      //Sets the title
        terminalLogStage.setSize(500, 800);                 //Sets width and height
        terminalLogStage.setLocation(50,50);
        terminalLogStage.setResizable(true);
        terminalLogStage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Creates new Pane (where everything is displayed)
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());

        logText = new JTextArea();   //defines Text
        logText.setEditable(false);
        logText.setFont(new Font("Monospaced", Font.PLAIN, 18));
        logText.setForeground(Color.GRAY);

        JScrollPane logScrollPane = new JScrollPane(logText);
        logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Creates the input field
        inputField = new JTextField();
        inputField.setToolTipText("Enter command");
        inputField.setColumns(1);
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 18));

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText(); //Fetches text from input
                executeCommand(input); //Executes command
                inputField.setText(""); //Clears input
            }
        });

        //Required to get everything showing on the window
        JPanel centerBox = new JPanel();
        centerBox.setLayout(new BorderLayout());
        centerBox.add(logScrollPane, BorderLayout.CENTER);
        centerBox.add(inputField, BorderLayout.PAGE_END);

        root.add(centerBox, BorderLayout.CENTER);

        terminalLogStage.add(root);



        // Register some basic commands. REQUIRED otherwise not shown when 'list' is typed
        registerCommand("help", new HelpCommand());
        registerCommand("clear", new ClearCommand());
        registerCommand("list", new ListCommand());
        registerCommand("version", new VersionCommand());
        registerCommand("adduser", new AddUserCommand());
        registerCommand("removeuser", new RemoveUserCommand());
        registerCommand("edituser", new EditUserCommand());
        registerCommand("viewuser", new ViewUserCommand());
        registerCommand("addbook", new AddBookCommand());
        registerCommand("removebook", new RemoveBookCommand());
        registerCommand("editbook", new EditBookCommand());
        registerCommand("viewbooks", new ViewBooksCommand());
        registerCommand("goto", new GotoCommand());
        registerCommand("loan", new AddLoanCommand());
        registerCommand("return", new AddReturnCommand());
        registerCommand("returnall", new AddReturnMultipleCommand());
        registerCommand("viewdocs", new ViewDocsCommand());
        


        updateLogText();//Auto updates logs

        terminalLogStage.setVisible(true);
    }

    //required to update the Log TextArea
    public static void updateLogText() {
        StringBuilder log = new StringBuilder();
        for (String message : logMessages) {
            log.append(message).append("\n");
        }
        logText.setText(log.toString());
    }
    
    
    /*
     * This section is about the types of logs
     * The types of Logs are lcoated in LogLevel.java
     */




    //Error Log. Prints out any java errors or errors within the application.
    public static void ErrorLog(String message) {
        logMessages.add(LogLevel.ERROR + ": " + message); 
        System.out.println(LogLevel.ERROR + ": " + message); 
        if(terminalLogStage == null) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (terminalLogStage != null && terminalLogStage.isShowing()) {
            updateLogText();
            inputField.setText(""); // Clear the input field
        } else {
            return;
        }
    }

    //Dispalys important info e.g. current processes 
    public static void InfoLog(String message) {
        logMessages.add(LogLevel.INFO + ": " + message); //Prints to TerminalLog
        System.out.println(LogLevel.INFO + ": " + message);  //Prints to java terminal
        if (terminalLogStage != null && terminalLogStage.isShowing()) {
            updateLogText();
        } else {
            return;
        }
    }

    //Warning Log. Provides warnings as to functionality. 
    public static void WarningLog(String message) {
        logMessages.add(LogLevel.WARNING + ": " + message); //Prints to TerminalLog
        System.out.println(LogLevel.WARNING + ": " + message); //Prints to java terminal
        if (terminalLogStage != null && terminalLogStage.isShowing()) {
            updateLogText();
        } else {
            return;
        }
    }

    //Command Log. Prints out logs of the Commands inputted.
    public static void CommandLog(String message) {
        logMessages.add(LogLevel.COMMAND + ": " + message); //Prints to TerminalLog
        System.out.println(LogLevel.COMMAND + ": " + message); //Print to java terminal
        if (terminalLogStage != null && terminalLogStage.isShowing()) {
            updateLogText();
        } else {
            return;
        }
    }

    public static void LolLog(String message) {
        logMessages.add(LogLevel.LOL + " , you messed up :P \n u typd:" + message + "\n (╬ಠ益ಠ) "); //Prints to TerminalLog
        System.out.println(LogLevel.LOL + " , you messed up :P \n u typd:" + message + "\n (╬ಠ益ಠ) "); 
        if (terminalLogStage != null && terminalLogStage.isShowing()) {
            updateLogText();
        } else {
            return;
        }
    }
    

    //Adds commands. Shows the format to use: name then the command (what it does)
    public static void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    //This is where the commands get executed.
    public static void executeCommand(String input) {
        CommandLog(">" + input); // print the command that was inputted
        String[] parts = input.split(" ");
        if (parts.length > 0) {
            String commandName = parts[0].toLowerCase();
            Command command = commands.get(commandName);
            if (command != null) {
                command.execute(parts);
            } else {
                ErrorLog("Unknown command: " + commandName);
            }
        }
        commandHistory.add(input); // add the command to the history
        commandHistoryIndex = commandHistory.size(); // update the history index
    }

    //The main executor of the commands. Required for functionality
    public interface Command {
        void execute(String[] args);
    }

    //The extracing of arguements
    public static String extractArgument(String arg) {
        if (arg.startsWith("\"") && arg.endsWith("\"")) {
            return arg.substring(1, arg.length() - 1);
        } else if (arg.startsWith("<") && arg.endsWith(">")) {
            return arg.substring(1, arg.length() - 1);
        } else {
            ErrorLog("Invalid argument format. Use double quotes \"\" or angle brackets <>.");
            return null;
        }
    }

    //help command. Gives short description of software and pointer.
    public static class HelpCommand implements Command {
        @Override
        public void execute(String[] args) {
    String message =

"""
This is the terminal for the Library System Application.
You can directly control the various options and settings here.

The view the Terminal Log Documentation, type 'viewdocs'

To view a full list of commands type 'list'.""";

            CommandLog(message);
        }
    }

    //Clear console command. Clears the console
    public static class ClearCommand implements Command {
        @Override
        public void execute(String[] args) {
            logMessages.clear();
            updateLogText();
        }
    }

    //Provides a list of all available commands as of right now.
    public static class ListCommand implements Command {
        @Override
        public void execute(String[] args) {
            CommandLog("Available commands:");
            for (String commandName : commands.keySet()) {
                CommandLog(commandName);
            }
        }
    }

    // Version Command.
    public static class VersionCommand implements Command {
        @Override
        public void execute(String[] args) {
            String projectName = "Library System";
            String version = "v0.7.0";
            String developer = "Felix Gallé";
    
            if (args.length == 1) {
                CommandLog(projectName+ "\nUsage: version cur/all");
            } else if (args.length > 1) {
                String subcommand = args[1].toLowerCase();
                switch (subcommand) {
                    case "cur":
                        CommandLog("Current version: " + version);
                        break;
                    case "all":
                        String allVersionsMessage = projectName + "\n" +
                                "Current Version: " + version + "\n" +
                                "Developed by: " + developer + "\n" +
                                "\nPast Versions:\n" +
                                "0.1\n0.2\n0.3\n0.4\n0.5\n0.6\n0.7";
                        CommandLog(allVersionsMessage);
                        break;
                    default:
                        ErrorLog("Unknown subcommand: " + subcommand);
                }
            }
        }
    }
    // AddUserCommand
    public static class AddUserCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 5) {
                ErrorLog("Usage: adduser <userID> <Lname> <Fname> <grade>\nAccepted separators are speechmarks \" and normal brackets ( & )");
                return;
            }
            String userID = extractArgument(args[1]);
            String Lname = extractArgument(args[2]);
            String Fname = extractArgument(args[3]);
            String grade = extractArgument(args[4]);
            User.addUser(userID, Lname, Fname, grade);
            InfoLog("User added: " + userID);
        }

        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("(") && arg.endsWith(")")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    // EditUserCommand
    public static class EditUserCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 5) {
                ErrorLog("Usage: edituser <userID> <Lname> <Fname> <grade>");
                return;
            }
            String userID = extractArgument(args[1]);
            String Lname = extractArgument(args[2]);
            String Fname = extractArgument(args[3]);
            String grade = extractArgument(args[4]);
            User.editUser(userID, Lname, Fname, grade);
            CommandLog("User edited: " + userID);
        }

        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    // RemoveUserCommand
    public static class RemoveUserCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 2) {
                ErrorLog("Usage: removeuser <userID>");
                return;
            }
            String userID = extractArgument(args[1]);
            User.removeUser(userID);
            CommandLog("User removed: " + userID);
        }

        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    public static class ViewUserCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 2) {
                ErrorLog("Usage: viewuser <userID>");
                return;
            }
            String userID = extractArgument(args[1]);
            User.viewUser(userID);
        }
    
        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }


    public static class AddBookCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 7) {
                ErrorLog("Usage: addbook <bookid> <title> <author> <genre> <type> <description>");
                return;
            }
            String bookid = extractArgument(args[1]);
            String title = extractArgument(args[2]);
            String author = extractArgument(args[3]);
            String genre = extractArgument(args[4]);
            String type = extractArgument(args[5]);
            String description = extractArgument(args[6]);
            List<Boolean> types = new ArrayList<>();
            for (String t : type.split(",")) {
                if (t.trim().toLowerCase().equals("fiction")) {
                    types.add(true);
                } else if (t.trim().toLowerCase().equals("non fiction")) {
                    types.add(false);
                } else if (t.trim().toLowerCase().equals("hardcover")) {
                    types.add(true);
                } else if (t.trim().toLowerCase().equals("paperback")) {
                    types.add(false);
                }
            }
            Book.addBook(bookid, title, author, genre, types, description);
            CommandLog("Book added: " + title);
        }

        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                return arg; // Return the argument as is if it doesn't have quotes or brackets
            }
        }
    }

    public static class EditBookCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 7) {
                ErrorLog("Usage: editbook <bookid> <newtitle> <author> <genre> <type> <description>");
                return;
            }
            String bookid = args[1];
            String newTitle = args[2];
            String author = args[3];
            String genre = args[4];
            List<Boolean> type = new ArrayList<>();
            for (String t : args[5].split(",")) {
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
            String description = args[6];
            Book.editBook(bookid, newTitle, author, genre, type, description);
            CommandLog("Book edited: " + bookid);
        }
    }

    // RemoveBookCommand
    public static class RemoveBookCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 2) {
                ErrorLog("Usage: removebook <title>");
                return;
            }
            String title = extractArgument(args[1]);
            // Delete the book file
            Path path = Paths.get(title + ".txt");
            try {
                java.nio.file.Files.deleteIfExists(path);
                CommandLog("Book removed: " + title);
            } catch (IOException e) {
                ErrorLog("Error removing book: " + e.getMessage());
            }
        }

        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    public static class ViewBooksCommand implements Command {
        @Override
        public void execute(String[] args) {
            // List all books in the system
            File folder = new File(".");
            File[] listOfFiles = folder.listFiles();
            StringBuilder bookList = new StringBuilder();
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String bookId = file.getName().replace(".txt", "");
                    bookList.append(bookId).append("\n");
                }
            }
            CommandLog("Available books:");
            CommandLog(bookList.toString());
        }
    }

    //goto commands, select the desired scene/stage and it will go to it
    public static class GotoCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length == 1) {
                ErrorLog("Usage: goto <self/main> <scene/stage>");
                CommandLog("Available scenes:");
                CommandLog("home (HomePage)\n\t loan (LoanPage)\n\t browse (BrowsePage)\n\t profile (ProfilePage)\n\t template (TemplatePage)");
                return;
            }
            if (args.length == 2) {
                ErrorLog("Usage: goto <self/main> <scene/stage>");
                return;
            }
            String target = args[1];
            String scene = args[2];
            String scene_select = scene.toLowerCase();

            switch (target) {
                case "self":
                    switch (scene_select) {
                        case "home":
                        case "homepage":
                            TerminalLog.terminalLogStage.getContentPane().removeAll();
                            TerminalLog.terminalLogStage.getContentPane().add(new HomePage(TerminalLog.terminalLogStage).getHomePage());
                            TerminalLog.terminalLogStage.revalidate();
                            TerminalLog.terminalLogStage.repaint();
                            break;
                        case "browse":
                        case "browsepage":
                            TerminalLog.terminalLogStage.getContentPane().removeAll();
                            TerminalLog.terminalLogStage.getContentPane().add(new BrowsePage(TerminalLog.terminalLogStage).getBrowsePage());
                            TerminalLog.terminalLogStage.revalidate();
                            TerminalLog.terminalLogStage.repaint();
                            break;
                        case "loan":
                        case "loanpage":
                            TerminalLog.terminalLogStage.getContentPane().removeAll();
                            TerminalLog.terminalLogStage.getContentPane().add(new LoanPage(TerminalLog.terminalLogStage).getLoanPage());
                            TerminalLog.terminalLogStage.revalidate();
                            TerminalLog.terminalLogStage.repaint();
                            break;
                        case "template":
                        case "templatepage":
                            TerminalLog.terminalLogStage.getContentPane().removeAll();
                            TerminalLog.terminalLogStage.getContentPane().add(new TemplatePage().getTemplatePage());
                            TerminalLog.terminalLogStage.revalidate();
                            TerminalLog.terminalLogStage.repaint();
                            break;
                        default:
                            ErrorLog("Invalid scene: " + scene);
                    }
                    break;
                case "main":
                    if (main_window instanceof App) {
                        App app = (App) main_window;
                        switch (scene_select) {
                            case "home":
                            case "homepage":
                                app.showHomePage();
                                break;
                            case "browse":
                            case "browsepage":
                                app.showBrowsePage();
                                break;
                            case "loan":
                            case "loanpage":
                                app.showLoanPage();
                                break;
                            case "template":
                            case "templatepage":
                                app.showProfilePage();
                                break;
                            default:
                                ErrorLog("Invalid scene: " + scene);
                        }
                    } else {
                        ErrorLog("Main window is not an instance of App");
                    }
                    break;
                case "slef":
                    LolLog(target + " " + scene_select);
                    break;
                default:
                    ErrorLog("Invalid target: " + target);
            }
        }
    }

    public static class AddLoanCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 3) {
                ErrorLog("Usage: loan <userID> <bookID>");
                return;
            }
            String userID = extractArgument(args[1]);
            String bookID = extractArgument(args[2]);
            User.loanBook(userID, bookID);
        }
    
        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    public static class AddReturnCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 3) {
                ErrorLog("Usage: return <userID> <bookID>");
                return;
            }
            String userID = extractArgument(args[1]);
            String bookID = extractArgument(args[2]);
            Book.returnBook(userID, bookID);
        }
    
        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }

    public static class AddReturnMultipleCommand implements Command {
        @Override
        public void execute(String[] args) {
            if (args.length < 3) {
                ErrorLog("Usage: returnall <userID> <bookID1> <bookID2> ...");
                return;
            }
            String userID = extractArgument(args[1]);
            List<String> bookIDs = new ArrayList<>();
            for (int i = 2; i < args.length; i++) {
                bookIDs.add(extractArgument(args[i]));
            }
            Book.returnBooks(userID, bookIDs);
        }
    
        private static String extractArgument(String arg) {
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            } else if (arg.startsWith("<") && arg.endsWith(">")) {
                return arg.substring(1, arg.length() - 1);
            } else {
                ErrorLog("Invalid argument format. Use double quotes or angle brackets.");
                return null;
            }
        }
    }
    
    public static class ViewDocsCommand implements Command {
        @Override
        public void execute(String[] args) {
            // Get the current directory of the TerminalLog class
            String currentDir = System.getProperty("user.dir");
            // Construct the relative path to the docs folder
            String folderPath = currentDir + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "library" + File.separator + "systemv07" + File.separator + "library_systemv07" + File.separator + "docs";

            // Create a File object for the folder
            File folder = new File(folderPath);

            // Check if the folder exists
            if (!folder.exists()) {
                TerminalLog.ErrorLog("The folder: " + folderPath + " doesn't exist.");
                return;
            }

            // Check if the Desktop class is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    // Open the folder in File Explorer
                    desktop.open(folder);
                } catch (IOException e) {
                    TerminalLog.ErrorLog(e.toString());
                }
            } else {
                TerminalLog.ErrorLog("java.awt.Desktop is not supported");
            }
        }
    }
}
