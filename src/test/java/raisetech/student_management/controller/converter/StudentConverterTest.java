package raisetech.student_management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    studentInfo(student);
    studentCourseInfo(studentCourse);
    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<StudentDetail> studentDetailList = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);
    studentDetailList.add(studentDetail);


    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(studentDetailList.get(0).getStudent());
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentDetailList.get(0).getStudentCourseList());

  }


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
    studentCourse.setCourseName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setCompleteDate(LocalDateTime.now().plusYears(1));
  }
}