package ru.itis.services;

import ru.itis.models.User;

import java.io.InputStream;

public interface PhotoService {
    void upload(User user, InputStream inputStream);
    void setPhotoName(User user);
    void checkContentType(String contentType);
    void deleteOldPhoto(String photoName);
}
