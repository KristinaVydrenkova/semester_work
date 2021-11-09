package ru.itis.database;

import ru.itis.dto.EntryTestForm;
import ru.itis.exceptions.DBException;
import ru.itis.models.Point;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestsRepositoryImpl implements TestsRepository {
    private final DataSource dataSource;

    //language=SQL
    private final static String SQL_SELECT_TEST_ANSWERS = "select * from test_answers where test_number = ?";
    //language=SQL
    private final static String SQL_INSERT_INTO_SCORES = "insert into scores(student_id,test_number,scores,possible_scores) values (?,?,?,?)";
    //language=SQL
    private final static String SQL_GET_SCORES = "select scores from scores where student_id = ? and test_number = ?";
    //language=SQL
    private final static String SQL_GET_ALL_SCORES = "select * from scores where student_id = ?";

    public TestsRepositoryImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<String> getTestAnswers(int testNumber) {
        List<String> answers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TEST_ANSWERS)){
            statement.setInt(1,testNumber);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    answers.add(row.getString("answer"));
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return answers;
    }

    @Override
    public void addScores(int scores, int studentId, int testNumber,int numberOfPossibleScores) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_SCORES)) {

            statement.setInt(1, studentId);
            statement.setInt(2, testNumber);
            statement.setInt(3,scores);
            statement.setInt(4,numberOfPossibleScores);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        }catch (SQLException e){
            throw new DBException("Connection error");        }
    }

    @Override
    public int getScores(int studentId,int testNumber) {
        int scores = -1;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SCORES)){
            statement.setInt(1,studentId);
            statement.setInt(2,testNumber);
            try(ResultSet row = statement.executeQuery()){
                if (row.next()){
                    scores = row.getInt("scores");
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return scores;
    }

    @Override
    public List<Point> getAllScores(int studentId) {
        List<Point> points = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_SCORES)){
            statement.setInt(1,studentId);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    Point point = new Point();
                     point.setTestNumber(row.getInt("test_number"));
                     point.setScores(row.getInt("scores"));
                     point.setMaxScore(row.getInt("possible_scores"));
                     points.add(point);
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        return points;
    }
}
