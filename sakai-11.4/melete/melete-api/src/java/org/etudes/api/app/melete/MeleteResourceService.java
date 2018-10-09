/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/MeleteResourceService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2008 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License. 
 ********************************************************************************/
package org.etudes.api.app.melete;

import java.io.Serializable;

public interface MeleteResourceService extends Serializable
{
	/**
	 * Get resource's license allow Modifications attribute
	 * 
	 * @return
	 */
	public abstract int getAllowMod();

	/**
	 * Get resource's license URL
	 * 
	 * @return
	 */
	public abstract String getCcLicenseUrl();

	/**
	 * Get the copyrightOwner.
	 * @return 
	 */
	public abstract String getCopyrightOwner();

	/**
	 * Get the copyrightYear
	 * @return 
	 */
	public abstract String getCopyrightYear();

	/**
	 * Get resource's license code
	 * 
	 * @return
	 */
	public abstract int getLicenseCode();

	/**
	 * Get resourceId
	 * @return 
	 */
	public abstract String getResourceId();

	/**
	 * 
	 * @return
	 */
	public abstract int getVersion();

	/**
	 * Get resource's license allow Commercial Use attribute
	 * 
	 * @return
	 */
	public abstract boolean isAllowCmrcl();

	/**
	 * Get resource's license require Attribution attribute
	 * 
	 * @return
	 */
	public abstract boolean isReqAttr();

	/**
	 * Set resource's license allow Commercial Use attribute
	 * 
	 * @param allowCmrcl
	 *        Allow Commercial Use attribute
	 */
	public abstract void setAllowCmrcl(boolean allowCmrcl);

	/**
	 * Get resource's license allow Modifications attribute
	 * 
	 * @param allowMod
	 *        Allow Modifications attribute
	 */
	public abstract void setAllowMod(int allowMod);

	/**
	 * Set resource's license URL
	 * 
	 * @param ccLicenseUrl
	 */
	public abstract void setCcLicenseUrl(String ccLicenseUrl);

	/**
	 * @param copyrightOwner
	 *        The copyrightOwner to set.
	 */
	public abstract void setCopyrightOwner(String copyrightOwner);

	/**
	 * @param copyrightYear
	 *        The copyrightYear to set.
	 */
	public abstract void setCopyrightYear(String copyrightYear);

	/**
	 * Set resource's license code
	 * 
	 * @param licenseCode
	 *        License code
	 */
	public abstract void setLicenseCode(int licenseCode);

	/**
	 * Set resource's license require Attribution attribute
	 * 
	 * @param reqAttr
	 *        require Attribution attribute
	 */
	public abstract void setReqAttr(boolean reqAttr);

	/**
	 * @param resourceId
	 *        The resourceId to set.
	 */
	public abstract void setResourceId(String resourceId);

	/**
	 * 
	 * @param version
	 */
	public abstract void setVersion(int version);

	/**
	 * 
	 * @return
	 */
	public abstract String toString();
}
