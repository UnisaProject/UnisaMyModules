package za.ac.unisa.lms.tools.nosite.dao;
//package za.ac.unisa.lms.tools.nosite.forms;

public class College{

 private String college;
 private String collegecode;
 private  int active;
 private  int inactive;
 private  int inactivewithstudents;

public College(){}

  public College(String college)
  {this.college=college;}


public String getCollege()
      {return college;}

public void incrActive()
  {active ++;}

public void incrInactive()
{inactive ++;}

public void setCollege(String collegename)
{college=collegename;}

public  int getActive() {
	return active;
}

public  void setActive(int active) {
	this.active = active;
}

public int getInactive() {
	return inactive;
}

public  void setInactive(int inactive) {
	this.inactive = inactive;
}

public int getInactivewithstudents() {
	return inactivewithstudents;
}

public void setInactivewithstudents(int inactivewithstudents) {
	this.inactivewithstudents = inactivewithstudents;
}

public String getCollegecode() {
	return collegecode;
}

public void setCollegecode(String collegecode) {
	this.collegecode = collegecode;
}



}
