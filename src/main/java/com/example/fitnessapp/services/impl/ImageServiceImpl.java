package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Image;
import com.example.fitnessapp.repositories.ImageRepository;
import com.example.fitnessapp.services.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.fitnessapp.models.entities.ImageEntity;
import com.example.fitnessapp.exceptions.NotFoundException;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper mapper;
    private File path;
    @PersistenceContext
    private EntityManager entityManager;

    public ImageServiceImpl(ImageRepository imageRepository, ModelMapper mapper) {
        this.imageRepository = imageRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void initialize() throws IOException {
        ClassPathResource pdfPath = new ClassPathResource("");

        this.path =new File(pdfPath.getFile().getAbsolutePath() + File.separator + "images");
        if (!path.exists())
            path.mkdir();
    }

    @Override
    public Integer setImage(MultipartFile file) throws IOException {
        var name = StringUtils.cleanPath(file.getOriginalFilename());
        var image = ImageEntity.builder().name(name).type(file.getContentType()).size(((int) file.getSize())).build();
        imageRepository.saveAndFlush(image);
        entityManager.refresh(image);
        Files.write(Path.of(getPath(image)), file.getBytes());
        return image.getId();
    }

    @Override
    public Image getImage(Integer id) throws IOException {
        var image = imageRepository.findById(id).orElseThrow(NotFoundException::new);
        var path = getPath(image);
        var data = Files.readAllBytes(Path.of(path));
        var imageDto = mapper.map(image, Image.class);
        imageDto.setData(data);
        return imageDto;
    }


    @Override
    public void deleteImage(ImageEntity image) throws IOException {
        imageRepository.delete(image);
        var path = getPath(image);
        File file = new File(path);
        file.delete();
    }

    public String[] getPathById(Integer id){
        var image = imageRepository.findById(id).orElseThrow(NotFoundException::new);
        return new String[]{this.getPath(image), image.getName()};
    }

    @Override
    public void deleteImageById(Integer id) throws IOException {
        var image = imageRepository.findById(id).orElseThrow(NotFoundException::new);
        this.deleteImage(image);
    }

    private String getPath(ImageEntity image) {
        var tmp = image.getType().split("/");
        var name = image.getId() + "." + tmp[1];
        var file = this.path + File.separator + name;
        System.out.println(file);
        return file;
    }

}