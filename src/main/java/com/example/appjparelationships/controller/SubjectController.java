package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Subject;
import com.example.appjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;
    //CREATE
    @PostMapping("/postSubject")
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

    @PutMapping("/editSubject/{id}")
    public String editsubject(@PathVariable Integer id,@RequestBody Subject subject){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()){
        Subject subject1 = optionalSubject.get();
        subject1.setName(subject.getName());
        subjectRepository.save(subject1);
        }
        return "subject has edited";
    }


    @DeleteMapping("/deleteSubject/{id}")
    public String deleteSubject(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "subject has deleted";
    }



}
