package table;

import entity.Schedule;

public class UploadTable {
    private int id;
    private int year;
    private String group;

    private String specialization;
    private String discipline;
    private String lecturer;
    private String day;
    private int period;
    private String classroom;
    private String classType;
    private int weekNumber;


    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getGroup() {
        return group;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getDay() {
        return day;
    }

    public int getPeriod() {
        return period;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getClassType() {
        return classType;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public UploadTable(Schedule schedule){
        this.id = schedule.getId();
        this.year = schedule.getYear();
        this.group = schedule.getGroup();
        this.specialization = schedule.getSpecialization().getName();
        this.discipline = schedule.getDiscipline().getName();
        this.lecturer = schedule.getLecturer().getName();
        this.day = schedule.getDay().getName();
        this.period = schedule.getPeriod().getNumber();
        this.classroom = schedule.getClassroom().getNumber();
        this.classType = schedule.getClassType().getName();
        this.weekNumber = schedule.getWeekNumber();

    }






}
