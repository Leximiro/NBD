package entity;

public class Lecturer {
	
	private int id;
	private String name;
	private String degree;
	
	public Lecturer() {}
	
	public Lecturer(int id, String name, String degree) {
		this.id = id;
		this.name = name;
		this.degree = degree;
	}

	public Lecturer(String name, String lecturerDegree) {
		this.name = name;
		this.degree = lecturerDegree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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
		Lecturer other = (Lecturer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lecturer [id=" + id + ", name=" + name + ", degree=" + degree + "]";
	}

}
