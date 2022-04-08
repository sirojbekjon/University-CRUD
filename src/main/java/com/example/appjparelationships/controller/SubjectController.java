package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Subject;
import com.example.appjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;
    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestBody Subject subject){
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "this subject already exist";
        subjectRepository.save(subject);
          return "Save successfully";
    }

    @GetMapping
    public List<Subject> getSubject(){
        final List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }



}
