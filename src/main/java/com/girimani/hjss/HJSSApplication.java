package com.girimani.hjss;

import com.girimani.hjss.service.SwimmingSchoolManagement;
import com.girimani.hjss.entities.*;
import com.girimani.hjss.exceptions.CoachAlreadyExistsException;
import com.girimani.hjss.exceptions.LearnerAgeLimitException;
import com.girimani.hjss.exceptions.LearnerAlreadyExistsException;

import java.util.Scanner;

public class HJSSApplication {
    private static final SwimmingSchoolManagement swimmingSchoolManagement = new SwimmingSchoolManagement();
    
    private static final Scanner inputScanner = new Scanner(System.in);
    
    private static final String DASH_LINE_ROW = "------------------------------------------------";
    
    public static void main(String[] args) {
        while (true) {
            System.out.flush();
            System.out.println(DASH_LINE_ROW);
            System.out.println("HJSS Booking Management System");
            System.out.println(DASH_LINE_ROW);
            System.out.println("1. Add Learner");
            System.out.println("2. Add Coach");
            System.out.println("3. Add Lesson");
            System.out.println("4. Remove Learner");
            System.out.println("5. Remove Coach");
            System.out.println("6. Remove Lesson");
            System.out.println("7. Book Lesson");
            System.out.println("8. Cancel Lesson");
            System.out.println("9. Attend Lesson");
            System.out.println("10. Schedule View by Day Wise");
            System.out.println("11. Schedule View by Grade Wise");
            System.out.println("12. Schedule View by Coach Wise");
            System.out.println("13. Learners Report");
            System.out.println("14. Coaches Report");
            System.out.println("15. Feedback");
            System.out.println("16. Exit from Application");
            System.out.println(DASH_LINE_ROW);
            System.out.print("Enter your choice number: ");
            int userInput = inputScanner.nextInt();
            inputScanner.nextLine();
            onNavigateToUserInput(userInput);
        }
    }

    private static void onNavigateToUserInput(int userInput) {
        switch (userInput) {
            case 1:
                showHeader("Add Learner");
                addNewLearner();
                break;
            case 2:
                showHeader("Add Coach");
                addNewCoach();
                break;
            case 3:
                showHeader("Add Lesson");
                addLesson();
                break;
            case 4:
                showHeader("Remove Learner");
                removeLearner();
                break;

            case 5:
                showHeader("Remove Coach");
                removeCoach();
                break;

            case 6:
                showHeader("Remove Lesson");
                removeLesson();
                break;

            case 7:
                showHeader("Book Lesson");
                bookLesson();
                break;
            case 8:
                showHeader("Cancel Lesson");
                cancelLesson();
                break;
            case 9:
                showHeader("Attend Lesson");
                attendLesson();
                break;
            case 10:
                showHeader("Schedule View by Day Wise");
                scheduleViewByDayWise();
                break;
            case 11:
                showHeader("Schedule View by Grade Wise");
                scheduleViewByGradeWise();
                break;
            case 12:
                showHeader("Schedule View by Coach Wise");
                scheduleViewByCoachWise();
                break;
            case 13:
                swimmingSchoolManagement.learnersReportView();
                break;
            case 14:
                swimmingSchoolManagement.CoachesReportView();
                break;
            case 15:
                showHeader("Feedback");
                submitFeedback();
                break;
            case 16:
                System.out.println("Exit from Application...");
                return;
            default:
                System.out.println("Invalid option. Please choose the correct option.");
                inputScanner.nextLine();
        }
    }

    private static void showHeader(String headName) {
        System.out.flush();
        System.out.println(DASH_LINE_ROW);
        System.out.println(headName);
        System.out.println(DASH_LINE_ROW);
    }
    
    private static void addNewLearner() {
        System.out.print("Enter new learner name: ");
        String lName = inputScanner.nextLine();
        System.out.print("Enter learner gender: ");
        String lGender = inputScanner.nextLine();
        System.out.print("Enter learner age: ");
        int lAge = inputScanner.nextInt();
        inputScanner.nextLine();
        System.out.print("Enter learner emergency contact: ");
        String lEmergencyContact = inputScanner.nextLine();
        System.out.print("Enter learner grade: ");
        int lGrade = inputScanner.nextInt();
        inputScanner.nextLine();
        try {
            swimmingSchoolManagement.addLearner(
                    new Learner(
                            lName,
                            lGender,
                            lAge,
                            lEmergencyContact,
                            lGrade
                    )
            );
            System.out.println("Learner added successfully. Name: " + lName);
            inputScanner.nextLine();
        } catch (LearnerAlreadyExistsException | LearnerAgeLimitException e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
    }

    private static void removeLearner() {
        System.out.print("Enter learner name: ");
        Learner learnerDetails = swimmingSchoolManagement.getLearners().stream().filter(
                l -> l.getName().equals(inputScanner.nextLine())
        ).findFirst().orElse(null);
        if (learnerDetails == null) {
            System.out.println("Learner not found!");
            inputScanner.nextLine();
            return;
        }
        swimmingSchoolManagement.removeLearner(learnerDetails);
        System.out.println("Learner removed successfully. Name: " + learnerDetails.getName());
        inputScanner.nextLine();
    }

    private static void addNewCoach() {
        System.out.print("Enter new coach name: ");
        String cName = inputScanner.nextLine();
        try {
            swimmingSchoolManagement.addCoach(new Coach(cName));
            System.out.println("Coach added successfully!");
            inputScanner.nextLine();
        } catch (CoachAlreadyExistsException e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
    }

    private static void removeCoach() {
        System.out.print("Enter the coach name: ");
        Coach coachDetails = swimmingSchoolManagement.getCoaches().stream().filter(
                c -> c.getName().equals(inputScanner.nextLine())
        ).findFirst().orElse(null);
        if (coachDetails == null) {
            System.out.println("Coach not found!");
            inputScanner.nextLine();
            return;
        }
        swimmingSchoolManagement.removeCoach(coachDetails);
        System.out.println("Coach removed successfully! Name:"+ coachDetails.getName());
        inputScanner.nextLine();
    }

    private static void addLesson() {
        System.out.print("Days");
        System.out.println("1. "+ ClassDay.MONDAY);
        System.out.println("2. "+ ClassDay.WEDNESDAY);
        System.out.println("3. "+ ClassDay.FRIDAY);
        System.out.println("4. "+ ClassDay.SATURDAY);
        System.out.println("5. Exit");
        ClassDay selectedDay = null;
        System.out.print("Enter day: ");
        int userChoice = inputScanner.nextInt();
        inputScanner.nextLine();
        switch (userChoice) {
            case 1:
                selectedDay = ClassDay.MONDAY;
                break;
            case 2:
                selectedDay = ClassDay.WEDNESDAY;
                break;
            case 3:
                selectedDay = ClassDay.FRIDAY;
                break;
            case 4:
                selectedDay = ClassDay.SATURDAY;
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid user choice!");
                inputScanner.nextLine();
                return;
        }
        System.out.print("Timeslot");
        System.out.println("1. "+ TimeSlot.From2To3);
        System.out.println("2. "+ TimeSlot.From3To4);
        System.out.println("3. "+ TimeSlot.From4To5);
        System.out.println("4. "+ TimeSlot.From5To6);
        System.out.println("5. "+ TimeSlot.From6To7);
        System.out.println("6. Exit");
        TimeSlot selectedTime = null;
        System.out.print("Enter time: ");
        userChoice = inputScanner.nextInt();
        inputScanner.nextLine();
        switch (userChoice) {
            case 1:
                selectedTime = TimeSlot.From2To3;
                break;
            case 2:
                selectedTime = TimeSlot.From3To4;
                break;
            case 3:
                selectedTime = TimeSlot.From4To5;
                break;
            case 4:
                selectedTime = TimeSlot.From5To6;
                break;
            case 5:
                selectedTime = TimeSlot.From6To7;
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
                inputScanner.nextLine();
                return;
        }
        System.out.print("Enter grade: ");
        int gradeInput = inputScanner.nextInt();
        inputScanner.nextLine();
        System.out.print("Enter coach name: ");
        String cName = inputScanner.nextLine();
        Coach coachDetails = swimmingSchoolManagement.getCoaches().stream().filter(
                c -> c.getName().equals(cName)
        ).findFirst().orElse(null);
        if (coachDetails == null) {
            System.out.println("Coach not found!");
            inputScanner.nextLine();
            return;
        }
        inputScanner.nextLine();
        swimmingSchoolManagement.addLesson(
                new SwimmingLesson( (int) Math.random()*1000, selectedTime, coachDetails, selectedDay, gradeInput )
        );
        System.out.println("Lesson added successfully!");
        inputScanner.nextLine();
    }

    private static void removeLesson() {
        System.out.print("Enter Lesson ID: ");
        int lessonId = inputScanner.nextInt();
        inputScanner.nextLine();
        SwimmingLesson lessonDetails = swimmingSchoolManagement.getLessons().stream().filter(
                l -> l.getId() == lessonId
        ).findFirst().orElse(null);
        if (lessonDetails == null) {
            System.out.println("Lesson not found!");
            inputScanner.nextLine();
            return;
        }
        swimmingSchoolManagement.removeLesson(lessonDetails);
        System.out.println("Lesson removed successfully!");
        inputScanner.nextLine();
    }


    private static void bookLesson() {
        System.out.print("Enter learner name: ");
        String lName = inputScanner.nextLine();
        Learner learnerDetails = swimmingSchoolManagement.getLearners().stream().filter(
                l -> l.getName().equals(lName)
        ).findFirst().orElse(null);
        if (learnerDetails == null) {
            System.out.println("Learner not found!");
            inputScanner.nextLine();
            return;
        }
        System.out.print("Enter lesson id: ");
        int id = inputScanner.nextInt();
        inputScanner.nextLine();
        SwimmingLesson lesson = swimmingSchoolManagement.getLessons().stream().filter(
                l -> l.getId() == id
        ).findFirst().orElse(null);
        if (lesson == null) {
            System.out.println("Lesson not found!");
            inputScanner.nextLine();
            return;
        }
        try {
            swimmingSchoolManagement.bookLesson(learnerDetails, lesson );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
        System.out.println("Lesson booked successfully!");
        inputScanner.nextLine();
    }

    private static void scheduleViewByDayWise() {
        System.out.print("Days");
        System.out.println("1. "+ ClassDay.MONDAY);
        System.out.println("2. "+ ClassDay.WEDNESDAY);
        System.out.println("3. "+ ClassDay.FRIDAY);
        System.out.println("4. "+ ClassDay.SATURDAY);
        System.out.println("5. Exit");
        ClassDay selectedDay = null;
        System.out.print("Enter day: ");
        int choice = inputScanner.nextInt();
        inputScanner.nextLine();
        switch (choice) {
            case 1:
                selectedDay = ClassDay.MONDAY;
                break;
            case 2:
                selectedDay = ClassDay.WEDNESDAY;
                break;
            case 3:
                selectedDay = ClassDay.FRIDAY;
                break;
            case 4:
                selectedDay = ClassDay.SATURDAY;
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
                inputScanner.nextLine();
                return;
        }
        swimmingSchoolManagement.scheduleViewByDayWise(selectedDay);
        inputScanner.nextLine();
    }

    private static void scheduleViewByGradeWise() {
        System.out.print("Enter grade: ");
        int gradeValue = inputScanner.nextInt();
        inputScanner.nextLine();
        swimmingSchoolManagement.scheduleViewByGradeWise(gradeValue);
        inputScanner.nextLine();
    }

    private static void scheduleViewByCoachWise() {
        System.out.print("Enter coach name: ");
        String cName = inputScanner.nextLine();
        Coach coachDetails = swimmingSchoolManagement.getCoaches().stream().filter(
                c -> c.getName().equals(cName)
        ).findFirst().orElse(null);

        if (coachDetails == null) {
            System.out.println("Coach not found!");
            inputScanner.nextLine();
            return;
        }
        swimmingSchoolManagement.scheduleViewByGradeCoach(coachDetails);
        inputScanner.nextLine();
    }



    private static void submitFeedback() {
        System.out.print("Enter learner name: ");
        String learnerName = inputScanner.nextLine();
        Learner learner = swimmingSchoolManagement.getLearners().stream().filter(
                l -> l.getName().equals(learnerName)
        ).findFirst().orElse(null);
        if (learner == null) {
            System.out.println("Learner not found!");
            inputScanner.nextLine();
            return;
        }
        System.out.println("Enter Lesson ID: ");
        int id = inputScanner.nextInt();
        inputScanner.nextLine();
        SwimmingLesson lesson = swimmingSchoolManagement.getLessons().stream().filter(
                l -> l.getId() == id
        ).findFirst().orElse(null);
        if (lesson == null) {
            System.out.println("Lesson not found!");
            inputScanner.nextLine();
            return;
        }
        System.out.print("Enter review: 1-5");
        int review = inputScanner.nextInt();
        inputScanner.nextLine();
        try {
            swimmingSchoolManagement.submitFeedback(
                    learner,
                    lesson,
                    review
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
        System.out.println("FeebackReview written successfully!");
        inputScanner.nextLine();
    }

    private static void cancelLesson() {
        System.out.print("Enter learner name: ");
        String learnerName = inputScanner.nextLine();
        Learner learner = swimmingSchoolManagement.getLearners().stream().filter(
                l -> l.getName().equals(learnerName)
        ).findFirst().orElse(null);
        if (learner == null) {
            System.out.println("Learner not found!");
            inputScanner.nextLine();
            return;
        }
        System.out.print("Enter lesson id: ");
        int id = inputScanner.nextInt();
        inputScanner.nextLine();
        SwimmingLesson lesson = swimmingSchoolManagement.getLessons().stream().filter(
                l -> l.getId() == id
        ).findFirst().orElse(null);
        if (lesson == null) {
            System.out.println("Lesson not found!");
            inputScanner.nextLine();
            return;
        }
        try {
            swimmingSchoolManagement.cancelLesson(
                    learner,
                    lesson
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
        System.out.println("Lesson cancelled successfully!");
        inputScanner.nextLine();
    }

    private static void attendLesson() {
        System.out.print("Enter learner name: ");
        String learnerName = inputScanner.nextLine();
        Learner learner = swimmingSchoolManagement.getLearners().stream().filter(
                l -> l.getName().equals(learnerName)
        ).findFirst().orElse(null);
        if (learner == null) {
            System.out.println("Learner not found!");
            inputScanner.nextLine();
            return;
        }
        System.out.print("Enter lesson id: ");
        int id = inputScanner.nextInt();
        inputScanner.nextLine();
        SwimmingLesson lesson = swimmingSchoolManagement.getLessons().stream().filter(
                l -> l.getId() == id
        ).findFirst().orElse(null);
        if (lesson == null) {
            System.out.println("Lesson not found!");
            return;
        }
        try {
            swimmingSchoolManagement.attendLesson(
                    learner,
                    lesson
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputScanner.nextLine();
        }
        System.out.println("Lesson attended successfully!");
        inputScanner.nextLine();
    }
}
