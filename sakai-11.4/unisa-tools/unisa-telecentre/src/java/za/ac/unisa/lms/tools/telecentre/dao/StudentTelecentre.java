package za.ac.unisa.lms.tools.telecentre.dao;

	public class StudentTelecentre{
		
			//Sifiso Changes:Changed line below:2016/07/05-Changed 'usedHours' variable type from int to double 
			//private int usedHours,studentNumber,teleId;
			private double usedHours;			//Sifiso Changes:Declare the 'usedHours' varaible as double
			private int studentNumber,teleId;	//Sifiso Changes:Declare the rest of the above variables as int
			private String timeUnit;			//Sifiso Changes:Declare variable for time units in minutes, hours or seconds
			private String startTime,endTime;
			//Sifiso Changes:Changed line below:2016/07/05-Changed the method parameter type for 'usedHours' from int to double 
			//public void setUsedHours(int usedHours){
			public void setUsedHours(double usedHours){		//Sifiso Changes
				this.usedHours=usedHours;
			}
			//Sifiso Changes:Changed line below:2016/07/05-Changed the method return type from int to double 
			//public int getUsedHours(){
			public double getUsedHours(){		//Sifiso Changes
				return usedHours;
			}
			public void setStudentNumber(int studentNumber){
				   this.studentNumber=studentNumber;
			}
			public int getStudentNumber(){
				   return studentNumber;
			}
			public void setTeleId(int teleId){
				 this.teleId=teleId;
			}
			public int getTeleId(){
				return teleId;
			}
			public void setStartTime(String startTime){
				 this.startTime=startTime;
			}
			public String getStartTime(){
				     return startTime;
			}
			public void setEndTime(String endTime){
				 this.endTime=endTime;
			}
			public String getEndTime(){
				     return endTime;
			}
			public void setTimeUnit(String timeUnit){	//Sifiso Changes:Added:2016/07/11-Setter method for timeUnit
				this.timeUnit=timeUnit;
			}
			public String getTimeUnit(){				//Sifiso Changes:Added:2016/07/11-Getter method for timeUnit
				return timeUnit;
			}
		
		
	}
