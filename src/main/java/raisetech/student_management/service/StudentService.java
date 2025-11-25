package raisetech.student_management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;
import raisetech.student_management.domain.StudentCourseSet;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 条件を指定することで、条件に紐づいた受講生詳細の一覧検索ができます。
   *
   * @return　受講生一覧（条件検索）
   */
  public List<StudentDetail> searchStudentList(Student student) {
    List<Student> studentList = repository.search(student);
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<StudentCourseStatus> studentCourseStatusList = repository.searchStudentCourseStatusList();
    List<StudentDetail> studentDetailList = converter.convertStudentDetailList(studentList,
        studentCourseList, studentCourseStatusList);
    return studentDetailList;
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報とコース申し込み状況を取得して設定します。
   *
   * @param id 受講生ID
   * @return　受講生詳細
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourseSet> studentCourseSetList = new ArrayList<>();
    List<StudentCourse> studentCourseList = repository.searchStudentCourse(id);

    studentCourseList.forEach(studentCourse -> {
      List<StudentCourseStatus> studentCourseStatusList = repository.searchStudentCourseStatus(
          studentCourse.getCourseId());

      studentCourseStatusList.forEach(studentCourseStatus -> {
        StudentCourseSet studentCourseSet = new StudentCourseSet();
        if (studentCourse.getCourseId().equals(studentCourseStatus.getCourseId())) {
          Map<String, StudentCourseStatus> studentCourseStatusMap = Map.of(
              studentCourse.getCourseId(), studentCourseStatus);
          studentCourseSet.setStudentCourse(studentCourse);
          studentCourseSet.setStudentCourseStatusMap(studentCourseStatusMap);
          studentCourseSetList.add(studentCourseSet);
        }
      });
    });

    StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);
    return studentDetail;
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   * 受講生コース申し込み状況のにはコースIDを紐づけて、初期値として申し込み状況には「仮申し込み」を登録します。
   *
   * @param studentDetail 受講生詳細
   * @return　登録情報を付与した受講生情報
   */
  @Transactional
  public void registerNewStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);

    for (StudentCourseSet studentCourseSet : studentDetail.getStudentCourseSetList()) {
      initStudentCourse(studentCourseSet.getStudentCourse(), student);
      repository.registerStudentCourse(studentCourseSet.getStudentCourse());

      StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
      studentCourseStatus.setCourseId(studentCourseSet.getStudentCourse().getCourseId());
      studentCourseStatus.setCourseStatus("仮申し込み");
      studentCourseSet.getStudentCourseStatusMap()
          .put(studentCourseSet.getStudentCourse().getCourseId(), studentCourseStatus);
      repository.registerStudentCourseStatus(studentCourseStatus);
    }
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param student       受講生
   */
  void initStudentCourse(StudentCourse studentCourse, Student student) {
    studentCourse.setStudentId(student.getId());

    LocalDateTime now = LocalDateTime.now();
    studentCourse.setStartDateAt(now);
    studentCourse.setCompleteDateAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報、コース申し込み状況をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.updateStudent(student);

    for (StudentCourseSet studentCourseSet : studentDetail.getStudentCourseSetList()) {
      repository.updateStudentCourse(studentCourseSet.getStudentCourse());
      repository.updateStudentCourseStatus(studentCourseSet.getStudentCourseStatusMap()
          .get(studentCourseSet.getStudentCourse().getCourseId()));

    }
  }
}
