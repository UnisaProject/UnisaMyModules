/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/MeleteResource.java $
 * $Id: MeleteResource.java 78260 2012-01-24 21:26:42Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.etudes.api.app.melete.MeleteResourceService;

public class MeleteResource implements MeleteResourceService
{

	/** persistent field */
	private String resourceId;

	/** nullable persistent field */
	private int licenseCode;

	/** nullable persistent field */
	private String ccLicenseUrl;

	/** nullable persistent field */
	private boolean reqAttr;

	/** nullable persistent field */
	private boolean allowCmrcl;

	/** nullable persistent field */
	private int allowMod;

	/** nullable persistent field */
	private int version;

	private String copyrightOwner;

	private String copyrightYear;

	/** full constructor */
	public MeleteResource(String resourceId, int licenseCode, String ccLicenseUrl, boolean reqAttr, boolean allowCmrcl, int allowMod, int version,
			org.etudes.component.app.melete.Section section, String copyrightOwner, String copyrightYear)
	{
		this.resourceId = resourceId;
		this.licenseCode = licenseCode;
		this.ccLicenseUrl = ccLicenseUrl;
		this.reqAttr = reqAttr;
		this.allowCmrcl = allowCmrcl;
		this.allowMod = allowMod;
		this.version = version;
		this.copyrightOwner = copyrightOwner;
		this.copyrightYear = copyrightYear;

	}

	/** default constructor */
	public MeleteResource()
	{
	}

	/** copy constructor */
	public MeleteResource(MeleteResource s1)
	{
		// this.resourceId = s1.resourceId;
		this.licenseCode = s1.licenseCode;
		this.ccLicenseUrl = s1.ccLicenseUrl;
		this.reqAttr = s1.reqAttr;
		this.allowCmrcl = s1.allowCmrcl;
		this.allowMod = s1.allowMod;
		this.copyrightOwner = s1.copyrightOwner;
		this.copyrightYear = s1.copyrightYear;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MeleteResource other = (MeleteResource) obj;
		if (resourceId == null)
		{
			if (other.resourceId != null) return false;
		}
		else if (!resourceId.equals(other.resourceId)) return false;
		return true;
	}

	/**
	 * @return Returns the copyrightOwner.
	 */
	public String getCopyrightOwner()
	{
		return copyrightOwner;
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
	 * @return Returns the copyrightYear.
	 */
	public String getCopyrightYear()
	{
		return copyrightYear;
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
	 * @return Returns the resourceId.
	 */
	public String getResourceId()
	{
		return resourceId;
	}

	/**
	 * @param resourceId
	 *        The resourceId to set.
	 */
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getLicenseCode()
	{
		return this.licenseCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLicenseCode(int licenseCode)
	{
		this.licenseCode = licenseCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCcLicenseUrl()
	{
		return this.ccLicenseUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCcLicenseUrl(String ccLicenseUrl)
	{
		this.ccLicenseUrl = ccLicenseUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReqAttr()
	{
		return this.reqAttr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setReqAttr(boolean reqAttr)
	{
		this.reqAttr = reqAttr;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAllowCmrcl()
	{
		return this.allowCmrcl;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAllowCmrcl(boolean allowCmrcl)
	{
		this.allowCmrcl = allowCmrcl;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getAllowMod()
	{
		return this.allowMod;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAllowMod(int allowMod)
	{
		this.allowMod = allowMod;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getVersion()
	{
		return this.version;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("resourceId", getResourceId()).toString();
	}

}
