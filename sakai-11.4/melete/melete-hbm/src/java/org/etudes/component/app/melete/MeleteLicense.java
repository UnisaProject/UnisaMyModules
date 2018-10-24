/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/MeleteLicense.java $
 * $Id: MeleteLicense.java 73546 2011-04-04 17:35:08Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008 Etudes, Inc.
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.etudes.api.app.melete.MeleteLicenseService;

public class MeleteLicense implements Serializable, MeleteLicenseService
{

	/** identifier field */
	private Integer code;

	/** nullable persistent field */
	private String description;

	/** default constructor */
	public MeleteLicense()
	{
	}

	/** minimal constructor */
	public MeleteLicense(Integer code)
	{
		this.code = code;
	}

	/** full constructor */
	public MeleteLicense(Integer code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getCode()
	{
		return this.code;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCode(Integer code)
	{
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("code", getCode()).toString();
	}

}
