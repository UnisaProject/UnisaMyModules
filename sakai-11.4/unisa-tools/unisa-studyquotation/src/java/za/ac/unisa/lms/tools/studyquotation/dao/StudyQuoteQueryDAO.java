package za.ac.unisa.lms.tools.studyquotation.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class StudyQuoteQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudyQuoteQueryDAO.class);
	
	/**
	 * This method returns false if the today's date not within valid dates
	 * on the database.
	 * Read database on current year, if false read on current year plus 1.
	 *
	 * @param type       The type of period
	 * @param addPeriod  The addition period
	 */
	public short getValidQuotationYear() throws Exception{

		short result = Short.parseShort("0");
		String toDate = "";
		int currentYear = -1;
		//currentYear = Calendar.getInstance().get(Calendar.YEAR);
		currentYear = getCurrentQuotationYear();
			
		String query1 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0";
		String query2 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0 and trunc(sysdate) between from_date and to_date";
		String query3 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0 and trunc(sysdate)>trunc(from_date)";

		try{
			Log log = LogFactory.getLog(StudyQuoteQueryDAO.class);
			toDate = this.querySingleValue(query1,"TO_DATE");

			if (!"".equalsIgnoreCase(toDate)) {
				// If record exists default to 0
				result = Short.parseShort("0");
				toDate="";
				if ("0001-01-01".equalsIgnoreCase(toDate)){
					toDate = this.querySingleValue(query3,"TO_DATE");
				}else{
					toDate = this.querySingleValue(query2,"TO_DATE");
				}
				if (!"".equalsIgnoreCase(toDate)) {
					result = Short.parseShort(Integer.toString(currentYear));
				}
			}

			log.debug("Quotation period 0 = "+ Integer.toString(result));

			if (result==0){
				//Try again with current year + 1
				currentYear = currentYear + 1;
				query1 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0";
				query2 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0 and trunc(sysdate) between from_date and to_date";
				query3 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+currentYear+" and type='IQ' and semester_period=0 and trunc(sysdate)>trunc(from_date)";

				toDate = this.querySingleValue(query1,"TO_DATE");

				if (!"".equalsIgnoreCase(toDate)) {
					// If record exists default to false
					result = Short.parseShort("0");
					toDate="";
					if ("0001-01-01".equalsIgnoreCase(toDate)){
						toDate = this.querySingleValue(query3,"TO_DATE");
					}else{
						toDate = this.querySingleValue(query2,"TO_DATE");
					}
					if (!"".equalsIgnoreCase(toDate)) {
						result = Short.parseShort(Integer.toString(currentYear));
					}
				}

				log.debug("Quotation period 0 = "+ Integer.toString(result));

			}
		}catch(Exception ex){
			throw new Exception("StudyQuoteQueryDao : Error validating quotation period / "+ ex,ex);
		}

		return result;
	}
	
	
	public int getCurrentQuotationYear() {

		int currentYear = 0;
		log.debug("get current year - RegistrationStatus.");
			/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		if (Calendar.getInstance().get(Calendar.MONTH) < 7) {
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/*Removed for test purposes +1*/
			currentYear = (Calendar.getInstance().get(Calendar.YEAR) + 1 );
		}
		log.debug("Returning "+currentYear+" as the current study fee quotation year");

		return currentYear;

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
			if (data.get(field)!= null){
				result = data.get(field).toString();
			}else{
				result = "";
			}
		}
		return result;
	}
}

