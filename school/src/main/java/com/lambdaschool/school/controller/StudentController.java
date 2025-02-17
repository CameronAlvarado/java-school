package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "return all Students",
            response = Student.class,
            responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    // http://localhost:2019/students/students/paging/
    // http://localhost:2019/students/students/paging/?page=1
    // http://localhost:2019/students/students/paging/?page=0&size=10
    // http://localhost:2019/students/students/paging/?page=0&size=10&sort=studname
    // http://localhost:2019/students/students/paging/?page=0&size=10&sort=studname,desc
    @GetMapping(value = "/students/paging",
            produces = {"application/json"})
    public ResponseEntity<?> listAllStudentsByPage(@PageableDefault(page = 0, size = 3) Pageable pageable)
    {
        List<Student> myStudents = studentService.findAllPageable(pageable);
//      List<Student> myStudents = studentService.findAllPageable(Pageable.unpaged()); <-- un-pages using code.
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    //               slf4j ^

    // Please note there is no way to add students to course yet! <--- MVP

    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(HttpServletRequest request)
    {
        logger.warn("This is a warn log");
        logger.trace("This is a trace log");
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a student accociated with the student ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
    @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})

    @GetMapping(value = "/student/{StudentId}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(value = "Student ID", required = true, example = "3")
            @PathVariable
                    Long StudentId)
    {
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @GetMapping(value = "/student/namelike/{name}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @PathVariable String name)
    {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value = "Saves a new Student Object", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Saved", response = Student.class),
                           @ApiResponse(code = 400, message = "Student Already Exists", response = ErrorDetail.class)})

    @PostMapping(value = "/Student",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a student accociated with the student ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Updated", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})

    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid)
    {
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a student accociated with the student ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Deleted", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})

    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @PathVariable
                    long Studentid)
    {
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
