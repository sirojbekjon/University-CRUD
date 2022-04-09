package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Faculty;
import com.example.appjparelationships.entity.Grouph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrouphRepository extends JpaRepository<Grouph,Integer> {
    boolean existsByNameAndFaculty_id(String name, Integer faculty_id);
    List<Grouph> findAllByFaculty_University_Id(Integer faculty_university_id);
    Page<Grouph> findAllByFaculty_University_Id(Integer faculty_university_id, Pageable pageable);
    Page<Grouph> findAllByFacultyId(Integer faculty_id, Pageable pageable);
//    @Query("select gr from grouph gr where gr.faculty.university.id=:university_id=:university_id")
//    List<Grouph> getGroupsByUniversityId(Integer university_id);



//    @Query(value ="select *\n"+
//            "from grouph g\n"+
//            "         join faculty f on g.faculty_id = f.id\n"+
//            "         join university u on f.university_id = u.id\n"+
//            "where u.id = :un",nativeQuery = true)
//    List<Grouph> getGrouphByUniversityIdNative(Integer university_id);
}


