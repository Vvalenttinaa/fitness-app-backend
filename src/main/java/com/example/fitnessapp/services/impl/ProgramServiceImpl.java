package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Comment;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Reply;
import com.example.fitnessapp.models.dtos.Subscription;
import com.example.fitnessapp.models.entities.*;
import com.example.fitnessapp.models.requests.*;
import com.example.fitnessapp.repositories.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.fitnessapp.exceptions.NotFoundException;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @PersistenceContext
    EntityManager entityManager;


    public ProgramServiceImpl(ModelMapper modelMapper, ProgramRepository programRepository, CategoryEntityRepository categoryRepository, AttributeRepository attributeRepository, AttributeDescriptionRepository attributeDescriptionRepository, ImageRepository imageRepository, InstructorRepository instructorRepository, LocationRepository locationRepository, CommentRepository commentRepository, UserRepository userRepository, ReplyRepository replyRepository) {
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
    }

    @Override
    public List<Program> findAll(String category, String attribute) {
        List<Program> programs = new ArrayList<>();
        if (category == null && attribute == null) {
            List<ProgramEntity> programEntities = programRepository.findAll();
            List<Program> programsDto=programEntities.stream().map(e->modelMapper.map(e,Program.class)).collect(Collectors.toList());
            return programsDto;
        } else if (attribute == null && category != null) {
            int idCat = categoryRepository.findCategoryEntityByName(category).getId();
            List<ProgramEntity> programEntities = programRepository.findAllByCategoryId(idCat);
            return programEntities.stream()
                    .map(programEntity -> modelMapper.map(programEntity, Program.class))
                    .collect(Collectors.toList());
        } else {
            int idCat = categoryRepository.findCategoryEntityByName(category).getId();
            List<AttributeEntity> attributes = attributeRepository.findAllByCategoryId(idCat);

            List<Integer> attributeDescriptionIds = new ArrayList<>();
            for (AttributeEntity attributeEntity : attributes) {
                attributeDescriptionIds.add(attributeDescriptionRepository.findAllByAttributeId(attributeEntity.getId()).getId());
            }

            List<Integer> programIds = new ArrayList<>();
            for (Integer attributeDescriptionId : attributeDescriptionIds) {
                programIds.add(attributeDescriptionRepository.findAttributedescriptionEntityById(attributeDescriptionId).getProgramId());
            }

            for (Integer programId : programIds) {
                Optional<ProgramEntity> programEntity = programRepository.findById(programId);
                programEntity.ifPresent(entity -> programs.add(modelMapper.map(entity, Program.class)));
            }
        }
        return programs;
    }

    @Override
    public Program findById(Integer id) {
         ProgramEntity programEntity = this.programRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(programEntity, Program.class);
    }

    @Override
    public Program insert(ProgramRequest programRequest) {

        ProgramEntity programEntity = modelMapper.map(programRequest, ProgramEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByName(programRequest.getCategoryName());
        programEntity.setCategoryId(categoryEntity.getId());
        programEntity.setCategoryByCategoryId(categoryEntity);

        InstructorEntity instructorEntity = instructorRepository.findByName(programRequest.getInstructorName());
        programEntity.setInstructorId(instructorEntity.getId());
        programEntity.setInstructorByInstructorId(instructorEntity);

        LocationEntity locationEntity = locationRepository.findByName(programRequest.getLocationName());
        programEntity.setLocationId(locationEntity.getId());
        programEntity.setLocationByLocationId(locationEntity);
        programEntity = programRepository.saveAndFlush(programEntity);

        //kategorija, instruktor, lokacija su id-jevi

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
            attributedescriptionEntity.setDescription(attributeDescriptionRequest.getValue());
            AttributeEntity attributeEntity = attributeRepository.findByName(attributeDescriptionRequest.getAttributeName()).orElseThrow(NotFoundException::new);
            attributedescriptionEntity.setAttributeId(attributeEntity.getId());
            attributedescriptionEntity.setAttributeByAttributeId(attributeEntity);
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
    public List<Program> searchProgram(SearchRequest searchRequest) {
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
        List<ProgramEntity> programEntities = typedQuery.getResultList();
        List<Program> programs = programEntities.stream().map(e -> modelMapper.map(e, Program.class)).toList();
        return programs;
    }


    @Override
    public Comment comment(CommentRequest commentRequest) {

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
        return commentRepository.findAllByProgramId(id).stream()
                .map(programEntity -> modelMapper.map(programEntity, Comment.class))
                .collect(Collectors.toList());
    }
    @Override
    public  List<Reply> findAllRepliesByComment(Integer id)
    {
        return replyRepository.findAllByCommentId(id).stream()
                .map(replyEntity -> modelMapper.map(replyEntity, Reply.class))
                .collect(Collectors.toList());
    }
}
