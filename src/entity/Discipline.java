package entity;

public class Discipline {
	
	private int id;
	private String name;
	
	public Discipline() {}
	
	public Discipline(int id) {
		super();
		this.id = id;
	}

	public Discipline(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Discipline(String name) {
		this.name = name;
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
		Discipline other = (Discipline) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Discipline [id=" + id + ", name=" + name + "]";
	}

}
