/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/ModuleDateBeanService.java $
 * $Id: ModuleDateBeanService.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2011 Etudes, Inc.
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

import java.util.List;

public interface ModuleDateBeanService
{

	/**
	 * @return course module
	 */
	public abstract org.etudes.api.app.melete.CourseModuleService getCmod();

	/**
	 * @return the module
	 */
	public abstract org.etudes.api.app.melete.ModuleObjService getModule();

	/**
	 * @return the module id
	 */
	public abstract int getModuleId();

	/**
	 * @return moduleshdatesservice object
	 */
	public abstract org.etudes.api.app.melete.ModuleShdatesService getModuleShdate();

	/**
	 * @return rowClasses string
	 */
	public abstract String getRowClasses();

	/**
	 * @return sectionBeans list
	 */
	public abstract List<SectionBeanService> getSectionBeans();

	/**
	 * @return truncTitle string
	 */
	public abstract String getTruncTitle();

	
	/**
	 * @return selected flag
	 */
	public abstract boolean isSelected();

	/**
	 * @param cmod
	 *        set course module object
	 */
	public abstract void setCmod(org.etudes.api.app.melete.CourseModuleService cmod);

	/**
	 * @param module
	 *        the module
	 */
	public abstract void setModule(org.etudes.api.app.melete.ModuleObjService module);

	/**
	 * @param moduleId
	 *        the module id
	 */
	public abstract void setModuleId(int moduleId);

	/**
	 * @param moduleShdate
	 *        the moduleShdate object
	 */
	public abstract void setModuleShdate(org.etudes.api.app.melete.ModuleShdatesService moduleShdate);

	/**
	 * @param rowClasses
	 *        the rowClasses string
	 */
	public abstract void setRowClasses(String rowClasses);

	/**
	 * @param sectionBeans
	 *        the sectionBeans list
	 */
	public abstract void setSectionBeans(List<SectionBeanService> sectionBeans);

	/**
	 * @param selected
	 *        the selected flag
	 */
	public abstract void setSelected(boolean selected);

	/**
	 * @param truncTitle
	 *        the truncTitle string
	 */
	public abstract void setTruncTitle(String truncTitle);

	/**
	 * @return string value of moduledatebeanservice object
	 */
	public abstract String toString();
}
