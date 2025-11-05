package raisetech.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
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

  @ParameterizedTest
  @CsvSource({"1", "2", "3"})
  void 受講生詳細の検索＿リポジトリが適切に呼び出されていること(String id){

    Student student = new Student();
    List<StudentCourse> studentCourse = new ArrayList<>();
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourse);

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(student.getId());

  }


  @Test
  void 受講生詳細の登録＿入力した値が適切にオブジェクトに入ること(){
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    student.setId("1");
    student.setFullName("山田　太郎");
    student.setFurigana("ヤマダタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");
    student.setRemark("");
    student.isDeleted();
    studentDetail.setStudent(student);


    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourse.setStudentId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("国語");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setCompleteDate(LocalDateTime.now().plusYears(1));
    studentCourseList.add(studentCourse);
    studentDetail.setStudentCourseList(studentCourseList);

    StudentDetail actual = sut.registerNewStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);

  }

  @Test
  void 受講生詳細の更新＿入力した値で適切に更新が機能すること(){
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    student.setId("1");
    student.setFullName("山田　太郎");
    student.setFurigana("ヤマダタロウ");
    student.setHandleName("タロー");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");
    student.setRemark("");
    student.isDeleted();
    studentDetail.setStudent(student);


    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourse.setStudentId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("国語");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setCompleteDate(LocalDateTime.now().plusYears(1));
    studentCourseList.add(studentCourse);
    studentDetail.setStudentCourseList(studentCourseList);

    repository.updateStudent(student);
    repository.updateStudentCourse(studentCourse);



    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);

  }
}