package services;

import exceptions.ActiveEnrollmentException;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import repositories.CourseRepository;
import repositories.EnrollmentRepository;

public class CourseService {
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;

    public CourseService(CourseRepository courseRepo, EnrollmentRepository enrollmentRepo) {
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public void deleteCourse(int courseId) {
        if (courseId <= 0) throw new InvalidInputException("courseId must be > 0");

        courseRepo.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));

        if (enrollmentRepo.hasEnrollmentsForCourse(courseId)) {
            throw new ActiveEnrollmentException("Cannot delete course. Active enrollments exist.");
        }

        courseRepo.deleteById(courseId);
    }
}
