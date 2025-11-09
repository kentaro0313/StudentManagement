CREATE TABLE IF NOT EXISTS students
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(10),
  furigana VARCHAR(20),
  handle_name VARCHAR(10),
  mail_address VARCHAR(30),
  area VARCHAR(50),
  age INT,
  gender VARCHAR(8),
  remark VARCHAR(200),
  is_deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  course_name VARCHAR(10),
  start_date TIMESTAMP,
  complete_date TIMESTAMP
);