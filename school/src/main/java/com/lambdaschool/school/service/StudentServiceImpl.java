package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceFoundException;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "studentService")
public class StudentServiceImpl implements StudentService
{
    @Autowired
    private StudentRepository studrepos;

    @Override
    public List<Student> findAll()
    {
        List<Student> list = new ArrayList<>();
        studrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Student> findAllPageable(Pageable pageable)
    {
        List<Student> list = new ArrayList<>();
        studrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Student findStudentById(long id) throws ResourceNotFoundException
    {
        return studrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + Long.toString(id) + " not found."));
    }

    @Override
    public List<Student> findStudentByNameLike(String name)
    {
        List<Student> list = new ArrayList<>();
        studrepos.findByStudnameContainingIgnoreCase(name).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if (studrepos.findById(id).isPresent())
        {
            studrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("User id: " + Long.toString(id) + " not found.");
        }
    }

    @Transactional
    @Override
    public Student save(Student student) //   --- MVP.
    {
        if (studrepos.findByStudname(student.getStudname()) != null)
        {
            throw new ResourceFoundException(student.getStudname() + " is alreay taken! ");
        } else {
            Student newStudent = new Student();

            newStudent.setStudname(student.getStudname());

            return studrepos.save(newStudent);
        }
    }

    @Override
    public Student update(Student student, long id)
    {
        Student currentStudent = studrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + Long.toString(id) + " not found."));

        if (student.getStudname() != null)
        {
            currentStudent.setStudname(student.getStudname());
        }

        return studrepos.save(currentStudent);
    }
}
