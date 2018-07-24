/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/CcLicenseService.java $
 * $Id: CcLicenseService.java 73420 2011-03-29 18:45:36Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008 Etudes, Inc.
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
package org.etudes.api.app.melete;

public interface CcLicenseService
{
	/**
	 * 
	 * @param other
	 * @return
	 */
	public abstract boolean equals(Object other);

	/**
	 * Get Allow Modifications part of the license.
	 * 
	 * @return 0 if No Modifications allowed,
	 *         1 Yes, if others share the work alike 
	 *         2 Yes modifications are allowed.
	 */
	public abstract int getAllowMod();

	/**
	 * Get License name 
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Get the License Url
	 * 
	 * @return
	 */
	public abstract String getUrl();

	/**
	 * 
	 * @return
	 */
	public abstract int hashCode();

	/**
	 * Gets Allow Commercial Use part of the license.
	 * 
	 * @return true if allowed
	 */
	public abstract boolean isAllowCmrcl();

	/**
	 * Gets Require Attribution part of the license. True for all Creative commons License variations.
	 * 
	 * @return true if required
	 */
	public abstract boolean isReqAttr();

	/**
	 * Set Allow Commercial Use part of the license.
	 * 
	 * @param allowCmrcl
	 */
	public abstract void setAllowCmrcl(boolean allowCmrcl);

	/**
	 * Set Allow Modifications part of the license.
	 * 
	 * @param allowMod
	 */
	public abstract void setAllowMod(int allowMod);

	/**
	 * Set license name
	 * 
	 * @param name
	 */
	public abstract void setName(String name);

	/**
	 * Set Require Attribution part of the license.
	 * 
	 * @param reqAttr
	 */
	public abstract void setReqAttr(boolean reqAttr);

	/**
	 * Set License Url
	 * 
	 * @param url
	 */
	public abstract void setUrl(String url);

	/**
	 * 
	 * @return
	 */
	public abstract String toString();
}
