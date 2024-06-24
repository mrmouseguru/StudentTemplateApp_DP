package domain;

import java.util.ArrayList;
import java.util.List;

import domain.model.Student;
import persistence.StudentPersistenceService;



public class StudentServiceImpl implements StudentService {
    private final StudentPersistenceService persistenceService;

    public StudentServiceImpl(StudentPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public void addStudent(Student student) {
        persistenceService.saveStudent(student);
    }

    @Override
    public void removeStudent(int id) {
        persistenceService.deleteStudent(id);
    }

    @Override
    public void editStudent(Student student) {
        persistenceService.updateStudent(student);
    }

    @Override
    public Student findStudent(int id) {
        return persistenceService.getStudentById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return persistenceService.getAllStudents();
    }

    @Override
    public List<Student> searchStudentsByName(String name) {
        List<Student> allStudents = persistenceService.getAllStudents();
        List<Student> matchingStudents = new ArrayList<>();
        for (Student student : allStudents) {
            if (student.getName().contains(name)) {
                matchingStudents.add(student);
            }
        }
        return matchingStudents;
    }


    @Override
    public List<Student> searchFullTextStudentsByName(String query) {
        List<Student> allStudents = persistenceService.getAllStudents();
        List<Student> matchingStudents = new ArrayList<>();

        for (Student student : allStudents) {
            if (containsIgnoreCase(student.getName(), query)) {
                matchingStudents.add(student);
            }
        }

        return matchingStudents;
    }

    private boolean containsIgnoreCase(String str, String query) {
        return str.toLowerCase().contains(query.toLowerCase());
    }

}
