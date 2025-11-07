package raisetech.student_management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
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
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

  }

  @Test
  void 受講生詳細の検索＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    student.setId("999");
    student.setFullName("田中　太郎");
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    when(repository.searchStudent(student.getId())).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    StudentDetail actual = sut.searchStudent(student.getId());

    verify(repository, times(1)).searchStudent(student.getId());
    verify(repository, times(1)).searchStudentCourse(student.getId());
    Assertions.assertNotNull(actual.getStudent(), "NULLになってます");
    Assertions.assertNotNull(actual.getStudentCourseList(), "NULLになってます");
    assertThat(actual.getStudent().getId()).isEqualTo(studentDetail.getStudent().getId());
    assertThat(actual.getStudent().getFullName()).isEqualTo(
        studentDetail.getStudent().getFullName());
  }


  @Test
  void 受講生詳細の登録＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    student.setId("999");
    student.setFullName("田中　太郎");
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    doNothing().when(repository).registerStudent(student);
    doNothing().when(repository).registerStudentCourse(studentCourse);

    StudentDetail actual = sut.registerNewStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
    Assertions.assertNotNull(actual.getStudent(), "NULLになってます");
    Assertions.assertNotNull(actual.getStudentCourseList(), "NULLになってます");
    assertThat(actual.getStudent().getId()).isEqualTo(studentDetail.getStudent().getId());
    assertThat(actual.getStudent().getFullName()).isEqualTo(
        studentDetail.getStudent().getFullName());

  }

  @Test
  void 受講生詳細の更新＿リポジトリが適切に呼び出されていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    doNothing().when(repository).updateStudent(student);
    doNothing().when(repository).updateStudentCourse(studentCourse);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);

  }
}