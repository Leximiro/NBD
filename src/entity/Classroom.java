package entity;

public class Classroom {
	
	private int id;
	private int building;
	private String number;
	private int capacity;
	private boolean computers;
	private boolean projector;
	private boolean board;
	
	public Classroom(int id, int building, String number, int capacity, boolean computers, boolean projector,
			boolean board) {
		this.id = id;
		this.building = building;
		this.number = number;
		this.capacity = capacity;
		this.computers = computers;
		this.projector = projector;
		this.board = board;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBuilding() {
		return building;
	}

	public void setBuilding(int building) {
		this.building = building;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isComputers() {
		return computers;
	}

	public void setComputers(boolean computers) {
		this.computers = computers;
	}

	public boolean isProjector() {
		return projector;
	}

	public void setProjector(boolean projector) {
		this.projector = projector;
	}

	public boolean isBoard() {
		return board;
	}

	public void setBoard(boolean board) {
		this.board = board;
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
		Classroom other = (Classroom) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Classroom [id=" + id + ", building=" + building + ", number=" + number + ", capacity=" + capacity
				+ ", computers=" + computers + ", projector=" + projector + ", board=" + board + "]";
	}
	
}
