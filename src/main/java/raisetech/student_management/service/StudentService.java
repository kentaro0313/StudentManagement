package raisetech.student_management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentsCourses;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    repository.search();
    return repository.search();
  }

  public List<StudentsCourses> searchCoursesList() {
    return repository.searchCourses();
  }

  @Transactional
  public void registerNewStudent(StudentDetail studentDetail) {
    repository.registerNewStudent(studentDetail.getStudent());
    for(StudentsCourses studentsCourses: studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setStartDate(LocalDateTime.now());
      studentsCourses.setCompleteDate(LocalDateTime.now().plusYears(1));
      repository.registerNewStudentCourse(studentsCourses);
    }
  }
}
