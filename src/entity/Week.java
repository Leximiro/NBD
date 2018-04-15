package entity;

import java.util.Date;

public class Week {
	
	private int id;
	private int number;
	private Date startDate;
	private Date endDate;
	
	public Week() {}
	
	public Week(int id, int number) {
		this.id = id;
		this.number = number;
	}

	public Week(int id, int number, Date startDate, Date endDate) {
		this.id = id;
		this.number = number;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		Week other = (Week) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Week [id=" + id + ", number=" + number + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
