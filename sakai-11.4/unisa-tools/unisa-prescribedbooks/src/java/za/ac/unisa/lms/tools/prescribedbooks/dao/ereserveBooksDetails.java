package za.ac.unisa.lms.tools.prescribedbooks.dao;
public class ereserveBooksDetails extends books{
	// this class encapsulates the details of a book classified as Ereserve
		   private String url;
		   private String volume;
		   public String getUrl() {
			     return url;
		   }
		   public void setUrl(String url){
			      this.url=url;
		   }
		   public String getVolume(){
			   return volume;
		   }
		   public void setVolume(String volume){
			      this.volume=volume;
		   }
 
}

