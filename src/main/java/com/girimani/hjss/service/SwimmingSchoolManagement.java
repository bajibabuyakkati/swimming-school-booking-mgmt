package com.girimani.hjss.service;


import com.girimani.hjss.entities.*;
import com.girimani.hjss.reportviews.CoachesReportView;
import com.girimani.hjss.reportviews.LearnersReportView;
import com.girimani.hjss.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class SwimmingSchoolManagement implements ISwimmingSchoolManagement {

    private List<SwimmingLesson> lessonList;
    private List<Coach> coachList;
    private List<Learner> learnerList;
    private List<FeebackReview> feebackReviewList;

    public SwimmingSchoolManagement(){
        this.lessonList = new ArrayList<>();
        this.coachList = new ArrayList<>();
        this.learnerList = new ArrayList<>();
        this.feebackReviewList = new ArrayList<>();
    }

    @Override
    public void coachAssignToLesson(Coach coach, SwimmingLesson lesson) throws SwimmingLessonsAlreadyHasCoachException {
        if(lesson.getCoach() != null){
            throw new SwimmingLessonsAlreadyHasCoachException("This lesson already has a coach");
        }
        lesson.setCoach(coach);
    }

    @Override
    public void addLearner(Learner learner) throws LearnerAlreadyExistsException {
        if(learnerList.contains(learner)){
            throw new LearnerAlreadyExistsException("Learner already exists");
        }
        learnerList.add(learner);
    }

    @Override
    public void removeLearner(Learner learner) {
        learnerList.remove(learner);
    }

    @Override
    public void addCoach(Coach coach) throws CoachAlreadyExistsException {
        if(coachList.contains(coach)){
           throw new CoachAlreadyExistsException("Coach already exists");
        }
        coachList.add(coach);
    }

    @Override
    public void removeCoach(Coach coach) {
        coachList.remove(coach);
    }

    @Override
    public void addLesson(SwimmingLesson lesson) {
        if(!lessonList.contains(lesson)){
            lessonList.add(lesson);
        }
        lessonList.add(lesson);
    }

    @Override
    public void removeLesson(SwimmingLesson lesson) {
        lessonList.remove(lesson);
    }

    @Override
    public void scheduleViewByDayWise(ClassDay day) {
        List<SwimmingLesson> lessonsByDay = lessonList.stream()
                .filter(lesson -> lesson.getDay() == day).toList();
        printSeparator();
        System.out.println("Swimming Lessons on " + day + ":");
        printSeparator();
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Lesson ID", "Day", "Time", "Grade", "Coach");
        for(SwimmingLesson lesson : lessonsByDay){
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
                    lesson.getId(),
                    lesson.getDay(),
                    lesson.getTimeSlot(),
                    lesson.getGrade(),
                    lesson.getCoach().getName()
            );
        }
        printSeparator();
    }

    @Override
    public void scheduleViewByGradeWise(int grade) {
        List<SwimmingLesson> lessonsByGrade = lessonList.stream()
                .filter(lesson -> lesson.getGrade() == grade)
                .toList();
        printSeparator();
        System.out.println("Swimming Lessons for Grade " + grade + ":");
        printSeparator();
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Lesson ID", "Day", "Time", "Grade", "Coach");
        for(SwimmingLesson lesson : lessonsByGrade){
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
                    lesson.getId(),
                    lesson.getDay(),
                    lesson.getTimeSlot(),
                    lesson.getGrade(),
                    lesson.getCoach().getName()
            );
        }
        printSeparator();
    }

    @Override
    public void scheduleViewByGradeCoach(Coach coach) {
        List<SwimmingLesson> lessonsByCoach = lessonList.stream()
                .filter(lesson -> lesson.getCoach().equals(coach))
                .toList();
        printSeparator();
        System.out.println("Swimming Lesson by " + coach.getName() + ":");
        printSeparator();
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Lesson ID", "Day", "Time", "Grade", "Coach");
        for(SwimmingLesson lesson : lessonsByCoach){
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
                    lesson.getId(),
                    lesson.getDay(),
                    lesson.getTimeSlot(),
                    lesson.getGrade(),
                    lesson.getCoach().getName()
            );
        }
        printSeparator();
    }

    @Override
    public void bookLesson(Learner learner, SwimmingLesson lesson) throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException {
        if(!learner.isEligibleForLesson(lesson)){
            throw new LessonIneligibleException("You are not eligible for this lesson.");
        }
        if(learner.isLessonBooked(lesson)){
           throw new LessonAlreadyBookedException("You are already attending this lesson.");
        }
        if(lesson.isFull()){
           throw new LessonExceededException("This lesson is fully occupied.");
        }
        lesson.addAttendee(learner);
        learner.bookLesson(lesson);
    }

    @Override
    public void cancelLesson(Learner learner, SwimmingLesson lesson) throws LessonUnBookedException, LessonIsAttendedException {
        if(!learner.isLessonBooked(lesson)){
            throw new LessonUnBookedException("You are not going to attend this lesson");
        }
        if(learner.isLessonAttended(lesson)){
            throw new LessonIsAttendedException("You can't cancel a lesson that you have already attended");
        }
        lesson.removeAttendee(learner);
        learner.cancelLesson(lesson);
    }

    @Override
    public void attendLesson(Learner learner, SwimmingLesson lesson) throws LessonUnBookedException {
        if(!learner.isLessonBooked(lesson)){
            throw new LessonUnBookedException("You can't attend a lesson that you haven't booked");
        }
        learner.attendLesson(lesson);
    }

    @Override
    public void submitFeedback(Learner learner, SwimmingLesson lesson, int rating) throws InvalidRatingNumberException, FeebackReviewException {
        if(rating < 1 || rating > 5){
            throw new InvalidRatingNumberException("Rating must be between 1 to 5 only.");
        }
        System.out.println("lesson.getAttendees().contains(learner)");
        System.out.println(lesson.getAttendees().contains(learner));
        if(!lesson.getAttendees().contains(learner)){
           throw new FeebackReviewException("You can't write a review for a lesson that you haven't attended");
        }
        feebackReviewList.add(
                new FeebackReview(learner, rating, lesson)
        );
    }

    @Override
    public void learnersReportView() {
        LearnersReportView.viewReport(this);
    }

    @Override
    public void CoachesReportView() {
        CoachesReportView.viewReport(this);
    }

    public List<SwimmingLesson> getLessons() {
        return lessonList;
    }

    public List<Coach> getCoaches() {
        return coachList;
    }

    public List<Learner> getLearners() {
        return learnerList;
    }

    public List<FeebackReview> getReviews() {
        return feebackReviewList;
    }

    private void printSeparator(){
        System.out.println("---------------------------------------------");
    }
}
