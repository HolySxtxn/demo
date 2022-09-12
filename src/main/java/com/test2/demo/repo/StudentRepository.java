package com.test2.demo.repo;

import com.test2.demo.Models.Student;
import com.test2.demo.Models.KindergartenTeacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StudentRepository extends CrudRepository<Student,Long>{
    List<Student> findBySurname(String surname);// поиск по точному названию
    List<Student> findBySurnameContains(String surname);// поиск по символам и содержимому

}
