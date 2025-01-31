# TerminalLog Command Usage Guide

## General Information

This guide provides detailed instructions on how to use each command available in the TerminalLog system. Commands are executed by typing them into the terminal log input field.

## Command syntax

All Commands use the same type of syntax (however, I may have been lazy and done it wrong).

Angled brackets  `<>` are used to represent required parameters.
Square brackets `[]` are used to represent optional parameters.
Curly brackets `{}` are used to represent a list of parameters.
Normal brackets `()` are used to represent additional information.

## Commands

### Help Command

**Usage:** `help`

Displays a short description of the software and a pointer to the list of available commands.

### Clear Command

**Usage:** `clear`

Clears the console log.

### List Command

**Usage:** `list`

Provides a list of all available commands.

### Version Command

**Usage:** `version <{cur | all}>`

Displays the current version or all versions of the software.

- `cur`: Shows the current version.
- `all`: Shows all versions along with the developer information.

### AddUser Command

**Usage:** `adduser <userID> <Lname> <Fname> <grade>`

Adds a new user to the system.

- `userID`: The user's ID.
- `Lname`: The user's last name.
- `Fname`: The user's first name.
- `grade`: The user's grade.

### EditUser Command

**Usage:** `edituser <userID> <Lname> <Fname> <grade>`

Edits an existing user's information.

- `userID`: The user's ID.
- `Lname`: The user's last name.
- `Fname`: The user's first name.
- `grade`: The user's grade.

### RemoveUser Command

**Usage:** `removeuser <userID>`

Removes a user from the system.

- `userID`: The user's ID.

### ViewUser Command

**Usage:** `viewuser <userID>`

Displays information about a specific user.

- `userID`: The user's ID.

### AddBook Command

**Usage:** `addbook <bookid> <title> <author> <genre> <type> <description>`

Adds a new book to the system.

- `bookid`: The book's ID.
- `title`: The book's title.
- `author`: The book's author.
- `genre`: The book's genre.
- `type`: The book's type (e.g., fiction, non-fiction, hardcover, paperback).
- `description`: A description of the book.

### EditBook Command

**Usage:** `editbook <bookid> <newtitle> <author> <genre> <type> <description>`

Edits an existing book's information.

- `bookid`: The book's ID.
- `newtitle`: The new title of the book.
- `author`: The book's author.
- `genre`: The book's genre.
- `type`: The book's type (e.g., fiction, non-fiction, hardcover, paperback).
- `description`: A description of the book.

### RemoveBook Command

**Usage:** `removebook <title>`

Removes a book from the system.

- `title`: The book's title.

### ViewBooks Command

**Usage:** `viewbooks`

Lists all books available in the system.

### Goto Command

**Usage:** `goto <{self | main}> <{home | browse | loan | template}>`

Navigates to a specified scene or stage.

- `self`: Refers to the terminal log window.
- `main`: Refers to the main application window.
- `home`: Navigates to the HomePage.
- `browse`: Navigates to the BrowsePage.
- `loan`: Navigates to the LoanPage.
- `template`: Navigates to the TemplatePage.

### AddLoan Command

**Usage:** `loan <userID> <bookID>`

Loans a book to a user.

- `userID`: The user's ID.
- `bookID`: The book's ID.

### AddReturn Command

**Usage:** `return <userID> <bookID>`

Returns a book from a user.

- `userID`: The user's ID.
- `bookID`: The book's ID.

### AddReturnMultiple Command

**Usage:** `returnall <userID> <bookID1> <bookID2> ...`

Returns multiple books from a user.

- `userID`: The user's ID.
- `bookID1`, `bookID2`, ...: The IDs of the books to be returned.

## Log Levels

- **ErrorLog:** Logs errors within the application.
- **InfoLog:** Displays important information about current processes.
- **WarningLog:** Provides warnings about functionality.
- **CommandLog:** Logs the commands inputted.
- **LolLog:** Logs humorous messages when an invalid target is used.
