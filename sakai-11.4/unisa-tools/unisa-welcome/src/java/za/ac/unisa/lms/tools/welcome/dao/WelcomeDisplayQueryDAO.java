package za.ac.unisa.lms.tools.welcome.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.HttpAccess;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.util.BaseResourceProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WelcomeDisplayQueryDAO implements EntityProducer, EntityTransferrer {
	private SqlService sqlService;

	public String getContent(String siteId) throws Exception {
		sqlService = (SqlService) ComponentManager.get(SqlService.class);
		String content = "";
		String sql = "select CONTENT " +
					 "  from WELCOME_CONTENT " +
		             " where SITE_ID = '"+siteId+"'";
		try {

			Iterator<?> i = sqlService.dbRead(sql).iterator();
			if (i.hasNext()){
				while (i.hasNext()){
					content = (String) i.next();
				}
			} else {
				sql = "select CONTENT" +
				 	  "  from WELCOME_CONTENT " +
	                  " where SITE_ID = '!admin'";
				try {
					Iterator<?> j = sqlService.dbRead(sql).iterator();
					if (j.hasNext()) {
						while (j.hasNext()){
							content = (String) j.next();
						}
					} else {
						content = "Default Welcome Message does not exist";
					}
				} catch (Exception ex) {
				       throw new Exception("WelcomeDisplayQueryDAO : Error reading Default Welcome Message / "+ ex,ex);
				}
			}
		}  catch (Exception ex) {
	       throw new Exception("WelcomeDisplayQueryDAO : Error reading Site Welcome Message / "+ ex,ex);
	    }
		return content;
	}

	public void writeContent(String siteId, String content){
		sqlService = (SqlService) ComponentManager.get(SqlService.class);
		Calendar cal = Calendar.getInstance();
		java.sql.Date currentDate =  new java.sql.Date(cal.getTime().getTime());
		String sql = "select count(*) from WELCOME_CONTENT where SITE_ID = '"+siteId+"'";
		int count = (new Integer((String)sqlService.dbRead(sql).iterator().next())).intValue();
		if (count == 0){
			sql = "insert into WELCOME_CONTENT(SITE_ID, CONTENT, MODIFIED_ON) values(?,?,to_date('"+currentDate+"','YYYY-MM-DD'))";
			sqlService.dbWrite(sql,new Object[]{siteId,content});
		} else {
			sql = "update WELCOME_CONTENT" +
				  " set CONTENT = ?, " +
				  "     MODIFIED_ON = to_date(?,'YYYY-MM-DD')" +
				  " where SITE_ID = ?";
//			sqlService.dbWrite(sql,new Object[]{content, currentDate, siteId});
			sql = "update WELCOME_CONTENT" +
			  " set CONTENT = ?, " +
			  "     MODIFIED_ON = to_date('"+currentDate+"','YYYY-MM-DD')" +
			  " where SITE_ID = ?";
		sqlService.dbWrite(sql,new Object[]{content,siteId});
		}

	}
	public void revertMessage(String siteId){
		sqlService = (SqlService) ComponentManager.get(SqlService.class);
		String sql = "select count(*) from WELCOME_CONTENT where SITE_ID = '"+siteId+"'";
		int count = (new Integer((String)sqlService.dbRead(sql).iterator().next())).intValue();
		if (count > 0){
			sql = "delete from WELCOME_CONTENT where SITE_ID =?";
			sqlService.dbWrite(sql,new Object[]{siteId});
		}
	}

	private void copy(String fromContext, String toContext) throws Exception {
		String fromContent = getContent(fromContext);
		writeContent(toContext, fromContent);
	}
	
	public String[] myToolIds() {
		String[] toolId = {"unisa.welcome"};
		return toolId;
	}

	public void transferCopyEntities(String fromContext, String toContext,
			List ids) {
		transferCopyEntities(fromContext, toContext, ids, false);
	}
	
	public void transferCopyEntities(String fromContext, String toContext,
			List ids, boolean cleanup) {
		try {
			copy(fromContext, toContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		return "Welcome Message Entity";
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
