package com.example.appjparelationships.repository;


import com.example.appjparelationships.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
//boolean existsByFirstNameAfterAndGrouph_IdAndAddress_id(String firstName, Integer grouph_id, Integer address_id);
    Page<Student> findAllByGrouph_Faculty_UniversityId(Integer grouph_faculty_university_id, Pageable pageable);
    Page<Student> findAllByGrouph_FacultyId(Integer grouph_faculty_id, Pageable pageable);
    Page<Student> findAllByGrouphId(Integer grouph_id, Pageable pageable);
    boolean existsByFirstNameAndLastNameAndGrouph_IdAndAddress_Id(String firstName, String lastName, Integer grouph_id, Integer address_id);
}
