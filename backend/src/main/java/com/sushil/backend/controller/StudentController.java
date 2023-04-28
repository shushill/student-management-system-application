package com.sushil.backend.controller;

import jakarta.validation.Valid;
import com.sushil.backend.dto.StudentDto;
import com.sushil.backend.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // handler method to handle list students request
    @GetMapping({"/students"})
    public ResponseEntity<List<StudentDto>> listStudents(){
        List<StudentDto> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // handler method to handle save student form submit request
    @PostMapping("/students")
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto student){
        StudentDto studentdto = studentService.createStudent(student);
        return new ResponseEntity<>(studentdto, HttpStatus.CREATED);
    }

    // handler method to handle edit student request
    @GetMapping("/students/{studentId})
    public ResponseEntity<StudentDto> getStudent(@PathVariable("studentId") Long studentId){
        StudentDto student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // handler method to handle edit student form submit request
    @PostMapping("/students/{studentId}")
    public String updateStudent(@PathVariable("studentId") Long studentId,
                                @Valid @ModelAttribute("student") StudentDto studentDto,
                                BindingResult result,
                                Model model){
        studentDto.setId(studentId);
        studentService.updateStudent(studentDto);
        return "redirect:/students";
    }

    // Handler method to handle delete student request
    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
        return "redirect:/students";
    }

    // Handler method to handle view student request
    @GetMapping("/students/{studentId}/view")
    public String viewStudent(@PathVariable("studentId") Long studentId,
                              Model model){
        StudentDto studentDto = studentService.getStudentById(studentId);
        model.addAttribute("student", studentDto);
        return "view_student";
    }
}
