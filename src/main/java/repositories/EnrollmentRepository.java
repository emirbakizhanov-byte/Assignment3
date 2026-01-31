package repositories;

import entities.Enrollment;
import repositories.base.Repository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends Repository<Enrollment, Integer> {

    Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId);

    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);

    int countByCourseId(int courseId);
    boolean hasEnrollmentsForCourse(int courseId);

    void deleteByStudentAndCourse(int studentId, int courseId);
}
