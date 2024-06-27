package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Comment;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Reply;
import com.example.fitnessapp.models.dtos.Status;
import com.example.fitnessapp.models.entities.*;
import com.example.fitnessapp.models.requests.*;
import com.example.fitnessapp.repositories.*;
import com.example.fitnessapp.services.ImageService;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.ProgramService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.fitnessapp.exceptions.NotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {
    private final ModelMapper modelMapper;
    private final ProgramRepository programRepository;
    private final CategoryEntityRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeDescriptionRepository attributeDescriptionRepository;
    private final ImageRepository imageRepository;
    private final InstructorRepository instructorRepository;
    private final LocationRepository locationRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final StatusRepository statusRepository;
    private final ImageService imageService;
    private final LoggerService loggerService;
    @PersistenceContext
    EntityManager entityManager;


    public ProgramServiceImpl(ModelMapper modelMapper, ProgramRepository programRepository, CategoryEntityRepository categoryRepository, AttributeRepository attributeRepository, AttributeDescriptionRepository attributeDescriptionRepository, ImageRepository imageRepository, InstructorRepository instructorRepository, LocationRepository locationRepository, CommentRepository commentRepository, UserRepository userRepository, ReplyRepository replyRepository, StatusRepository statusRepository, ImageService imageService, LoggerService loggerService) {
        this.modelMapper = modelMapper;
        this.programRepository = programRepository;
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
        this.attributeDescriptionRepository = attributeDescriptionRepository;
        this.imageRepository = imageRepository;
        this.instructorRepository = instructorRepository;
        this.locationRepository = locationRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.statusRepository = statusRepository;
        this.imageService = imageService;
        this.loggerService = loggerService;
    }

//    @Override
//    public List<Program> findAll(String category, String attribute) {
//        List<Program> programs = new ArrayList<>();
//        if (category == null && attribute == null) {
//            List<ProgramEntity> programEntities = programRepository.findAll();
//            List<Program> programsDto=programEntities.stream().map(e->modelMapper.map(e,Program.class)).collect(Collectors.toList());
//            return programsDto;
//        } else if (attribute == null && category != null) {
//            int idCat = categoryRepository.findCategoryEntityByName(category).getId();
//            List<ProgramEntity> programEntities = programRepository.findAllByCategoryId(idCat);
//            return programEntities.stream()
//                    .map(programEntity -> modelMapper.map(programEntity, Program.class))
//                    .collect(Collectors.toList());
//        } else {
//            int idCat = categoryRepository.findCategoryEntityByName(category).getId();
//            List<AttributeEntity> attributes = attributeRepository.findAllByCategoryId(idCat);
//
//            List<Integer> attributeDescriptionIds = new ArrayList<>();
//            for (AttributeEntity attributeEntity : attributes) {
//                attributeDescriptionIds.add(attributeDescriptionRepository.findAllByAttributeId(attributeEntity.getId()));
//            }
//
//            List<Integer> programIds = new ArrayList<>();
//            for (Integer attributeDescriptionId : attributeDescriptionIds) {
//                programIds.add(attributeDescriptionRepository.findAttributedescriptionEntityById(attributeDescriptionId).getProgramId());
//            }
//
//            for (Integer programId : programIds) {
//                Optional<ProgramEntity> programEntity = programRepository.findById(programId);
//                programEntity.ifPresent(entity -> programs.add(modelMapper.map(entity, Program.class)));
//            }
//        }
//        return programs;
//    }

    @Override
    public Page<Program> findAll(int page, int size) {
        loggerService.addLog("Finding all programs");

        Pageable pageable = PageRequest.of(page, size);
        Page<ProgramEntity> programEntitiesPage = programRepository.findAll(pageable);
        return programEntitiesPage.map(programEntity -> modelMapper.map(programEntity, Program.class));
    }
    @Override
    public Program findById(Integer id) {
        loggerService.addLog("Find program by id " + id);

        ProgramEntity programEntity = this.programRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(programEntity, Program.class);
    }

    @Override
    public Program insert(ProgramRequest programRequest) {

        loggerService.addLog("Inserting program " + programRequest.getName());


        ProgramEntity programEntity = modelMapper.map(programRequest, ProgramEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(programRequest.getCategoryId());
        programEntity.setCategoryId(categoryEntity.getId());
        programEntity.setCategoryByCategoryId(categoryEntity);

        InstructorEntity instructorEntity = new InstructorEntity();
        if(instructorRepository.existsByName(programRequest.getInstructorName())) {
            instructorEntity = instructorRepository.findByName(programRequest.getInstructorName());
        }else{
            instructorEntity.setName(programRequest.getInstructorName());
            instructorEntity = instructorRepository.saveAndFlush(instructorEntity);
        }
        programEntity.setInstructorId(instructorEntity.getId());
        programEntity.setInstructorByInstructorId(instructorEntity);

        LocationEntity locationEntity = new LocationEntity();
        if(locationRepository.existsByName(programRequest.getLocationName())) {
            locationEntity = locationRepository.findByName(programRequest.getLocationName());
        }else{
            locationEntity.setName(programRequest.getLocationName());
            locationEntity = locationRepository.saveAndFlush(locationEntity);
        }
        programEntity.setLocationId(locationEntity.getId());
        programEntity.setLocationByLocationId(locationEntity);
        programEntity = programRepository.saveAndFlush(programEntity);

        //kategorija, instruktor, lokacija su id-jevi

        if(programRequest.getImages() != null && programRequest.getImages().size() > 0)
        for (ImageRequest image : programRequest.getImages()) {
            ImageEntity imageEntity = modelMapper.map(image, ImageEntity.class);
            imageEntity.setId(null);
            imageEntity.setProgramId(programEntity.getId());
            imageEntity.setProgramByProgramId(programEntity);
            imageRepository.saveAndFlush(imageEntity);
        }

        //attributedescription (attributeName, value)
        for(AttributeDescriptionRequest attributeDescriptionRequest : programRequest.getAttributes())
        {
            AttributedescriptionEntity attributedescriptionEntity = new AttributedescriptionEntity();
            attributedescriptionEntity.setProgramId(programEntity.getId());
            attributedescriptionEntity.setDescription(attributeDescriptionRequest.getName());
            attributedescriptionEntity.setAttributeId(attributeDescriptionRequest.getAttributeId());
            attributedescriptionEntity.setAttributeByAttributeId(attributeRepository.findById(attributeDescriptionRequest.getAttributeId()).orElseThrow(NotFoundException::new));
            attributedescriptionEntity.setProgramByProgramId(programEntity);
            attributeDescriptionRepository.saveAndFlush(attributedescriptionEntity);
        }
        programEntity = programRepository.saveAndFlush(programEntity);

        return modelMapper.map(programEntity, Program.class);
    }

//    @Override
//    public List<Program> searchProgram(SearchRequest searchRequest)
//    {
//            List<ProgramEntity> programEntities = new ArrayList<>();
//            //treba naci program koji ima kategoriju, atribut, vrijednost atributa
//            if (searchRequest.getCategoryName() != null && !searchRequest.getCategoryName().isEmpty()) {
//                int id = categoryRepository.findCategoryEntityByName(searchRequest.getCategoryName()).getId();
//                programEntities = programRepository.findAllByCategoryId(id);
//            }
//            if (!searchRequest.getAtributes().isEmpty() && searchRequest.getAtributes() != null) {
//                for (AttributeDescriptionRequest attribute : searchRequest.getAtributes()) {
//                    Integer attributeId = attributeRepository.findByName(attribute.getAttributeName()).orElseThrow(NotFoundException::new).getId();
//                    List<AttributedescriptionEntity> attributeValues =
//                            attributeDescriptionRepository.findAllByDescriptionAndAttributeId(attribute.getValue(), attributeId);
//                    if (attributeValues.isEmpty())
//                        throw new NotFoundException();
//                    for (AttributedescriptionEntity attr : attributeValues) {
//                        Optional<ProgramEntity> program = programRepository.findById(attr.getProgramId());
//                        if (program.isPresent()) {
//                            programEntities.add(program.orElse(null));
//                        }
//                    }
//                }
//            }
//            return programEntities.stream()
//                    .map(programEntity -> modelMapper.map(programEntity, Program.class))
//                    .collect(Collectors.toList());
//    }
//
//    @Override
//    public /* Page */List <Program> searchProgram(/* Pageable page, */ SearchRequest searchRequest) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<ProgramEntity> criteriaQuery = criteriaBuilder.createQuery(ProgramEntity.class);
//        Root<ProgramEntity> root = criteriaQuery.from(ProgramEntity.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        // Dodajemo predikat za završene programe
//    //    predicates.add(criteriaBuilder.equal(root.get("finished"), 0));
//
//        // Pretraga po nazivu programa, kategoriji, atributu ili opisu atributa
//        if (searchRequest.getSearch() != null) {
//            String searchValue = "%" + searchRequest.getSearch() + "%";
//            Predicate searchPredicate = criteriaBuilder.or(
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchValue),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), searchValue),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.join("attributeValues").get("attribute").get("name")), searchValue),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.join("attributeValues").get("description")), searchValue)
//            );
//            predicates.add(searchPredicate);
//        }
//
//        if (searchRequest.getCategory() != null) {
//            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("category").get("name")), searchRequest.getCategory().toLowerCase()));
//        }
//
//        if (searchRequest.getAttribute() != null) {
//            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.join("attributeValues").get("attribute").get("name")), searchRequest.getAttribute().toLowerCase()));
//        }
//
//        if (searchRequest.getDescription() != null) {
//            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("attributeValues").get("description")), "%" + searchRequest.getDescription().toLowerCase() + "%"));
//        }
//
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//        TypedQuery<ProgramEntity> typedQuery = entityManager.createQuery(criteriaQuery);
//        int totalResults = typedQuery.getResultList().size();
//
////        typedQuery.setFirstResult((int) page.getOffset());
////        typedQuery.setMaxResults(page.getPageSize());
//        List<ProgramEntity> programEntities = typedQuery.getResultList();
//        List<Program> programs = programEntities.stream().map(e -> modelMapper.map(e, Program.class)).toList();
//        return programs;
//      //  return new PageImpl<>(programs, page, totalResults);
//    }

    @Override
    public Page<Program> searchProgram(SearchRequest searchRequest, Pageable page) {
        loggerService.addLog("Searching program for specified programs");

        if(searchRequest.getSearch() == null && searchRequest.getAttribute() == null && searchRequest.getCategory() == null && searchRequest.getDescription() == null){
            return findAll(page.getPageNumber(), page.getPageSize());
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProgramEntity> criteriaQuery = criteriaBuilder.createQuery(ProgramEntity.class);
        Root<ProgramEntity> root = criteriaQuery.from(ProgramEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        // Pretraga po nazivu programa, kategoriji, atributu ili opisu atributa
        if (searchRequest.getSearch() != null) {
            String searchValue = "%" + searchRequest.getSearch() + "%";
            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("categoryByCategoryId").get("name")), searchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("categoryByCategoryId").join("attributesById").get("name")), searchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("attributedescriptionsById").get("description")), searchValue)
            );
            predicates.add(searchPredicate);
        }

        // Pretraga po kategoriji
        if (searchRequest.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("categoryByCategoryId").get("name")), searchRequest.getCategory().toLowerCase()));
        }

        // Pretraga po atributu
        if (searchRequest.getAttribute() != null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.join("attributedescriptionsById").join("attributeByAttributeId").get("name")), searchRequest.getAttribute().toLowerCase()));
        }

        // Pretraga po opisu atributa
        if (searchRequest.getDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("attributedescriptionsById").get("description")), searchRequest.getDescription().toLowerCase()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ProgramEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        int totalResults = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) page.getOffset());
        typedQuery.setMaxResults(page.getPageSize());
        List<ProgramEntity> programEntities = typedQuery.getResultList();
        List<Program> programs = programEntities.stream().map(e -> modelMapper.map(e, Program.class)).toList();

        return new PageImpl<>(programs, page, totalResults);
//        int totalResults = typedQuery.getResultList().size();
//
//        typedQuery.setFirstResult(page);
//        typedQuery.setMaxResults(size);
//
//        List<ProgramEntity> programEntities = typedQuery.getResultList();
//        List<Program> programs = programEntities.stream().map(e -> modelMapper.map(e, Program.class)).toList();
//        return new PageImpl<>(programs, PageRequest.of(page, size), totalResults);
    }


    @Override
    public Comment comment(CommentRequest commentRequest) {
        loggerService.addLog("Posting comment " + commentRequest.getContent());

        ProgramEntity program = programRepository.findById(commentRequest.getProgramId()).orElseThrow(NotFoundException::new);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setProgram(program);
        commentEntity.setUser(userRepository.getById(1));
        commentEntity = commentRepository.saveAndFlush(commentEntity);

        return modelMapper.map(commentEntity, Comment.class);
    }

    @Override
    public Reply reply(ReplyRequest replyRequest)
    {
        loggerService.addLog("Posting reply " + replyRequest.getContent());

        CommentEntity comment = commentRepository.findById(replyRequest.getCommentId()).orElseThrow(NotFoundException::new);
        UserEntity user = userRepository.findById(replyRequest.getUserId()).orElseThrow(NotFoundException::new);
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setUser(user);
        replyEntity.setContent(replyRequest.getContent());
        replyEntity.setComment(comment);
        replyEntity = replyRepository.saveAndFlush(replyEntity);
        return modelMapper.map(replyEntity, Reply.class);
    }
    @Override
    public List<Comment> findAllCommentsByProgram(Integer id) {
        loggerService.addLog("Finding all comments for program");

        return commentRepository.findAllByProgramId(id).stream()
                .map(programEntity -> modelMapper.map(programEntity, Comment.class))
                .collect(Collectors.toList());
    }
    @Override
    public  List<Reply> findAllRepliesByComment(Integer id)
    {
        loggerService.addLog("Finding all replies for comment" + id);

        return replyRepository.findAllByCommentId(id).stream()
                .map(replyEntity -> modelMapper.map(replyEntity, Reply.class))
                .collect(Collectors.toList());
    }

    @Override
    public Status findStatus(Integer userId, Integer programId)
    {
        return modelMapper.map(statusRepository.findByUserIdAndProgramId(userId, programId), Status.class);
    }
}
