package persistence;

import java.util.List;

import domain.model.Student;



public interface StudentPersistenceService {
    void saveStudent(Student student);
    void deleteStudent(int id);
    void updateStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
}
