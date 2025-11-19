package raisetech.student_management.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細一覧の条件検索が実行できて空のリストが返ってくること() throws Exception {
    Student student = new Student();

    mockMvc.perform(MockMvcRequestBuilders.get("/studentList", student))
        .andExpect(status().isOk());
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "999";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の検索で無効なIdを入力した時に入力チェックに掛かること() throws Exception {
    String id = "aaa";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    StudentDetail studentDetail = new StudentDetail();

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON).content(
                """
                        {
                            "student": {
                                "fullName":"田中太郎",
                                "furigana": "タナカタロウ",
                                "handleName" : "タロウ",
                                "mailAddress" : "taro@example.com",
                                "area" : "東京",
                                "age" : "30",
                                "gender" : "男性"
                            },
                         "studentCourseSetList": [
                                {
                                    "studentCourse": {
                                        "courseName": "英語コース"
                                    },
                                    "studentCourseStatusMap": {}
                                }
                            ]
                        }    
                    """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerNewStudent(any(StudentDetail.class));

  }

  @Test
  void 受講生の更新が実行できて空で返ってくること() throws Exception {
    StudentDetail studentDetail = new StudentDetail();

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON).content(
                """
                        {
                          "student": {
                              "id": "999",
                              "fullName": "山田　太郎",
                              "furigana": "ヤマダタロウ",
                              "handleName": "タロウ",
                              "mailAddress": "taro@exaple.com",
                              "area": "大分",
                              "age": 49,
                              "gender": "男性",
                              "remark": "",
                              "deleted": false
                          },
                          "studentCourseSetList": [
                              {
                                "studentCourse": {
                                  "courseId": "999",
                                  "studentId": "999",
                                  "courseName": "英語コース",
                                  "startDateAt": "2025-08-18T00:00:00",
                                  "completeDateAt": "2028-07-12T00:00:00"
                                },
                                "studentCourseStatusMap": {
                                  "2": {
                                    "statusId": "999",
                                    "courseId": "999",
                                    "courseStatus": "仮申し込み"
                                  }
                                }
                              }
                          ]
                        }
                    """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }


  @Test
  void 不正なAPIに対してメッセージを表示する() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/getErrorMessage"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください");

  }

  @Test
  void 受講生詳細の受講生が名前を空欄で出したときにに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("名前を入力してください");
  }

  @Test
  void 受講生詳細の受講生でフリガナにカタカナ以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中　太郎");
    student.setFurigana("田中　太郎");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("カタカナのみで入力してください");

  }

  @Test
  void 受講生詳細の受講生で不正なメールアドレスを用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("メールアドレスの形式が不正です");

  }

  @Test
  void 受講生詳細の受講生で年齢に数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("18~120で入力してください");

  }

  @Test
  void 受講生コース情報で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("英語コース");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生コース情報でコース名を空欄で出したときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("コース名を入力してください");
  }


}