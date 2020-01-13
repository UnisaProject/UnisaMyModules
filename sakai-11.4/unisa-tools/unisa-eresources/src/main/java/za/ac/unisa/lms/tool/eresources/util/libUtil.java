package za.ac.unisa.lms.tool.eresources.util;

import java.util.ArrayList;

import za.ac.unisa.lms.tool.eresources.model.Vendor;

public class libUtil {

	public libUtil() {
		// TODO Auto-generated constructor stub
	}
	
	
	/***************************************************************************************
	 * 
	 * These methods for Util class Maintain
	 * 
	 ***************************************************************************************/
	
			public void clearStartAlphabet()
			{
			}

			public ArrayList<?> getAlphabets(int placementID, int subjectID, int vendorID) throws Exception{
				return null;
				
			}
			
			
		
		   
		public ArrayList<?> selectSpecificdb() throws Exception {
			
			ArrayList<?> vendorList = new ArrayList<Vendor>();
			
			removeSpaces();
			return vendorList;
		}

	/**
		 * This method executes a query and returns a String value as result.
		 * An empty String value is returned if the query returns no results.
		 *
		 * @param query       The query to be executed on the database
		 * @param field		  The field that is queried on the database
		 * method written by: E Penzhorn*/
		
		@SuppressWarnings("unused")
		private String querySingleValue(String query, String field){
			return field;

		/*	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	List queryList;
	    	String result = "";

	 	   queryList = jdt.queryForList(query);
	 	   Iterator i = queryList.iterator();
	 	   i = queryList.iterator();
	 	   if (i.hasNext()) {
				 ListOrderedMap data = (ListOrderedMap) i.next();
				 if (data.get(field) == null){
				 } else {
					 result = data.get(field).toString();
				 }
	 	   }
	 	   return result;*/
		}
		

	/***************************************************************************************
	 * 
	 * These methods for Util class Front End
	 * 
	 ***************************************************************************************/
	/**These methods for Util class*/
		
	public boolean isEnabled(char alphabet, int placementID, int subjectID, int vendorID){
		return false;
		
	}
		
	public String getStartAlphabet(String startAlphabet){
		return startAlphabet;
	}
			
	public void clearStartAlphabet(String startAlphabet){
	}
		
	/*	public ArrayList<Alphabet> getAlphabets(int placementID, int subjectID, int vendorID) throws Exception{
		}*/
		
		
	public void removeSpaces(){
	}
		
	// remove leading whitespace 
	public static String ltrim(String source) {
			return source.replaceAll("^\\s+", "");
		
	}
		
	// remove trailing whitespace 
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}
	

	//A-Z Methods
	public void atozInsert(ArrayList<?> resourceList) throws Exception {
	
	}
		
	public void deleteATOZ() throws Exception{
	
	}
		
	public boolean isRecordExist(int resID, String resDescr, String logoFile, String logoURL, String resName, String vendorName, String addInfo, String logIn, String password, 
			String dTraining, String tTraining, String newsURL, String newsTitle, String alert, String accessNote, String refManagement, String highlight, String highlightDesc, 
			String contentType, int placementID) throws Exception{
				return false;
		
	}

}
