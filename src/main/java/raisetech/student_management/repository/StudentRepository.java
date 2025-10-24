package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;
import raisetech.student_management.domain.StudentDetail;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Insert("INSERT students values #{studentDetail}")
  void registerNewStudent(StudentDetail studentDetail);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourses();

}

