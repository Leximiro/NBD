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
    private String period;
    private String classroom;
    private String classType;
    private int weekNumber;


    public UploadTable(Schedule schedule){
        this.id = schedule.getId();
        this.year = schedule.getYear();
        this.group = schedule.getGroup();
        this.specialization = schedule.getSpecialization().toString();
        this.discipline = schedule.getDiscipline().toString();
        this.lecturer = schedule.getLecturer().toString();
        this.day = schedule.getDay().toString();
        this.period = schedule.getPeriod().toString();
        this.classroom = schedule.getClassroom().toString();
        this.classType = schedule.getClassType().toString();
        this.weekNumber = schedule.getWeekNumber();

    }






}
