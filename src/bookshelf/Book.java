package bookshelf;

import java.util.Date;

public class Book {
	private String name = "";
	private String description = "";
	private String type = "";
	private String location = "";
	private Date lastOpenTime;

	public Book(String name, String type, String location, String description) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTime(long time) {
		this.lastOpenTime = new Date(time);
	}

	public Date getTime() {
		return this.lastOpenTime;
	}

}
