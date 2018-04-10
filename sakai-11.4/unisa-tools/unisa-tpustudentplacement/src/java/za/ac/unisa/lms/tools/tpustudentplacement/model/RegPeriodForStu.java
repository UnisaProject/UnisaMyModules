package za.ac.unisa.lms.tools.tpustudentplacement.model;

public class RegPeriodForStu {
	           protected short semester;
	           protected short acadYear;
	           protected int stuNum;
	           
	           public short getSemester(){
	        	   return semester;
	           }
	           public void setSemester(short semester){
	        	   this.semester=semester;
	           }
	           public int getStuNum(){
	        	   return stuNum;
	           }
	           public void setStuNum(int stuNum){
	        	   this.stuNum=stuNum;
	           }
	           public short getAcadYear(){
	        	   return acadYear;
	           }
	           public void setAcadYear(short acadYear){
	        	         this.acadYear=acadYear;
	           }
}
