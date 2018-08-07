/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/java/org/etudes/tool/melete/BookmarkPage.java $
 * $Id: BookmarkPage.java 3647 2012-12-02 22:30:41Z ggolden $
 ***********************************************************************************
 * Copyright (c) 2010, 2011 Etudes, Inc.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 **********************************************************************************/

package org.etudes.tool.melete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.BookmarkObjService;
import org.etudes.api.app.melete.BookmarkService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.component.app.melete.Bookmark;
import org.etudes.component.app.melete.Section;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.util.ResourceLoader;

public class BookmarkPage implements Serializable
{

	private List<? extends BookmarkObjService> bmList;

	private BookmarkObjService bookmark;

	private BookmarkService bookmarkService;

	private int deleteBookmarkId;

	private String deleteBookmarkTitle;

	private String fromModuleId;
	
	private String fromSectionId;
	
	private String fromModuleSeqNo;

	private String fromPage;

	private boolean nobmsFlag;

	private String sectionId;

	private String sectionTitle;

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(BookmarkPage.class);

	/** identifier field */

	protected MeleteSiteAndUserInfo meleteSiteAndUserInfo;

	protected SectionService sectionService;

	/**
	 * default constructor
	 */
	public BookmarkPage()
	{

	}

	/**
	 * Add a Bookmark.
	 * 
	 * @return
	 */
	public String addBookmark()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		if (bookmarkService == null) bookmarkService = getBookmarkService();

		/*
		 * if (bookmark == null) bookmark = new Bookmark();
		 */

		this.bookmark.setSiteId(getMeleteSiteAndUserInfo().getCurrentSiteId());
		this.bookmark.setUserId(getMeleteSiteAndUserInfo().getCurrentUser().getId());
		if (null != this.sectionId) this.bookmark.setSectionId(Integer.parseInt(this.sectionId));
		try
		{
			bookmarkService.insertBookmark(this.bookmark);
		}
		catch (Exception ex)
		{
			String errMsg = bundle.getString(ex.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), errMsg));
			return "failure";
		}
		resetValues();
		return "confirm_bookmark";

	}

	/**
	 * Cancel delete bookmarks.
	 * 
	 * @return
	 */
	public String cancelDeleteBookmark()
	{
		return "list_bookmarks";
	}

	/**
	 * Sets the delete bookmark values.
	 * 
	 * @param evt
	 *        ActionEvent
	 */
	public void deleteAction(ActionEvent evt)
	{
		UICommand cmdLink = (UICommand) evt.getComponent();

		List<?> cList = cmdLink.getChildren();
		if (cList == null || cList.size() < 2) return;
		UIParameter param1 = (UIParameter) cList.get(0);
		UIParameter param2 = (UIParameter) cList.get(1);

		setDeleteBookmarkId(((Integer) param1.getValue()).intValue());
		setDeleteBookmarkTitle((String) param2.getValue());
		return;
	}

	/**
	 * Deletes the Bookmark.
	 * 
	 * @return
	 */
	public String deleteBookmark()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try
		{
			bookmarkService.deleteBookmark(this.deleteBookmarkId);
		}
		catch (Exception ex)
		{
			String errMsg = bundle.getString(ex.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), errMsg));
			return "failure";
		}
		resetValues();
		// setFromPage("list_bookmarks");
		return "list_bookmarks";
	}

	/**
	 * On clicking the bookmark title from the bookmark listing, it takes author user to edit the section.
	 * 
	 * @param evt
	 *        ActionEvent
	 */
	public void editSection(ActionEvent evt)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		UICommand cmdLink = (UICommand) evt.getComponent();

		List<?> cList = cmdLink.getChildren();
		if (cList == null || cList.size() < 1) return;
		UIParameter param1 = (UIParameter) cList.get(0);
		Section sec = (Section) sectionService.getSection(((Integer) param1.getValue()).intValue());

/*		ValueBinding binding = Util.getBinding("#{editSectionPage}");
		EditSectionPage esPage = (EditSectionPage) binding.getValue(ctx);
		esPage.setEditInfo(sec);
		*/
		if ((fromModuleId != null) && (fromModuleId.trim().length() > 0))
		{
			if (sec.getModuleId() != Integer.parseInt(fromModuleId))
			{
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
				mPage.setNavigateCM(null);
			}
		}
		try
		{	
			ctx.getExternalContext().redirect("editmodulesections.jsf?sectionId=" + sec.getSectionId().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
	}

	/**
	 * Export Notes from bookmarks as siteTitlexxxx_my_bookmarks_notes.txt
	 * 
	 * @param evt
	 *        ActionEvent
	 */
	public void exportNotes(ActionEvent evt)
	{
		String packagingdirpath = ServerConfigurationService.getString("melete.packagingDir", "");
		FacesContext context = FacesContext.getCurrentInstance();

		File packagedir = null;
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		try
		{
			if (packagingdirpath == null || packagingdirpath.length() <= 0)
			{
				logger.warn("Melete Packaging Dir property is not set. Please check melete's readme file. ");
				return;
			}
			File basePackDir = new File(packagingdirpath);
			if (!basePackDir.exists()) basePackDir.mkdirs();

			String title = getMeleteSiteAndUserInfo().getCourseTitle();
			title = title.trim();

			String courseId = getMeleteSiteAndUserInfo().getCurrentSiteId();
			String userId = getMeleteSiteAndUserInfo().getCurrentUser().getId();

			packagedir = new File(basePackDir.getAbsolutePath() + File.separator + courseId + "_" + userId + File.separator + title.replace(' ', '_'));

			if (!packagedir.exists()) packagedir.mkdirs();

			String outputfilename = packagedir.getParentFile().getAbsolutePath() + File.separator + title.replace(' ', '_')
					+ "_my_bookmarks_notes.txt";

			bookmarkService.createFile(bmList, outputfilename);
			File outfile = new File(outputfilename);

			download(new File(outfile.getAbsolutePath()));

			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String errMsg = bundle.getString("list_bookmarks_export_error");
			FacesMessage msg = new FacesMessage(null, errMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
		finally
		{
			// delete the files - Directory courseid_instructorid and
			// it's child
			if (packagedir != null && packagedir.exists()) bookmarkService.deleteFiles(packagedir.getParentFile());

		}

	}

	/**
	 * Get all bookmarks.
	 * 
	 * @return a list of BookmarkObjService objects
	 */
	public List<?> getBmList()
	{
		if (bmList == null)
		{
			setNobmsFlag(true);
			bmList = bookmarkService.getBookmarks(getMeleteSiteAndUserInfo().getCurrentUser().getId(), getMeleteSiteAndUserInfo().getCurrentSiteId());

			if ((bmList != null) && (bmList.size() > 0))
			{
				setNobmsFlag(false);
			}

		}
		return bmList;
	}

	/**
	 * Read Bookmark from the database.
	 * 
	 * @return
	 */
	public BookmarkObjService getBookmark()
	{

		if (bookmark == null)
		{
			if (null != this.sectionId)
			{
				bookmark = bookmarkService.getBookmark(getMeleteSiteAndUserInfo().getCurrentUser().getId(), getMeleteSiteAndUserInfo()
						.getCurrentSiteId(), Integer.parseInt(this.sectionId));
				if (bookmark == null)
				{
					bookmark = new Bookmark();
					if (null != this.sectionId)
					{
						bookmark.setTitle(getSectionTitle());
					}
				}
			}
		}
		return bookmark;
	}

	/**
	 * @return Returns the BookmarkService.
	 */
	public BookmarkService getBookmarkService()
	{
		return bookmarkService;
	}

	/**
	 * Get to be deleted bookmark Id
	 * 
	 * @return
	 */
	public int getDeleteBookmarkId()
	{
		return deleteBookmarkId;
	}

	/**
	 * Get to be deleted bookmark title
	 * 
	 * @return
	 */
	public String getDeleteBookmarkTitle()
	{
		return deleteBookmarkTitle;
	}

	/**
	 * @return the fromModuleId
	 */
	public String getFromModuleId()
	{
		return fromModuleId;
	}
	
	/**
	 * @return the fromSectionId
	 */
	public String getFromSectionId()
	{
		return fromSectionId;
	}
		
	
	/**
	 * @return the fromModuleSeqNo
	 */
	public String getFromModuleSeqNo()
	{
		return fromModuleSeqNo;
	}

	/**
	 * Get the start page
	 * 
	 * @return
	 */
	public String getFromPage()
	{
		return fromPage;
	}

	/**
	 * Get the user's role in Melete tool of the site.
	 * 
	 * @return true if author otherwise false.
	 */
	public boolean getInstRole()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try
		{
			return getMeleteSiteAndUserInfo().isUserAuthor();
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("auth_failed");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "auth_failed", errMsg));
			logger.warn(e.toString());
		}
		return false;
	}

	/**
	 * Get no bookmarks flag and show the message.
	 * 
	 * @return
	 */
	public boolean getNobmsFlag()
	{
		getBmList();
		return this.nobmsFlag;
	}

	/**
	 * Get the section Id.
	 * 
	 * @return
	 */
	public String getSectionId()
	{
		return sectionId;
	}

	/**
	 * @return Returns the SectionService.
	 */
	public SectionService getSectionService()
	{
		return sectionService;
	}

	/**
	 * Get section title
	 * 
	 * @return
	 */
	public String getSectionTitle()
	{
		return sectionTitle;
	}

	/**
	 * Get Section's title from db.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 */
	public String getTitle(int sectionId)
	{
		return sectionService.getSectionTitle(sectionId);
	}

	/**
	 * 
	 * @return
	 */
	public boolean getTrueFlag()
	{
		return true;
	}

	/**
	 * Reset values and go to Listing page.
	 * 
	 * @return
	 */
	public String gotoMyBookmarks()
	{
		resetValues();
		FacesContext context = FacesContext.getCurrentInstance();
		setFromPage((String) context.getExternalContext().getRequestParameterMap().get("fromPage"));
		if (getInstRole() && this.fromPage != null && this.fromPage.equals("list_auth_modules"))
		{
			ValueBinding lamBinding = Util.getBinding("#{listAuthModulesPage}");
			ListAuthModulesPage lamPage = (ListAuthModulesPage) lamBinding.getValue(context);
			if (!lamPage.saveModuleDates()) 
			{
				return "list_auth_modules";
			}
		}
		String fromModuleIdStr = (String) context.getExternalContext().getRequestParameterMap().get("fromModuleId");
		if (fromModuleIdStr != null)
		{
			setFromModuleId(fromModuleIdStr);
		}
		String fromModuleSeqNoStr = (String) context.getExternalContext().getRequestParameterMap().get("fromModuleSeqNo");
		if (fromModuleSeqNoStr != null)
		{
			setFromModuleSeqNo(fromModuleSeqNoStr);
		}
		fromSectionId = (String) context.getExternalContext().getRequestParameterMap().get("fromSectionId");
		
		return "list_bookmarks";
	}

	/**
	 * Reset values and go to Listing page.
	 * 
	 * @param from
	 *        The start point
	 * @return
	 */
	/*public String gotoMyBookmarks(String from, int fromModuleId)
	{
		resetValues();
		setFromPage(from);
		setFromModuleId(fromModuleId);
		return "list_bookmarks";
	}*/

	
	public String gotoMyBookmarks(String from, int fromModuleId, String fromSectionId)
	{
		resetValues();
		setFromPage(from);
		setFromModuleId(Integer.toString(fromModuleId));
		setFromSectionId(fromSectionId);
		return "list_bookmarks";
	}

	/**
	 * Navigation
	 */
	public String redirectDeleteLink()
	{
		return "delete_bookmark";
	}

	/**
	 * Navigation
	 */
	public String redirectEditSection()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding lamBinding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lamPage = (ListAuthModulesPage) lamBinding.getValue(ctx);
		if (!lamPage.saveModuleDates()) return "list_auth_modules";
		return "editmodulesections";
	}

	/**
	 * Navigation
	 */
	public String redirectExportNotes()
	{
		return "list_bookmarks";
	}

	/**
	 * Refresh the page.
	 * 
	 * @return
	 */
	public String refreshAction()
	{
		resetValues();
		return "list_bookmarks";
	}

	/**
	 * Reset Values.
	 */
	public void resetValues()
	{
		deleteBookmarkId = 0;
		deleteBookmarkTitle = null;
		bmList = null;
		nobmsFlag = true;
	}

	/**
	 * Go back to start page.
	 * 
	 * @return
	 */
	public String returnAction()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		if (this.fromPage != null)
		{
			if (this.fromPage.equals("list_auth_modules"))
			{
				ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
				ListAuthModulesPage lamPage = (ListAuthModulesPage) binding.getValue(context);
				lamPage.resetValues();
			}
			if (this.fromPage.equals("list_modules_student"))
			{
				ValueBinding binding = Util.getBinding("#{listModulesPage}");
				ListModulesPage lmPage = (ListModulesPage) binding.getValue(context);
				lmPage.resetValues();
			}
			if (this.fromPage.equals("view_section"))
			{
				String fromModuleIdStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleId");
				if (fromModuleIdStr != null)
				{
					setFromModuleId(fromModuleIdStr);
				}
				String fromModuleSeqNoStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleSeqNo");
				if (fromModuleSeqNoStr != null)
				{
					setFromModuleSeqNo(fromModuleSeqNoStr);
				}
				fromSectionId = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromSectionId");
				
				/*ValueBinding binding = Util.getBinding("#{viewSectionsPage}");
				ViewSectionsPage vsPage = (ViewSectionsPage) binding.getValue(context);
				vsPage.resetValues();
				vsPage.setSection(null);
				vsPage.setModule(null);
				vsPage.setModuleId(Integer.parseInt(this.fromModuleId));
				vsPage.setModuleSeqNo(Integer.parseInt(this.fromModuleSeqNo));
				vsPage.setSectionId(Integer.parseInt(this.fromSectionId));*/
				try
				{
					context.getExternalContext().redirect("view_section.jsf?moduleId="+this.fromModuleId+"&sectionId="+this.fromSectionId+"&moduleSeqNo="+this.fromModuleSeqNo);
				}
				catch (Exception e)
				{
					return "list_auth_modules";
				}
			}
			if (this.fromPage.equals("view_module"))
			{
				String fromModuleIdStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleId");
				if (fromModuleIdStr != null)
				{
					setFromModuleId(fromModuleIdStr);
				}
				String fromModuleSeqNoStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleSeqNo");
				if (fromModuleSeqNoStr != null)
				{
					setFromModuleSeqNo(fromModuleSeqNoStr);
				}
				/*ValueBinding binding = Util.getBinding("#{viewModulesPage}");
				ViewModulesPage vmPage = (ViewModulesPage) binding.getValue(context);
				vmPage.setViewMbean(null);
				vmPage.setModuleId(Integer.parseInt(this.fromModuleId));
				vmPage.setModuleSeqNo(Integer.parseInt(this.fromModuleSeqNo));*/
				try
				{
					context.getExternalContext().redirect("view_module.jsf?modId=" + this.fromModuleId + "&modSeqNo=" + this.fromModuleSeqNo);
				}
				catch (Exception e)
				{
					return "list_auth_modules";
				}
			}
			if (this.fromPage.equals("view_whats_next"))
			{
				String fromModuleIdStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleId");
				if (fromModuleIdStr != null)
				{
					setFromModuleId(fromModuleIdStr);
				}
				String fromModuleSeqNoStr = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromModuleSeqNo");
				if (fromModuleSeqNoStr != null)
				{
					setFromModuleSeqNo(fromModuleSeqNoStr);
				}
				fromSectionId = (String) context.getExternalContext().getRequestParameterMap().get("ManageBookmarksForm:fromSectionId");
			
				/*ValueBinding binding = Util.getBinding("#{viewNextStepsPage}");
				ViewNextStepsPage vnPage = (ViewNextStepsPage) binding.getValue(context);
				vnPage.setModule(null);
				vnPage.setPrevSecId(Integer.parseInt(this.fromSectionId));
				vnPage.setPrevModId(Integer.parseInt(this.fromModuleId));
				vnPage.setNextSeqNo(Integer.parseInt(this.fromModuleSeqNo));*/
				try
				{
					context.getExternalContext().redirect("view_whats_next.jsf?nextSeqNo="+this.fromModuleSeqNo+"&prevSecId="+this.fromSectionId+"&prevModId="+this.fromModuleId);
				}
				catch (Exception e)
				{
					return "list_auth_modules";
				}
			}
			if(this.fromPage.equals("editmodulesections"))
			{
				try
				{
					context.getExternalContext().redirect("editmodulesections.jsf?sectionId=" + fromSectionId);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return "list_auth_modules";
				}
			}
		}
		else
		{
			this.fromPage = "list_bookmarks";
		}
		return this.fromPage;
	}

	public void setBmList(List<? extends BookmarkObjService> bmList)
	{
		this.bmList = bmList;
	}

	/**
	 * 
	 * @param bookmark
	 */
	public void setBookmark(BookmarkObjService bookmark)
	{
		this.bookmark = bookmark;
	}

	/**
	 * @param bookmarkService
	 *        The bookmarkService to set.
	 */
	public void setBookmarkService(BookmarkService bookmarkService)
	{
		this.bookmarkService = bookmarkService;
	}

	/**
	 * 
	 * @param deleteBookmarkId
	 *        set bookmark Id
	 */
	public void setDeleteBookmarkId(int deleteBookmarkId)
	{
		this.deleteBookmarkId = deleteBookmarkId;
	}

	/**
	 * 
	 * @param deleteBookmarkTitle
	 *        set bookmark title
	 */
	public void setDeleteBookmarkTitle(String deleteBookmarkTitle)
	{
		this.deleteBookmarkTitle = deleteBookmarkTitle;
	}

	/**
	 * @param fromModuleId
	 *        the fromModuleId to set
	 */
	public void setFromModuleId(String fromModuleId)
	{
		this.fromModuleId = fromModuleId;
	}
	
	/**
	 * @param fromSectionId
	 *        the fromSectionId to set
	 */
	public void setFromSectionId(String fromSectionId)
	{
		this.fromSectionId = fromSectionId;
	}	
	
	/**
	 * @param fromModuleSeqNo
	 *        the fromModuleSeqNo to set
	 */
	public void setFromModuleSeqNo(String fromModuleSeqNo)
	{
		this.fromModuleSeqNo = fromModuleSeqNo;
	}	

	/**
	 * Set the start page
	 * 
	 * @param fromPage
	 *        the start page
	 */
	public void setFromPage(String fromPage)
	{
		this.fromPage = fromPage;
	}

	/**
	 * Set no bookmarks flag.
	 * 
	 * @param nobmsFlag
	 *        No Bookmarks flag
	 */
	public void setNobmsFlag(boolean nobmsFlag)
	{
		this.nobmsFlag = nobmsFlag;
	}

	/**
	 * Set section Id
	 * 
	 * @param sectionId
	 */
	public void setSectionId(String sectionId)
	{
		this.sectionId = sectionId;
	}

	/**
	 * @param SectionService
	 *        The SectionService to set.
	 */
	public void setSectionService(SectionService sectionService)
	{
		this.sectionService = sectionService;
	}

	/**
	 * 
	 * @param sectionTitle
	 */
	public void setSectionTitle(String sectionTitle)
	{
		this.sectionTitle = sectionTitle;
	}

	/**
	 * On clicking the bookmark title from the bookmark listing, it takes student user to view the section.
	 * 
	 */
	public void viewSection(ActionEvent evt)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();

		UICommand cmdLink = (UICommand) evt.getComponent();

		List<?> cList = cmdLink.getChildren();
		if (cList == null || cList.size() < 1) return;
		UIParameter param1 = (UIParameter) cList.get(0);
		/*ValueBinding binding = Util.getBinding("#{viewSectionsPage}");

		ViewSectionsPage vsPage = (ViewSectionsPage) binding.getValue(ctx);

		vsPage.resetValues();
		vsPage.setSectionId(((Integer) param1.getValue()).intValue());
		Section sec = (Section) sectionService.getSection(((Integer) param1.getValue()).intValue());
		vsPage.setModuleId(sec.getModuleId());
		vsPage.setModuleSeqNo(sec.getModule().getCoursemodule().getSeqNo());
		vsPage.setSection(sec);
		vsPage.setModule(null);*/
		Section sec = (Section) sectionService.getSection(((Integer) param1.getValue()).intValue());
		if ((fromModuleId != null) && (fromModuleId.trim().length() > 0))
		{
			if (sec.getModuleId() != Integer.parseInt(fromModuleId))
			{
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
				mPage.setNavigateCM(null);
			}
		}
		try
		{
			ctx.getExternalContext().redirect("view_section.jsf?moduleId="+sec.getModuleId()+"&sectionId="+((Integer) param1.getValue()).intValue()+"&moduleSeqNo="+sec.getModule().getCoursemodule().getSeqNo());
		}
		catch (Exception e)
		{
			return;
		}
		
	}

	/**
	 * writes the text file to browser
	 * 
	 * @param file
	 *        - text file to download
	 * @throws Exception
	 */
	private void download(File file) throws Exception
	{
		FileInputStream fis = null;
		ServletOutputStream out = null;
		try
		{
			String disposition = "attachment; filename=\"" + file.getName() + "\"";
			fis = new FileInputStream(file);

			FacesContext cxt = FacesContext.getCurrentInstance();
			ExternalContext context = cxt.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) context.getResponse();
			response.setContentType("application/text"); // application/text
			response.addHeader("Content-Disposition", disposition);
			// Contributed by Diego for ME-233
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "public, post-check=0, must-revalidate, pre-check=0");

			out = response.getOutputStream();

			int len;
			byte buf[] = new byte[102400];
			while ((len = fis.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}

			out.flush();
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (out != null) out.close();
			}
			catch (IOException e1)
			{
			}

			try
			{
				if (fis != null) fis.close();
			}
			catch (IOException e2)
			{
			}
		}
	}

	/**
	 * get MeleteSiteAndUserInfo
	 * 
	 * @return
	 */
	private MeleteSiteAndUserInfo getMeleteSiteAndUserInfo()
	{
		if (meleteSiteAndUserInfo == null)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			meleteSiteAndUserInfo = (MeleteSiteAndUserInfo) binding.getValue(context);
		}
		return meleteSiteAndUserInfo;
	}

}
