package raisetech.student_management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private Integer courseId;
  private Integer studentId;
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime completeDate;

}
