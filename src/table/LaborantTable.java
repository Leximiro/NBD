package table;

import entity.Classroom;
import entity.Day;
import entity.Period;
import entity.Schedule;

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
        this.classroom = classroom.getNumber();
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
        String lesson = "Курс: "+schedule.getYear()+"\nПредмет: "+schedule.getDiscipline().getName()+"\n Викладач: "+schedule.getLecturer().getName()+"\nСпеціальність:\n"+schedule.getSpecialization().getName();

        return lesson;

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
