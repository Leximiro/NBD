package table;

import entity.Day;
import entity.Period;
import entity.Schedule;
import entity.Week;
import parser.Utils;

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
        String lesson = "\u0422\u0438\u0436\u043d\u0456: "+ Utils.getWeeks(schedule.getWeeks())+"\n\u041f\u0440\u0435\u0434\u043c\u0435\u0442: "+schedule.getDiscipline().getName()+"\n\u0413\u0440\u0443\u043f\u0430: "+schedule.getGroup()+"\n\u0410\u0443\u0434\u0438\u0442\u043e\u0440\u0456\u044f: "+schedule.getClassroom().getBuilding()+"-"+schedule.getClassroom().getNumber()+"\n\u0421\u043f\u0435\u0446\u0456\u0430\u043b\u044c\u043d\u0456\u0441\u0442\u044c:\n"+schedule.getSpecialization().getName();
        return lesson;
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
