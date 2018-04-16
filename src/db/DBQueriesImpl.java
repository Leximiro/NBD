package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import entity.*;

public class DBQueriesImpl implements DBQueries {
	
	public DBQueriesImpl() {
	}
	
	@Override
	public int getNumberOfClasses() {
		int amount = 0;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT COUNT(id) FROM schedule");
			res = pst.executeQuery();
			if (res.next()) {
				amount = res.getInt(1);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}

	@Override
	public void cleanDB() {
		try {
			Connection conn = DBConnector.getConnection();

			PreparedStatement pst = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
			pst.executeQuery();

			pst = conn.prepareStatement("TRUNCATE TABLE finals");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE schedule_week");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE schedule");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE discipline");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE weeks");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE teacher");
			pst.executeQuery();
			pst = conn.prepareStatement("TRUNCATE TABLE specialty");
			pst.executeQuery();

			pst = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Day getDayByName(String name) {
		Day day = new Day();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM day_name WHERE name = ?");
			pst.setString(1, name);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				day.setId(res.getInt(1));
				day.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return day;
		else
			return null;
	}
	
	@Override
	public Specialization getSpecializationByName(String name) {
		Specialization specialization = new Specialization();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM specialty WHERE name = ?");
			pst.setString(1, name);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				specialization.setId(res.getInt(1));
				specialization.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return specialization;
		else
			return null;
	}

	@Override
	public void addSpecialization(Specialization specialization) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO specialty(name) VALUES(?)");
			pst.setString(1, specialization.getName());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Discipline getDisciplineByName(String name) {
		Discipline discipline = new Discipline();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM discipline WHERE name = ?");
			pst.setString(1, name);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				discipline.setId(res.getInt(1));
				discipline.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return discipline;
		else
			return null;
	}

	@Override
	public void addDiscipline(Discipline discipline) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO discipline(name) VALUES(?)");
			pst.setString(1, discipline.getName());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Lecturer getLecturerByName(String name) {
		Lecturer lecturer = new Lecturer();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM teacher WHERE name = ?");
			pst.setString(1, name);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				lecturer.setId(res.getInt(1));
				lecturer.setName(res.getString(2));
				lecturer.setDegree(res.getString(3));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return lecturer;
		else
			return null;
	}

	@Override
	public void addLecturer(Lecturer lecturer) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO teacher(name, degree) VALUES(?, ?)");
			pst.setString(1, lecturer.getName());
			pst.setString(1, lecturer.getDegree());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Week getWeekByNumber(int number) {
		Week week = new Week();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM weeks WHERE _number = ?");
			pst.setInt(1, number);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				week.setId(res.getInt(1));
				week.setNumber(res.getInt(2));
				week.setStartDate(res.getDate(3));
				week.setEndDate(res.getDate(4));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return week;
		else
			return null;
	}

	//TODO add dates.
	@Override
	public void addWeek(Week week) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO weeks(_number) VALUES(?)");
			pst.setInt(1, week.getNumber());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Classroom getClassroomByNumber(String number) {
		Classroom classroom = new Classroom();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM classroom WHERE _number = ?");
			pst.setString(1, number);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				classroom.setId(res.getInt(1));
				classroom.setCapacity(res.getInt(2));
				classroom.setProjector(res.getBoolean(3));
				classroom.setComputers(res.getBoolean(4));
				classroom.setBoard(res.getBoolean(5));
				classroom.setBuilding(res.getInt(6));
				classroom.setNumber(res.getString(7));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return classroom;
		else
			return null;
	}

	@Override
	public void addSchedule(Schedule schedule) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO "
					+ "schedule(course, specialty_id, day_name_id, class_period_id, classroom_id, discipline_id, class_type_id, teacher_id, group_number) "
					+ "VALUES(?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, schedule.getYear());
			pst.setInt(2, schedule.getSpecialization().getId());
			pst.setInt(3, schedule.getDay().getId());
			pst.setInt(4, schedule.getPeriod().getId());
			pst.setInt(5, schedule.getClassroom().getId());
			pst.setInt(6, schedule.getDiscipline().getId());
			pst.setInt(7, schedule.getClassType().getId());
			pst.setInt(8, schedule.getLecturer().getId());
			pst.setString(9, schedule.getGroup());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ScheduleWeek(ScheduleWeek scheduleWeek) {
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO schedule_week(schedule_id, weeks_id) VALUES(?, ?)");
			pst.setInt(1, scheduleWeek.getSchedule().getId());
			pst.setInt(2, scheduleWeek.getWeek().getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Schedule getScheduleById(int id) {
		Schedule schedule = new Schedule();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM schedule WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				schedule.setId(res.getInt(1));
				schedule.setYear(res.getInt(2));
					Specialization specialization = getSpecializationById(res.getInt(3));
				schedule.setSpecialization(specialization);
					Day day = getDayById(res.getInt(4));
				schedule.setDay(day);
					Period period = new Period(res.getInt(5), res.getInt(5));
				schedule.setPeriod(period);
					Classroom classroom = getClassroomById(res.getInt(6));
				schedule.setClassroom(classroom);
					Discipline discipline = getDisciplineById(res.getInt(7));
				schedule.setDiscipline(discipline);
					ClassType classType = getClassTypeById(res.getInt(8));
				schedule.setClassType(classType);
					Lecturer lecturer = getLecturerById(res.getInt(9));
				schedule.setLecturer(lecturer);
				schedule.setGroup(res.getString(10));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return schedule;
		else
			return null;
	}
	
	private Lecturer getLecturerById(int id) {
		Lecturer lecturer = new Lecturer();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM teacher WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				lecturer.setId(res.getInt(1));
				lecturer.setName(res.getString(2));
				lecturer.setDegree(res.getString(3));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return lecturer;
		else
			return null;
	}

	private ClassType getClassTypeById(int id) {
		ClassType classType = new ClassType();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM class_type WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				classType.setId(res.getInt(1));
				classType.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return classType;
		else
			return null;
	}

	private Discipline getDisciplineById(int id) {
		Discipline discipline = new Discipline();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM discipline WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				discipline.setId(res.getInt(1));
				discipline.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return discipline;
		else
			return null;
	}

	private Classroom getClassroomById(int id) {
		Classroom classroom = new Classroom();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM classroom WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				classroom.setId(res.getInt(1));
				classroom.setCapacity(res.getInt(2));
				classroom.setProjector(res.getBoolean(3));
				classroom.setComputers(res.getBoolean(4));
				classroom.setBoard(res.getBoolean(5));
				classroom.setBuilding(res.getInt(6));
				classroom.setNumber(res.getString(7));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return classroom;
		else
			return null;
	}

	private Day getDayById(int id) {
		Day day = new Day();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM day_name WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				day.setId(res.getInt(1));
				day.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return day;
		else
			return null;
	}

	private Specialization getSpecializationById(int id) {
		Specialization specialization = new Specialization();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM specialty WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				specialization.setId(res.getInt(1));
				specialization.setName(res.getString(2));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return specialization;
		else
			return null;
	}

	private Week getWeekById(int id) {
		Week week = new Week();
		boolean exists = false;
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM weeks WHERE id = ?");
			pst.setInt(1, id);
			res = pst.executeQuery();
			if (res.next()) {
				exists = true;
				week.setId(res.getInt(1));
				week.setNumber(res.getInt(2));
				week.setStartDate(res.getDate(3));
				week.setEndDate(res.getDate(4));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(exists)
			return week;
		else
			return null;
	}

	@Override
	public ArrayList<ScheduleWeek> getAllScheduleWeeks() {
		ArrayList<ScheduleWeek> scheduleWeeks = new ArrayList<ScheduleWeek>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM schedule_week");
            res = pst.executeQuery();
            while (res.next()) {
            	ScheduleWeek scheduleWeek = new ScheduleWeek();
            	scheduleWeek.setId(res.getInt(1));
            	Schedule schedule = getScheduleById(res.getInt(2));
            	Week week = getWeekById(res.getInt(3));
            	scheduleWeek.setSchedule(schedule);
            	scheduleWeek.setWeek(week);
            	scheduleWeeks.add(scheduleWeek);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleWeeks;
	}

	@Override
	public ArrayList<Lecturer> getAllLecturers(){
		ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM teacher");
            res = pst.executeQuery();
            while (res.next()) {
            	Lecturer lecturer = new Lecturer();
            	lecturer.setId(res.getInt(1));
				lecturer.setName(res.getString(2));
				lecturer.setDegree(res.getString(3));
            	lecturers.add(lecturer);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
	}

	@Override
	public ArrayList<Week> getAllWeeks(){
		ArrayList<Week> weeks = new ArrayList<Week>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM weeks");
            res = pst.executeQuery();
            while (res.next()) {
            	Week week = new Week();
            	week.setId(res.getInt(1));
				week.setNumber(res.getInt(2));
				week.setStartDate(res.getDate(3));
				week.setEndDate(res.getDate(4));
            	weeks.add(week);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeks;
	}

	@Override
	public ArrayList<Specialization> getAllSpecializations(){
		ArrayList<Specialization> specializations = new ArrayList<Specialization>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM specialty");
            res = pst.executeQuery();
            while (res.next()) {
            	Specialization specialization = new Specialization();
				specialization.setId(res.getInt(1));
				specialization.setName(res.getString(2));
				specializations.add(specialization);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specializations;
	}
	
	@Override
	public ArrayList<Integer> getAllCourses(){
		ArrayList<Integer> courses = new ArrayList<Integer>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT DISTINCT course FROM schedule");
            res = pst.executeQuery();
            while (res.next()) {
            	courses.add(res.getInt(1));
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
	}
	
	@Override
	public ArrayList<Discipline> getAllDisciplines(){
		ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM discipline");
            res = pst.executeQuery();
            while (res.next()) {
            	Discipline discipline = new Discipline();
				discipline.setId(res.getInt(1));
				discipline.setName(res.getString(2));
            	disciplines.add(discipline);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
	}
	
	@Override
	public ArrayList<Schedule> getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(
			Lecturer lecturer, Week week, Specialization specialization, Integer course, Discipline discipline){
		
		StringBuilder query = new StringBuilder(""
        		+ "SELECT schedule.id, course, specialty_id, day_name_id, class_period_id, classroom_id, discipline_id, class_type_id, teacher_id, group_number, weeks._number "
        		+ "FROM schedule INNER JOIN schedule_week ON schedule.id = schedule_week.schedule_id "
        					  + "INNER JOIN weeks ON schedule_week.weeks_id = weeks.id "
        	    + "WHERE ");
		
		query.append((lecturer==null) 		? 	"teacher_id = teacher_id AND " 		: 		"teacher_id = " 	+ lecturer.getId() + " AND ");
		query.append((week==null) 			? 	"weeks_id = weeks_id AND " 			: 		"weeks_id = " 		+ week.getId()	   + " AND ");
		query.append((specialization==null) ? 	"specialty_id = specialty_id AND " 	: 		"specialty_id = " 	+ specialization.getId() + " AND ");
		query.append((course==null) 		? 	"course = course AND " 				: 		"course = " 		+ course + " AND ");
		query.append((discipline==null) 	? 	"discipline_id = discipline_id" 	: 		"discipline_id = " 	+ discipline.getId() );
		
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(query.toString());
            res = pst.executeQuery();
            while (res.next()) {
            	Schedule schedule = new Schedule();
            	schedule.setId(res.getInt(1));
				schedule.setYear(res.getInt(2));
					Specialization special = getSpecializationById(res.getInt(3));
				schedule.setSpecialization(special);
					Day day = getDayById(res.getInt(4));
				schedule.setDay(day);
					Period period = new Period(res.getInt(5), res.getInt(5));
				schedule.setPeriod(period);
					Classroom classroom = getClassroomById(res.getInt(6));
				schedule.setClassroom(classroom);
					Discipline discipl = getDisciplineById(res.getInt(7));
				schedule.setDiscipline(discipl);
					ClassType classType = getClassTypeById(res.getInt(8));
				schedule.setClassType(classType);
					Lecturer lectur = getLecturerById(res.getInt(9));
				schedule.setLecturer(lectur);
				schedule.setGroup(res.getString(10));
            	schedule.setWeekNumber(res.getInt(11));
            	schedules.add(schedule);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
	}

	@Override
	public ArrayList<Schedule> getScheduleByBuildingAndClassroomTypeAndClassroomNumber(
			Integer building, Boolean board, Boolean comps, Boolean projector, String number){
				
		StringBuilder query = new StringBuilder(""
        		+ "SELECT schedule.id, course, specialty_id, day_name_id, class_period_id, classroom_id, discipline_id, class_type_id, teacher_id, group_number, weeks._number "
        		+ "FROM schedule INNER JOIN schedule_week ON schedule.id = schedule_week.schedule_id "
        					  + "INNER JOIN weeks ON schedule_week.weeks_id = weeks.id "
        					  + "INNER JOIN classroom ON classroom_id = classroom.id "
        	    + "WHERE ");
		
		query.append((building==null) 		? 	"building = building AND " 					: 		"building = " 			+ building + " AND ");
		query.append((board==null) 			? 	"board = board AND " 						: 		"board = " 				+ (board ? 1 : 0) + " AND ");
		query.append((comps==null) 			? 	"computers = computers AND " 				: 		"computers = " 			+ (comps ? 1 : 0) + " AND ");
		query.append((projector==null) 		? 	"projector = projector AND " 				: 		"projector = " 			+ (projector ? 1 : 0) + " AND ");
		query.append((number==null) 		? 	"classroom._number = classroom._number" 	: 		"classroom._number = " 	+ number );
				
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(query.toString());
            res = pst.executeQuery();
            while (res.next()) {
            	Schedule schedule = new Schedule();
            	schedule.setId(res.getInt(1));
				schedule.setYear(res.getInt(2));
					Specialization special = getSpecializationById(res.getInt(3));
				schedule.setSpecialization(special);
					Day day = getDayById(res.getInt(4));
				schedule.setDay(day);
					Period period = new Period(res.getInt(5), res.getInt(5));
				schedule.setPeriod(period);
					Classroom classroom = getClassroomById(res.getInt(6));
				schedule.setClassroom(classroom);
					Discipline discipl = getDisciplineById(res.getInt(7));
				schedule.setDiscipline(discipl);
					ClassType classType = getClassTypeById(res.getInt(8));
				schedule.setClassType(classType);
					Lecturer lectur = getLecturerById(res.getInt(9));
				schedule.setLecturer(lectur);
				schedule.setGroup(res.getString(10));
            	schedule.setWeekNumber(res.getInt(11));
            	schedules.add(schedule);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
	}

	@Override
	public ArrayList<Integer> getAllBuildings(){
		ArrayList<Integer> buildings = new ArrayList<Integer>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT DISTINCT building FROM classroom");
            res = pst.executeQuery();
            while (res.next()) {
            	buildings.add(res.getInt(1));
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildings;
	}

	@Override
	public ArrayList<Classroom> getAllClassrooms(){
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM classroom");
            res = pst.executeQuery();
            while (res.next()) {
            	Classroom classroom = new Classroom();
				classroom.setId(res.getInt(1));
				classroom.setCapacity(res.getInt(2));
				classroom.setProjector(res.getBoolean(3));
				classroom.setComputers(res.getBoolean(4));
				classroom.setBoard(res.getBoolean(5));
				classroom.setBuilding(res.getInt(6));
				classroom.setNumber(res.getString(7));
				classrooms.add(classroom);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
	}
	
	@Override
	public ArrayList<ClassType> getAllClassTypes(){
		ArrayList<ClassType> classTypes = new ArrayList<ClassType>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM class_type");
            res = pst.executeQuery();
            while (res.next()) {
            	ClassType classType = new ClassType();
            	classType.setId(res.getInt(1));
            	classType.setName(res.getString(2));
				classTypes.add(classType);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classTypes;
	}

	@Override
	public ArrayList<Schedule> getScheduleErrors(){
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		ResultSet res = null;
        try {
        	Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(""+
            		"SELECT schedule.*, weeks._number " + 
            		"FROM schedule INNER JOIN schedule_week ON schedule.id = schedule_week.schedule_id "
					  + "INNER JOIN weeks ON schedule_week.weeks_id = weeks.id "
					  + "INNER JOIN classroom ON classroom_id = classroom.id " + 
            		"GROUP BY day_name_id, class_period_id, teacher_id " +
            		"HAVING count(*) > 1 "
            		+ "UNION ALL " +
            		"SELECT schedule.*, weeks._number " + 
            		"FROM schedule INNER JOIN schedule_week ON schedule.id = schedule_week.schedule_id "
					  + "INNER JOIN weeks ON schedule_week.weeks_id = weeks.id "
					  + "INNER JOIN classroom ON classroom_id = classroom.id " + 
            		"GROUP BY day_name_id, class_period_id, classroom_id " +
            		"HAVING count(*) > 1");
            res = pst.executeQuery();
            while (res.next()) {
            	Schedule schedule = new Schedule();
            	schedule.setId(res.getInt(1));
				schedule.setYear(res.getInt(2));
					Specialization special = getSpecializationById(res.getInt(3));
				schedule.setSpecialization(special);
					Day day = getDayById(res.getInt(4));
				schedule.setDay(day);
					Period period = new Period(res.getInt(5), res.getInt(5));
				schedule.setPeriod(period);
					Classroom classroom = getClassroomById(res.getInt(6));
				schedule.setClassroom(classroom);
					Discipline discipl = getDisciplineById(res.getInt(7));
				schedule.setDiscipline(discipl);
					ClassType classType = getClassTypeById(res.getInt(8));
				schedule.setClassType(classType);
					Lecturer lectur = getLecturerById(res.getInt(9));
				schedule.setLecturer(lectur);
				schedule.setGroup(res.getString(10));
            	schedule.setWeekNumber(res.getInt(11));
            	schedules.add(schedule);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
	}
	
	@Override
	public ArrayList<Day> getAllDays(){
		ArrayList<Day> days = new ArrayList<Day>();
		ResultSet res = null;
		try {
			Connection conn = DBConnector.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM day_name");
			res = pst.executeQuery();
			while (res.next()) {
				Day day = new Day();
				day.setId(res.getInt(1));
				day.setName(res.getString(2));
				days.add(day);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return days;
	}

}

class DBConnector{

	private static final String url = "jdbc:mysql://den1.mysql1.gear.host/nbd";
	private static final String user = "nbd";
	private static final String password = "Ls76xn_QaO!O";
	private static Connection con = null;
	private static Properties properties = new Properties();

	public static Connection getConnection() {
		if(con==null) {
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			properties.setProperty("useUnicode", "true");
			properties.setProperty("characterEncoding", "UTF-8");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url, properties);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
}
