# Employee Information Management System (EIMS)

A complete college-level Java desktop application project that manages employee records using Java Swing and MySQL Database.

## 1. Project Abstract
The **Employee Information Management System (EIMS)** is a desktop-based application developed to digitize and manage employee data within an organization. Traditionally, employee records are maintained in physical files or scattered spreadsheets, which is prone to errors, data loss, and inefficiency in searching for specific records. This software provides a unified graphical user interface (GUI) to Add, View, Search, Update, and Delete (CRUD) employee profiles quickly and reliably. By storing data in a relational database (MySQL), EIMS ensures data persistence, integrity, and security against unauthorized modifications.

## 2. Objectives of the Project
- To transition from manual paper-based record-keeping to a digitized approach.
- To implement standard CRUD (Create, Read, Update, Delete) operations cleanly.
- To demonstrate practical knowledge of Object-Oriented Programming (OOP) concepts in Java.
- To understand graphical UI design using Java Swing.
- To establish a secure connection between Java and a database using JDBC.

## 3. Modules/Features of the Project
- **Data Entry Module (Add):** Validates and inserts new employee records into the database. Prevents duplication of employee IDs.
- **Viewing Module (View):** Fetches all available records from the MySQL database and presents them in an organized JTable format.
- **Search Module:** Quickly locates an employee using their unique Employee ID.
- **Update Module:** Loads an existing record, allows the user to modify details like designation or salary, and safely updates the database.
- **Deletion Module:** Allows an administrator to permanently remove a retired or resigned employee from the system upon confirmation.

## 4. Required JDBC Driver / Dependency Setup
This project uses pure Java and JDBC. You need the **MySQL Connector/J** library.
1. Download `mysql-connector-j-x.x.xx.jar` from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/).
2. Create a folder named `lib` inside this project directory.
3. Paste the downloaded `.jar` file inside the `lib` folder.

## 5. Database Setup Instructions
1. Install **XAMPP**, **WAMP**, or **MySQL Server**.
2. Open your MySQL client (e.g., MySQL Workbench, phpMyAdmin, or command-line).
3. Copy the contents of `database.sql` and execute it.
   - This creates the `employee_db` database and the `employees` table.
4. **Important**: Open `src/com/eims/DBConnection.java` and ensure the `USER` and `PASSWORD` match your MySQL setup (usually `root` and empty/`password` for XAMPP).

## 6. Exact Instructions for Running the Project
**On Windows:**
1. Open the command prompt in the `EmployeeInformationManagementSystem` directory.
2. Compile the project:
   ```cmd
   javac -d bin src/com/eims/*.java
   ```
3. Run the project (Make sure you replace `mysql-connector-j-8.0.33.jar` with your exact jar name):
   ```cmd
   java -cp "bin;lib/mysql-connector-j-8.0.33.jar" com.eims.EmployeeManagementGUI
   ```
   *(A `run.bat` file is included to automate this process on Windows. You just need to ensure the correct jar file name inside it.)*

**On Linux / Mac:**
Use `:` instead of `;` for the classpath separator.
```bash
javac -d bin src/com/eims/*.java
java -cp "bin:lib/mysql-connector-j-8.0.33.jar" com.eims.EmployeeManagementGUI
```

## 7. How GUI, Java, JDBC, and MySQL Work Together
1. **GUI (Java Swing):** `EmployeeManagementGUI.java` creates buttons, text fields, and tables. When a user clicks a button (Event Handling), it triggers a specific method.
2. **Model (Java OOP):** `Employee.java` is a standard class acting as a blueprint. It holds data temporarily in RAM while transferring data from GUI to Database and vice versa.
3. **JDBC (Java Database Connectivity):** `DBConnection.java` establishes a pipeline between your Java app and the MySQL server using the `mysql-connector-j` driver.
4. **DAO (Data Access Object):** `EmployeeDAO.java` contains SQL Queries. It takes the Employee object, uses `PreparedStatement` to safely plug the data into an SQL string, and sends it through the DBConnection pipeline to MySQL.
5. **MySQL:** Stores the actual data on the hard drive. Returns success/failure codes or result sets back to Java.

## 8. Test Cases

| Test Case | Scenario | Expected Result | Pass/Fail |
|-----------|----------|-----------------|-----------|
| Add 1 | Enter valid details and click 'Add' | Message: "Employee Added Successfully", Table refreshes | Pass |
| Add 2 | Enter duplicate ID and click 'Add' | Message: "Failed to add employee" | Pass |
| Search 1 | Enter an existing ID in search prompt | Form fields are auto-filled with employee details | Pass |
| Search 2 | Enter non-existent ID | Message: "No Employee found" | Pass |
| Update 1 | Load details, change salary, click 'Update' | Message: "Updated Successfully", new salary visible in table | Pass |
| Delete 1 | Enter ID, click 'Delete', click 'Yes' on confirm | Record disappears from Table, deleted from DB | Pass |

## 9. Common Errors and How to Fix Them
- **Error:** `java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver`
  - *Fix:* Your JDBC `.jar` file is missing from the classpath. Ensure it's in the `lib` folder and correctly referenced in your run command.
- **Error:** `Access denied for user 'root'@'localhost'`
  - *Fix:* Incorrect database password. Open `DBConnection.java` and correct the `PASSWORD` variable.
- **Error:** `Communications link failure`
  - *Fix:* MySQL Server/XAMPP is not running. Start the MySQL service.
- **Error:** Table `employee_db.employees` doesn't exist
  - *Fix:* You forgot to run the `database.sql` script to create the table.

## 10. Future Enhancements
- **Authentication:** Add an Admin Login screen before showing the dashboard.
- **Export to PDF/Excel:** Ability to generate reports of the employee records.
- **Profile Pictures:** Allow uploading and saving images as BLOBs in the database.
- **Cloud Database:** Connect the application to a cloud SQL instance (e.g., AWS RDS) instead of localhost.

## 11. Viva Questions and Answers

**Q1: What is JDBC and what are its main components?**
*Answer:* JDBC stands for Java Database Connectivity. It's an API for connecting and executing queries on a database. Main components: DriverManager, Connection, Statement/PreparedStatement, and ResultSet.

**Q2: Why did you use PreparedStatement instead of Statement?**
*Answer:* `PreparedStatement` is pre-compiled, making it faster for repeated executions. More importantly, it prevents **SQL Injection** attacks because parameters are safely escaped before being sent to the database.

**Q3: What is the purpose of the DAO pattern used in your project?**
*Answer:* The Data Access Object (DAO) pattern separates database logic (SQL queries) from the business logic and user interface. It makes the code modular, easier to maintain, and cleaner.

**Q4: How do you handle exceptions in JDBC?**
*Answer:* We use `try-catch` blocks specifically catching `SQLException`. In this project, `try-with-resources` was used to automatically close the `Connection`, `PreparedStatement`, and `ResultSet` objects, preventing memory leaks.

**Q5: What layout managers did you use in Java Swing?**
*Answer:* I used a combination of `BorderLayout` (for overall screen structure), `GridLayout` (for aligning the input form neatly in rows/columns), and `FlowLayout` (for spacing the buttons evenly).
