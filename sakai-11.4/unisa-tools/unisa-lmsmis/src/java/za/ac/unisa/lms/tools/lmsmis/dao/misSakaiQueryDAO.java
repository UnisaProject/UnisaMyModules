package za.ac.unisa.lms.tools.lmsmis.dao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class misSakaiQueryDAO extends SakaiDAO{
	//method
	public ArrayList getAnnualStats(String event,String[] forYear, String category, String userType)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;
		String tmp = "";

		String select = "";
		for (int i = 0; i < forYear.length; i++) {
			select = "SELECT ROUND(SUM(MIS_VALUE)) AS TOTAL "+
					 "FROM UNISA_MIS "+
					 "WHERE ACTION='"+event+"' "+
					 "AND TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"' "+
					 "AND CATEGORY = '"+category+"' ";


			if ((null != userType)||(!userType.equals(""))) {
				select = select+" AND UPPER(USER_TYPE) = UPPER('"+userType+"') ";

			}

			select = select+"GROUP BY TO_CHAR(DATE_COUNTED,'YYYY') ";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				List list = jdt.queryForList(select);
				Iterator j = list.iterator();
				String stats = "0";

				while (j.hasNext()){

					ListOrderedMap data = (ListOrderedMap) j.next();
					float tmpTotal = Float.parseFloat(data.get("Total").toString());
                    int tmpTotal2 = (int) tmpTotal;
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
				    String s = formatter.format(tmpTotal2);  // -001235
				    stats = s.replace(',',' ');
					count++;
				}// end while
				statsList.add(new org.apache.struts.util.LabelValueBean(forYear[i], stats));
			} catch (Exception ex){
				throw new Exception ("misSakaiQueryDAO: selectgetAnnualStats: Error occurred /"+ ex,ex);
			}// end try
		}
	 return statsList;
	}
	public ArrayList getmisAdminAnnual(String event,String[] forYear, String category)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;

		String select = "";
		for (int i = 0; i < forYear.length; i++) {
			select = "SELECT TO_CHAR(DATE_COUNTED,'YYYY') YearMonth, ROUND(SUM(MIS_VALUE)) AS Total "+
					 "FROM UNISA_MIS "+
					 " WHERE ACTION='"+event+"' "+
					 " AND TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"' ";

			if (category.length() >=1) {
				select = select +" AND CATEGORY = '"+category+"' ";
			}
			select = select + " GROUP BY TO_CHAR(DATE_COUNTED,'YYYY') ";



			try{
                JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
                List list = jdt.queryForList(select);
                Iterator j = list.iterator();
                String stats = "0";

                while (j.hasNext()){

                    ListOrderedMap data = (ListOrderedMap) j.next();
					float tmpTotal = Float.parseFloat(data.get("Total").toString());
                    int tmpTotal2 = (int) tmpTotal;
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
				    String s = formatter.format(tmpTotal2);  // -001235
				    stats = s.replace(',',' ');
					count++;
                }// end while
                statsList.add(new org.apache.struts.util.LabelValueBean("passwordChanges", stats));
            } catch (Exception ex){
                throw new Exception ("misSakaiQueryDAO: selectgetMonthlyStats: Error occurred /"+ ex,ex);
            }// end try
		}
	 return statsList;
	}
	public ArrayList getMonthlyStats(String event,String[] forYear, String category)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;
		String currentMonth = "";
		Calendar rightNow = Calendar.getInstance();
		currentMonth = new Integer (rightNow.get(Calendar.MONTH)).toString();
		String select = "";
		for (int i = 1; i <= 12; i++) {
			select = "SELECT TO_CHAR(DATE_COUNTED,'YYYY-MM') YearMonth, ROUND(SUM(MIS_VALUE))  AS Total "+
					 "FROM UNISA_MIS "+
					 "WHERE ACTION='"+event+"' ";
			if (i < 10){
				select = select+" AND TO_CHAR(DATE_COUNTED,'YYYY-MM') = '"+forYear[0]+"-0"+i+"' ";
			} else {
				select = select+" AND TO_CHAR(DATE_COUNTED,'YYYY-MM') = '"+forYear[0]+"-"+i+"' ";
			}
			if (category.length() >=1) {
				select = select +" AND CATEGORY = '"+category+"' ";
			}
			select = select + " GROUP BY TO_CHAR(DATE_COUNTED,'YYYY-MM') ";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				List list = jdt.queryForList(select);
				Iterator j = list.iterator();
				String stats = "0";

				while (j.hasNext()){

					ListOrderedMap data = (ListOrderedMap) j.next();
					float tmpTotal = Float.parseFloat(data.get("Total").toString());
                    int tmpTotal2 = (int) tmpTotal;
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
				    String s = formatter.format(tmpTotal2);  // -001235
				    stats = s.replace(',',' ');
					count++;

				}// end while
				if (new Integer(forYear[0]).intValue() == rightNow.get(Calendar.YEAR)){
					if (i > new Integer(currentMonth).intValue()+1){
						stats = "";
					}
				}
				statsList.add(new org.apache.struts.util.LabelValueBean("passwordChanges", stats));
			} catch (Exception ex){
				throw new Exception ("misSakaiQueryDAO: selectgetMonthlyStats: Error occurred /"+ ex,ex);
			}// end try
		}
	 return statsList;
		}
	public ArrayList getAnnualStats1(String event,String[] forYear, String category, String userType)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;
		String select = "";
		for (int i = 0; i < forYear.length; i++) {
			select = "SELECT MAX(MIS_VALUE) AS A "+
			 "FROM UNISA_MIS "+
			 "WHERE TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"' "+
			 "AND CATEGORY = '"+category+"' "+
			 "AND ACTION='"+event+"' ";
			 if ((null != userType)||(!userType.equals(""))) {
					select = select+" AND UPPER(USER_TYPE) = UPPER('"+userType+"') ";
			System.out.println(select);
			 }
			 try{
					JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
					List list = jdt.queryForList(select);
					Iterator j = list.iterator();
					String stats = "0";

					while (j.hasNext()){

						ListOrderedMap data = (ListOrderedMap) j.next();
						float tmpTotal = Float.parseFloat(data.get("A").toString());
	                    int tmpTotal2 = (int) tmpTotal;
						NumberFormat formatter = new DecimalFormat("###,###,###,###");
					    String s = formatter.format(tmpTotal2);  // -001235
					    stats = s.replace(',',' ');
						count++;
					}// end while
					statsList.add(new org.apache.struts.util.LabelValueBean(forYear[i], stats));
				} catch (Exception ex){
					throw new Exception ("misSakaiQueryDAO: selectgetAnnualStats1: Error occurred /"+ ex,ex);
				}// end try

		}

		return statsList;
		}
	/*public ArrayList getAnnualStats2(String event,String[] forYear, String category, String userType)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;
		String select = "";
		for (int i = 0; i < forYear.length; i++) {
			select = "SELECT MIS_VALUE "+
			 "FROM UNISA_MIS "+
			 "WHERE DATE_COUNTED = (SELECT MAX(DATE_COUNTED) "+
			 						"FROM UNISA_MIS "+
			 						"WHERE ACTION = '"+event+"' AND TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"') "+
			 "AND CATEGORY = '"+category+"' "+
			 "AND TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"' "+
			 "AND ACTION='"+event+"' ";
			 if ((null != userType)||(!userType.equals(""))) {
					select = select+" AND UPPER(USER_TYPE) = UPPER('"+userType+"') ";
			System.out.println(select);
			 }
			 try{
					JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
					List list = jdt.queryForList(select);
					Iterator j = list.iterator();
					String stats = "0";

					while (j.hasNext()){

						ListOrderedMap data = (ListOrderedMap) j.next();
						float tmpTotal = Float.parseFloat(data.get("MIS_VALUE").toString());
	                    int tmpTotal2 = (int) tmpTotal;
						NumberFormat formatter = new DecimalFormat("###,###,###,###");
					    String s = formatter.format(tmpTotal2);  // -001235
					    stats = s.replace(',',' ');
						count++;
					}// end while
					statsList.add(new org.apache.struts.util.LabelValueBean(forYear[i], stats));
				} catch (Exception ex){
					throw new Exception ("misSakaiQueryDAO: selectgetAnnualStats2: Error occurred /"+ ex,ex);
				}// end try

		}

		return statsList;
		}*/
		public ArrayList getmisTeachMonthly(String event,String[] forYear)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;
		String currentMonth = "";
		Calendar rightNow = Calendar.getInstance();
		currentMonth = new Integer (rightNow.get(Calendar.MONTH)).toString();
		String select = "";
		String yearmonth = "";
		for (int i = 1; i <= 12; i++) {
			select = "SELECT TO_CHAR(DATE_COUNTED,'YYYY-MM') YearMonth, ROUND(SUM(MIS_VALUE)) AS Total "+
					 "FROM UNISA_MIS "+
					 "WHERE ACTION='"+event+"' ";
			if (i < 10){
				select = select+" AND TO_CHAR(DATE_COUNTED,'YYYY-MM') = '"+forYear[0]+"-0"+i+"' ";
			} else {
				select = select+" AND TO_CHAR(DATE_COUNTED,'YYYY-MM') = '"+forYear[0]+"-"+i+"' ";
			}
			select = select +" GROUP BY TO_CHAR(DATE_COUNTED,'YYYY-MM') ";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				List list = jdt.queryForList(select);
				Iterator j = list.iterator();
				String stats = "0";

				while (j.hasNext()){

					ListOrderedMap data = (ListOrderedMap) j.next();
					float tmpTotal = Float.parseFloat(data.get("Total").toString());
					yearmonth = (data.get("YearMonth").toString());
                    int tmpTotal2 = (int) tmpTotal;
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
				    String s = formatter.format(tmpTotal2);  // -001235
				    stats = s.replace(',',' ');
					count++;
				}// end while
				if (new Integer(forYear[0]).intValue() == rightNow.get(Calendar.YEAR)){
					if (i > new Integer(currentMonth).intValue()+1){
						stats = "";
					}
				}
				if (event.equals("content.revise")&&((yearmonth.equals("2006-01"))||(yearmonth.equals("2006-02"))||(yearmonth.equals("2006-03")))){
					stats = "--";

				}
				statsList.add(new org.apache.struts.util.LabelValueBean("passwordChanges", stats));
			} catch (Exception ex){
				throw new Exception ("misSakaiQueryDAO: selectgetmisTeachAnnual: Error occurred /"+ ex,ex);
			}// end try
		}
	 return statsList;
	}
	public ArrayList getmisTeachAnnual(String event,String[] forYear)throws Exception{
		ArrayList statsList = new ArrayList();
		int count = 0;

		String select = "";
		for (int i = 0; i < forYear.length; i++) {
			select = "SELECT TO_CHAR(DATE_COUNTED,'YYYY') YearMonth, ROUND(SUM(MIS_VALUE)) AS Total "+
					 "FROM UNISA_MIS "+
					 "WHERE ACTION='"+event+"' "+
					 "AND TO_CHAR(DATE_COUNTED,'YYYY') = '"+forYear[i]+"' "+
					 "GROUP BY TO_CHAR(DATE_COUNTED,'YYYY') ";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				List list = jdt.queryForList(select);
				Iterator j = list.iterator();
				String stats = "0";

				while (j.hasNext()){

					ListOrderedMap data = (ListOrderedMap) j.next();
					float tmpTotal = Float.parseFloat(data.get("Total").toString());
                    int tmpTotal2 = (int) tmpTotal;
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
				    String s = formatter.format(tmpTotal2);  // -001235
				    stats = s.replace(',',' ');
					count++;
				}// end while
				statsList.add(new org.apache.struts.util.LabelValueBean("passwordChanges", stats));
			} catch (Exception ex){
				throw new Exception ("misSakaiQueryDAO: selectgetAnnualStats: Error occurred /"+ ex,ex);
			}// end try
		}
	 return statsList;
	}
}
