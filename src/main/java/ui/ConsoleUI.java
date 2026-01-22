package ui;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import exceptions.*;
import repositories.CourseRepository;
import repositories.EnrollmentRepository;
import repositories.StudentRepository;
import repositories.impl.inmemory.InMemoryCourseRepository;
import repositories.impl.inmemory.InMemoryEnrollmentRepository;
import repositories.impl.inmemory.InMemoryStudentRepository;
import services.CourseService;
import services.RegistrationService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    // In-memory repositories
    private final StudentRepository studentRepo = new InMemoryStudentRepository();
    private final CourseRepository courseRepo = new InMemoryCourseRepository();
    private final EnrollmentRepository enrollmentRepo = new InMemoryEnrollmentRepository();

    // Services
    private final RegistrationService registrationService =
            new RegistrationService(studentRepo, courseRepo, enrollmentRepo);

    private final CourseService courseService =
            new CourseService(courseRepo, enrollmentRepo);

    public void start() {
        seedData();

        while (true) {
            printMenu();
            int choice = readInt("Choose option: ");

            try {
                switch (choice) {
                    case 1 -> createStudent();
                    case 2 -> createCourse();
                    case 3 -> enrollStudent();
                    case 4 -> dropStudent();
                    case 5 -> listAllStudents();
                    case 6 -> listAllCourses();
                    case 7 -> listAllEnrollments();
                    case 8 -> viewEnrollmentsForCourse();
                    case 9 -> deleteCourse();
                    case 0 -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (RuntimeException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }

            System.out.println();
        }
    }

    private void seedData() {
        studentRepo.create(new Student("Ali"));
        studentRepo.create(new Student("Sara"));

        courseRepo.create(new Course("OOP", 2, "MON", 600, 720));      // 10:00-12:00
        courseRepo.create(new Course("Database", 2, "MON", 700, 800)); // conflict
        courseRepo.create(new Course("Math", 3, "TUE", 600, 660));     // no conflict
    }

    private void printMenu() {
        System.out.println("===== Campus Registration System =====");
        System.out.println("1) Create student");
        System.out.println("2) Create course");
        System.out.println("3) Enroll student in course");
        System.out.println("4) Drop student from course");
        System.out.println("5) List students");
        System.out.println("6) List courses");
        System.out.println("7) List all enrollments");
        System.out.println("8) View enrollments for a course");
        System.out.println("9) Delete course");
        System.out.println("0) Exit");
    }

    private void createStudent() {
        String name = readString("Enter student name: ");
        Student created = studentRepo.create(new Student(name));
        System.out.println("Created: " + created);
    }

    private void createCourse() {
        String title = readString("Enter course title: ");
        int capacity = readInt("Enter capacity: ");
        String day = readString("Enter dayOfWeek (MON/TUE/WED/THU/FRI): ");
        int start = readInt("Enter startMinute (e.g. 600 = 10:00): ");
        int end = readInt("Enter endMinute (e.g. 720 = 12:00): ");

        Course created = courseRepo.create(new Course(title, capacity, day, start, end));
        System.out.println("Created: " + created);
    }

    private void enrollStudent() {
        int studentId = readInt("Enter studentId: ");
        int courseId = readInt("Enter courseId: ");

        Enrollment e = registrationService.enroll(studentId, courseId);
        System.out.println("SUCCESS: " + e);
    }

    private void dropStudent() {
        int studentId = readInt("Enter studentId: ");
        int courseId = readInt("Enter courseId: ");

        registrationService.drop(studentId, courseId);
        System.out.println("SUCCESS: Dropped.");
    }

    private void listAllStudents() {
        List<Student> list = studentRepo.findAll();
        System.out.println("--- Students ---");
        list.forEach(System.out::println);
    }

    private void listAllCourses() {
        List<Course> list = courseRepo.findAll();
        System.out.println("--- Courses ---");
        list.forEach(System.out::println);
    }

    private void listAllEnrollments() {
        List<Enrollment> list = registrationService.listAllEnrollments();
        System.out.println("--- Enrollments ---");
        list.forEach(System.out::println);
    }

    private void viewEnrollmentsForCourse() {
        int courseId = readInt("Enter courseId: ");
        List<Enrollment> list = registrationService.listEnrollmentsForCourse(courseId);
        System.out.println("--- Enrollments for courseId=" + courseId + " ---");
        list.forEach(System.out::println);
    }

    private void deleteCourse() {
        int courseId = readInt("Enter courseId: ");
        courseService.deleteCourse(courseId);
        System.out.println("SUCCESS: Course deleted.");
    }

    // ---------- helper input methods ----------
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}

