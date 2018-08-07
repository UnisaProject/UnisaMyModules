/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-hbm/src/java/org/etudes/component/app/melete/ViewModBean.java $
 * $Id: ViewModBean.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2009, 2010, 2011 Etudes, Inc.
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
import java.util.List;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.etudes.api.app.melete.*;

public class ViewModBean implements Serializable, ViewModBeanService
{
	private String blockedBy;
	
	protected String blockedDetails;

	protected boolean closedBeforeFlag;

	protected boolean dateFlag;

	protected String description;

	protected Date endDate;
	
	protected Date allowUntilDate;

	/** identifier field */
	protected int moduleId;

	private String nextStepsNumber;

	protected Integer noOfSectionsRead = 0;

	protected boolean openLaterFlag;

	protected boolean readComplete;

	protected Date readDate;

	protected String rowClasses;

	protected boolean selected;
	
	protected int seqNo;

	protected String seqXml;

	/** nullable persistent field */
	protected Date startDate;

	protected String title;

	protected boolean visibleFlag;

	protected List<ViewSecBeanService> vsBeans;

	private int vsBeansSize;

	protected String whatsNext;

	/** default constructor */
	public ViewModBean()
	{
	}

	/**
	 * @param moduleId
	 * @param selected
	 * @param dateFlag
	 * @param description
	 * @param visibleFlag
	 * @param title
	 * @param rowClasses
	 * @param whatsNext
	 * @param startDate
	 * @param endDate
	 * @param allowUntilDate
	 * @param seqNo
	 * @param seqXml
	 * @param vsBeans
	 */
	public ViewModBean(int moduleId, boolean selected, boolean dateFlag, String description, boolean visibleFlag, String title, String rowClasses,
			String whatsNext, Date startDate, Date endDate, Date allowUntilDate, int seqNo, String seqXml, List<ViewSecBeanService> vsBeans)
	{
		this.moduleId = moduleId;
		this.selected = selected;
		this.dateFlag = dateFlag;
		this.description = description;
		this.visibleFlag = visibleFlag;
		this.title = title;
		this.rowClasses = rowClasses;
		this.whatsNext = whatsNext;
		this.startDate = startDate;
		this.endDate = endDate;
		this.allowUntilDate = allowUntilDate;
		this.seqNo = seqNo;
		this.seqXml = seqXml;
		this.vsBeans = vsBeans;
		if (vsBeans != null)
			this.vsBeansSize = vsBeans.size();
		else
			this.vsBeansSize = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBlockedBy()
	{
		return blockedBy;
	}

	public String getBlockedDetails()
	{
		return blockedDetails;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getEndDate()
	{
		return this.endDate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Date getAllowUntilDate()
	{
		return this.allowUntilDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getModuleId()
	{
		return this.moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getNextStepsNumber()
	{
		return nextStepsNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getNoOfSectionsRead()
	{
		return noOfSectionsRead;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getReadDate()
	{
		return this.readDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRowClasses()
	{
		return this.rowClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSeqNo()
	{
		return this.seqNo;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSeqXml()
	{
		return this.seqXml;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getStartDate()
	{
		return this.startDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ViewSecBeanService> getVsBeans()
	{
		if (this.vsBeans != null)
			this.vsBeansSize = this.vsBeans.size();
		else
			this.vsBeansSize = 0;
		return this.vsBeans;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getWhatsNext()
	{
		return this.whatsNext;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isClosedBeforeFlag()
	{
		if (visibleFlag == false && blockedBy == null)
		{
			Date currentDate = new java.util.Date();
			if ((getAllowUntilDate() != null) && (getAllowUntilDate().before(currentDate)))
			{
				return true;
			}
			else
			{
				if ((getEndDate() != null) && (getEndDate().before(currentDate)))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDateFlag()
	{
		return dateFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOpenLaterFlag()
	{
		if (visibleFlag == false && blockedBy == null)
		{
			Date currentDate = new java.util.Date();
			if ((getStartDate() != null) && (getStartDate().after(currentDate)))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReadComplete()
	{
		return readComplete;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isVisibleFlag()
	{
		return visibleFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBlockedBy(String blockedBy)
	{
		this.blockedBy = blockedBy;
	}

	public void setBlockedDetails(String blockedDetails)
	{
		this.blockedDetails = blockedDetails;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDateFlag(boolean dateFlag)
	{
		this.dateFlag = dateFlag;
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
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setAllowUntilDate(Date allowUntilDate)
	{
		this.allowUntilDate = allowUntilDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModuleId(int moduleId)
	{
		this.moduleId = moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNextStepsNumber(String nextStepsNumber)
	{
		this.nextStepsNumber = nextStepsNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNoOfSectionsRead(Integer noOfSectionsRead)
	{
		this.noOfSectionsRead = noOfSectionsRead;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setReadComplete(boolean readComplete)
	{
		this.readComplete = readComplete;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setReadDate(Date readDate)
	{
		this.readDate = readDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRowClasses(String rowClasses)
	{
		this.rowClasses = rowClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSeqNo(int seqNo)
	{
		this.seqNo = seqNo;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSeqXml(String seqXml)
	{
		this.seqXml = seqXml;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVisibleFlag(boolean visibleFlag)
	{
		this.visibleFlag = visibleFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVsBeans(List<ViewSecBeanService> vsBeans)
	{
		this.vsBeans = vsBeans;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWhatsNext(String whatsNext)
	{
		this.whatsNext = whatsNext;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("moduleId", getModuleId()).toString();
	}

}
