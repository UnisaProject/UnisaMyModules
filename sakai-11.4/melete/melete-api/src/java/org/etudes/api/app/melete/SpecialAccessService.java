/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/SpecialAccessService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2010, 2011 Etudes, Inc.
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
 ***************************************************************************************/
package org.etudes.api.app.melete;

import java.util.List;
import java.io.File;


public interface SpecialAccessService
{
	/**
	 * Delete special accesses specified in the list from the db
	 * 
	 * @param saList
	 *        List of special accesses
	 * @throws Exception
	 */
	public void deleteSpecialAccess(List<Integer> saList) throws Exception;

	/**
	 * Get special access list of this module
	 * 
	 * @param moduleId
	 *        module id
	 * @return List of special accesses
	 */
	public List<SpecialAccessService> getSpecialAccess(int moduleId);

	/**
	 * Insert or update new special access into existing list of special accesses
	 * 
	 * @param saList
	 *        Current list of special accesses
	 * @param mb
	 *        New special access object
	 * @param mod
	 *        Current Module
	 * @throws Exception
	 */
	public void insertSpecialAccess(List<SpecialAccessService> saList, SpecialAccessObjService mb, ModuleObjService mod) throws Exception;
}
