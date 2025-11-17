package raisetech.student_management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;
import raisetech.student_management.domain.StudentCourseSet;
import raisetech.student_management.domain.StudentDetail;


class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生情報と受講生コース情報と受講生コース申し込み状況を受講生詳細へと変換＿データの詰め替えが適切に機能していること() {
    Student student = new Student();
    studentInfo(student);
    List<Student> studentList = List.of(student);
    StudentCourse studentCourse = new StudentCourse();
    studentCourseInfo(studentCourse);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatusInfo(studentCourseStatus);
    List<StudentCourseStatus> studentCourseStatusList = List.of(studentCourseStatus);

    StudentCourseSet studentCourseSet = new StudentCourseSet(studentCourse,
        Map.of(studentCourse.getCourseId(), studentCourseStatus));
    List<StudentCourseSet> studentCourseSetList = List.of(studentCourseSet);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);
    List<StudentDetail> studentDetailList = List.of(studentDetail);

    List<StudentDetail> actual = sut.convertStudentDetailList(studentList, studentCourseList,
        studentCourseStatusList);

    assertThat(actual.get(0).getStudent()).isEqualTo(studentDetailList.get(0).getStudent());
    assertThat(actual.get(0).getStudentCourseSetList().get(0).getStudentCourse()).isEqualTo(
        studentDetailList.get(0).getStudentCourseSetList().get(0).getStudentCourse());
    assertThat(
        actual.get(0).getStudentCourseSetList().get(0).getStudentCourseStatusMap()).isEqualTo(
        studentDetailList.get(0).getStudentCourseSetList().get(0).getStudentCourseStatusMap());
  }


  private void studentInfo(Student student) {
    student.setId("999");
    student.setFullName("田中　太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");
  }

  private void studentCourseInfo(StudentCourse studentCourse) {
    studentCourse.setCourseId("999");
    studentCourse.setStudentId("999");
    studentCourse.setCourseName("英語コース");
    studentCourse.setStartDateAt(LocalDateTime.now());
    studentCourse.setCompleteDateAt(LocalDateTime.now().plusYears(1));
  }

  private void studentCourseStatusInfo(StudentCourseStatus studentCourseStatus) {
    studentCourseStatus.setStatusId("999");
    studentCourseStatus.setCourseId("999");
    studentCourseStatus.setCourseStatus("仮申し込み");
  }


}