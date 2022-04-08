package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.*;
import com.example.appjparelationships.payload.GrouphDto;
import com.example.appjparelationships.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/grouph")
public class GrouphController {

    @Autowired
    GrouphRepository grouphRepository;


    @Autowired
    FacultyRepository facultyRepository;



    //Vazirlik uchun
    //READ
    @GetMapping
    public List<Grouph> getGrouph(){
        final List<Grouph> grouphList = grouphRepository.findAll();
        return grouphList;
    }


    //Universitetning masul hodimi uchun
    @GetMapping("/byUniversityId/{university_id}")
    public List<Grouph> getGroupsByUniversity(@PathVariable Integer university_id){
        List<Grouph> allByFaculty_university_id=grouphRepository.findAllByFaculty_University_Id(university_id);
//        List<Grouph> grouphByUniversityIdNative = grouphRepository.getGrouphByUniversityIdNative(university_id);
        return allByFaculty_university_id;
    }


    @PostMapping
    public String addGroup(@RequestBody GrouphDto grouphDto){
        boolean exists = grouphRepository.existsByNameAndFaculty_id(grouphDto.getName(),grouphDto.getFaculty_id());
        if (exists)
            return "group and faculty already exist";
            Optional<Faculty> optionalFaculty = facultyRepository.findById(grouphDto.getFaculty_id());
            if (optionalFaculty.isEmpty())
            return "faculty not found ";
            Faculty faculty = optionalFaculty.get();
            Grouph addGroup = new Grouph();
            addGroup.setName(grouphDto.getName());
            addGroup.setFaculty(faculty);
            grouphRepository.save(addGroup);
        return "add success";

    }



}
