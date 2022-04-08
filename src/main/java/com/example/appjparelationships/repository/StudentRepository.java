package com.example.appjparelationships.repository;


import com.example.appjparelationships.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
//boolean existsByFirstNameAfterAndGrouph_IdAndAddress_id(String firstName, Integer grouph_id, Integer address_id);
    boolean existsByFirstNameAndLastNameAndGrouph_IdAndAddress_Id(String firstName, String lastName, Integer grouph_id, Integer address_id);
}
