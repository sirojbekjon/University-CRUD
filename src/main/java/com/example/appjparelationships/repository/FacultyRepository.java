package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Integer> {
    boolean existsByNameAndUniversity_id(String name, Integer university_id);
    Page<Faculty> findAllByUniversityId(Integer university_id, Pageable pageable);
    List<Faculty> findAllByUniversity_id(Integer university_id);

}
