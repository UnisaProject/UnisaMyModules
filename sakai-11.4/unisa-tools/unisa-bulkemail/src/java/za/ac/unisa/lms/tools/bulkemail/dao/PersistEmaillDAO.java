package za.ac.unisa.lms.tools.bulkemail.dao;

import java.sql.Types;
import java.util.Vector;
import java.sql.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;


import za.ac.unisa.lms.db.SakaiDAO;

public class PersistEmaillDAO extends SakaiDAO {
	private PreparedStatementCreatorFactory pstmtFactoryMessage;

	public void insertContent(String userID, String messageSubject,
			String messageText,
			//String sysdate,
			String confineGender,
			String confineHomeLanguage,
			String confineCountry,
			String confineProvince,
			String confineRegion,
			String confineRace,
			String confineResRegion,
			Vector sites) throws Exception {


		if(confineGender.equalsIgnoreCase("") | confineGender.equalsIgnoreCase("notspecified")) {
			confineGender=null;
		}
		//else{confineGender="'confineGender'";}
		//confineGender(confineGender);
		if(confineHomeLanguage.equalsIgnoreCase("")) {
			confineHomeLanguage=null;
		}
		if(confineCountry.equalsIgnoreCase("")) {
			confineCountry=null;
		}
		if(confineProvince.equalsIgnoreCase("")) {
			confineProvince=null;
		}

		if(confineRegion.equalsIgnoreCase("")) {
			confineRegion=null;
		}
		if(confineResRegion.equalsIgnoreCase("")) {
			confineResRegion=null;
		}
		if(confineRace.equalsIgnoreCase("")) {
			confineRace=null;
		}
		

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql="insert into BULKMAIL_CONTENT (Message_ID,user_ID, Subject, Date_created, Content, Gender, HLanguage, Country, Province, Region, Race, ResRegion)" +
		" values(BULKMAIL_CONTENT_0.nextval, '"+userID+"' ,'"+makeJDBCCompatible(messageSubject)+"' ,"+"sysdate"+" ,"+"'"+makeJDBCCompatible(messageText)+"' , "+confineGender(confineGender)+
		" , '"+confineHomeLanguage+"' , '"+confineCountry+"' , '"+confineProvince+"' , '"+confineRegion+"' , '" +confineRace+"' , '"+confineResRegion+ "' )" ;
		
		System.out.println("The query is as follows: -");
		System.out.println(sql);
		
		jdt.update(sql);
		//jdt.execute(sql);
		
		//" values(BULKMAIL_CONTENT_0.nextval,?,?,sysdate,?,?,?,?,?,?,?,?)

	}
		
	/*
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		pstmtFactoryMessage = new PreparedStatementCreatorFactory(
				"insert into BULKMAIL_CONTENT (Message_ID,user_ID, Subject, Date_created, Content, Gender, HLanguage, Country, Province, Region, Race, ResRegion)" +
				" values(BULKMAIL_CONTENT_0.nextval,?,?,sysdate,?,?,?,?,?,?,?,?)",
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });

		pstmtFactoryMessage.setGeneratedKeysColumnNames(new String[] {"Message_ID"} );
		pstmtFactoryMessage.setReturnGeneratedKeys(true);
		PreparedStatementCreator pstmt = pstmtFactoryMessage.newPreparedStatementCreator(
				new Object[] {userID, messageSubject,messageText, confineGender, confineHomeLanguage, confineCountry, confineProvince, confineRegion, confineRace, confineResRegion });
		jdt.update(pstmt, keyHolder);

		
		System.out.println(" the value of the key is : "+keyHolder.getKey());
		scrollSites(sites, new Integer((keyHolder.getKey()).intValue()));

	}
*/
	
	
	private String confineGender(String gender ){
		String gend =gender;
		if (gend==null){gend=null;}
		else {gend ="'"+gend+"'";}
		
  return gend;		
	}
	
    private String makeJDBCCompatible(String convert) {
        String converted = null;
        
        if (convert.lastIndexOf("'") > -1) {
            converted = convert.replaceAll("'","''");
        }
        else 
            converted = convert;
            
        return converted;
    }
    
    
	public void scrollSites(Vector selectedSitesPerLecturer, Integer messageID) throws Exception {
		try {

			for(int i = 0; i < selectedSitesPerLecturer.size(); i++) {


				insertSite((String)selectedSitesPerLecturer.get(i), messageID.intValue());


			}

		}catch(SQLException ex) {

			throw new Exception("PersistEmail DAO: Error "+ ex.getCause());

		}
	}
/*
	public void insertSite(String siteID, int messageID) throws Exception{
		pstmtFactoryMessage = new PreparedStatementCreatorFactory(
				"insert into BULKMAIL_SITE (Message_ID, SITE_ID) values " +
				" (?, ?)", new int[] { Types.NUMERIC, Types.VARCHAR });

		//"insert into BULKMAIL_SITE (Message_ID, SITE_ID) values " +
		//"("+ messageID+",'"+siteID+"'");//, new int[] { Types.INTEGER,Types.VARCHAR });

		//Types.NUMERIC
		//pstmtFactoryMessage.
		System.out.println("insert into BULKMAIL_SITE (Message_ID, SITE_ID) values("+ messageID+",'"+siteID+"'");

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator pstmt = pstmtFactoryMessage.newPreparedStatementCreator(
				new Object[] {new Integer(messageID),siteID });
		jdt.update(pstmt, keyHolder);
		//jdt.exectute(pstmt,pstmtFactoryMessage);
	}
	*/
	public void insertSite(String siteID, int messageID) throws Exception{

		    String sql ="insert into BULKMAIL_SITE (Message_ID, SITE_ID) values " +
			" (?, ?)";

			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			jdt.update(sql,new Object[] { messageID, siteID});



	}





}
