package za.ac.unisa.lms.tools.liberesource.dao;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.MSSQLServerDAO;
import za.ac.unisa.lms.tools.liberesource.forms.Alphabet;
import za.ac.unisa.lms.tools.liberesource.forms.ResourceForm;
import za.ac.unisa.lms.tools.liberesource.forms.LibmainForm;
import za.ac.unisa.lms.tools.liberesource.forms.Tab;


public class ResourceDAO extends MSSQLServerDAO {


	private static final String Map = null;
	private static final String ip1 = "163.200";
	//private static final String ip1 = "127"; localhost
	private static final String ip2 = "10.1.4.3";
	private int placementID = 0;
	private String startAlphabet = null;
	private int subjectID = 0;
	private int vendorID = 0;

	public ResourceDAO(String database) {
		super(database);
		
	}
	/**
	 * This method will select all the subject data

	*/
	public ArrayList selectSubjectList() throws Exception {
		/*
		 * CREATE TABLE LIBSUBJ
(SUBJID INT PRIMARY KEY,
SUBJECT VARCHAR(50) NOT NULL,
ENABLED  CHAR);

		 */
		ArrayList subjectList = new ArrayList();
				
		String select = " SELECT SUBJID, SUBJECT"+
						" FROM   LIBSUBJ "+
						" WHERE ENABLED = 'Y'"+
						" ORDER BY SUBJECT ";
						
					
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
			ListOrderedMap data;
    		while (j.hasNext()) {
    			    data = (ListOrderedMap) j.next();
    				
        			ResourceForm form = new ResourceForm();
        			form.setSubjectId(Integer.parseInt(data.get("SUBJID").toString()));
        			form.setSubject(data.get("SUBJECT").toString());
        			//form.setEnabled(data.get("ENABLED").toString());
        			
        			subjectList.add(form);
    			

    			   			
    		 // end while

    	} 
    	}catch (Exception ex) {
    		throw new Exception("ResourcesDAO: selectSubjectList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return subjectList;
	}
	
	/**
	 * This method will select all the vendors from the database
	 *
	 * @input typeDate OPTIONLIST = arraylist of labelValueBean/ FORMLIST = arraylist of vendorForm
	*/
	public ArrayList selectVendorList() throws Exception {
		
		
		ArrayList vendorList = new ArrayList();
		
				
		String select = " SELECT VENDORID, VENDORNAME" +
						" FROM   LIBVENDOR "+
						" WHERE ENABLED = 'Y'"+
						" ORDER BY VENDORNAME";	
		
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			ListOrderedMap data;
			
    		while (j.hasNext()) {
    			
    			    data = (ListOrderedMap) j.next();
	    			ResourceForm form = new ResourceForm();
	    			form.setVendorId(Integer.parseInt(data.get("VENDORID").toString()));
	    			form.setVendorName(data.get("VENDORNAME").toString());
	    			
	    			
	    			vendorList.add(form);
    			}
    			
    		} // end while

    	 catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	 }// end try
    	
    	return vendorList;
	}
	
	//select for specific subject  
	
	public ArrayList selectSpecificSubject( String subId,String startChar, String ipAddress ) throws Exception {
		
		
		ArrayList vendorList = new ArrayList();
		
		
		String select = " SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL,"+ 
                        " ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL,"+
                        " ISNULL(a.ADDINFO,'') AS ADDINFO,"+
                        "  ISNULL(a.VENDORID,'') AS VENDORID,"+
                        " ISNULL(a.TRAINING,'') AS TRAINING,"+
                        " ISNULL(a.NEWSID,'') AS NEWSID, "+
                        " ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE,"+
                        " ISNULL(a.NEWSURL,'') AS NEWSURL,  "+
                        " ISNULL(d.VENDORNAME,'') AS VENDORNAME,"+ 
                        " ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL,"+
                        " ISNULL(d.ONCAMPUSURL,'') AS VENONURL,"+
                        " ISNULL(d.LOGOURL,'') AS LOGOURL,"+
                        " isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT, "+
    					" isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC , (SELECT TEXTDESC FROM LIBTXT WHERE TXTID = a.TXTID) AS CONTENTTYPE, a.ALERT, a.ACCESSNOTE, a.REF_MANAGEMENT " +
                        " FROM    LIBRESOURCE a, LIBVENDOR d, LIBRESSUBJ e"+
                        " WHERE  e.RESID = a.RESID"+
                        " and    e.SUBJID ="+subId+
                        " and    a.VENDORID = d.VENDORID "+
                        " and ISNULL(a.ENABLED,'Y') = 'Y'";
						
						if(!(startChar == null)){
							select += " AND     upper(a.RESNAME) like '"+startChar+"%'";
						}
						select += " order by a.resname";
                       /* " AND     upper(a.RESNAME) like '"+startChar+"%'"+
                        select += " AND    1 <= (SELECT COUNT(*)"+
                        " FROM LIBRESPLA c "+
                        " WHERE  c.RESID = a.RESID"+
                        " AND    (GETDATE() > STARTDATE"+ 
                        " OR     STARTDATE IS NULL)"+
                        " AND    (GETDATE() < ENDDATE"+ 
                        " OR ENDDATE IS NULL)) " +
                        "order by a.resname";*/
		//System.out.println(">>>>> selectSpecificSubject "+select);
	
		String oncampus = "";
		String offcampus = "";
		Map news = null;
		ArrayList subcover= null;
		ArrayList placement= null;
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			ListOrderedMap data;
    		while (j.hasNext()) {
    			data = (ListOrderedMap) j.next();

    			ResourceForm form = new ResourceForm();
	    		form.setResName(data.get("RESNAME").toString());
    			form.setVresname(data.get("VENDORNAME").toString());
	    		try{
	    			String vendorId = data.get("VENDORID").toString();
	    			form.setUrl(getLogo(vendorId));
    			}catch(NullPointerException ne){
    				
    			}
	    	
    			String resid = data.get("RESID").toString(); 
	    
    			placement = getPlacement(resid,form,"");
	    		try{
	    			Iterator m = placement.iterator();
	    			String tempPlacement="";
	    			while(m.hasNext()){
	    				tempPlacement= tempPlacement+m.next().toString()+", ";
	    	   		}
	    				
    				form.setPlacement(tempPlacement);
    			}catch(NullPointerException ne){
    				
    			}
    			//form.setLogo(getLogo1(vendorId));
    			try{
    				form.setAddInfo(data.get("ADDINFO").toString());
    			}catch(NullPointerException ne){
   			  		  
    			}
    			try{
    				form.setResdescr(data.get("RESDESCR").toString());
    			}catch(NullPointerException ne){
    				
    			}
	   			 
	   			try{
	    			if(ipAddress.substring(0, 7).equals(ip1)||ipAddress.substring(0,8).equals(ip2)){
	    				 
	    				form.setCampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());
	    			}else{
	    				form.setCampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString());
	    			}
	   			}catch(NullPointerException ne){
			  		  
	   			}
	   			/* remove temp for speed problems Sonette 
	   			subcover = getSubCovered(resid);	
	   			try{   	   			
		   			if(subcover.size()<=5){
		   				String tempSubCovered="";
		   				Iterator k = subcover.iterator();
		   				while(k.hasNext()){
		   					tempSubCovered = tempSubCovered+"\r\n"+k.next().toString();
		   			
		   				}
		   				form.setSubjectcover(tempSubCovered);
		   			}else{
		   				form.setSubjectcover("Multi disciplinary ");
		   			}
	   			}catch(NullPointerException ne){
	   				
	   			}
	   			*/
	   			form.setSubjectcover("Multi disciplinary ");
	  
	   			try{
	   				if(!(data.get("TRAINING").toString().equals(" "))){
	   				form.setTraining(data.get("TRAINING").toString());
	   				}
	   			}catch(NullPointerException ne){
			  		  
	   			}
	    		
	    		try{	
	    			news = getNews(resid);
	    			form.setNewsUrl(news.get("newsUrl").toString());
	    			form.setNewsTitle(news.get("newsTitle").toString());
	    		}catch(NullPointerException ne){
	    			
	    		}
	    		
	    		try{
				   	form.setAlert(data.get("ALERT").toString());
	    		}catch(NullPointerException ne){
	    		}
	    		
	    	/*	try{
				   	form.setAccessNote(data.get("ACCESSNOTE").toString());
	   			} catch(NullPointerException ne){
	   			}*/
	    		
	    		if(!(data.get("ACCESSNOTE").toString().equals("n/a"))){
	    			form.setAccessNote(data.get("ACCESSNOTE").toString());
	    			//System.out.println("setting access note : "+data.get("ACCESSNOTE").toString());
	    		}
	   			
	    		try{
				   	form.setRfManagement(data.get("REF_MANAGEMENT").toString());
	   			} catch(NullPointerException ne){
	   			}
	   			
	    			
	    		form.setViewPassword(getPassword(resid));
	    		form.setHighlight(data.get("HIGHLIGHT").toString());
	    		form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
	    		form.setContentType(data.get("CONTENTTYPE").toString());
	    		vendorList.add(form);
    		}// end while
    			
		}catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectSpecificSubject(): Error occurred / "+ ex,ex);
    	}// end try
    	
    	return vendorList;
	}

	
	/** select for specific vendor */ 
	public ArrayList selectSpecificVendor( String venId, String startChar, String ipAddress ) throws Exception {

		ArrayList vendorList = new ArrayList();
		
		String select =  " SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'')AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL,"+
	                     " ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL,"+
	                     " ISNULL(a.ADDINFO,'') AS ADDINFO,"+
	                     " ISNULL(a.TRAINING,'') AS TRAINING,"+
	                     " ISNULL(a.NEWSID,'') AS NEWSID,"+ 
	                     " ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE,"+
	                     " ISNULL(a.NEWSURL,'') AS NEWSURL,"+  
	                     " ISNULL(d.VENDORNAME,'') AS VENDORNAME,"+ 
	                     " ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL,"+
	                     " ISNULL(d.ONCAMPUSURL,'') AS VENONURL,"+	                     
	                     " ISNULL(d.LOGOURL,'') AS LOGOURL,"+
	                     " ISNULL(d.LOGOFILE,'') AS LOGOFILE, "+
                         " isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT, "+
         				 " isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC, (SELECT TEXTDESC FROM LIBTXT WHERE TXTID = a.TXTID) AS CONTENTTYPE, a.ALERT, a.ACCESSNOTE, a.REF_MANAGEMENT " +
	                     " FROM    LIBRESOURCE a, LIBVENDOR d"+
	                     " WHERE  a.VENDORID = "+venId+
	                     " AND d.VENDORID = a.VENDORID "+
	                     " and ISNULL(a.ENABLED,'Y') = 'Y'";
		
						if(!(startChar == null)){
							select += " AND     upper(a.RESNAME) like '"+startChar+"%'";
						}
						select += " order by a.resname";
	                    // " AND     upper(a.RESNAME) like '"+startChar+"%'"+
	                    
	                     /*" AND    1 <= (SELECT COUNT(*)"+
	                     " FROM LIBRESPLA c "+
	                     " WHERE  c.RESID = a.RESID"+
	                     " AND    (GETDATE() > STARTDATE"+ 
	                     " OR     STARTDATE IS NULL)"+
	                     " AND    (GETDATE() < ENDDATE"+ 
	                     " OR ENDDATE IS NULL))" +
	                     " order by a.resname"; */
		//System.out.println(">>>>> selectSpecificVendor "+select);

		String sub = "http://library.unisa.ac.za/infoweb/applications/inforesources/";
		String oncampus = "";
		String offcampus = "";
		String logo = "";
		String url = "";
		String vlogo;
		String resId ;
		ArrayList subcover= null;
		Map news = null;
		ArrayList placement= null;
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			ListOrderedMap data;
    		while (j.hasNext()) {
    			data = (ListOrderedMap) j.next();
    			
    			ResourceForm form = new ResourceForm();
    			logo =   data.get("LOGOFILE").toString();
    		    url =  data.get("LOGOURL").toString();
    		    vlogo = sub+url+logo;
    		    form.setVurl(vlogo);
    			form.setVresname(data.get("RESNAME").toString());
    		//	form.setVresname(data.get("VENDORNAME").toString());
	    		try{
	    			form.setUrl(getLogo(venId));
    			}catch(NullPointerException ne){
    				
    			}
    			
    			resId = data.get("RESID").toString();
    			//form.setVplacement(getPlacement(resId));
    			placement = getPlacement(resId,form,"");
    			try{
    				Iterator m = placement.iterator();
		    		String tempPlacement="";
		    		while(m.hasNext()){
		    			tempPlacement= tempPlacement+m.next().toString()+", ";
		    	   	}
    				form.setVplacement(tempPlacement);
    			}catch(NullPointerException ne){
	    				
    			}
    			form.setVaddInfo(data.get("ADDINFO").toString());
    			form.setVresDescr(data.get("RESDESCR").toString());
    			try{
    				if(ipAddress.substring(0, 7).equals(ip1)||ipAddress.substring(0,8).equals(ip2)){
	    				form.setCampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());
	    			}else{
	    				form.setCampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString()); 
	    			
	    			}
    			
	   			}catch(NullPointerException ne){
				  		  
	   			}
	    			
		   		try{
		   			if(!(data.get("TRAINING").toString().equals(" "))){
		   				form.setVtraining(data.get("TRAINING").toString());
		   			}
	   			}catch(NullPointerException ne){
	   			}
	   			subcover = getSubCovered(resId);	
			   	try{
		   			if(subcover.size()<=5){
		   				String tempSubCovered="";
		   				Iterator k = subcover.iterator();
		   				while(k.hasNext()){
		   					tempSubCovered=tempSubCovered+"\r\n"+k.next().toString();
		   					//form.setSubjectcover(k.next().toString());
			   			}
			   			form.setSubjectcover(tempSubCovered);
			   		}else{
			   			form.setSubjectcover("Multi disciplinary");
			   		}
			   	}catch(NullPointerException ne){
			   			
			   	}
			   	try{	
			   		news = getNews(resId);
				    form.setNewsUrl(news.get("newsUrl").toString());
				    form.setNewsTitle(news.get("newsTitle").toString());
			   	}catch(NullPointerException ne){
				   			
			   	}
			   	
			   	try{
				   	form.setAlert(data.get("ALERT").toString());
			   	}
			   	catch(NullPointerException ne){
			   	}
	    		
			   /*	try{
				   	form.setAccessNote(data.get("ACCESSNOTE").toString());
	   			} catch(NullPointerException ne){
	   			}*/
			   	
			   	if(!(data.get("ACCESSNOTE").toString().equals("n/a"))){
	    			form.setAccessNote(data.get("ACCESSNOTE").toString());
	    			//System.out.println("setting access note : "+data.get("ACCESSNOTE").toString());
	    		}
			   	
	    		try{
				   	form.setRfManagement(data.get("REF_MANAGEMENT").toString());
	   			} catch(NullPointerException ne){
	   			}
	   			
			   	form.setViewPassword(getPassword(resId));	
			   	form.setHighlight(data.get("HIGHLIGHT").toString());
			   	form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
			   	form.setContentType(data.get("CONTENTTYPE").toString());
			   	
	    		vendorList.add(form);
    		}// end while
    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	}// end try
    	
    	return vendorList;
	}
	
	
	
	
	public ArrayList selectSpecificdb(String placeId,String startChar, String ipAddress) throws Exception {
		
		ArrayList vendorList = new ArrayList();
		//System.out.println("***** PlaceId = "+placeId+" startChar = "+startChar+" ipAddress = "+ipAddress);
		setPlacementID(Integer.parseInt(placeId));
		
		removeSpaces();

		String select = " SELECT z.RESID, z.RESNAME,ISNULL(z.RESDESCR,'') AS RESDESCR, ISNULL(z.ONCAMPUSURL,'') AS ONCAMPUSURL,"+  
	                    " ISNULL(z.OFFCAMPUSURL,'') AS OFFCAMPUSURL,"+
	                    " ISNULL(z.ADDINFO,'') AS ADDINFO,"+
	                    " ISNULL(z.ViewPassword,'') AS ViewPassword, "+
	                    " ISNULL(z.DTRAINING,'') AS DTRAINING, "+
	                    " ISNULL(z.TTRAINING,'') AS TTRAINING, "+
	                    " ISNULL(z.NEWSTITLE,'') AS NEWSTITLE, "+
	                    " ISNULL(z.NEWSURL,'') AS NEWSURL,"+
	                    " ISNULL(z.VENDORNAME,'') AS VENDORNAME,"+ 
                        " ISNULL(z.VENOFFURL,'') AS VENOFFURL,"+
                        " ISNULL(z.VENONURL,'') AS VENONURL,"+	                    
	                    " ISNULL(z.LOGOURL,'') AS LOGOURL,"+
	                    " ISNULL(z.LOGOFILE,'') AS LOGOFILE, "+
	                    " ISNULL(z.vlogo,'') AS vlogo,"+
                        " isnull(z.HIGHLIGHT,'') AS HIGHLIGHT, "+
                        " isnull(z.HighlightDesc,'') AS HighlightDesc, "+
                        " isnull(z.CONTENTTYPE,'') AS CONTENTTYPE, "+
    					" z.ALERT, " +
    					" z.ACCESSNOTE, z.REFMANAGEMENT, " +
    					" isnull(z.PASSWORD,'') as PASSWORD ,"+
    					" isnull(z.LOGIN,'') as LOGIN " +
	                    " FROM ATOZ z"+
	                    " where z.placementid = "+placeId;
	                  
						//FL 19/08/2011
						if(!(startChar == null)){
							select += " AND     upper(z.RESNAME) like '"+startChar+"%'";
						}
						  select += " order by z.resname";
	              //      " AND     upper(a.RESNAME) like '"+startChar+"%'"+
						/*select += " AND    1 <= (SELECT c.PLACEMENTID "+
	                    " FROM   LIBRESPLA c "+
	                    " WHERE  c.RESID = a.RESID"+
	                    " AND    c.PLACEMENTID ="+placeId+
	                    " AND    (GETDATE() > c.STARTDATE"+ 
	                    " OR     c.STARTDATE is NULL)"+
	                    " AND    (GETDATE() < c.ENDDATE"+
	                    " OR     c.ENDDATE IS NULL))"+*/
						//select += " AND    (GETDATE() > e.STARTDATE OR e.STARTDATE is NULL or e.startdate = '1900-01-01 00:00:00.000') "+ 
						//          " AND    (GETDATE() < e.ENDDATE OR e.ENDDATE IS NULL or e.enddate = '1900-01-01 00:00:00.000')"
		//String select = "selectSpecificdb  SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL, ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL, ISNULL(a.ADDINFO,'') AS ADDINFO, ISNULL(a.NEWSID,'') AS NEWSID,  ISNULL(a.TRAINING,'') AS TRAINING,   ISNULL(a.VENDORID,'') AS VENDORID, ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE, ISNULL(a.NEWSURL,'') AS NEWSURL, ISNULL(d.VENDORNAME,'') AS VENDORNAME, ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL, ISNULL(d.ONCAMPUSURL,'') AS VENONURL, ISNULL(d.LOGOURL,'') AS LOGOURL, ISNULL(d.LOGOFILE,'') AS LOGOFILE,  isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT,  isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC  FROM    LIBRESOURCE a, LIBVENDOR d WHERE   a.VENDORID = d.VENDORID AND     upper(a.RESNAME) like 'a%' AND    1 <= (SELECT c.PLACEMENTID  FROM   LIBRESPLA c  WHERE  c.RESID = a.RESID AND    c.PLACEMENTID =10 AND    (GETDATE() > c.STARTDATE OR     c.STARTDATE is NULL) AND    (GETDATE() < c.ENDDATE OR     c.ENDDATE IS NULL)) order by a.resname";
		//System.out.println(">>>>> selectSpecificdb FL"+select);
		
		String sub = "http://library.unisa.ac.za/infoweb/applications/inforesources/";
		String oncampus = "";
		String offcampus = "";
		String logo = "";
		String url = "";
		String vlogo;
		String resId ;
		ArrayList subcover= null;
		Map news = null;
		ArrayList placement= null;
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			ListOrderedMap data;
    		while (j.hasNext()) {
    			data = (ListOrderedMap) j.next();
    			ResourceForm form = new ResourceForm();
	    		/*logo =  data.get("LOGOFILE").toString();
	    	    url =  data.get("LOGOURL").toString();
	    	    vlogo = sub+url+logo; // build url for vendor logo*/
	    	    form.setUrl(data.get("vlogo").toString());
	    	    form.setDlogo(data.get("vlogo").toString());
	    		form.setDresName(data.get("RESNAME").toString());
    			form.setVresname(data.get("VENDORNAME").toString());
	    		//resId = data.get("RESID").toString(); // RESOURCE id
	    		form.setDaddInfo(data.get("ADDINFO").toString());
    			form.setDresDescr(data.get("RESDESCR").toString());
    			//String tmpLogin = data.get("LOGIN").toString();
    			String viewpassword = data.get("ViewPassword").toString();
    			
    			form.setViewPassword(ltrim(viewpassword));
    			form.setViewPassword(rtrim(viewpassword));
	    		   			
    			/* Is it a  trial database, if it is add trial expires as a highlight note */
    			//placement = getPlacement(resId, form, "10");
    			

    			// set urls according to offcampus or oncampus ip
    			try{
    				if(ipAddress.substring(0, 7).equals(ip1)||ipAddress.substring(0,8).equals(ip2)){
	    			//if(ipAddress.equals(ip1)||ipAddress.equals(ip2)){
	    				form.setDcampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());
	    				form.setCampusUrl(data.get("ONCAMPUSURL").toString());
	    			}else{
	    				form.setDcampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString());
	    				form.setCampusUrl(data.get("OFFCAMPUSURL").toString());
	    			}
	   			}catch(NullPointerException ne){
			  		  
	   			}
	   			
		   		try{
		   		form.setDtraining(data.get("TRAINING").toString());
			   	form.setTraining(data.get("TRAINING").toString());
	   			}catch(NullPointerException ne){
					  		  
	   			}
	   			try{
				   	form.setAlert(data.get("ALERT").toString());
	   			} catch(NullPointerException ne){
	   			}
	    		try{
				   	form.setRfManagement(data.get("REFMANAGEMENT").toString());
	   			} catch(NullPointerException ne){
	   			}		
	   			//form.setViewPassword(getPassword(resId));	
	   			try{
	   			form.setAccessNote(data.get("ACCESSNOTE").toString());
	   			}catch(NullPointerException ne){
	   			}
	   			form.setNewsUrl(data.get("NEWSURL").toString());
   				form.setNewsTitle(data.get("NEWSTITLE").toString());
   			    form.setHighlight(data.get("HIGHLIGHT").toString());
	   			form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
			   	form.setContentType(data.get("CONTENTTYPE").toString());
	    		vendorList.add(form);
    		}// end while
    	} catch (Exception ex) {
    		throw new Exception("ResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	}// end try
    	
    	return vendorList;
	}
	
	
	public ArrayList getFeaturedDatabase(String ipAddress) throws Exception {
		
		removeSpaces();
		
		ArrayList vendorList = new ArrayList();
		
		String select = "SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL, ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL, ISNULL(a.ADDINFO,'') AS ADDINFO, ISNULL(a.NEWSID,'') AS NEWSID,  ISNULL(a.TRAINING,'') AS TRAINING,   ISNULL(a.VENDORID,'') AS VENDORID, ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE, ISNULL(a.NEWSURL,'') AS NEWSURL, ISNULL(d.VENDORNAME,'') AS VENDORNAME, ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL, ISNULL(d.ONCAMPUSURL,'') AS VENONURL, ISNULL(d.LOGOURL,'') AS LOGOURL, ISNULL(d.LOGOFILE,'') AS LOGOFILE,  isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT,  isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC, (SELECT TEXTDESC FROM LIBTXT WHERE TXTID = a.TXTID) AS CONTENTTYPE, a.ALERT, a.ACCESSNOTE, a.REF_MANAGEMENT FROM    LIBRESOURCE a, LIBVENDOR d WHERE   a.VENDORID = d.VENDORID AND        1 <= (SELECT c.PLACEMENTID  FROM   LIBRESPLA c  WHERE  c.RESID = a.RESID AND  c.PLACEMENTID =19 AND    (GETDATE() > c.STARTDATE OR     c.STARTDATE is NULL) AND    (GETDATE() < c.ENDDATE OR     c.ENDDATE IS NULL)) and ISNULL(a.ENABLED,'Y') = 'Y' order by a.resname";
		// SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL, ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL, ISNULL(a.ADDINFO,'') AS ADDINFO, ISNULL(a.NEWSID,'') AS NEWSID,  ISNULL(a.TRAINING,'') AS TRAINING,   ISNULL(a.VENDORID,'') AS VENDORID, ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE, ISNULL(a.NEWSURL,'') AS NEWSURL, ISNULL(d.VENDORNAME,'') AS VENDORNAME, ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL, ISNULL(d.ONCAMPUSURL,'') AS VENONURL, ISNULL(d.LOGOURL,'') AS LOGOURL, ISNULL(d.LOGOFILE,'') AS LOGOFILE,  isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT,  isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC  FROM    LIBRESOURCE a, LIBVENDOR d WHERE   a.VENDORID = d.VENDORID AND     upper(a.RESNAME) like 'a%' AND    1 <= (SELECT c.PLACEMENTID  FROM   LIBRESPLA c  WHERE  c.RESID = a.RESID AND a.RESID = 575 AND  c.PLACEMENTID =10 AND    (GETDATE() > c.STARTDATE OR     c.STARTDATE is NULL) AND    (GETDATE() < c.ENDDATE OR     c.ENDDATE IS NULL)) order by a.resname
		//String select = "selectSpecificdb  SELECT a.RESID, a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL, ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL, ISNULL(a.ADDINFO,'') AS ADDINFO, ISNULL(a.NEWSID,'') AS NEWSID,  ISNULL(a.TRAINING,'') AS TRAINING,   ISNULL(a.VENDORID,'') AS VENDORID, ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE, ISNULL(a.NEWSURL,'') AS NEWSURL, ISNULL(d.VENDORNAME,'') AS VENDORNAME, ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL, ISNULL(d.ONCAMPUSURL,'') AS VENONURL, ISNULL(d.LOGOURL,'') AS LOGOURL, ISNULL(d.LOGOFILE,'') AS LOGOFILE,  isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT,  isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC  FROM    LIBRESOURCE a, LIBVENDOR d WHERE   a.VENDORID = d.VENDORID AND     upper(a.RESNAME) like 'a%' AND    1 <= (SELECT c.PLACEMENTID  FROM   LIBRESPLA c  WHERE  c.RESID = a.RESID AND    c.PLACEMENTID =10 AND    (GETDATE() > c.STARTDATE OR     c.STARTDATE is NULL) AND    (GETDATE() < c.ENDDATE OR     c.ENDDATE IS NULL)) order by a.resname";
		//System.out.println("**** selectSpecificFL **** "+select);
		
		String sub = "http://library.unisa.ac.za/infoweb/applications/inforesources/";
		String oncampus = "";
		String offcampus = "";
		String logo = "";
		String url = "";
		String vlogo;
		String resId ;
		ArrayList subcover= null;
		Map news = null;
		ArrayList placement= null;
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			ListOrderedMap data;
    		while (j.hasNext()) {
    			data = (ListOrderedMap) j.next();
    			ResourceForm form = new ResourceForm();
	    		logo =   data.get("LOGOFILE").toString();
	    	    url =  data.get("LOGOURL").toString();
	    	    vlogo = sub+url+logo;
	    	    form.setDlogo(vlogo);
	    		form.setDresName(data.get("RESNAME").toString());
	    		try{
	    			String vendorId = data.get("VENDORID").toString();
	    			form.setVresname(data.get("VENDORNAME").toString());
	    			form.setUrl(getLogo(vendorId));
    			}catch(NullPointerException ne){
    				
    			}
    			resId = data.get("RESID").toString(); 
    			//form.setDplacement(getPlacement(resId));
    			placement = getPlacement(resId,form,"");
		    	try{
		    		Iterator m = placement.iterator();
		    		String tempPlacement="";
		    		while(m.hasNext()){
		    			tempPlacement = tempPlacement+m.next().toString()+", ";
   	   				}
    				form.setDplacement(tempPlacement);
    			}catch(NullPointerException ne){
	    				
    			}
    			form.setDaddInfo(data.get("ADDINFO").toString());
    			form.setDresDescr(data.get("RESDESCR").toString());
    		
    			
    		/*	try{
	    			if(!(ipAddress.equals(ip1)||ipAddress.equals(ip2))){
	    				form.setDcampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());

	    			}else{
	    				form.setDcampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString());
	    			}
	   			}catch(NullPointerException ne){
			  		  
	   			} */ 
		   		try{
		   			if(!(data.get("TRAINING").toString().equals(" "))){
			   			form.setDtraining(data.get("TRAINING").toString());
			   		 }
	   			}catch(NullPointerException ne){
					  		  
	   			}
			   	subcover = getSubCovered(resId);	
			   	try{
			   		if(subcover.size()<=5){
		   				Iterator k = subcover.iterator();
		   				String tempSubcovered="";
		   				while(k.hasNext()){
		   					tempSubcovered=tempSubcovered+" \r\n"+k.next().toString();
		   				}
		   				form.setSubjectcover(tempSubcovered);
		   			}else{
		   				form.setSubjectcover("Multi disciplinary");
		   			}
	   			}catch(NullPointerException ne){
			   				
	   			}
			   		
	    		try{
	    			if(ipAddress.substring(0, 7).equals(ip1)||ipAddress.substring(0,8).equals(ip2)){
	    				form.setCampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());
	    			}else{
	    				form.setCampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString());
	    			}
	   			}catch(NullPointerException ne){
						  		  
	   			}
				   			
	   			try{	
	   				news = getNews(resId);
	   				form.setNewsUrl(news.get("newsUrl").toString());
	   				form.setNewsTitle(news.get("newsTitle").toString());
	   			}catch(NullPointerException ne){
					    			
	   			}
	   			
	   			try{
				   	form.setAlert(data.get("ALERT").toString());
	   			} catch(NullPointerException ne){
	   			}
	   			
	   		/*	try{
				   	form.setAccessNote(data.get("ACCESSNOTE").toString());
	   			} catch(NullPointerException ne){
	   			} */
	   			
	    		if(data.get("ACCESSNOTE")==null){
	    			form.setAccessNote(data.get("ACCESSNOTE").toString());
	    			//System.out.println("setting access note : "+data.get("ACCESSNOTE").toString());
	    		}

	    		try{
				   	form.setRfManagement(data.get("REF_MANAGEMENT").toString());
	   			} catch(NullPointerException ne){
	   			}
			   	form.setContentType(data.get("CONTENTTYPE").toString());
	   			
	   			form.setViewPassword(getPassword(resId));	
	   			form.setHighlight(data.get("HIGHLIGHT").toString());
	   			form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
	    		vendorList.add(form);
    		}// end while
    	} catch (Exception ex) {
    		throw new Exception("ResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	}// end try
    	
    	return vendorList;
	}
	
	public ArrayList getTabs() throws Exception
	{
		ArrayList tabList = new ArrayList(); 

		String select = "select * from LIBPLACEMENT where link is not null and DISPLAYORDER is not null and ENABLED = 'Y' order by DISPLAYORDER";
		
    	try{
    		//TO DO: Look into JdbTemplate and String sub = "http://library.unisa.ac.za/infoweb/applications/inforesources/ duplication
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			int i=0;
			Tab tab;
			ListOrderedMap data;
    		while (j.hasNext()) {
    			i++;
    			data = (ListOrderedMap) j.next();
    			tab = new Tab(Integer.parseInt(data.get("placementid").toString()), data.get("placement").toString(), data.get("link").toString(), i);
    			tabList.add(tab);
    		}
    		}
    	
    	catch(Exception e){
    		throw new Exception ("ResourceDAO: getting tabs"+e);
    	}
    	
    	return tabList;

		
	}
	
	public void setPlacementID(int placementID){
		this.placementID = placementID;
	}
	
	private int getPlacementID()
	{
		return this.placementID;
	}
	
	public void setSubjectID(int subjectID)
	{
		this.subjectID = subjectID;
	}
	
	public int getSubjectID()
	{
		return subjectID;
	}
	
	public void clearSubjectID()
	{
		this.subjectID = 0;
	}
	
	public void setVendorID(int vendorID)
	{
		this.vendorID = vendorID;
	}
	
	public int getVendorID()
	{
		return vendorID;
	}
	
	public void clearVendorID()
	{
		this.vendorID = 0;
	}
	private boolean isEnabled(char alphabet, int placementID, int subjectID, int vendorID){

		String select = null;
		
		if(!(subjectID == 0))
		{
			setSubjectID(subjectID);
			
			select = "SELECT a.RESID, a.RESDESCR "+ 
					 "FROM    LIBRESOURCE a, LIBVENDOR d, LIBRESSUBJ e "+ 
					 "WHERE  e.RESID = a.RESID "+ 
					 "AND a.ENABLED = 'Y' "+
					 "AND    e.SUBJID = "+subjectID+ 
					 " AND    a.VENDORID = d.VENDORID "+
					 " AND a.RESNAME like '"+alphabet+"%'";
		}
		
		if(!(vendorID == 0))
		{
			setVendorID(vendorID);
			
			select = "SELECT a.RESID, a.RESDESCR " +
					 "FROM    LIBRESOURCE a, LIBVENDOR d, LIBRESSUBJ e "+ 
					 "WHERE  e.RESID = a.RESID "+
					 "AND a.ENABLED = 'Y' "+
					 "AND    d.VENDORID = "+vendorID+
					 " AND    a.VENDORID = d.VENDORID "+ 
					 " AND a.RESNAME like '"+alphabet+"%'";
		}
		
		if(subjectID == 0 && vendorID == 0)
		{
			
			select = "SELECT * FROM LIBRESPLA a, LIBRESOURCE b "+
					 "WHERE a.RESID = b.RESID and a.PLACEMENTID = "+placementID+
					 "AND b.ENABLED = 'Y' "+
					 " AND b.RESNAME like '"+alphabet+"%'";		

		}

		
		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		List tmpList = jdt.queryForList(select);
		if(tmpList.size()>0)
		{
			if(this.startAlphabet== null){
				this.startAlphabet = Character.toString(alphabet);
			}
			
			return true;
		}
		else{
			return false;
		}
		
		//return true;
		
	}
	
	public String getStartAlphabet()
	{
		return startAlphabet;
	}
	
	public void clearStartAlphabet()
	{
		this.startAlphabet = null;
	}

	public ArrayList<Alphabet> getAlphabets(int placementID, int subjectID, int vendorID) throws Exception
	{
		ArrayList<Alphabet> alphabets = new ArrayList<Alphabet>();
		//System.out.println("**********subject ID : "+subjectID);
		String select ="select * from Alphabets";
		try{
    		// Look into duplication
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			
			Iterator j = tmpList.iterator();
			int i=0;
			Alphabet alphabet;
			ListOrderedMap data;
			
    		while (j.hasNext()) {
    			i++;
    			data = (ListOrderedMap) j.next();
    			
    			int ID = Integer.parseInt(data.get("ID").toString());
    			String alpha = data.get("Alphabet").toString();
    			if(this.startAlphabet== null){
    				this.startAlphabet = "A";
    			}
    			alphabet = new Alphabet(ID, new Character(alpha.charAt(0)), isEnabled(new Character(alpha.charAt(0)), placementID, subjectID, vendorID));
    			//alphabet = new Alphabet(ID, new Character(alpha.charAt(0)), true);
    			
    			//Alphabet alphabet = new Alphabet(ID, new Character(alpha.charAt(0)), true);
    			
    			alphabets.add(alphabet);
    		}
    		}
    	
    	catch(Exception e){
    		throw new Exception ("ResourceDAO: getting alphabets"+e);
    	}
    	
    	return alphabets;
	}
	
	public String getLogo(String vendorID){
		
		String sql= " Select logofile, logourl"+ 
			        " from   libvendor"+
			        " where  vendorid = "+vendorID;

		String url = null;
		String logo = null; 
		String Url = null;
		String sub = "http://library.unisa.ac.za/infoweb/applications/inforesources/";
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(sql);
			Iterator j = tmpList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			logo =    data.get("LOGOFILE").toString();
    		    url =  data.get("LOGOURL").toString();
    		    Url = sub+url+logo;
   			}
    			
   		} catch (Exception ex) {
    		//throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	}
    	return Url;
	
	}
	public ArrayList getPlacement(String resID, ResourceForm form, String specificPlacement){
		ResourceForm resourceForm = (ResourceForm) form;
		
		String sql= " select b.placement, b.PLACEMENTID,convert(varchar(10),isNull(a.ENDDATE,''),103) AS ENDD, " +
				    "        CAST(DAY(a.ENDDATE) AS VARCHAR(2)) + ' ' + DATENAME(MM, a.ENDDATE) + ' ' + CAST(YEAR(a.ENDDATE) AS VARCHAR(4)) as END2" +
				    " from   librespla a, libplacement b"+
                    " where  a.resid ="+resID+
                    " and    b.placementid = a.placementid " +
                    " AND    (GETDATE() > a.STARTDATE OR a.STARTDATE is NULL or a.startdate = '1900-01-01 00:00:00.000') "+ 
		            " AND    (GETDATE() < a.ENDDATE OR a.ENDDATE IS NULL or a.enddate = '1900-01-01 00:00:00.000')";
		
		if (!specificPlacement.equals("")) {
			sql = sql+ " AND a.placementid = "+specificPlacement;
			
		}

		ArrayList placements = new ArrayList();
		String placement;
		String placementId;
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(sql);
			Iterator j = tmpList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			 placement =   data.get("PLACEMENT").toString();
    			 placementId =   data.get("PLACEMENTID").toString();
    			 
    			 /*DateFormat formatter ; 
	   				Date date ;
	   				String str_date = data.get("ENDD").toString();
	   				
	   				formatter = new SimpleDateFormat("mm-dd-yyyy");
	   				date = (Date)formatter.parse(str_date);
	   				
	   				//Date s = formatter.getDateInstance(formatter.LONG).parse(str_date);*/
    			 
    			 placements.add(placement);
    			 if (placementId.equals("10")) {
    				 resourceForm.setExpiryDate(data.get("END2").toString());
    			 }
    			 
    		    	
    			}
    			
    		} 

    	 catch (Exception ex) {
    		//throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	 }
    	return placements;
	
	}
	public ArrayList getSubCovered(String resID){
		
		String sql= " SELECT B.SUBJID, ISNULL(A.SUBJECT,'') as SUBJECT"+
                    " FROM   LIBSUBJ A, LIBRESSUBJ B"+
                    " WHERE  RESID = "+resID+
                    " AND    B.SUBJID = A.SUBJID";
   

      
		ArrayList subcovered= new ArrayList();
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(sql);
			Iterator j = tmpList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			String subjects =   data.get("SUBJECT").toString();
    			subcovered.add(subjects);
    			}
    		
    			 
    		} 
		
    	 catch (Exception ex) {
    		//throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	 }// end try
    	return subcovered;
	
	}
	public Map getNews(String resID){
		
		String sql= " SELECT LIBRESOURCE.NEWSID, LIBRESOURCE.NEWSURL,"+
	                " LIBNEWSTITLE.NEWSTITLE"+
	                " FROM   LIBRESOURCE, LIBNEWSTITLE"+
	                " WHERE  LIBRESOURCE.RESID = "+resID+
	                " AND LIBRESOURCE.NEWSID = LIBNEWSTITLE.NEWSID";

		  Map news = Collections.synchronizedMap(new TreeMap());
     
	
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(sql);
			Iterator j = tmpList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			String newsurl =   data.get("NEWSURL").toString();
    			String newstitle =   data.get("NEWSTITLE").toString();
    			news.put("newsUrl",newsurl);
    			news.put("newsTitle",newstitle);
    			}
  
    		
    			 
    		} 
		
    	 catch (Exception ex) {
    		//throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	 }
    	return news;
	
	}
	public String getPassword(String resID){
		
		String sql = " SELECT PASSWAVAIL , PASSWORD , LOGIN FROM LIBRESOURCE "+ 
                     " WHERE RESID ="+resID;
		
		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
        List tmpPass = jdt.queryForList(sql);
        Iterator p = tmpPass.iterator();
        String tempPassLogin ="";
        while (p.hasNext()) {
        	ListOrderedMap data1 = (ListOrderedMap) p.next();
        	String pass="";
        	String login="";
        	String avail="";
        	try{
        		pass = data1.get("PASSWORD").toString();   	    	
                login = data1.get("LOGIN").toString();
                avail = data1.get("PASSAVAI").toString();
               
        	}catch(NullPointerException ne){
	
        	}
        	
        	if (avail.equals("Y")) {
        		tempPassLogin = "Login="+login+";"+"Password="+pass;
        	} else {
        		tempPassLogin = "";
        	}
        }
        return tempPassLogin;
	}
	
	public void removeSpaces(){
		
		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		
		String alerts = "UPDATE LIBRESOURCE SET REF_MANAGEMENT = NULL WHERE REF_MANAGEMENT = ''",
			   refManagement = "UPDATE LIBRESOURCE SET ALERT = NULL WHERE ALERT = ''";
		
		jdt.update(alerts);
		jdt.update(refManagement);
	}
	
	/*
	 * remove leading whitespace 
	 */
	 public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	     }

	    /* remove trailing whitespace */
	 public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
}
