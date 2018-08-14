package za.ac.unisa.lms.tools.brochures.constants;

public class BrochureQueries {
	
	/**
	 * 
	 * @param collCode
	 * @param catCode
	 * @param qualificationCode
	 * @param special
	 * @param year
	 * @param repeatYear
	 * @param repeat
	 * @param heqfComp
	 * @param reglevel
	 * @return
	 */
	
	
	
	public String myRegQuery (String schCode, String dptCode, String researchFlag, String collCode, String catCode, String qualificationCode, String special, int year, int repeatYear, String repeat, String heqfComp, String reglevel) {

		int lastyr = year-1;
		int nextyr = year+1;
		int yr = year-1;
		int yr1 = year+2; 
		
		StringBuilder sb = new StringBuilder();
	
					sb.append("SELECT colleg.abbreviation coll, COLLEG.eng_description COLLEGE_DESCR, under_post_categor quallvl,");
				    sb.append(" qspsun.mk_qual_code qual,");
				    //sb.append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),");
				    //sb.append(" ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),"); 
				    //sb.append(" 'With','with'),'Hiv','HIV'),'Aids','AIDS'),' To ',' to '),'Ctma','CTMA'),");
				    //sb.append(" 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL') qualdesc,");
				    sb.append(" grd.long_eng_descripti qualdesc,");
				    sb.append(" CASE");
				    sb.append(" WHEN grd.repeaters_from_yea BETWEEN 1 AND " +lastyr);
				    sb.append(" THEN 'R'");
				    sb.append(" ELSE ' '");
				    sb.append(" END qual_repeat,");
				    sb.append(" CASE");
				    sb.append(" WHEN quaspc.speciality_code > ' '");
				    sb.append(" THEN quaspc.speciality_code"); 
				    sb.append(" ELSE ' '");
				    sb.append(" END spes,");
				    //sb.append(" CASE");
				    //sb.append(" WHEN quaspc.speciality_code > ' '");
				    //sb.append(" THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),' For ',' for '),");
				    //sb.append(" ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior'),'Hiv','HIV'),'Aids','AIDS'),");
				    //sb.append(" ' Curriculum',' curriculum'),' Dissertation',' dissertation'),' Limited Scope',' limited scope'),' A ',' a ')"); 
				    //sb.append(" ELSE ' '");
				    //sb.append(" END spesdesc,");
				    sb.append(" CASE");
				    sb.append(" WHEN quaspc.speciality_code > ' '");
				    sb.append(" THEN quaspc.english_descriptio");
				    sb.append(" ELSE ' '");
				    sb.append(" END spesdesc,");
				    sb.append(" CASE");
				    sb.append(" WHEN quaspc.repeaters_from_yea BETWEEN 1 AND "+repeatYear);
				    sb.append(" OR quaspc.repeaters_only = 'Y'");
				    sb.append(" THEN 'R'");
				    sb.append(" ELSE ' '");
				    sb.append(" END spec_repeat,");
				    sb.append(" grd.TYPE qualtype, kat.eng_description catname,");
				    sb.append(" nqf_exit_level nqflvl, grd.nqf_credits creds, qspsun.study_level lvl,");
				    sb.append(" qspsun.group0 grp, mk_study_unit_code module,");
				    //sb.append(" REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE  (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE (REPLACE (REPLACE (REPLACE  (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (NLS_INITCAP (sun.eng_long_descripti),");
				    //sb.append("'Iii','III' ),' Ict',' ICT' ),'Ict Ap','ICT Ap' ),'Hr ','HR '),' Or ',' or ' ),' Of ',' of ' ),' As ',' as ' ),' Its ',' its ' ),'Pgce','PGCE' ),' User ',' user ' ),' Their ',' their '),' And ',' and ' ),' The ',' the ' ), 'Fet ','FET ' ),'''S','''s'),'''S ','''s '),'It So', 'IT So'),'It Proj', 'IT Proj'), 'Itsm', 'IT SM'),' To ',' to ' ),' A ',' a '), ' On ',' on '),' An ',' an '),' In ',' in '),' That',' that' ),' Tb/Ca', ' TB/CA'),' Ah ', ' AH '),' This',' this' ),' Is ',' is ' ), ' For ',' for ' ),'Bed','BED' ),' Note',' note' ),'Ii','II' ),'Iv','IV' ),' IVa',' IVA' ),'Llb','LLB' ),'Hiv','HIV' ),' Dna ',' DNA '), '1a','1A'),'1b','1B'),'Ia','IA'), 'Ib','IB'), 'IIa', 'IIA'), 'IIb', 'IIB'), 'IIIa', 'IIIA'), 'IIIb', 'IIIB'), 'IIIc', 'IIIC'),'( Wil )', '(WIL)   '),'(Wil', '(WIL'), '3a', '3A'), '3b', '3B'), ' Ic', ' IC') module1,");
				    sb.append(" sun.eng_long_descripti  module1,");
				    sb.append(" ppreq_comment, admission_require,");
				    sb.append(" CASE");
				    sb.append(" WHEN (SELECT TRIM (grp_description)");
				    sb.append(" || '. '");
				    sb.append(" || grp_instruction");
				    sb.append(" FROM qspgrp");
				    sb.append(" WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c");
				    sb.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code");
				    sb.append(" AND qspgrp.level0 = qspsun.study_level");
				    sb.append(" AND qspgrp.group0 = qspsun.group0) = '. '") ;
				    sb.append(" THEN 'No group'");
				    sb.append(" ELSE (SELECT REPLACE (   TRIM (grp_description)");
				    sb.append(" || '. '");
				    sb.append(" || grp_instruction,"); 
				    sb.append(" ':.',");
				    sb.append(" ':'");
				    sb.append(" )");
				    sb.append(" FROM qspgrp");
				    sb.append(" WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c");
				    sb.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code");
				    sb.append(" AND qspgrp.level0 = qspsun.study_level");
				    sb.append(" AND qspgrp.group0 = qspsun.group0)");
				    sb.append(" END groupname,");
				    sb.append(" grd.to_year qual_to_year, quaspc.to_year, sun.from_year mod_from_year,");
				    sb.append(" CASE");
				    sb.append(" WHEN exists(select trim(text0) from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1)");
				    sb.append(" THEN 'Y'");
				    sb.append(" ELSE");
				    sb.append(" 'N'");
				    sb.append(" END ruleyes,");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1)\"RULE LINE1\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=2)\"RULE LINE2\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=3)\"RULE LINE3\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=4)\"RULE LINE4\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=5)\"RULE LINE5\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=6)\"RULE LINE6\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=7)\"RULE LINE7\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=8)\"RULE LINE8\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=9)\"RULE LINE9\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=10)\"RULE LINE10\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=11)\"RULE LINE11\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=12)\"RULE LINE12\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=13)\"RULE LINE13\","); 
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=14)\"RULE LINE14\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=15)\"RULE LINE15\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=16)\"RULE LINE16\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=17)\"RULE LINE17\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=18)\"RULE LINE18\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=19)\"RULE LINE19\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=20)\"RULE LINE20\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=21)\"RULE LINE21\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=22)\"RULE LINE22\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=23)\"RULE LINE23\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=24)\"RULE LINE24\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=25)\"RULE LINE25\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=26)\"RULE LINE26\",");
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=27)\"RULE LINE27\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=28)\"RULE LINE28\",");         
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=29)\"RULE LINE29\",");        
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=30)\"RULE LINE30\",");          
				    sb.append(" (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=31)\"RULE LINE31\",");
				    sb.append(" grd.purpose purpose_statement,");
				    //New APS Score Element 42
				    sb.append("GRD.APS_SCORE aps_score,");
				    
				    //Adding rules per study level
				    sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=1)\"LEVEL RULE LINE1\",");
		    		sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=2)\"LEVEL RULE LINE2\",");
    				sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=3)\"LEVEL RULE LINE3\",");
    				sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=4)\"LEVEL RULE LINE4\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=5)\"LEVEL RULE LINE5\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=6)\"LEVEL RULE LINE6\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=7)\"LEVEL RULE LINE7\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=8)\"LEVEL RULE LINE8\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=9)\"LEVEL RULE LINE9\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=10)\"LEVEL RULE LINE10\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=11)\"LEVEL RULE LINE11\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=12)\"LEVEL RULE LINE12\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=13)\"LEVEL RULE LINE13\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=14)\"LEVEL RULE LINE14\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=15)\"LEVEL RULE LINE15\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=16)\"LEVEL RULE LINE16\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=17)\"LEVEL RULE LINE17\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=18)\"LEVEL RULE LINE18\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=19)\"LEVEL RULE LINE19\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=20)\"LEVEL RULE LINE20\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=21)\"LEVEL RULE LINE21\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=22)\"LEVEL RULE LINE22\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=23)\"LEVEL RULE LINE23\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=24)\"LEVEL RULE LINE24\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=25)\"LEVEL RULE LINE25\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=26)\"LEVEL RULE LINE26\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=27)\"LEVEL RULE LINE27\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=28)\"LEVEL RULE LINE28\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=29)\"LEVEL RULE LINE29\",");
				    sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=30)\"LEVEL RULE LINE30\",");
					sb.append(" (select text0 from QSPLRU where mk_qualification_code=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and study_level = qspsun.study_level and seq_no=31)\"LEVEL RULE LINE31\",");

				    
					//Adding SAQA ID
					 sb.append("grd.saqa_id,");
					//foundation flag to choose which aps_score to extract
				    sb.append("grd.foundation_flag,");
				    //New APS Score Element 391
				    sb.append("quaspc.aps_score aps_score_1,");
				    sb.append(" gencod.afr_description CAT_GRP, gencod.eng_description CATEGORY_GROUP,"); 
				    sb.append(" grd.delivery_mode_gc186 DELIVERY_MODE,grd.pqm_compliant_flag HEQF_COMPLIANT,");
				    sb.append(" (select SUNPDT.BROCHURE_RECOMMEND from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1))\"Recommendation\",");
				    sb.append(" (select SUNPDT.PRE_REQUISITES_INFO from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1))\"Pre-requisite\",");
				    sb.append(" (select SUNPDT.CO_PARALLEL_REQUISITES_INFO from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1))\"Co-requisite\",");
				    sb.append(" (select sununs.purpose from sununs where mk_study_unit_code= sun.code) \"SYLLABUS\"");
				    sb.append(" FROM quaspc, grd, qspsun, sun, colleg, kat, qspgrp, gencod");
				    sb.append(" WHERE quaspc.in_use_flag = 'Y'");
				    sb.append(" AND quaspc.mk_qualification_c = grd.code");
				   // sb.append(" and quaspc.college_code = 3");
				    sb.append(" AND qspgrp.mk_qual_code = quaspc.mk_qualification_c");
				    sb.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code");
				    sb.append(" AND qspgrp.level0 = qspsun.study_level");
				    sb.append(" AND qspgrp.group0 = qspsun.group0");
				    sb.append(" AND qspgrp.grp_description NOT LIKE '%A & B%'");
				    sb.append(" AND grd.in_use_flag = 'Y'");
				    sb.append(" AND (grd.to_year = 0 OR grd.to_year > "+yr+")"); //grd stands for Qualification
					sb.append(" AND grd.from_year < "+nextyr);
					sb.append(" AND grd.TYPE <> 'S'");
					sb.append(" AND under_post_categor IN ('U', 'H')");
					sb.append(" AND qspsun.mk_qual_code = grd.code");
					sb.append(" AND quaspc.college_code = colleg.code");
					sb.append(" AND fk_katcode = kat.code");
					sb.append(" AND (quaspc.to_year = 0 or quaspc.to_year > "+yr+")");
					sb.append(" AND qspsun.mk_spes_code = quaspc.speciality_code");
					sb.append(" AND (qspsun.to_year = 0 OR qspsun.to_year > "+yr+")");
					sb.append(" AND qspsun.from_year < "+yr1);
					sb.append(" AND qspsun.frequently_used = 'Y'");
					sb.append(" AND mk_study_unit_code = sun.code");
					sb.append(" AND sun.in_use_flag = 'Y'");
					sb.append(" AND sun.from_year < "+nextyr);  
					sb.append(" AND (sun.to_year = 0 OR sun.to_year > "+yr+")"); 
					              
					if(!schCode.equals("-1")){
						//Input code here
						sb.append(" AND sun.school_code="+"'"+schCode+"'");
				         
					}else{ }
			        if(!dptCode.equals("-1")){
			        	//Input code here
			        	sb.append(" AND sun.mk_department_code="+"'"+dptCode+"'");
				          
				    }else{ }
				    if(!researchFlag.equals("-1")){
				        //Input code here
				    	sb.append(" AND sun.research_flag="+"'"+researchFlag+"'");
				    }
			
			        if(!catCode.equals("-1")){
			               sb.append(" AND kat.code="+catCode);
			        }else{ }
			        if(!collCode.equals("-1")){
			        	sb.append(" AND colleg.code="+collCode);
			        }else{ }
			        if(!qualificationCode.equals("-1")){
			        	sb.append(" AND grd.CODE="+"'"+qualificationCode+"'");
			       }else{ }
			        if(heqfComp.equals("Y")){
			        	sb.append(" AND grd.PQM_COMPLIANT_FLAG='Y'");
			         }else if(heqfComp.equals("N")){ 
			        	 sb.append(" AND grd.PQM_COMPLIANT_FLAG=' '");
			         }else if(heqfComp.equals("B")){ 
			        	//query = query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			        	 
			         }
        		   sb.append(" AND grd.fk_katcode = kat.code"); 
        		   sb.append(" and grd.fk_katcode = gencod.code");
        		   sb.append(" and gencod.fk_gencatcode=172");
        		   // sb.append(" AND ROWNUM <= 10");
        		   sb.append(" ORDER BY abbreviation, grd.TYPE, gencod.afr_description, qspsun.mk_qual_code,qspsun.mk_spes_code,qspsun.study_level,qspsun.group0,mk_study_unit_code");
        
        		 //System.out.println("****************My Registration QUERY ********************"+ sb);
        		 return sb.toString().intern();
	}
	
	public String myRegMDQuery (String schCode, String dptCode, String researchFlag, String collCode, String catCode, String qualificationCode, String special, int year, int repeatYear, String repeat, String heqfComp, String reglevel) {
		
		int lastyr = year-1;
		int nextyr = year+1;
		int yr = year-1;
		int yr1 = year+2;
		
		       StringBuilder queryBuilder = new StringBuilder();     
					            
					queryBuilder.append("SELECT colleg.abbreviation coll, COLLEG.eng_description COLLEGE_DESCR, under_post_categor quallvl, qspsun.mk_qual_code qual,"); 
					queryBuilder.append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),");     
			    	queryBuilder.append(" ' And ',' and '),' Of ',' of '),' Or ',' or '),'Phd','PhD'),' In ',' in '),");     
			    	queryBuilder.append(" 'With','with'),'Hiv','HIV'),'Aids','AIDS'),' To ',' to '),");       
			    	queryBuilder.append(" 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL') qualdesc,");      
			    	queryBuilder.append(" CASE");           
			    	queryBuilder.append(" WHEN grd.repeaters_from_yea BETWEEN 1 AND 2013");       
			    	queryBuilder.append(" THEN 'R'");        
			    	queryBuilder.append(" ELSE ' '");         
			    	queryBuilder.append(" END qual_repeat,");     
			    	queryBuilder.append(" CASE");     
			    	queryBuilder.append(" WHEN quaspc.speciality_code > ' '");          
			    	queryBuilder.append(" THEN quaspc.speciality_code");              
			    	queryBuilder.append(" ELSE ' '");             
			    	queryBuilder.append(" END spes,");             
			    	queryBuilder.append(" CASE");    
			    	queryBuilder.append(" WHEN quaspc.speciality_code > ' '");         
			    	queryBuilder.append(" THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),");               
			    	queryBuilder.append(" ' For ',' for '),");			                   
			    	queryBuilder.append(" ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior'),'Hiv','HIV'),'Aids','AIDS'),");		                         
			    	queryBuilder.append(" ' Curriculum',' curriculum'),' Dissertation',' dissertation'),' Limited Scope',' limited scope'),' A ',' a ')");					                        
			    	queryBuilder.append(" ELSE ' '");				                    
			    	queryBuilder.append(" END spesdesc,");				                    
			    	queryBuilder.append(" CASE");				            
			    	queryBuilder.append(" WHEN quaspc.repeaters_from_yea > 2010");				                
			    	queryBuilder.append(" OR quaspc.repeaters_only = 'Y'");				                     
			    	queryBuilder.append(" THEN 'R'");				                
			    	queryBuilder.append(" ELSE ' '");				                
			    	queryBuilder.append(" END spec_repeat,");				                
			    	queryBuilder.append(" grd.TYPE qualtype, fk_katcode cat, kat.eng_description catname,"); 
			    	//New APS Score Element
					//queryBuilder.append("GRD.APS_SCORE aps_score,");
			    	//Adding SAQA ID
			    	queryBuilder.append("grd.saqa_id,");
			    	queryBuilder.append(" nqf_exit_level nqflvl, grd.nqf_credits creds, qspsun.study_level lvl,");         
			    	queryBuilder.append(" qspsun.group0 grp, mk_study_unit_code module,");             
			    	queryBuilder.append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(REPLACE(REPLACE (REPLACE (REPLACE (replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (sun.eng_long_descripti), ' Iii', ' III'), ' Ii',' II'),' Iv',' IV'),'Llb','LLB'),' For ',' for '),"); 	             
	    			queryBuilder.append(" ' And ',' and '),' Of ',' of '),' Or ',' or '),"); 	                    
	    			queryBuilder.append(" 'With','with'),'Hiv','HIV'),'Aids','AIDS'),' To ',' to '),"); 		                     
	    			queryBuilder.append(" 'Mcom ','MCom '), 'Mcom','MCom'),'Mcompt','MCOMPT'), 'Msc','MSc'),'DCOMpt','DCOMPT'),'D Litt Et Phil','D Litt et Phil'),'Dadmin','DADMIN'),'Dcom ','DCom '),'Phd','PhD'),'PHD','PhD'),'Dpa','DPA'),'Dphil','DPHIL'),'Llm','LLM'),'Lld','LLD'),'Cset','CSET'),'Cset fg 3','CSET FG 3'),'Cset fg 4','CSET FG 4'),'Edu ','EDU '),'Ded','DED'),'Ma ','MA '),'Ma(Ss)','MA(SS)'),'Mth','MTH'),'Madmin','MADMIN'),'Mnf','MNF'),'Chs','CHS'),'Dth','DTH'),'Mph','MPH'),"); 	                                                             
	    			queryBuilder.append(" 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL'),' In ',' in '), ' Ia',' IA'), ' Ib',' IB'),' IIa',' IIA'),' IIb',' IIB'),' IIIa',' IIIA'),' IIIb',' IIIB'), ' Dna ',' DNA '), ' wil ', ' WIL '), 'Itsm', 'IT SM') module1,"); 	                     
	    			queryBuilder.append(" ppreq_comment, admission_require,"); 	                     
	    			queryBuilder.append(" CASE"); 	            
	    			queryBuilder.append(" WHEN (SELECT TRIM (grp_description)|| '. '|| grp_instruction ");                 
	    			queryBuilder.append(" FROM qspgrp"); 	                     
	    			queryBuilder.append(" WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c"); 	                     
	    			queryBuilder.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code"); 	                     
	    			queryBuilder.append(" AND qspgrp.level0 = qspsun.study_level"); 	                     
	    			queryBuilder.append(" AND qspgrp.group0 = qspsun.group0) = '. '");                      
	    			queryBuilder.append(" THEN 'No group'");                         
	    			queryBuilder.append(" ELSE (SELECT REPLACE (   TRIM (grp_description)|| '. '");                         
	    			queryBuilder.append(" || grp_instruction,':.', ':')");                               
	    			queryBuilder.append(" FROM qspgrp");                         
	    			queryBuilder.append(" WHERE qspgrp.mk_qual_code = quaspc.mk_qualification_c");                        
	    			queryBuilder.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code");                         
	    			queryBuilder.append(" AND qspgrp.level0 = qspsun.study_level");                         
	    			queryBuilder.append(" AND qspgrp.group0 = qspsun.group0)");                         
					queryBuilder.append(" END groupname,");                         
					queryBuilder.append(" grd.to_year qual_to_year, quaspc.to_year, sun.from_year mod_from_year,"); 	             
					queryBuilder.append(" CASE"); 	            
					queryBuilder.append(" WHEN exists(select trim(text0) from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1)"); 	               
					queryBuilder.append(" THEN 'Y'"); 	                    
					queryBuilder.append(" ELSE 'N'"); 	                    
					queryBuilder.append(" END ruleyes,"); 	                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1),' ')\"RULE LINE1\","); 	             
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=2),' ')\"RULE LINE2\","); 		             
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=3),' ')\"RULE LINE3\","); 			                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=4),' ')\"RULE LINE4\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=5),' ')\"RULE LINE5\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=6),' ')\"RULE LINE6\","); 				                  
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=7),' ')\"RULE LINE7\","); 				            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=8),' ')\"RULE LINE8\","); 			            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=9),' ')\"RULE LINE9\","); 			                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=10),' ')\"RULE LINE10\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=11),' ')\"RULE LINE11\","); 				                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=12),' ')\"RULE LINE12\","); 				                       
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=13),' ')\"RULE LINE13\","); 				            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=14),' ')\"RULE LINE14\","); 				            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=15),' ')\"RULE LINE15\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=16),' ')\"RULE LINE16\","); 				                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=17),' ')\"RULE LINE17\","); 				                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=18),' ')\"RULE LINE18\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=19),' ')\"RULE LINE19\","); 				                       
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=20),' ')\"RULE LINE20\","); 				            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=21),' ')\"RULE LINE21\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=22),' ')\"RULE LINE22\","); 				                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=23),' ')\"RULE LINE23\","); 				                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=24),' ')\"RULE LINE24\","); 				                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=25),' ')\"RULE LINE25\","); 			                      
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=26),' ')\"RULE LINE26\","); 				            
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=27),' ')\"RULE LINE27\","); 			                     
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=28),' ')\"RULE LINE28\","); 			                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=29),' ')\"RULE LINE29\","); 			                    
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=30),' ')\"RULE LINE30\","); 			                      
					queryBuilder.append(" nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=31),' ')\"RULE LINE31\","); 			             
					queryBuilder.append(" gencod.afr_description CAT_GRP, gencod.eng_description CATEGORY_GROUP,"); 			             
					         /*  (select purpose from sununs where sununs.mk_study_unit_code = qspsun.mk_study_unit_code) purpose_statement 
					             grd.purpose purpose_statement */
					
					queryBuilder.append(" (select SUNPDT.BROCHURE_RECOMMEND from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1)) \"Recommendation\","); 			                                     
					queryBuilder.append(" (select SUNPDT.PRE_REQUISITES_INFO from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1)) \"Pre-requisite\", "); 			                                   
					queryBuilder.append(" (select SUNPDT.CO_PARALLEL_REQUISITES_INFO from sunpdt where fk_suncode=sun.code and mk_academic_year="+year+" and semester_period in (0,1)) \"Co-requisite\","); 			                                      
					queryBuilder.append(" (select sununs.purpose from sununs where mk_study_unit_code= sun.code) \"SYLLABUS\""); 			                                     
					queryBuilder.append(" FROM quaspc, grd, qspsun, sun, colleg, kat,gencod"); 			                
					queryBuilder.append(" WHERE quaspc.in_use_flag = 'Y'"); 			                
					queryBuilder.append(" AND quaspc.mk_qualification_c = grd.code"); 			                      
					//queryBuilder.append(" AND qspgrp.mk_qual_code = quaspc.mk_qualification_c");
					//queryBuilder.append(" AND qspgrp.mk_spes_code = quaspc.speciality_code");
					//queryBuilder.append(" AND qspgrp.level0 = qspsun.study_level");
					//queryBuilder.append(" AND qspgrp.group0 = qspsun.group0");
					queryBuilder.append(" AND grd.in_use_flag = 'Y'"); 			                      
					queryBuilder.append(" AND (grd.to_year = 0 OR grd.to_year > "+yr+")"); 			                      
					queryBuilder.append(" AND grd.from_year <= "+nextyr); 			                      
					queryBuilder.append(" AND grd.TYPE <> 'S'"); 		                      
					queryBuilder.append(" AND qspsun.mk_qual_code = grd.code"); 		                      
					queryBuilder.append(" AND quaspc.college_code = colleg.code"); 		                      
					queryBuilder.append(" AND fk_katcode = kat.code"); 		                      
					queryBuilder.append(" AND (quaspc.to_year = 0 or quaspc.to_year > "+yr+")"); 		                      
					queryBuilder.append(" AND qspsun.mk_spes_code = quaspc.speciality_code"); 			                      
					queryBuilder.append(" AND (qspsun.to_year = 0 OR qspsun.to_year > "+yr+")"); 			                      
					queryBuilder.append(" AND qspsun.from_year < "+yr1); 			                      
					queryBuilder.append(" AND mk_study_unit_code = sun.code"); 			                      
					queryBuilder.append(" AND sun.in_use_flag = 'Y'"); 		                      
					queryBuilder.append(" AND sun.from_year < "+nextyr); 		                      
					queryBuilder.append(" AND (sun.to_year = 0 OR sun.to_year > "+yr+")"); 		                      
					queryBuilder.append(" AND under_post_categor IN ('M', 'D')"); 	
					
					if(!schCode.equals("-1")){
						//Input code here
						queryBuilder.append(" AND sun.school_code="+"'"+schCode+"'");
				         
					}else{ }
			        if(!dptCode.equals("-1")){
			        	//Input code here
			        	queryBuilder.append(" AND sun.mk_department_code="+"'"+dptCode+"'");
				          
				    }else{ }
				    if(!researchFlag.equals("-1")){
				        //Input code here
				    	queryBuilder.append(" AND sun.research_flag="+"'"+researchFlag+"'");
				    }
			
			        if(!catCode.equals("-1")){
			        	queryBuilder.append(" AND kat.code="+catCode);
			        }else{ }
			        if(!collCode.equals("-1")){
			        	queryBuilder.append(" AND colleg.code="+collCode);
			        }else{ }
			        if(!qualificationCode.equals("-1")) {
			        	
			        	queryBuilder.append(" AND grd.CODE="+"'"+qualificationCode+"'");
			        	
			       } else { }
			        if (heqfComp.equals("Y")) {
			        	queryBuilder.append(" AND grd.PQM_COMPLIANT_FLAG='Y'");
			         }else if(heqfComp.equals("N")){ 
			        	 queryBuilder.append(" AND grd.PQM_COMPLIANT_FLAG=' '");
			         }else if(heqfComp.equals("B")){ 
			        	//query = query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			        	 
			         }
			        
					queryBuilder.append(" AND grd.fk_katcode = kat.code"); 		                      
					queryBuilder.append(" and grd.fk_katcode = gencod.code"); 		                      
					queryBuilder.append(" and gencod.fk_gencatcode=172"); 	
					queryBuilder.append(" ORDER BY abbreviation, gencod.afr_description, grd.TYPE, qspsun.mk_qual_code, spes, qspsun.study_level, grp, qspsun.mk_spes_code, mk_study_unit_code"); 		            

					//System.out.println("****************My RegistrationM&D QUERY ********************"+ queryBuilder);
					            
	  return queryBuilder.toString().intern();
	}
	
	public String myModuleQuery(String colCode, String schCode, String dptCode,
			String module, String subCode, int year) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT /*+ NO_CPU_COSTING */ DISTINCT (SELECT COLLEG.ABBREVIATION");
		sb.append(" FROM colleg WHERE colleg.code = sun.college_code) COLL, sun.college_code,");
		sb.append(" (SELECT COLLEG.eng_description FROM colleg WHERE colleg.code = sun.college_code) COLL_DESCR,");
		sb.append(" (SELECT school.eng_description FROM school WHERE school.code = sun.school_code AND school.college_code = sun.college_code) SCHOOL_DESCR,");
		sb.append(" sunsbj.mk_subject_code, SUNSBJ.COMMENT0 SUNSBJ_COMMENT,");
		sb.append(" (SELECT subject_name FROM sunsub WHERE sunsub.subject_code = sunsbj.mk_subject_code) SUBJECT_NAME,");
		sb.append(" (SELECT comment0 FROM sunsub WHERE sunsub.subject_code = sunsbj.mk_subject_code) SUBJECT_COMMENT,");
		sb.append(" sun.mk_department_code DEPT,");
		sb.append(" (SELECT eng_description FROM dpt WHERE sun.mk_department_code = dpt.code) DEPT_DESCR,");
		//sb.append(" sun.code, REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NLS_INITCAP(sun.eng_long_descripti), ' Tb/Ca', ' TB/CA'),'''S','''s'),' Ah', ' AH'),' A ', ' a '),' User ', ' user '),' Issues ', ' issues '),' For ', ' for '),'( Wil )', '(WIL)   '),'(Wil', '(WIL'), 'Ict ', 'ICT '), ' And ', ' and '), ' The ', ' the '),' Of ', ' of '), ' Or ', ' or '), 'Itsm', 'IT SM'),' In ', ' in '), 'With', 'with'), 'Hiv', 'HIV'), 'Aids', 'AIDS'), ' To ', ' to '), ' i ',' I '), ' ii', ' II'), ' 1a',' 1A'), ' iii',' III'), ' iv',' IV'), ' v',' V'), ' Ia',' IA'),' IIIc',' IIIC'),' IIc',' IIC'),' Iii', ' III'), ' Ii',' II'),' Iv',' IV'), ' IIa', ' IIA'),' IIb',' IIB'),' IIIa',' IIIA'),' IIIb',' IIIB'), ' Iiib',' IIIB'), ' Iia',' IIA'), ' Iiia',' IIIA'), ' 1c',' 1C'), ' Ic',' IC'), ' Ia',' IA'), ' Ib',' IB'), 'Specialisation', 'specialisation'), 'Endorsement', 'endorsement'), 'Tesol', 'TESOL'),'Through','through'),'Their','their'),' An ',' an '),' As ',' as '),'ancient israel','Ancient Israel'),'ancient','Ancient'),' On',' on '),' From ',' from '),'Up','up'),'Note','noted'),'Hr ','HR '),'IVa','IVA'),'2a','2A'),'Fet','FET'),'Phd','PhD'),'Bed','BEd'),'It ','IT '),'Business-To-Business','Business-to-Business'),'Odl','ODL'),'Mathematics a','Mathematics A'),'''S ','''s '),'animal ',' Animal '),'analysis ',' Analysis '),'anatomy',' Anatomy '),'world wide web ',' World Wide Web '),'assessing ',' Assessing '),'technologiae ',' Technologiae '),' Dna ',' DNA '),'Sp ',' SP '),'Pgce',' PGCE ') ENG_DESC,");
		sb.append(" sun.code, sun.eng_long_descripti ENG_DESC,");
		//sb.append(" REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(NLS_INITCAP(sun.afr_long_descripti), ' For ', ' for '), ' And ', ' and '), ' Of ', ' of '), ' Or ', ' or '), ' In ', ' in '), 'With', 'with'), 'Hiv', 'HIV'), 'Aids', 'AIDS'), ' To ', ' to '), ' i',' I'), ' ii', ' II'), ' iii',' III'), ' iv',' IV'), ' v',' V'), ' Ia',' IA'), ' Ib',' IB'),'IIa','IIA'),' IIb',' IIB'),' IIIa',' IIIA'),' IIIb',' IIIB'), ' Iii', ' III'), ' Ii',' II'),' Iv',' IV'), ' Iib',' IIB'), ' Iiib',' IIIB'), ' Iia',' IIA'), ' Iiia',' IIIA'), ' 1a',' 1A'),' 1c',' 1C'), ' Ic',' IC'), ' Ia',' IA'), ' Ib',' IB'), 'Specialisation', 'specialisation'), 'Endorsement', 'endorsement'), 'Tesol', 'TESOL') AFR_DESC,");
		sb.append(" sun.afr_long_descripti AFR_DESC,");
		sb.append(" sun.nqf_credits, sun.nqf_category NQF_LEVEL, sun.pqm_nqf_level, sun.fk_suntypcode, sun.language0, sun.formal_tuition_fla TUITION_IND,");
		sb.append(" (SELECT LISTAGG(mk_tal_code, ',') WITHIN GROUP (ORDER BY mk_tal_code) FROM sunpdtlang WHERE fk_suncode = sunpdt.fk_suncode AND fk_academic_year = sunpdt.MK_ACADEMIC_YEAR AND fk_semester_period IN (0, 1)) LANG,");
		sb.append(" (SELECT LISTAGG(MODULE_LEVEL_GC200, ',') WITHIN GROUP (ORDER BY MODULE_LEVEL_GC200) FROM sunpdtmodlvl WHERE fk_suncode = sunpdt.fk_suncode AND fk_academic_year = sunpdt.mK_ACADEMIC_YEAR AND fk_semester_period IN (0, 1)) MODULE_LEVEL,");
		sb.append(" sunpdt.ONLINE_TUITION_FLAG, sunpdt.STUDY_MATERIAL_IND_GC196, sunpdt.FORMATIVE_ASSESS_IND_GC197, sunpdt.SUMMATIVE_ASSESS_IND_GC198, sunpdt.NON_VENUE_EXAM_TYPE_GC195, sunpdt.NON_VENUE_EXAM_INFO, sunpdt.LANGUAGE_MEDIUM, sunpdt.BROCHURE_RECOMMEND \"Recommendation\", sunpdt.PRE_REQUISITES_INFO \"Pre-requisite\", SUNPDT.CO_PARALLEL_REQUISITES_INFO \"Co-requisite\", sununs.purpose \"SYLLABUS\"");
		sb.append(" FROM sun,sunpdt, sunsbj, sununs ");
		sb.append(" WHERE sun.in_use_flag = 'Y' AND sun.formal_tuition_fla = 'F' AND sun.academic_level IN ('U', 'H') AND sun.from_year <= "
				+ year);
		sb.append(" AND (sun.to_year = 0 OR sun.to_year >= "
				+ year
				+ ") AND sununs.mk_study_unit_code = sun.code || '' AND sunpdt.fk_suncode = sun.code || '' AND sunpdt.mk_academic_year + 0 = "
				+ year
				+ " AND sunpdt.semester_period IN (0, 1) AND sun.code = sunsbj.mk_study_unit_code (+) ");

		if (!colCode.equals("-1")) {

			sb.append(" AND sun.college_code=" + colCode);

		}
		if (!schCode.equals("-1")) {

			sb.append(" and sun.school_code=" + schCode);

		}
		if (!dptCode.equals("-1")) {

			sb.append(" and sun.mk_department_code=" + dptCode);

		}
		if (!module.equals("-1")) {

			sb.append(" and sun.code=" + "'" + module + "'");

		}
		if (!subCode.equals("-1")) {

			sb.append(" and sunsub.subject_code=" + "'" + subCode + "'");
		}

		sb.append(" ORDER BY sun.college_code, sunsbj.mk_subject_code, sun.pqm_nqf_level, sun.code");
		
		//System.out.println("++++++++++BrochureQueiries.java myModule Query String++++++++   :/n" + sb);

		return sb.toString().intern();

	}

}
