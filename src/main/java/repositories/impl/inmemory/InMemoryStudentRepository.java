package repositories.impl.inmemory;

import entities.Student;
import repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository {

    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Student create(Student student) {
        student.setId(nextId++);
        students.add(student);
        return student;
    }

    @Override
    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }


    public void deleteById(int id) {
        students.removeIf(s -> s.getId() == id);
    }
}

