package za.ac.unisa.lms.tools.liberesource.forms;

public class Tab {
	
	private int placementID;
	private String name;
	private String link;
	private String selected = "false";
	private int number;
	
	public Tab(int placementID, String name, String link, int number)
	{
		this.placementID = placementID;
		this.name = name;
		this.link = link;
		this.number = number;
	}
	
	public int getPlacementID()
	{
		return placementID;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getSelected()
	{
		return selected;
	}
	
	public void setPlacementID(int placementID)
	{
		this.placementID = placementID;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public void setSelected(String selected)
	{
		this.selected = selected;
	}
	

}
