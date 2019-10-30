/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.faqs.tool;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.cover.SecurityService;
import org.sakaiproject.cheftool.Context;
import org.sakaiproject.cheftool.JetspeedRunData;
import org.sakaiproject.cheftool.RunData;
import org.sakaiproject.cheftool.VelocityPortlet;
import org.sakaiproject.cheftool.VelocityPortletPaneledAction;
import org.sakaiproject.cheftool.api.Menu;
import org.sakaiproject.cheftool.menu.MenuDivider;
import org.sakaiproject.cheftool.menu.MenuEntry;
import org.sakaiproject.cheftool.menu.MenuImpl;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.SessionState;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.cover.UsageSessionService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.faqs.cover.FaqsService;
import org.sakaiproject.faqs.dataModel.FaqCategory;
import org.sakaiproject.faqs.dataModel.FaqContent;
import org.sakaiproject.faqs.dataModel.FaqsListForm;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;

/**
 * <p>
 * FaqsToolAction is the faqs display tool
 * </p>
 */
public class FaqsToolAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(FaqsToolAction.class);
	private static ResourceLoader rb = new ResourceLoader("faqs");
	@Getter
	@Setter
	private boolean addFaqContent;
	@Getter
	@Setter
	private boolean editFaqContent;
	@Getter
	@Setter
	private boolean deleteFaqContent;
	@Getter
	@Setter
	private boolean addFaqCategory;
	@Getter
	@Setter
	private boolean editFaqCategory;
	@Getter
	@Setter
	private boolean deleteFaqCategory;
	protected static final String expandedCatogories = "";
	protected static final String STATE_DISPLAY_MODE = "display_mode";
	private EventTrackingService eventTrackingService = null;

	/**
	 * Populate the state object, if needed.
	 */
	/*
	 * protected void initState(SessionState state, VelocityPortlet portlet,
	 * JetspeedRunData rundata) {
	 * 
	 * }
	 */
	@SuppressWarnings("deprecation")
	public String buildMainPanelContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) {
		context.put("tlang", rb);
		context.put(Menu.CONTEXT_ACTION, state.getAttribute(STATE_ACTION));
		String userId = SessionManager.getCurrentSessionUserId();
		String siteReference = null;
		String siteTitle = "";
		String siteId = ToolManager.getCurrentPlacement().getContext();
		String template = (String) getContext(rundata).get("template");

		try {
			siteReference = getSiteReference(siteId);
		} catch (IdUnusedException e) {
			context.put("nopermission", rb.getString("faqs.permissionerror"));
			return template + "_noaccess";
		}

		M_log.info(this + "faqs name " + template);

		context.put("addFaqContent", SecurityService.unlock("faqs.add", siteReference));
		context.put("editFaqContent", SecurityService.unlock("faqs.contentedit", siteReference));
		context.put("deleteFaqContent", SecurityService.unlock("faqs.contentdelete", siteReference));
		context.put("addFaqCategory", SecurityService.unlock("faqs.createcategory", siteReference));
		context.put("editFaqCategory", SecurityService.unlock("faqs.editcatergory", siteReference));
		context.put("deleteFaqCategory", SecurityService.unlock("faqs.categorydelete", siteReference));

		try {
			if (userId != null) {
				if (userId.equalsIgnoreCase("admin")) {
					context.put("userType", "Instructor");
				} else {
					context.put("userType",
							UserDirectoryService.getUser(SessionManager.getCurrentSessionUserId()).getType());
				}
			} else {
				// log
				return (String) getContext(rundata).get("faqs") + "_noaccess";
			}
		} catch (UserNotDefinedException idu) {
			// log
			return (String) getContext(rundata).get("faqs") + "_noaccess";
		}

		try {
			context.put("siteTitle", SiteService.getSite(siteId).getTitle().toString());
		} catch (IdUnusedException e) {
			System.out.println(e.getLocalizedMessage());
		}

		if ("EDIT_CATEGORY".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			context.put("category", state.getAttribute("categoryList"));
			context.put("systemDate", new Timestamp(new Date().getTime()));
			return template + "_edit_category";
		}
		if ("DELETE_CONFIRM".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			//context.put("deletedCategory", state.getAttribute("deletedCategory")); 
			context.put("categories", state.getAttribute("categories")); 
			context.put("contents", state.getAttribute("contents"));
 
			
			return template + "_delete_confirm";
		}
		if ("EDIT_FAQ_CONTENT".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			Menu createCategory = new MenuImpl();
			createCategory.add(new MenuEntry(rb.getString("link.createcategory"), "createCategory"));
			context.put(Menu.CONTEXT_MENU, createCategory);
			
			context.put("content", state.getAttribute("contentList"));
			context.put("faqCotegory", state.getAttribute("categoryListForFaq"));
			return template + "_edit_faq_content";
		}
		
		if ("EDIT_FAQ".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			Menu menu = new MenuImpl();
			if(SecurityService.unlock("faqs.contentedit", siteReference)) {
				menu.add(new MenuEntry(rb.getString("link.editfaq"), "editContent"));
			}
			if(SecurityService.unlock("faqs.add", siteReference)) {
				menu.add(new MenuEntry(rb.getString("link.addfaq"), "addFaq"));
			}
			if(SecurityService.unlock("faqs.contentdelete", siteReference)) {
				menu.add(new MenuEntry(rb.getString("link.remove"), "removeSpecific"));
			}			
			context.put(Menu.CONTEXT_MENU, menu);
				
			context.put("nextId", state.getAttribute("nextId"));
			context.put("previousId", state.getAttribute("previousId"));

			context.put("content", state.getAttribute("contentList"));
			context.put("faqCotegory", state.getAttribute("categoryListForFaq"));
			return template + "_edit_faq";
		}
		
		if ("EDIT_SPECIFIC_FAQ".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
		 	Menu menu = new MenuImpl();
			if(SecurityService.unlock("faqs.add", siteReference)) {
				menu.add(new MenuEntry(rb.getString("link.createcategory"), "createCategory"));
			}
			context.put(Menu.CONTEXT_MENU, menu);
			
			context.put("content", state.getAttribute("contentList"));
			context.put("faqCotegory", state.getAttribute("categoryListForFaq"));
			return template + "_edit_faq_content";
		}

		FaqsListForm listForm = new FaqsListForm();
		List categories = FaqsService.getFaqCategories(siteId);

		Vector v2 = (Vector) state.getAttribute(expandedCatogories);
		if (v2 != null) {
			// listForm.setExpandedCategories(expandCategory(rundata, context));
			Vector v = (Vector) state.getAttribute(expandedCatogories);
			listForm.setExpandedCategories(v);
		}

		if (listForm.getExpandedCategories() == null) {
			listForm.setExpandedCategories(new Vector());
		}
		boolean faqExist = listForm.isFaqExist();
		int countExpandedCategories = 0;
		Iterator i = categories.iterator();

		FaqCategory c = new FaqCategory();
		while (i.hasNext()) {
			c = (FaqCategory) i.next();
			// FaqContent cont = FaqContent
			Vector ids = listForm.getExpandedCategories();
			Iterator idsi = ids.iterator();

			while (idsi.hasNext()) {
				Integer id = (Integer) idsi.next();
				if (id.equals(c.getCategoryId())) {

					c.setExpanded(true);
					List contents = FaqsService.getFaqContents(c.getCategoryId());

					if (contents.size() > 0) {
						faqExist = faqExist | ((contents.size() > 0) && c.isExpanded() == true);
						if (c.isExpanded()) {
							countExpandedCategories++;
						}

					} else {
						faqExist = faqExist && (countExpandedCategories > 1);
					}
					c.setContents(contents);
					context.put("contents", c.getContents());
					continue;
				}
			}
		}

		listForm.setFaqExist(faqExist);
		context.put("faqExist", faqExist);
		listForm.setCategories(categories);
		context.put("categories", categories);

		Menu bar = new MenuImpl();
		bar.add(new MenuEntry(rb.getString("link.addfaq"), "addFaq"));
		bar.add(new MenuEntry(rb.getString("link.createcategory"), "createCategory"));
		context.put(Menu.CONTEXT_MENU, bar);

		if ("ADD_CATEGORY".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			context.put("systemDate", new Timestamp(new Date().getTime()));
			return template + "_add_category";
		}
		if ("ADD_FAQ".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			context.put("systemDate", new Timestamp(new Date().getTime()));	
			context.put("selectedCategory", state.getAttribute("selectedCategory"));
			context.put("newCategoryDesc", state.getAttribute("newCategoryDesc"));
			context.put("question", state.getAttribute("question"));
			context.put("answer", state.getAttribute("answer"));
			
			return template + "_add_faq";
		}
		context.put("service", FaqsService.getInstance());
		return template + "_main_view";

	}

	public static final String getSiteReference(String siteId) throws IdUnusedException {
		return SiteService.getSite(siteId).getReference(); // returns /site/site_id
	}

	public Vector expandCategory(RunData rundata, Context context) {
		Vector ids = null;
		FaqsListForm listForm = new FaqsListForm();

		int itemReference = Integer.parseInt(rundata.getParameters().getString("itemReference"));
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);

		listForm.setExpandCategoryId(itemReference);

		if (listForm.getExpandedCategories() == null) {
			listForm.setExpandedCategories(new Vector());
		}
		ids = listForm.getExpandedCategories();
		Iterator i = ids.iterator();

		while (i.hasNext()) {
			Integer id = (Integer) i.next();
			if (id.equals(itemReference)) {
				break;
			}
		}
		ids.add(listForm.getExpandCategoryId());
		listForm.setExpandedCategories(ids);
		state.setAttribute(expandedCatogories, ids);

		return ids;
	}

	public Vector collapseCategory(RunData rundata, Context context) {
		Vector ids = null;
		FaqsListForm listForm = new FaqsListForm();

		int itemReference = Integer.parseInt(rundata.getParameters().getString("itemReference"));
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);

		listForm.setExpandCategoryId(itemReference);
		listForm.setFaqExist(false);

		if (listForm.getExpandedCategories() == null) {
			listForm.setExpandedCategories(new Vector());
		}

		ids = (Vector) state.getAttribute("expandedCatogories");
		if (ids == null) {
			ids = new Vector();
		}
		// ids = listForm.getExpandedCategories();
		Iterator i = ids.iterator();

		while (i.hasNext()) {
			Integer id = (Integer) i.next();
			if (id.equals(itemReference)) {
				ids.remove(id);
			}
		}
		listForm.setExpandedCategories(ids);
		state.setAttribute(expandedCatogories, ids);

		return ids;
	}

	public void createCategory(RunData data, Context context) {
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_CATEGORY");
	}

	public void addFaq(RunData data, Context context) {
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
	}

	public void doCancel(RunData data, Context context) {
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, null);
	}

	public void saveCategory(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		String categoryDesc = data.getParameters().getString("categoryDesc").trim();

		if (categoryDesc == null || categoryDesc.length() < 1) {
			addAlert(state, rb.getString("faq.alert.nocategorydesc"));
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_CATEGORY");
		}else {
		// save category to db
		FaqsService.insertFaqCategory(ToolManager.getCurrentPlacement().getContext(), categoryDesc);

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(eventTrackingService.newEvent("faqs.categoryadd",
				ToolManager.getCurrentPlacement().getContext(), false));
		}
	}

	public void editcategory(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		int itemReference = Integer.parseInt(rundata.getParameters().getString("itemReference").trim());
		state.setAttribute("categoryList", FaqsService.getFaqCategory(itemReference));
		state.setAttribute(STATE_DISPLAY_MODE, "EDIT_CATEGORY");
	}

	public String updateEditedCategory(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		String categoryDesc = data.getParameters().getString("categoryDesc").trim();
		String categoryId = data.getParameters().getString("categoryId").trim();

		if (categoryDesc == null || categoryDesc.length() < 1) {
			addAlert(state, rb.getString("faq.alert.nocategorydesc"));
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "EDIT_CATEGORY");
		}
		// save edited category to db
		FaqsService.updateFaqCategory(categoryDesc, Integer.parseInt(categoryId));

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(eventTrackingService.newEvent("faqs.categoryedit",
				ToolManager.getCurrentPlacement().getContext(), false));
		return "";
	}

	//edit faq FAQ under catogery 
	public void editFaqContent(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
 
		state.setAttribute("contentList", FaqsService.getFaqContent(
				Integer.parseInt(rundata.getParameters().getString("itemReference").trim().split("-")[0])));  
		state.setAttribute("categoryListForFaq", FaqsService.getFaqCategory(
				Integer.parseInt(rundata.getParameters().getString("itemReference").trim().split("-")[1])));
		state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FAQ_CONTENT");
	} 

	//edit faq FAQ under next and previous buttons 
	public void editContent(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer  contentId = 0;
		Integer categoryId=0;
 
		//int contentId =Integer.parseInt(rundata.getParameters().getString("contentId").trim());  

		contentId =    (Integer) state.getAttribute("contentId") ;
		categoryId =    (Integer) state.getAttribute("categoryId");
		
		 state.setAttribute("contentList", FaqsService.getFaqContent(contentId));  
		 state.setAttribute("categoryListForFaq", FaqsService.getFaqCategory(categoryId)); 
		 
		state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FAQ_CONTENT");
	}
	
	//vijay: on click faq
	public void editFaq(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer  contentId = 0;
		Integer categoryId=0;
 
		String prevNext = null;     
		prevNext =   (String) state.getAttribute("prevNext");
				
		if(prevNext == null ) {
			contentId = Integer.parseInt(rundata.getParameters().getString("itemReference").trim().split("-")[0]);
			categoryId = Integer.parseInt(rundata.getParameters().getString("itemReference").trim().split("-")[1]);
			state.setAttribute("contentId", contentId);
			state.setAttribute("categoryId", categoryId);
		}else {
			contentId =    (Integer) state.getAttribute("contentId") ;
			categoryId =    (Integer) state.getAttribute("categoryId");
			state.setAttribute("contentId", contentId);
			state.setAttribute("categoryId", categoryId);
		}
        
		state.setAttribute("contentList", FaqsService.getFaqContentWithCategory(contentId));
		//state.setAttribute("categoryListForFaq", FaqsService.getFaqCategory(categoryId));

		List contents = FaqsService.getFaqContents(categoryId);
		Integer previousId = null;
		Integer nextId = null;
		Iterator i = contents.iterator();
		while (i.hasNext()) {
			FaqContent q = (FaqContent) i.next();
			if (q.getContentId().equals(contentId)) {
				if (i.hasNext()) {
					q = (FaqContent) i.next();
					nextId = q.getContentId();
				}continue;
			}
			previousId = q.getContentId();
		}
		state.setAttribute("nextId", nextId);
		state.setAttribute("previousId", previousId);
		
		state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FAQ");
	}
	
	public void previousFaq(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		int contentId = Integer.parseInt(rundata.getParameters().getString("previousId").trim());
		int categoryId = Integer.parseInt(rundata.getParameters().getString("categoryId").trim());
		state.setAttribute("prevNext", "prevNext");
		state.setAttribute("contentId", contentId);
		state.setAttribute("categoryId", categoryId);
		editFaq(rundata,context);
	}
	
	public void nextFaq(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		int contentId = Integer.parseInt(rundata.getParameters().getString("nextId").trim());
		int categoryId = Integer.parseInt(rundata.getParameters().getString("categoryId").trim());
		state.setAttribute("prevNext", "prevNext");
		state.setAttribute("contentId", contentId);
		state.setAttribute("categoryId", categoryId);
		editFaq(rundata,context);
	}
	
	public void returnToList(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null); 
	}
	
	
	public void viewFaq(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		int faqContentId = Integer.parseInt(rundata.getParameters().getString("itemReference").trim());
		// get category for category id
		List faqContent = FaqsService.getFaqContent(faqContentId);
		context.put("content", faqContent);

	}

	public String saveContent(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);

		state.setAttribute(STATE_DISPLAY_MODE, null);
		FaqContent faqContent = new FaqContent();
		String selectedCategory = data.getParameters().getString("selectedCategory").trim();
        String categoryId = selectedCategory.trim().split("-")[1];
		String newCategoryDesc = data.getParameters().getString("newCategoryDesc").trim();
		String question = data.getParameters().getString("question").trim();
		String answer = data.getParameters().getString("answer").trim();
		

		state.setAttribute("selectedCategory", selectedCategory);
		state.setAttribute("newCategoryDesc", newCategoryDesc);
		state.setAttribute("question", question);
		state.setAttribute("answer", answer);
		
		if ((selectedCategory.equals("-1")) & (newCategoryDesc.length() < 1)) {
			String errorCode = rb.getString("faq.content.alert.nocategorydesc");
			addAlert(state, errorCode);
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
		}

		if ((!selectedCategory.equals("-1")) & (newCategoryDesc.length() > 0)) {
			String errorCode = rb.getString("faq.content.alert.selectedboth");
			addAlert(state, errorCode);
			state.setAttribute(selectedCategory, selectedCategory);
			state.setAttribute(newCategoryDesc, newCategoryDesc);
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
		}

		if (question == null || question.length() < 1) {
			String errorCode = rb.getString("faq.alert.noquestiontitle");
			addAlert(state, errorCode);
			 state.setAttribute(selectedCategory, selectedCategory);
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
		}
		
		if (answer == null || answer.length() < 1) {
			String errorCode = rb.getString("faq.alert.noanswer");
			addAlert(state, errorCode);
			 state.setAttribute(selectedCategory, selectedCategory);
			 state.setAttribute(question, question);
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "ADD_FAQ");
		}
 
		FaqsService.insertFaqContent(question, answer, categoryId);
		
		return (String) state.setAttribute(STATE_DISPLAY_MODE, null); 
	}

	public String saveEditedFaqContent(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		String question = data.getParameters().getString("question").trim();
		String answer = data.getParameters().getString("answer").trim();
		FaqContent faqContent = new FaqContent();
		faqContent.setAnswer(data.getParameters().getString("answer").trim());
		faqContent.setContentId(Integer.parseInt(data.getParameters().getString("contentId").trim()));
		faqContent.setCategoryId(Integer.parseInt(data.getParameters().getString("categoryId").trim()));
		faqContent.setQuestion(question);
		
		if (question == null || question.length() < 1) {
			addAlert(state, rb.getString("faq.alert.noquestiontitle"));
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FAQ_CONTENT");
		}	
		
		if (answer == null || answer.length() < 1) {
			addAlert(state, rb.getString("faq.alert.noanswer"));
			return (String) state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FAQ_CONTENT");
		}	
		
		FaqsService.updateFaqContent(faqContent);
		return (String) state.setAttribute(STATE_DISPLAY_MODE, null); 
	}
	
	public void confirmRemove(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		String[] selectedCategories = data.getParameters().getStrings("selectedCategories");
		String[] selectedContents = data.getParameters().getStrings("selectedContents");
		List<FaqCategory> categoriesSelected = new ArrayList<FaqCategory>();
		List<FaqContent> contentsSelected = new ArrayList<FaqContent>();
		  if(selectedCategories != null || selectedContents != null) {
			
			if(null!=selectedCategories) {
			for( int i = 0; i < selectedCategories.length; i++){
     		    categoriesSelected.addAll(FaqsService.getFaqCategory(Integer.parseInt(selectedCategories[i])));
     		
     			if(null!=selectedContents) { 
     		    for( int j = 0; j < selectedContents.length; j++)
   	     		{   				
   					Iterator ci = FaqsService.getFaqContentWithCategory(Integer.parseInt(selectedContents[j])).iterator();
   					while (ci.hasNext()) {
   						FaqContent q = (FaqContent) ci.next();
   						if(q.getCategoryId() != Integer.parseInt(selectedCategories[i]) ) {
   							contentsSelected.addAll(FaqsService.getFaqContentWithCategory(Integer.parseInt(selectedContents[j])));
   						}
   						
   					}
            	}
            }
     		    
			}
			}else {
				//delete only cantent
				if(null!=selectedContents) {
					for( int j = 0; j < selectedContents.length; j++)
	   	     		{   				
	   				if (selectedContents != null) {
	   					Iterator ci = FaqsService.getFaqContentWithCategory(Integer.parseInt(selectedContents[j])).iterator();
	   					while (ci.hasNext()) {
	   						FaqContent q = (FaqContent) ci.next();
	   						contentsSelected.addAll(FaqsService.getFaqContentWithCategory(Integer.parseInt(selectedContents[j])));
	   					}
	   				}
	   				
	   		    	}
	            }
				}
			state.setAttribute("categories", categoriesSelected);
			state.setAttribute("contents", contentsSelected); 
		    state.setAttribute(STATE_DISPLAY_MODE, "DELETE_CONFIRM");
		  }else {
			  state.setAttribute(STATE_DISPLAY_MODE, null);
		  }
	}

	
	public void remove(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
 
		List<FaqCategory> categoriesSelected = (List<FaqCategory>) state.getAttribute("categories");
		List<FaqContent> contentsSelected = (List<FaqContent>) state.getAttribute("contents");
	
		if(null!=categoriesSelected) {
			//delete faq category and its contents
			Iterator ci = categoriesSelected.iterator();
			while (ci.hasNext()) {
				FaqCategory q = (FaqCategory) ci.next();
				FaqsService.deleteFaqCategory(q.getCategoryId());
			}
			
		}
		if(null!=contentsSelected) {
			//delete faq content only
			Iterator ci = contentsSelected.iterator();
			while (ci.hasNext()) {
					FaqContent q = (FaqContent) ci.next();
					FaqsService.deleteFaqContent(q.getContentId());
			}
		}
	state.setAttribute(STATE_DISPLAY_MODE, null);
		
	}
	
	public void removeSpecific(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer  contentId = 0;
		Integer categoryId=0;
		contentId =    (Integer) state.getAttribute("contentId") ;

	   state.setAttribute("categories",  null); 
	   state.setAttribute("contents",  FaqsService.getFaqContentWithCategory(contentId)); 
       state.setAttribute(STATE_DISPLAY_MODE, "DELETE_CONFIRM");
 		
	}
	
	
	
}
