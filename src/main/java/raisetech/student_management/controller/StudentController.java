package raisetech.student_management.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student_management.data.Student;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.exception.TestException;
import raisetech.student_management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受けつけるControllerです。
 */

@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索や条件を指定することによって条件検索が可能です。
   *
   * @return　受講生詳細一覧
   */
  @Operation(summary = "受講生詳細の一覧検索", description = "受講生詳細の一覧を検索します。条件をクエリパラメータに入力すると絞り込み検索が可能です")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(
      @RequestParam(required = false)
      String id,
      @RequestParam(required = false)
      String fullName,
      @RequestParam(required = false)
      String furigana,
      @RequestParam(required = false)
      String handleName,
      @RequestParam(required = false)
      String mailAddress,
      @RequestParam(required = false)
      String area,
      @RequestParam(required = false)
      Integer age,
      @RequestParam(required = false)
      String gender
  ) {
    Student student = new Student();
    student.setId(id);
    student.setFullName(fullName);
    student.setFurigana(furigana);
    student.setHandleName(handleName);
    student.setMailAddress(mailAddress);
    student.setArea(area);
    student.setAge(age);
    student.setGender(gender);
    return service.searchStudentList(student);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return　受講生
   */
  @Operation(summary = "受講生検索", description = "受講生を検索します")
  @GetMapping("/student/{id:^\\d+$}")
  public StudentDetail getStudent(
      @Parameter(description = "検索したい受講生のID", example = "123")
      @PathVariable @Size(min = 1, max = 3)
      String id
  ) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します",
      requestBody = @RequestBody(
          description = "受講生情報",
          required = true,
          content = @Content(
              schema = @Schema(implementation = StudentDetail.class),
              examples = @ExampleObject(
                  name = "受講生の登録例",
                  value = "{\n"
                      + "    \"student\": {\n"
                      + "        \"fullName\":\"山田　太郎\", \n"
                      + "        \"furigana\": \"タナカタロウ\",\n"
                      + "        \"handleName\" : \"タロウ\",\n"
                      + "        \"mailAddress\" : \"taro@example.com\",\n"
                      + "        \"area\" : \"東京\",\n"
                      + "        \"age\" : \"35\",\n"
                      + "        \"gender\" : \"男性\"\n"
                      + "    },\n"
                      + " \"studentCourseSetList\": [\n"
                      + "        {\n"
                      + "            \"studentCourse\": {\n"
                      + "                \"courseName\": \"英語語コース\"\n"
                      + "            },\n"
                      + "            \"studentCourseStatusMap\": {}\n"
                      + "        }\n"
                      + "    ]\n"
                      + "}"
              )
          )
      ))
  @PostMapping("/registerStudent")
  public ResponseEntity<String> registerStudent(
      @Parameter(description = "登録したい受講生の情報")
      @RequestBody @Valid
      StudentDetail studentDetail
  ) {
    service.registerNewStudent(studentDetail);
    return ResponseEntity.ok("登録処理が完了しました");
  }

  /**
   * 受講生詳細の更新をおこないます。 キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生の情報を更新します",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "受講生情報",
          required = true,
          content = @Content(
              schema = @Schema(implementation = StudentDetail.class),
              examples = @ExampleObject(
                  name = "受講生の更新例",
                  value = "  {\n"
                      + "        \"student\": {\n"
                      + "            \"id\": \"999\",\n"
                      + "            \"fullName\": \"田中太郎\",\n"
                      + "            \"furigana\": \"タナカタロウ\",\n"
                      + "            \"handleName\": \"タロウ\",\n"
                      + "            \"mailAddress\": \"taro@example.com\",\n"
                      + "            \"area\": \"東京\",\n"
                      + "            \"age\": 35,\n"
                      + "            \"gender\": \"男性\",\n"
                      + "            \"remark\": null,\n"
                      + "            \"deleted\": false\n"
                      + "        },\n"
                      + "        \"studentCourseSetList\": [\n"
                      + "            {\n"
                      + "                \"studentCourse\": {\n"
                      + "                    \"courseId\": \"999\",\n"
                      + "                    \"studentId\": \"999\",\n"
                      + "                    \"courseName\": \"英語語コース\",\n"
                      + "                    \"startDateAt\": \"2025-11-17T21:56:39\",\n"
                      + "                    \"completeDateAt\": \"2026-11-17T21:56:39\"\n"
                      + "                },\n"
                      + "                \"studentCourseStatusMap\": {\n"
                      + "                    \"28\": {\n"
                      + "                        \"statusId\": \"999\",\n"
                      + "                        \"courseId\": \"999\",\n"
                      + "                        \"courseStatus\": \"本申し込み\"\n"
                      + "                    }\n"
                      + "                }\n"
                      + "            }\n"
                      + "        ]\n"
                      + "    }"

              )
          )
      ))
  @PutMapping("/updateStudent")
  @ApiResponse(responseCode = "200", description = "更新が完了しました")
  public ResponseEntity<String> updateStudent(
      @Parameter(description = "更新内容")
      @RequestBody
      StudentDetail studentDetail
  ) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が完了しました。");
  }


  /**
   * 例外処理を行います。
   *
   * @return　メッセージ
   */
  @Operation(summary = "例外処理", description = "例外処理を行います")
  @ApiResponse(responseCode = "400", description = "現在このAPIは利用出来ません。別のAPIを試してください。")
  @GetMapping("/getErrorMessage")
  public String getErrorMessage() throws TestException {
    throw new TestException("現在このAPIは利用出来ません。別のAPIを試してください。");
  }
}
