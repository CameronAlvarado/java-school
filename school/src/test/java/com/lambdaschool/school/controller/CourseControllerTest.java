package com.lambdaschool.school.controller;

//import org.codehaus.jackson.map.ObjectMapper;
import  com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//        @ContextConfiguration("/applicationContext.xml")
@WebMvcTest(value = CourseController.class, secure = false) // secure isolates the code, doens't check auth.)
public class CourseControllerTest // unit test
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ArrayList<Course> courseList;

    @Before
    public void setUp() throws Exception
    {
        courseList = new ArrayList<>();

        Instructor i1 = new Instructor("Cameron");
        i1.setInstructid(0);

        Instructor i2 = new Instructor("Joe Jengles");
        i2.setInstructid(1);

        Course c1 = new Course("Java");
        Course c2 = new Course("Node.js");
        Course c3 = new Course("Javascript");

        Student s1 = new Student("John");
        s1.setStudid(1);
        Student s2 = new Student("Julian");
        s1.setStudid(2);
        Student s3 = new Student("Mary");
        s1.setStudid(3);

        c1.getStudents().add(s1);
        c1.getStudents().add(s3);

        c1.setCourseid(0);
        c2.setCourseid(1);
        c3.setCourseid(2);

        c1.setInstructor(i1);
        c2.setInstructor(i2);
        c3.setInstructor(i2);

        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
    }

    @After
    public void tearDown()
    {

    }


    @Test
    public void listAllCoursesByPage() throws Exception
    {
//        String apiUrl = "/courses/courses";
        String apiUrl = "/courses/courses/paging";

//        Mockito.when(courseService.findAll()).thenReturn(courseList);
        Mockito.when(courseService.findAllPageable(Mockito.any(Pageable.class))).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();


//        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void save() throws Exception
    {
        String apiUrl = "/courses/courses/course/add";

        String course5Name = "Number 1 Test Course";
        Instructor testInstruct = new Instructor("Jerry Jengles");
        Course c5 = new Course(course5Name, testInstruct);

        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(c5);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(c5);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(courseString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

}