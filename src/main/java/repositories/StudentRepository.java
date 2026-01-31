package repositories;

import entities.Student;
import repositories.base.Repository;

import java.util.Optional;

public interface StudentRepository extends Repository<Student, Integer> {

    // your existing method
    Optional<Student> findById(int id);

    // bridge for generic Repository<Integer>
    @Override
    default Optional<Student> findById(Integer id) {
        return findById(id.intValue());
    }
}
