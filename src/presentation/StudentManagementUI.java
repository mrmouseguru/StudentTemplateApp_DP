package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.StudentService;
import domain.model.Student;
import persistence.*;
import domain.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentManagementUI extends JFrame {
    private final StudentService studentService;
    private final DefaultTableModel tableModel;
    private final JTable studentTable;
    private final JLabel idLabel, nameLabel, majorLabel, javaLabel, htmlLabel, cssLabel;
    private final JTextField idField, nameField, majorField, javaField, htmlField, cssField;
    private final JButton addButton, removeButton, editButton, findButton;

    // search
    private final JLabel searchLabel;
    private final JTextField searchField;
    private final JButton searchButton;

    public StudentManagementUI(StudentService studentService) {
        this.studentService = studentService;

        // Initialize components
        idLabel = new JLabel("ID:");
        nameLabel = new JLabel("Name:");
        majorLabel = new JLabel("Major:");
        javaLabel = new JLabel("Java Mark:");
        htmlLabel = new JLabel("HTML Mark:");
        cssLabel = new JLabel("CSS Mark:");

        idField = new JTextField(10);
        nameField = new JTextField(20);
        majorField = new JTextField(20);
        javaField = new JTextField(5);
        htmlField = new JTextField(5);
        cssField = new JTextField(5);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        findButton = new JButton("Find");

        // search
        searchLabel = new JLabel("Search by Name:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Table setup
        String[] columnNames = { "ID", "Name", "Major", "Java Mark", "HTML Mark", "CSS Mark" };
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }
        });

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findStudent();
            }
        });

        // search
        // Button action for search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudentByName();
            }
        });

        // search
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Table selection action
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedStudentInfo();
            }
        });

        // Layout setup
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(idLabel, gbc);
        gbc.gridx++;
        inputPanel.add(idField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx++;
        inputPanel.add(nameField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(majorLabel, gbc);
        gbc.gridx++;
        inputPanel.add(majorField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(javaLabel, gbc);
        gbc.gridx++;
        inputPanel.add(javaField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(htmlLabel, gbc);
        gbc.gridx++;
        inputPanel.add(htmlField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(cssLabel, gbc);
        gbc.gridx++;
        inputPanel.add(cssField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(searchLabel, gbc);
        gbc.gridx++;
        inputPanel.add(searchField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(findButton);
        buttonPanel.add(searchButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        //search
       // mainPanel.add(searchPanel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setTitle("Student Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);
        this.add(mainPanel);
        this.setVisible(true);

        refreshStudentTable();
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String major = majorField.getText();
            int javaMark = Integer.parseInt(javaField.getText());
            int htmlMark = Integer.parseInt(htmlField.getText());
            int cssMark = Integer.parseInt(cssField.getText());

            Student student = new Student(id, name, major, javaMark, htmlMark, cssMark);
            studentService.addStudent(student);

            // Clear input fields
            idField.setText("");
            nameField.setText("");
            majorField.setText("");
            javaField.setText("");
            htmlField.setText("");
            cssField.setText("");

            // Refresh the table
            refreshStudentTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for marks or ID. Please enter valid numbers.");
        }
    }

    private void removeStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int studentId = (int) studentTable.getValueAt(selectedRow, 0);
            studentService.removeStudent(studentId);
            refreshStudentTable();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to remove.");
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String major = majorField.getText();
                int javaMark = Integer.parseInt(javaField.getText());
                int htmlMark = Integer.parseInt(htmlField.getText());
                int cssMark = Integer.parseInt(cssField.getText());

                Student updatedStudent = new Student(id, name, major, javaMark, htmlMark, cssMark);
                studentService.editStudent(updatedStudent);

                refreshStudentTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for marks or ID. Please enter valid numbers.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
        }
    }

    private void findStudent() {
        String idStr = JOptionPane.showInputDialog(this, "Enter the student ID to find:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Student student = studentService.findStudent(id);
                if (student != null) {
                    populateInputFields(student);
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for ID. Please enter a valid number.");
            }
        }
    }

    private void showSelectedStudentInfo() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int studentId = (int) studentTable.getValueAt(selectedRow, 0);
            Student student = studentService.findStudent(studentId);
            if (student != null) {
                populateInputFields(student);
            }
        }
    }

    private void populateInputFields(Student student) {
        idField.setText(String.valueOf(student.getId()));
        nameField.setText(student.getName());
        majorField.setText(student.getMajor());
        javaField.setText(String.valueOf(student.getJavaMark()));
        htmlField.setText(String.valueOf(student.getHtmlMark()));
        cssField.setText(String.valueOf(student.getCssMark()));
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        majorField.setText("");
        javaField.setText("");
        htmlField.setText("");
        cssField.setText("");
    }

    private void refreshStudentTable() {
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Retrieve all students from the service and populate the table
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            Object[] rowData = { student.getId(), student.getName(), student.getMajor(), student.getJavaMark(),
                    student.getHtmlMark(), student.getCssMark() };
            tableModel.addRow(rowData);
        }
    }

    private void searchStudentByName() {
        String name = searchField.getText().trim();
        if (!name.isEmpty()) {
            List<Student> matchingStudents = studentService.searchFullTextStudentsByName(name);
            //List<Student> matchingStudents = studentService.searchStudentsByName(name);
            if (!matchingStudents.isEmpty()) {
                populateStudentTable(matchingStudents);
            } else {
                JOptionPane.showMessageDialog(this, "No students found with the name: " + name);
            }
        } else {
            // If the search field is empty, display all students
            refreshStudentTable();
        }
    }

    private void populateStudentTable(List<Student> students) {
        // Clear existing data in the table
        tableModel.setRowCount(0);
    
        // Populate the table with matching students
        for (Student student : students) {
            Object[] rowData = {student.getId(), student.getName(), student.getMajor(), student.getJavaMark(),
                    student.getHtmlMark(), student.getCssMark()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        StudentPersistenceService studentPersistenceService = new StudentPersistenceServiceImpl("dataStudent.db");
        StudentService studentService = new StudentServiceImpl(studentPersistenceService);
        StudentManagementUI ui = new StudentManagementUI(studentService);
    }
}
