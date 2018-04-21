//package db;
//
//import java.util.HashSet;
//
//import entity.*;
//
//public class DBImitation {
//
//	public static HashSet<Classroom> classrooms = new HashSet<Classroom>();
//	public static HashSet<ClassType> classTypes = new HashSet<ClassType>();
//	public static HashSet<Day> days = new HashSet<Day>();
//	public static HashSet<Discipline> disciplines = new HashSet<Discipline>();
//	public static HashSet<Finals> finals = new HashSet<Finals>();
//	public static HashSet<Lecturer> lecturers = new HashSet<Lecturer>();
//	public static HashSet<Period> periods = new HashSet<Period>();
//	public static HashSet<Schedule> schedules = new HashSet<Schedule>();
//	public static HashSet<ScheduleWeek> scheduleWeeks = new HashSet<ScheduleWeek>();
//	public static HashSet<Specialization> specializations = new HashSet<Specialization>();
//	public static HashSet<Week> weeks = new HashSet<Week>();
//
//	public static void init() {
//		initClassrooms();
//		initClassTypes();
//		initDays();
//		initPeriods();
//		initWeeks();
//	}
//
//	public static void showSchedules() {
//		for (Schedule s : schedules) {
//			System.out.println(s);
//		}
//	}
//
//	public static Specialization getSpecializationByName(String name){
//		for (Specialization s : specializations) {
//			if (s.getName().equals(name)) {
//				return s;
//			}
//		}
//		return null;
//	}
//
//	public static Day getDayByName(String name){
//		for (Day d : days) {
//			if (d.getName().equals(name)) {
//				return d;
//			}
//		}
//		return null;
//	}
//
//	public static Period getPeriodByNumber(int number){
//		for (Period p : periods) {
//			if (p.getNumber() == number) {
//				return p;
//			}
//		}
//		return null;
//	}
//
//	public static Discipline getDisciplineByName(String name){
//		for (Discipline d : disciplines) {
//			if (d.getName().equals(name)) {
//				return d;
//			}
//		}
//		return null;
//	}
//
//	public static Lecturer getLecturerByName(String name){
//		for (Lecturer l : lecturers) {
//			if (l.getName().equals(name)) {
//				return l;
//			}
//		}
//		return null;
//	}
//
//	public static ClassType getClassTypeByName(String name) {
//		for (ClassType ct : classTypes) {
//			if (ct.getName().equals(name)) {
//				return ct;
//			}
//		}
//		return null;
//	}
//
//	public static Week getWeekByNumber(int number) {
//		for (Week w : weeks) {
//			if (w.getNumber() == number) {
//				return w;
//			}
//		}
//		return null;
//	}
//
//	public static Classroom getClassroomByNumber(String number) {
//		for (Classroom c : classrooms) {
//			if (c.getNumber().equals(number)) {
//				return c;
//			}
//		}
//		return null;
//	}
//
//	private static void initClassrooms() {
//		classrooms.add(new Classroom(1, 1, "223", 60, false, true, true));
//		classrooms.add(new Classroom(2, 1, "225", 74, false, false, true));
//		classrooms.add(new Classroom(3, 1, "310", 40, true, true, true));
//		classrooms.add(new Classroom(4, 1, "313", 108, false, false, true));
//		classrooms.add(new Classroom(5, 1, "108", 15, true, true, true));
//		classrooms.add(new Classroom(6, 1, "112", 15, true, false, true));
//		classrooms.add(new Classroom(7, 1, "206", 15, true, false, true));
//		classrooms.add(new Classroom(8, 1, "208", 15, true, false, true));
//		classrooms.add(new Classroom(9, 1, "306", 15, true, false, true));
//		classrooms.add(new Classroom(10, 1, "307", 15, true, false, true));
//		classrooms.add(new Classroom(11, 1, "309", 15, true, true, true));
//		classrooms.add(new Classroom(12, 1, "331", 15, true, true, true));
//		classrooms.add(new Classroom(13, 3, "220a", 30, false, false, true));
//		classrooms.add(new Classroom(14, 3, "302", 52, false, false, true));
//		classrooms.add(new Classroom(15, 10, "1", 15, true, true, false));
//		classrooms.add(new Classroom(16, 10, "2", 15, true, true, false));
//		classrooms.add(new Classroom(17, 10, "3", 15, true, true, false));
//		classrooms.add(new Classroom(18, 10, "4", 15, true, true, false));
//	}
//
//	private static void initClassTypes() {
//		classTypes.add(new ClassType(1, "������"));
//		classTypes.add(new ClassType(2, "��������"));
//	}
//
//	private static void initDays() {
//		days.add(new Day(1, "��������"));
//		days.add(new Day(2, "³������"));
//		days.add(new Day(3, "������"));
//		days.add(new Day(4, "������"));
//		days.add(new Day(5, "�`������"));
//		days.add(new Day(6, "������"));
//	}
//
//	private static void initPeriods() {
//		periods.add(new Period(1, 1));
//		periods.add(new Period(2, 2));
//		periods.add(new Period(3, 3));
//		periods.add(new Period(4, 4));
//		periods.add(new Period(5, 5));
//		periods.add(new Period(6, 6));
//		periods.add(new Period(7, 7));
//	}
//
//	private static void initWeeks() {
//		weeks.add(new Week(1, 1));
//		weeks.add(new Week(2, 2));
//		weeks.add(new Week(3, 3));
//		weeks.add(new Week(4, 4));
//		weeks.add(new Week(5, 5));
//		weeks.add(new Week(6, 6));
//		weeks.add(new Week(7, 7));
//		weeks.add(new Week(8, 8));
//		weeks.add(new Week(9, 9));
//		weeks.add(new Week(10, 10));
//		weeks.add(new Week(11, 11));
//		weeks.add(new Week(12, 12));
//		weeks.add(new Week(13, 13));
//		weeks.add(new Week(14, 14));
//		weeks.add(new Week(15, 15));
//	}
//
//}
