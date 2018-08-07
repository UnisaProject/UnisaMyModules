/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-hbm/src/java/org/etudes/component/app/melete/MeleteUserPreference.java $
 * $Id: MeleteUserPreference.java 73420 2011-03-29 18:45:36Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.etudes.api.app.melete.MeleteUserPreferenceService;

public class MeleteUserPreference implements Serializable, MeleteUserPreferenceService
{
	/** nullable persistent field */
	private Boolean allowCmrcl;

	/** nullable persistent field */
	private Integer allowMod;

	/** nullable persistent field */
	private String ccLicenseUrl;

	private String copyrightOwner;

	private String copyrightYear;

	/** nullable persistent field */
	private String editorChoice;

	/** nullable persistent field */
	private Integer licenseCode;

	private int prefId;

	/** nullable persistent field */
	private Boolean reqAttr;

	private Boolean showLTIChoice;

	/** identifier field */
	private String userId;

	private Boolean viewExpChoice;

	/**
	 * default
	 */
	public MeleteUserPreference()
	{
		this.viewExpChoice = true;
		this.showLTIChoice = false;
		this.licenseCode = 0;
	}

	/**
	 * full constructor
	 * 
	 * @param userId
	 * @param editorChoice
	 */
	public MeleteUserPreference(String userId, String editorChoice, Boolean viewExpChoice, Boolean showLTIChoice, Integer licenseCode,
			String ccLicenseUrl, Boolean reqAttr, Boolean allowCmrcl, Integer allowMod, int version, org.etudes.component.app.melete.Section section,
			String copyrightOwner, String copyrightYear)
	{
		this.userId = userId;
		this.editorChoice = editorChoice;
		this.viewExpChoice = viewExpChoice;
		this.showLTIChoice = showLTIChoice;
		this.licenseCode = licenseCode;
		this.ccLicenseUrl = ccLicenseUrl;
		this.reqAttr = reqAttr;
		this.allowCmrcl = allowCmrcl;
		this.allowMod = allowMod;
		this.copyrightOwner = copyrightOwner;
		this.copyrightYear = copyrightYear;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getAllowMod()
	{
		return this.allowMod;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCcLicenseUrl()
	{
		return this.ccLicenseUrl;
	}

	/**
	 * @return Returns the copyrightOwner.
	 */
	public String getCopyrightOwner()
	{
		return copyrightOwner;
	}

	/**
	 * @return Returns the copyrightYear.
	 */
	public String getCopyrightYear()
	{
		return copyrightYear;
	}

	/**
	 * @return Returns the editorChoice.
	 */
	public String getEditorChoice()
	{
		return editorChoice;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getLicenseCode()
	{
		return this.licenseCode;
	}

	/**
	 * @return Returns the prefId.
	 */
	public int getPrefId()
	{
		return prefId;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isAllowCmrcl()
	{
		return this.allowCmrcl;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isReqAttr()
	{
		return this.reqAttr;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isShowLTIChoice()
	{
		return showLTIChoice;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isViewExpChoice()
	{
		return viewExpChoice;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAllowCmrcl(Boolean allowCmrcl)
	{
		this.allowCmrcl = allowCmrcl;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAllowMod(Integer allowMod)
	{
		this.allowMod = allowMod;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCcLicenseUrl(String ccLicenseUrl)
	{
		this.ccLicenseUrl = ccLicenseUrl;
	}

	/**
	 * @param copyrightOwner
	 *        The copyrightOwner to set.
	 */
	public void setCopyrightOwner(String copyrightOwner)
	{
		this.copyrightOwner = copyrightOwner;
	}

	/**
	 * @param copyrightYear
	 *        The copyrightYear to set.
	 */
	public void setCopyrightYear(String copyrightYear)
	{
		this.copyrightYear = copyrightYear;
	}

	/**
	 * @param editorChoice
	 *        The editorChoice to set.
	 */
	public void setEditorChoice(String editorChoice)
	{
		this.editorChoice = editorChoice;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLicenseCode(Integer licenseCode)
	{
		this.licenseCode = licenseCode;
	}

	/**
	 * @param prefId
	 *        The prefId to set.
	 */
	public void setPrefId(int prefId)
	{
		this.prefId = prefId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setReqAttr(Boolean reqAttr)
	{
		this.reqAttr = reqAttr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setShowLTIChoice(Boolean showLTIChoice)
	{
		this.showLTIChoice = showLTIChoice;
	}

	/**
	 * @param userId
	 *        The userId to set.
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setViewExpChoice(Boolean viewExpChoice)
	{
		this.viewExpChoice = viewExpChoice;
	}

	/**
	 * 
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("prefId", getPrefId()).toString();
	}
}
