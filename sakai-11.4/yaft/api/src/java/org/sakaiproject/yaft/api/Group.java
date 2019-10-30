package org.sakaiproject.yaft.api;

public class Group
{
	private String id = "";
	private String title = "";
	
	public Group(String id, String title)
	{
		this.id = id;
		this.title = title;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return id;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
}
