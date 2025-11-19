package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;


/**
 * 受講生テーブルと受講生コーステーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索または条件検索を行います。
   *
   * @return　受講生一覧（全件OR条件）
   */
  List<Student> search(Student student);

  /**
   * 受講生IDに紐づく受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return　受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報の検索を行います。
   *
   * @param　studentID 受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生コース申し込み状況の全件検索を行います。
   *
   * @return　受講生のコース申し込み状況（全件）
   */
  List<StudentCourseStatus> searchStudentCourseStatusList();

  /**
   * コースIDに紐づく受講生コース申し込み状況の検索を行います。
   *
   * @param courseId コースID
   * @return　コースIDに紐づく受講生コース申し込み状況
   */
  List<StudentCourseStatus> searchStudentCourseStatus(String courseId);

  /**
   * 受講生情報を新規登録します。 IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。 IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コースの申し込み状況を新規登録します。 IDに関しては自動採番を行う。
   *
   * @param studentCourseStatus コース申し込み状況
   */
  void registerStudentCourseStatus(StudentCourseStatus studentCourseStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);


  /**
   * 受講生コースのコース申し込み状況を更新します。
   *
   * @param studentCourseStatus 受講生コース情報
   */
  void updateStudentCourseStatus(StudentCourseStatus studentCourseStatus);
}

