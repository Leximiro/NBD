package table;

import entity.Day;
import entity.Period;
import entity.Schedule;
import entity.Week;
import parser.Utils;

import java.util.ArrayList;

public class StudentTable {

    private Integer period;
    private String monday;
    private String tuesday ;
    private String wednesday ;
    private String thursday ;
    private String friday;
    private String saturday ;


    public  StudentTable(Period period, ArrayList<Schedule> schedule){
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
        String lesson = "\u0422\u0438\u0436\u043d\u0456: "+ Utils.getWeeks(schedule.getWeeks())+"\n\u0414\u0438\u0441\u0446\u0438\u043f\u043b\u0456\u043d\u0430: "+schedule.getDiscipline().getName()+"\n\u0413\u0440\u0443\u043f\u0430: "+schedule.getGroup()+"\n\u0410\u0443\u0434\u0438\u0442\u043e\u0440\u0456\u044f: "+schedule.getClassroom().getBuilding()+"-"+schedule.getClassroom().getNumber()+"\n\u0412\u0438\u043a\u043b\u0430\u0434\u0430\u0447:\n"+schedule.getLecturer().getName();
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
