package org.sakaiproject.yaft.api;


/**
 * This is a json friendly, sparse representation of a Gradebook assignment object.
 * 
 * @author fisha
 */
public class YaftGBAssignment {
	
	public Long id;
	public Long getId() {
		return id;
	}
	public String name;
	public String getName() {
		return name;
	}
	
	public YaftGBAssignment(Long id,String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
