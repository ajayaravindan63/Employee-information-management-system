package com.eims;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeManagementGUI extends JFrame {

    // UI Components
    private JTextField txtId, txtName, txtDepartment, txtDesignation, txtSalary, txtPhone, txtEmail;
    private JButton btnAdd, btnView, btnSearch, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;
    
    // Database access object
    private EmployeeDAO employeeDAO;

    public EmployeeManagementGUI() {
        // Initialize DAO
        employeeDAO = new EmployeeDAO();

        // Frame Setup
        setTitle("Employee Information Management System");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10));

        initComponents();

        // Load data initially when the app starts
        loadEmployeeData();
    }

    private void initComponents() {
        // 1. FORM PANEL (North)
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        formPanel.add(new JLabel("Employee ID (Numeric):"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        formPanel.add(txtDepartment);

        formPanel.add(new JLabel("Designation:"));
        txtDesignation = new JTextField();
        formPanel.add(txtDesignation);

        formPanel.add(new JLabel("Salary:"));
        txtSalary = new JTextField();
        formPanel.add(txtSalary);

        formPanel.add(new JLabel("Phone Number:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Email Address:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        // 2. BUTTONS PANEL (Center)
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnAdd = new JButton("Add Employee");
        btnView = new JButton("View/Refresh");
        btnSearch = new JButton("Search");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear Form");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnView);
        buttonsPanel.add(btnSearch);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnClear);

        // Combine Form and Buttons in a Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // 3. TABLE PANEL (South/Center)
        String[] columns = {"ID", "Name", "Department", "Designation", "Salary", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        // Prevent editing cells directly in table
        table.setDefaultEditor(Object.class, null); 
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Employee Records"));
        add(scrollPane, BorderLayout.CENTER);

        // 4. EVENT HANDLING
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEmployeeData();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    // --- Action Methods ---

    private void addEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String dept = txtDepartment.getText().trim();
            String desig = txtDesignation.getText().trim();
            double salary = Double.parseDouble(txtSalary.getText().trim());
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            if (name.isEmpty() || dept.isEmpty() || desig.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Employee emp = new Employee(id, name, dept, desig, salary, phone, email);
            
            if (employeeDAO.addEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "Employee Added Successfully!");
                clearFields();
                loadEmployeeData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee. Make sure the ID is unique.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Employee ID and Salary must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployeeData() {
        tableModel.setRowCount(0); // Clear current table rows
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        for (Employee emp : employees) {
            Object[] row = {
                    emp.getEmployeeId(), 
                    emp.getName(), 
                    emp.getDepartment(),
                    emp.getDesignation(), 
                    emp.getSalary(), 
                    emp.getPhone(), 
                    emp.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    private void searchEmployee() {
        try {
            String input = JOptionPane.showInputDialog(this, "Enter Employee ID to Search:");
            if (input != null && !input.trim().isEmpty()) {
                int id = Integer.parseInt(input.trim());
                Employee emp = employeeDAO.searchEmployeeById(id);
                
                if (emp != null) {
                    txtId.setText(String.valueOf(emp.getEmployeeId()));
                    txtName.setText(emp.getName());
                    txtDepartment.setText(emp.getDepartment());
                    txtDesignation.setText(emp.getDesignation());
                    txtSalary.setText(String.valueOf(emp.getSalary()));
                    txtPhone.setText(emp.getPhone());
                    txtEmail.setText(emp.getEmail());
                    
                    // Highlight row in table if found (Optional enhancement)
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (Integer.parseInt(table.getValueAt(i, 0).toString()) == id) {
                            table.setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Employee Record Found.");
                } else {
                    JOptionPane.showMessageDialog(this, "No Employee found with ID: " + id, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String dept = txtDepartment.getText().trim();
            String desig = txtDesignation.getText().trim();
            double salary = Double.parseDouble(txtSalary.getText().trim());
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            Employee emp = new Employee(id, name, dept, desig, salary, phone, email);
            
            if (employeeDAO.updateEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "Employee Details Updated Successfully!");
                loadEmployeeData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee. Please search an existing ID first.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid inputs. Ensure ID and Salary are numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try {
            String idStr = txtId.getText().trim();
            if(idStr.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter or search an Employee ID to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = Integer.parseInt(idStr);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Employee ID: " + id + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (employeeDAO.deleteEmployee(id)) {
                    JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
                    clearFields();
                    loadEmployeeData(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee. ID might not exist.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDepartment.setText("");
        txtDesignation.setText("");
        txtSalary.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        // Use the system look and feel for a better appearance (Windows/Mac/Linux style)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new EmployeeManagementGUI().setVisible(true);
        });
    }
}
