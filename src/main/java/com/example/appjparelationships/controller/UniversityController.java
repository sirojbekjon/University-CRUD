package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Address;
import com.example.appjparelationships.entity.Faculty;
import com.example.appjparelationships.entity.University;
import com.example.appjparelationships.payload.UniversityDto;
import com.example.appjparelationships.repository.AddressRepository;
import com.example.appjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;

    @RequestMapping(value = "/university",method = RequestMethod.GET)
    public List<University> getUniversitys(){
        List<University> universityList = universityRepository.findAll();
        return universityList;
        }

    @GetMapping("/forMinistry")
    public Page<University> getGrouphListForMinistry(@RequestParam int page){
        //select * from student limit 10 offset 0 where...
        Pageable pageable = PageRequest.of(page,10);
        Page<University> universityPage = universityRepository.findAll(pageable);
        return universityPage;
    }

    @RequestMapping(value = "/university",method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){
         Address address = new Address(universityDto.getCity(),universityDto.getDistrict(),universityDto.getStreet());
         Address saveAddress = addressRepository.save(address);
         University university = new University();
         university.setName(universityDto.getName());
         university.setAddress(saveAddress);
         universityRepository.save(university);
         return "University added";

    }

    @RequestMapping(value = "/university/{id}",method=RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id,@RequestBody UniversityDto universityDto){
        Optional<University> optionalUniversityDto = universityRepository.findById(id);
        if (optionalUniversityDto.isPresent()){
            University university = optionalUniversityDto.get();
            Address saveAddress = university.getAddress();
            saveAddress.setCity(universityDto.getCity());
            saveAddress.setDistrict(universityDto.getDistrict());
            saveAddress.setStreet(universityDto.getStreet());
            university.setName(universityDto.getName());
            university.setAddress(saveAddress);

            addressRepository.save(saveAddress);
            universityRepository.save(university);
            return "Edit successfully" ;
        }
        return "not found";
    }

    @RequestMapping(value = "/university/{id}",method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "University deleted";
    }

}
