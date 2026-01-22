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
        Student created = new Student(nextId++, student.getName());
        students.add(created);
        return created;
    }

    @Override
    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
}

