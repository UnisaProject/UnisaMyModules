package za.ac.unisa.lms.tools.cronjobs.dao;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class BrochureXMLGeneratorDAO extends StudentSystemDAO{
	
	public void generateXML(int collegeCode) throws Exception{
		String query = 	"SELECT   colleg.abbreviation coll, under_post_categor quallvl, "+
        "qspsun.mk_qual_code qual, "+
        "colleg.eng_description coll_name, kat.eng_description qual_type, quaspc.mk_qualification_c qual_code, sun.code moduleCode, "+
		"grd.long_eng_descripti qual_type_grp, "+
		"nvl(ppreq_comment,' ') AS pre_req, "+
        "NLS_INITCAP (grd.long_eng_descripti) qualdesc, "+
        "CASE "+
           "WHEN grd.repeaters_from_yea BETWEEN 1 AND 2012 "+
              "THEN 'R' "+
           "ELSE ' ' "+
        "END qual_repeat, "+
        "CASE "+
           "WHEN quaspc.speciality_code > ' ' "+
              "THEN quaspc.speciality_code "+
           "ELSE ' ' "+
        "END spes, "+
        "CASE "+
           "WHEN quaspc.speciality_code > ' ' "+
              "THEN NLS_INITCAP (quaspc.english_descriptio) "+
           "ELSE ' ' "+
        "END spesdesc, "+
        "CASE "+
           "WHEN quaspc.repeaters_from_yea BETWEEN 1 AND 2012 "+
            "OR quaspc.repeaters_only = 'Y' "+
              "THEN 'R' "+
           "ELSE ' ' "+
        "END spec_repeat, "+
        "grd.TYPE qualtype, fk_katcode cat, kat.eng_description catname, "+
        "nqf_exit_level nqflvl, grd.nqf_credits creds, qspsun.study_level lvl, "+
        "qspsun.group0 grp, mk_study_unit_code module, "+
        "REPLACE "+
           "(REPLACE (REPLACE (REPLACE (NLS_INITCAP (sun.eng_long_descripti), "+
                                       "'Iii', "+
                                       "'III' "+
                                      "), "+
                              "'Ii', "+
                              "'II' "+
                             "), "+
                     "'Iv', "+
                     "'IV' "+
                    "), "+
            "'Llb', "+
            "'LLB' "+
           ") module1, "+
        "ppreq_comment, nvl(admission_require,' ') admission_req, "+
        "CASE "+
           "WHEN (SELECT    TRIM (grp_description) "+
                        "|| '. ' "+
                        "|| grp_instruction "+
                    "FROM qspgrp "+
                    "WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c "+
                    "AND qspgrp.mk_spes_code = quaspc.speciality_code "+
                    "AND qspgrp.level0 = qspsun.study_level "+
                    "AND qspgrp.group0 = qspsun.group0) = '. ' "+
              "THEN 'No group' "+
           "ELSE (SELECT REPLACE (   TRIM (grp_description) "+
                                 "|| '. ' "+
                                 "|| grp_instruction, "+
                                 "':.', "+
                                 "':' "+
                                ") "+
                    "FROM qspgrp "+
                    "WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c "+
                    "AND qspgrp.mk_spes_code = quaspc.speciality_code "+
                    "AND qspgrp.level0 = qspsun.study_level "+
                    "AND qspgrp.group0 = qspsun.group0) "+
        "END groupname, "+
        "grd.to_year, quaspc.to_year to_yearS,nvl(substr(quaspc.comments,1,1),' ') rules "+
    "FROM quaspc, grd, qspsun, sun, colleg, kat, qspgrp "+
    "WHERE quaspc.in_use_flag = 'Y' "+
    "AND quaspc.mk_qualification_c = grd.code and  "+
    "not (QUASPC.MK_QUALIFICATION_C='98574'  "+
    "or (QUASPC.MK_QUALIFICATION_C in ('04332','04553','04413','05827','0555X','05258')  "+
    "and QUASPC.SPECIALITY_CODE like '%11')) "+
    "and quaspc.college_code = "+collegeCode+" "+ // Verander college: 1:CLAW, 2:CHS, 3:CEMS, 4:CSET, 5:CAES "+
    "AND quaspc.mk_qualification_c NOT IN "+
             "('02402', 'NDACG', 'NDSBM', 'NDTRA', 'BTBAN', '05096', '05282','02690') "+
    "AND NOT (    quaspc.mk_qualification_c = 'BTCOP' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '05193' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '05207' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '05738' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '05746' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = 'NDCSM' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
//    "AND NOT (    quaspc.mk_qualification_c = '02143' "+
//             "AND quaspc.speciality_code = 'COM' "+
//            ") "+
//    "AND NOT (    quaspc.mk_qualification_c = '02143' "+
//             "AND quaspc.speciality_code = 'GEN' "+
//           " ) "+
    "AND NOT (    quaspc.mk_qualification_c = '0460X' "+
             "AND quaspc.speciality_code = 'OLD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0460X' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0331X' "+
             "AND quaspc.speciality_code = 'CMN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0331X' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0331X' "+
             "AND quaspc.speciality_code = 'HMN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02089' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02089' "+
             "AND quaspc.speciality_code = 'MAM' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02089' "+
             "AND quaspc.speciality_code = 'MMA' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '98801' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02674' "+
             "AND quaspc.speciality_code = 'OLD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02682' "+
             "AND quaspc.speciality_code = 'OLD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02038' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02038' "+
             "AND quaspc.speciality_code = 'GE1' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02291' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02291' "+
             "AND quaspc.speciality_code = 'PST' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02291' "+
             "AND quaspc.speciality_code = 'IRD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02291' "+
             "AND quaspc.speciality_code = 'PSD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02291' "+
             "AND quaspc.speciality_code = 'GAD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02283' "+
             "AND quaspc.speciality_code = 'CRW' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02305' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02305' "+
             "AND quaspc.speciality_code = 'MMV' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02305' "+
             "AND quaspc.speciality_code = 'MMA' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02305' "+
             "AND quaspc.speciality_code = 'MMC' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02054' "+
             "AND quaspc.speciality_code = ' ' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02070' "+
             "AND quaspc.speciality_code = 'N03' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02135' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02135' "+
             "AND quaspc.speciality_code = 'GE9' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0216X' "+
             "AND quaspc.speciality_code = 'HMC' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '0216X' "+
             "AND quaspc.speciality_code = 'HSM' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02011' "+
             "AND quaspc.speciality_code = 'GEN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02011' "+
             "AND quaspc.speciality_code = 'MUN' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02011' "+
             "AND quaspc.speciality_code = 'OLD' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02259' "+
             "AND quaspc.speciality_code = 'LAW' "+
            ") "+
    "AND NOT (    quaspc.mk_qualification_c = '02003' "+
             "AND quaspc.speciality_code = 'OLD' "+
            ") "+
    "AND qspgrp.mk_qual_code = quaspc.mk_qualification_c "+
    "AND qspgrp.mk_spes_code = quaspc.speciality_code "+
    "AND qspgrp.level0 = qspsun.study_level "+
    "AND qspgrp.group0 = qspsun.group0 "+
    "AND qspgrp.grp_description NOT LIKE '%A & B%' "+
    "AND grd.in_use_flag = 'Y' "+
    "AND (grd.to_year = 0 OR grd.to_year > 2011) "+
    "AND grd.from_year < 2013 "+
    "AND grd.TYPE <> 'S' "+
    "AND under_post_categor IN ('U', 'H') "+
    "AND qspsun.mk_qual_code = grd.code "+
    "AND quaspc.college_code = colleg.code "+
    "AND fk_katcode = kat.code "+
    "AND qspsun.mk_spes_code = quaspc.speciality_code "+
    "AND (qspsun.to_year = 0 OR qspsun.to_year > 2011) "+
    "AND qspsun.from_year < 2013 "+
    "AND qspsun.frequently_used = 'Y' "+
    "AND mk_study_unit_code = sun.code "+
    "AND sun.in_use_flag = 'Y' "+
    "AND sun.from_year < 2013 "+
    "AND (sun.to_year = 0 OR sun.to_year > 2011) "+
    "ORDER BY abbreviation, "+
        "grd.TYPE, "+
        "fk_katcode, "+
        "qspsun.mk_qual_code, "+
        "qspsun.mk_spes_code, "+
        "qspsun.study_level, "+
        "qspsun.group0, "+
        "mk_study_unit_code ";
		
		try{
			 DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			 

			 Document document = documentBuilder.newDocument();
			 			 
			
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List qualInfo = jdt.queryForList(query);
			Iterator i = qualInfo.iterator();
			
			String filename = "";
			String collegeName = "";
			String qualTypeGrp = "";
			String qualType = "";
			String qual = "";
			String qualificationLevel = "";
			String qualificationChoice = "";
										
			Element college = document.createElement("college");
			Element qualTypeGrpElement = null;
			Element qualTypeElement = null;
			Element qualification = null;
			Element qualLevel = null;
			Element qualChoice = null;
			Element module = null;
			
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				
				if (! collegeName.equals(data.get("coll_name").toString())){
					filename = data.get("coll").toString()+".xml";
					document.appendChild(college);
					
					collegeName = data.get("coll_name").toString();
					Element  collName= document.createElement("name");
				    collName.appendChild(document.createTextNode(data.get("coll_name").toString()));
				    
				    college.appendChild(collName);
				}
				
				if (! qualTypeGrp.equals(data.get("qualType").toString())){
					qualTypeGrp = data.get("qualType").toString();
									    
					qualTypeGrpElement = document.createElement("qualificationstypesgroup");
					college.appendChild(qualTypeGrpElement);
				    
				    Element qualTypeGrpName = document.createElement("name");
				    qualTypeGrpName.appendChild(document.createTextNode(data.get("qualType").toString()));
				    qualTypeGrpElement.appendChild(qualTypeGrpName);
				}
				
				if (! qualType.equals(data.get("qual_type").toString())){
					qualType = data.get("qual_type").toString();
					qualTypeElement = document.createElement("qualtype");
					qualTypeGrpElement.appendChild(qualTypeElement);
				    
				    Element qualTypeName = document.createElement("name");
				    qualTypeName.appendChild(document.createTextNode(data.get("qual_type").toString()));
				    qualTypeElement.appendChild(qualTypeName);
				}
						
				if (! qual.equals(data.get("qualdesc").toString())){
					qual = data.get("qualdesc").toString();
				    qualification = document.createElement("qualification");
				    qualTypeElement.appendChild(qualification);
				    				    
				    Element qualName = document.createElement("name");
				    qualName.appendChild(document.createTextNode(data.get("qualdesc").toString()));
				    				    
				    Element qualCode = document.createElement("qualcode");
				    qualCode.appendChild(document.createTextNode(data.get("qual_code").toString()));
				    				    
				    Element admission = document.createElement("admission");
				    admission.appendChild(document.createTextNode(data.get("admission_req").toString()));
				    				    
				    Element spesCode = document.createElement("spescode");
				    spesCode.appendChild(document.createTextNode(data.get("spes").toString()));
				    				    
				    Element spesDesc = document.createElement("spesdesc");
				    spesDesc.appendChild(document.createTextNode(data.get("spesdesc").toString()));
				    				    
				    Element nqfEL = document.createElement("NQFel");
				    nqfEL.appendChild(document.createTextNode(data.get("nqflvl").toString()));
				    				    
				    Element nqfC = document.createElement("NQFc");
				    nqfC.appendChild(document.createTextNode(data.get("creds").toString()));
				    				    
				    Element qualRep = document.createElement("qualrep");
				    qualRep.appendChild(document.createTextNode(data.get("qual_repeat").toString()));
				    				    
				    Element specRep = document.createElement("spesrep");
				    specRep.appendChild(document.createTextNode(data.get("spec_repeat").toString()));
				    				    
				    Element toYear = document.createElement("toyear");
				    toYear.appendChild(document.createTextNode(data.get("to_year").toString()));
				    				    
				    Element toYearS = document.createElement("toyears");
				    toYearS.appendChild(document.createTextNode(data.get("to_yearS").toString()));
				    
				    Element rules = document.createElement("rules");
				    rules.appendChild(document.createTextNode(data.get("rules").toString()));
				     				    
				    qualification.appendChild(qualName);
				    qualification.appendChild(qualCode);
				    qualification.appendChild(admission);
				    qualification.appendChild(spesCode);
				    qualification.appendChild(spesDesc);
				    qualification.appendChild(nqfEL);
				    qualification.appendChild(nqfC);
				    qualification.appendChild(qualRep);
				    qualification.appendChild(specRep);
				    qualification.appendChild(toYear);
				    qualification.appendChild(toYearS);
				    qualification.appendChild(rules);
				    
				    qualificationLevel = "";
				}
				
				if (! qualificationLevel.equals(data.get("lvl").toString())){
					qualificationLevel = data.get("lvl").toString();
					qualLevel = document.createElement("qualificationlevel");
				    qualification.appendChild(qualLevel);
				    
				    Element level = document.createElement("name");
				    level.appendChild(document.createTextNode(data.get("lvl").toString()));
				    				    
				    qualLevel.appendChild(level);
				    qualificationChoice = "";
				}
								
				if (! qualificationChoice.equals(data.get("groupname").toString())){
					qualificationChoice = data.get("groupname").toString();
					qualChoice = document.createElement("qualificationchoice");
				    qualLevel.appendChild(qualChoice);
				    				    
				    Element choice = document.createElement("name");
				    choice.appendChild(document.createTextNode(data.get("groupname").toString()));
				    
				    Element choiceCode = document.createElement("code");
				    choiceCode.appendChild(document.createTextNode(data.get("grp").toString()));
				    
				    
				    qualChoice.appendChild(choice);
				    qualChoice.appendChild(choiceCode);
				}
				
			    module = document.createElement("module");
			    qualChoice.appendChild(module);
				    
				    
				Element moduleName = document.createElement("name");
				moduleName.appendChild(document.createTextNode(data.get("module1").toString()));
							    
				Element moduleCode = document.createElement("moduleCode");
				moduleCode.appendChild(document.createTextNode(data.get("moduleCode").toString()));
				
				Element preReq = document.createElement("preco");
				preReq.appendChild(document.createTextNode(data.get("pre_req").toString()));
				
				module.appendChild(moduleName);
				module.appendChild(moduleCode);
				module.appendChild(preReq);
			}
			
			  
			 if ( filename != null && filename.length() != 0){
			    //get path from Sakai.properties
				String path = ServerConfigurationService.getString("brochures.path");
			 	File file= new File(path+filename);
			 	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			 	TransformerFactory transformerFactory = TransformerFactory.newInstance();
 
	        	Transformer transformer = transformerFactory.newTransformer();

	        	transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
	        	DOMSource source = new DOMSource(document);
	        
	        	StreamResult result =  new StreamResult(output);
	        	transformer.transform(source, result);
	        
	        	if (output != null){
	        		output.close();
	        	}
			 }
		}catch(Exception ex){
  			System.out.println("Problem : "+ ex);
			throw new Exception("za.ac.unisa.lms.db.StudentSystemDAO.BrochureXMLGenerator: generateXML: Error occurred / "+ ex,ex);
		}
	}

	
}
