package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import domain.model.Student;



public class StudentPersistenceServiceImpl implements StudentPersistenceService {
    private final String filePath;

    public StudentPersistenceServiceImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveStudent(Student student) {
        List<Student> students = getAllStudents();
        students.add(student);
        saveStudentsToFile(students);
    }

    @Override
    public void deleteStudent(int id) {
        List<Student> students = getAllStudents();
        students.removeIf(student -> student.getId() == id);
        saveStudentsToFile(students);
    }

    @Override
    public void updateStudent(Student student) {
        List<Student> students = getAllStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                break;
            }
        }
        saveStudentsToFile(students);
    }

    @Override
    public Student getStudentById(int id) {
        List<Student> students = getAllStudents();
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                Student student = (Student) inputStream.readObject();
                students.add(student);
            }
        } catch (EOFException e) {
            // End of file, do nothing
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return students;
    }

    private void saveStudentsToFile(List<Student> students) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Student student : students) {
                outputStream.writeObject(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
