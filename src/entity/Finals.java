package entity;

import java.util.Date;

public class Finals {
	
	private int id;
	private String type;
	private Date date;
	private int year;
	
	private Specialization specialization;
	private Discipline discipline;
	private Lecturer lecturer;
	private Classroom classroom;
	private Period period;
	
	public Finals() {}
	
	public Finals(int id, String type, Date date, int year) {
		this.id = id;
		this.type = type;
		this.date = date;
		this.year = year;
	}

	public Finals(int id, String type, Date date, int year, Specialization specialization, Discipline discipline,
			Lecturer lecturer, Classroom classroom, Period period) {
		this.id = id;
		this.type = type;
		this.date = date;
		this.year = year;
		this.specialization = specialization;
		this.discipline = discipline;
		this.lecturer = lecturer;
		this.classroom = classroom;
		this.period = period;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
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
		Finals other = (Finals) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Finals [id=" + id + ", type=" + type + ", date=" + date + ", year=" + year + ", specialization="
				+ specialization + ", discipline=" + discipline + ", lecturer=" + lecturer + ", classroom=" + classroom
				+ ", period=" + period + "]";
	}
	
}
