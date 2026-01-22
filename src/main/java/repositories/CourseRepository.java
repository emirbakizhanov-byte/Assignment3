package repositories;

import entities.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course create(Course course);
    Optional<Course> findById(int id);
    List<Course> findAll();
    void deleteById(int id);
}

