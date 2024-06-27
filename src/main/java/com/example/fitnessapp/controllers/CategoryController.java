package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.Attribute;
import com.example.fitnessapp.models.dtos.AttributeDescription;
import com.example.fitnessapp.models.dtos.Category;
import com.example.fitnessapp.services.AttributeDescriptionService;
import com.example.fitnessapp.services.AttributeService;
import com.example.fitnessapp.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    CategoryService categoryService;
    AttributeService attributeService;
    AttributeDescriptionService attributeDescriptionService;

    public CategoryController(CategoryService service, AttributeService attributeService, AttributeDescriptionService attributeDescriptionService) {
        this.categoryService = service;
        this.attributeService = attributeService;
        this.attributeDescriptionService = attributeDescriptionService;
    }

    @GetMapping("")
    public List<Category> getAll() {
        return categoryService.findAll();
    }
    @GetMapping("{id}")
    public Category getById(@PathVariable Integer id)
    {
        return categoryService.findById(id);
    }
    @GetMapping("/name/{name}")
    public Category getByName(@PathVariable String name)
    {
        return categoryService.findByName(name);
    }
    @GetMapping("/{id}/attributes")
    public List<Attribute> getAttributesByCategory(@PathVariable Integer id){
        return attributeService.findAllByCategoryId(id);
    }
    @GetMapping("/attributes")
    public List<Attribute> getAttributes(){ return attributeService.findAll();}

    @GetMapping("/attributeDescriptions")
    public List<AttributeDescription> getAttributeDescriptions(){ return attributeDescriptionService.findAll();}

    @GetMapping("/attributes/{id}/description")
    public List<AttributeDescription>getAttributeDescriptionsByAttributeId(@PathVariable Integer id)
    {
        return attributeDescriptionService.findByAttributeId(id);
    }


}

