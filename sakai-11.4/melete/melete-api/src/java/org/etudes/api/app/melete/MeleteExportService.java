/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/MeleteExportService.java $
 * $Id: MeleteExportService.java 73855 2011-04-19 20:41:15Z rashmi@etudes.org $  
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
package org.etudes.api.app.melete;

import java.io.File;
import java.util.List;
import org.dom4j.Element;
import org.etudes.api.app.melete.ModuleObjService;
/**
 * MeleteImportExportService provides the methods for import export
 */
public interface MeleteExportService
{

	/**
	 * creates the default namespace element
	 * 
	 * @param elename
	 *        element name
	 * @param qname
	 *        qualified name
	 * @return the default namespace element
	 */
	public Element createDefaultNSElement(String elename, String qname);

	/**
	 * creates file from input path to output path
	 * 
	 * @param inputpath
	 *        input path for file
	 * @param outputpath
	 *        output path for file
	 * @throws Exception
	 */
	public void createFile(String inputpath, String outputpath) throws Exception;

	/**
	 * creates the LOM metadata element
	 * 
	 * @param elename
	 *        element name
	 * @param qname
	 *        qualified name
	 * @return the metadata element
	 */
	public Element createLOMElement(String elename, String qname);

	/**
	 * creates document root element "manifest" and adds the namespaces
	 * 
	 * @return the manifest element
	 * @throws Exception
	 */
	public Element createManifest() throws Exception;

	/**
	 * create manifest metadata element with schema and schemaversion elements
	 * 
	 * @return metadata element
	 */
	public Element createManifestMetadata();

	/**
	 * creates metadata description element
	 * 
	 * @param description
	 *        description
	 * @return the metadata description element
	 */
	public Element createMetadataDescription(String description);

	/**
	 * creates metadata title element
	 * 
	 * @param title
	 *        title
	 * @return the title element
	 */
	public Element createMetadataTitle(String title);

	/**
	 * deletes the file and its children
	 * 
	 * @param delfile
	 *        file to be deleted
	 */
	public void deleteFiles(File delfile);

	/**
	 * adds organization and resource items to manifest
	 * 
	 * @param modList
	 *       list of modules 
	 * @param packagedir
	 *        package directory
	 * @return the list of manifest elements
	 * @throws Exception
	 */
	List<Element> generateOrganizationResourceItems(List<? extends ModuleObjService> modList, boolean allFlag, File packagedir,
			String maintitle, String courseId) throws Exception;

	/**
	 * creates document root element "manifest" from the default manifest file and adds the namespaces
	 * 
	 * @param xmlFile
	 *        Default manifest file
	 * @return the manifest element
	 * @throws Exception
	 */
	public Element getManifest(File xmlFile) throws Exception;

	public void initValues();
}
