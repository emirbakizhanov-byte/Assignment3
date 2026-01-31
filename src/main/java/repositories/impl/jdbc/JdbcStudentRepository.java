package repositories.impl.jdbc;


import entities.Student;
import repositories.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStudentRepository implements StudentRepository {

    private final Connection connection;

    public JdbcStudentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student create(Student student) {
        String sql = """
                INSERT INTO students (name)
                VALUES (?)
                RETURNING id
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getName());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student.setId(rs.getInt("id"));
                    return student;
                }
            }

            throw new SQLException("Student insert failed");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create student", e);
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        String sql = """
                SELECT id, name
                FROM students
                WHERE id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapStudent(rs));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find student id=" + id, e);
        }
    }

    @Override
    public List<Student> findAll() {
        String sql = """
                SELECT id, name
                FROM students
                ORDER BY id
                """;

        List<Student> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(rs));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list students", e);
        }
    }

    public void deleteById(int id) {
        // safe version: delete enrollments first (FK)
        try {
            try (PreparedStatement s1 = connection.prepareStatement(
                    "DELETE FROM enrollments WHERE student_id = ?"
            )) {
                s1.setInt(1, id);
                s1.executeUpdate();
            }

            try (PreparedStatement s2 = connection.prepareStatement(
                    "DELETE FROM students WHERE id = ?"
            )) {
                s2.setInt(1, id);
                s2.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student id=" + id, e);
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
