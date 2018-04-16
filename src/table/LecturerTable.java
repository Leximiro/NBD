package table;

import entity.Day;
import entity.Schedule;

import java.util.ArrayList;

public class LecturerTable {
    private String day;
    private String lesson1;
    private String lesson2;
    private String lesson3;
    private String lesson4;
    private String lesson5;
    private String lesson6;
    private String lesson7;


    public  LecturerTable(Day day, ArrayList<Schedule> schedule){
        this.day = day.getName();
        for (Schedule sced: schedule) {
            int number = sced.getPeriod().getNumber();
            switch(number){
                case 1: this.lesson1 = lessonToString(sced);
                case 2: this.lesson2 = lessonToString(sced);
                case 3: this.lesson3 = lessonToString(sced);
                case 4: this.lesson4 = lessonToString(sced);
                case 5: this.lesson5 = lessonToString(sced);
                case 6: this.lesson6 = lessonToString(sced);
                case 7: this.lesson7 = lessonToString(sced);

            }

        }


    }

    private String lessonToString(Schedule schedule){
        String lesson = ""+schedule.getWeekNumber()+" "+schedule.getDiscipline().getName()+" "+schedule.getGroup()+" "+schedule.getClassroom().getNumber()+" "+schedule.getSpecialization().getName();



        return lesson;
    }




}
