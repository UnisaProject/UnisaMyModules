package za.ac.unisa.lms.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
public class StudentSystemGeneralDAO extends StudentSystemDAO{
	public List getExamPeriods() {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.queryForList("SELECT * FROM XAMPRD ORDER BY CODE");

		List xamprds = new Vector();

		Iterator i = results.iterator();
		while (i.hasNext()) {
			ListOrderedMap row = (ListOrderedMap) i.next();
			Xamprd xamprd = new Xamprd();
			xamprd.setAfrDescription((String) row.get("AFR_DESCRIPTION"));
			xamprd.setAfrShortDescript((String) row.get("AFR_SHORT_DESCRIPTION"));
			xamprd.setEngDescription((String) row.get("ENG_DESCRIPTION"));
			xamprd.setEngShortDescript((String) row.get("ENG_SHORT_DESCRIPTION"));
			xamprd.setCode(new Short(((BigDecimal) row.get("CODE")).shortValue()));

			xamprds.add(xamprd);
		}

		return xamprds;
	}
	
	public String getExamPeriodDesc(Short code, String type){
		String description="";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		List results = jdt.queryForList("select ENG_DESCRIPTION,ENG_SHORT_DESCRIPT from XAMPRD  where CODE=" + code);
		
		Iterator i = results.iterator();
		if (i.hasNext()) {
			ListOrderedMap row = (ListOrderedMap) i.next();
			if (type.equalsIgnoreCase("short")){
				description = (String)row.get("ENG_SHORT_DESCRIPT");
			}else{
				description = (String)row.get("ENG_DESCRIPTION");
			}
		}

		return description;
	}

	/**
	 * This method returns a list of general codes for a specific category.
	 *
	 * @param genCategory     The category code
	 * @param sortOrder		  This field indicates the sort order
	 *
	 * Sort order:
	 * 0 - sort on code
	 * 1 - sort on english description
	 * 3 - don't sort
	 */
	public List<Gencod> getGenCodes(short genCategory, int sortOrder) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		/* Setup order by clause */
		String orderBy = "";
		if (sortOrder == 0){
			orderBy = " ORDER BY CODE";
		}else if (sortOrder == 1){
			orderBy = " ORDER BY ENG_DESCRIPTION";
		}else if (sortOrder == 2){
			orderBy = " ";
		}else if (sortOrder ==3) {
			orderBy = " ORDER BY AFR_DESCRIPTION";
		}

		List results = jdt.queryForList("SELECT * FROM GENCOD WHERE FK_GENCATCODE =" + genCategory + " AND IN_USE_FLAG='Y'" + orderBy);
		List<Gencod> gencods = new Vector();

		Iterator i = results.iterator();
		while (i.hasNext()) {
			ListOrderedMap row = (ListOrderedMap) i.next();
			Gencod gencod = new Gencod();
			gencod.setCode((String) row.get("CODE"));
			gencod.setAfrDescription((String) row.get("AFR_DESCRIPTION"));
			gencod.setEngDescription((String) row.get("ENG_DESCRIPTION"));

			gencods.add(gencod);
		}

		return gencods;
	}

	/**
	 * This method returns detail for a specific general code and category.
	 *
	 * @param genCategory     The category code
	 * @param genCode	  	  The general code
	 *
	 */
	public Gencod getGenCode(String genCategory, String gencode) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		List results = jdt.queryForList("select * from gencod g where g.CODE='"+gencode+
										"' and g.FK_GENCATCODE="+genCategory);

		Gencod gencod = new Gencod();
		Iterator i = results.iterator();
		if (i.hasNext()) {
			ListOrderedMap row = (ListOrderedMap) i.next();
			gencod.setAfrDescription((String) row.get("AFR_DESCRIPTION"));
			gencod.setEngDescription((String) row.get("ENG_DESCRIPTION"));

		}

		return gencod;
	}
	
public String getGenMessage(String msgCode, String program) throws Exception {
		
		PreparedStatement pst = null;
		Connection con = null;
	    ResultSet rs = null;
	    String message="";
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    con = jdt.getDataSource().getConnection();		

		    String sql =
		    "select (message || ' (Msg:' || msg_Code || ')') as message from genmsg where UPPER(program)=UPPER(?)" +
	    	" and UPPER(msg_code)=UPPER(?) " +
	    	" and UPPER(system_gc285)='MYUNISA'";
		    pst = con.prepareStatement(sql);	
		    pst.setString(1, program);
		    pst.setString(2, msgCode);
		    
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	message =rs.getString("message").trim();
		    }		     
		    return message;
	    }
		    catch (Exception ex) {
				throw new Exception("StudentSystemGeneralDAO : Error reading table genmsg/ " + ex, ex);				
		    } finally {
				try {
					if (con!=null){con.close();}
					if (pst!=null){pst.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
	}	

	public String getGenMessage(String msgCode, String program, String system) throws Exception {
	
	PreparedStatement pst = null;
	Connection con = null;
    ResultSet rs = null;
    String message="";
    
    try{
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    con = jdt.getDataSource().getConnection();		

	    String sql =
	    "select (message || ' (Msg:' || msg_Code || ')') as message from genmsg where UPPER(program)=UPPER(?)" +
    	" and UPPER(msg_code)=UPPER(?) " +
    	" and UPPER(system_gc285)=UPPER(?)";
	    pst = con.prepareStatement(sql);	
	    pst.setString(1, program);
	    pst.setString(2, msgCode);
	    pst.setString(3, system);
	    
	    rs = pst.executeQuery();
	    if (rs.next()){
	    	message =rs.getString("message").trim();
	    }		     
	    return message;
    }
	    catch (Exception ex) {
			throw new Exception("StudentSystemGeneralDAO : Error reading table genmsg/ " + ex, ex);				
	    } finally {
			try {
				if (con!=null){con.close();}
				if (pst!=null){pst.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
}	
}