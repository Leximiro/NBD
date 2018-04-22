package table;

import entity.Day;
import entity.Period;
import entity.Schedule;
import entity.Week;

import java.util.ArrayList;

public class LecturerTable {
    private Integer period;
    private String monday;
    private String tuesday ;
    private String wednesday ;
    private String thursday ;
    private String friday;
    private String saturday ;


    public  LecturerTable(Period period, ArrayList<Schedule> schedule){
        this.period = period.getNumber();
        for (Schedule sced: schedule) {
            int number = sced.getDay().getId();
            switch(number){
                case 1: this.monday = lessonToString(sced);break;
                case 2: this.tuesday = lessonToString(sced);break;
                case 3: this.wednesday = lessonToString(sced);break;
                case 4: this.thursday = lessonToString(sced);break;
                case 5: this.friday = lessonToString(sced);break;
                case 6: this.saturday = lessonToString(sced);break;

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

    public Integer getPeriod() {
        return period;
    }

    public String getMonday() {
        return monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getFriday() {
        return friday;
    }

    public String getSaturday() {
        return saturday;
    }
}
