package builders;

import entities.Course;
import entities.StudentSchedule;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduleBuilder {
    private int studentId;
    private final List<Course> courses = new ArrayList<>();

    public StudentScheduleBuilder studentId(int studentId) {
        this.studentId = studentId;
        return this;
    }

    public StudentScheduleBuilder addCourse(Course c) {
        this.courses.add(c);
        return this;
    }

    public StudentScheduleBuilder addCourses(List<Course> list) {
        this.courses.addAll(list);
        return this;
    }

    public StudentSchedule build() {
        // فعلاً totalCredits = تعداد course ها (اگر credit فیلد ندارید)
        int totalCredits = courses.size() * 3; // یا courses.size()

        StringBuilder sb = new StringBuilder();
        sb.append("=== Student Schedule ===\n");
        sb.append("StudentId: ").append(studentId).append("\n");
        sb.append("Total credits: ").append(totalCredits).append("\n");
        sb.append("Courses:\n");
        for (Course c : courses) {
            sb.append(" - ").append(c).append("\n");
        }

        return new StudentSchedule(studentId, courses, totalCredits, sb.toString());
    }
}

