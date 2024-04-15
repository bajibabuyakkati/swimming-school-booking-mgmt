package com.girimani.hjss.entities;

public class FeebackReview {
    private Learner learner;
    private int rating;
    private SwimmingLesson lesson;
    public FeebackReview(Learner learner, int rating, SwimmingLesson lesson) {
        this.learner = learner;
        this.rating = rating;
        this.lesson = lesson;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public SwimmingLesson getLesson() {
        return lesson;
    }

    public void setLesson(SwimmingLesson lesson) {
        this.lesson = lesson;
    }
}
