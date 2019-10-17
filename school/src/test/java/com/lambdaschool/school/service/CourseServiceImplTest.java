package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class) // unit testing, looking for main class
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourseServiceImplTest
{
    @Autowired
    private CourseService courseService;

//    @Autowired
//    private StudentService studentService;

    @Before
    public void setUp() throws Exception // ???
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception // ???
    {

    }

    @Test
    public void AfindCourseById()
    {
        assertEquals("Node.js", courseService.findCourseById(3L).getCoursename());
    }

    @Test
    public void BdeleteFound()
    {
        courseService.delete(6L);
        assertEquals(5, courseService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class) // checks for exception
    public void CdeleteNotFound()
    {
        courseService.delete(7L);
        assertEquals(5, courseService.findAll().size());
    }
}