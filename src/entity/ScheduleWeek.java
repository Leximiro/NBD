package entity;

public class ScheduleWeek {
	
	private int id;
	private Schedule schedule;
	private Week week;
	
	public ScheduleWeek() {}
	
	public ScheduleWeek(int id, Schedule schedule, Week week) {
		this.id = id;
		this.schedule = schedule;
		this.week = week;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
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
		ScheduleWeek other = (ScheduleWeek) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScheduleWeek [id=" + id + ", schedule=" + schedule + ", week=" + week + "]";
	}

}
