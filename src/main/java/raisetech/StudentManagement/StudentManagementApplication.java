package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private final Map<String, String> student = new HashMap<>();


  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }


  @GetMapping("/studentsInfo")
  public Map<String, String> studentsInfo() {
    return student;
  }


  @PostMapping("/addStudentInfo")
  public void addStudentInfo(String name, String age) {
    student.put(name, age);
  }

  @PostMapping("/updateStudentAge")
  public void updateStudentAge(String name, String age) {
    student.replace(name, age);
  }


  @PostMapping("/deleteStudentInfo")
  public void deleteStudentInfo(String name) {
    student.remove(name);
  }


}
