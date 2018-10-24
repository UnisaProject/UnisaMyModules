/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/ModulePage.java $
 * $Id: ModulePage.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009, 2010, 2011 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
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

import java.util.*;
import java.io.Serializable;
import java.io.File;
import java.io.FileOutputStream;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import javax.faces.component.*;
import javax.faces.event.*;

import org.sakaiproject.util.ResourceLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.etudes.component.app.melete.CourseModule;
import org.etudes.component.app.melete.Module;
import org.etudes.component.app.melete.ModuleShdates;
import org.etudes.component.app.melete.ModuleDateBean;
import org.etudes.api.app.melete.*;

public abstract class ModulePage implements Serializable
{

	private StringBuffer author;
	private boolean calendarFlag;
	private int currYear;

	// added by rashmi -- 11/24
	private boolean editPageFlag;
	private String formName;

	private ModuleShdatesService moduleShdates;

	// license form rendering
	private boolean saveFlag;
	private String season;
	private ArrayList showDates;

	private boolean success = true;
	private int year;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(ModulePage.class);
	protected ModuleDateBean mdBean;

	protected ModuleObjService module;
	protected int moduleNumber;
	protected ModuleService moduleService;

	public ModulePage()
	{
	}

	/**
	 * Reset value on list auth page and redirect
	 * 
	 * @return list_auth_modules
	 */
	public String backToModules()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		listPage.setModuleDateBeans(null);
		return "list_auth_modules";
	}

	/**
	 * Reset value on list auth page and redirect
	 * 
	 * @return list_auth_modules
	 */
	public String cancel()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		listPage.setModuleDateBeans(null);
		return "list_auth_modules";
	}

	/**
	 * Consolidate author name by concatenating first name and last name.
	 * 
	 * @return consolidated author name
	 */
	public String getAuthor()
	{
		if (author == null)
		{
			author = new StringBuffer();
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
			author.append(mPage.getCurrentUser().getFirstName() + " ");
			author.append(mPage.getCurrentUser().getLastName());
		}
		return author.toString();
	}

	/**
	 * @return true if calendar tool is enabled in this course, false if not
	 */
	public boolean getCalendarFlag()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		return moduleService.checkCalendar(mPage.getCourse_id());
	}

	/**
	 * Return current year as a string
	 * 
	 * @return current year
	 */
	public String getCurrYear()
	{
		String yr = "";
		if (currYear != 0)
		{
			yr = new Integer(currYear).toString();
		}
		else
		{
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
			currYear = c.get(Calendar.YEAR);
			yr = new Integer(currYear).toString();
		}
		return yr;
	}

	public String getFormName()
	{
		return formName;
	}

	/**
	 * Gets the module. if module instance is not created , create here and set the default values. set author name from session.
	 * 
	 * @return module object
	 */
	public ModuleObjService getModule()
	{
		if (null == module)
		{
			module = new Module();
		
			success = false;

		}
		return module;
	}

	/**
	 * @return moduleDateBean object
	 */
	public ModuleDateBean getModuleDateBean()
	{
		return mdBean;
	}

	/**
	 * Doesn't seem to be in use
	 * 
	 * @return moduleNumber
	 */
	public String getModuleNumber()
	{
		if (moduleNumber != 0) return new Integer(moduleNumber).toString();

		CourseModule cm = (CourseModule) module.getCoursemodule();
		if (cm == null)
		{
			return "0";
		}

		moduleNumber = cm.getSeqNo();

		return new Integer(moduleNumber).toString();
	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService()
	{
		return moduleService;
	}

	/**
	 * @return moduleShdates object for this module
	 */
	public ModuleShdatesService getModuleShdates()
	{
		if (null == moduleShdates)
		{
			moduleShdates = new ModuleShdates();
			/*
			 * GregorianCalendar cal = new GregorianCalendar(); cal.set(Calendar.HOUR,8); cal.set(Calendar.MINUTE,0); cal.set(Calendar.SECOND,0); cal.set(Calendar.AM_PM,Calendar.AM); moduleShdates.setStartDate(cal.getTime()); cal.add(Calendar.YEAR, 1);
			 * cal.set(Calendar.HOUR,11); cal.set(Calendar.MINUTE,59); cal.set(Calendar.SECOND,0); cal.set(Calendar.AM_PM,Calendar.PM); moduleShdates.setEndDate(cal.getTime());
			 */}
		return moduleShdates;
	}

	/**
	 * Return term information such as Winter, Fall
	 * 
	 * @return term information
	 */
	public String getSeason()
	{
		if (season == null)
		{
			try
			{

				FacesContext context = FacesContext.getCurrentInstance();
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo ms = (MeleteSiteAndUserInfo) binding.getValue(context);
				season = ms.getCourseTerm();
				if (season == null) season = " ";

			}
			catch (Exception e)
			{
				season = " ";
				logger.debug("sesaon is null prob some error in meleteSiteand user");
			}
		}
		return season;
	}

	/**
	 * This method calculates the next 5 yrs and populate the combo box of year in add_module.jsp page.
	 * 
	 * @return list of next 5 years.
	 */
	public ArrayList getShowDates()
	{
		if (showDates == null)
		{
			showDates = new ArrayList();
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
			int yr = c.get(Calendar.YEAR);
			currYear = yr;

			for (int i = 0; i < 5; i++)
				showDates.add(new SelectItem((new Integer(yr + i)).toString()));
		}
		return showDates;
	}

	/**
	 * @return true if operation succeeded, false if not
	 */
	public boolean getSuccess()
	{
		return success;
	}

	/**
	 * Return year selected as a string
	 * 
	 * @return year
	 */
	public String getYear()
	{
		String yr = "";
		if (year != 0)
		{
			yr = new Integer(year).toString();
		}
		else
		{
			try
			{
				FacesContext context = FacesContext.getCurrentInstance();
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo ms = (MeleteSiteAndUserInfo) binding.getValue(context);

			}
			catch (Exception e)
			{
				yr = " ";
				logger.debug("error in getting yr ..prob becos of melete site and user");
			}
		}
		return yr;
	}

	/**
	 * Reset module number for edit page
	 * 
	 */
	public void resetModuleNumber()
	{
		moduleNumber = 0;
		editPageFlag = true;
	}

	/**
	 * Method to reset module values to clear module
	 * 
	 */
	public void resetModuleValues()
	{
		saveFlag = false;
		success = false;
		season = null;
		moduleNumber = 0;
		editPageFlag = false;
	}

	/**
	 * Submits the form and sends module and moduleShdates instances to the db level for adding a new module.
	 * 
	 * @return confirm_module or edit_module
	 */
	public abstract String save();

	/**
	 * Not in use
	 * 
	 * @param author
	 */
	public void setAuthor(String author)
	{
	}

	/**
	 * @param formName
	 *        name of form(add_module or edit_module)
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * @param module
	 *        module being added or edited
	 */
	public void setModule(ModuleObjService module)
	{

		this.module = module;

	}

	/**
	 * @param mdBean
	 *        moduleDateBean object
	 */
	public void setModuleDateBean(ModuleDateBean mdBean)
	{
		this.mdBean = mdBean;
	}

	/**
	 * @param ModuleService
	 *        The ModuleService to set.
	 */
	public void setModuleService(ModuleService moduleService)
	{
		this.moduleService = moduleService;
	}

	/**
	 * @param moduleShdates
	 *        moduleShdates object for this module
	 */
	public void setModuleShdates(ModuleShdatesService moduleShdates)
	{
		this.moduleShdates = moduleShdates;
	}

	/**
	 * @param season
	 *        term information
	 */
	public void setSeason(String season)
	{
		this.season = season;
	}

	/**
	 * @param success
	 *        value of success
	 */
	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	/**
	 * @param year
	 *        year select by user
	 */
	public void setYear(String year)
	{
		this.year = (new Integer(year)).intValue();
	}

	/**
	 * Adds message to faces context
	 * 
	 * @param ctx
	 *        FacesContext object
	 * @param msgName
	 *        Message name
	 * @param msgDetail
	 *        Message detail
	 * @param severity
	 *        Severity of message
	 */
	protected void addMessage(FacesContext context, String msgName, String msgDetail, FacesMessage.Severity severity)
	{
		FacesMessage msg = new FacesMessage(msgName, msgDetail);
		msg.setSeverity(severity);
		context.addMessage(null, msg);
	}

	public void validateField(FacesContext context, UIComponent toValidate, Object value)
	{
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		String check = (String) value;
	
		// title is required
		if (toValidate.getClientId(context).indexOf("title") != -1 && check.trim().length() == 0)
		{			
			((UIInput) toValidate).setValid(false);
			String errMsg = bundle.getString("title_reqd");
			context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "title_reqd", errMsg));
		}
		// title no more than 200 characters
		if (toValidate.getClientId(context).indexOf("title") != -1 && check != null && check.trim().length() > ModuleService.MAX_TITLE_LENGTH)
		{
			((UIInput) toValidate).setValid(false);
			String errMsg = bundle.getString("invalid_title_len");
			context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid_title_len", errMsg));
		}
		// description no more than 500 characters
		if (toValidate.getClientId(context).indexOf("description") != -1 && check != null && check.trim().length() > ModuleService.MAX_DESC_LENGTH)
		{
			((UIInput) toValidate).setValid(false);
			String errMsg = bundle.getString("invalid_description_len");
			context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid_description_len", errMsg));
		}
		// keywords no more than 250 characters
		if (toValidate.getClientId(context).indexOf("keywords") != -1 && check != null && check.trim().length() > ModuleService.MAX_TITLE_LENGTH)
		{
			((UIInput) toValidate).setValid(false);
			String errMsg = bundle.getString("invalid_keywords_len");
			context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid_keywords_len", errMsg));
		}
	}
}
