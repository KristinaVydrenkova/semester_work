package ru.itis.services;

import ru.itis.models.Student;
import ru.itis.models.Task;
import ru.itis.models.User;

import java.util.List;

public interface StudentsService {
    List<Student> getStudents(int teacherId);
    List<Task> getTasks(int teacherId);
    void addStudents(List<String> students, User user);
    void deleteStudents(List<String> students, User user);
    void deleteTask(List<String> tasks);
    void addTask(Task task);
    List<Task> getStudentTasks(int studentId);
    void signOut(User user);
}
