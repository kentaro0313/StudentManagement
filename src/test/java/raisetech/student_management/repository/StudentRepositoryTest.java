package raisetech.student_management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student_management.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の検索が行えること(){
    Student student = new Student();
    student.setId("1");
    student.setFullName("田中太郎");
    student.setFurigana("タナカタロウ");
    student.setHandleName("タロウ");
    student.setMailAddress("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setGender("男性");
    String id = "1";

    Student actual = sut.searchStudent(id);

    assertThat(actual.getFullName()).isEqualTo(student.getFullName());
    assertThat(actual.getFurigana()).isEqualTo(student.getFurigana());
    assertThat(actual.getHandleName()).isEqualTo(student.getHandleName());
    assertThat(actual.getMailAddress()).isEqualTo(student.getMailAddress());
    assertThat(actual.getArea()).isEqualTo(student.getArea());
    assertThat(actual.getAge()).isEqualTo(student.getAge());
    assertThat(actual.getGender()).isEqualTo(student.getGender());
  }

  @Test
  void 受講生のコース情報の全件検索が行えること(){

  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索が行えること(){

  }

  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setFullName("廣瀬　健太朗");
    student.setFurigana("ヒロセケンタロウ");
    student.setHandleName("ケン");
    student.setMailAddress("kentaro@example.com");
    student.setArea("兵庫");
    student.setAge(29);
    student.setGender("男性");

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生のコース情報の登録が行えること(){

  }

  @Test
  void 受講生の更新が行えること(){

  }

  @Test
  void 受講生のコース情報の更新が行えること(){

  }
}