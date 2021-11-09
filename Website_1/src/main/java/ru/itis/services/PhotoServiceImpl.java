package ru.itis.services;

import ru.itis.database.UserRepository;
import ru.itis.exceptions.PhotoException;
import ru.itis.exceptions.WrongFileException;
import ru.itis.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PhotoServiceImpl implements PhotoService {
    private UserRepository userRepository;
    public final static String PHOTO_STORAGE_PATH = "C:\\Users\\Кристина\\Desktop\\programms\\SemesterWork\\Website_1\\src\\main\\webapp\\resources\\images\\users\\";
    public final static String DEFAULT_PHOTO_NAME = "default_photo.jpg";
    public PhotoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void upload(User user, InputStream inputStream) {
        try {
            userRepository.uploadPhoto(user);
            Files.copy(inputStream, Paths.get(PHOTO_STORAGE_PATH + user.getPhotoName()));
        } catch (IOException e) {
            throw new PhotoException("File error");
        }

    }

    @Override
    public void setPhotoName(User user) {
        if(user.getPhotoName()==null){
            user.setPhotoName(DEFAULT_PHOTO_NAME);
        }
    }

    @Override
    public void checkContentType(String contentType) {
        if(!(contentType.equals("image/jpeg")||(contentType.equals("image/png")))){
            throw new WrongFileException("wrong contentType");
        }
    }

    @Override
    public void deleteOldPhoto(String photoName) {
        if(!(photoName==null||photoName.equals(DEFAULT_PHOTO_NAME))){
            try{
                Files.delete(Paths.get(PHOTO_STORAGE_PATH + photoName));
            }catch (IOException e){
                throw new PhotoException("File error");
            }
        }
    }
}
