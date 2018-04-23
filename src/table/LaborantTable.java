package table;

import entity.*;

import java.util.ArrayList;

public class LaborantTable {
    private String classroom;
    private Integer period;
    private String monday;
    private String tuesday ;
    private String wednesday ;
    private String thursday ;
    private String friday;
    private String saturday ;



    public LaborantTable() {
    }

    public LaborantTable(Classroom classroom, Period period , ArrayList<Schedule> schedules) {
        this.period = period.getNumber();
        this.classroom = classroom.getBuilding()+"-"+classroom.getNumber();
        for (Schedule sced: schedules) {
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

    private String lessonToString(Schedule schedule) {
        String lesson = "\u0422\u0438\u0436\u043d\u0456: "+extractWeeks(schedule.getWeeks())+"\n\u041a\u0443\u0440\u0441: "+schedule.getYear()+"\n\u041f\u0440\u0435\u0434\u043c\u0435\u0442: "+schedule.getDiscipline().getName()+"\n \u0412\u0438\u043a\u043b\u0430\u0434\u0430\u0447: "+schedule.getLecturer().getName()+"\n\u0421\u043f\u0435\u0446\u0456\u0430\u043b\u044c\u043d\u0456\u0441\u0442\u044c:\n"+schedule.getSpecialization().getName();

        return lesson;

    }
    private String extractWeeks(ArrayList<Week> weeks) {
        StringBuilder res = new StringBuilder("");
        for(Week w : weeks)
            res.append(w.getNumber()+" ");
        return res.toString();
    }

    public String getClassroom() {

        return classroom;
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
