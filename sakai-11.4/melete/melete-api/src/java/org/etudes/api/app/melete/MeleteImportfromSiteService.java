/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/MeleteImportfromSiteService.java $
 * $Id: MeleteImportfromSiteService.java 73448 2011-03-30 18:24:49Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009,2010, 2011 Etudes, Inc.
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

public interface MeleteImportfromSiteService
{
	/**
	 * Imports all active and archived modules and un-referred meleteDocs items from old site to new site. Tries to bring one copy of module even on
	 * running import from site multiple times.
	 * 
	 * @param fromContext
	 *        The old Site Id
	 * @param toContext
	 *        The new Site Id
	 */
	public void copyModules(String fromContext, String toContext);
}
