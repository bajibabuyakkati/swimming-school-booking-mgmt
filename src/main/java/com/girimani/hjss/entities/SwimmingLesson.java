package com.girimani.hjss.entities;

import java.util.ArrayList;
import java.util.List;

public class SwimmingLesson {
    private int id;
    private TimeSlot timeSlot;
    private Coach coach;
    private ClassDay day;
    private int grade;
    private List<Learner> attendees;

    public SwimmingLesson() {}

    public SwimmingLesson(int id, TimeSlot timeSlot, Coach coach, ClassDay day, int grade) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.coach = coach;
        this.day = day;
        this.grade = grade;
        this.attendees = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFull() {
        return this.attendees.size() == 4;
    }

    public void addAttendee(Learner learner) {
        this.attendees.add(learner);
    }

    public void removeAttendee(Learner learner) {
        this.attendees.remove(learner);
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public ClassDay getDay() {
        return day;
    }

    public void setDay(ClassDay day) {
        this.day = day;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Learner> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Learner> attendees) {
        this.attendees = attendees;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SwimmingLesson other = (SwimmingLesson) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "SwimmingLesson{" +
                "id=" + id +
                ", timeSlot=" + timeSlot +
                ", coach=" + coach +
                ", day=" + day +
                ", grade=" + grade +
                ", attendees=" + attendees +
                '}';
    }
}
