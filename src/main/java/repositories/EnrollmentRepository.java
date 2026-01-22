package repositories;

import entities.Enrollment;
import entities.Student;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    Enrollment create(Enrollment enrollment);
    Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId);

    List<Enrollment> findAll();
    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);

    int countByCourseId(int courseId);
    boolean hasEnrollmentsForCourse(int courseId);

    void deleteByStudentAndCourse(int studentId, int courseId);
}
