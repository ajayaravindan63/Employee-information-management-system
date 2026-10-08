-- Create the Database
CREATE DATABASE IF NOT EXISTS employee_db;

-- Select the database to use
USE employee_db;

-- Create the employees table
CREATE TABLE IF NOT EXISTS employees (
    employee_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    designation VARCHAR(50),
    salary DOUBLE,
    phone VARCHAR(15),
    email VARCHAR(100)
);

-- Insert dummy data for initial testing (Optional)
INSERT INTO employees (employee_id, name, department, designation, salary, phone, email) 
VALUES 
(101, 'John Doe', 'Engineering', 'Software Engineer', 75000.00, '555-1234', 'john.doe@company.com'),
(102, 'Jane Smith', 'Human Resources', 'HR Manager', 65000.00, '555-5678', 'jane.smith@company.com'),
(103, 'Robert Brown', 'Marketing', 'Marketing Executive', 55000.00, '555-8765', 'robert.brown@company.com');
