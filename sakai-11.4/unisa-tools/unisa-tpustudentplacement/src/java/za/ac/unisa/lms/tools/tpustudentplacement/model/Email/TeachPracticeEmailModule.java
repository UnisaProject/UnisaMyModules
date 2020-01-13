package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.domain.general.Person;
public class TeachPracticeEmailModule {
		
		private String recipient;
		private String recipientType;//either a school or student
		
		
		public String  getRecipient(){
			return recipient;
		}
		public void  setRecipient(String recipient){
			     this.recipient=recipient;
		}
		public String  getRecipientType(){
			return recipientType;
		}
		public void  setRecipientType(String recipientType){
			     this.recipientType=recipientType;
		}
		private Student  student;
		private School school;
		public void setSchool( School school){
			this.school=  school;
		}
		public School getSchool(){
			return school;
		}
		public Student getStudent(){
			  return student;
		}
		public void  setStudent(Student student){
			      this.student=student;
		}
		Person user;
		public void  setUser(Person user){
			this.user=user;
		}
		public Person  getUser(){
			   return user;
		}


}
