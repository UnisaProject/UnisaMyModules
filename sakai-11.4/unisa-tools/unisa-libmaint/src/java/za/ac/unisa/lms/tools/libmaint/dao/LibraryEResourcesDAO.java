package za.ac.unisa.lms.tools.libmaint.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.MSSQLServerDAO;
import za.ac.unisa.lms.tools.libmaint.forms.MaintenanceForm;
import za.ac.unisa.lms.tools.libmaint.forms.NewsTitleForm;
import za.ac.unisa.lms.tools.libmaint.forms.PlacementForm;
import za.ac.unisa.lms.tools.libmaint.forms.ResourceForm;
import za.ac.unisa.lms.tools.libmaint.forms.SubjectForm;
import za.ac.unisa.lms.tools.libmaint.forms.TextForm;
import za.ac.unisa.lms.tools.libmaint.forms.VendorForm;
import za.ac.unisa.lms.tools.libmaint.forms.highlightNoteForm;

public class LibraryEResourcesDAO extends MSSQLServerDAO{

	private String LOGOURL = "http://library.unisa.ac.za/infoweb/applications/inforesources/";
	
	public LibraryEResourcesDAO(String database) {
		super(database);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method will select all the txt coverage data
	 * @input typeData OPTIONLIST = arraylist of labelValueBean/ FORMLIST = arraylist of vendorForm
	*/
	public ArrayList selectTextCoverageList(String typeData) throws Exception {
		
		ArrayList txtList = new ArrayList();
		
		if (typeData.equals("OPTIONLIST")) {
			txtList.add(new org.apache.struts.util.LabelValueBean("..",""));
		}
				
		String select = " SELECT TXTID, TEXTDESC, ENABLED " +
						" FROM   LIBTXT ";
		if (typeData.equals("OPTIONLIST")) {
			select = select + " WHERE ENABLED='Y'";
		}
		select = select + " ORDER BY TEXTDESC";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				txtList.add(new org.apache.struts.util.LabelValueBean(data.get("TEXTDESC").toString(),data.get("TXTID").toString()));
    			} else {
    			
	    			TextForm form = new TextForm();
	    			form.setTxtId(Integer.parseInt(data.get("TXTID").toString()));
	    			form.setTxtDesc(data.get("TEXTDESC").toString());
	    			form.setEnabled(data.get("ENABLED").toString());
	    			
	    			txtList.add(form);
    			}
    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectTextCoverageList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return txtList;
	}
	
	public void deleteText(int txtId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBTXT" +
			  " SET    ENABLED = 'N'"+
			  " WHERE  TXTID = "+txtId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteText (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBTXT " +
			  " WHERE  TXTID = "+txtId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteText (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	/**
	 * This method will check if a specific text record is linked to an e-resource

	*/
	public boolean textCoverageInUse(int txtId) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBRESOURCE "+
						" WHERE  TXTID = "+txtId;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			inUse = true;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: textCoverageInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	public void insertText(String textDesc, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextTxtSequence();
		
		
		String sql1 = "INSERT INTO LIBTXT(TXTID, TEXTDESC, ENABLED)" +
				" VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, textDesc,enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertText: Error occurred / "+ ex,ex);
		}
	}
	
	public void updateText(int textId, String textDesc, String enabled) throws Exception {

		String sql1 = " UPDATE LIBTXT" +
					  " SET   TEXTDESC = '"+textDesc+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE TXTID = "+textId;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateText: Error occurred / "+ ex,ex);
		}
	}
	
	/**
	 * This method will select all the subject data

	*/
	public ArrayList selectSubjectList(String typeData, String startChar) throws Exception {
		/*
		 * CREATE TABLE LIBSUBJ
(SUBJID INT PRIMARY KEY,
SUBJECT VARCHAR(50) NOT NULL,
ENABLED  CHAR);

		 */
		ArrayList subjectList = new ArrayList();
				
		String select = " SELECT SUBJID, SUBJECT, ENABLED " +
						" FROM   LIBSUBJ ";
		
		if (typeData.equals("OPTIONLIST")) {
			// only display enabled vendors in options list
			select = select + " WHERE ENABLED='Y'";
		} else {
			if ((null!=startChar)&&(!startChar.equals(""))) {
				select = select + " WHERE SUBJECT LIKE '"+startChar+"%'";
				
			}
		}
		select = select +" ORDER  BY SUBJECT";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				subjectList.add(new org.apache.struts.util.LabelValueBean(data.get("SUBJECT").toString(),data.get("SUBJID").toString()));
    			} else {
    				
        			SubjectForm form = new SubjectForm();
        			form.setSubjectId(Integer.parseInt(data.get("SUBJID").toString()));
        			form.setSubject(data.get("SUBJECT").toString());
        			form.setEnabled(data.get("ENABLED").toString());
        			
        			subjectList.add(form);
    			}

    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectSubjectList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return subjectList;
	}
	
	/**
	 * This method will select all the highlight note data

	*/
	public ArrayList selectHighLightList(String typeData) throws Exception {
		/*
		 * CREATE TABLE LIBHIGHLIGHTNOTE
(HIGHLIGHTID INT PRIMARY KEY,
HIGHLIGHTDESC VARCHAR(50) NOT NULL,
ENABLED  CHAR);

		 */
		ArrayList highList = new ArrayList();
		
		if (typeData.equals("OPTIONLIST")) {
			highList.add(new org.apache.struts.util.LabelValueBean("..",""));
		}
				
		String select = " SELECT HIGHLIGHTID, HIGHLIGHTDESC, ENABLED " +
						" FROM   LIBHIGHLIGHTNOTE ";
		
		if (typeData.equals("OPTIONLIST")) {
			// only display enabled vendors in options list
			select = select + " WHERE ENABLED='Y'";
		}
		select = select +" ORDER  BY HIGHLIGHTDESC";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				highList.add(new org.apache.struts.util.LabelValueBean(data.get("HIGHLIGHTDESC").toString(),data.get("HIGHLIGHTID").toString()));
    			} else {
    				
        			highlightNoteForm form = new highlightNoteForm();
        			form.setHighId(Integer.parseInt(data.get("HIGHLIGHTID").toString()));
        			form.setHighlight(data.get("HIGHLIGHTDESC").toString());
        			form.setEnabled(data.get("ENABLED").toString());
        			
        			highList.add(form);
    			}

    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectHighLightList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return highList;
	}
	
	/**
	 * This method will select all the placements from the database
	 *
	 *@input typeDate OPTIONLIST = arraylist of labelValueBean/ FORMLIST = arraylist of placementForm
	*/
	public ArrayList selectPlacementList(String typeData, String resourcesPerPlacement,boolean enabledOnly,boolean orderByRanking) throws Exception {
		
		ArrayList dataList = new ArrayList();
		if (typeData.equals("OPTIONLIST")) {
			dataList.add(new org.apache.struts.util.LabelValueBean("..","0"));
		}
						
		String select = " SELECT PLACEMENTID, PLACEMENT, ISNULL(DISPLAYORDER,999) as DISPLAYORDER, ENABLED " +
						" FROM   LIBPLACEMENT ";
		if (typeData.equals("OPTIONLIST")) {
			// only display enabled vendors in options list
			select = select + " WHERE ENABLED='Y'";
		} else if (enabledOnly == true) {
			select = select + " WHERE ENABLED='Y'";
		} else {
			if ((resourcesPerPlacement != null)&&(!resourcesPerPlacement.equals("0"))) {
				select = select + " WHERE PLACEMENTID = "+resourcesPerPlacement;
			}
		}
		
		if (orderByRanking == true) {
			select = select +" order by displayorder";
		} else {
			select = select + " ORDER BY PLACEMENT";
		}
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				dataList.add(new org.apache.struts.util.LabelValueBean(data.get("PLACEMENT").toString(),data.get("PLACEMENTID").toString()));
    			} else {
    				
	    			PlacementForm form = new PlacementForm();
	    			form.setPlacementId(Integer.parseInt(data.get("PLACEMENTID").toString()));
	    			form.setPlacement(data.get("PLACEMENT").toString());
	    			form.setPlacementRank(data.get("DISPLAYORDER").toString());
	    			form.setEnabled(data.get("ENABLED").toString());
	 				
	    			if (orderByRanking == true) {
	    				if (form.getPlacementRank().equals("999")) {
	    					form.setPlacementId(0);
	    				}
	    			}

	    			if (form.getPlacementRank().equals("999")) {
    					form.setPlacementRank("");
    				}			
	    			
	    			dataList.add(form);
    			}
	    			    			
    			
    		} // end while  		

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectPlacementList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return dataList;
	}
	
	
	/**
	 * This method will select all the vendors from the database
	 *
	 * @input typeDate OPTIONLIST = arraylist of labelValueBean/ FORMLIST = arraylist of vendorForm
	*/
	public ArrayList selectVendorList(String typeData, String startChar) throws Exception {
		
		
		ArrayList dataList = new ArrayList();
		if (typeData.equals("OPTIONLIST")) {
			dataList.add(new org.apache.struts.util.LabelValueBean("..","null"));
		}
				
		String select = " SELECT VENDORID, VENDORNAME, ONCAMPUSURL, OFFCAMPUSURL," +
						"        ISNULL(LOGOFILE,'') as LOGOFILE, ISNULL(LOGOURL,'') AS LOGOURL," +
						"        ENABLED" +
						" FROM   LIBVENDOR ";
		if (typeData.equals("OPTIONLIST")) {
			// only display enabled vendors in options list
			select = select + " WHERE ENABLED='Y'";
		} else {
			if ((null!=startChar)&&(!startChar.equals(""))) {
				select = select + " WHERE VENDORNAME LIKE '"+startChar+"%'";
				
			}
		}
		select = select + " ORDER BY VENDORNAME";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				dataList.add(new org.apache.struts.util.LabelValueBean(data.get("VENDORNAME").toString(),data.get("VENDORID").toString()));
    			} else {
    			
	    			VendorForm form = new VendorForm();
	    			form.setVendorId(Integer.parseInt(data.get("VENDORID").toString()));
	    			form.setVendor(data.get("VENDORNAME").toString());
	    			form.setOnCampusURL(data.get("ONCAMPUSURL").toString());
	    			form.setOffCampusURL(data.get("OFFCAMPUSURL").toString());
	    			form.setLogoFileName(data.get("LOGOFILE").toString());
	    			form.setLogoURL(data.get("LOGOURL").toString());
	    			String fullLogoURL = LOGOURL+form.getLogoURL()+form.getLogoFileName();
	    			form.setFullLogoURL(fullLogoURL);
	    			form.setEnabled(data.get("ENABLED").toString());
	    			
	    			dataList.add(form);
    			}
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return dataList;
	}
	
	/**
	 * This method will return the description of a specific vendor
	*/
	public String selectVendorDesc(int vendorId) throws Exception {
		
		String vendorDesc = "";
		
		String select = " SELECT VENDORNAME "+
						" FROM   LIBVENDOR "+
						" WHERE  VENDORID = "+vendorId;
			
    	try{
    		vendorDesc = this.querySingleValue(select,"VENDORNAME");

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectVendorDesc(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return vendorDesc;
	}
	
	/**
	 * This method will return the description of a specific text coverage
	*/
	public String selectTextCoverageDesc(String textId) throws Exception {
		
		String textDesc = "";
		
		String select = " SELECT TEXTDESC "+
						" FROM   LIBTXT "+
						" WHERE  TXTID = "+textId;
			
    	try{
    		textDesc = this.querySingleValue(select,"TEXTDESC");

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectTextCoverageDesc(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return textDesc;
	}
	
	/**
	 * This method will return the description of a specific text coverage
	*/
	public String selectNewsTitleDesc(String newsTitleId) throws Exception {
		
		String newsDesc = "";
		
		String select = " SELECT NEWSTITLE "+
						" FROM   LIBNEWSTITLE "+
						" WHERE  NEWSID = "+newsTitleId;
			
    	try{
    		newsDesc = this.querySingleValue(select,"NEWSTITLE");

    	} catch (DataAccessException ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNewsTitleDesc(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return newsDesc;
	}
	
	/**
	 * This method will select all the resource data

	*/
	public ArrayList selectResourceList(String startChar, String forPlacement, String forVendor) throws Exception {
		/*
		 * CREATE TABLE LIBRESOURCE
			(RESID INT PRIMARY KEY,
			RESNAME VARCHAR(255) NOT NULL,
			RESDESCR VARCHAR(255) NOT NULL ,
			ENABLED CHAR,
			ONCAMPUSURL VARCHAR(300),
			OFFCAMPUSURL VARCHAR(300),
			VENDORID INT NOT NULL,
			ADDINFO VARCHAR(255),
			TXTID INT NOT NULL,
			CDROM VARCHAR(255),
			TRAINING VARCHAR(255),
			PASSWAVAIL CHAR NOT NULL,
			LOGIN VARCHAR(20),
			PASSWORD VARCHAR(20),
			NEWSID VARCHAR(50),
			NEWSURL VARCHAR(255));
			
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (VENDORID) REFERENCES LIBVENDOR(VENDORID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (TXTID) REFERENCES LIBTXT(TXTID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (NEWSID) REFERENCES LIBNEWSTITLE(NEWSID);
		 */
		ArrayList resourceList = new ArrayList();
				
		if ((forPlacement == null)||(forPlacement.equals(""))) {
			forPlacement="0";
		}
		if ((forVendor == null)||(forVendor.equals(""))) {
			forVendor="0";
		}
		
		String select = "";
		if ((forPlacement.equals("0"))&&(forVendor.equals("0"))) {
			select = " SELECT LIBRESOURCE.RESID as RESID,LIBRESOURCE.RESNAME AS NAME," +
							" LIBRESOURCE.VENDORID AS VID,LIBVENDOR.VENDORNAME AS VENDOR,LIBRESOURCE.ENABLED AS ENABLED" +
							" FROM   LIBRESOURCE, LIBVENDOR " +
							" WHERE  LIBRESOURCE.VENDORID = LIBVENDOR.VENDORID" +
							" AND    LIBRESOURCE.RESNAME LIKE '"+startChar+"%'"+
							" ORDER BY LIBRESOURCE.RESNAME";
			
			/*
			if ((null!=startChar)&&(!startChar.equals(""))) {
				select = select + " AND RESNAME LIKE '"+startChar+"%'";
				
			}
			*/
		} else if (!forVendor.equals("0")) {
			select = " SELECT LIBRESOURCE.RESID as RESID,LIBRESOURCE.RESNAME AS NAME," +
			" LIBRESOURCE.VENDORID AS VID,LIBVENDOR.VENDORNAME AS VENDOR,LIBRESOURCE.ENABLED AS ENABLED" +
			" FROM   LIBRESOURCE, LIBVENDOR "+
			" WHERE  LIBRESOURCE.VENDORID = LIBVENDOR.VENDORID " +
			" AND    LIBRESOURCE.VENDORID = "+forVendor+" ";
			

			if ((null!=startChar)&&(!startChar.equals(""))) {
				select = select + " AND RESNAME LIKE '"+startChar+"%'";
			
			}

		} else {
			select = " SELECT LIBRESOURCE.RESID as RESID,LIBRESOURCE.RESNAME AS NAME," +
						" LIBRESOURCE.VENDORID AS VID,LIBVENDOR.VENDORNAME AS VENDOR,LIBRESOURCE.ENABLED AS ENABLED" +
						" FROM   LIBRESOURCE, LIBVENDOR, LIBRESPLA "+
						" WHERE  LIBRESOURCE.RESID = LIBRESPLA.RESID " +
						" AND    LIBRESOURCE.VENDORID = LIBVENDOR.VENDORID"+
						" AND    LIBRESPLA.PLACEMENTID = '"+forPlacement+"' ";

			if ((null!=startChar)&&(!startChar.equals(""))) {
			select = select + " AND RESNAME LIKE '"+startChar+"%'";
			
			}			
		}
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			ResourceForm form = new ResourceForm();
    			form.setResourceId(Integer.parseInt(data.get("RESID").toString()));
    			form.setResourceName(data.get("NAME").toString());
    			form.setVendorId(Integer.parseInt(data.get("VID").toString()));
    			form.setVendorDesc(data.get("VENDOR").toString());
    			form.setEnabled(data.get("ENABLED").toString());
    			
    			// select placements for resource
    			form.setPlacements(selectResourcePlacements(form.getResourceId(),"L"));
    			
    			resourceList.add(form);
    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourceList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return resourceList;
	}
	
	/**
	 * This method will select resource information for a specific resource

	*/
	public void selectResource(MaintenanceForm form) throws Exception {
		/*
		 * CREATE TABLE LIBRESOURCE
			(RESID INT PRIMARY KEY,
			RESNAME VARCHAR(255) NOT NULL,
			RESDESCR VARCHAR(255) NOT NULL ,
			ENABLED CHAR,
			ONCAMPUSURL VARCHAR(300),
			OFFCAMPUSURL VARCHAR(300),
			VENDORID INT NOT NULL,
			ADDINFO VARCHAR(255),
			TXTID INT NOT NULL,
			CDROM VARCHAR(255),
			TRAINING VARCHAR(255),
			PASSWAVAIL CHAR NOT NULL,
			LOGIN VARCHAR(20),
			PASSWORD VARCHAR(20),
			NEWSID VARCHAR(50),
			NEWSURL VARCHAR(255));
			
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (VENDORID) REFERENCES LIBVENDOR(VENDORID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (TXTID) REFERENCES LIBTXT(TXTID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (NEWSID) REFERENCES LIBNEWSTITLE(NEWSID);
		 */
		String select = "";
		
		select = " SELECT LIBRESOURCE.RESID as RESID,ISNULL(LIBRESOURCE.RESNAME,'') AS NAME, ISNULL(LIBRESOURCE.VENDORID,'') AS VID, " +
					" (SELECT LIBVENDOR.VENDORNAME FROM LIBVENDOR WHERE LIBRESOURCE.VENDORID = LIBVENDOR.VENDORID) AS VENDOR, "+
					" LIBRESOURCE.ENABLED AS ENABLED," +
					" ISNULL(LIBRESOURCE.RESDESCR,'') AS RDESC, ISNULL(LIBRESOURCE.ONCAMPUSURL,'') AS ONURL, " +
					" ISNULL(LIBRESOURCE.OFFCAMPUSURL,'') AS OFFURL," +
					" ISNULL(LIBRESOURCE.ADDINFO,'') AS ADDINFO, LIBRESOURCE.TXTID AS TXTID, ISNULL(LIBTXT.TEXTDESC,'') AS TXT, " +
					" ISNULL(LIBRESOURCE.CDROM,'') AS CD, ISNULL(LIBRESOURCE.TRAINING,'') AS TRAINING, " +
					" ISNULL(LIBRESOURCE.PASSWAVAIL,'') AS PAVAIL," +
					" isnull((SELECT LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE LIBRESOURCE.NEWSID = LIBNEWSTITLE.NEWSID),'') AS NEWSTITLE," +
					" ISNULL(LIBRESOURCE.NEWSID,'') AS NEWSID, ISNULL(LIBRESOURCE.NEWSURL,'') AS NEWSURL, " +
					" isnull(LIBRESOURCE.LOGIN,'') AS LOGIN, ISNULL(LIBRESOURCE.PASSWORD,'')AS PASSWORD," +
					" isnull(LIBRESOURCE.HIGHLIGHTID,'') AS HIGHLIGHT, "+
					" isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE LIBRESOURCE.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC, " +
					" ISNULL(ACCESSNOTE,'') AS ACCESSNOTE,ISNULL(ALERT,'x') AS ALERT, ISNULL(REF_MANAGEMENT,'') AS REFMAN " +
					" FROM   LIBRESOURCE, LIBTXT " +
					" WHERE  LIBRESOURCE.RESID = "+form.getId()+
					" AND    LIBRESOURCE.TXTID = LIBTXT.TXTID ";
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			form.setResourceName(data.get("NAME").toString());
    			form.setVendorId(Integer.parseInt(data.get("VID").toString()));
    			form.setVendorDesc(data.get("VENDOR").toString());
    			form.setEnabled(data.get("ENABLED").toString());
    			form.setResourceDesc(data.get("RDESC").toString());
    			try{
    			form.setOnCampusURL(data.get("ONURL").toString());
    			form.setOffCampusURL(data.get("OFFURL").toString());
    			}catch(NullPointerException e){
    				
    			}
    			form.setTextId(data.get("TXTID").toString());
    			form.setTextDesc(data.get("TXT").toString());
    			form.setCdRom(data.get("CD").toString());
    			form.setPasswordExist(data.get("PAVAIL").toString());
    			form.setNewsTitle(data.get("NEWSID").toString());
    			form.setNewsTitleDesc(data.get("NEWSTITLE").toString());
    			form.setNewsURL(data.get("NEWSURL").toString());
    			form.setHighlight(data.get("HIGHLIGHT").toString());
    			form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
    			form.setAccessNote(data.get("ACCESSNOTE").toString());
    			form.setTrainingURL(data.get("TRAINING").toString());
    			form.setRefManagementURL(data.get("REFMAN").toString());
    			System.out.println("alert=="+data.get("ALERT").toString()+"==");
    			form.setAlert(data.get("ALERT").toString());
    			if (form.getAlert().equals("x")) {
    				form.setAlert("");
    			}
    			
    			// select placements for resource
    			selectResourcePlacements(form.getId(), form);
    			
    			// select subjects for resource
    			selectResourceSubject(form.getId(), form);
    			
    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResource(): Error occurred / "+ ex,ex);
    	} // end try
    	
	}
	
	/**
	 * This method will select all placements linked to a specific resource
	 * in: resid = resource id
	 * typeList = must list be arrayLIst of labelValueBean(L) or PlacementForm objects (O).

	*/
	public ArrayList selectResourcePlacements(int resid, String typeList) throws Exception {

		ArrayList placements = new ArrayList();
				
		String select = " SELECT b.PLACEMENT as PLACEMENT, a.PLACEMENTID as ID, " +
				        "        convert(varchar(10),isNull(a.STARTDATE,''),103) AS STARTD, convert(varchar(10),isNull(a.ENDDATE,''),103) AS ENDD " +
						" FROM   LIBRESPLA a, LIBPLACEMENT b "+
						" WHERE  a.RESID = "+resid+
						" AND    a.PLACEMENTID = b.PLACEMENTID";
		System.out.println("selectResourcePlacements "+select);
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeList.equals("O")) {
    				PlacementForm pForm = new PlacementForm();
    				pForm.setResId(resid);
    				pForm.setPlacementId(Integer.parseInt(data.get("ID").toString()));
    				pForm.setPlacement(data.get("PLACEMENT").toString());
    				pForm.setFromDate(data.get("STARTD").toString());
    				if (pForm.getFromDate().equals("01/01/1900")) {
    					pForm.setFromDate("");
    				}
    				pForm.setEndDate(data.get("ENDD").toString());
    				if (pForm.getEndDate().equals("01/01/1900")) {
    					pForm.setEndDate("");
    				}
    				
    				placements.add(pForm);
    			} else {
    				placements.add(new org.apache.struts.util.LabelValueBean(data.get("PLACEMENT").toString(),data.get("ID").toString()));    				
    			}
    			    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourcePlacements(int resid, String typeList): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return placements;
	}
	
	/**
	 * This method will select all placements linked to a specific resource

	*/
	public void selectResourcePlacements(int resid, MaintenanceForm form) throws Exception {
	
		String select = " SELECT b.PLACEMENT as PLACEMENT, a.PLACEMENTID as ID " +
						" FROM   LIBRESPLA a, LIBPLACEMENT b "+
						" WHERE  a.RESID = "+resid+
						" AND    a.PLACEMENTID = b.PLACEMENTID";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
			int counter = 0;
			String[] placements = new String[courseInfoList.size()];
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			placements[counter]= data.get("ID").toString();
    			counter++;
    			    			   			
    		} // end while
    		
   			form.setSelectedPlacements(placements);


    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourcePlacements(resid, maintenanceForm): Error occurred / "+ ex,ex);
    	} // end try
    	
	}
	
	/**
	 * This method will select all subjects linked to a specific resource

	*/
	public void selectResourceSubject(int resid, MaintenanceForm form) throws Exception {

		
				
		String select = " SELECT b.SUBJECT as SUBJ, a.SUBJID as ID " +
						" FROM   LIBRESSUBJ a, LIBSUBJ b "+
						" WHERE  a.RESID = "+resid+
						" AND    a.SUBJID = b.SUBJID" +
						" ORDER BY b.SUBJECT";
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List subjList = jdt.queryForList(select);
			Iterator j = subjList.iterator();
			int counter = 0;
			String[] subjects = new String[subjList.size()];
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			subjects[counter]= data.get("ID").toString();
    			counter++;
    			    			   			
    		} // end while
    		
   			form.setSelectedSubjects(subjects);


    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourceSubject(resid, maintenanceForm): Error occurred / "+ ex,ex);
    	} // end try
    	
	}
	
	/**
	 * This method will select all subjects linked to a specific resource

	*/
	public ArrayList selectResourceSubject(int resid) throws Exception {

		ArrayList subjects = new ArrayList();
				
		String select = " SELECT b.SUBJECT as SUBJ, a.SUBJID as ID " +
						" FROM   LIBRESSUBJ a, LIBSUBJ b "+
						" WHERE  a.RESID = "+resid+
						" AND    a.SUBJID = b.SUBJID" +
						" ORDER BY b.SUBJECT";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List subjList = jdt.queryForList(select);
			Iterator j = subjList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			subjects.add(new org.apache.struts.util.LabelValueBean(data.get("SUBJ").toString(),data.get("ID").toString()));
    			    			   			
    		} // end while
    		


    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourceSubject(resid): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return subjects;
    	
	}
	
	/**
	 * This method will check if resource name is unique and return true/false

	*/
	public Boolean selectResourceUnique(String resourceName) throws Exception {
		/*
		 * CREATE TABLE LIBRESOURCE
			(RESID INT PRIMARY KEY,
			RESNAME VARCHAR(255) NOT NULL,
			RESDESCR VARCHAR(255) NOT NULL ,
			ENABLED CHAR,
			ONCAMPUSURL VARCHAR(300),
			OFFCAMPUSURL VARCHAR(300),
			VENDORID INT NOT NULL,
			ADDINFO VARCHAR(255),
			TXTID INT NOT NULL,
			CDROM VARCHAR(255),
			TRAINING VARCHAR(255),
			PASSWAVAIL CHAR NOT NULL,
			LOGIN VARCHAR(20),
			PASSWORD VARCHAR(20),
			NEWSID VARCHAR(50),
			NEWSURL VARCHAR(255));
			
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (VENDORID) REFERENCES LIBVENDOR(VENDORID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (TXTID) REFERENCES LIBTXT(TXTID);
			ALTER TABLE LIBRESOURCE ADD FOREIGN KEY (NEWSID) REFERENCES LIBNEWSTITLE(NEWSID);
		 */
		ArrayList resourceList = new ArrayList();
		Boolean unique = true;
				
		String select = " SELECT COUNT(*) as A" +
						" FROM   LIBRESOURCE "+
						" WHERE  LIBRESOURCE.RESNAME = '"+resourceName+"' ";
			
		try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			unique = false;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectResourceUnique(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return unique;
	}
	
	/**
	 * This method will select all the news title list data
	 * @input typeData OPTIONLIST = arraylist of labelValueBean/ FORMLIST = arraylist of vendorForm
	*/
	public ArrayList selectNewsTitleList(String typeData) throws Exception {
		
		/*
		 * CREATE TABLE LIBNEWSTITLE
			(NEWSID INT PRIMARY KEY,
			NEWSTITLE VARCHAR(50) NOT NULL,
			ENABLED CHAR);

		 */
		
		ArrayList dataList = new ArrayList();
		
		if (typeData.equals("OPTIONLIST")) {
			dataList.add(new org.apache.struts.util.LabelValueBean("..",""));
		}
				
		String select = " SELECT NEWSID, NEWSTITLE, ENABLED " +
						" FROM   LIBNEWSTITLE ";
		if (typeData.equals("OPTIONLIST")) {
			select = select + " WHERE ENABLED='Y'";
		}
		select = select + " ORDER BY NEWSTITLE";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeData.equals("OPTIONLIST")) {
    				dataList.add(new org.apache.struts.util.LabelValueBean(data.get("NEWSTITLE").toString(),data.get("NEWSID").toString()));
    			} else {
    			
	    			NewsTitleForm form = new NewsTitleForm();
	    			form.setNewsTitleId(Integer.parseInt(data.get("NEWSID").toString()));
	    			form.setNewsTitleDesc(data.get("NEWSTITLE").toString());
	    			form.setEnabled(data.get("ENABLED").toString());
	    			
	    			dataList.add(form);
    			}
    			   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNewsTitleList(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return dataList;
	}
	
	/**
	 * This method will check if a specific placement record is linked to an e-resource

	*/
	public boolean placementInUse(int id) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBRESPLA "+
						" WHERE  PLACEMENTID = "+id;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			inUse = true;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: placementInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	
	/**
	 * This method will check if a specific placement record is linked to an e-resource

	*/
	public boolean vendorInUse(int id) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBRESOURCE "+
						" WHERE  VENDORID = "+id;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			inUse = true;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: vendorInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	/**
	 * This method will check if a specific newsTitle record is linked to an e-resource

	*/
	public boolean newsTitleInUse(int id) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBRESOURCE "+
						" WHERE  NEWSID = "+id;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			inUse = true;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: newsTitleInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	/**
	 * This method will check if a specific subject record is linked to an e-resource

	*/
	public boolean subjectInUse(int subjectId) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBRESSUBJ "+
						" WHERE  SUBJID = "+subjectId;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		if (nrOfRecords > 0) {
    			inUse = true;
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: subjectInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	/**
	 * This method will check if a specific highlight note record is linked to an e-resource

	*/
	public boolean highlightInUse(int highId) throws Exception {
		
		boolean inUse = false;
				
		String select = " SELECT count(*) as A " +
						" FROM   LIBHIGHLIGHTNOTE "+
						" WHERE  HIGHLIGHTID = "+highId;
			
    	try{
    		int nrOfRecords = Integer.parseInt(this.querySingleValue(select,"A"));
    		/*if (nrOfRecords > 0) {
    			inUse = true;
    		}*/

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: highlightInUse(): Error occurred / "+ ex,ex);
    	} // end try
    	
    	return inUse;
	}
	
	public void deletePlacement(int txtId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBPLACEMENT" +
			  " SET    ENABLED = 'N'"+
			  " WHERE  PLACEMENTID = "+txtId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deletePlacement (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBPLACEMENT " +
			  " WHERE  PLACEMENTID = "+txtId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deletePlacement (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	public void deleteVendor(int txtId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBVENDOR" +
						  " SET    ENABLED = 'N'"+
						  " WHERE  VENDORID = "+txtId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteVendor (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBVENDOR " +
			  			  " WHERE  VENDORID = "+txtId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteVendor (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	public void deleteSubject(int subjectId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBSUBJ" +
						  " SET    ENABLED = 'N'"+
						  " WHERE  SUBJID = "+subjectId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteSubject (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBSUBJ " +
			  			  " WHERE  SUBJID = "+subjectId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteSubject (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	public void deleteHighlight(int highId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBHIGHLIGHTNOTE" +
						  " SET    ENABLED = 'N'"+
						  " WHERE  HIGHLIGHTID = "+highId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteHighlight (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBHIGHLIGHTNOTE " +
			  			  " WHERE  HIGHLIGHTID = "+highId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteHighlight (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	public void deleteResource(int resId) throws Exception{

		// First delete all linked subjects to this e-resource
		deleteResSubj(resId);
		
		// Secondly delete all linked placements to this e-resource
		deleteResPlacements(resId);
		
		// Thirdly delete the actual e-resource record
		String sql1 = " DELETE FROM LIBRESOURCE " +
		  			  " WHERE  RESID = "+resId;
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: deleteResource (Delete): Error occurred /"+ ex,ex);
		}
	}
	
	public void deleteNewsTitle(int newsTitleId, boolean inUse) throws Exception{

		if (inUse == true) {
			// only disable record
			String sql1 = " UPDATE LIBNEWSTITLE" +
						  " SET    ENABLED = 'N'"+
						  " WHERE  NEWSID = "+newsTitleId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteNewsTitle (disable): Error occurred / "+ ex,ex);
			}
		} else {
			// remove record
			String sql1 = " DELETE FROM LIBNEWSTITLE " +
			  			  " WHERE  NEWSID = "+newsTitleId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteNewsTitle (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	public void insertPlacement(String placement, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextPlacementSequence();
		
		
		String sql1 = "INSERT INTO LIBPLACEMENT(PLACEMENTID, PLACEMENT, ENABLED)" +
				" VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, placement, enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertPlacement: Error occurred / "+ ex,ex);
		}
	}
	
	public void insertVendor(String vendor, String onCampusURL, String offCampusURL,
			String logoFileName, String logoURL, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextVendorSequence();
		
		
		String sql1 = " INSERT INTO LIBVENDOR(VENDORID, VENDORNAME,ONCAMPUSURL,OFFCAMPUSURL, " +
				" LOGOFILE, LOGOURL, ENABLED)" +
				" VALUES (?,?,?,?,?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, vendor,onCampusURL,offCampusURL,logoFileName, logoURL, enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertVendor: Error occurred / "+ ex,ex);
		}
	}
	
	public void insertSubject(String subject, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextSubjectSequence();
		
		
		String sql1 = " INSERT INTO LIBSUBJ(SUBJID, SUBJECT,ENABLED) " +
				" VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, subject,enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertSubject: Error occurred / "+ ex,ex);
		}
	}
	
	public void insertHighlight(String highlight, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextHighlightSequence();
		
		
		String sql1 = " INSERT INTO LIBHIGHLIGHTNOTE(HIGHLIGHTID, HIGHLIGHTDESC,ENABLED) " +
				" VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, highlight,enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertHighlight: Error occurred / "+ ex,ex);
		}
	}
	
	public void insertResource(String resourceName, String resourceDesc, String onCampusURL,
			String offCampusURL, int vendorId, String textId, String cdRom, 
			String trainingURL, String newsTitle, String newsURL, String accessNote,
			String passwordExist, String enabled, String[] selectedPlacements, 
			String[] selectedSubjects, String login, String password, String highlight, String alert,
			String refManagementURL
			) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextResourceSequence();
		
		/*String sql1 = " INSERT INTO LIBRESOURCE(RESID,RESNAME,RESDESCR," +
		" ENABLED,ONCAMPUSURL,OFFCAMPUSURL,VENDORID, "+
		" TXTID, CDROM, TRAINING, PASSWAVAIL, NEWSID, NEWSURL,LOGIN,PASSWORD," +
		" HIGHLIGHTID, ACCESSNOTE, ALERT) " +
		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?.?)";*/
		String tmpVendorId;
		if (vendorId == 0) {
			tmpVendorId = null;
		} else {
			tmpVendorId = Integer.toString(vendorId); 
		}

		String sql1 = "";
		if (newsTitle.equals("")&&(highlight.equals(""))) {
			sql1 = " INSERT INTO LIBRESOURCE(RESID,RESNAME,RESDESCR," +
			" ENABLED,ONCAMPUSURL,OFFCAMPUSURL,VENDORID, "+
			" TXTID, CDROM, TRAINING, PASSWAVAIL, NEWSID, NEWSURL,LOGIN,PASSWORD," +
			" HIGHLIGHTID, ACCESSNOTE, ALERT, REF_MANAGEMENT) " +
			" VALUES ("+nextSequence+",'"+resourceName+"','"+resourceDesc+"','"+enabled+"','"+onCampusURL+"',"+
			"'"+offCampusURL+"',"+tmpVendorId+","+textId+",'"+cdRom+"','"+trainingURL+"','"+passwordExist+"',null,"+
			"'"+newsURL+"','"+login+"','"+password+"',null,'"+accessNote+"','"+alert+"','"+refManagementURL+"')";
		} else if ((!newsTitle.equals(""))&&(highlight.equals(""))) {
			sql1 = " INSERT INTO LIBRESOURCE(RESID,RESNAME,RESDESCR," +
			" ENABLED,ONCAMPUSURL,OFFCAMPUSURL,VENDORID, "+
			" TXTID, CDROM, TRAINING, PASSWAVAIL, NEWSID, NEWSURL,LOGIN,PASSWORD," +
			" HIGHLIGHTID, ACCESSNOTE, ALERT, REF_MANAGEMENT) " +
			" VALUES ("+nextSequence+",'"+resourceName+"','"+resourceDesc+"','"+enabled+"','"+onCampusURL+"',"+
			"'"+offCampusURL+"',"+tmpVendorId+","+textId+",'"+cdRom+"','"+trainingURL+"','"+passwordExist+"','"+newsTitle+"',"+
			"'"+newsURL+"','"+login+"','"+password+"',null,'"+accessNote+"','"+alert+"','"+refManagementURL+"')";
		} else if ((newsTitle.equals(""))&&(!highlight.equals(""))) {
			sql1 = " INSERT INTO LIBRESOURCE(RESID,RESNAME,RESDESCR," +
			" ENABLED,ONCAMPUSURL,OFFCAMPUSURL,VENDORID, "+
			" TXTID, CDROM, TRAINING, PASSWAVAIL, NEWSID, NEWSURL,LOGIN,PASSWORD," +
			" HIGHLIGHTID, ACCESSNOTE, ALERT, REF_MANAGEMENT) " +
			" VALUES ("+nextSequence+",'"+resourceName+"','"+resourceDesc+"','"+enabled+"','"+onCampusURL+"',"+
			"'"+offCampusURL+"',"+tmpVendorId+","+textId+",'"+cdRom+"','"+trainingURL+"','"+passwordExist+"',null,"+
			"'"+newsURL+"','"+login+"','"+password+"','"+highlight+"','"+accessNote+"','"+alert+"','"+refManagementURL+"')";
		} else {
			sql1 = " INSERT INTO LIBRESOURCE(RESID,RESNAME,RESDESCR," +
			" ENABLED,ONCAMPUSURL,OFFCAMPUSURL,VENDORID, "+
			" TXTID, CDROM, TRAINING, PASSWAVAIL, NEWSID, NEWSURL,LOGIN,PASSWORD," +
			" HIGHLIGHTID, ACCESSNOTE, ALERT, REF_MANAGEMENT) " +
			" VALUES ("+nextSequence+",'"+resourceName+"','"+resourceDesc+"','"+enabled+"','"+onCampusURL+"',"+
					"'"+offCampusURL+"',"+tmpVendorId+","+textId+",'"+cdRom+"','"+trainingURL+"','"+passwordExist+"','"+newsTitle+"',"+
					"'"+newsURL+"','"+login+"','"+password+"','"+highlight+"','"+accessNote+"','"+alert+"','"+refManagementURL+"')";
		}

		
		System.out.println("insert: "+sql1);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
			/*			//jdt1.update(sql1, new Object[] {nextSequence, resourceName,resourceDesc,
								//enabled,onCampusURL,offCampusURL,vendorId, textId, cdRom, trainingURL,
								//passwordExist, newsTitle, newsURL, login, password, highlight, accessNote, alert });*/ 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertResource: Error occurred / "+ ex,ex);
		}
		
		// Insert linked placements
		for (int i = 0; i < selectedPlacements.length; i++) {
			insertResPlacement(Integer.toString(nextSequence),selectedPlacements[i]);
		}
		
		// Insert linked subjects
		for (int i = 0; i < selectedSubjects.length; i++) {
			insertResSubject(Integer.toString(nextSequence),selectedSubjects[i]);
		}

	}
	
	public void updateResource(int resourceId, String resourceName, String resourceDesc, String onCampusURL,
			String offCampusURL, int vendorId, String textId, String cdRom, 
			String trainingURL, String newsTitle, String newsURL, String accessNote,
			String passwordExist, String enabled, String[] selectedPlacements, 
			String[] selectedSubjects, String login, String password, String highlight, String alert, String refManagementURL
			) throws Exception {

			String tmpVendorId;
			if (vendorId == 0) {
				tmpVendorId = null;
			} else {
				tmpVendorId = Integer.toString(vendorId); 
			}
			
			String sql1 = "UPDATE LIBRESOURCE " +
			  " SET RESNAME = '"+resourceName+"'," +
			  " RESDESCR = '"+resourceDesc+"'," +
			  " ENABLED = '"+enabled+"'," +
			  " ONCAMPUSURL = '"+onCampusURL+"', " +
			  " OFFCAMPUSURL = '"+offCampusURL+"', " +
			  " VENDORID = "+tmpVendorId+", " +
			  " TXTID = "+textId+", "+
			  " CDROM = '"+cdRom+"', " +
			  " TRAINING = '"+trainingURL+"', " +
			  " REF_MANAGEMENT = '"+refManagementURL+"', "+
			  " PASSWAVAIL = '"+passwordExist+"', "+
			  " LOGIN = '"+login+"', " +
			  " PASSWORD = '"+password+"'," +
			  " ACCESSNOTE = '"+accessNote+"'," +
			  " ALERT = '"+alert+"'";

			if (highlight.length() > 0) {
				sql1 = sql1+", HIGHLIGHTID = "+highlight;
			} else {
				sql1 = sql1+", HIGHLIGHTID = null";
			}
			if (newsTitle.length() > 0) {
				sql1 = sql1+", NEWSID = "+newsTitle+", "+
				" NEWSURL = '"+newsURL+"' ";
			} else {
				sql1 = sql1+", NEWSID = null, "+
				" NEWSURL = '' ";
			}
			sql1 = sql1+  " WHERE RESID = "+resourceId;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateResource: Error occurred / "+ ex,ex);
		}
		
		// delete all linked placement
		deleteResPlacements(resourceId);
		// re-Insert new linked placements
		for (int i = 0; i < selectedPlacements.length; i++) {
			insertResPlacement(Integer.toString(resourceId),selectedPlacements[i]);
		}
		
		// delete all linked subjects
		deleteResSubj(resourceId);
		// re-Insert new linked subjects
		for (int i = 0; i < selectedSubjects.length; i++) {
			insertResSubject(Integer.toString(resourceId),selectedSubjects[i]);
		}
	}
	
	public void deleteResPlacements(int resId) throws Exception{

		String sql1 = " DELETE FROM LIBRESPLA " +
		  			  " WHERE  RESID = "+resId;
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: deleteResPlacements (Delete): Error occurred /"+ ex,ex);
		}
	}
	
	public void insertResPlacement(String resid, String placementid) throws Exception {
		
		String sql1 = "INSERT INTO LIBRESPLA(RESID, PLACEMENTID)" +
				" VALUES (?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {resid, placementid}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertResPlacement: Error occurred / "+ ex,ex);
		}
	}
	
	public void updateResPlacementDates(PlacementForm placement) throws Exception {
		
		String sql1 = "UPDATE LIBRESPLA " +
					" SET   STARTDATE= '"+placement.getFromDate()+"'," +
					"       ENDDATE = '"+placement.getEndDate()+"'" +
					" WHERE RESID = "+placement.getResId()+
					" AND   PLACEMENTID = "+placement.getPlacementId();
		
		System.out.println("updateResPlacement: "+sql1);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateResPlacementDates: Error occurred / "+ ex,ex);
		}
	}
	
	public void deleteResSubj(int resId) throws Exception{

		String sql1 = " DELETE FROM LIBRESSUBJ " +
		  			  " WHERE  RESID = "+resId;
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: deleteResSubj (Delete): Error occurred /"+ ex,ex);
		}
	}
	
	public void insertResSubject(String resid, String subjectid) throws Exception {
		
		String sql1 = "INSERT INTO LIBRESSUBJ(RESID, SUBJID)" +
				" VALUES (?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {resid, subjectid}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertResSubject: Error occurred / "+ ex,ex);
		}
	}
	
	public void insertNewsTitle(String newsTitleDesc, String enabled) throws Exception {
		int nextSequence = 0;

		// SELECT sequence
		nextSequence = selectNextNewsTitleSequence();
		
		
		String sql1 = " INSERT INTO LIBNEWSTITLE(NEWSID, NEWSTITLE,ENABLED) " +
				" VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {nextSequence, newsTitleDesc,enabled}); 
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: insertNewsTitle: Error occurred / "+ ex,ex);
		}
	}
	
	public void updatePlacement(int id, String placement, String enabled) throws Exception {

		String sql1 = " UPDATE LIBPLACEMENT" +
					  " SET   PLACEMENT = '"+placement+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE PLACEMENTID = "+id;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updatePlacement: Error occurred / "+ ex,ex);
		}
	}
	
	public void updatePlacementRanking(int plcRanking, int plcId) throws Exception {
	
		String sql1 = " UPDATE LIBPLACEMENT" +
					  " SET   displayorder = "+plcRanking+
					  " WHERE PLACEMENTID = "+plcId;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updatePlacement: Error occurred / "+ ex,ex);
		}
	}
	
	public void updateVendor(int id, String vendor, String onCampusURL, String offCampusURL,
			String logoFileName, String logoURL, String enabled) throws Exception {

		String sql1 = " UPDATE LIBVENDOR" +
					  " SET   VENDORNAME = '"+vendor+"',"+
					  "       ONCAMPUSURL = '"+onCampusURL+"',"+
					  "       OFFCAMPUSURL = '"+offCampusURL+"',"+
					  "       LOGOFILE = '"+logoFileName+"',"+
					  "       LOGOURL = '"+logoURL+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE VENDORID = "+id;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateVendor: Error occurred / "+ ex,ex);
		}
	}
	
	/*
	 * to get the records
	 */
	
public ArrayList selectSpecificdb() throws Exception {
		
		ArrayList vendorList = new ArrayList();
		
		removeSpaces();

		String select = " SELECT distinct (a.RESID), a.RESNAME,ISNULL(a.RESDESCR,'') AS RESDESCR, ISNULL(a.ONCAMPUSURL,'') AS ONCAMPUSURL,"+  
	                    " ISNULL(a.OFFCAMPUSURL,'') AS OFFCAMPUSURL,"+
	                    " ISNULL(a.ADDINFO,'') AS ADDINFO,"+
	                    " ISNULL(a.NEWSID,'') AS NEWSID, "+
	                    " ISNULL(a.TRAINING,'') AS TRAINING, "+
	                    "  ISNULL(a.VENDORID,'') AS VENDORID,"+
	                    " ISNULL((select LIBNEWSTITLE.NEWSTITLE FROM LIBNEWSTITLE WHERE a.NEWSID = LIBNEWSTITLE.NEWSID),'') as NEWSTITLE,"+
	                    " ISNULL(a.NEWSURL,'') AS NEWSURL,"+
	                    " ISNULL(d.VENDORNAME,'') AS VENDORNAME,"+ 
                        " ISNULL(d.OFFCAMPUSURL,'') AS VENOFFURL,"+
                        " ISNULL(d.ONCAMPUSURL,'') AS VENONURL,"+	                    
	                    " ISNULL(d.LOGOURL,'') AS LOGOURL,"+
	                    " ISNULL(d.LOGOFILE,'') AS LOGOFILE, "+
                        " isnull(a.HIGHLIGHTID,'') AS HIGHLIGHT, "+
    					" isnull((SELECT HIGHLIGHTDESC FROM LIBHIGHLIGHTNOTE WHERE a.HIGHLIGHTID = LIBHIGHLIGHTNOTE.HIGHLIGHTID),'') AS HIGHLIGHTDESC, " +
    					"(SELECT TEXTDESC FROM LIBTXT WHERE TXTID = a.TXTID) AS CONTENTTYPE, a.ALERT, " +
    					" a.ACCESSNOTE, a.REF_MANAGEMENT, e.ENDDATE, " +
    					" isnull(a.PASSWORD,'') as PASSWORD ,"+
    					" isnull(a.LOGIN,'') as LOGIN ," +
    					" e.placementid as placeId "+
	                    " FROM    LIBRESOURCE a, LIBVENDOR d, LIBRESPLA e"+
	                    " WHERE   a.VENDORID = d.VENDORID"+
	                    " AND e.RESID = a.RESID " +
	                    " and ISNULL(a.ENABLED,'Y') = 'Y'"+
	                    " order by a.resname";
	                    /* if(!(startChar == null)){
								select += " AND     upper(a.RESNAME) like '"+startChar+"%'";
							}*/
	
		
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
    			LibResourceDetails form = new LibResourceDetails();
	    		logo =   data.get("LOGOFILE").toString();
	    	    url =  data.get("LOGOURL").toString();
	    	    form.setLogoFile(logo);
	    	    form.setLogoUrl(url);
	    	    vlogo = sub+url+logo; // build url for vendor logo
	    	    form.setvLogo(vlogo);// vendor logo
	    	    //form.setDlogo(vlogo);
	    		form.setResName(data.get("RESNAME").toString());
    			form.setVendorName(data.get("VENDORNAME").toString());
	    		resId = data.get("RESID").toString(); // RESOURCE id
	    		form.setResId(Integer.parseInt(resId));
	    		form.setAddInfo(data.get("ADDINFO").toString());
    			form.setResDesr(data.get("RESDESCR").toString());
    			String tmpLogin = data.get("LOGIN").toString();
    			form.setLogIn(tmpLogin);
    			form.setPassword(data.get("PASSWORD").toString());
    			form.setLogIn(data.get("LOGIN").toString());
    			if ((!tmpLogin.equals("")) && (!tmpLogin.equals(" "))) {
    				form.setViewPassword("Login: "+data.get("LOGIN").toString()+"; Password: "+data.get("PASSWORD").toString());
    				
    			}
	    		   			
    			/* Is it a  trial database, if it is add trial expires as a highlight note */
    			placement = getPlacement(resId, form, "10");
    			

    			// set urls according to offcampus or oncampus ip
    			/*try{
    				if(ipAddress.substring(0, 7).equals(ip1)||ipAddress.substring(0,8).equals(ip2)){
	    			//if(ipAddress.equals(ip1)||ipAddress.equals(ip2)){
	    				form.setDcampusUrl(data.get("ONCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENONURL").toString());
	    				form.setCampusUrl(data.get("ONCAMPUSURL").toString());
	    			}else{
	    				form.setDcampusUrl(data.get("OFFCAMPUSURL").toString());
	    				form.setVcampusUrl(data.get("VENOFFURL").toString());
	    				form.setCampusUrl(data.get("OFFCAMPUSURL").toString());	    			}
	   			}catch(NullPointerException ne){
			  		  
	   			}*/
	   		
		   		try{
		   			 if(!(data.get("TRAINING").toString().equals(" "))){
		   				form.setdTraining(data.get("TRAINING").toString());
			   			form.settTtraining(data.get("TRAINING").toString());}
	   			}catch(NullPointerException ne){
					  		  
	   			}

                 form.setOnCampusUrl(data.get("ONCAMPUSURL").toString());
                 form.setOffCampusUrl(data.get("OFFCAMPUSURL").toString());
             	 form.setVenOnUrl(data.get("VENONURL").toString());
				 form.setVenOffUrl(data.get("VENOFFURL").toString());	
				
	   			String tmpNewsUrl = data.get("NEWSURL").toString();
	   			if ((!tmpNewsUrl.equals(""))&&(!tmpNewsUrl.equals(" "))){
	   				form.setNewsUrl(data.get("NEWSURL").toString());
	   				form.setNewsTitle(data.get("NEWSTITLE").toString());
	   			}
	   			
	   			try{
				   	form.setAlert(data.get("ALERT").toString());
	   			} catch(NullPointerException ne){
	   			}

	   			if(!(data.get("ACCESSNOTE").toString().equals("n/a"))){
	    			form.setAccessNote(data.get("ACCESSNOTE").toString());
	    		}
	   			
	    		try{
				   	form.setRefManagement(data.get("REF_MANAGEMENT").toString());
	   			} catch(NullPointerException ne){
	   			}
	   			   			
	   			//form.setViewPassword(getPassword(resId));	
	   			if (form.getExpiryDate()!= null) {
	   				form.setHighlight("Trial Expires: "+form.getExpiryDate());
	   			} else {
	   				form.setHighlight(data.get("HIGHLIGHT").toString());
	   			}
	   			form.setHighlightDesc(data.get("HIGHLIGHTDESC").toString());
			   	form.setContentType(data.get("CONTENTTYPE").toString());
			   	int placeid =Integer.parseInt(data.get("placeId").toString());
			   	form.setPlacementID(placeid);
	    		vendorList.add(form);
    		}// end while
    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourceDAO: selectVendorList(): Error occurred / "+ ex,ex);
    	}// end try
    	
    	return vendorList;
	}


   public void atozInsert(ArrayList resourceList) throws Exception {
	
	   boolean exist=false;
	   deleteATOZ();
	   
	   try{
		   
	   JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());

	   for(int i=0;i<resourceList.size();i++){
		     LibResourceDetails libResourceDetails = (LibResourceDetails)resourceList.get(i);
		     libResourceDetails.getResId();
		 
	     String sql1 = "INSERT INTO ATOZ(ResID, ResDescr, LogoFile, LogoURL, VLogo, OnCampusUrl, OffCampusUrl, VENOFFURL, VENONURL, ViewPassword," +
	     		    " ResName, VendorName, AddInfo, LogIn, Password, " +
	  		        " DTraining, TTraining, NewsURL, NewsTitle, Alert, AccessNote, RefManagement, Highlight, HighlightDesc, " +
	  		        " ContentType, PlacementID)"+
	  		        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	     
		   jdt1.update(sql1, new Object[] {libResourceDetails.getResId(), libResourceDetails.getResDesr(), libResourceDetails.getLogoFile(), libResourceDetails.getLogoUrl(), libResourceDetails.getvLogo(),
				   libResourceDetails.getOnCampusUrl(), libResourceDetails.getOffCampusUrl(), libResourceDetails.getVenOffUrl(),
				   libResourceDetails.getVenOnUrl(), libResourceDetails.getViewPassword(),
				   libResourceDetails.getResName(), libResourceDetails.getVendorName(),libResourceDetails.getAddInfo(),libResourceDetails.getLogIn(),
				   libResourceDetails.getPassword(), libResourceDetails.getdTraining(), libResourceDetails.gettTtraining(),libResourceDetails.getNewsUrl(),libResourceDetails.getNewsTitle(),
				   libResourceDetails.getAlert(), libResourceDetails.getAccessNote(), libResourceDetails.getRefManagement(), libResourceDetails.getHighlight(), libResourceDetails.getHighlightDesc(),
				   libResourceDetails.getContentType(), libResourceDetails.getPlacementID()}); 
	       } 
	    
	   	}
   
	   catch (Exception ex) {
		      throw new Exception("LibraryEResourcesDAO: insert ATOZ: Error occurred / "+ ex,ex);
	       }
   }
   
	public void deleteATOZ() throws Exception{
		
			String sql = " TRUNCATE TABLE ATOZ";
			
			try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				jdt.update(sql);
			} catch (Exception ex) {
				throw new Exception("LibraryEResourcesDAO: deleteATOZ (Delete): Error occurred /"+ ex,ex);
			}
		

	}
             
	public boolean isRecordExist(int resID, String resDescr, String logoFile, String logoURL, String resName, String vendorName, String addInfo, String logIn, String password, 
		        String dTraining, String tTraining, String newsURL, String newsTitle, String alert, String accessNote, String refManagement, String highlight, String highlightDesc, 
  		        String contentType, int placementID) throws Exception{
		
	
		
		String query = "SELECT count(*) as COUNT from ATOZ where Resid ="+resID+
                       " and ResDescr ="+"'"+resDescr+"'"+
                       " and LogoFile ="+"'"+logoFile+"'"+
                       " and LogoURL ="+"'"+logoURL+"'"+
                       " and ResName ="+"'"+resName+"'"+
                       " and VendorName ="+"'"+vendorName+"'"+
                       " and AddInfo ="+"'"+addInfo+"'"+
                       " and LogIn ="+"'"+logIn+"'"+
                       " and Password ="+"'"+password+"'"+
                       " and DTraining ="+"'"+dTraining+"'"+
                       " and TTraining ="+"'"+tTraining+"'"+
                       " and NewsURL ="+"'"+newsURL+"'"+
                       " and NewsTitle ="+"'"+newsTitle+"'"+
                       " and Alert ="+"'"+alert+"'"+
                       " and AccessNote ="+"'"+accessNote+"'"+
                       " and RefManagement ="+"'"+refManagement+"'"+
                       " and Highlight ="+"'"+highlight+"'"+
                       " and HighlightDesc ="+"'"+highlightDesc+"'"+
                       " and ContentType ="+"'"+contentType+"'"+
                       " and PlacementID ="+placementID;
 
        	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 
        		if(counter > 0){
        			return true;
        		}else{
        			return false;
        		}
        		
        		
        	} catch (Exception ex) {
        		throw new Exception("isRecordExist ATOZ / "+ ex,ex);
        		
        	}
        	
		
    }


	
	public void updateSubject(int id, String subject, String enabled) throws Exception {

		String sql1 = " UPDATE LIBSUBJ" +
					  " SET   SUBJECT = '"+subject+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE SUBJID = "+id;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateSubject: Error occurred / "+ ex,ex);
		}
	}
	
	public void updateHighlight(int id, String highlight, String enabled) throws Exception {

		String sql1 = " UPDATE LIBHIGHLIGHTNOTE" +
					  " SET   HIGHLIGHTDESC = '"+highlight+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE HIGHLIGHTID = "+id;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateHighlight: Error occurred / "+ ex,ex);
		}
	}
	
	public void updateNewsTitle(int id, String newsTitle, String enabled) throws Exception {

		String sql1 = " UPDATE LIBNEWSTITLE" +
					  " SET   NEWSTITLE = '"+newsTitle+"',"+
					  "       ENABLED = '"+enabled+"'"+
					  " WHERE NEWSID = "+id;

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("LibraryEResourcesDAO: updateNewsTitle: Error occurred / "+ ex,ex);
		}
	}


	public int selectNextTxtSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(TXTID)+1 AS SEQ FROM LIBTXT ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextTxtSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextPlacementSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(PLACEMENTID)+1 AS SEQ FROM LIBPLACEMENT ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextPlacementSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextVendorSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(VENDORID)+1 AS SEQ FROM LIBVENDOR ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextVendorSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextSubjectSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(SUBJID)+1 AS SEQ FROM LIBSUBJ ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextSubjectSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextHighlightSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(HIGHLIGHTID)+1 AS SEQ FROM LIBHIGHLIGHTNOTE ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextHighlightSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextNewsTitleSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(NEWSID)+1 AS SEQ FROM LIBNEWSTITLE ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextNewsTitleSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
	}
	
	public int selectNextResourceSequence() throws Exception {
		int sequence = 0;
		
		String select = " SELECT MAX(RESID)+1 AS SEQ FROM LIBRESOURCE ";
		
    	try{
    		String sequenceTmp = this.querySingleValue(select,"SEQ");
    		if (sequenceTmp.equals("")) {
    			sequence = 0;
    		} else {
    			sequence = Integer.parseInt(sequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("LibraryEResourcesDAO: selectNextResourceSequence: Error occurred / "+ ex,ex);
    	} // end try

		return sequence;
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
 	   return result;
	}
	
	
	public ArrayList getPlacement(String resID, LibResourceDetails form, String specificPlacement){
		LibResourceDetails resourceForm = (LibResourceDetails) form;
		
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
	
	public void removeSpaces(){
		
		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		
		String alerts = "UPDATE LIBRESOURCE SET REF_MANAGEMENT = NULL WHERE REF_MANAGEMENT = ''",
			   refManagement = "UPDATE LIBRESOURCE SET ALERT = NULL WHERE ALERT = ''";
		
		jdt.update(alerts);
		jdt.update(refManagement);
	}
}
