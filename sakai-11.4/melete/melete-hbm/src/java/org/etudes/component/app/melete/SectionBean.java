/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/SectionBean.java $
 * $Id: SectionBean.java 73855 2011-04-19 20:41:15Z rashmi@etudes.org $  
 ***********************************************************************************
 *
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.etudes.api.app.melete.SectionBeanService;
import org.etudes.api.app.melete.SectionObjService;

public class SectionBean implements SectionBeanService, Serializable
{

	protected boolean selected;
	protected String truncTitle;
	protected String displaySequence;

	/** nullable persistent field */
	protected Section section;

	/** default constructor */
	public SectionBean()
	{
	}

	/** full constructor */
	public SectionBean(Section section)
	{
		this.section = (Section) section;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#getDisplaySequence()
	 */
	public String getDisplaySequence()
	{
		return displaySequence;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#getSection()
	 */
	public Section getSection()
	{
		return this.section;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#getTruncTitle()
	 */
	public String getTruncTitle()
	{
		return truncTitle;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#isSelected()
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#setDisplaySequence(java.lang.String)
	 */
	public void setDisplaySequence(String displaySequence)
	{
		this.displaySequence = displaySequence;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#setSection(org.etudes.component.app.melete.Section)
	 */
	public void setSection(SectionObjService section)
	{
		this.section = (Section) section;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#setSelected(boolean)
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/* (non-Javadoc)
	 * @see org.etudes.component.app.melete.SectionBeanService#setTruncTitle(java.lang.String)
	 */
	public void setTruncTitle(String truncTitle)
	{
		this.truncTitle = truncTitle;
	}

	/**
	 * {@inheritDoc}
	 */

	public String toString()
	{
		return new ToStringBuilder(this).toString();
	}

}
