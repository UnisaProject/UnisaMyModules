package za.ac.unisa.lms.tools.biodetails.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.biodetails.actions.BioDetailsAction;
import za.ac.unisa.lms.tools.biodetails.forms.Qualification;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AddressDetailsDAO extends StudentSystemDAO {

	Log log = LogFactory.getLog(BioDetailsAction.class);

	public ArrayList getPostalCodeListBySuburb(String searchType, String searchCriteria, String postalType, String addressType) throws Exception {
		//Construct list object of postal codes and suburbs
		List postalCodeList = new ArrayList();
		ArrayList postalCodes = new ArrayList();
		String bsParam1 = "B";
		String bsParam2 = "S";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
	    String searchParam = (searchCriteria == null || searchCriteria.trim()=="")? 
	    		searchParam = "%" : 
	    			searchCriteria.toUpperCase() + "%";
	    
		//2016 Edmund
		//Rule changed to get Postal Code for Postal as Box first, then if not exit, get Postal Code for Street and vice versa
		if (addressType.equalsIgnoreCase("Postal")){
	    if ("street".equalsIgnoreCase(postalType)|| "straat".equalsIgnoreCase(postalType)){
				bsParam1 = "S";
			}else{
				bsParam1 = "B";
			}
		}else{
			bsParam1 = "S";
			bsParam2 = "B";
		}
			
		log.debug("AddresDetailsAction - getPostalCodeListBySuburb - SearchType="+searchType+", SearchCriteria="+searchCriteria+", PostalType="+postalType+", bsParam1="+bsParam1+", bsParam2="+bsParam2);


		if (addressType.equalsIgnoreCase("Postal")){
			
			postalCodeList = jdt.queryForList(""
					+ " Select code , type , suburb, town from pstcod "
					+ " where upper(suburb) like ? "
					+ " and type = ? "
					+ " order by code ",
					new Object[] { "%"+searchParam.toUpperCase()+"%", bsParam1 },
					new int[] { Types.VARCHAR, Types.VARCHAR });
		}else{
			postalCodeList = jdt.queryForList(""
					+ " WITH query1 as (                                      "
			    	+ "       	Select code , type , suburb, town from pstcod "
			    	+ "       	where upper(suburb) like ?                    " 
			    	+ "			and type = ?                                  " 
			    	+ " 		order by code                                 "
			    	+ "  ),                                                   "
			    	+ "  query2 as (                                          "
			    	+ "       	Select code , type , suburb, town from pstcod "
			    	+ "       	where upper(suburb) like ?                    " 
			    	+ "			and type = ?                                  " 
			    	+ "			and upper(suburb) not in (					  "
			    	+ " 		Select upper(suburb) from pstcod 			  " 
			    	+ " 		where upper(suburb) like ? 			  "
			    	+ " 		and type = 'S')								  "
			    	+ " 		order by code                                 "
			    	+ " )                                                     "
			    	+ " SELECT *                                              "
			    	+ " FROM query1                                           "
			    	+ " UNION ALL                                             "
			    	+ " SELECT *                                              "
			    	+ " FROM query2                                           ",
					new Object[] { "%"+searchParam.toUpperCase()+"%", bsParam1, "%"+searchParam.toUpperCase()+"%", bsParam2, "%"+searchParam.toUpperCase()+"%" },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
		}
			if (postalCodeList.size() > 0) {
				String code = "";
				String descr = "";
				String town = "";
				String type = "";
				for ( int i=0; i<postalCodeList.size(); i++ ) { 
					ListOrderedMap test = (ListOrderedMap) postalCodeList.get(i);
					code =  (String) test.get("CODE").toString();
					descr = (String) test.get("SUBURB").toString();
					town = (String) test.get("TOWN").toString();
					type = (String) test.get("TYPE").toString();
					if (code.length()==1){
						code = "000" + code;
					}else if (code.length()==2){
						code = "00" + code;
					}else if (code.length()==3){
						code = "0" + code;
					}
					postalCodes.add(new org.apache.struts.util.LabelValueBean(code + " - " + type + " - " + descr+", " + town, code +"~"+ descr +"~"+ town));
				} 
			}
		
		return postalCodes;
	}
	
	public ArrayList getPostalCodeListByCode(String searchType, String searchCriteria, String postalType, String addressType) throws Exception {
		//Construct list object of postal codes and suburbs
		List postalCodeList = new ArrayList();
		ArrayList postalCodes = new ArrayList();
		String bsParam1 = "B";
		String bsParam2 = "S";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		//2016 Edmund
		//Rule changed to get Postal Code for Postal as Box first, then if not exit, get Postal Code for Street and vice versa
		if (addressType.equalsIgnoreCase("Postal")){
	    if ("street".equalsIgnoreCase(postalType)|| "straat".equalsIgnoreCase(postalType)){
				bsParam1 = "S";
			}else{
				bsParam1 = "B";
			}
		}else{
			bsParam1 = "S";
			bsParam2 = "B";
		}
			
		log.debug("AddresDetailsAction - getPostalCodeListBySuburb - SearchType="+searchType+", SearchCriteria="+searchCriteria+", PostalType="+postalType+", bsParam1="+bsParam1+", bsParam2="+bsParam2);


		if (addressType.equalsIgnoreCase("Postal")){
			
			postalCodeList = jdt.queryForList(""
					+ " Select code , type , suburb, town from pstcod "
					+ " where (substr('0000',1,4-length(CODE)) || to_char(CODE)) like ? "
					+ " and type = ? "
					+ " order by code ",
					new Object[] { "%"+searchCriteria.toUpperCase()+"%", bsParam1 },
					new int[] { Types.VARCHAR, Types.VARCHAR });
		}else{
			postalCodeList = jdt.queryForList(""
					+ " WITH query1 as (                                     					"
			    	+ "       	Select code , type , suburb, town from pstcod 					"
			    	+ "       	where (substr('0000',1,4-length(CODE)) || to_char(CODE)) like ?	"
					+ " 		and type = ? 													"
			    	+ " 		order by code                                 					"
			    	+ "  ),                                                   					"
			    	+ "  query2 as (                                          					"
			    	+ "       	Select code , type , suburb, town from pstcod 					"
			    	+ "       	where (substr('0000',1,4-length(CODE)) || to_char(CODE)) like ?	"
					+ " 		and type = ? 													"
			    	+ " 		order by code                                 					"
			    	+ " )                                                     					"
			    	+ " SELECT *                                              					"
			    	+ " FROM query1                                          					"
			    	+ " UNION ALL                                             					"
			    	+ " SELECT *                                              					"
			    	+ " FROM query2                                           					",
					new Object[] { "%"+searchCriteria.toUpperCase()+"%", bsParam1, "%"+searchCriteria.toUpperCase()+"%", bsParam2 },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
		}
			if (postalCodeList.size() > 0) {
				String code = "";
				String descr = "";
				String town = "";
				String type = "";
				for ( int i=0; i<postalCodeList.size(); i++ ) { 
					ListOrderedMap test = (ListOrderedMap) postalCodeList.get(i);
					code =  (String) test.get("CODE").toString();
					descr = (String) test.get("SUBURB").toString();
					town = (String) test.get("TOWN").toString();
					type = (String) test.get("TYPE").toString();
					if (code.length()==1){
						code = "000" + code;
					}else if (code.length()==2){
						code = "00" + code;
					}else if (code.length()==3){
						code = "0" + code;
					}
					postalCodes.add(new org.apache.struts.util.LabelValueBean(code + " - " + type + " - " + descr+", " + town, code +"~"+ descr +"~"+ town));
				} 
			}
		
		return postalCodes;
	}
	

	
	public ArrayList getCountryCodeList() throws Exception {
		//Construct list object of Country codes
		List countryList = new ArrayList();
		ArrayList countryCodes = new ArrayList();
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		countryList = jdt.queryForList("select code, eng_description from lns where code <> '1015' and in_use_flag = 'Y' order by eng_description");

		countryCodes.add(new org.apache.struts.util.LabelValueBean("Select Country", "-1"));
		
		if (countryList.size() > 0) {
				String code = "";
				String descr = "";
				for ( int i=0; i<countryList.size(); i++ ) { 
					ListOrderedMap test = (ListOrderedMap) countryList.get(i);
					code =  (String) test.get("code").toString();
					descr = (String) test.get("eng_description").toString();
					countryCodes.add(new org.apache.struts.util.LabelValueBean(descr, code));
				} 
			}
		
		return countryCodes;
	}
	
	public String getStreetOrBox(String postalCode){
		String result = "";
		String query = "SELECT type from pstcod where code = " + postalCode;
		result = this.querySingleValue(query,"TYPE");
		return result;
	}
	
	/**
	 * This method returns false if the suburb of the student's courier address doesn't match the postal code
	 *
	 * @param suburb     	The suburb name
	 * @param postalCode    The postal code
	 */
	public boolean isAddressValid(String suburb, String postalCode, String addressType, String streetOrBox) throws Exception {

		log.debug("AddressDetailsDAO - isAddressValid - AddressType="+addressType+", Suburb="+suburb+", PostalCode="+postalCode+", StreetOrBox="+streetOrBox);
		String query = "";
		boolean result = false;
		List suburbList = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		
		try {
			if (addressType.equalsIgnoreCase("Courier")){
				query = "SELECT distinct suburb from pstcod "
						+ " where (upper(suburb) = ? or upper(town) = ?) "
						+ " and (substr('0000',1,4-length(CODE)) || to_char(CODE)) = ? ";
				suburbList = jdt.queryForList(query,
						new Object[] {suburb, suburb, postalCode },
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
			}else{
				query = "SELECT distinct suburb from pstcod where TYPE = ? "
						+ " and (upper(suburb) = ? or upper(town) = ?) "
						+ " and (substr('0000',1,4-length(CODE)) || to_char(CODE)) = ? ";
				suburbList = jdt.queryForList(query,
						new Object[] {streetOrBox, suburb, suburb, postalCode },
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
			}

			log.debug("AddressDetailsDAO - isAddressValid - query: " + query);
			log.debug("AddressDetailsDAO - isAddressValid - streetOrBox: " + streetOrBox + ", suburb: " + suburb + ", postalCode: " + postalCode);

			if (suburbList.size() > 0) {
				log.debug("AddressDetailsDAO - isAddressValid - "+addressType+" Suburb/PstCode OK");
				result = true;
			}else{
				log.debug("AddressDetailsDAO - isAddressValid - "+addressType+" Suburb/PstCode NOT OK");
			}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error validating "+addressType+" address suburb vs postal code / "
					+ ex, ex);
		}

		return result;
	}
	
	 /* This method returns true if the String entered is a Suburb or Town
	 *
	 * @param addressLine     The Address Line
	 */
	public boolean isLineSub(String addressLine) throws Exception {

		//log.debug("AddressDetailsDAO - isLineSub - addressLine="+addressLine);
		
		boolean result = false;

		try {
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				
				String sql1 = "SELECT suburb, town from pstcod "
							+ " where (upper(suburb) = ? or upper(town) = ?) ";

				//log.debug("AddressDetailsDAO - isLineSub - sql1="+sql1+", addressLine="+addressLine.toUpperCase().trim());

				List queryList = jdt1.queryForList(sql1,
						new Object[] { addressLine.toUpperCase().trim(), addressLine.toUpperCase().trim() });
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					result = true;
				}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error validating courier address suburb on first line / " + ex);
		}

		//log.debug("AddressDetailsDAO - isLineSub - result final="+result);
		return result;
	}
	
	/**
	 * This method returns false if the postal code doesn't exist
	 *
	 * @param postalCode     The postal code
	 */
	public boolean isCodeValid(String postalCode, String addressType, String streetOrBox) throws Exception {

		log.debug("AddressDetailsDAO - isCodeValid - AddressType="+addressType+", PostalCode="+postalCode+", StreetOrBox="+streetOrBox);

		String query = "";
		boolean result = false;
		List codeList = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		try {
			if (addressType.equalsIgnoreCase("Courier")){
				query = "SELECT distinct CODE from pstcod "
						+ " where (substr('0000',1,4-length(CODE)) || to_char(CODE)) = ? ";
				codeList = jdt.queryForList(query,
						new Object[] {postalCode},
						new int[] {Types.VARCHAR });
			}else{
				query = "SELECT distinct CODE from pstcod where TYPE = ? "
						+ " and (substr('0000',1,4-length(CODE)) || to_char(CODE)) = ? ";
				codeList = jdt.queryForList(query,
						new Object[] {streetOrBox, postalCode },
						new int[] { Types.VARCHAR, Types.VARCHAR });
			}
		
			log.debug("AddressDetailsDAO - isCodeValid - query: " + query);
			log.debug("AddressDetailsDAO - isCodeValid - streetOrBox: " + streetOrBox + " , postalCode: " + postalCode);

			if (codeList.size() > 0) {
				log.debug("AddressDetailsDAO - isCodeValid - "+addressType+" Postal Code OK");
				result = true;
			}else{
				log.debug("AddressDetailsDAO - isCodeValid - "+addressType+" Postal Code NOT OK");
			}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error validating "+addressType+" Postal code / "
					+ ex, ex);
		}

		return result;
	}
	
	/**
	 * This method returns false if the suburb/town of the entered address doesn't exist
	 *
	 * @param suburb     	The suburb name
	 */
	public boolean isSuburbValid(String suburb, String addressType, String streetOrBox) throws Exception {

		log.debug("AddressDetailsDAO - isSuburbValid - AddressType="+addressType+", Suburb="+suburb+", StreetOrBox="+streetOrBox);
		String query = "";
		boolean result = false;
		List suburbList = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		
		try {
			if (addressType.equalsIgnoreCase("Courier")){
				query = "SELECT distinct suburb from pstcod "
						+ " where upper(suburb) = ? ";
				suburbList = jdt.queryForList(query,
						new Object[] {suburb},
						new int[] {Types.VARCHAR });
			}else{
				query = "SELECT distinct suburb from pstcod "
						+ " where TYPE = ? "
						+ " and upper(suburb) = ? ";
				suburbList = jdt.queryForList(query,
						new Object[] {streetOrBox, suburb },
						new int[] { Types.VARCHAR, Types.VARCHAR });
			}

			log.debug("AddressDetailsDAO - isSuburbValid - query: " + query);
			log.debug("AddressDetailsDAO - isSuburbValid - streetOrBox: " + streetOrBox + ", suburb: " + suburb);
			
			

			if (suburbList.size() > 0) {
				log.debug("AddressDetailsDAO - isSubTownValid - "+addressType+" Suburb OK");
				result = true;
			}else{
				log.debug("AddressDetailsDAO - isSubTownValid - "+addressType+" Suburb NOT OK");
			}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error validating "+addressType+" address Suburb / "
					+ ex, ex);
		}

		return result;
	}
	
	/**
	 * This method returns false if the Town of the entered address doesn't exist
	 *
	 * @param town     	The Town name
	 */
	public boolean isTownValid(String town, String addressType, String streetOrBox) throws Exception {

		log.debug("AddressDetailsDAO - isTownValid - AddressType="+addressType+", Town="+town+", StreetOrBox="+streetOrBox);

		String query = "";
		boolean result = false;
		List townList = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		 query = "SELECT distinct town from pstcod "
					+ " where TYPE = ? "
					+ " and upper(town) = ? ";
		try {
			if (addressType.equalsIgnoreCase("Courier")){
				query = "SELECT distinct town from pstcod "
						+ " where upper(town) = ? ";
				townList = jdt.queryForList(query,
						new Object[] {town},
						new int[] {Types.VARCHAR });
			}else{
				query = "SELECT distinct town from pstcod "
						+ " where TYPE = ? "
						+ " and upper(town) = ? ";
				townList = jdt.queryForList(query,
						new Object[] {streetOrBox, town },
						new int[] { Types.VARCHAR, Types.VARCHAR });
			}
			log.debug("AddressDetailsDAO - isTownValid - query: " + query);
			log.debug("AddressDetailsDAO - isTownValid - streetOrBox: " + streetOrBox + ", Town: " + town);

			if (townList.size() > 0) {
				log.debug("AddressDetailsDAO - isSubTownValid - "+addressType+" Town OK");
				result = true;
			}else{
				log.debug("AddressDetailsDAO - isSubTownValid - "+addressType+" Town NOT OK");
			}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error validating "+addressType+" address Town / "
					+ ex, ex);
		}

		return result;
	}
	
	/**
	 * This method returns the Town for the Suburb Entered
	 *
	 * @param suburb     	The Suburb name
	 */
	public String getTownName(String suburb, String addressType, String streetOrBox) throws Exception {
		
		log.debug("AddressDetailsDAO - getTownName - AddressType="+addressType+", Suburb="+suburb+", StreetOrBox="+streetOrBox);

		String result = "";
		List townList = new ArrayList();
		
		String query = "SELECT distinct town from pstcod "
					+ " where TYPE = ? "
					+ " and upper(suburb) = ? ";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			log.debug("AddressDetailsDAO - getTownName - query: " + query);
			log.debug("AddressDetailsDAO - getTownName - streetOrBox: " + streetOrBox + ", Suburb: " + suburb);
			
			townList = jdt.queryForList(query,
					new Object[] {streetOrBox, suburb },
					new int[] { Types.VARCHAR, Types.VARCHAR });

			Iterator i = townList.iterator();
			if (i.hasNext()) {
				log.debug("AddressDetailsDAO - getTownName - "+addressType+" Town Found");
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = (data.get("TOWN").toString());
			}else{
				log.debug("AddressDetailsDAO - getTownName - "+addressType+" Town NOT Found");
			}
		} catch (Exception ex) {
			throw new Exception("AddressDetailsDAO : Error finding "+addressType+" address Town / "
					+ ex, ex);
		}

		return result;
	}
	
	public Qualification getQualification(String studentNr) throws Exception{
		Qualification qual = new Qualification();
		String sql = "SELECT MK_HIGHEST_QUALIFI,SHORT_DESCRIPTION "+
        			 "FROM   STUANN, grd "+
        			 "WHERE MK_ACADEMIC_YEAR = (SELECT MK_LAST_ACADEMIC_y" +
        			 "  FROM   STU" +
        			 "	WHERE  NR = "+studentNr+")" +
        			 " AND MK_ACADEMIC_PERIOD = 1"+
        			 " AND MK_STUDENT_NR = "+studentNr+" "+
        			 " AND code = MK_HIGHEST_QUALIFI";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qual.setQualCode(data.get("MK_HIGHEST_QUALIFI").toString());
				qual.setQualDesc(data.get("SHORT_DESCRIPTION").toString());
			}
		} catch (Exception ex) {
				throw new Exception("AddressDetailsDAO : Error reading qualification from stuann / "+ ex,ex);
		}	
			return qual;
	}
	
	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results. 
	 * 
	 * @param query     The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 */
	private String querySingleValue(String query, String field){
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";
		
		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();									
			result = data.get(field).toString();
		}
		return result;
	}
	
	public String getStudentflag(String studentNr, String flag){
		String result = "";
		String query = "select fk_stunr from stadoc where FK_STUFLGCODE= '"+ flag + "' and fk_stunr="+studentNr;
		result = this.querySingleValue(query,"NUMBR");
		
		return result;
	}
	
	public void writeStudentFlag(String studentNr, String flag)throws Exception{
		
		Calendar cal = Calendar.getInstance();
		java.sql.Date currentDate =  new java.sql.Date(cal.getTime().getTime());
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		
		String sql1 = "select * from stadoc where FK_STUFLGCODE= '"+ flag + "' and fk_stunr="+studentNr;
		String sql2 = "insert into stadoc (creation_date, fk_stunr, FK_STUFLGCODE) values (?, ?, ?)";
		
		queryList = jdt.queryForList(sql1);
		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			log.debug("STADOC Record Exists Already: Flag=" + flag + ", Student=" + studentNr);
		}else{
			int recordsAffected = 0;
			recordsAffected = jdt.update(sql2,new Object[] { currentDate,studentNr,flag});
			if (recordsAffected > 0) {
				log.debug("Record Created: Flag=" + flag + ", Student=" + studentNr);
			}else{
				log.debug("Record Creation Failed: Flag=" + flag + ", Student=" + studentNr);
			}
	    }
			
	}


}
