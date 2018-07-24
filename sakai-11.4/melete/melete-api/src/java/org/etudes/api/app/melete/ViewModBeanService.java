/**********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/ViewModBeanService.java $
 * $Id: ViewModBeanService.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $
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

package org.etudes.api.app.melete;

import java.util.Date;
import java.util.List;

public interface ViewModBeanService
{

	/**
	 * @return get blockedBy
	 */
	public abstract String getBlockedBy();

	public String getBlockedDetails();

	/**
	 * @return description
	 */
	public String getDescription();

	/**
	 * @return end date
	 */
	public abstract Date getEndDate();
	
	/**
	 * @return allow until date
	 */
	public abstract Date getAllowUntilDate();

	/**
	 * @return get module id
	 */
	public abstract int getModuleId();

	/**
	 * @return get number of sections viewed
	 */
	public abstract Integer getNoOfSectionsRead();

	/**
	 * @return get readDate
	 */
	public abstract Date getReadDate();

	/**
	 * @return get rowClasses
	 */
	public abstract String getRowClasses();

	/**
	 * @return get seq no
	 */
	public abstract int getSeqNo();

	/**
	 * @return seqXml string value
	 */
	public abstract String getSeqXml();

	/**
	 * @return get start date
	 */
	public abstract Date getStartDate();

	/**
	 * @return title
	 */
	public abstract String getTitle();

	/**
	 * @return get list of vsBeans
	 */
	public abstract List<ViewSecBeanService> getVsBeans();

	/**
	 * @return whatsNext string value
	 */
	public abstract String getWhatsNext();

	/**
	 * @return the date flag
	 */
	public abstract boolean isDateFlag();

	/**
	 * @return if module is completely read by user
	 */
	public boolean isReadComplete();

	/**
	 * @return selected boolean flag
	 */
	public abstract boolean isSelected();

	/**
	 * @return the visible flag
	 */
	public abstract boolean isVisibleFlag();

	/**
	 * @param blockedBy
	 *        set blockedBy string
	 */
	public abstract void setBlockedBy(String blockedBy);

	public void setBlockedDetails(String blockedDetails);

	/**
	 * @param dateFlag
	 *        the date flag
	 */
	public abstract void setDateFlag(boolean dateFlag);

	/**
	 * @param description
	 *        the module description
	 */
	public abstract void setDescription(String description);

	/**
	 * @param endDate
	 *        the end date
	 */
	public abstract void setEndDate(Date endDate);
	
	/**
	 * @param allowUntilDate
	 *        the allow until date
	 */
	public abstract void setAllowUntilDate(Date allowUntilDate);

	/**
	 * @param moduleId
	 *        the module id
	 */
	public abstract void setModuleId(int moduleId);

	/**
	 * @param noOfSectionsRead
	 *        set number of sections viewed
	 */
	public abstract void setNoOfSectionsRead(Integer noOfSectionsRead);

	/**
	 * @param readComplete
	 *        set if user has read all sections of a module
	 */
	public abstract void setReadComplete(boolean readComplete);

	/**
	 * @param readDate
	 *        set read date
	 */
	public abstract void setReadDate(Date readDate);

	/**
	 * @param rowClasses
	 *        set rowClasses string
	 */
	public abstract void setRowClasses(String rowClasses);

	/**
	 * @param selected
	 *        set selected flag
	 */
	public abstract void setSelected(boolean selected);

	/**
	 * @param seqNo
	 *        set seq no
	 */
	public abstract void setSeqNo(int seqNo);

	/**
	 * @param seqXml
	 *        set seq xml
	 */
	public abstract void setSeqXml(String seqXml);

	/**
	 * @param startDate
	 *        set start date
	 */
	public abstract void setStartDate(Date startDate);

	/**
	 * @param title
	 *        set title
	 */
	public abstract void setTitle(String title);

	/**
	 * @param visibleFlag
	 *        set visible flag
	 */
	public abstract void setVisibleFlag(boolean visibleFlag);

	/**
	 * @param vsBeans
	 *        set list of vsBeans
	 */
	public abstract void setVsBeans(List<ViewSecBeanService> vsBeans);

	/**
	 * @param whatsNext
	 *        set whats next
	 */
	public abstract void setWhatsNext(String whatsNext);

	/**
	 * @return string value of object
	 */
	public abstract String toString();

}
