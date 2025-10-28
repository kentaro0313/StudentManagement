package raisetech.student_management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCoursesList();

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchCourses(String student_id);

  @Insert("INSERT INTO students (full_name, furigana, handle_name, mail_address, area, age, gender, remark, isDeleted)"
      + " values (#{fullName},#{furigana}, #{handleName}, #{mailAddress}, #{area}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerNewStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name , start_date, complete_date) "
      + "values(#{studentId}, #{courseName}, #{startDate}, #{completeDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerNewStudentCourse(StudentsCourses studentsCourses);

  @Update("UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, handle_name = #{handleName}, "
      + "mail_address = #{mailAddress}, area = #{area}, age = #{age}, gender = #{gender}, remark =  #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}" )
  void updateStudentCourse(StudentsCourses studentsCourses);


}

