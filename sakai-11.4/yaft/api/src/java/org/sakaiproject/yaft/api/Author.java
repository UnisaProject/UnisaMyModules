package org.sakaiproject.yaft.api;

import org.sakaiproject.service.gradebook.shared.GradeDefinition;

public class Author
{
	private int numberOfPosts = 0;
	
	private String id;
	
	private String displayName;
	
	private GradeDefinition grade = null;
	
	public Author(String id,String displayName,int numberOfPosts) {
		this.id = id;
		this.displayName = displayName;
		this.numberOfPosts = numberOfPosts;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getNumberOfPosts() {
		return numberOfPosts;
	}

	public void setGrade(GradeDefinition grade) {
		this.grade = grade;
	}

	public GradeDefinition getGrade() {
		return grade;
	}
	
	public boolean isGraded() {
		return grade != null;
	}
}