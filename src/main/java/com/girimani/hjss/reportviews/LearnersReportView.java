package com.girimani.hjss.reportviews;

import com.girimani.hjss.entities.Learner;
import com.girimani.hjss.service.SwimmingSchoolManagement;

import java.util.List;

public class LearnersReportView {
    
    public static void viewReport(SwimmingSchoolManagement swimmingSchoolManagement) {
        List<Learner> learners = swimmingSchoolManagement.getLearners();
        System.out.println("----------------------------------------------");
        System.out.println("Learners Report View");
        System.out.println("----------------------------------------------");
        for (Learner learner : learners) {
            System.out.println(learner);
        }
        System.out.println("----------------------------------------------");
    }
}
