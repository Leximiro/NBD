package entity;

public class Schedule {
	
	private int id;
	private int year;
	private String group;
	
	private Specialization specialization;
	private Discipline discipline;
	private Lecturer lecturer;
	private Day day;
	private Period period;
	private Classroom classroom;
	private ClassType classType;
	
	private int weekNumber;
	
	public Schedule() {}
	
	public Schedule(int id, int year, String group) {
		this.id = id;
		this.year = year;
		this.group = group;
	}

	public Schedule(int id, int year, String group, Specialization specialization, Discipline discipline,
			Lecturer lecturer, Day day, Period period, Classroom classroom, ClassType classType) {
		super();
		this.id = id;
		this.year = year;
		this.group = group;
		this.specialization = specialization;
		this.discipline = discipline;
		this.lecturer = lecturer;
		this.day = day;
		this.period = period;
		this.classroom = classroom;
		this.classType = classType;
	}

	public Schedule(int year, String group, Specialization specialization, Discipline discipline,
			Lecturer lecturer, Day day, Period period, Classroom classroom, ClassType classType) {
		this.year = year;
		this.group = group;
		this.specialization = specialization;
		this.discipline = discipline;
		this.lecturer = lecturer;
		this.day = day;
		this.period = period;
		this.classroom = classroom;
		this.classType = classType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", year=" + year + ", group=" + group + ", specialization=" + specialization
				+ ", discipline=" + discipline + ", lecturer=" + lecturer + ", day=" + day + ", period=" + period
				+ ", classroom=" + classroom + ", classType=" + classType + "]";
	}

}
