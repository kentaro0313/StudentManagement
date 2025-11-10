package raisetech.student_management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter

public class StudentCourse {

  private String courseId;
  private String studentId;

  @NotBlank
  private String courseName;
  private LocalDateTime startDateAt;
  private LocalDateTime completeDateAt;

}
