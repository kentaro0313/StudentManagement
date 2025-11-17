CREATE TABLE IF NOT EXISTS students
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(10) NOT NULL,
  furigana VARCHAR(20) NOT NULL,
  handle_name VARCHAR(10) NOT NULL,
  mail_address VARCHAR(30) NOT NULL,
  area VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(8),
  remark VARCHAR(200),
  is_deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course_name VARCHAR(10) NOT NULL,
  start_date_at TIMESTAMP NOT NULL,
  complete_date_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses_status
(
  status_id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT NOT NULL,
  course_status VARCHAR(10) NOT NULL
);