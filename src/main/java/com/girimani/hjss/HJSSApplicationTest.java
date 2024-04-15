package com.girimani.hjss;

import com.girimani.hjss.service.SwimmingSchoolManagement;
import com.girimani.hjss.entities.*;
import com.girimani.hjss.exceptions.CoachAlreadyExistsException;
import com.girimani.hjss.exceptions.LearnerAgeLimitException;

public class HJSSApplicationTest {

    public static void main(String[] args) throws LearnerAgeLimitException {
        SwimmingSchoolManagement swimmingSchool = new SwimmingSchoolManagement();

        System.out.println("Adding 5 coach list...");
        Coach coach1 = new Coach("Coach1");
        Coach coach2 = new Coach("Coach2");
        Coach coach3 = new Coach("Coach3");
        Coach coach4 = new Coach("Coach4");
        Coach coach5 = new Coach("Coach5");
        try {
            swimmingSchool.addCoach(coach1);
            swimmingSchool.addCoach(coach2);
            swimmingSchool.addCoach(coach3);
            swimmingSchool.addCoach(coach4);
            swimmingSchool.addCoach(coach5);
        } catch (CoachAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Adding 10 learner list...");
        Learner learner1 = new Learner("Learner1", "Female", 5, "123456789", 1);
        Learner learner2 = new Learner("Learner2", "Male", 7, "123456789", 1);
        Learner learner3 = new Learner("Learner3", "Female", 8, "123456789", 1);
        Learner learner4 = new Learner("Learner4", "Male", 7, "123456789", 1);
        Learner learner5 = new Learner("Learner5", "Female", 9, "123456789", 1);
        Learner learner6 = new Learner("Learner6", "Female", 6, "123456789", 1);
        Learner learner7 = new Learner("Learner7", "Male", 7, "123456789", 1);
        Learner learner8 = new Learner("Learner8", "Female", 8, "123456789", 1);
        Learner learner9 = new Learner("Learner9", "Female", 10, "123456789", 1);
        Learner learner10 = new Learner("Learner10", "Male", 6, "123456789", 1);

        try {
            swimmingSchool.addLearner(learner1);
            swimmingSchool.addLearner(learner2);
            swimmingSchool.addLearner(learner3);
            swimmingSchool.addLearner(learner4);
            swimmingSchool.addLearner(learner5);
            swimmingSchool.addLearner(learner6);
            swimmingSchool.addLearner(learner7);
            swimmingSchool.addLearner(learner8);
            swimmingSchool.addLearner(learner9);
            swimmingSchool.addLearner(learner10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Adding 5 lesson list...");
        SwimmingLesson lesson1 = new SwimmingLesson(1, TimeSlot.From2To3, coach1, ClassDay.MONDAY, 2);
        SwimmingLesson lesson2 = new SwimmingLesson(2, TimeSlot.From3To4, coach2, ClassDay.WEDNESDAY, 1);
        SwimmingLesson lesson3 = new SwimmingLesson(3, TimeSlot.From4To5, coach3, ClassDay.FRIDAY, 2);
        SwimmingLesson lesson4 = new SwimmingLesson(4, TimeSlot.From5To6, coach4, ClassDay.SATURDAY, 1);
        SwimmingLesson lesson5 = new SwimmingLesson(5, TimeSlot.From6To7, coach5, ClassDay.MONDAY, 2);
        try {
            swimmingSchool.addLesson(lesson1);
            swimmingSchool.addLesson(lesson2);
            swimmingSchool.addLesson(lesson3);
            swimmingSchool.addLesson(lesson4);
            swimmingSchool.addLesson(lesson5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Booking the lessons...");
        bookLesson(swimmingSchool, learner1, lesson1);
        bookLesson(swimmingSchool, learner1, lesson2);
        bookLesson(swimmingSchool, learner2, lesson2);
        bookLesson(swimmingSchool, learner2, lesson3);
        bookLesson(swimmingSchool, learner3, lesson3);
        bookLesson(swimmingSchool, learner3, lesson4);
        bookLesson(swimmingSchool, learner4, lesson4);
        bookLesson(swimmingSchool, learner4, lesson5);
        bookLesson(swimmingSchool, learner5, lesson5);
        bookLesson(swimmingSchool, learner5, lesson1);

        System.out.println("Attending the lessons...");
        attendLesson(swimmingSchool, learner1, lesson1);
        attendLesson(swimmingSchool, learner1, lesson2);
        attendLesson(swimmingSchool, learner2, lesson2);
        attendLesson(swimmingSchool, learner2, lesson3);
        attendLesson(swimmingSchool, learner4, lesson5);
        attendLesson(swimmingSchool, learner5, lesson5);
        attendLesson(swimmingSchool, learner5, lesson1);

        System.out.println("Cancelling the lessons...");
        cancelLesson(swimmingSchool, learner3, lesson3);
        cancelLesson(swimmingSchool, learner3, lesson4);
        cancelLesson(swimmingSchool, learner4, lesson4);

        // Display timetables
        System.out.println("Displaying timetables for Monday");
        swimmingSchool.scheduleViewByDayWise(ClassDay.MONDAY);
        System.out.println("Displaying timetables for Grade 2");
        swimmingSchool.scheduleViewByGradeWise(2);
        System.out.println("Displaying timetables for Coach1");
        swimmingSchool.scheduleViewByGradeCoach(coach1);

        // Adding reviews
        System.out.println("Adding reviews...");
        try{
            swimmingSchool.submitFeedback(learner1, lesson1,5);
            swimmingSchool.submitFeedback(learner1, lesson2,4);
            swimmingSchool.submitFeedback(learner2, lesson2,4);
            swimmingSchool.submitFeedback(learner2, lesson3,4);
            swimmingSchool.submitFeedback(learner4, lesson5,3);
            swimmingSchool.submitFeedback(learner5, lesson5,2);
            swimmingSchool.submitFeedback(learner5, lesson1,5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Generate reports
        System.out.println("Learners Report View..");
        swimmingSchool.learnersReportView();

        System.out.println("Coaches Report View...");
        swimmingSchool.CoachesReportView();
    }

    private static void bookLesson(SwimmingSchoolManagement swimmingSchool, Learner learner, SwimmingLesson lesson) {
        try {
            swimmingSchool.bookLesson(learner, lesson);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void attendLesson(SwimmingSchoolManagement swimmingSchool, Learner learner, SwimmingLesson lesson) {
        try {
            swimmingSchool.attendLesson(learner, lesson);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelLesson(SwimmingSchoolManagement swimmingSchool, Learner learner, SwimmingLesson lesson) {
        try {
            swimmingSchool.cancelLesson(learner, lesson);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}