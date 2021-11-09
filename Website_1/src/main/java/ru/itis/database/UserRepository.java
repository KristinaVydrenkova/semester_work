package ru.itis.database;

import ru.itis.models.Student;
import ru.itis.models.Task;
import ru.itis.models.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    int addUser(User user);
    String findPasswordByEmail(String email);
    boolean findEmail(String email);
    User getUserByUUID(String uuid);
    int getUserId(String email);
    void addUuid(User user, String uuid);
    User getUserById(int id);
    void updateDateOfBirth(User user);
    void updateEmail(User user);
    void updatePassword(User user);
    void uploadPhoto(User user);
    void removeUUID(String uuid);
    void deleteUser(int id);
    void deleteAllUUIDs(int id);

}
