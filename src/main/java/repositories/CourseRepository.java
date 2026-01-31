package repositories;

import entities.Course;
import repositories.base.Repository;

import java.util.Optional;

public interface CourseRepository extends Repository<Course, Integer> {

    Optional<Course> findById(int id);
    void deleteById(int id);

    @Override
    default Optional<Course> findById(Integer id) {
        return findById(id.intValue());
    }

    @Override
    default void deleteById(Integer id) {
        deleteById(id.intValue());
    }
}
