package com.lambdaschool.school.repository;

import com.lambdaschool.school.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


// Crud Repo - first choice
// PagingAndSortingRepository - extends Crud Repo but with paging/sorting
// JpaRepository - Sessions - Don't use

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>
{
    Student findByStudname(String username); // <-- added functionality

    List<Student> findByStudnameContainingIgnoreCase(String name);
}
