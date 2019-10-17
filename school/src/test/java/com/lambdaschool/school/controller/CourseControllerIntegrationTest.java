package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredReponseTime()
    {
        given().when().get("/restaurants/restaurants").then().time(lessThan(5000L));
    }


//  GET /courses/courses
    @Test
    public void givenFindAllCourses()
    {
        given().when().get("/courses/courses").then().statusCode(200).and().body(containsString("Java"));
    }

    //    POST /restaurants/restaurant
    @Test
    public void givenPostACourse() throws Exception
    {
        String course5Name = "Number 1 Test Course";
        Instructor testInstruct = new Instructor("Jerry Jengles");
        Course c5 = new Course(course5Name, testInstruct);

        ObjectMapper mapper = new ObjectMapper();
        String stringC5 = mapper.writeValueAsString(c5);

        given().contentType("application/json").body(stringC5).when().post("/courses/courses/course/add").then().statusCode(201);
    }
}
