package repositories.impl.jdbc;

import entities.Enrollment;
import repositories.EnrollmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcEnrollmentRepository implements EnrollmentRepository {

    private final Connection connection;

    public JdbcEnrollmentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Enrollment create(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    enrollment.setId(rs.getInt("id"));
                    return enrollment;
                }
                throw new RuntimeException("Failed to create enrollment");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create enrollment", e);
        }
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
        String sql = "SELECT id, student_id, course_id FROM enrollments WHERE student_id=? AND course_id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapEnrollment(rs));
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find enrollment", e);
        }
    }

    @Override
    public List<Enrollment> findAll() {
        String sql = "SELECT id, student_id, course_id FROM enrollments";
        List<Enrollment> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(mapEnrollment(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all enrollments", e);
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        String sql = "SELECT id, student_id, course_id FROM enrollments WHERE student_id=?";
        List<Enrollment> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapEnrollment(rs));
                return list;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find enrollments by student", e);
        }
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        String sql = "SELECT id, student_id, course_id FROM enrollments WHERE course_id=?";
        List<Enrollment> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapEnrollment(rs));
                return list;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find enrollments by course", e);
        }
    }

    @Override
    public int countByCourseId(int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE course_id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to count enrollments", e);
        }
    }

    @Override
    public boolean hasEnrollmentsForCourse(int courseId) {
        return countByCourseId(courseId) > 0;
    }

    @Override
    public void deleteByStudentAndCourse(int studentId, int courseId) {
        String sql = "DELETE FROM enrollments WHERE student_id=? AND course_id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete enrollment", e);
        }
    }

    private Enrollment mapEnrollment(ResultSet rs) throws SQLException {
        Enrollment e = new Enrollment();
        e.setId(rs.getInt("id"));
        e.setStudentId(rs.getInt("student_id"));
        e.setCourseId(rs.getInt("course_id"));
        return e;
    }
}
