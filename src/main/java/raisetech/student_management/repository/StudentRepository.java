package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Insert("INSERT INTO students (full_name, furigana, handle_name, mail_address, area, age, gender, remark, isDeleted)"
      + " values (#{fullName},#{furigana}, #{handleName}, #{mailAddress}, #{area}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerNewStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name , start_date, complete_date) "
      + "values(#{studentId}, #{courseName}, #{startDate}, #{completeDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerNewStudentCourse(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourses();

}

