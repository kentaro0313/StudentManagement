package raisetech.student_management.controller.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.data.StudentCourseStatus;
import raisetech.student_management.domain.StudentCourseSet;
import raisetech.student_management.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換するコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。 受講生コース情報は受講生に対して複数存在するので、ループを回しいて受講生詳細情報を組み立てる。
   *
   * @param studentList             受講生一覧
   * @param studentCourseList       受講生コース情報のリスト
   * @param studentCourseStatusList 受講生コースのコース申し込み状況のリスト
   * @return　受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetailList(List<Student> studentList,
      List<StudentCourse> studentCourseList, List<StudentCourseStatus> studentCourseStatusList) {
    List<StudentDetail> studentDetailList = new ArrayList<>();
    for (Student student : studentList) {
      List<StudentCourseSet> studentCourseSetList = new ArrayList<>();
      StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);
      for (StudentCourse studentCourse : studentCourseList) {
        if (studentCourse.getStudentId().equals(student.getId())) {
          for (StudentCourseStatus studentCourseStatus : studentCourseStatusList) {
            if (studentCourseStatus.getCourseId().equals(studentCourse.getCourseId())) {
              StudentCourseSet studentCourseSet = new StudentCourseSet();
              studentCourseSet.setStudentCourse(studentCourse);
              Map<String, StudentCourseStatus> studentCourseStatusMap = new HashMap<>();
              studentCourseStatusMap.put(studentCourse.getCourseId(), studentCourseStatus);
              studentCourseSet.setStudentCourseStatusMap(studentCourseStatusMap);
              studentCourseSetList.add(studentCourseSet);
            }
          }
        }
      }
      studentDetail.setStudentCourseSetList(studentCourseSetList);
      studentDetailList.add(studentDetail);
    }
    return studentDetailList;
  }

}
