package repositories.impl.jdbc;

import entities.Course;
import repositories.CourseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCourseRepository implements CourseRepository {

    private final Connection connection;

    public JdbcCourseRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Course create(Course course) {
        String sql = """
                INSERT INTO course (title, capacity, day_of_week, start_minute, end_minute)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDayOfWeek());
            stmt.setInt(4, course.getStartMinute());
            stmt.setInt(5, course.getEndMinute());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    course.setId(rs.getInt("id"));
                    return course;
                }
            }

            throw new SQLException("Course insert failed");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create course", e);
        }
    }

    @Override
    public Optional<Course> findById(int id) {
        String sql = """
                SELECT id, title, capacity, day_of_week, start_minute, end_minute
                FROM course
                WHERE id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapCourse(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find course id=" + id, e);
        }
    }

    @Override
    public List<Course> findAll() {
        String sql = """
                SELECT id, title, capacity, day_of_week, start_minute, end_minute
                FROM course
                ORDER BY id
                """;

        List<Course> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapCourse(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to list courses", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM course WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete course id=" + id, e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("capacity"),
                rs.getString("day_of_week"),
                rs.getInt("start_minute"),
                rs.getInt("end_minute")
        );
    }
}
