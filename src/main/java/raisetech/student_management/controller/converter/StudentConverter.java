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
 * 受講生詳細、受講生コース情報や申し込み状況を変換するコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。
   * 受講生コース情報は受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てる。
   * @param studentList　受講生一覧
   * @param studentCourseList　受講生コース情報のリスト
   * @return　受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetailList(List<Student> studentList,
      List<StudentCourse> studentCourseList, List<StudentCourseStatus> studentCourseStatusList) {
    List<StudentDetail> studentDetailList = new ArrayList<>();
    for (Student student : studentList) {
      List<StudentCourseSet> studentCourseSetList = new ArrayList<>();
      StudentDetail studentDetail = new StudentDetail(student, studentCourseSetList);
      studentCourseList.stream()
          .filter(studentCourse -> studentCourse.getStudentId().equals(student.getId())).forEach(
              studentCourse -> combineStudentCourseInfo(studentCourseStatusList, studentCourse,
                  studentCourseSetList));
      studentDetail.setStudentCourseSetList(studentCourseSetList);
      studentDetailList.add(studentDetail);
    }
    return studentDetailList;
  }

  void combineStudentCourseInfo(List<StudentCourseStatus> studentCourseStatusList,
      StudentCourse studentCourse, List<StudentCourseSet> studentCourseSetList) {
    studentCourseStatusList.stream().filter(
        studentCourseStatus -> studentCourseStatus.getCourseId()
            .equals(studentCourse.getCourseId())).forEach(studentCourseStatus -> {
      StudentCourseSet studentCourseSet = new StudentCourseSet();
      studentCourseSet.setStudentCourse(studentCourse);
      Map<String, StudentCourseStatus> studentCourseStatusMap = new HashMap<>();
      studentCourseStatusMap.put(studentCourse.getCourseId(), studentCourseStatus);
      studentCourseSet.setStudentCourseStatusMap(studentCourseStatusMap);
      studentCourseSetList.add(studentCourseSet);
    });
  }

}
