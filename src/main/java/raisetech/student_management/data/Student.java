package raisetech.student_management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Pattern(regexp = "^\\d+$", message = "数字のみ入力するようにしてください")
  private String id;

  @NotBlank(message = "名前を入力してください")
  @Pattern(regexp = "^[^\\s]+$", message = "スペースを空けず詰めて入力してください")
  private String fullName;

  @NotBlank
  @Pattern(regexp = "^[ァ-ヶーｦ-ﾟ]+$", message = "カタカナのみで入力してください")
  private String furigana;

  @NotBlank
  private String handleName;

  @Email(message = "メールアドレスの形式が不正です")
  @NotBlank
  private String mailAddress;

  @NotBlank(message = "住んでいる地域を入力してください（例：東京、大阪）")
  private String area;

  @NotNull
  @Range(min = 18, max = 120, message = "18~120で入力してください")
  private Integer age;

  private String gender;

  private String remark;

  private boolean isDeleted;
}


