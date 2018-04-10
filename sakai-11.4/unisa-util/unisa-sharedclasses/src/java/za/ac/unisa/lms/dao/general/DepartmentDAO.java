package za.ac.unisa.lms.dao.general;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;

public class DepartmentDAO extends StudentSystemDAO {
	
	public String getDepartmentDesc(Short code) throws Exception{
		String description = "";
						
		String sql = "select eng_description" + 
		             " from dpt" +
		             " where code =" + code; 
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				description = data.get("ENG_DESCRIPTION").toString();				
			}
		}
		catch (Exception ex) {
			throw new Exception("DepartmentDAO : Error reading Department description / " + ex,ex);
		}		
		return description;		
	}
	
	public Department getDepartment(Short code) throws Exception{
		Department department = new Department();
		Person person = new Person();
		Person schoolDirector = new Person();
		List actingCodList = new ArrayList();
		List deputyDeanList = new ArrayList();
		List deputyDirectorList = new ArrayList();
				
		String sql = "select dpt.code,dpt.eng_description,head_of_dept,college_code,school_code," + 
		             "dean,deputy_dean1,deputy_dean2,deputy_dean3,deputy_dean4" +
		             " from dpt,colleg" +
		             " where dpt.code =" + code +
		             " and dpt.college_code=colleg.code"; 
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				department.setCode(data.get("CODE").toString());
				department.setDescription(data.get("ENG_DESCRIPTION").toString());
				department.setCollege(replaceNull(data.get("COLLEGE_CODE")));
				department.setSchool(replaceNull(data.get("SCHOOL_CODE")));
				//dean
				String deanCode = replaceNull(data.get("DEAN"));
				if ("".equalsIgnoreCase(deanCode)){
					}else{
						PersonnelDAO daopers = new PersonnelDAO();
						person = daopers.getPerson(Integer.parseInt(deanCode));
					}	
				department.setDean(person);
				//deputy deans
//				deanCode = replaceNull(data.get("DEPUTY_DEAN1"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							deputyDeanList.add(person);
//						}						
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN2"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							deputyDeanList.add(person);
//						}		
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN3"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							deputyDeanList.add(person);
//						}		
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN4"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							deputyDeanList.add(person);
//						}		
//				}	
//				department.setDeputyDeanList(deputyDeanList);
				//cod
				String codCode = replaceNull(data.get("HEAD_OF_DEPT"));
				if ("".equalsIgnoreCase(codCode)){
					}else{
						PersonnelDAO daopers = new PersonnelDAO();
						person = daopers.getPerson(Integer.parseInt(codCode));
					}	
				department.setCod(person);
				actingCodList = getActingCodList(code.toString());
				if (department.getCollege()!=null && department.getCollege()!="" && department.getCollege()!="0"){
					deputyDeanList = getActingDeanList(department.getCollege());
					if (department.getSchool()!=null && department.getSchool()!="" && department.getSchool()!="0"){
						schoolDirector = getSchoolDirector(Short.parseShort(department.getCollege()),Short.parseShort(department.getSchool()));
						deputyDirectorList = getActingDirectorList(department.getCollege(), department.getSchool());
					}
				}				
				department.setActingCodList(actingCodList);
				department.setDeputyDeanList(deputyDeanList);
				department.setSchoolDirector(schoolDirector);
				department.setDeputyDirectorList(deputyDirectorList);
			}
		}
		catch (Exception ex) {
			throw new Exception("DepartmentDAO : Error reading Department / " + ex,ex);
		}
		return department;		
	}
	
	public List getActingCodList(String dptCode)throws Exception {
		List list = new ArrayList();
		PersonnelDAO persUser = new PersonnelDAO();
		
		
		//String sql = "select usrsun.novell_user_id" + 
        //" from usrsun" +
        //" where mk_study_unit_code ='" + code + "'" +
        //" and system_type='H'"; 
		String sql ="select personnel_no" +
				" from usrdpt" +
				" where mk_department_code='" + dptCode + "'" +
				" and type='DPT'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			Person person = new Person();
			person = persUser.getPerson(Integer.parseInt(data.get("PERSONNEL_NO").toString()));
			list.add(person);
		}
	}
	catch (Exception ex) {
		throw new Exception("DepartmentDAO : Error reading ActingCodList / " + ex,ex);
	}		
	return list;
	}
	
	public List getActingDeanList(String collegeCode)throws Exception {
		List list = new ArrayList();
		PersonnelDAO persUser = new PersonnelDAO();
		
		String sql = "select a.personnel_no,b.surname,b.initials" +
		" from usrdpt a,staff b" +
		" where a.college_code = " + Short.parseShort(collegeCode) +	 		
		" and a.type='COLLEGE'" +
 		" and a.personnel_no=b.persno" +
 		" order by b.surname, b.initials";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			Person person = new Person();
			person = persUser.getPerson(Integer.parseInt(data.get("PERSONNEL_NO").toString()));
			list.add(person);
		}
	}
	catch (Exception ex) {
		throw new Exception("DepartmentDAO : Error reading ActingDeanList / " + ex,ex);
	}		
	return list;
	}
	
	public List getActingDirectorList(String collegeCode, String schoolCode)throws Exception {
		List list = new ArrayList();
		PersonnelDAO persUser = new PersonnelDAO();
		
		String sql ="select a.personnel_no,b.surname,b.initials" +
		" from usrdpt a, staff b" +
		" where a.college_code = " + Short.parseShort(collegeCode) +		
		" and a.school_code = " + Short.parseShort(schoolCode) +
		" and a.type='SCHOOL'" +
		" and a.personnel_no=b.persno" +
 		" order by b.surname, b.initials";		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			Person person = new Person();
			person = persUser.getPerson(Integer.parseInt(data.get("PERSONNEL_NO").toString()));
			list.add(person);
		}
	}
	catch (Exception ex) {
		throw new Exception("DepartmentDAO : Error reading ActingDirectorList / " + ex,ex);
	}		
	return list;
	}
	
	public Person getSchoolDirector(Short college,Short school)throws Exception {
		Person director = new Person();
		
		String sql = "select HEAD_OF_SCHOOL" +
        " from school" +
        " where college_code =" + college +
        " and code=" + school;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				String headOfSchool = replaceNull(data.get("HEAD_OF_SCHOOL"));
				if ("".equalsIgnoreCase(headOfSchool)){
				}else{
					PersonnelDAO daopers = new PersonnelDAO();
					director = daopers.getPerson(Integer.parseInt(headOfSchool));
						
				}								
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table school/ " + ex);
		}		
		
		return director;
	}
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
}

