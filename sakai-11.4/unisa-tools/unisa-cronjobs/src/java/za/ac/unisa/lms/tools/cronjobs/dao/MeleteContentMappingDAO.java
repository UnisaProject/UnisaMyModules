package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections.map.ListOrderedMap;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.sakaiproject.component.cover.ComponentManager;


public class MeleteContentMappingDAO extends SakaiDAO {

	private PreparedStatementCreatorFactory psFactoryModuleSelect;
	private PreparedStatementCreatorFactory psFactoryModuleIdSelect;
	private PreparedStatementCreatorFactory psFactoryModuleIdMapInsert;

	private PreparedStatementCreatorFactory psFactorySectionSelect;
	private boolean transactionSuccess = false;

	/**
	 * retrieve the melete data that was just copied to the group site and
	 * insert the mapping data into uni_content_mapping
	 */
	public void meleteDataToBeMapped(String toolId, String fromContext,
			String toContext) throws Exception {

		System.out.println("*************** IN meleteDataToBeMapped");
		// is there duplicate titles on the master site
		List<String> duplicateModuleList = getModuleList(fromContext, toContext);
		if (duplicateModuleList.size() >= 1) {
			System.out.println(">>>>>>>>>>>>> Duplicates exist data not mapped <<<<<<<<<<<<<< ");
		} else {
			// no duplicate learning unit title in master site, continue with mapping
			// get melete module_ids that must be mapped
			System.out.println("*************** IN meleteDataToBeMapped getModuleIdList");
			List moduleIdList = getModuleIdList(fromContext, toContext);

		}


		// get melete section_ids that must be mapped

	} // end of public void meleteDataToBeMapped

	public List getModuleIdList(String fromContext, String toContext) {

		System.out.println("*************** in GetModuleIDList <<<<<<<<<<<<<< ");
		
		psFactoryModuleIdSelect = new PreparedStatementCreatorFactory(
				" select MCM.module_id as to_Module_id, MM.title as to_title,"
						+ " (select MCM2.module_id "
						+ "  from   melete_course_module MCM2, melete_module MM2"
						+ "  where  MCM2.course_id = ? "
						+ "  and    MCM2.module_id = MM2.module_id"
						+ "  and    MM2.title = MM.title) as from_Module_id"
						+ " from  melete_course_module MCM, melete_module MM"
						+ " where MCM.course_id = ? "
						+ " and   MCM.module_id = MM.module_id", new int[] {Types.VARCHAR,Types.VARCHAR});

	
		System.out.println(">>>>>>>>>>>>> before jdbcTemplate <<<<<<<<<<<<<< ");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

		System.out.println(">>>>>>>>>>>>> after jdbcTemplate <<<<<<<<<<<<<< ");
		PreparedStatementCreator psCreatorModuleIdSelect = psFactoryModuleIdSelect
				.newPreparedStatementCreator(new Object[]{fromContext,toContext});

		System.out
				.println(">>>>>>>>>>>>> after PreparedStatementCreator <<<<<<<<<<<<<< ");

		UnipooleMappingRowMapper unipooleMappingRowMapper = new UnipooleMappingRowMapper();
		System.out.println(">>>>>>>>>>>>> after rowmapper <<<<<<<<<<<<<< ");
		List moduleIdMappingList = jdbcTemplate.query(psCreatorModuleIdSelect,
				unipooleMappingRowMapper);

		Iterator moduleIdListIterator = moduleIdMappingList.iterator();
		while (moduleIdListIterator.hasNext()) {

			UnipooleMapping unipooleMapping = (UnipooleMapping) moduleIdListIterator
					.next();
			System.out.println(">>>> "+unipooleMapping.getFromModuleId()+" "+unipooleMapping.getTitle()+" "+unipooleMapping.getToModuleId());
			
			/* 
			 * module mapping retrieved from melete id & to melete id
			 * now insert into mapping table
			 * uni_content_mapping 
			 */
//			boolean insertStatus = insertModuleMapping(fromContext, toContext, "melete", unipooleMapping.getFromModuleId(),unipooleMapping.getToModuleId());
//			System.out.println("<<<<< insertStatus = "+insertStatus);

		} // end of while (moduleIdListIterator.hasNext()) {

		return moduleIdMappingList;
	} // end of public List getModuleIdList(String fromContext, String
		// toContext)
	

	public List<String> getModuleList(String fromContext, String toContext) {

		List<String> duplicateModules = new ArrayList<String>();
		psFactoryModuleSelect = new PreparedStatementCreatorFactory(
				" select MCM.module_id as from_module_id, MM.title as to_title, "
						+ " null as to_Module_id"
						+ " from melete_course_module MCM, melete_module MM"
						+ " where MCM.course_id = ?"
						+ " and   MCM.module_id = MM.module_id",
				new int[] { Types.VARCHAR });

		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

		PreparedStatementCreator psCreatorModuleIdSelect = psFactoryModuleSelect
				.newPreparedStatementCreator(new Object[] { fromContext });

		UnipooleMappingRowMapper unipooleMappingRowMapper = new UnipooleMappingRowMapper();

		List moduleIdMappingList = jdbcTemplate.query(psCreatorModuleIdSelect,
				unipooleMappingRowMapper);

		Iterator moduleIdListIterator = moduleIdMappingList.iterator();

		while (moduleIdListIterator.hasNext()) {

			UnipooleMapping unipooleMapping = (UnipooleMapping) moduleIdListIterator
					.next();
			String moduleId = unipooleMapping.getFromModuleId();

			// validate if there are duplicate titles, if duplicates then
			// mapping may not be done.
			Iterator moduleIdListIterator2 = moduleIdMappingList.iterator();
			int duplicateCounter = 0;

			while (moduleIdListIterator2.hasNext()) {

				UnipooleMapping unipooleMapping2 = (UnipooleMapping) moduleIdListIterator2
						.next();

				if (unipooleMapping.getTitle().equals(
						unipooleMapping2.getTitle())) {
					duplicateCounter = duplicateCounter + 1;

				} // end if
					// (unipooleMapping.getTitle().equals(unipooleMapping2.getTitle()))

			}// end of while (moduleIdListIterator2.hasNext()) {

			if (duplicateCounter >= 2) {
				// duplicate ad module id to list to be ignored when mapping
				duplicateModules.add(moduleId);

			} // end of if (duplicateCounter >= 2) {

		} // end of while (moduleIdListIterator.hasNext()) {

		return duplicateModules;
	} // end of public List getModuleIdList(String fromContext, String
		// toContext)


	/**
	 * insert new melete mapping
	 * 
	 * fromContext,toContext,'melete',unipooleMapping.getFromModuleId(),unipooleMapping.getToModuleId()
	 */
	public boolean insertModuleMapping(String fromContext,String toContext, String toolId, String fromModuleId, String toModuleId){
		boolean statusSuccess = false;
		try
		{
			psFactoryModuleIdMapInsert = new PreparedStatementCreatorFactory(
					"insert into uni_content_mapping(id, site_from_id, site_to_id, tool_name, tool_from_id, tool_to_id)"+
					" values((select nvl(max(id),0) +1 from uni_content_mapping), ?, ?, ?, ?, ?)",
					new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	
			PreparedStatementCreator psCreatorModuleIdMapInsert = psFactoryModuleIdMapInsert.newPreparedStatementCreator(
					new Object[]{fromContext, toContext, toolId, fromModuleId, toModuleId}
				);

			jdbcTemplate.update(psCreatorModuleIdMapInsert);
			statusSuccess=true;
		}
		catch(Exception ex)
		{
			StackTraceElement[] element  = ex.getStackTrace();
			System.out.println("insertModuleMapping error  "+ex.getMessage());
			statusSuccess=false;
		}
		return statusSuccess;
	} // end of public boolean insertModuleMapping
	
	
} // end of public class MeleteContentMappingDAO 