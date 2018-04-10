package za.ac.unisa.lms.tools.booklist.dao;

public class College{

 private String college;
 private String collegecode;
 private  int active;
 private  int inactive;
 private  int inactivewithstudents;
 
 private int code;
 private String totals;

public int getCode() {
	return code;
}

public void setCode(int code) {
	this.code = code;
}

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

public String getTotal() {
	return totals;
}

public void setTotal(String total) {
	this.totals = totals;
}



}
