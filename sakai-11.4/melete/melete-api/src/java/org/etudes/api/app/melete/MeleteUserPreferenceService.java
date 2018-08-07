/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/MeleteUserPreferenceService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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
 ***************************************************************************************/
package org.etudes.api.app.melete;

public interface MeleteUserPreferenceService
{
	/**
	 * Get Allow Modifications part of the license.
	 * 
	 * @return 0 if No Modifications allowed, 
	 * 		   1 Yes, if others share the work alike 
	 *         2 Yes modifications are allowed.
	 */
	public abstract Integer getAllowMod();

	/**
	 * Gets preferred license URL
	 * 
	 * @return
	 */
	public abstract String getCcLicenseUrl();

	/**
	 * Gets the copyright owner of the license
	 * 
	 * @return
	 */
	public abstract String getCopyrightOwner();

	/**
	 * Gets the copyright year of license
	 * 
	 * @return
	 */

	public abstract String getCopyrightYear();

	/**
	 * Gets user preference for the editor.
	 * 
	 * @return "Sferyx Editor" if choice is sferyx 
	 * 			FCK Editor if choice is FCK Editor 
	 * 			null if no preference is set so far
	 */
	public abstract String getEditorChoice();

	/**
	 * Get user's preferred license code.
	 * 		0 means I have not determined copyright yet, 1 means Copyright of Author 
	 * 		2 means Public Domain, 3 means Creative Commons License and 4 means Fair Use Exception
	 * 
	 * @return
	 */
	public abstract Integer getLicenseCode();

	/**
	 * Get preference Id
	 * 
	 * @return
	 */
	public abstract int getPrefId();

	/**
	 * Get the User Id
	 * 
	 * @return
	 */
	public abstract String getUserId();

	/**
	 * Gets Allow Commercial Use part of the license.
	 * 
	 * @return true if allowed
	 */
	public abstract Boolean isAllowCmrcl();

	/**
	 * Gets Require Attribution part of the license.
	 * 
	 * @return true if required
	 */
	public abstract Boolean isReqAttr();

	/**
	 * Check user's preference for LTI. If set, 4th option of linking with publisher's content shows in section author page.
	 * 
	 * @return user's LTI preference
	 */
	public abstract Boolean isShowLTIChoice();

	/**
	 * Checks user's collapsed/expanded preference
	 * 
	 * @return true for expanded view
	 */
	public abstract Boolean isViewExpChoice();

	/**
	 * Set Allow Commercial Use part of the license.
	 * 
	 * @param allowCmrcl
	 *        Allow Commercial use choice
	 */
	public abstract void setAllowCmrcl(Boolean allowCmrcl);

	/**
	 * Set Allow Modifications part of the license.
	 * 
	 * @param allowMod
	 *        Allow Modification choice
	 */
	public abstract void setAllowMod(Integer allowMod);

	/**
	 * Set preferred license URL
	 * 
	 * @param ccLicenseUrl
	 */
	public abstract void setCcLicenseUrl(String ccLicenseUrl);

	/**
	 * Set copyright owner of license
	 * 
	 * @param copyrightOwner
	 *        The Copyright owner
	 */

	public abstract void setCopyrightOwner(String copyrightOwner);

	/**
	 * Sets the copyright year of license
	 * 
	 * @param copyrightYear
	 *        The copyright year. can be in format 2007-2009 or 2007,2008,2009 etc
	 */
	public abstract void setCopyrightYear(String copyrightYear);

	/**
	 * Gets user preference for the editor.
	 * 
	 * @param editorChoice
	 *        The editor choice
	 */
	public abstract void setEditorChoice(String editorChoice);

	/**
	 * Set user's preferred license code
	 * 
	 * @param licenseCode
	 *        the preferred License code
	 */
	public abstract void setLicenseCode(Integer licenseCode);

	/**
	 * Set Preference Id
	 * 
	 * @param prefId
	 *        The preference Id
	 */
	public abstract void setPrefId(int prefId);

	/**
	 * Sets Require Attribution part of the license. True for all Creative commons License variations. 
	 * false for all other licenses.
	 * 
	 * @param reqAttr
	 *        require Attribution choice.
	 * 
	 */
	public abstract void setReqAttr(Boolean reqAttr);

	/**
	 * Set user's preference for LTI
	 * 
	 * @param showLTIChoice
	 */
	public abstract void setShowLTIChoice(Boolean showLTIChoice);

	/**
	 * Set user Id
	 * 
	 * @param userId
	 *        The User Id
	 */
	public abstract void setUserId(String userId);

	/**
	 * Set user's collapsed/expanded preference
	 * 
	 * @param viewExpChoice
	 */
	public abstract void setViewExpChoice(Boolean viewExpChoice);
}
