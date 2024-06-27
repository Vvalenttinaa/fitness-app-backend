package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Image;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.repositories.ImageRepository;
import com.example.fitnessapp.repositories.ProgramRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.ImageService;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.UserService;
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
import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProgramRepository programRepository;
    private final ModelMapper mapper;
    private final LoggerService loggerService;
    private File path;
    private final UserService userService;

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ImageServiceImpl(ImageRepository imageRepository, ProgramRepository programRepository, ModelMapper mapper, LoggerService loggerService, UserService userService, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.programRepository = programRepository;
        this.mapper = mapper;
        this.loggerService = loggerService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initialize() throws IOException {
        ClassPathResource pdfPath = new ClassPathResource("");
        this.path =new File(pdfPath.getFile().getAbsolutePath() + File.separator + "images");
        if (!path.exists())
            path.mkdir();
    }

    @Override
    public Image setImage(MultipartFile file, Integer programId) throws IOException {
        loggerService.addLog("Saving details about image in database, programId is" + programId);

        var name = StringUtils.cleanPath(file.getOriginalFilename());
     //   var image = ImageEntity.builder().name(name).type(file.getContentType()).size(((int) file.getSize())).programId(programId).build();
        ImageEntity imageEntity=new ImageEntity();
        imageEntity.setType(file.getContentType());
        imageEntity.setSize((int)file.getSize());
        imageEntity.setName(file.getName());
        imageEntity.setProgramId(programId);
        imageEntity.setProgramByProgramId(programRepository.findById(programId).get());
        imageRepository.saveAndFlush(imageEntity);
        entityManager.refresh(imageEntity);
        Files.write(Path.of(getPath(imageEntity)), file.getBytes());
        return mapper.map(imageEntity, Image.class);
//        return imageEntity.getId();
    }

    @Override
    public Image setProfileImage(MultipartFile file, Integer userId) throws IOException {
        loggerService.addLog("Saving profile image");

        var name = StringUtils.cleanPath(file.getOriginalFilename());
        //   var image = ImageEntity.builder().name(name).type(file.getContentType()).size(((int) file.getSize())).programId(programId).build();
        ImageEntity imageEntity=new ImageEntity();
        imageEntity.setType(file.getContentType());
        imageEntity.setSize((int)file.getSize());
        imageEntity.setName(file.getName());
        imageEntity.setProgramId(0);
        imageEntity.setProgramByProgramId(null);
        imageRepository.saveAndFlush(imageEntity);
        entityManager.refresh(imageEntity);
        Files.write(Path.of(getPath(imageEntity)), file.getBytes());

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        userEntity.setAvatar(imageEntity.getId().toString());
        userRepository.saveAndFlush(userEntity);

        return mapper.map(imageEntity, Image.class);
//        return imageEntity.getId();
    }

    @Override
    public Image getImage(Integer id) throws IOException {
        loggerService.addLog("Finding imaage by id " + id);

        ImageEntity image = imageRepository.findById(id).orElseThrow(NotFoundException::new);
        var path = getPath(image);
        var data = Files.readAllBytes(Path.of(path));
        var imageDto = mapper.map(image, Image.class);
        imageDto.setData(data);
        return imageDto;
    }

    @Override
    public Image getImageByProgramId(Integer programId) throws IOException{
        loggerService.addLog("Finding image by program id " + programId);
        List<ImageEntity> images = imageRepository.findAllByProgramId(programId);
        if(images.size()==0){
            throw  new NotFoundException();
        }
        loggerService.addLog("duzina niza slika je" + images.size());
        loggerService.addLog("prvi nadjen pi id-ju je" + images.get(0).getName());
        var path = getPath(images.get(0));
        loggerService.addLog("founded path is" + path.toString());
        var data = Files.readAllBytes(Path.of(path));
        var imageDto = mapper.map(images.get(0), Image.class);
        imageDto.setData(data);
        return imageDto;
    }

    @Override
    public void deleteImage(ImageEntity image) throws IOException {
        loggerService.addLog("Deleting image" + image.getName());

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
        loggerService.addLog("Deleting image by id " + id );
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

    public Image getByImageId(Integer id) throws IOException{
        loggerService.addLog("Getting image by id" + id);
        ImageEntity image = this.imageRepository.findById(id).orElseThrow(NotFoundException::new);
        var path = getPath(image);
        var data = Files.readAllBytes(Path.of(path));
        var imageDto = mapper.map(image, Image.class);
        imageDto.setData(data);
        return imageDto;
    }
}