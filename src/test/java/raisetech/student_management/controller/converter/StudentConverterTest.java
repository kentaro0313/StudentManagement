package raisetech.student_management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;


class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() { sut = new StudentConverter();}

  @Test
  void 受講生詳細を受講生情報と受講生コース情報への相互変換＿データの詰め替えが適切に機能していること() {
    StudentConverter sut = new StudentConverter();
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentInfo(student);
    studentCourseInfo(studentCourse);
    studentList.forEach(student1 -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student1);
      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourse1 -> studentCourse1.getStudentId().equals(student1.getId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetailList.add(studentDetail);
    });

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);
    assertThat(actual.get(0).getStudent()).isEqualTo(studentDetailList.get(0).getStudent());
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentDetailList.get(0).getStudentCourseList());

  }


  @Test
  void 受講生詳細を受講生情報と受講生コース情報への相互変換＿生徒詳細情報に不足があった場合(){}

  @Test
  void 受講生詳細を受講生情報と受講生コース情報への相互変換＿生徒コース情報に不足があった場合(){}

  private void studentInfo(Student student) {
    student.setId("1");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");
  }

  private void studentCourseInfo(StudentCourse studentCourse) {
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("国語");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setCompleteDate(LocalDateTime.now().plusYears(1));
  }
}