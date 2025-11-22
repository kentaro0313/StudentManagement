package raisetech.student_management.data;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseStatus {

  private String statusId;
  private String courseId;

  @NotBlank
  private String courseStatus;
}
