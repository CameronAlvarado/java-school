package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceFoundException;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.repository.CourseRepository;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courserepos;

    @Override
    public ArrayList<Course> findAll()
    {
        ArrayList<Course> list = new ArrayList<>();
        courserepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Course findCourseById(long id) throws ResourceNotFoundException
    {
        return courserepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + Long.toString(id) + " not found."));
    }

    @Override
    public ArrayList<Course> findAllPageable(Pageable pageable)
    {
        ArrayList<Course> list = new ArrayList<>();
        courserepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public ArrayList<CountStudentsInCourses> getCountStudentsInCourse()
    {
        return courserepos.getCountStudentsInCourse();
    }

    @Transactional
    @Override
    public Course save(Course course) //   --- MVP.
    {
        if (courserepos.findByCoursename(course.getCoursename()) != null)
        {
            throw new ResourceFoundException(course.getCoursename() + " is alreay taken! ");
        } else {
            Course newCourse = new Course();

            newCourse.setCoursename(course.getCoursename());
            newCourse.setInstructor(course.getInstructor());

            return courserepos.save(newCourse);
        }
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if (courserepos.findById(id).isPresent())
        {
            courserepos.deleteCourseFromStudcourses(id);
            courserepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Course id: " + Long.toString(id) + " not found.");
        }
    }
}
