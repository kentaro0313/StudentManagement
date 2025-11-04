package raisetech.student_management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  private String id;


  @NotBlank
  private String fullName;

  @NotBlank
  private String furigana;

  private String handleName;

  @Email
  private String mailAddress;

  @NotBlank
  private String area;

  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}


