package com.girimani.hjss.service;

import com.girimani.hjss.entities.*;
import com.girimani.hjss.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SwimmingSchoolTest {

    private SwimmingSchoolManagement swimmingSchool;
    private Learner learner;
    private SwimmingLesson lesson;
    @Before
    public void setUp() throws LearnerAgeLimitException {
        swimmingSchool = new SwimmingSchoolManagement();
        learner = new Learner("Adithya","Male", 10,"+94771234567",1);
        lesson = new SwimmingLesson();
        lesson.setId(1234);
        lesson.setGrade(2);
        lesson.setAttendees(new ArrayList<>());
    }

    @Test
    public void testAddCoachVerifySuccessful() throws CoachAlreadyExistsException {
        Coach coach = new Coach("CoachABC");
        swimmingSchool.addCoach(coach);
        assertTrue(swimmingSchool.getCoaches().contains(coach));
    }

    @Test(expected = CoachAlreadyExistsException.class)
    public void testAddCoachVerifyAlreadyExists() throws CoachAlreadyExistsException {
        Coach coach = new Coach("CoachXYZ");
        swimmingSchool.addCoach(coach); // Adding the coach once
        swimmingSchool.addCoach(coach); // Trying to add the same coach again should throw an exception
    }

    @Test
    public void testRemoveCoachVerifySuccessful() throws CoachAlreadyExistsException {
        Coach coach = new Coach("CoachPQR");
        swimmingSchool.addCoach(coach);
        assertTrue(swimmingSchool.getCoaches().contains(coach));
        swimmingSchool.removeCoach(coach);
        assertFalse(swimmingSchool.getCoaches().contains(coach));
    }

    @Test
    public void testAssignCoachToLessonVerifySuccessful() throws SwimmingLessonsAlreadyHasCoachException {
        Coach coach = new Coach("CoachABC");
        SwimmingLesson lesson = new SwimmingLesson();
        lesson.setId(1);
        lesson.setGrade(2);
        lesson.setAttendees(new ArrayList<>());
        swimmingSchool.coachAssignToLesson(coach, lesson);
        assertEquals(coach, lesson.getCoach());
    }
    @Test
    public void testCancelLessonVerifySuccessful() throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException, LessonUnBookedException, LessonIsAttendedException, LearnerAlreadyExistsException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.cancelLesson(learner, lesson);
        assertFalse(learner.isLessonBooked(lesson));
        assertFalse(lesson.getAttendees().contains(learner));
    }

    @Test(expected = LessonUnBookedException.class)
    public void testCancelLessonVerifyNotBooked() throws LessonUnBookedException, LessonIsAttendedException {
        swimmingSchool.cancelLesson(learner, lesson);
    }

    @Test
    public void testAddLearnerVerifySuccessful() throws LearnerAlreadyExistsException, LearnerAgeLimitException {
        swimmingSchool.addLearner(learner);
        assertTrue(swimmingSchool.getLearners().contains(learner));
    }

    @Test(expected = LearnerAlreadyExistsException.class)
    public void testAddLearnerVerifyAlreadyExists() throws LearnerAlreadyExistsException, LearnerAgeLimitException {
        swimmingSchool.addLearner(learner); // Adding the learner once
        swimmingSchool.addLearner(learner); // Trying to add the same learner again should throw an exception
    }

    @Test
    public void testRemoveLearnerVerifySuccessful() throws LearnerAlreadyExistsException, LearnerAgeLimitException {
        swimmingSchool.addLearner(learner);
        assertTrue(swimmingSchool.getLearners().contains(learner));
        swimmingSchool.removeLearner(learner);
        assertFalse(swimmingSchool.getLearners().contains(learner));
    }

    @Test(expected = SwimmingLessonsAlreadyHasCoachException.class)
    public void testAssignCoachToLessonVerifyAlreadyHasCoach() throws SwimmingLessonsAlreadyHasCoachException {
        Coach coach1 = new Coach("CoachPQR");
        Coach coach2 = new Coach("CoachBCD");
        SwimmingLesson lesson = new SwimmingLesson();
        lesson.setId(2);
        lesson.setGrade(2);
        lesson.setAttendees(new ArrayList<>());
        swimmingSchool.coachAssignToLesson(coach1, lesson); // Assigning the first coach
        swimmingSchool.coachAssignToLesson(coach2, lesson); // Trying to assign another coach should throw an exception
    }

    @Test
    public void testBookLessonVerifySuccessful() throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException, LearnerAlreadyExistsException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        assertTrue(learner.isLessonBooked(lesson));
        assertTrue(lesson.getAttendees().contains(learner));
    }

    @Test(expected = LessonAlreadyBookedException.class)
    public void testBookLessonVerifyAlreadyBooked() throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException, LearnerAlreadyExistsException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.bookLesson(learner, lesson);
    }



    @Test
    public void testAttendLessonVerifySuccessful() throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException, LessonUnBookedException, LearnerAlreadyExistsException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.attendLesson(learner, lesson);
        assertTrue(learner.isLessonAttended(lesson));
    }

    private String showOutput(Runnable code) {
        var outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        code.run();
        System.setOut(System.out);
        return outContent.toString();
    }

    @Test
    public void testScheduleViewTimetableByCoach() {
        Coach coach = new Coach("ABCD");
        SwimmingLesson lesson = new SwimmingLesson(1,TimeSlot.From2To3, coach, ClassDay.MONDAY, 1);
        swimmingSchool.addLesson(lesson);
        String output = showOutput(() -> swimmingSchool.scheduleViewByGradeCoach(coach));
        assertNotNull(output);
        assertNotEquals(-1, output.indexOf("Swimming Lesson by ABCD:"));
        assertNotEquals(-1, output.indexOf("2:00 PM - 3:00 PM"));
    }

    @Test(expected = LessonUnBookedException.class)
    public void testAttendLessonVerifyNotBooked() throws LessonUnBookedException {
        swimmingSchool.attendLesson(learner, lesson);
    }

    @Test
    public void testSubmitFeedbackVerifySuccessful() throws LessonIneligibleException, InvalidRatingNumberException, FeebackReviewException, LearnerAlreadyExistsException, LessonExceededException, LessonAlreadyBookedException, LessonUnBookedException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.attendLesson(learner, lesson);

        int rating = 5;
        swimmingSchool.submitFeedback(learner, lesson, rating);

        FeebackReview feebackReview = swimmingSchool.getReviews().get(0);
        assertEquals(learner, feebackReview.getLearner());
        assertEquals(lesson, feebackReview.getLesson());
        assertEquals(rating, feebackReview.getRating());
    }

    @Test(expected = LessonIneligibleException.class)
    public void testSubmitFeedbackVerifyNotEligibleForLesson() throws LessonIneligibleException, InvalidRatingNumberException, FeebackReviewException, LearnerAlreadyExistsException, LessonExceededException, LessonAlreadyBookedException {
        SwimmingLesson lesson = new SwimmingLesson();
        lesson.setId(1234);
        lesson.setGrade(3);
        lesson.setAttendees(new ArrayList<>());
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.submitFeedback(learner, lesson, 5); // Learner hasn't booked or attended the lesson
    }

    @Test(expected = FeebackReviewException.class)
    public void testSubmitFeedbackVerifyNotAttendedLesson() throws InvalidRatingNumberException, FeebackReviewException, LearnerAlreadyExistsException, LessonExceededException, LessonAlreadyBookedException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.submitFeedback(learner, lesson, 5); // Learner hasn't attended the lesson
    }

    @Test(expected = InvalidRatingNumberException.class)
    public void testSubmitFeedbackVerifyRatingOutOfRange() throws LessonIneligibleException, InvalidRatingNumberException, FeebackReviewException, LearnerAlreadyExistsException, LessonExceededException, LessonAlreadyBookedException, LessonUnBookedException {
        swimmingSchool.addLearner(learner);
        swimmingSchool.addLesson(lesson);
        swimmingSchool.bookLesson(learner, lesson);
        swimmingSchool.attendLesson(learner, lesson);
        swimmingSchool.submitFeedback(learner, lesson, 6); // Rating out of range
    }

    @Test
    public void testScheduleViewByDay() {
        Coach coach = new Coach("ABCD");
        SwimmingLesson lesson = new SwimmingLesson(1, TimeSlot.From2To3, coach, ClassDay.MONDAY, 1);
        swimmingSchool.addLesson(lesson);
        String output = showOutput(() -> swimmingSchool.scheduleViewByDayWise(ClassDay.MONDAY));
        assertNotNull(output);
        assertNotEquals(-1, output.indexOf("Swimming Lessons on Monday:"));
        assertNotEquals(-1, output.indexOf("2:00 PM - 3:00 PM"));
    }

    @Test
    public void testScheduleViewTimetableByGrade() {
        Coach coach = new Coach("ABCD");
        SwimmingLesson lesson = new SwimmingLesson(1,TimeSlot.From2To3, coach, ClassDay.MONDAY, 1);
        swimmingSchool.addLesson(lesson);
        String output = showOutput(() -> swimmingSchool.scheduleViewByGradeWise(1));
        assertNotNull(output);
        assertNotEquals(-1, output.indexOf("Swimming Lessons for Grade 1:"));
        assertNotEquals(-1, output.indexOf("2:00 PM - 3:00 PM"));
    }


}