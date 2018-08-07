/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/StudentPreferencePage.java $
 *
 ***************************************************************************************
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
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
 **************************************************************************/
package org.etudes.tool.melete;

import java.util.ArrayList;
import java.util.Map;
import org.sakaiproject.util.ResourceLoader;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.component.app.melete.MeleteUserPreference;
import org.etudes.api.app.melete.MeleteAuthorPrefService;
import org.sakaiproject.component.cover.ServerConfigurationService;

public class StudentPreferencePage
{

	private MeleteAuthorPrefService authorPref;

	private MeleteUserPreference mup;
	private String userView;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(StudentPreferencePage.class);

	public StudentPreferencePage()
	{
	}

	/**
	 * @return student_preference page
	 */
	public String backToPrefsPage()
	{
		return "student_preference";
	}

	/**
	 * @return Returns the authorPref.
	 */
	public MeleteAuthorPrefService getAuthorPref()
	{
		return authorPref;
	}

	/**
	 * @return mup melete user preference
	 */
	public MeleteUserPreference getMup()
	{
		return mup;
	}

	/**
	 * @return userView true if expand is true, false if not
	 */
	public String getUserView()
	{
		getUserChoice();
		return userView;
	}

	/**
	 * @param authorPref
	 *        The authorPref to set.
	 */
	public void setAuthorPref(MeleteAuthorPrefService authorPref)
	{
		this.authorPref = authorPref;
	}

	/**
	 * @param mup
	 *        melete user preference
	 */
	public void setMup(MeleteUserPreference mup)
	{
		this.mup = mup;
	}

	public void changeViewValue(ValueChangeEvent event) throws AbortProcessingException
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		UIInput viewSelect = (UIInput) event.getComponent();
		setUserView((String) viewSelect.getValue());
		processChoice();
	}
	
	/**
	 * Set user preference and save it
	 * 
	 * @return student_preference page
	 */
	public String setUserChoice()
	{
		processChoice();
		return "student_preference";
	}

	private void processChoice()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		if (mup == null)
		{
			mup = new MeleteUserPreference();
		}
		try
		{
			if (userView.equals("true"))
			{
				mup.setViewExpChoice(true);
			}
			else
			{
				mup.setViewExpChoice(false);
			}
			mup.setUserId(mPage.getCurrentUser().getId());
			authorPref.insertUserChoice(mup);
			String successMsg = bundle.getString("Set_prefs_success");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Set_prefs_success", successMsg));
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("Set_prefs_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Set_prefs_fail", errMsg));
		}
	}
	/**
	 * @param userView
	 *        The userView to set.
	 */
	public void setUserView(String userView)
	{
		this.userView = userView;
	}

	/**
	 * Gets users expand or collapse preference
	 * 
	 */
	private void getUserChoice()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = (MeleteUserPreference) getAuthorPref().getUserChoice(mPage.getCurrentUser().getId());

		if (mup == null)
		{
			userView = "true";
		}
		else
		{
			if (mup.isViewExpChoice() == true)
			{
				userView = "true";
			}
			else
			{
				userView = "false";
			}
		}
		return;
	}
}
