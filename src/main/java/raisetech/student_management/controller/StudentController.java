package raisetech.student_management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;
import raisetech.student_management.service.StudentService;

@RestController

public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentsList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/coursesList")
  public List<StudentsCourses> getCoursesList() {
    return service.searchCoursesList();
  }
}
