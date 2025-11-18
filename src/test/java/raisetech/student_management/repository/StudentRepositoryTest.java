package raisetech.student_management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    Student student = new Student();
    List<Student> actual = sut.search(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender());
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生IDに紐づいた受講生の検索が行えること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロウ");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    Student actual = sut.searchStudent("1");

    assertThat(actual.getFullName()).isEqualTo(student.getFullName());
    assertThat(actual.getFurigana()).isEqualTo(student.getFurigana());
    assertThat(actual.getHandleName()).isEqualTo(student.getHandleName());
    assertThat(actual.getMailAddress()).isEqualTo(student.getMailAddress());
    assertThat(actual.getArea()).isEqualTo(student.getArea());
    assertThat(actual.getAge()).isEqualTo(student.getAge());
    assertThat(actual.getGender()).isEqualTo(student.getGender());
  }

  @Test
  void 受講生のコース情報の全件検索が行えること() {

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("英語コース");
    LocalDateTime localDateTime = LocalDateTime.of(
        LocalDate.parse("2023/03/12", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        LocalTime.of(0, 0, 0));
    studentCourse.setStartDateAt(localDateTime);
    studentCourse.setCompleteDateAt(localDateTime.plusYears(1));
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse("1");

    assertThat(actual.get(0).getStudentId()).isEqualTo(studentCourseList.get(0).getStudentId());
    assertThat(actual.get(0).getCourseName()).isEqualTo(studentCourseList.get(0).getCourseName());
    assertThat(actual.get(0).getStartDateAt()).isEqualTo(studentCourseList.get(0).getStartDateAt());
    assertThat(actual.get(0).getCompleteDateAt()).isEqualTo(
        studentCourseList.get(0).getCompleteDateAt());
  }


  @Test
  void 受講生コースの申し込み状況の全件検索を行います() {

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatusList();

    assertThat(actual.size()).isEqualTo(10);

  }

  @Test
  void コースIDに紐づく受講生コース申し込み状況の検索を行えること() {
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setStatusId("1");
    studentCourseStatus.setCourseId("1");
    studentCourseStatus.setCourseStatus("仮申し込み");

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatus("1");

    assertThat(actual.get(0).getCourseId()).isEqualTo(studentCourseStatus.getCourseId());
    assertThat(actual.get(0).getCourseStatus()).isEqualTo(studentCourseStatus.getCourseStatus());

  }

  @Test
  void 受講生の新規登録が行えること() {
    Student student = new Student();
    student.setFullName("廣瀬　健太朗");
    student.setFurigana("ヒロセケンタロウ");
    student.setHandleName("ケン");
    student.setMailAddress("kentaro@example.com");
    student.setArea("兵庫");
    student.setAge(29);
    student.setGender("男性");
    Student student1 = new Student();

    sut.registerStudent(student);
    List<Student> actual = sut.search(student1.getId(), student1.getFullName(), student1.getFurigana(), student1.getHandleName(), student1.getMailAddress(), student1.getArea(), student1.getAge(), student1.getGender());

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生のコース情報の新規登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("100");
    studentCourse.setCourseName("英語コース");
    LocalDateTime localDateTime = LocalDateTime.of(
        LocalDate.parse("2025/11/09", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        LocalTime.of(0, 0, 0));
    studentCourse.setStartDateAt(localDateTime);
    studentCourse.setCompleteDateAt(localDateTime.plusYears(1));

    sut.registerStudentCourse(studentCourse);
    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中太郎");
    student.setFurigana("タロウタナカ");
    student.setHandleName("タロウ");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");

    sut.updateStudent(student);
    Student actual = sut.searchStudent("1");

    assertThat(actual.getFullName()).isEqualTo(student.getFullName());
    assertThat(actual.getFurigana()).isEqualTo(student.getFurigana());
    assertThat(actual.getHandleName()).isEqualTo(student.getHandleName());
    assertThat(actual.getMailAddress()).isEqualTo(student.getMailAddress());
    assertThat(actual.getArea()).isEqualTo(student.getArea());
    assertThat(actual.getAge()).isEqualTo(student.getAge());
    assertThat(actual.getGender()).isEqualTo(student.getGender());

  }

  @Test
  void 受講生のコース情報の更新が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("ドイツ語コース");
    LocalDateTime localDateTime = LocalDateTime.of(
        LocalDate.parse("2023/03/12", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        LocalTime.of(0, 0, 0));
    studentCourse.setStartDateAt(localDateTime);
    studentCourse.setCompleteDateAt(localDateTime.plusYears(1));
    String courseId = "1";

    sut.updateStudentCourse(studentCourse);
    List<StudentCourse> actual = sut.searchStudentCourse(courseId);

    assertThat(actual.get(0).getCourseName()).isEqualTo(studentCourse.getCourseName());
  }

  @Test
  void 受講生コースのコース申し込み状況の更新が行えること() {
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setStatusId("1");
    studentCourseStatus.setCourseId("1");
    studentCourseStatus.setCourseStatus("仮申し込み");

    sut.updateStudentCourseStatus(studentCourseStatus);
    List<StudentCourseStatus> actual = sut.searchStudentCourseStatus("1");

    assertThat(actual.get(0).getCourseId()).isEqualTo(studentCourseStatus.getCourseId());
    assertThat(actual.get(0).getCourseStatus()).isEqualTo(studentCourseStatus.getCourseStatus());
  }

}