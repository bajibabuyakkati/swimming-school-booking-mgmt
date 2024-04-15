package com.girimani.hjss.service;

import com.girimani.hjss.entities.Coach;
import com.girimani.hjss.entities.Learner;
import com.girimani.hjss.entities.ClassDay;
import com.girimani.hjss.entities.SwimmingLesson;
import com.girimani.hjss.exceptions.*;

public interface ISwimmingSchoolManagement {

    void scheduleViewByDayWise(ClassDay day);
    void scheduleViewByGradeWise(int grade);
    void scheduleViewByGradeCoach(Coach coach);

    void bookLesson(Learner learner, SwimmingLesson lesson) throws LessonIneligibleException, LessonAlreadyBookedException, LessonExceededException;
    void cancelLesson(Learner learner, SwimmingLesson lesson) throws LessonUnBookedException, LessonIsAttendedException;
    void attendLesson(Learner learner, SwimmingLesson lesson) throws LessonUnBookedException;

    void coachAssignToLesson(Coach coach, SwimmingLesson lesson) throws SwimmingLessonsAlreadyHasCoachException;

    void addLearner(Learner learner) throws LearnerAlreadyExistsException;
    void removeLearner(Learner learner);

    void learnersReportView();
    void CoachesReportView();

    void addCoach(Coach coach) throws CoachAlreadyExistsException;
    void removeCoach(Coach coach);

    void addLesson(SwimmingLesson lesson);
    void removeLesson(SwimmingLesson lesson);

    void submitFeedback(Learner learner,SwimmingLesson lesson, int rating) throws InvalidRatingNumberException, FeebackReviewException;

}
