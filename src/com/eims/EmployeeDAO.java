package com.eims;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDAO (Data Access Object) handles all CRUD operations 
 * (Create, Read, Update, Delete) to the database.
 */
public class EmployeeDAO {

    // 1. ADD EMPLOYEE (Create)
    public boolean addEmployee(Employee emp) {
        String query = "INSERT INTO employees (employee_id, name, department, designation, salary, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // Using try-with-resources to automatically close connection and statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, emp.getEmployeeId());
            pstmt.setString(2, emp.getName());
            pstmt.setString(3, emp.getDepartment());
            pstmt.setString(4, emp.getDesignation());
            pstmt.setDouble(5, emp.getSalary());
            pstmt.setString(6, emp.getPhone());
            pstmt.setString(7, emp.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    // 2. VIEW EMPLOYEES (Read - All)
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employees";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employeeList.add(extractEmployeeFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
        }
        return employeeList;
    }

    // 3. SEARCH EMPLOYEE (Read - Single by ID)
    public Employee searchEmployeeById(int id) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractEmployeeFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching employee: " + e.getMessage());
        }
        return null;
    }

    // 4. UPDATE EMPLOYEE (Update)
    public boolean updateEmployee(Employee emp) {
        String query = "UPDATE employees SET name=?, department=?, designation=?, salary=?, phone=?, email=? WHERE employee_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getDepartment());
            pstmt.setString(3, emp.getDesignation());
            pstmt.setDouble(4, emp.getSalary());
            pstmt.setString(5, emp.getPhone());
            pstmt.setString(6, emp.getEmail());
            pstmt.setInt(7, emp.getEmployeeId()); // ID goes last in the WHERE clause

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    // 5. DELETE EMPLOYEE (Delete)
    public boolean deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    // Helper method to convert a ResultSet row into an Employee object
    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("employee_id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getString("designation"),
                rs.getDouble("salary"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
