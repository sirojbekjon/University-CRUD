package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Faculty;
import com.example.appjparelationships.entity.University;
import com.example.appjparelationships.payload.FacultyDto;
import com.example.appjparelationships.repository.FacultyRepository;
import com.example.appjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping(value = "/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UniversityRepository universityRepository;

    @RequestMapping(value = "/faculty",method = RequestMethod.GET)
            public List<Faculty> getFaculty(){
            List<Faculty> facultyList = facultyRepository.findAll();
            return facultyList;
        }


    @GetMapping
    public List<Faculty>getFaculties(){

        return facultyRepository.findAll();
    }

    @RequestMapping(value = "/faculty",method = RequestMethod.POST)
    public String addFaculty(@RequestBody FacultyDto facultyDto){
        final boolean exists = facultyRepository.existsByNameAndUniversity_id(facultyDto.getName(), facultyDto.getUniversity_id());
        if (exists)
            return "this university such faculty exist";

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversity_id());
        if (optionalUniversity.isEmpty())
            return  "university not found";
         University university = optionalUniversity.get();
         Faculty faculty = new Faculty();
         faculty.setUniversity(university);
         faculty.setName(facultyDto.getName());
         facultyRepository.save(faculty);
         return "Faculty added";
    }



    @GetMapping("/byUniversityId/{university_id}")
    public List<Faculty> getFacultiesByuniversityId(@PathVariable Integer university_id){
        List<Faculty> allByUniversity_id = facultyRepository.findAllByUniversity_id(university_id);

        return allByUniversity_id;
    }

}
