package factories;
import entities.Course;
import entities.CourseType;
import entities.LectureCourse;
import entities.LabCourse;
import entities.OnlineCourse;


public class CourseFactory {

    private CourseFactory() {}

    public static Course createCourse(
            CourseType type,
            int id,
            String title,
            int capacity,
            String dayOfWeek,
            int startMinute,
            int endMinute,
            String extra // hall / labRoom / platform
    ) {
        return switch (type) {
            case LECTURE -> new LectureCourse(id, title, capacity, dayOfWeek, startMinute, endMinute, extra);
            case LAB -> new LabCourse(id, title, capacity, dayOfWeek, startMinute, endMinute, extra);
            case ONLINE -> new OnlineCourse(id, title, capacity, dayOfWeek, startMinute, endMinute, extra);
        };
    }
}
