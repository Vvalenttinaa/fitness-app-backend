package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Image;
import com.example.fitnessapp.models.entities.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image setImage(MultipartFile file, Integer programId)throws IOException;

    Image setProfileImage(MultipartFile file, Integer userId) throws IOException;

    Image getImage(Integer id)throws IOException;
    Image getImageByProgramId(Integer programId) throws IOException;
    void deleteImage(ImageEntity image)throws IOException;
    String[] getPathById(Integer id);
    void deleteImageById(Integer id)throws IOException;
    Image getByImageId(Integer id) throws IOException;
}
