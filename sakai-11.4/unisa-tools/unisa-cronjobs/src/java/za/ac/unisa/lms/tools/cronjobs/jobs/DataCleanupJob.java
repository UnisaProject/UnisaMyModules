package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.rpc.ServiceException;

//import oracle.jdbc.OracleTypes;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;


import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

import za.ac.unisa.lms.db.SakaiDAO;

public class DataCleanupJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	public static long runcount = 1L;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private EmailService emailService;
	String url = "";

	/*
	 * Crontab file
___________
Crontab syntax :-
A crontab file has five fields for specifying day , date and time  followed by the command to be run at that interval.
*     *   *   *    *  command to be executed
-     -    -    -    -
|     |     |     |     |
|     |     |     |     +----- day of week (0 - 6) (Sunday=0)
|     |     |     +------- month (1 - 12)
|     |     +--------- day of month (1 - 31)
|     +----------- hour (0 - 23)
+------------- min (0 - 59) (non-Javadoc)
	 */

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		String subjectStart = "DataCleanup Job Started";
		String bodyStart = "DataCleanup Job Started";
		try {
			sendEmail(subjectStart, bodyStart, "syzelle@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("~~~~~~~~~~ Cronjob: DataCleanupJob");
		
		// delete extra tables
		
		// delete Discussion Forums
		
		// delete Melete
		
		// delete gradebook
		
		// delete calendar
		
		// delete Samigo
		
		// delete Content
		
		// delete Sakai realms
		System.out.println(">>>>>> call method deleteRealmData <<<<<");
		deleteRealmData();
		
		// test calling of a db stored procedure
		//https://www.mkyong.com/jdbc/jdbc-callablestatement-stored-procedure-out-parameter-example/
		//https://docs.oracle.com/cd/A84870_01/doc/java.816/a81354/samapp2.htm
				
		// delete sakai management tables
		
		// delete UNISA_MIS
		/*
		String DELETE_UNISA_MIS = " DELETE "+ 
								  " FROM  UNISA_MIS"+ 
								  " WHERE to_char(date_counted,'YYYY') <= 2010;"; 
		JdbcTemplate jdt_DeleteMIS = new JdbcTemplate(new SakaiDAO().getDataSource());
		try {
			jdt_DeleteMIS.update(DELETE_UNISA_MIS,
                    new Object[] {resourceId.toString()});
                    						        	
        } catch (Exception e) { //jdt_DeleteMIS
            //System.out.println("***** CONTENTCLEANUP: delete record from database table content_resource error "+e);
           // result.append("<br> ----- DELETE_CONTENT_RESOURCE_BB_DELETE ERROR ----- ");
           // result.append(resourceId.toString());
           // result.append(e);
        } //jdt_DeleteMIS	  
		*/
	} // end of public void executeLocked
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");


		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
	} // end of sendEmail

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	} // end of public String getOutput()
	
	public void deleteRealmData() {
        
/*		String DB_PACKAGE = "SAKAIDBA";
		String DB_SCHEMA = "SAKAIDBA";
	    String STORED_PROC = "DELETE_SAKAI_REALMS";
	    String outParamMsg = "";	//For Stored Procedure results
    	String outParam = "";		//For Stored Procedure arguments
    	boolean procedureSuccess = false;	

	    // JdbcTemplate for stored procedure
	    JdbcTemplate jdt2 = new JdbcTemplate( new SakaiDAO().getDataSource() );

	    System.out.println(">>>>> BEFORE call of stored procedure");
	 	// Define SimpleJdbcCall object that will be used to call stored procedure
	 	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall( jdt2 );
	 	simpleJdbcCall.withSchemaName( DB_SCHEMA );			// Schema name
	 	simpleJdbcCall.withProcedureName( STORED_PROC );	// Procedure name
	 	//simpleJdbcCall.withCatalogName( DB_PACKAGE );		// Package Name
	 	simpleJdbcCall.declareParameters(
	 			new SqlParameter( "param_siteid", OracleTypes.VARCHAR ),
	 			new SqlOutParameter( "param_message", OracleTypes.VARCHAR ) );
	    
	 	System.out.println(">>>>> BEFORE in out params of stored procedure");
		// Put Procedure IN and OUT parameters in a map
		Map<String, Object> procParamsMap = new HashMap<String, Object>();
		procParamsMap.put( "param_siteid", "" );
		procParamsMap.put( "param_message", outParam );
		
		// Add IN and OUT parameters from above map into object for procedure call
		SqlParameterSource inOutParams = new MapSqlParameterSource().addValues( procParamsMap );
		
		// Call Database Stored Procedure 'SAKAIDBA.DELETE_DISCUSSION_FORUM_DATA' for each site ID
		// Execute stored procedure and return the OUT Param as a Map
		Map<String,Object> outParamMsgMap = simpleJdbcCall.execute( inOutParams );
		outParamMsg = (String)outParamMsgMap.get("param_message");		//Cast Map Results into String
		
		System.out.println(">>>>> OUT Message of stored procedure == "+outParamMsg);
		// When OUT Param returns a message (not empty), webservice run is successfull
		if( !outParamMsg.isEmpty() )
			procedureSuccess = true;
*/
		
		/*
        System.out.println(">>>>> BEFORE call of stored procedure");
        //https://lalitjc.wordpress.com/2013/07/02/different-ways-of-calling-stored-procedure-using-spring/
        JdbcTemplate jdt1 = new JdbcTemplate(new SakaiDAO().getDataSource());
        System.out.println(">>>>> after jdt1");
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdt1)
            .withCatalogName("SAKAIDBA")
			.withProcedureName("DELETE_SAKAI_REALMS");
		System.out.println(">>>>> after SimpleJdbcCall");

		/*
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		System.out.println(">>>>> after inParamMap");
		inParamMap.put("param_acadyear", "06-S1");
		//inParamMap.put("param_siteid", "");
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		System.out.println(">>>>> after SqlParamtersource");
		*/
		
		//Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		//Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute();
		//System.out.println(">>>>> after simpleJdbcCallResult");
		//System.out.println(">>>>> stored procedure RESULT" +simpleJdbcCallResult);


        /*
        List<SqlParameter> parameters = Arrays.asList(
                new SqlParameter(Types.BIGINT), new SqlOutParameter("status_out", Types.BOOLEAN));

        Map<String, Object> t = jdt1.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement callableStatement = con.prepareCall("{call DELETE_SAKAI_REALM ()}");
                callableStatement.setLong(1, "XX");
                callableStatement.registerOutParameter(2, Types.BOOLEAN);
                return callableStatement;
            }
        }, parameters);

       System.out.println("Status of moving the person to history: "+t.get("status_out"));
       */

    }

/*
http://www.logicbig.com/tutorials/spring-framework/spring-data-access-with-jdbc/spring-call-stored-procedure/
*/
/*   @Override
    public void moveToHistoryTable(Person person) {
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(dataSource);
        procedure.setSql("DELETE_SAKAI_REALM");
        procedure.setFunction(false);

        SqlParameter[] parameters = {
                new SqlParameter(Types.BIGINT),
                new SqlOutParameter("status_out", Types.BOOLEAN)
        };

        procedure.setParameters(parameters);
        procedure.compile();

        Map<String, Object> result = procedure.execute(person.getId());
        logger.info("Status of moving the person to history: " + result);
    }
*/

} // end of public class DataCleanupJob