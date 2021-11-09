package ru.itis.database;

import ru.itis.exceptions.DBException;
import ru.itis.models.Student;
import ru.itis.models.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeachRepositoryImpl implements TeachRepository {
    private final DataSource dataSource;
    //language=SQL
    private final static String SQL_SELECT_STUDENTS_BY_TEACHER_ID = "select ts.s_id as id, first_name,last_name,patronymic,email " +
            "from teacher_student ts join usr u on ts.s_id=u.id where ts.t_id=?";
    //language=SQL
    private final static String SQL_INSERT_STUDENT = "insert into teacher_student(t_id,s_id) values (?,?)";
    //language=SQL
    private final static String SQL_FIND_STUDENT_BY_TEACHER_ID = "select s_id from teacher_student where t_id = ? and s_id = ?";
    //language=SQL
    private final static String SQL_DELETE_STUDENT = "delete from teacher_student where t_id = ? and s_id = ?";
    //language=SQL
    private final static String SQL_SELECT_TASK_BY_TEACHER_ID = "select * from teacher_tasks where t_id = ?";
    //language=SQL
    private final static String SQL_FIND_TASK_BY_ID = "select * from teacher_tasks where id = ?";
    //language=SQL
    private final static String SQL_DELETE_TASK = "delete from teacher_tasks where id = ?";
    //language=SQL
    private final static String SQL_INSERT_TASK = "insert into teacher_tasks(t_id,t_name,task,deadline) values(?,?,?,?)";
    //language=SQL
    private final static String SQL_SELECT_TEACHER_ID_BY_STUDENT_ID = "select t_id from teacher_student where s_id = ?";
    //language=SQL
    private final static String SQL_DELETE_SCORES = "delete from scores where student_id = ?";
    //language=SQL
    private final static String SQL_DELETE_STUDENT_FROM_TEACHER_STUDENT = "delete from teacher_student where s_id = ?";
    //language=SQL
    private final static String SQL_DELETE_TEACHER_FROM_TASKS = "delete from teacher_tasks where t_id = ?";
    //language=SQL
    private final static String SQL_DELETE_TEACHER_FROM_TEACHER_STUDENT = "delete from teacher_student where t_id = ?";

    public TeachRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Student> getStudentsByTeacherId(int teacherId) {
        List<Student> students = new ArrayList<>();
        int i = 1;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_STUDENTS_BY_TEACHER_ID)){
            statement.setInt(1,teacherId);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    Student student = new Student();
                    student.setNumber(i);
                    student.setFirstName(row.getString("first_name"));
                    student.setLastName(row.getString("last_name"));
                    student.setPatronymic(row.getString("patronymic"));
                    student.setEmail(row.getString("email"));
                    students.add(student);
                    i++;
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return students;
    }

    @Override
    public void addStudent(int studentId, int teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_STUDENT)) {

            statement.setInt(1, teacherId);
            statement.setInt(2, studentId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public boolean findStudent(int studentId, int teacherId) {
        int id = 0;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_STUDENT_BY_TEACHER_ID)){
            statement.setInt(1,teacherId);
            statement.setInt(2,studentId);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    id = row.getInt("s_id");
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return id==studentId;
    }

    @Override
    public void deleteStudent(int studentId, int teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STUDENT)) {

            statement.setInt(1, teacherId);
            statement.setInt(2, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public List<Task> getTasksByTeacherId(int teacherId) {
        List<Task> tasks = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TASK_BY_TEACHER_ID)){
            statement.setInt(1,teacherId);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    Task task = new Task();
                    task.setId(row.getInt("id"));
                    task.setTask(row.getString("task"));
                    task.setDeadline(row.getString("deadline"));
                    task.setTeacherName(row.getString("t_name"));
                    tasks.add(task);
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return tasks;
    }

    @Override
    public void deleteTask(Task task) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TASK)) {

            statement.setInt(1, task.getId());

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public List<Integer> getTeachers(int studentId) {
        List<Integer> teacherIds = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TEACHER_ID_BY_STUDENT_ID)){
            statement.setInt(1,studentId);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    int teacherId = row.getInt("t_id");
                    teacherIds.add(teacherId);
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return teacherIds;

    }

    @Override
    public void addTask(Task task) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TASK)) {

            statement.setInt(1, task.getTeacherId());
            statement.setString(2,task.getTeacherName());
            statement.setString(3, task.getTask());
            statement.setString(4,task.getDeadline());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }

    }

    @Override
    public void deleteScores(int id) {
        System.out.println(id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_SCORES)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void deleteStudentFromTeachers(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STUDENT_FROM_TEACHER_STUDENT)) {

            statement.setInt(1, id);

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void deleteTeacherFromTask(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TEACHER_FROM_TASKS)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void deleteTeacherFromTeacherStudents(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TEACHER_FROM_TEACHER_STUDENT)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }
}
