package entities;

public class Course {
    private int id;
    private String title;
    private int capacity;

    // for time conflict check
    private String dayOfWeek;   // e.g. "MON"
    private int startMinute;    // e.g. 10:30 -> 630
    private int endMinute;      // e.g. 12:00 -> 720

    public Course() {}

    public Course(int id, String title, int capacity, String dayOfWeek, int startMinute, int endMinute) {
        this.id = id;
        this.title = title;
        this.capacity = capacity;
        this.dayOfWeek = dayOfWeek;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }

    public Course(String title, int capacity, String dayOfWeek, int startMinute, int endMinute) {
        this.title = title;
        this.capacity = capacity;
        this.dayOfWeek = dayOfWeek;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getStartMinute() { return startMinute; }
    public void setStartMinute(int startMinute) { this.startMinute = startMinute; }

    public int getEndMinute() { return endMinute; }
    public void setEndMinute(int endMinute) { this.endMinute = endMinute; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", title='" + title + "', capacity=" + capacity +
                ", dayOfWeek='" + dayOfWeek + "', startMinute=" + startMinute + ", endMinute=" + endMinute + "}";
    }
}


