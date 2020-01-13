package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sakaiproject.component.cover.ComponentManager;

public class MyUnisaStatsDAO extends SakaiDAO {
	
	private UserDirectoryService userDirectoryService;

	/** history tables
	 * SAKAI_EVENT - original /current table (from 1 Sept to 2006 up to now)
	 * SAKIA_EVENT_OLD - history data until end August 2006
	 * SAKAI_SESSION - original session table (history up to 18 May 2007 was copied to SAKAI_SESSION_ORIG)
	 * SAKAI_SESSION_ORIG - history session data (1 jan 2006 to 18 May 2007)
	 */

	/**
	 * Method: myUnisaAccountExist
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 * input: studentNr
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public ListOrderedMap getStatistics(String startDate, String endDate) throws Exception{
		ListOrderedMap statsListOrderedMap = new ListOrderedMap();
		if(null == startDate || "".equals(startDate)) {
			startDate = "'2006-01-01'";
		}
		if(null == endDate || "".equals(endDate)) {
			endDate = "NOW()";
		}
		String joinedActiveSQL = "SELECT count(USER_ID) as JA " +
        "FROM  JOIN_ACTIVATION " +
        "WHERE ACT_STATUS = 'Y' " +
        "AND JOIN_DATE BETWEEN "+startDate+" AND "+endDate;
		String joinedNotActiveSQL = "SELECT count(USER_ID) as JN " +
        "FROM  JOIN_ACTIVATION " +
        "WHERE ACT_STATUS = 'N' " +
        "AND JOIN_DATE BETWEEN "+startDate+" AND "+endDate;


		try{
			int joinActivated = Integer.parseInt(this.querySingleValue(joinedActiveSQL,"JA"));
			int joinNotActivated = Integer.parseInt(this.querySingleValue(joinedNotActiveSQL,"JN"));
			Integer joinAll = new Integer(joinActivated + joinNotActivated);
			statsListOrderedMap.put("joinActivated",new Integer(joinActivated));
			statsListOrderedMap.put("joinNotActivated",new Integer(joinNotActivated));
			statsListOrderedMap.put("joinAll",joinAll);
			statsListOrderedMap.put("windowPeriod","From: "+startDate+" To: "+endDate);
		} catch (Exception ex) {
            throw new Exception("MyUnisaStatsDAO: getStatistics: Error occurred / "+ ex,ex);
		}

       return statsListOrderedMap;
	}

	/**
	 * Method: getEventStats
	 *  to get stats for a certain event for a certain day.
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getEventStats(String event, String forDate, String dateType, String forSite, String forUser) throws Exception{
		int eventStats = 0;
		String errorMessage = "";

		String select = "SELECT COUNT(*) AS A "+
		             "FROM   SAKAI_EVENT "+
		             "WHERE  EVENT = '"+event+"' "+
		             "AND    TO_CHAR(EVENT_DATE,'YYYY-MM-DD') = '"+forDate+"'";

		try{
			eventStats = Integer.parseInt(this.querySingleValue(select,"A"));

			// insert/update table with stats
			errorMessage = unisaMISInsertOrUpdate(event,forDate,dateType,eventStats,forSite, forUser);
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getEventStats: Error occurred (event="+event+") / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getEventStats: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/*
	 * 31 Oct 2007 Sonette removed method as table SAKAI_EVENT_OLD1 will not exist in SAKAI ORACLE
	 * Method: getEventStatsTmp1
	 *  to get stats for a certain event for a certain day.
	 *  using tables: SAKAI_EVENT_OLD1 AND SAKAI_SESSION_200707201729
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	/**
	 * Method: getUserLoginDaily
	 *  to get stats daily user logins
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getUserLoginDaily(String event, String forDate) throws Exception{
		String errorMessage = "";
		int lecturerLogin = 0;
		int studentLogin = 0;
		int nullLogin = 0;
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		String select = "SELECT SAKAI_SESSION.SESSION_USER AS USR, COUNT(*) AS TOTAL "+
		             "FROM   SAKAI_SESSION, SAKAI_EVENT "+
		             "WHERE  SAKAI_SESSION.SESSION_ID = SAKAI_EVENT.SESSION_ID " +
		             "AND    SAKAI_EVENT.EVENT = '"+event+"' "+
		             "AND    TO_CHAR(SAKAI_EVENT.EVENT_DATE,'YYYY-MM-DD') = '"+forDate+"' "+
		             "GROUP BY SAKAI_SESSION.SESSION_USER ";

		try{
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String user = data.get("USR").toString();
    			int total = Integer.parseInt(data.get("TOTAL").toString());

    			User userTmp;
    			try {
    				userTmp = userDirectoryService.getUser(user);

    				if (null == userTmp.getType()) {
    					nullLogin = nullLogin + total;
    				} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    					lecturerLogin = lecturerLogin + total;
    				} else if (userTmp.getType().equalsIgnoreCase("student")) {
    					studentLogin = studentLogin + total;
    				} else {
    					nullLogin = nullLogin + total;
    				}
    			} catch (UserNotDefinedException e){
    				try {
    					userTmp = userDirectoryService.getUserByEid(user);
    					if (null == userTmp.getType()) {
    						nullLogin = nullLogin + total;
    					} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    						lecturerLogin = lecturerLogin + total;
    					} else if (userTmp.getType().equalsIgnoreCase("student")) {
    						studentLogin = studentLogin + total;
    					} else {
    						nullLogin = nullLogin + total;
    					}
    				} catch (UserNotDefinedException ex) {
    					nullLogin = nullLogin + 1;
    				}
    			}

    		}

    		errorMessage = insertUnisaMIS(event,forDate,studentLogin,"gateway","S");
    		errorMessage = insertUnisaMIS(event,forDate,lecturerLogin,"gateway","L");
    		errorMessage = insertUnisaMIS(event,forDate,nullLogin,"gateway","A");
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getUserLoginDaily: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getUserLoginDaily: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * 1 Nov 2007 Sonette removed method as was only used to get history stats
	 * Method: getUserLoginDailyOrig
	 *  to get stats daily user logins
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */


	/**
	 * Method: getUserLoginMonthly
	 *  to get stats daily user logins
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getUserLoginMonthly(String event, String forDate) throws Exception{
		String errorMessage = "";
		int lecturerLogin = 0;
		int studentLogin = 0;
		int nullLogin = 0;
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		String[] tmp = forDate.split("-");


		String select = "SELECT DISTINCT SAKAI_SESSION.SESSION_USER AS USR "+
		             "FROM   SAKAI_SESSION, SAKAI_EVENT "+
		             "WHERE  SAKAI_SESSION.SESSION_ID = SAKAI_EVENT.SESSION_ID " +
		             "AND    SAKAI_EVENT.EVENT = '"+event+"' "+
		             "AND    TO_CHAR(SAKAI_EVENT.EVENT_DATE,'YYYY') = '"+tmp[0]+"' ";

		try{
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String user = data.get("USR").toString();

       			User userTmp = null;
    			try {
    				userTmp = userDirectoryService.getUser(user);
    				if (null == userTmp.getType()) {
    					nullLogin = nullLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    					lecturerLogin = lecturerLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("student")) {
    					studentLogin = studentLogin + 1;
    				} else {
    					nullLogin = nullLogin + 1;
    				}
    			} catch (UserNotDefinedException e){
    				try {
    					userTmp = userDirectoryService.getUserByEid(user);

    					if (null == userTmp.getType()) {
    						nullLogin = nullLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    						lecturerLogin = lecturerLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("student")) {
    						studentLogin = studentLogin + 1;
    					} else {
    						nullLogin = nullLogin + 1;
    					}
    				} catch (UserNotDefinedException ex) {
    					nullLogin = nullLogin + 1;
    				}
    			}
    		}

    		errorMessage = insertUnisaMIS("user.login.unique",forDate+"-01",studentLogin,"gateway","S");
    		errorMessage = insertUnisaMIS("user.login.unique",forDate+"-01",lecturerLogin,"gateway","L");
    		errorMessage = insertUnisaMIS("user.login.unique",forDate+"-01",nullLogin,"gateway","A");
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getUserLoginMonthly: Error occurred / "+ ex;
			//throw new Exception("MyUnisaStatsDAO: getUserLoginMonthly: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * Method: getUserLoginMonthly
	 *  to get stats daily user logins
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getUserLoginMonthlyHistory(String event, String forYearMonth) throws Exception{
		String errorMessage = "";
		int lecturerLogin = 0;
		int studentLogin = 0;
		int nullLogin = 0;
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		String[] tmp = forYearMonth.split("-");
		String tmpDate = tmp[0]+"-"+tmp[1];

		String select = "SELECT DISTINCT SAKAI_SESSION_HISTORY.SESSION_USER AS USR "+
		             "FROM   SAKAI_SESSION_HISTORY, SAKAI_EVENT_HISTORY "+
		             "WHERE  SAKAI_SESSION_HISTORY.SESSION_ID = SAKAI_EVENT_HISTORY.SESSION_ID " +
		             "AND    SAKAI_EVENT_HISTORY.EVENT = '"+event+"' "+
		             "AND    TO_CHAR(SAKAI_EVENT_HISTORY.EVENT_DATE,'YYYY-MM') = '"+tmpDate+"' ";
		System.out.println("getUserLoginMonthlyHistory: "+select);

		try{
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String user = data.get("USR").toString();
    			System.out.println(">> 1 << "+user);

       			User userTmp = null;
    			try {
    				userTmp = userDirectoryService.getUser(user);
    				System.out.println(">> 2 << ");
    				if (null == userTmp.getType()) {
    					nullLogin = nullLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    					lecturerLogin = lecturerLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("student")) {
    					studentLogin = studentLogin + 1;
    				} else {
    					nullLogin = nullLogin + 1;
    				}
    			} catch (UserNotDefinedException e){
    				try {
    					userTmp = userDirectoryService.getUserByEid(user);
    					System.out.println(">> 3 << ");

    					if (null == userTmp.getType()) {
    						nullLogin = nullLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    						lecturerLogin = lecturerLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("student")) {
    						studentLogin = studentLogin + 1;
    					} else {
    						nullLogin = nullLogin + 1;
    					}
    				} catch (UserNotDefinedException ex) {
    					nullLogin = nullLogin + 1;
        			} catch (NullPointerException ex) {
        				nullLogin = nullLogin + 1;
    				} catch (OBJECT_NOT_EXIST ex) {
    					System.out.println("OBJECT NOT EXIST");
    					nullLogin = nullLogin + 1;
    				}
    			} catch (NullPointerException e) {
    				nullLogin = nullLogin + 1;
				} catch (OBJECT_NOT_EXIST ex) {
					System.out.println("OBJECT NOT EXIST");
					nullLogin = nullLogin + 1;
				}
    		}

    		System.out.println("VOOR insertUnisaMIS");
    		errorMessage = insertUnisaMIS("user.login.unique",forYearMonth,studentLogin,"gateway","S");
    		errorMessage = insertUnisaMIS("user.login.unique",forYearMonth,lecturerLogin,"gateway","L");
    		errorMessage = insertUnisaMIS("user.login.unique",forYearMonth,nullLogin,"gateway","A");
    		System.out.println("NA insertUnisaMIS");
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getUserLoginMonthlyHistory: Error occurred / "+ ex;
			//throw new Exception("MyUnisaStatsDAO: getUserLoginMonthlyHistory: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * Method: getUserLoginDaily
	 *  to get stats daily user logins
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getUserLoginAnnual(String event, String forDate) throws Exception{
		String errorMessage = "";
		int lecturerLogin = 0;
		int studentLogin = 0;
		int nullLogin = 0;
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		String select = "SELECT DISTINCT SAKAI_SESSION.SESSION_USER AS USR "+
						"FROM   SAKAI_SESSION "+
						"WHERE  TO_CHAR(SAKAI_SESSION.SESSION_START,'YYYY') = '"+forDate+"' "+
						"GROUP BY SAKAI_SESSION.SESSION_USER ";

		try{
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String user = "";
    			if (null == data.get("USR")) {
    				System.out.println("user null");
    			} else {
    				user = data.get("USR").toString();
    			}

       			User userTmp = null;
    			try {
    				userTmp = userDirectoryService.getUser(user);
    				if (null == userTmp.getType()) {
    					nullLogin = nullLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    					lecturerLogin = lecturerLogin + 1;
    				} else if (userTmp.getType().equalsIgnoreCase("student")) {
    					studentLogin = studentLogin + 1;
    				} else {
    					nullLogin = nullLogin + 1;
    				}
    			} catch (UserNotDefinedException e){
    				try {
    					userTmp = userDirectoryService.getUserByEid(user);

    					if (null == userTmp.getType()) {
    						nullLogin = nullLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("instructor")) {
    						lecturerLogin = lecturerLogin + 1;
    					} else if (userTmp.getType().equalsIgnoreCase("student")) {
    						studentLogin = studentLogin + 1;
    					} else {
    						nullLogin = nullLogin + 1;
    					}
    				} catch (UserNotDefinedException ex) {
    					nullLogin = nullLogin + 1;
    				}
    			}
    		}

    		errorMessage = insertUnisaMIS("user.login.unique.annual",forDate+"-12-31",studentLogin,"gateway","S");
    		errorMessage = insertUnisaMIS("user.login.unique.annual",forDate+"-12-31",lecturerLogin,"gateway","L");
    		errorMessage = insertUnisaMIS("user.login.unique.annual",forDate+"-12-31",nullLogin,"gateway","A");
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getUserLoginAnnual: Error occurred / "+ ex;
			//throw new Exception("MyUnisaStatsDAO: getUserLoginMonthly: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * 5 Nov 2007 Sonette removed method as was only used to get history stats
	 * Method: getUserLoginMonthlyOrig
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	/**
	 * Method: getEventStats
	 *  to get stats for a certain event for a certain day.
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */
	public String getEventStatsMonthly(String event, String forDate, String dateType, String forSite, String userType) throws Exception{
		int eventStats = 0;
		String errorMessage;

		String select = "SELECT COUNT(*) AS A "+
		             "FROM   SAKAI_EVENT "+
		             "WHERE  EVENT = '"+event+"' "+
		             "AND    TO_CHAR(EVENT_DATE,'YY-MM') = '"+forDate+"'";

		try{
			eventStats = Integer.parseInt(this.querySingleValue(select,"A"));
			errorMessage = insertUnisaMIS(event,forDate+"-01",eventStats,forSite,userType);
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getEventStatsMonthly: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getEventStatsMonthly: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * 5 Nov 2007 Sonette removed method as was only used to get history stats
	 * Method: getEventStatsMonthlyTmp1
	 *  to get stats for a certain event for a certain day.
	 *  Using tables: SAKAI_EVENT_OLD1 AND SAKAI_SESSION_200707201729
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    forSite = stats for which site (gateway/admin)
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	/**
	 * Method: getEventStatsPerSite
	 *  to get stats for a certain event for a certain day PER SITE.
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getEventStatsPerSite(String event, String forDate, String dateType, String forUser) throws Exception{
		String errorMessage = "";

		String select = "SELECT REF,COUNT(*) AS A "+
		             "FROM   SAKAI_EVENT "+
		             "WHERE  EVENT = '"+event+"' "+
		             "AND    TO_CHAR(EVENT_DATE,'YYYY-MM-DD') = '"+forDate+"'"+
		             "GROUP BY REF";

		try{

    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			int eventStats = Integer.parseInt(data.get("A").toString());
    			String forSite= data.get("REF").toString();
    			errorMessage = unisaMISInsertOrUpdate(event,forDate,dateType,eventStats,forSite, forUser);
    		}
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getEventStatsPerSite: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getEventStatsPerSite: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}


	/**
	 * Method: unisaMISInsertOrUpdate
	 *  Must stats record be inserted or updated
	 *  If monthly: (insert/update) If new month, create new record, if existing month add stats to that months stats (Update)
	 *  If daily: do an insert
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats "YYYY-MM-DD"
	 *    dateType = "m"/"d" for store monthly or daily
	 *    eventStats = stats
	 *    forSite = for stats per site. (gateway, admin, coursecode)
	 * output:
	 * 	  doInsert = do an insert = true; do an update (and not an insert) = false
	 * @throws Exception
	 */
	private String unisaMISInsertOrUpdate(String event, String forDate, String dateType, int eventStats, String forSite, String forUser) throws Exception{

		String errorMsg = "";

		String[] tmp=forDate.split("-");
		if (dateType.equals("m")) {
			// Monthly
			// if not first day of month update UNISA_MIS
			if (tmp[2].equals("1")||tmp[2].equals("01")) {
				errorMsg = insertUnisaMIS(event,forDate,eventStats,forSite,forUser);
			} else {
			// if first day of month insert UNISA_MIS
				errorMsg = updateUnisaMIS(event,forDate,eventStats,forSite,forUser);
			}
		} else {
			// Daily
			errorMsg = insertUnisaMIS(event,forDate,eventStats,forSite,forUser);

		}

		return errorMsg;

	}

	/**
	 * Method: insertUnisaMIS
	 *  Insert a record into UNISA_MIS
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    eventStats = stats
	 * output:
	 * 	  errorMsg = counted stats
	 * @throws Exception
	 */
	String insertUnisaMIS(String event, String forDate, int eventStats, String forSite, String userType) throws Exception{

		String errorMsg = "";
		String eventStatsStr = ""+eventStats;

		if (userType.equals(" ")) {
			userType = "A";
		}

		String sqlInsert = "INSERT INTO UNISA_MIS(DATE_COUNTED,USER_TYPE,CATEGORY, ACTION, MIS_VALUE) "+
		                   "VALUES(TO_DATE('"+forDate+"','YYYY-MM-DD'),'"+userType+"','"+forSite+"','"+event+"',"+eventStatsStr+")";

		System.out.println("sqlInsert: "+sqlInsert);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sqlInsert,new Object[] {});
			errorMsg = sqlInsert;
			//System.out.println("insertUnisaMIS: "+event+" "+eventStats+" "+forSite+"forDate "+forDate);
		} catch (Exception ex) {
			throw new Exception("MyUnisaStatsDAO: insertUnisaMIS (Insert): Error occurred / "+ ex,ex);
		}

		return errorMsg;
	}


	/**
	 * Method: updateUnisaMIS
	 *  Update UNISA_MIS record
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    eventStats = stats
	 * output:
	 * 	  errorMsg = counted stats
	 * @throws Exception
	 */
	private String updateUnisaMIS(String event, String forDate, int eventStats, String forSite, String userType) throws Exception{

		String errorMsg="";
		int eventStatsTmp;
		String eventStatsTmpStr = "";

		String[] tmpDate = forDate.split("-");
		String newDate = tmpDate[0]+"-"+tmpDate[1]+"-01";

		if (userType.equals(" ")) {
			userType = "A";
		}

		String select = "SELECT MIS_VALUE "+
        		"FROM   UNISA_MIS "+
        		"WHERE  ACTION = '"+event+"' "+
        		"AND    TO_CHAR(DATE_COUNTED,'YYYY-MM-DD') = '"+newDate+"' ";
		if (forSite.length()>1) {
			select = select +" AND    CATEGORY = '"+forSite+"' ";
		}
		//System.out.println("sqlUpdate:"+select);

		try{
			//System.out.println("SELECT: "+select+" ");
			eventStatsTmpStr = this.querySingleValue(select,"MIS_VALUE");
			if ((null==eventStatsTmpStr)||(eventStatsTmpStr.equals(""))) {
				eventStatsTmp = 0;
			} else {
				eventStatsTmp = Integer.parseInt(eventStatsTmpStr);
			}
			//eventStatsTmp = Integer.parseInt(this.querySingleValue(select,"VALUE"));
		} catch (Exception ex) {
			//System.out.println("ERROR: "+ex);
			throw new Exception("MyUnisaStatsDAO: updateUnisaMIS (select="+select+"): (event="+event+") Error occurred / "+ ex,ex);
		}

		//System.out.println("eventStatsTmp +"+eventStatsTmp+" + "+eventStats);

		eventStatsTmp = eventStatsTmp + eventStats;

		if ((null==eventStatsTmpStr)||(eventStatsTmpStr.equals(""))) {
			errorMsg = insertUnisaMIS(event,newDate,eventStats,forSite,userType);
		} else {

			String sqlUpdate = "UPDATE UNISA_MIS "+
							"SET    MIS_VALUE = "+eventStatsTmp+" "+
							"WHERE  DATE_COUNTED = TO_DATE('"+newDate+"','YYYY-MM-DD') "+
							"AND    ACTION = '"+event+"' "+
							"AND    USER_TYPE = '"+userType+"' "+
							"AND    CATEGORY = '"+forSite+"' ";
			//System.out.println("update: "+sqlUpdate);



			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sqlUpdate);
			} catch (Exception ex) {
				throw new Exception("MyUnisaStatsDAO: updateUnisaMIS (sqlUpdate="+sqlUpdate+"): (event="+event+") Error occurred / "+ ex,ex);
			}
		}

		return errorMsg;
	}

	/**
	 * Method: updateUnisaMIS2
	 *  Update UNISA_MIS record
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 *    eventStats = stats
	 * output:
	 * 	  errorMsg = counted stats
	 * @throws Exception
	 */
	private String updateUnisaMIS2(String event, String forDate, int eventStats, String forSite, String userType) throws Exception{
		String errorMsg="";

		//String[] tmpDate = forDate.split("-");
		//String newDate = tmpDate[0]+"-"+tmpDate[1]+"-01";

		if (userType.equals(" ")) {
			userType = "A";
		}

		String sqlUpdate = "UPDATE UNISA_MIS "+
		                   "SET    MIS_VALUE = "+eventStats+" "+
		                   "WHERE  DATE_COUNTED = TO_DATE('"+forDate+"','YYYY-MM-DD') "+
		                   "AND    ACTION = '"+event+"' "+
		                   "AND    USER_TYPE = '"+userType+"' "+
		                   "AND    CATEGORY = '"+forSite+"' ";
		//System.out.println("sqlUpdate:"+sqlUpdate);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sqlUpdate);
			errorMsg = errorMsg + ">> "+sqlUpdate;
		} catch (Exception ex) {
			errorMsg = "MyUnisaStatsDAO: updateUnisaMIS2 (sqlUpdate="+sqlUpdate+"): (event="+event+") Error occurred / "+ ex;
			//throw new Exception("MyUnisaStatsDAO: updateUnisaMIS2 (sqlUpdate="+sqlUpdate+"): (event="+event+") Error occurred / "+ ex,ex);
		}

		return errorMsg;
	}


	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List<?> queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator<?> i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}



	/**
	 * Method: getEventStatsPerSite
	 *  to get stats for a certain event for a certain day PER SITE.
	 *
	 * input:
	 *    event = event code
	 *    forDate = relevant date for stats
	 *    dateType = "m"/"d" for store monthly or daily
	 * output:
	 * 	  stats = counted stats
	 * @throws Exception
	 */

	public String getEventStatsPerSiteMonthly(String event, String forDate, String dateType, String forUser) throws Exception{
		String errorMessage = "";

		String select = "SELECT REF,COUNT(*) AS A "+
		             "FROM   SAKAI_EVENT "+
		             "WHERE  EVENT = '"+event+"' "+
		             "AND    TO_CHAR(EVENT_DATE,'YYY-MM') = '"+forDate+"' "+
		             "GROUP BY REF";
		//System.out.println("getEventStatsPerSiteMonthly: "+select);

		try{

    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			int eventStats = Integer.parseInt(data.get("A").toString());
    			String forSite= data.get("REF").toString();
    			errorMessage = insertUnisaMIS(event,forDate+"-01",eventStats,forSite,"A");
    		}
		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getEventStatsPerSite: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getEventStatsPerSite: Error occurred / "+ ex,ex);
		}

       return errorMessage;
	}

	/**
	 * Method: myUnisaAccountExist
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 * input: studentNr
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public String getJoinActivationStatistics(String forDate) throws Exception{
		int eventStats = 0;
		String errorMessage = "";

		String joinedActiveSQL = "SELECT count(*) as JA " +
        						 "FROM   JOIN_ACTIVATION " +
        						 "WHERE  ACT_STATUS = 'Y' " +
        						 "AND    TO_CHAR(JOIN_DATE,'YYYY-MM-DD') = '"+forDate+"' ";

		try{
			eventStats = Integer.parseInt(this.querySingleValue(joinedActiveSQL,"JA"));

		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getJoinActivationStatistics: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getJoinActivationStatistics: Error occurred / "+ ex,ex);
		}

		// insert/update table with stats
		errorMessage = insertUnisaMIS("joined.students",forDate,eventStats,"gateway","S");

       return errorMessage;
	}

	/**
	 * Method: myUnisaAccountExist
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 * input: studentNr
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public String getJoinActivationStatisticsTmp1(String forDate) throws Exception{
		int eventStats = 0;
		String errorMessage = "";

		String joinedActiveSQL = "SELECT count(*) as JA " +
        						 "FROM   JOIN_ACTIVATION " +
        						 "WHERE  ACT_STATUS = 'Y' " +
        						 "AND    TO_CHAR(JOIN_DATE,'YYYY-MM-DD') = '"+forDate+"' ";

		try{
			eventStats = Integer.parseInt(this.querySingleValue(joinedActiveSQL,"JA"));

		} catch (Exception ex) {
			errorMessage = "MyUnisaStatsDAO: getJoinActivationStatistics: Error occurred / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getJoinActivationStatistics: Error occurred / "+ ex,ex);
		}

		// insert/update table with stats
		errorMessage = updateUnisaMIS2("joined.students",forDate,eventStats,"gateway","s");

       return errorMessage;
	}

/*	public void deleteEvent(String tmpDate,String action) throws Exception{

		String sql1 = "DELETE FROM UNISA_MIS " +
					  "WHERE  ACTION = '"+action+"' "+
					  "AND    TO_CHAR(DATE_COUNTED,'YYYY-MM-DD') = '"+tmpDate+"'";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MyUnisaStatsDAO: deleteEvent (Delete): Error occurred /"+ ex,ex);
		}
	}*/

	public void deleteEventAll(String action) throws Exception{

		String sql1 = "DELETE FROM UNISA_MIS " +
					  "WHERE  ACTION = '"+action+"' ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MyUnisaStatsDAO: deleteEventAll (Delete): Error occurred /"+ ex,ex);
		}
	}

	//public void deleteEventsForJuly() throws Exception{
	/* 4 Sep 2007: Change delete from July to June - onceoff delete for recount of month stats */
	/* 5 Sep 2007: Change delete from June to August - onceoff delete for recount of month stats */
	public void deleteEventsForAug() throws Exception{

		String sql1 = "DELETE FROM UNISA_MIS " +
					  "WHERE  ACTION NOT IN ('user.login','joined.students') "+
					  "AND    DATE_FORMAT(DATE_COUNTED,'YYYY-MM') = '2007-08' ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MyUnisaStatsDAO: deleteEventsForAug (Delete): Error occurred /"+ ex,ex);
		}
	}
}