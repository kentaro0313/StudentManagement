package raisetech.student_management.data;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  @NotBlank
  private String fullName;
  private String furigana;
  private String handleName;
  private String mailAddress;
  private String area;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}


