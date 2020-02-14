
/**
 * AUTHOR: Justin Nichols
 * FILE: PA1Main.java
 * ASSIGNMENT: Programming Assignment 1 - Gradenator
 * COURSE: CSC210; Section D; Spring 2019
 * PURPOSE: Accepts as input the name of a file s.t. each line of the file 
 *              contains information about a type of task that a student 
 *              completed, what grade they received for each instance of that 
 *              task, and how heavily that type of task was weighted into 
 *              their overall grade. Prints out the results
 * 
 * USAGE: 
 * java PA1Main infile
 * 
 * where infile is the name of an input file of the following format:
 * -- EXAMPLE INPUT (CREATED BY INSTRUCTORS, NOT BY ME)--
 *  Input File:                       
 *  ------------------------------------
 *  | 80; final; 20%                   |
 *  | 90; programming assignments; 30% |
 *  | 70; midterm 2; 10%               |
 *  | 90; sections; 10%                |
 *  | 90 60 50; quizzes; 10%           |
 *  | 60; midterm 1; 10%               |
 *  | 80; peer reviews and drills; 10% |
 *  |                                  |
 *  ------------------------------------
 *
 *
 * The format of the input file must match the format shown above.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class PA1Main {
    public static void main(String[] args) {
        
        // getting file name, attempting to open
        String gradesFileName = getGradesFileName();
        Scanner gradesFile = null;

        try {
            gradesFile = new Scanner(new File(gradesFileName));
        } catch (FileNotFoundException error) {
            error.printStackTrace();
            System.exit(1);
        }
        
        // initializing some overall stats
        double overallGradePossible = 0;
        double overallGradeAchieved = 0;

        // computing stats for each type of task
        // these will determine the overall stats in the end
        String gradeInfoLine;
        try {
            while ((gradeInfoLine = gradesFile.nextLine()) != null) {
                Double[] taskStatsArray = computeTaskStats(gradeInfoLine);
                overallGradePossible += taskStatsArray[0];
                overallGradeAchieved += taskStatsArray[1];
            }
        } catch (Exception error) {

        }

        // closing input file. Printing overall stats
        gradesFile.close();
        System.out.format("TOTAL = %.1f%% out of %.1f%%\n",
                overallGradeAchieved,
                overallGradePossible);


    }

    /*
     * @Returns the name of the input file
     */
    public static String getGradesFileName() {
        // Question for grader: where is the best place to close the Scanner
        // 'userInput' below?
        Scanner userInput = new Scanner(System.in);
        System.out.println("File name?");

        return userInput.nextLine().trim();
    }


    /*
     * Computes how many points a task contributed to a student's overall
     * grade, relative to how much it could have
     * 
     * @param gradeInfoLine, a String containing info about a task a student
     * completed
     * 
     * @return taskStatsArray, an array containing how many points the task
     * vs how many it could have
     */
    public static Double[] computeTaskStats(String gradeInfoLine) {
        // getting relevant figures from info line
        String[] infoList = gradeInfoLine.split(";");
        String taskScores = infoList[0].trim();
        String taskName = infoList[1].trim();
        String taskWeightStrForm = infoList[2].trim();
        double taskWeightDubForm = Double
                .parseDouble(taskWeightStrForm.substring(0,
                        taskWeightStrForm.length() - 1));
        String[] taskScoresArray = taskScores.split(" ");

        // running calculations
        double taskTotalScore = 0;
        for (String taskScore : taskScoresArray) {
            taskTotalScore += Double.parseDouble(taskScore);
        }
        double taskAvg = taskTotalScore / taskScoresArray.length;
        double taskContribution = taskAvg * taskWeightDubForm / 100;

        // printing + returning results for this task
        System.out.format("%s; %.1f%%; avg=%.1f\n", taskName,
                taskWeightDubForm, taskAvg);
        Double[] taskStatsArray = { taskWeightDubForm, taskContribution };

        return taskStatsArray;
    }
}

