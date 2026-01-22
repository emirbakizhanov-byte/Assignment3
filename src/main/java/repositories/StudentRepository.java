package repositories;

import entities.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student create(Student student);
    Optional<Student> findById(int id);
    List<Student> findAll();
}

