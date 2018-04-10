package za.ac.unisa.lms.tools.faqs.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.map.ListOrderedMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.HttpAccess;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.util.BaseResourceProperties;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.core.RowMapperResultReader;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import za.ac.unisa.lms.db.SakaiDAO;

public class FaqDAO extends SakaiDAO implements EntityProducer, EntityTransferrer {
	ToolManager  toolManager;

	private Log log;
	private PreparedStatementCreatorFactory pstmtFactoryFaqCategory;
	private PreparedStatementCreatorFactory pstmtFactoryFaqContent;
	private PreparedStatementCreatorFactory pstmtFactoryEditFaqContent;


	public FaqDAO() {
		log = LogFactory.getLog(this.getClass());
		pstmtFactoryFaqCategory = new PreparedStatementCreatorFactory(
				"insert into FAQ_CATEGORY (Category_ID,SITE_ID,Description,Modified_On) values " +
				" (FAQ_CATEGORY_0.nextval,?, ?, sysdate)", new int[] { Types.VARCHAR,Types.VARCHAR });
		pstmtFactoryFaqCategory.setGeneratedKeysColumnNames(new String[] {"Category_ID"} );
		pstmtFactoryFaqCategory.setReturnGeneratedKeys(true);


		pstmtFactoryFaqContent = new PreparedStatementCreatorFactory(
				"insert into FAQ_CONTENT (Content_ID,Question, Answer, Category_ID, Modified_On) " +
				"values (FAQ_CONTENT_0.nextval,?, ?, ?,sysdate)",
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER } );
		pstmtFactoryFaqContent.setGeneratedKeysColumnNames(new String[] {"Content_ID"} );
		pstmtFactoryFaqContent.setReturnGeneratedKeys(true);

		pstmtFactoryEditFaqContent = new PreparedStatementCreatorFactory(
				"select * from FAQ_CONTENT where Content_ID = ?",
				new int[] {Types.INTEGER } );

	}

	public String getTitle(String siteid){
		String sql =" select Title FROM SAKAI_SITE "+
					" WHERE SITE_ID = '"+siteid+"'";
		String result= querySingleValue(sql,"Title");
		System.out.println(sql);
	    System.out.println(" The title of site "+siteid+" is "+result);
		return result;
	}

	
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


	// Insert Category

	public void insertFaqCategory(FaqCategory category,String siteId) {
		log.debug("Trying to insert FAQ Description: "+category.getDescription());
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		//toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		//comment siteId to get from toolmanager because site import function failing.
		//String siteId = toolManager.getCurrentPlacement().getContext();
		if (category.getSiteId() != null) {
			siteId = category.getSiteId();
		} else {
			category.setSiteId(siteId);
		}
		//GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator pstmt = pstmtFactoryFaqCategory.newPreparedStatementCreator(
				new Object[] { siteId, category.getDescription() });
		jdt.update(pstmt);
	//	log.info("Added FAQ Category ID ("+keyHolder.getKey().toString()+") for site: "+siteId+" (" +category.getDescription()+") ");
		
		//category.setCategoryId(new Integer(keyHolder.getKey().intValue()));
		int categoryId=getCategoryId(siteId,category.getDescription());
        log.info("Added FAQ Category ID ("+categoryId+") for site: "+siteId+" (" +category.getDescription()+") ");
		category.setCategoryId(categoryId);
	}

	public int getCategoryId(String siteid, String description){
		String sql =" select max(Category_ID) as Category_ID from FAQ_CATEGORY where SITE_ID ='"+siteid+"'and Description='"+description+"'";
				
		String category_ID= querySingleValue(sql,"Category_ID");
		int category = Integer.parseInt(category_ID);
	
		return category;
	}

	// Add FAQ

	public void insertFaqContent(FaqContent content) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		//GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator pstmt = pstmtFactoryFaqContent.newPreparedStatementCreator(
				new Object[] { content.getQuestion(), content.getAnswer(), content.getCategoryId() });
		jdt.update(pstmt);
		//content.setContentId(new Integer(keyHolder.getKey().intValue()));
		
		log.info("Inserted new FAQ ("+content.getContentId()+")");
}
	
	
	
	//edit faq

	public void editFaqContent(FaqContent content) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		//GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator pstmt = pstmtFactoryEditFaqContent.newPreparedStatementCreator(
				new Object[] { content.getQuestion(), content.getAnswer(), content.getCategoryId() });
		jdt.update(pstmt);
		//content.setContentId(new Integer(keyHolder.getKey().intValue()));
		//log.info("Inserted new FAQ ("+content.getContentId()+")");
}
	//Add FAQ with Category from the FAQ screen

	public List getFaqCategories(String siteId) {
		log.debug("Getting Categories");
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.query("select SITE_ID, Category_ID, Description, Modified_On from FAQ_CATEGORY " +
				"where SITE_ID = ? order by Modified_On desc", new Object[] { siteId }, new int[] { Types.VARCHAR },
				new FaqCategoryRowMapper());
		log.debug("Got Categories");
		return results;
	}

	public List getFaqContents(Integer categoryId) {
		log.debug("Getting Contents for Category "+categoryId);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.query("select * from FAQ_CONTENT where Category_Id = ? order by Question",
				new Object[] { categoryId }, new int[] { Types.INTEGER },
				new FaqContentRowMapper());
		log.debug("Got Contents for Category "+categoryId);
		return results;
	}

	public List getFaqContentIds(Integer categoryId) {
		log.debug("Getting Contents for Category "+categoryId);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.query("select * from FAQ_CONTENT where Category_Id = ? order by Question",
				new Object[] { categoryId }, new int[] { Types.INTEGER },
				new FaqContentRowMapper());
		log.debug("Got Content IDs for Category "+categoryId);
		return results;
	}


	public void deleteFaqContent(Integer contentId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		log.info("FAQ Content "+contentId+" removed from database");
		jdt.update("delete from FAQ_CONTENT where Content_Id = ?",
				new Object[] { contentId }, new int[] { Types.INTEGER });
	}

	public void deleteFaqCategory(Integer categoryId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		log.info("FAQ Category "+categoryId+" removed from database");
		jdt.update("delete from FAQ_CONTENT where Category_Id = ?",
				new Object[] { categoryId }, new int[] { Types.INTEGER });
		jdt.update("delete from FAQ_CATEGORY where Category_Id = ?",
				new Object[] { categoryId }, new int[] { Types.INTEGER });
	}

	public void deleteAllFaqsForSite(String siteId) {
		List fromCategories = getFaqCategories(siteId);

		log.info("Deleting all FAQ Categories for SiteId " + siteId);
		Iterator i = fromCategories.iterator();
		while (i.hasNext()) {
			FaqCategory faqCategory = (FaqCategory) i.next();
			deleteFaqCategory(faqCategory.getCategoryId());
		}
	}

	public FaqCategory getFaqCategory(Integer categoryId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		log.debug("Retrieving FAQ Category "+categoryId+" from database");
		return (FaqCategory)
			DataAccessUtils.uniqueResult(
					(Collection) jdt.query("select * from FAQ_CATEGORY where Category_Id = ?",
							new Object[] { categoryId },
							new int[] { Types.INTEGER },
							new RowMapperResultSetExtractor(
									new FaqCategoryRowMapper(), 1)));
	}

	public void updateFaqCategory(FaqCategory c) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		log.debug("Updating FAQ Category "+c.getCategoryId()+" on database");
		jdt.update("update FAQ_CATEGORY set Description = ?, Modified_On = sysdate " +
				   "where Category_Id = ?",
				new Object[] {
					c.getDescription(),
					c.getCategoryId() },
				new int[] {
					Types.VARCHAR,
					Types.INTEGER });
	}

	public void updateFaqContent(FaqContent q) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		if (q.getQuestion()!=null){
		log.debug("Updating FAQ Content "+q.getContentId()+" on database");
		jdt.update("update FAQ_CONTENT set Question = ?, Answer = ?, Category_Id = ?, Modified_On = sysdate " +
		"where Content_Id = ?",
				new Object[] {
					q.getQuestion(),
					q.getAnswer(),
					q.getCategoryId(),
					q.getContentId()},
				new int[] {
					Types.VARCHAR,
					Types.VARCHAR,
					Types.INTEGER,
					Types.INTEGER });
	     }
	}
	public FaqContent getFaqContent(Integer contentId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		log.debug("Retrieving FAQ Content "+contentId+" from database");
		return (FaqContent)
			DataAccessUtils.uniqueResult(
					(Collection) jdt.query("select * from FAQ_CONTENT where Content_ID = ?",
							new Object[] { contentId },
							new int[] { Types.INTEGER },
							new RowMapperResultSetExtractor(
									new FaqContentRowMapper(), 1)));
	}
	
	public void copy(String fromSiteId, String toSiteId) {
		List fromCategories = getFaqCategories(fromSiteId);

		Iterator i = fromCategories.iterator();
		while(i.hasNext()){
			FaqCategory fromCategory = (FaqCategory) i.next();
			List contents = getFaqContents(fromCategory.getCategoryId());

			fromCategory.setSiteId(toSiteId);
			fromCategory.setCategoryId(null);
			fromCategory.setModifiedOn(new Timestamp(new Date().getTime()));
			insertFaqCategory(fromCategory,toSiteId);

			Iterator cont = contents.iterator();
			while(cont.hasNext()){
				FaqContent fc = (FaqContent) cont.next();
				fc.setCategoryId(fromCategory.getCategoryId());
				fc.setContentId(null);
				fc.setModifiedOn(new Timestamp(new Date().getTime()));
				insertFaqContent(fc);
			}
		}
	}
	
	
	public String[] myToolIds() {
		String[] toolId = {"unisa.faqs"};
		return toolId;
	}

	public void transferCopyEntities(String fromContext, String toContext,
			List ids) {
		transferCopyEntities(fromContext, toContext, ids, false);
	}
	
	public void transferCopyEntities(String fromContext, String toContext,
			List ids, boolean cleanup) {
		
		if (cleanup) {
			deleteAllFaqsForSite(toContext);
		}
		copy(fromContext, toContext);
		
	}

	
	// The following is just dummy code to implement the EntityProducer interface
	// This is needed so that the Site Tool can do Import/Export for this tool
	public String getLabel() {
		return "EmptyLabel";
	}

	public boolean willArchiveMerge() {
		return false;
	}

	public String archive(String siteId, Document doc, Stack stack,
			String archivePath, List attachments) {
		return "Emptyarchive";
	}

	public String merge(String siteId, Element root, String archivePath,
			String fromSiteId, Map attachmentNames, Map userIdTrans,
			Set userListAllowImport) {
		return "EmptyMerge";
	}

	public boolean parseEntityReference(String reference, Reference ref) {
		return false;
	}

	public String getEntityDescription(Reference ref) {
		return "EmptyEntityDescription";
	}

	public ResourceProperties getEntityResourceProperties(Reference ref) {
		return new BaseResourceProperties();
	}

	public Entity getEntity(Reference ref) {
		return new Entity() {
			
			public Element toXml(Document doc, Stack stack) {
				return null;
			}
			
			public String getUrl(String rootProperty) {
				return null;
			}
			
			public String getUrl() {
				return null;
			}
			
			public String getReference(String rootProperty) {
				return null;
			}
			
			public String getReference() {
				return null;
			}
			
			public ResourceProperties getProperties() {
				return null;
			}
			
			public String getId() {
				return null;
			}
		};
	}

	public String getEntityUrl(Reference ref) {
		return "EmptyEntityUrl";
	}

	public Collection getEntityAuthzGroups(Reference ref, String userId) {
		return null;
	}

	public HttpAccess getHttpAccess() {
		return null;
	}
}
