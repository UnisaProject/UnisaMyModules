/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/MeleteLicenseService.java $
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
 *
 **********************************************************************************/
package org.etudes.api.app.melete;

public interface MeleteLicenseService
{
	/**
	 * Get the License code. 0 means I have not determined copyright yet, 1 means Copyright of Author 
	 * 2 means Public Domain, 3 means Creative Commons License and 4 means Fair Use Exception
	 * 
	 * @return
	 */
	public abstract Integer getCode();

	/**
	 * Gets the license short description.
	 * 
	 * @return
	 */
	public abstract String getDescription();

	/**
	 * Set License code
	 * 
	 * @param code
	 */
	public abstract void setCode(Integer code);

	/**
	 * Set license description
	 * 
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
	 * 
	 * @return
	 */
	public abstract String toString();
}
