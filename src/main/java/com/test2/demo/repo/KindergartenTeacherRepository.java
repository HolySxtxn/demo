package com.test2.demo.repo;


import com.test2.demo.Models.Student;
import com.test2.demo.Models.KindergartenTeacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KindergartenTeacherRepository extends CrudRepository<KindergartenTeacher,Long>{
    List<KindergartenTeacher> findBySurname(String surname);// поиск по точному названию
    List<KindergartenTeacher> findBySurnameContains(String surname);// поиск по символам и содержимому
}
