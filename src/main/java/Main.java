import db.DatabaseConnectionManager;
import repositories.CourseRepository;
import repositories.EnrollmentRepository;
import repositories.StudentRepository;
import repositories.impl.jdbc.JdbcCourseRepository;
import repositories.impl.jdbc.JdbcEnrollmentRepository;
import repositories.impl.jdbc.JdbcStudentRepository;
import ui.ConsoleUI;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = DatabaseConnectionManager.getConnection()) {

            StudentRepository studentRepo = new JdbcStudentRepository(conn);
            CourseRepository courseRepo = new JdbcCourseRepository(conn);
            EnrollmentRepository enrollmentRepo = new JdbcEnrollmentRepository(conn);

            new ConsoleUI(studentRepo, courseRepo, enrollmentRepo).start();

        } catch (Exception e) {
            System.out.println("Failed to start app: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
