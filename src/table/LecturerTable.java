package table;

import entity.Day;
import entity.Schedule;
import entity.Week;

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
                case 1: this.lesson1 = lessonToString(sced);break;
                case 2: this.lesson2 = lessonToString(sced);break;
                case 3: this.lesson3 = lessonToString(sced);break;
                case 4: this.lesson4 = lessonToString(sced);break;
                case 5: this.lesson5 = lessonToString(sced);break;
                case 6: this.lesson6 = lessonToString(sced);break;
                case 7: this.lesson7 = lessonToString(sced);break;

            }

        }


    }

    private String lessonToString(Schedule schedule){
        String lesson = "Тижні: "+extractWeeks(schedule.getWeeks())+"\nПредмет: "+schedule.getDiscipline().getName()+"\nГрупа: "+schedule.getGroup()+"\nАудиторія: "+schedule.getClassroom().getNumber()+"\nСпеціальність:\n"+schedule.getSpecialization().getName();
        return lesson;
    }

    private String extractWeeks(ArrayList<Week> weeks) {
        StringBuilder res = new StringBuilder("");
        for(Week w : weeks)
            res.append(w.getNumber()+" ");
        return res.toString();
    }

    public String getDay() {
        return day;
    }

    public String getLesson1() {
        return lesson1;
    }

    public String getLesson2() {
        return lesson2;
    }

    public String getLesson3() {
        return lesson3;
    }

    public String getLesson4() {
        return lesson4;
    }

    public String getLesson5() {
        return lesson5;
    }

    public String getLesson6() {
        return lesson6;
    }

    public String getLesson7() {
        return lesson7;
    }

    @Override
    public String toString() {
        return "Lecturer [day=" + day + ", lesson1=" + lesson1 + ", lesson2=" + lesson2 +", lesson3=" + lesson3 +", lesson4=" + lesson4 +", lesson5=" + lesson5 +", lesson6=" + lesson6 + ", lesson7=" + lesson7 +"]";
    }



}
