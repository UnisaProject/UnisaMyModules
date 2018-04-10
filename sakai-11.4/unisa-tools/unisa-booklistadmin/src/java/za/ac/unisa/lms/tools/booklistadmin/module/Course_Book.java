package za.ac.unisa.lms.tools.booklistadmin.module;

public class Course_Book extends AbstractBook{
	       
	       private String academicYear;
	       private String academicStatus;
	       private String courseNote;
	       private Integer acadYear;
	       private String courseLang;
	       private String course;
	       public String getCourse() {
	   		        return course;
	   	   }
	   	   public void setCourse(String course) {
	   		            this.course = course;
	   	   }
	   	   public String getCourseNote() {
				return courseNote;
			}
			public void setCourseNote(String courseNote) {
				this.courseNote = courseNote;
			}
			 public String getAcademicStatus() {
				return academicStatus;
			}
			public void setAcademicStatus(String academicStatus) {
				this.academicStatus = academicStatus;
			}
			public String getAcademicYear() {
				return academicYear;
			}
			public void setAcademicYear(String academicYear) {
				this.academicYear = academicYear;
			}
			public Integer getAcadYear() {
				return acadYear;
			}
			public void setAcadYear(Integer acadYear) {
				this.acadYear = acadYear;
			}
			public String getCourseLang() {
				return courseLang;
			}
			public void setCourseLang(String courseLang) {
				this.courseLang = courseLang;
			}
			
}

