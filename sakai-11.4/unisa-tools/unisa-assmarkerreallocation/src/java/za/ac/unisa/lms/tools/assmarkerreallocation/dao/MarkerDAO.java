package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Assessment;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Utilities;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerModel;
public class MarkerDAO extends MarkingLanguageDAO{
	
	public List getPotentialMarkers(Short year,Short period,String studyUnit,Integer uniqueNr,String loginMode) throws Exception {
		ArrayList list = new ArrayList();
	     String sql="select distinct novell_user_id,access_level from usrsun"+ 
				 " where mk_study_unit_code = '"+studyUnit.toUpperCase().trim()+"'  and mk_academic_year ="+ year+
				      " and mk_semester_period ="+ period +
				      " and ((system_type = 'L'"+
				      " and access_level in ('PRIML','SECDL','CADMN')) "+
				      " OR ( system_type = 'J' and access_level ='MARKER'))";
        try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				MarkerModel markerModel = new MarkerModel();
				UserDAO daouser = new UserDAO();
				String novellUserId=data.get("NOVELL_USER_ID").toString();
				String role=data.get("access_level").toString();
				markerModel.setPerson(daouser.getPerson(novellUserId));
				markerModel.setRole(role);
				if(!isNetworkCodeValid(novellUserId)){
					   continue;
				}
				//Do not add as potential marker if no Personnel Number or Personnel Number not numeric
				if (markerModel.getPerson().getPersonnelNumber()!=null && Utilities.isInteger(markerModel.getPerson().getPersonnelNumber())){
					String departmentCode = markerModel.getPerson().getDepartmentCode();
					if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
						DepartmentDAO daodpt = new DepartmentDAO();
						markerModel.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
					}else{
						markerModel.setDepartmentDesc("");
					}
					markerModel.setPrevMarkPercentage("0");	
					markerModel.setMarkPercentage("0");	
					markerModel.setStatus("Active");
					if (markerModel.getPerson().getResignDate()!=null && 
							!markerModel.getPerson().getResignDate().equalsIgnoreCase("")&&
							markerModel.getStatus().equalsIgnoreCase("Active")){
							Calendar currentDate = Calendar.getInstance();
							Date nowDate = currentDate.getTime();
							SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
							String str_date=markerModel.getPerson().getResignDate();
					        Date resignDate = (Date)formatter.parse(str_date); 
					        
					        if (resignDate.after(nowDate)){
					        	markerModel.setStatus("Active");
					        }else{
					        	markerModel.setStatus("Inactive");
					        }
					}
					 Integer personellNum=Integer.parseInt(markerModel.getPerson().getPersonnelNumber());
			           List langList=getLangLinkedWithMarker(studyUnit,year,period,personellNum,uniqueNr);
			           if(loginMode.equals(Assessment.accessMode)){
				   	            markerModel.setMarkingLanguageList(langList);
				       }else{
					            addMarkerLangRec(markerModel,loginMode,langList);
				       }
				    if (markerModel.getStatus().equalsIgnoreCase("Active")){
					    	list.add(markerModel);
					}
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : : Error reading table usrsun / " + ex);
		}		
		return list;		
	}
	private void addMarkerLangRec(MarkerModel markerModel,String loginMode,List langList){
		               String status=markerModel.getStatus();
                        if((langList!=null)&&(!langList.isEmpty())){
	    		                       Iterator iter=langList.iterator();
	    		                       String[] langArr=new String[langList.size()];
	    		                       int x=0;
	    		                       while(iter.hasNext()){
	    			                       langArr[x]=iter.next().toString();
	    			                        x++;
	    		                       }
	    		                       markerModel.setChosenMarkingLanguageList(langArr);
	    	           }
	}
	public List getMarkers(Short dummyYear,Short year,Short period,Integer uniqueNr,String studyUnit,String loginMode,boolean langDisplayed) throws Exception{
		ArrayList list = new ArrayList();		
		
		String sql = "select assmrk.mk_lecturer_nr,assmrk.mark_percentage" +
	    " from assmrk" +
        " where assmrk.fk_unq_ass_year =" + dummyYear +
        " and assmrk.fk_unq_ass_period =" + period +
        " and assmrk.fk_unique_nr =" + uniqueNr +
        " and (assmrk.nr_returned > 0 or assmrk.mark_percentage > 0)" +
        " and assmrk.mk_lecturer_nr > 0";
                
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				MarkerModel markerModel = new MarkerModel();
				PersonnelDAO daopers = new PersonnelDAO();
				Integer persno=Integer.parseInt((data.get("MK_LECTURER_NR").toString()));
 				Person staffPerson = new Person();
 				Person usrPerson = new Person();
				//marker.setPerson(daopers.getPerson(persno));
				staffPerson = daopers.getPerson(persno);
				if (staffPerson.getPersonnelNumber()==null || staffPerson.getPersonnelNumber().equalsIgnoreCase("")){
					usrPerson = new Person();
					usrPerson = daopers.getPersonnelfromUSR(String.valueOf(persno));
					if (usrPerson.getPersonnelNumber()!=null && usrPerson.getPersonnelNumber().equalsIgnoreCase("")){
						markerModel.setPerson(usrPerson);
					}else{
						Person person = new Person();
						person.setPersonnelNumber(persno.toString());
						person.setName("Unknown");
						person.setNameReverse("Unknown");
						person.setNovellUserId("");
						markerModel.setPerson(person);
					}
				}else {
					markerModel.setPerson(staffPerson);
				}
				String departmentCode = markerModel.getPerson().getDepartmentCode();
				if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
					DepartmentDAO daodpt = new DepartmentDAO();
					markerModel.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
				}else{
					markerModel.setDepartmentDesc("");
				}
				String percentage=data.get("MARK_PERCENTAGE").toString();
				markerModel.setMarkPercentage(percentage);
				markerModel.setPrevMarkPercentage(percentage);
				if (markerModel.getPerson().getNovellUserId()==null || markerModel.getPerson().getNovellUserId()==""){
					markerModel.setStatus("Inactive");
					markerModel.setRole("NONE");
				}else{
					String sqlUSRSUN = "select access_level " +
		             "from usrsun " +
		             " where novell_user_id = '" + markerModel.getPerson().getNovellUserId().toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + year +
		             " and mk_semester_period = " + period +
		             " and ((system_type = 'L'"+
				     " and access_level in ('PRIML','SECDL','CADMN')) "+
				     " OR ( system_type = 'J' and access_level ='MARKER'))";
					try{ 
						queryList = jdt.queryForList(sqlUSRSUN);
						Iterator j = queryList.iterator();
						markerModel.setRole("NONE");
						while (j.hasNext()) {
							ListOrderedMap dataUSRSUN = (ListOrderedMap) j.next();
							markerModel.setRole(dataUSRSUN.get("access_level").toString());
						}
						if (markerModel.getRole().equalsIgnoreCase("NONE")) {
							markerModel.setStatus("Inactive");
						} else {
							markerModel.setStatus("Active");
						}
					}
					catch (Exception ex) {
						throw new Exception("AssMarkerReallocationDAO : : Error reading table usrsun getting access_level / " + ex);
					}	
				}
				if (markerModel.getPerson().getResignDate()!=null && 
						!markerModel.getPerson().getResignDate().equalsIgnoreCase("")&& 
						markerModel.getStatus().equalsIgnoreCase("Active")){
						Calendar currentDate = Calendar.getInstance();
						Date nowDate = currentDate.getTime();
						
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=markerModel.getPerson().getResignDate();
				        Date resignDate = (Date)formatter.parse(str_date); 
				        
				        if (resignDate.after(nowDate)){
				        	markerModel.setStatus("Active");
				        }else{
				        	markerModel.setStatus("Inactive");
				        }
				 }
				// if(langDisplayed){
				       Integer personellNum=Integer.parseInt(markerModel.getPerson().getPersonnelNumber());
			           List langList=getLangLinkedWithMarker(studyUnit,year,period,personellNum,uniqueNr);
			           if(loginMode.equals(Assessment.accessMode)){
				   	            markerModel.setMarkingLanguageList(langList);
				       }else{
					            addMarkerLangRec(markerModel,loginMode,langList);
				       }
				// }
			     list.add(markerModel);
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading table assmrk / " + ex);
		}		
		return list;		
	}
	public boolean isAuthorised(String novellid,String studyUnit,Short academicYear,Short semester) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + novellid.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + academicYear +
		             " and mk_semester_period = " + semester +
		             " and system_type = 'L'" +
		             " and access_level in ('SECDL','CADMN','PRIML')";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getPrimaryLecturer(String studyUnit,Short year,Short period)throws Exception{
		String userId="";
		
		String sql = "select distinct usrsun.novell_user_id" +
	    " from usrsun" +
	    " where usrsun.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
	    " and usrsun.mk_academic_year = " + year +
	    " and usrsun.mk_semester_period = " + period +
	    " and usrsun.system_type = 'L'" +
	    " and usrsun.access_level ='PRIML'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				userId=data.get("novell_user_id").toString();								
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading usrsun / " + ex);
		}		
		return userId;	
	}	
	public void updateAssMrk(short year, short period, int uniqueNr, int lecturerNr, short markPerc ) throws Exception {
		
		String sql = "select count(*)" +
			         " from assmrk " +
			         " where FK_UNQ_ASS_YEAR = " + year + 
			 		 " and FK_UNQ_ASS_PERIOD = " + period + 
			 		 " and FK_UNIQUE_NR = " + uniqueNr + 
			 		 " and MK_LECTURER_NR = " + lecturerNr; 	
			           
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				//record does not exists, insert new record
				sql = "insert into assmrk (FK_UNQ_ASS_YEAR,FK_UNQ_ASS_PERIOD,FK_UNIQUE_NR," +
				"MK_LECTURER_NR,MARK_PERCENTAGE,NR_OUTSTANDING,NR_RETURNED,AVERAGE_MARK," +
				"STATUS_FLAG)" +
				" VALUES" +
				" (" + year + 
				"," + period + 
				"," + uniqueNr + 
				"," + lecturerNr +
				"," + markPerc +
				",0,0,0,'A')"; 	
				try{ 
					jdt = new JdbcTemplate(getDataSource());
					result = jdt.update(sql);	
				}
				catch (Exception ex) {
					    throw new Exception("AssMarkerReallocationDao : Error inserting ASSMRK / " + ex,ex);
				}	
			} else {
				//record exists, update record
				sql = "update assmrk set" +
				" MARK_PERCENTAGE = " + markPerc + 
				" where FK_UNQ_ASS_YEAR = " + year + 
				" and FK_UNQ_ASS_PERIOD = " + period + 
				" and FK_UNIQUE_NR = " + uniqueNr + 
				" and MK_LECTURER_NR = " + lecturerNr; 	
				try{ 
					jdt = new JdbcTemplate(getDataSource());
					result = jdt.update(sql);	
				}
				catch (Exception ex) {
					throw new Exception("AssMarkerReallocationDao : Error updating ASSMRK / " + ex,ex);
				}	
			}
		}
}
