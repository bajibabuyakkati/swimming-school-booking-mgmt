package com.girimani.hjss.reportviews;

import com.girimani.hjss.entities.Coach;
import com.girimani.hjss.entities.FeebackReview;
import com.girimani.hjss.service.SwimmingSchoolManagement;
import com.girimani.hjss.utils.Helper;

import java.util.List;

public class CoachesReportView  {
    
    public static void viewReport(SwimmingSchoolManagement swimmingSchoolManagement) {
        List<FeebackReview> feebackReviews = swimmingSchoolManagement.getReviews();
        List<Coach> coachList = swimmingSchoolManagement.getCoaches();
        System.out.println("----------------------------------------------");
        System.out.println("Coaches Report View");
        System.out.println("----------------------------------------------");
        System.out.println("Number of coaches: " + coachList.size());
        System.out.println("Coaches: ");
        System.out.printf("%-20s%-20s%-20s\n", "Name", "Average Rating","Global Rating");
        for (Coach coach : coachList) {
            double rating = getOverallAvgRating(feebackReviews, coach);
            System.out.printf("%-20s%-20s%-20s\n", coach.getName(), rating, Helper.getRatingString((int) rating));
        }
        System.out.println("---------------------------------------------");
    }

    private static double getOverallAvgRating(List<FeebackReview> feebackReviews, Coach coach) {
        double sum = 0;
        int count = 0;
        for (FeebackReview feebackReview : feebackReviews) {
            if (feebackReview.getLesson().getCoach().equals(coach)) {
                sum += feebackReview.getRating();
                count++;
            }
        }
        return sum / count;
    }
}
