package raisetech.student_management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;

@Schema(description = "受講生コース情報＆受講生コースIDに紐づくコース申し込み状況")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseSet {

  @Valid
  private StudentCourse studentCourse;

  @Valid
  private Map<String, StudentCourseStatus> studentCourseStatusMap;

}
