package repositories.impl.inmemory;

import entities.Enrollment;
import repositories.EnrollmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryEnrollmentRepository implements EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Enrollment create(Enrollment enrollment) {
        Enrollment created = new Enrollment(nextId++, enrollment.getStudentId(), enrollment.getCourseId());
        enrollments.add(created);
        return created;
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
        return enrollments.stream()
                .filter(e -> e.getStudentId() == studentId && e.getCourseId() == courseId)
                .findFirst();
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        return enrollments.stream()
                .filter(e -> e.getStudentId() == studentId)
                .toList();
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        return enrollments.stream()
                .filter(e -> e.getCourseId() == courseId)
                .toList();
    }

    @Override
    public int countByCourseId(int courseId) {
        return (int) enrollments.stream().filter(e -> e.getCourseId() == courseId).count();
    }

    @Override
    public boolean hasEnrollmentsForCourse(int courseId) {
        return enrollments.stream().anyMatch(e -> e.getCourseId() == courseId);
    }

    @Override
    public void deleteByStudentAndCourse(int studentId, int courseId) {
        enrollments.removeIf(e -> e.getStudentId() == studentId && e.getCourseId() == courseId);
    }
}

