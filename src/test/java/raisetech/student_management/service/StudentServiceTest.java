package raisetech.student_management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;
import raisetech.student_management.domain.StudentCourseSet;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.repository.StudentRepository;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細一覧の条件検索＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<StudentCourseStatus> studentCourseStatusList = new ArrayList<>();
    List<StudentDetail> studentDetailList = new ArrayList<>();
    when(repository.search(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender())).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchStudentCourseStatusList()).thenReturn(studentCourseStatusList);
    when(converter.convertStudentDetailList(studentList, studentCourseList,
        studentCourseStatusList)).thenReturn(studentDetailList);

    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.searchStudentList(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender());

    assertThat(expected).isEqualTo(actual);
    verify(repository, times(1)).search(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender());
    verify(repository, times(1)).searchStudentCourseList();
    verify(repository, times(1)).searchStudentCourseStatusList();
    verify(converter, times(1)).convertStudentDetailList(studentList, studentCourseList,
        studentCourseStatusList);

  }

  @Test
  void 受講生詳細一覧の条件検索＿条件がある場合適切に絞り込めていること() {
    Student student = new Student();
    student.setFullName("田中太郎");
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<StudentCourseStatus> studentCourseStatusList = new ArrayList<>();
    List<StudentDetail> studentDetailList = new ArrayList<>();
    when(repository.search(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender())).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchStudentCourseStatusList()).thenReturn(studentCourseStatusList);
    when(converter.convertStudentDetailList(studentList, studentCourseList,
        studentCourseStatusList)).thenReturn(studentDetailList);

    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.searchStudentList(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender());

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void 受講生詳細一覧の条件検索＿条件がない場合全件取得になっていること() {
    Student student = new Student();
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<StudentCourseStatus> studentCourseStatusList = new ArrayList<>();
    List<StudentDetail> studentDetailList = new ArrayList<>();
    when(repository.search(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender())).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchStudentCourseStatusList()).thenReturn(studentCourseStatusList);
    when(converter.convertStudentDetailList(studentList, studentCourseList,
        studentCourseStatusList)).thenReturn(studentDetailList);

    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.searchStudentList(student.getId(), student.getFullName(), student.getFurigana(), student.getHandleName(), student.getMailAddress(), student.getArea(), student.getAge(), student.getGender());

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void 受講生詳細の検索＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    student.setId("999");
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId("999");
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setCourseId("999");
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    Map<String, StudentCourseStatus> studentCourseStatusMap = Map.of(studentCourse.getCourseId(),
        studentCourseStatus);
    StudentCourseSet studentCourseSet = new StudentCourseSet(studentCourse, studentCourseStatusMap);
    List<StudentCourseSet> studentCourseSetList = List.of(studentCourseSet);
    when(repository.searchStudent(student.getId())).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);
    when(repository.searchStudentCourseStatus(studentCourseStatus.getCourseId())).thenReturn(
        new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, studentCourseSetList);
    StudentDetail actual = sut.searchStudent(student.getId());

    verify(repository, times(1)).searchStudent(student.getId());
    verify(repository, times(1)).searchStudentCourse(student.getId());
    verify(repository, times(1)).searchStudentCourseStatus(studentCourse.getCourseId());
    Assertions.assertNotNull(actual.getStudent(), "NULLになってます");
    Assertions.assertNotNull(actual.getStudentCourseSetList(), "NULLになってます");
    assertThat(actual.getStudent().getId()).isEqualTo(expected.getStudent().getId());
    assertThat(actual.getStudent().getFullName()).isEqualTo(
        expected.getStudent().getFullName());
  }


  @Test
  void 受講生詳細の登録＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    Map<String, StudentCourseStatus> studentCourseStatusMap = new HashMap<>();
    studentCourseStatusMap.put(studentCourse.getCourseId(), studentCourseStatus);
    StudentCourseSet studentCourseSet = new StudentCourseSet(studentCourse, studentCourseStatusMap);
    List<StudentCourseSet> studentCourseSetList = List.of(studentCourseSet);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);

    sut.registerNewStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
    verify(repository, times(1)).registerStudentCourseStatus(any(StudentCourseStatus.class));

  }

  @Test
  void 受講生コース情報の生成() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    student.setId("999");
    studentCourse.setStudentId(student.getId());

    sut.initStudentCourse(studentCourse, student);

    assertThat("999".equals(studentCourse.getStudentId()));
    assertThat(LocalDateTime.now().getDayOfYear() == studentCourse.getStartDateAt().getDayOfYear());
    assertThat(LocalDateTime.now().getMonth() == studentCourse.getStartDateAt().getMonth());
    assertThat(LocalDateTime.now().getDayOfWeek() == studentCourse.getStartDateAt().getDayOfWeek());
    assertThat(LocalDateTime.now().getHour() == studentCourse.getStartDateAt().getHour());
    assertThat(LocalDateTime.now().getHour() == studentCourse.getCompleteDateAt().getHour());


  }

  @Test
  void 受講生詳細の更新＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    Map<String, StudentCourseStatus> studentCourseStatusMap = new HashMap<>();
    studentCourseStatusMap.put(studentCourse.getCourseId(), studentCourseStatus);
    StudentCourseSet studentCourseSet = new StudentCourseSet(studentCourse, studentCourseStatusMap);
    List<StudentCourseSet> studentCourseSetList = List.of(studentCourseSet);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
    verify(repository, times(1)).updateStudentCourseStatus(studentCourseStatus);


  }
}