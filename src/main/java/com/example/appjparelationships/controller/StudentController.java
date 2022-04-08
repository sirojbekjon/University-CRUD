package com.example.appjparelationships.controller;


import com.example.appjparelationships.entity.Address;
import com.example.appjparelationships.entity.Grouph;
import com.example.appjparelationships.entity.Student;
import com.example.appjparelationships.entity.Subject;
import com.example.appjparelationships.payload.StudentDto;
import com.example.appjparelationships.repository.AddressRepository;
import com.example.appjparelationships.repository.GrouphRepository;
import com.example.appjparelationships.repository.StudentRepository;
import com.example.appjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    GrouphRepository grouphRepository;


    @GetMapping
    public List<Student>getStudent(){
        return studentRepository.findAll();
    }


        @PostMapping
        @RequestMapping(method = RequestMethod.POST)
    public String addStudent(@RequestBody StudentDto studentDto){
        final List<Student> studentList = studentRepository.findAll();
        final boolean exists = studentRepository.existsByFirstNameAndLastNameAndGrouph_IdAndAddress_Id(
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getGrouph_id(),
                studentDto.getAddress_id()
        );
        if (exists)
            return "student already exsest";

            final Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddress_id());
            final Optional<Subject> optionalSubject = subjectRepository.findById(studentDto.getSubjects_id());
            final Optional<Grouph> optionalGrouph = grouphRepository.findById(studentDto.getGrouph_id());
            Address saveAddress = optionalAddress.get();
            Subject saveSubject = optionalSubject.get();
            Grouph saveGrouph = optionalGrouph.get();


            Student student = new Student();
            student.setAddress(saveAddress);
            List<Subject> subjectList = new ArrayList<>();
            subjectList.add(saveSubject);
            student.setSubjects(subjectList);
            student.setGrouph(saveGrouph);
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            studentRepository.save(student);
            return "addedd successfully";



    }

}
