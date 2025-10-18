package raisetech.student_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

@RestController

public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentsList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchCoursesList();

    return converter.convertStudentDetails(
        students, studentsCourses);

  }



  @GetMapping("/coursesList")
  public List<StudentsCourses> getCoursesList() {
    return service.searchCoursesList();
  }
}
