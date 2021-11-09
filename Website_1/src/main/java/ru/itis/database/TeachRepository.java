package ru.itis.database;

import ru.itis.models.Student;
import ru.itis.models.Task;

import java.util.List;

public interface TeachRepository {
    List<Student> getStudentsByTeacherId(int teacherId);
    void addStudent(int studentId, int teacherId);
    boolean findStudent(int studentId, int teacherId);
    void deleteStudent(int studentId, int teacherId);
    List<Task> getTasksByTeacherId(int teacherId);
    void deleteTask(Task task);
    void addTask(Task task);
    List<Integer> getTeachers(int studentId);
    void deleteScores(int id);
    void deleteStudentFromTeachers(int id);
    void deleteTeacherFromTask(int id);
    void deleteTeacherFromTeacherStudents(int id);
}
