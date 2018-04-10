package za.ac.unisa.lms.tools.brochures.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MyChoiceMDXmlBuilder extends MychoiceDAO {
	
	
	public Document buildXmlFile(String query) throws ParserConfigurationException {
		
		 DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		 Document document = documentBuilder.newDocument();
		 JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		 List qualInfo = jdt.queryForList(query);
		 Iterator i = qualInfo.iterator();
	
		Element colleges = document.createElement("colleges");
		document.appendChild(colleges);
			
		List<ListOrderedMap> cems = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> claw = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> cohs = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> coe = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> cset = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> caes = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> cas = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> acc = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> cgs = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> icb = new LinkedList<ListOrderedMap>();
		List<ListOrderedMap> other = new LinkedList<ListOrderedMap>();
		
		
		while (i.hasNext()) {
			
			ListOrderedMap data = (ListOrderedMap) i.next();
		
					
			if (data.get("code").toString().equals("3")) {
				cems.add(data);							
			}
			
			if (data.get("code").toString().equals("1")) {
				claw.add(data);
			}
			
			if (data.get("code").toString().equals("2")) {
				cohs.add(data);
			}
			
			if (data.get("code").toString().equals("9")) {
				coe.add(data);
			}
			
			if (data.get("code").toString().equals("4")) {
				cset.add(data);
			}
			
			if (data.get("code").toString().equals("5")) {
				caes.add(data);
			}
			
			if (data.get("code").toString().equals("6")) {
				other.add(data);
			}
			
			if (data.get("code").toString().equals("10")) {
				cgs.add(data);
			}
			
			if (data.get("code").toString().equals("11")) {
				cas.add(data);
			}
			
			if (data.get("code").toString().equals("8")) {
				icb.add(data);
			}
			
			if (data.get("code").toString().equals("7")) {
				acc.add(data);
			}
			
		}
		
		if (!caes.isEmpty()) {
			
			document = createColleges(caes, document, colleges);
		}
		if (!coe.isEmpty()) {
			
			document = createColleges(coe, document, colleges);			
		}
		if (!cems.isEmpty()) {
		
			document = createColleges(cems, document, colleges);
		}
		if (!cohs.isEmpty()) {
			
			document = createColleges(cohs, document, colleges);			
		}
		if (!claw.isEmpty()) {
			
			document = createColleges(claw, document, colleges);
		}
		if (!cset.isEmpty()) {
			
			document = createColleges(cset, document, colleges);			
		}
		
		if (!other.isEmpty()) {
			
			document = createColleges(other, document, colleges);			
		}
		if (!cgs.isEmpty()) {
	
			document = createColleges(cgs, document, colleges);			
		}
		if (!cas.isEmpty()) {
	
			document = createColleges(cas, document, colleges);			
		}
		if (!icb.isEmpty()) {
	
			document = createColleges(icb, document, colleges);			
		}
		if (!acc.isEmpty()) {
	
			document = createColleges(acc, document, colleges);			
		}
									    
		  return document;
	}
	
	public Document createColleges(List qualInfo, Document document, Element colleges) {
		
		
		String collegeName = "";
		String schoolName = "";
		String department = "";
		String collcode = "";
		String schoolCode = "";
		String deptCode = "";
		String qual = "";
		String qualificationLevel = "";    
		String qualificationType = "";
		String curricu="";
		String qualificationGroup ="";
		String qualificationCategory ="";
		String cat_code = "";
		
				
		Element qualification = null;
		Element qualLevel = null;
		Element college = null;
		Element school = null;
		Element dpt = null;
		Element qualificationtype = null;
		Element qualificationgroup = null;
		Element qualificationcategory = null;
		
		Iterator i = qualInfo.iterator();
		
		while (i.hasNext()) {
			
			ListOrderedMap data = (ListOrderedMap) i.next();
		
		String qual_repeat = data.get("qual_repeat").toString();
		String spec_repeat  = data.get("spec_repeat").toString();
		
		if (!(qual_repeat.equals("R")||spec_repeat.equals("R"))) {
			

			if (! collegeName.equals(data.get("COLLEGE_DESCR").toString())) {
				
				college = document.createElement("college");
				colleges.appendChild(college);
				
				collegeName = data.get("COLLEGE_DESCR").toString();
				collcode = data.get("code").toString();
				Element  collName= document.createElement("name");
				Element  collCode= document.createElement("code");
			    collName.appendChild(document.createTextNode(collegeName));
			    collCode.appendChild(document.createTextNode(collcode));
			    college.appendChild(collName);
			    college.appendChild(collCode);
			}
			
		/*	String tempDescr;
			try {
			 tempDescr = data.get("SCHOOLDESCR").toString();
			} catch (NullPointerException ne) {
				tempDescr="Unknown";
			}
						
				if (! schoolName.equals(tempDescr)) {
					
					school = document.createElement("School");
					college.appendChild(school);
					
					schoolName = tempDescr;
					schoolCode = data.get("schoolcode").toString();
					Element schName= document.createElement("name");
					Element schoolCode1 = document.createElement("code");
					schName.appendChild(document.createTextNode(schoolName));
					schoolCode1.appendChild(document.createTextNode(schoolCode));
					school.appendChild(schName);
					school.appendChild(schoolCode1);
				}
			
			String tempDept;
			String tempDptCode;
			try{
				tempDept = data.get("dept").toString();
			}catch(NullPointerException ne){
				tempDept = "Unknown";
			}
			try{
				tempDptCode = data.get("deptcode").toString();
			}catch(NullPointerException ne){
				tempDptCode = "Unknown";
			}
			if (! department.equals(tempDept)) {
				
				dpt = document.createElement("department");
				school.appendChild(dpt);
				
				department = tempDept;
				deptCode = tempDptCode;
				Element dept= document.createElement("name");
				Element dept_code = document.createElement("code");
				dept.appendChild(document.createTextNode(department));
				dept_code.appendChild(document.createTextNode(deptCode));
				dpt.appendChild(dept);
				dpt.appendChild(dept_code); 
			}*/
		
			if (! qualificationLevel.equals(ltrim(rtrim(data.get("UNDER_POST").toString())))) {
				
				qualificationLevel = data.get("UNDER_POST").toString();
				qualLevel = document.createElement("qualificationlevel");
			    
				college.appendChild(qualLevel);
			    
				    Element level = document.createElement("name");
				    level.appendChild(document.createTextNode(data.get("UNDER_POST").toString()));		    
				    qualLevel.appendChild(level);
			    
				/*qualificationtype = document.createElement("qualificationtype");
				Element name = document.createElement("name");
				try {
				name.appendChild(document.createTextNode(data.get("TYPE").toString()));
				qualificationtype.appendChild(name);
				
				qualLevel.appendChild(qualificationtype);
				
				} catch (NullPointerException e) {}*/
		
			}
				if (! qualificationGroup.equals(ltrim(rtrim(data.get("CATEGORY_GROUP").toString())))) {
					
					qualificationGroup = data.get("CATEGORY_GROUP").toString();
					cat_code = data.get("CAT_GRP").toString();
					
				    qualificationgroup = document.createElement("qualificationgroup");
				    qualLevel.appendChild(qualificationgroup);
				    
				    Element qualGroup= document.createElement("name");
				    qualGroup.appendChild(document.createTextNode(qualificationGroup));
				    Element catcode= document.createElement("code");
				    catcode.appendChild(document.createTextNode(cat_code));
				    qualificationgroup.appendChild(qualGroup);
				    qualificationgroup.appendChild(catcode);

				    }
				if (! qualificationCategory.equals(data.get("CAT_NAME").toString())) {
					
				        qualificationCategory =data.get("CAT_NAME").toString();
				        qualificationcategory = document.createElement("qualificationcategory");
				        qualificationgroup.appendChild(qualificationcategory);
				        Element qualCat= document.createElement("name");
				        qualCat.appendChild(document.createTextNode(qualificationCategory));
				        qualificationcategory.appendChild(qualCat);
				} 
			
					qualification = document.createElement("qualification");
				    qualificationcategory.appendChild(qualification);
				    				    
				    Element qualName = document.createElement("name");
				    qualName.appendChild(document.createTextNode(data.get("QUAL_DESCR").toString()));
				    
				    Element curriculum = document.createElement("curriculum");
				    try {

				    	curricu= data.get("RULE LINE1").toString()+" "+data.get("RULE LINE2").toString()+" "+data.get("RULE LINE3").toString()
			    		+" "+data.get("RULE LINE4").toString()+" "+data.get("RULE LINE5").toString()+" "+data.get("RULE LINE6").toString()
			    		+" "+data.get("RULE LINE7").toString()+" "+data.get("RULE LINE8").toString()+" "+data.get("RULE LINE9").toString()
			    		+" "+data.get("RULE LINE10").toString()+" "+data.get("RULE LINE11").toString()+" "+data.get("RULE LINE12").toString()
			    		+" "+data.get("RULE LINE13").toString()+" "+data.get("RULE LINE14").toString()+" "+data.get("RULE LINE15").toString()
			    		+" "+data.get("RULE LINE16").toString()+" "+data.get("RULE LINE17").toString()+" "+data.get("RULE LINE18").toString()
			    		+" "+data.get("RULE LINE19").toString()+" "+data.get("RULE LINE20").toString()+" "+data.get("RULE LINE21").toString()
			    		+" "+data.get("RULE LINE22").toString()+" "+data.get("RULE LINE23").toString()+" "+data.get("RULE LINE24").toString()
			    		+" "+data.get("RULE LINE25").toString()+" "+data.get("RULE LINE26").toString()+" "+data.get("RULE LINE27").toString()
			    		+" "+data.get("RULE LINE28").toString()+" "+data.get("RULE LINE29").toString()+" "+data.get("RULE LINE30").toString()
			    		+" "+data.get("RULE LINE31").toString();
                        String curriculum_spaces = rtrim(curricu);
               
				    	curriculum.appendChild(document.createTextNode(curriculum_spaces));

				    
				    } catch(NullPointerException e) {}	
				    
				    Element qualCode = document.createElement("code");
				    qualCode.appendChild(document.createTextNode(data.get("QUAL_CODE").toString()));
			
				    Element spesDesc = document.createElement("specDesc");
				    spesDesc.appendChild(document.createTextNode(data.get("SPEC_DESCR").toString()));
				    
				    Element spes = document.createElement("spec");
				    spes.appendChild(document.createTextNode(data.get("SPEC_CODE").toString()));
				 
				    Element admission = document.createElement("admission");				    
				    try{
				    String temp = rtrim(data.get("admission_require").toString());
				    String[]strList;
				    strList = temp.split("\n");
				    for(int j=0;j<strList.length;j++){
				    	 Element line = document.createElement("line"+(j+1));
				    	 line.appendChild(document.createTextNode(strList[j]));
				    	 admission.appendChild(line);
				    }
				    }catch(NullPointerException e){}
				    
				    try{
				    admission.appendChild(document.createTextNode(rtrim(data.get("admission_require").toString())));
				    }catch(NullPointerException e){
				    	
				    }
				    Element credits = document.createElement("credits");
				    credits.appendChild(document.createTextNode(data.get("NQF_CREDITS").toString()));
				    
				    Element nqfl = document.createElement("nqf");
				    nqfl.appendChild(document.createTextNode(data.get("NQF_EXIT_LEVEL").toString()));
				    
				    //New APS Score Element
					/*Element apsScore = document.createElement("aps_score");
					
					try {			
						apsScore.appendChild(document.createTextNode(data.get(
								"aps_score").toString()));

					} catch (NullPointerException e) {
						
					}*/
				    
				    Element research = document.createElement("research");
				    try{
				    research.appendChild(document.createTextNode(data.get("research").toString()));
				    }catch(NullPointerException ne){}
			
				  //SAQA ID
					Element saqaId = document.createElement("saqa_id");
					try {
						saqaId.appendChild(document.createTextNode(data.get(
								"approved_qual_id").toString()));
					} catch (NullPointerException ne) {
					}				    			    
				    qualification.appendChild(qualName);
				    qualification.appendChild(curriculum);
				    qualification.appendChild(qualCode);
				    qualification.appendChild(spes);
				    qualification.appendChild(spesDesc);
				    qualification.appendChild(admission);
				    qualification.appendChild(credits);
				    qualification.appendChild(nqfl);
				  /*  qualification.appendChild(apsScore);*/
				    qualification.appendChild(saqaId);
				    qualification.appendChild(research);
				    
				  
		}
		}
		  return document;
	}

}
