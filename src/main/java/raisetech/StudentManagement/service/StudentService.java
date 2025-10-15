package raisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> serchStudentList() {
    // 条件検索
    repository.search();

    List<Student> over30StudentsList= new ArrayList<>();
    //絞り込みをする。年齢が30代の人のみ抽出する。
    for (Student student: repository.search()){
      if(student.getAge()>30){
        over30StudentsList.add(student);
      }
    }
    //抽出したリストをコントローラーに返す。
    return over30StudentsList;
  }

  public List<StudentsCourses> searchCoursesList(){
    List<StudentsCourses> mathCourseList= new ArrayList<>();
    //絞り込みした「数学コース」のコース情報のみを抽出する。
    for (StudentsCourses courses: repository.searchCourses()){
      if(courses.getCourseName().equals("数学")){
        mathCourseList.add(courses);
      }
    }
    //抽出したリストをコントローラーに返す。
    return mathCourseList;
  }
}
