package ru.itis.services;

import ru.itis.database.TeachRepository;
import ru.itis.database.UserRepository;
import ru.itis.exceptions.NoSuchEmailException;
import ru.itis.models.Student;
import ru.itis.models.Task;
import ru.itis.models.User;

import java.util.ArrayList;
import java.util.List;

public class StudentsServiceImpl implements StudentsService {
    private TeachRepository teachRepository;
    private UserRepository userRepository;

    public StudentsServiceImpl(TeachRepository teachRepository,UserRepository userRepository) {
        this.teachRepository = teachRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Student> getStudents(int teacherId) {
        return teachRepository.getStudentsByTeacherId(teacherId);
    }

    @Override
    public void addStudents(List<String> emails,User user) {
        for (String email: emails) {
            Student student = new Student();
            student.setEmail(email);
            if(userRepository.getUserId(student.getEmail())==0){
                throw new NoSuchEmailException(student.getEmail());
            }
            student.setId(userRepository.getUserId(student.getEmail()));
            if(!teachRepository.findStudent(student.getId(),user.getId())) {
                teachRepository.addStudent(student.getId(), user.getId());
            }
        }
    }

    @Override
    public void deleteStudents(List<String> students, User user) {
        for(String student:students){
            int studentId = userRepository.getUserId(student);
            if(teachRepository.findStudent(studentId,user.getId())){
                teachRepository.deleteStudent(studentId,user.getId());
            }
        }
    }

    @Override
    public List<Task> getTasks(int teacherId) {
        return teachRepository.getTasksByTeacherId(teacherId);
    }

    @Override
    public void deleteTask(List<String> tasks) {
        for(String task:tasks){
            Task task1 = new Task();
            task1.setId(Integer.parseInt(task));
            teachRepository.deleteTask(task1);
        }
    }

    @Override
    public void addTask(Task task) {
        teachRepository.addTask(task);
    }

    @Override
    public List<Task> getStudentTasks(int studentId) {
        List<Integer> teacherIds = teachRepository.getTeachers(studentId);
        List<Task> tasks = new ArrayList<>();
        for(int teacherId: teacherIds){
            tasks.addAll(teachRepository.getTasksByTeacherId(teacherId));
        }
        return tasks;
    }

    @Override
    public void signOut(User user) {
        if(user.getRole()==1){
            teachRepository.deleteTeacherFromTask(user.getId());
            teachRepository.deleteTeacherFromTeacherStudents(user.getId());
            teachRepository.deleteScores(user.getId());
        }else{
            teachRepository.deleteScores(user.getId());
            teachRepository.deleteStudentFromTeachers(user.getId());
        }
    }
}
