package domain;


import java.util.List;

import domain.model.Student;



public interface StudentService {
    void addStudent(Student student);
    void removeStudent(int id);
    void editStudent(Student student);
    Student findStudent(int id);
    List<Student> getAllStudents();
    List<Student> searchStudentsByName(String name);

    List<Student> searchFullTextStudentsByName(String query);

}