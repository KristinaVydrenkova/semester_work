package ru.itis.database;
import ru.itis.exceptions.DBException;
import ru.itis.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;

    //language=SQL
    private final static String SQL_INSERT_INTO_USER = "insert into usr(first_name,last_name,patronymic,email,password,role_id) " +
            "values(?,?,?,?,?,?);";
    //language=SQL
    private final static String SQL_SELECT_PASSWORD_BY_EMAIL = "select password from usr where email = ?";
    //language=SQL
    private final static String SQL_FIND_EMAIL = "select id from usr where email = ?";
    //language=SQL
    private final static String SQL_INSERT_INTO_USER_UUID = "insert into usr_uuid(id,uuid,time) values (?,?,now())";
    //language=SQL
    private final static String SQL_FIND_USER_BY_UUID = "select u.id as id,first_name,last_name,patronymic,email,password,role_id, " +
            "date_of_birth, photo from usr u JOIN usr_uuid uu on (u.id = uu.id AND uu.uuid = ?)";
    //language=SQL
    private final static String SQL_FIND_USER_BY_ID = "select * from usr where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_DATE_OF_BIRTH = "update usr set date_of_birth = ? where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_EMAIL = "update usr set email = ? where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_PASSWORD = "update usr set password = ? where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_PHOTO = "update usr set photo = ? where id = ?";
    //language=SQL
    private final static String SQL_DELETE_UUID = "delete from usr_uuid where uuid = ?";
    //language=SQL
    private final static String SQL_DELETE_USER = "delete from usr where id = ?";
    //language=SQL
    private final static String SQL_DELETE_ALL_UUIDS = "delete from usr_uuid where id = ?";

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USER)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3,user.getPatronymic());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setInt(6,user.getRole());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
        return getUserId(user.getEmail());

    }
    @Override
    public int getUserId(String email){
        int id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_EMAIL)) {

            statement.setString(1, email);

            try (ResultSet row = statement.executeQuery()) {
                while(row.next()){
                    id =  row.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
        return id;
    }
    @Override
    public String findPasswordByEmail(String email){
        String password = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_EMAIL)) {

            statement.setString(1, email);

            try (ResultSet row = statement.executeQuery()) {
                while(row.next()){
                    password =  row.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
        return password;
    }
    @Override
    public boolean findEmail(String email){
        int id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_EMAIL)) {
             statement.setString(1, email);

            try (ResultSet row = statement.executeQuery()) {
                while(row.next()){
                    id =  row.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
        return id!=0;
    }
    @Override
    public void addUuid(User user, String uuid){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USER_UUID)) {

            statement.setLong(1, user.getId());
            statement.setString(2, uuid);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        }catch (SQLException e){
            throw new DBException("Connection error");
        }

    }

    @Override
    public User getUserByUUID(String uuid){
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_UUID)) {

            statement.setString(1, uuid);

            try (ResultSet row = statement.executeQuery()) {
                if(row.next()){
                     user.setId(row.getInt("id"));
                     user.setFirstName(row.getString("first_name"));
                     user.setLastName(row.getString("last_name"));
                     user.setPatronymic(row.getString("patronymic"));
                     user.setEmail(row.getString("email"));
                     user.setPassword(row.getString("password"));
                     user.setRole(row.getInt("role_id"));
                     user.setDateOfBirth(row.getString("date_of_birth"));
                     user.setPhotoName(row.getString("photo"));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
        if(user.getEmail()!=null) {
            return user;
        }else{
            return null;
        }
    }
    @Override
    public User getUserById(int id){
        User user = new User();
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){
            statement.setInt(1,id);
            try(ResultSet row = statement.executeQuery()){
                while (row.next()){
                    user.setFirstName(row.getString("first_name"));
                    user.setLastName(row.getString("last_name"));
                    user.setPatronymic(row.getString("patronymic"));
                    user.setEmail(row.getString("email"));
                    user.setPassword(row.getString("password"));
                    user.setRole(row.getInt("role_id"));
                    user.setPhotoName(row.getString("photo"));
                    user.setDateOfBirth(row.getString("date_of_birth"));
                }
            }
        }catch (SQLException e){
            throw new DBException("Connection error");
        }
        user.setId(id);
        return user;
    }

    @Override
    public void updateDateOfBirth(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DATE_OF_BIRTH)) {

            statement.setString(1, user.getDateOfBirth());
            statement.setInt(2, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void updateEmail(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMAIL)) {

            statement.setString(1, user.getEmail());
            statement.setInt(2, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void updatePassword(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {

            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void uploadPhoto(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PHOTO)) {

            statement.setString(1, user.getPhotoName());
            statement.setInt(2, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void removeUUID(String uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_UUID)) {

            statement.setString(1, uuid);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Exception in <Update>");
            }

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }

    @Override
    public void deleteAllUUIDs(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL_UUIDS)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }
}
