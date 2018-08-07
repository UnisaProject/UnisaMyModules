/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/AuthorPreferencePage.java $
 * $Id: AuthorPreferencePage.java 82172 2012-11-27 21:36:07Z rashmi@etudes.org $
 ***********************************************************************************
 *
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
 **********************************************************************************/
package org.etudes.tool.melete;

import java.util.ArrayList;

import org.sakaiproject.util.ResourceLoader;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.component.app.melete.MeleteSitePreference;
import org.etudes.component.app.melete.MeleteUserPreference;
import org.etudes.api.app.melete.MeleteAuthorPrefService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.api.app.melete.exception.UserErrorException;
import org.sakaiproject.component.cover.ServerConfigurationService;

public class AuthorPreferencePage
{
	final static String SFERYX = "Sferyx Editor";
	final static String FCKEDITOR = "FCK Editor";
	final static String CKEDITOR = "CK Editor";
	private String editorChoice;
	private String userEditor;
	private String userView = "true";
	private String showLTI = "false";
	private ArrayList<SelectItem> availableEditors;
	private boolean displaySferyx = false;

	// rendering flags
	private boolean shouldRenderSferyx = false;
	private boolean shouldRenderFCK = false;
	private boolean shouldRenderEditorPanel = false;
	private MeleteAuthorPrefService authorPref;
	private MeleteUserPreference mup;
	private MeleteSitePreference msp;
	private String materialPrintable = "false";
	private String materialAutonumber = "false";
	public String formName;
	private String displayLicenseCode;
	private String displayOwner;
	private String displayYear;
	private boolean displayAllowCmrcl;
	private Integer displayAllowMod;

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(AuthorPreferencePage.class);
	protected SectionService sectionService;

	/**
	 * default constructor
	 */
	public AuthorPreferencePage()
	{
	}

	/**
	 * Reads Editor preference if set for the current user. If not, gets the default editor specified in sakai.properties
	 */
	public void setEditorFlags()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = getMup(mPage.getCurrentUser().getId());
		shouldRenderSferyx = false;
		shouldRenderFCK = false;

		// if no choice is set then read default from sakai.properties
		if ((mup == null) || (mup.getEditorChoice() == null))
		{
			editorChoice = getMeleteDefaultEditor();
		}
		else
		{
			editorChoice = mup.getEditorChoice();
		}
		if (editorChoice.equals(SFERYX))
		{
			shouldRenderSferyx = true;
			shouldRenderFCK = false;
		}
		else if (editorChoice.equals(FCKEDITOR) || editorChoice.equals(CKEDITOR))
		{
			String defaultEditor = ServerConfigurationService.getString("melete.wysiwyg.editor", "");
			if (CKEDITOR.equals(defaultEditor) && editorChoice.equals(FCKEDITOR))
				editorChoice = CKEDITOR;
			shouldRenderSferyx = false;
			shouldRenderFCK = true;
		}

	}

	/**
	 * Reset the values. refreshes the page.
	 */
	public void resetValues()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = getMup(mPage.getCurrentUser().getId());

		binding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) binding.getValue(context);
		if (lPage.getLicenseCodes() == null)
		{
			lPage.setInitialValues(this.formName, mup);
		}
	}

	/**
	 * Reads other preferences set for the current user.
	 */
	private void getUserChoice()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = getMup(mPage.getCurrentUser().getId());
		msp = (MeleteSitePreference) getAuthorPref().getSiteChoice(mPage.getCurrentSiteId());

		// reset flags
		setEditorFlags();

		if (mup != null && (mup.isViewExpChoice() == null || !mup.isViewExpChoice().booleanValue()))
			userView = "false";
		else
			userView = "true";

		if (mup == null || mup.isShowLTIChoice() == null)
			showLTI = "false";
		else
		{
			// logger.debug("mup LTi choice is:" + mup.isShowLTIChoice());
			showLTI = mup.isShowLTIChoice().toString();
		}

		if (msp == null)
		{
			materialPrintable = ServerConfigurationService.getString("melete.print", "true");
		}
		else if (msp != null && msp.isPrintable() == null)
		{
			materialPrintable = ServerConfigurationService.getString("melete.print", "true");
		}
		else
			materialPrintable = msp.isPrintable().toString();

		if (msp != null && msp.isAutonumber()) materialAutonumber = "true";

		return;
	}

	/**
	 * Get user's preference set for melete.
	 * 
	 * @param userId
	 *        The user Id
	 * @return
	 */
	public MeleteUserPreference getMup(String userId)
	{
		MeleteUserPreference mup = (MeleteUserPreference) getAuthorPref().getUserChoice(userId);
		return mup;
	}

	/**
	 * Read by default editor specified for melete in sakai.properties. If melete.wysiwyg.editor property is not set then read from wysiwyg.editor property.
	 * 
	 * @return
	 */
	public String getMeleteDefaultEditor()
	{
		String defaultEditor = ServerConfigurationService.getString("melete.wysiwyg.editor", "");
//		logger.debug("getMeleteDefaultEditor:" + defaultEditor + "...");

		if (defaultEditor == null || defaultEditor.length() == 0)
		{
			defaultEditor = ServerConfigurationService.getString("wysiwyg.editor", "");
			if (logger.isDebugEnabled()) logger.debug("default editor ie from wysiwyg.editor is" + defaultEditor);
			if (defaultEditor.equalsIgnoreCase(FCKEDITOR) || defaultEditor.equalsIgnoreCase(CKEDITOR) ||  defaultEditor.startsWith("FCK") || defaultEditor.startsWith("fck"))
				defaultEditor = FCKEDITOR;
		}

		return defaultEditor;
	}

	/**
	 * Get all available editors. Reads from sakai.properties like melete.wysiwyg.editor1
	 * 
	 * @return The list of SelectItem objects
	 */
	public ArrayList<SelectItem> getAvailableEditors()
	{
		if (availableEditors == null)
		{
			availableEditors = new ArrayList<SelectItem>();
			int count = ServerConfigurationService.getInt("melete.wysiwyg.editor.count", 0);

			ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
			for (int i = 1; i <= count; i++)
			{
				String label = ServerConfigurationService.getString("melete.wysiwyg.editor" + i, "");
				String displayLabel = "";
				if (label.equalsIgnoreCase(FCKEDITOR)) displayLabel = bundle.getString("FCKEDITOR");
				if (label.equalsIgnoreCase(CKEDITOR)) displayLabel = bundle.getString("CKEDITOR");
				if (label.equalsIgnoreCase(SFERYX)) displayLabel = bundle.getString("SFERYX");
				availableEditors.add(new SelectItem(label, displayLabel));
			}
		}
		return availableEditors;
	}

	/**
	 * if no editors are set for Melete. Don't show the editor preference choice.
	 * 
	 * @return
	 */
	public boolean isShouldRenderEditorPanel()
	{
		resetValues();
		int count = ServerConfigurationService.getInt("melete.wysiwyg.editor.count", 0);
		if (count > 0) shouldRenderEditorPanel = true;
		return shouldRenderEditorPanel;
	}

	/**
	 * @return Returns the shouldRenderFCK.
	 */
	public boolean isShouldRenderFCK()
	{
		getUserChoice();
		return shouldRenderFCK;
	}

	/**
	 * @param shouldRenderFCK
	 *        The shouldRenderFCK to set.
	 */
	public void setShouldRenderFCK(boolean shouldRenderFCK)
	{
		this.shouldRenderFCK = shouldRenderFCK;
	}

	/**
	 * @return Returns the shouldRenderSferyx.
	 */
	public boolean isShouldRenderSferyx()
	{
		getUserChoice();
		return shouldRenderSferyx;
	}

	/**
	 * @param shouldRenderSferyx
	 *        The shouldRenderSferyx to set.
	 */
	public void setShouldRenderSferyx(boolean shouldRenderSferyx)
	{
		this.shouldRenderSferyx = shouldRenderSferyx;
	}

	/**
	 * Navigation rules
	 */
	public String goToEditorPreference()
	{
		return "pref_editor";
	}

	/**
	 * Default editor is Sferyx
	 * 
	 * @return Returns the userEditor.
	 */
	public String getUserEditor()
	{
		getEditorChoice();
		if (editorChoice == null)
			userEditor = SFERYX;
		else
			userEditor = editorChoice;
		return userEditor;
	}

	/**
	 * 
	 * @return
	 */
	public String getEditorChoice()
	{
		getUserChoice();
		return editorChoice;
	}

	/**
	 * @param userEditor
	 *        The userEditor to set.
	 */
	public void setUserEditor(String userEditor)
	{
		this.userEditor = userEditor;
	}

	/**
	 * Get preferences for list pages.
	 * 
	 * @return
	 */
	public String getUserView()
	{
		getUserChoice();
		return userView;
	}

	/**
	 * @param userView
	 *        The userView to set.
	 */
	public void setUserView(String userView)
	{
		this.userView = userView;
	}
	
	public void saveChoices(UIInput licenseSelect)
	{
		setUserEditor((String)((UIInput)((UIComponent)licenseSelect.findComponent("UserPreferenceForm")).findComponent("editorChoice")).getValue());
		setUserView((String)((UIInput)((UIComponent)licenseSelect.findComponent("UserPreferenceForm")).findComponent("viewChoice")).getValue());
		setShowLTI((String)((UIInput)((UIComponent)licenseSelect.findComponent("UserPreferenceForm")).findComponent("ltiChoice")).getValue());
		setMaterialPrintable((String)((UIInput)((UIComponent)licenseSelect.findComponent("UserPreferenceForm")).findComponent("printChoice")).getValue());
		setMaterialAutonumber((String)((UIInput)((UIComponent)licenseSelect.findComponent("UserPreferenceForm")).findComponent("numberChoice")).getValue());
		
		processChoice();
	}

	/*
	 * Save user's preferences.
	 */
	public void setChoices() throws Exception
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		if (mup == null)
		{
			mup = new MeleteUserPreference();
		}

		if (userEditor != null)
		{
			mup.setEditorChoice(userEditor);
		}
		if (userView.equals("true"))
		{
			mup.setViewExpChoice(true);
		}
		else
		{
			mup.setViewExpChoice(false);
		}

		if (showLTI.equals("true"))
			mup.setShowLTIChoice(true);
		else
			mup.setShowLTIChoice(false);

		binding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) binding.getValue(context);
		// validation: check for license and year lengths
		if (!lPage.getLicenseCodes().equals(LicensePage.NO_CODE))
		{
			lPage.validateLicenseLengths();
		}
		mup = lPage.processLicenseInformation(mup);
		mup.setUserId(mPage.getCurrentUser().getId());
		authorPref.insertUserChoice(mup);

		// set Site Preferences
		if (msp == null)
		{
			msp = new MeleteSitePreference();
			msp.setPrefSiteId(mPage.getCurrentSiteId());
		}

		// set print preference
		if (materialPrintable.equals("true"))
			msp.setPrintable(true);
		else
			msp.setPrintable(false);

		// set autonumber preference
		if (materialAutonumber.equals("true"))
			msp.setAutonumber(true);
		else
			msp.setAutonumber(false);

		authorPref.insertUserSiteChoice(msp);

	}

	/**
	 * Called from the page to save the choices.
	 * 
	 * @return
	 */
	public String setUserChoice()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		if (processChoice())
		{	
			String successMsg = bundle.getString("Set_prefs_success");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Set_prefs_success", successMsg));
		}
		return "author_preference";
	}
	
	public String autoSave()
	{
		if (processChoice()) return "#";
		return "author_preference";
	}
	
	
	private boolean processChoice()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		try
		{
			setChoices();
			return true;
		}
		catch (UserErrorException uex)
		{
			String errMsg = bundle.getString(uex.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, uex.getMessage(), errMsg));
			return false;
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("Set_prefs_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Set_prefs_fail", errMsg));
			return false;
		}
	}

	/**
	 * Get the printable preference.
	 * 
	 * @param site_id
	 *        The site Id
	 * @return
	 */
	public boolean isMaterialPrintable(String site_id)
	{
		Boolean defaultPrint = new Boolean(ServerConfigurationService.getString("melete.print", "true"));
		MeleteSitePreference checkMsp = (MeleteSitePreference) getAuthorPref().getSiteChoice(site_id);
		if (checkMsp != null && checkMsp.isPrintable() != null)
			return checkMsp.isPrintable();
		else
			return defaultPrint.booleanValue();
	}

	/**
	 * Get the preference for auto-numbering
	 * 
	 * @param site_id
	 *        The Site id
	 * @return
	 */
	public boolean isMaterialAutonumber(String site_id)
	{
		MeleteSitePreference checkMsp = (MeleteSitePreference) getAuthorPref().getSiteChoice(site_id);
		if (checkMsp != null)
			return checkMsp.isAutonumber();
		else
			return false;
	}

	/**
	 * Navigation
	 */
	public String backToPrefsPage()
	{
		userEditor = editorChoice;
		return "author_preference";
		// return "pref_editor";
	}

	/**
	 * Overwrite all section licenses with the preferred one. Sets all preference.
	 */
	public String changeAllLicense()
	{
		if (!processChoice()) return "author_preference";
		return "confirm_license";
	}

	/**
	 * Change License of all sections On success return back to author mode
	 */
	public String changeLicenseAction()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = getMup(mPage.getCurrentUser().getId());
		String courseId = mPage.getCurrentSiteId();
		try
		{
			sectionService.changeLicenseForAll(courseId, mup);
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("all_license_change_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "all_license_change_fail", errMsg));
			return "author_preference";
		}

		// refresh list page
		binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		return "list_auth_modules";
	}

	/**
	 * On cancel return back to preferences page
	 */
	public String cancelLicenseAction()
	{
		return "author_preference";
	}

	/**
	 * @return Returns the authorPref.
	 */
	public MeleteAuthorPrefService getAuthorPref()
	{
		return authorPref;
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
	 * @return Returns the displaySferyx.
	 */
	public boolean isDisplaySferyx()
	{
		return displaySferyx;
	}

	/**
	 * @param displaySferyx
	 *        The displaySferyx to set.
	 */
	public void setDisplaySferyx(boolean displaySferyx)
	{
		this.displaySferyx = displaySferyx;
	}

	/**
	 * 
	 * @return
	 */
	public MeleteUserPreference getMup()
	{
		return mup;
	}

	/**
	 * 
	 * @param mup
	 */
	public void setMup(MeleteUserPreference mup)
	{
		this.mup = mup;
	}

	/**
	 * @return the materialPrintable
	 */
	public String getMaterialPrintable()
	{
		return this.materialPrintable;
	}

	/**
	 * @param materialPrintable
	 *        the materialPrintable to set
	 */
	public void setMaterialPrintable(String materialPrintable)
	{
		this.materialPrintable = materialPrintable;
	}

	/**
	 * @return the materialAutonumber
	 */
	public String getMaterialAutonumber()
	{
		return this.materialAutonumber;
	}

	/**
	 * @param materialAutonumber
	 *        the materialAutonumber to set
	 */
	public void setMaterialAutonumber(String materialAutonumber)
	{
		this.materialAutonumber = materialAutonumber;
	}

	/**
	 * 
	 * @return
	 */
	public String getShowLTI()
	{
		return showLTI;
	}

	/**
	 * 
	 * @param showLTI
	 */
	public void setShowLTI(String showLTI)
	{
		this.showLTI = showLTI;
	}

	/**
	 * Get the user's preference to show Link to publisher's content option in Add section screen.
	 * 
	 * @param userId
	 *        The user id
	 * @return
	 */
	public boolean getUserLTIChoice(String userId)
	{
		MeleteUserPreference checkMup = (MeleteUserPreference) getAuthorPref().getUserChoice(userId);
		if (checkMup != null && checkMup.isShowLTIChoice() != null)
			return checkMup.isShowLTIChoice();
		else
			return false;
	}

	public String getFormName()
	{
		return formName;
	}

	/**
	 * @param formName
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * 
	 * @return
	 */
	public String getDisplayLicenseCode()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		mup = getMup(mPage.getCurrentUser().getId());

		binding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) binding.getValue(context);
		if (lPage.getLicenseCodes() == null)
		{
			lPage.setInitialValues(this.formName, mup);
		}
		displayLicenseCode = lPage.getLicenseCodes();
		displayYear = lPage.getCopyright_year();
		displayOwner = lPage.getCopyright_owner();
		displayAllowCmrcl = (lPage.getAllowCmrcl() != null && lPage.getAllowCmrcl().equals("true")) ? true : false;
		displayAllowMod = (lPage.getAllowMod() != null) ? new Integer(lPage.getAllowMod()) : 0;
		return displayLicenseCode;
	}

	/**
	 * 
	 * @param displayLicenseCode
	 *        Preferred LicenseCode
	 */
	public void setDisplayLicenseCode(String displayLicenseCode)
	{
		this.displayLicenseCode = displayLicenseCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getDisplayOwner()
	{
		return displayOwner;
	}

	/**
	 * 
	 * @param displayOwner
	 *        Preferred license Copyright owner
	 */
	public void setDisplayOwner(String displayOwner)
	{
		this.displayOwner = displayOwner;
	}

	/**
	 * 
	 * @return
	 */
	public String getDisplayYear()
	{
		return displayYear;
	}

	/**
	 * 
	 * @param displayYear
	 *        Preferred license Copyright year
	 */
	public void setDisplayYear(String displayYear)
	{
		this.displayYear = displayYear;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDisplayAllowCmrcl()
	{
		return displayAllowCmrcl;
	}

	/**
	 * 
	 * @param displayAllowCmrcl
	 *        Preferred license allow Commercial part
	 */
	public void setDisplayAllowCmrcl(boolean displayAllowCmrcl)
	{
		this.displayAllowCmrcl = displayAllowCmrcl;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getDisplayAllowMod()
	{
		return displayAllowMod;
	}

	/**
	 * 
	 * @param displayAllowMod
	 *        Preferred license allow Modifications part
	 */
	public void setDisplayAllowMod(Integer displayAllowMod)
	{
		this.displayAllowMod = displayAllowMod;
	}

	/**
	 * @return Returns the SectionService.
	 */
	public SectionService getSectionService()
	{
		return sectionService;
	}

	/**
	 * @param SectionService
	 *        The SectionService to set.
	 */
	public void setSectionService(SectionService sectionService)
	{
		this.sectionService = sectionService;
	}

}
