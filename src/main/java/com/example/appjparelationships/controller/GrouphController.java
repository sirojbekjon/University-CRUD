package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.*;
import com.example.appjparelationships.payload.GrouphDto;
import com.example.appjparelationships.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/forMinistry")
    public Page<Grouph> getGrouphListForMinistry(@RequestParam int page){
        //select * from student limit 10 offset 0 where...
        Pageable pageable = PageRequest.of(page,10);
        Page<Grouph> grouphPage = grouphRepository.findAll(pageable);
        return grouphPage;
    }

    @GetMapping("/forUniversity/{universityId}")
    public Page<Grouph> getGrouphListForUniversity(@PathVariable Integer universityId,@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Grouph> grouphPage = grouphRepository.findAllByFaculty_University_Id(universityId,pageable);
        return grouphPage;
    }

    @GetMapping("/forFaculty/{facultyId}")
    public Page<Grouph> getGrouphListForFaculty(@PathVariable Integer facultyId,@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Grouph> grouphPage = grouphRepository.findAllByFacultyId(facultyId,pageable);
        return grouphPage;
    }

    @GetMapping("/byUniversityId/{university_id}")
    public List<Grouph> getGroupsByUniversity(@PathVariable Integer university_id){
        List<Grouph> allByFaculty_university_id=grouphRepository.findAllByFaculty_University_Id(university_id);
        return allByFaculty_university_id;
    }

    @PostMapping("/addGrouph")
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


    @PutMapping("/editGrouph/{id}")
    public String editGrouph(@PathVariable Integer id,@RequestBody GrouphDto grouphDto){
        Optional<Grouph> optionalGrouph = grouphRepository.findById(id);
        if (optionalGrouph.isPresent()){
            Grouph grouph = optionalGrouph.get();
            Faculty facultyRepositoryById = facultyRepository.getById(grouphDto.getFaculty_id());
            grouph.setName(grouphDto.getName());
            grouph.setFaculty(facultyRepositoryById);
        }
        return "grouph has edited";
    }


}
