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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/allStudent")
    public List<Student> getallStudent(){
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page){
        //select * from student limit 10 offset 0 where...
        Pageable pageable = PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page){
        //select * from student limit 10 offset 0 where....
        Pageable pageable = PageRequest.of(page,10);
        return studentRepository.findAllByGrouph_Faculty_UniversityId(universityId, pageable);
    }
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                     @RequestParam int page){
        //select * from student limit 10 offset 0 where....
        Pageable pageable = PageRequest.of(page,10);
        return studentRepository.findAllByGrouph_FacultyId(facultyId,pageable);
    }
    @GetMapping("/forGrouph/{grouphId}")
    public Page<Student> getStudentListForGrouph(@PathVariable Integer grouphId,
                                               @RequestParam int page){
        //select * from student limit 10 offset 0 where....
        Pageable pageable = PageRequest.of(page,1);
        return studentRepository.findAllByGrouphId(grouphId,pageable);
    }
    @PostMapping("/addStudent")
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

    @PutMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Integer id,@RequestBody StudentDto studentDto){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            Student student1 = optionalStudent.get();
            final Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddress_id());
            final Optional<Subject> optionalSubject = subjectRepository.findById(studentDto.getSubjects_id());
            final Optional<Grouph> optionalGrouph = grouphRepository.findById(studentDto.getGrouph_id());
            Address saveAddress = optionalAddress.get();
            List<Subject> saveSubject = (List<Subject>) optionalSubject.get();
            Grouph saveGrouph = optionalGrouph.get();

            student1.setFirstName(studentDto.getFirstName());
            student1.setLastName(studentDto.getLastName());
            student1.setSubjects(saveSubject);
            student1.setGrouph(saveGrouph);
            student1.setAddress(saveAddress);
        }
        return "student has edited";
    }


}
