package raisetech.student_management.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;


@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;

}
