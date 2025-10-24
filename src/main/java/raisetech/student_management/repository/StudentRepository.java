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

  @Insert("INSERT INTO students (id, full_name, furigana, handle_name, mail_address, area, age, gender, remark, isDeleted) values (#{student.id}, #{student.fullName},#{student.furigana}, #{student.handleName}, #{student.mailAddress}, #{student.area}, #{student.age}, #{student.gender}, #{student.remark}, #{student.isDeleted})")
  void registerNewStudent(StudentDetail studentDetail);

  @Insert("INSERT INTO students_courses (student_id, course_name) values(#{student.id}, #{studentsCourses[0].courseName})")
  void registerNewStudentCourse(StudentDetail studentDetail);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourses();

}

