package db;

import java.util.ArrayList;

import entity.ClassType;
import entity.Classroom;
import entity.Day;
import entity.Discipline;
import entity.Lecturer;
import entity.Schedule;
import entity.ScheduleWeek;
import entity.Specialization;
import entity.Week;

public interface DBQueries {

	int getNumberOfClasses();

	void cleanDB();

	Day getDayByName(String name);

	Specialization getSpecializationByName(String name);

	void addSpecialization(Specialization specialization);

	Discipline getDisciplineByName(String name);

	void addDiscipline(Discipline discipline);

	Lecturer getLecturerByName(String name);

	void addLecturer(Lecturer lecturer);

	Week getWeekByNumber(int number);

	//TODO add dates.
	void addWeek(Week week);

	Classroom getClassroomByNumber(String number);

	void addSchedule(Schedule schedule);

	void ScheduleWeek(ScheduleWeek scheduleWeek);

	ArrayList<ScheduleWeek> getAllScheduleWeeks();

	ArrayList<Lecturer> getAllLecturers();

	ArrayList<Week> getAllWeeks();

	ArrayList<Specialization> getAllSpecializations();

	ArrayList<Integer> getAllCourses();

	ArrayList<Discipline> getAllDisciplines();

	ArrayList<Schedule> getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(Lecturer lecturer, Week week,
			Specialization specialization, Integer course, Discipline discipline);

	ArrayList<Schedule> getScheduleByBuildingAndClassroomTypeAndClassroomNumber(Integer building, Boolean board,
			Boolean comps, Boolean projector, String number);

	ArrayList<Integer> getAllBuildings();

	ArrayList<Classroom> getAllClassrooms();

	ArrayList<ClassType> getAllClassTypes();

	ArrayList<Schedule> getScheduleErrors();

	ArrayList<Day> getAllDays();

	void addClassroom(Classroom classroom);
}