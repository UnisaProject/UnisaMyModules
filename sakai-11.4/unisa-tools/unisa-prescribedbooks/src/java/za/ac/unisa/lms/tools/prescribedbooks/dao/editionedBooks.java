package za.ac.unisa.lms.tools.prescribedbooks.dao;

public class editionedBooks extends books{
	    //I lacked a better name so I  named this class this way to reflect the fact that it  an 'edition' member(field)
    	//and 
	    protected String edition;
	    public String  getEdition(){
		  return edition;
	    }
	    public void setEdition(String edition ){
		      this.edition= edition;
	    }
	    public String getPubYear() {
			return pubYear;
		}
		
}
