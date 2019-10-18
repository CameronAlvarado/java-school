package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface CourseService
{
    ArrayList<Course> findAll();

    Course findCourseById(long id);

    ArrayList<Course> findAllPageable(Pageable pageable);

    Course save (Course course);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    void delete(long id);
}
