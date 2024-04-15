package com.girimani.hjss.entities;

import com.girimani.hjss.exceptions.LearnerAgeLimitException;

import java.util.ArrayList;
import java.util.List;

public class Learner {
    private String name;
    private String gender;
    private int age;
    private String emergencyContact;
    private int grade;
    private List<SwimmingLesson> lessonsBooked;
    private List<SwimmingLesson> lessonsAttended;
    private List<SwimmingLesson> lessonsCancelled;

    public Learner(String name, String gender , int age, String emergencyContact, int grade) throws LearnerAgeLimitException {
        if(age < 4 || age > 11) {
            throw new LearnerAgeLimitException("Age should be between 4 and 11");
        }
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.grade = grade;
        this.lessonsBooked = new ArrayList<>();
        this.lessonsAttended = new ArrayList<>();
        this.lessonsCancelled = new ArrayList<>();
    }

    public boolean isLessonAttended(SwimmingLesson lesson) {
        return lessonsAttended.contains(lesson);
    }

    public boolean isLessonBooked(SwimmingLesson lesson) {
        return lessonsBooked.contains(lesson);
    }

    public boolean isEligibleForLesson(SwimmingLesson lesson){
        return grade == lesson.getGrade() || grade == lesson.getGrade() - 1;
    }

    public void bookLesson(SwimmingLesson lesson) {
        lessonsBooked.add(lesson);
    }

    public void attendLesson(SwimmingLesson lesson) {
        lessonsBooked.remove(lesson);
        lessonsAttended.add(lesson);
        setGrade(lesson.getGrade());
    }

    public void cancelLesson(SwimmingLesson lesson) {
        lessonsBooked.remove(lesson);
        lessonsCancelled.add(lesson);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<SwimmingLesson> getLessonsBooked() {
        return lessonsBooked;
    }

    public List<SwimmingLesson> getLessonsAttended() {
        return lessonsAttended;
    }


    public List<SwimmingLesson> getLessonsCancelled() {
        return lessonsCancelled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Learner learner = (Learner) o;
        return name.equals(learner.name);
    }


    @Override
    public String toString() {
        String sb = " Name : " + name + "\n" +
                "Gender : " + gender + "\n" +
                "Age : " + age + "\n" +
                "Emergency Contact Number: " + emergencyContact + "\n" +
                "Grade : " + grade + "\n" +
                "Lessons Booked : " + lessonsBooked.size() + "\n" +
                "Lessons Attended : " + lessonsAttended.size() + "\n" +
                "Lessons Cancelled : " + lessonsCancelled.size() + "\n";
        return sb;
    }
}
