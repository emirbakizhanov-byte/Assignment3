package repositories.impl.inmemory;

import entities.Course;
import repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {
    private final List<Course> courses = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Course create(Course course) {
        Course created = new Course(
                nextId++,
                course.getTitle(),
                course.getCapacity(),
                course.getDayOfWeek(),
                course.getStartMinute(),
                course.getEndMinute()
        );
        courses.add(created);
        return created;
    }

    @Override
    public Optional<Course> findById(int id) {
        return courses.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    @Override
    public void deleteById(int id) {
        courses.removeIf(c -> c.getId() == id);
    }
}
