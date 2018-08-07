/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-impl/src/java/org/etudes/component/app/melete/MeleteUtil.java $
 * $Id: MeleteUtil.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009, 2011 Etudes, Inc.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.cover.EntityManager;

public class MeleteUtil
{
	static final String changeforXmlParser = "[ ] %";
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(MeleteUtil.class);

	public MeleteUtil()
	{

	}

	/**
	 * Check if file exists
	 * 
	 * @param filePath
	 *        File path
	 * @return true if file exists, false otherwise and logs a debug message
	 */
	public boolean checkFileExists(String filePath)
	{
		boolean success = false;
		try
		{
			File file = new File(filePath);

			// Create file if it does not exist
			success = file.exists();
			if (success)
			{

			}
			else
			{
				// File did not exist and was created
				logger.debug("File " + filePath + " does not exist");
			}
		}
		catch (Exception e)
		{
			logger.error("error in checkFileExists" + e.toString());
			e.printStackTrace();
		}

		return success;
	}

	/**
	 * Creates file from input path to output path
	 * 
	 * @param inputpath
	 *        - input path for file
	 * @param outputpath
	 *        - output path for file
	 * @throws Exception
	 */
	public void createFileFromContent(byte[] content, String outputurl) throws Exception
	{
		FileOutputStream fout = new FileOutputStream(new File(outputurl));
		try
		{
			fout.write(content);
			fout.flush();
		}
		catch (IOException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (fout != null) fout.close();
		}
	}

	/**
	 * Delete a file or directory and all its files
	 * 
	 * @param delfile
	 *        File object
	 */
	public void deleteFiles(File delfile)
	{

		if (delfile.isDirectory())
		{
			File files[] = delfile.listFiles();
			int i = files.length;
			while (i > 0)
				deleteFiles(files[--i]);

			delfile.delete();
		}
		else
			delfile.delete();

	}

	/**
	 * Replace spaces in filename with underscores
	 * 
	 * @param filename
	 *        Filename to replace
	 * @return Filename with spaces replaced by underscores
	 */
	public String escapeFileforExportPackage(String filename)
	{
		String[] change = changeforXmlParser.split("\\s");
		for (int x = 0; x < change.length; x++)
		{
			filename = filename.replace(change[x], "_");
		}
		return filename;
	}
	
	/**
	 * Appends quotes to quotes in string
	 * 
	 * @param value
	 *        String to replace
	 * @return String's quotes appended with quotes
	 */
	public String escapeQuoted(String value)
	{
		if (value == null) return "";
		try
		{
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < value.length(); i++)
			{
				char c = value.charAt(i);

				// a single quote must be escaped with a leading backslash
				if (c == '\'')
				{
					buf.append("''");
				}
				else
				{
					buf.append(c);
				}
			}

			String rv = buf.toString();
			return rv;
		}
		catch (Exception e)
		{
			logger.warn("escapeQuoted: ", e);
			return value;
		}

	}


	/**
	 * Finds the embed item patters in the string and return three values the string with the embed pattern, the start index of the referred path the end index of the referred path
	 * 
	 * @param checkforimgs
	 *        String to check for embed pattern
	 * @return ArrayList with 3 values
	 */
	public ArrayList<?> findEmbedItemPattern(String checkforimgs)
	{
		ArrayList<Object> returnData = new ArrayList<Object>();
		// a and link uses href, applet uses archive, object uses data
		Pattern p1 = Pattern
				.compile("<[iI][mM][gG]\\s|<[aA]\\s|<[eE][mM][bB][eE][dD]\\s|<[sS][cC][rR][iI][pP][tT]\\s|<[lL][iI][nN][kK]\\s|<[aA][pP][pP][lL][eE][tT]\\s|<[oO][bB][jJ][eE][cC][tT]\\s");
		Pattern pi = Pattern.compile(">|\\s[sS][rR][cC]\\s*=");
		Pattern pa = Pattern.compile(">|\\s[hH][rR][eE][fF]\\s*=");
		Pattern pa1 = Pattern.compile(">|\\s[aA][rR][cC][hH][iI][vV][eE]\\s*=");
		Pattern pd = Pattern.compile(">|\\s[dD][aA][tT][aA]\\s*=");
		Pattern ps = Pattern.compile("\\S");
		Pattern pe = Pattern.compile("\\s|>");

		int startSrc = 0;
		int endSrc = 0;
		String foundPattern = null;
		while (checkforimgs != null)
		{
			foundPattern = null;
			// look for <img or <a
			Matcher m = p1.matcher(checkforimgs);
			if (!m.find()) // found anything?
				break;
			checkforimgs = checkforimgs.substring(m.start());
			// look for src= or href=
			if (checkforimgs.startsWith("<i") || checkforimgs.startsWith("<I") || checkforimgs.startsWith("<e") || checkforimgs.startsWith("<E")
					|| checkforimgs.startsWith("<s") || checkforimgs.startsWith("<S"))
				m = pi.matcher(checkforimgs);
			else if (checkforimgs.startsWith("<applet") || checkforimgs.startsWith("<Applet") || checkforimgs.startsWith("<APPLET"))
				m = pa1.matcher(checkforimgs);
			else if (checkforimgs.startsWith("<o") || checkforimgs.startsWith("<O"))
				m = pd.matcher(checkforimgs);
			else
				m = pa.matcher(checkforimgs);

			if (m.pattern().pattern().equals(pa.pattern()))
			{
				if (checkforimgs.startsWith("<a") || checkforimgs.startsWith("<A")) foundPattern = "link";
			}
			// end = start+1 means that we found a >
			// i.e. the attribute we're looking for isn't there
			if (!m.find() || (m.end() == m.start() + 1))
			{
				// prevent infinite loop by consuming the <
				checkforimgs = checkforimgs.substring(1);
				continue;
			}

			checkforimgs = checkforimgs.substring(m.end());

			// look for start of arg, a non-whitespace
			m = ps.matcher(checkforimgs);
			if (!m.find()) // found anything?
				break;

			checkforimgs = checkforimgs.substring(m.start());

			startSrc = 0;
			endSrc = 0;

			// handle either quoted or nonquoted arg
			if (checkforimgs.startsWith("\"") || checkforimgs.startsWith("\'"))
			{
				String quotestr = checkforimgs.substring(0, 1);
				startSrc = 1;
				endSrc = checkforimgs.indexOf(quotestr, startSrc);
				break;
			}
			else
			{
				startSrc = 0;
				// ends with whitespace or >
				m = pe.matcher(checkforimgs);
				if (!m.find()) // found anything?
					continue;
				endSrc = m.start();
			}
		} // while end

		if (foundPattern != null && foundPattern.equals("link"))
		{
			String anchorStr = checkforimgs.substring(startSrc, endSrc);
			anchorStr = anchorStr.trim();
			if (anchorStr != null && (anchorStr.startsWith("#") || anchorStr.startsWith("mailto:")))
			{
				checkforimgs = checkforimgs.substring(endSrc);
				if (checkforimgs != null)
				{
					ArrayList<?> r = findEmbedItemPattern(checkforimgs);
					checkforimgs = (String) r.get(0);
					if (r.size() > 1 && ((Integer) r.get(2)).intValue() > 0)
					{
						startSrc = ((Integer) r.get(1)).intValue();
						endSrc = ((Integer) r.get(2)).intValue();
						foundPattern = (String) r.get(3);
					}
					else
					{
						startSrc = 0;
						endSrc = 0;
					}
				}
			}
		}

		returnData.add(checkforimgs);
		if (endSrc != 0)
		{
			returnData.add(new Integer(startSrc));
			returnData.add(new Integer(endSrc));
			returnData.add(foundPattern);
		}

		return returnData;
	}

	/**
	 * Checks for form tag in string - Not in use
	 * 
	 * @param checkforimgs
	 *        string to check
	 * @return String with form portion
	 */
	public String findFormPattern(String checkforimgs)
	{
	
		Pattern pi = Pattern.compile("<\\s*<[fF][oO][rR][mM]");

		// look for <table tr td form
		Matcher m = pi.matcher(checkforimgs);
		if (!m.find())
		{
			int formIdx = -1;
			int endFormIdx = -1;
			if ((formIdx = checkforimgs.indexOf("<form")) != -1 || (formIdx = checkforimgs.indexOf("<FORM")) != -1)
			{
				logger.debug("formIdx and m.end() " + formIdx);

				// replace and add table tag
				String afterForm = checkforimgs.substring(formIdx + 6);
				afterForm = afterForm.substring(afterForm.indexOf(">") + 1);
				checkforimgs = checkforimgs.substring(0, formIdx) + afterForm;
				// now look for end of form

				if ((endFormIdx = checkforimgs.indexOf("</form>")) != -1 || (endFormIdx = checkforimgs.indexOf("</FORM>")) != -1)
					checkforimgs = checkforimgs.substring(0, endFormIdx) + checkforimgs.substring(endFormIdx + 8);
			}
		}
		return checkforimgs;

	}

	/**
	 * Returns access path for resource references
	 * 
	 * @param ref_id
	 *        Resource reference string
	 * @return Path including /access
	 */
	public String findParentReference(String ref_id)
	{
		String parentStr = ref_id.substring(0, ref_id.lastIndexOf("/") + 1);
		if (parentStr != null)
		{
			if (parentStr.startsWith("/private/meleteDocs"))
				parentStr = "/access/meleteDocs/content" + parentStr;
			else if (parentStr.startsWith("/group"))
				parentStr = "/access/content" + parentStr;
			else
				parentStr = null;
		}
		return parentStr;
	}

	/**
	 * Looks for resources in ContentHosting and replaces old course id with a new course id
	 * 
	 * @param Data
	 *        String reference to resource
	 * @param oldCourseId
	 *        Old course id
	 * @param toSiteId
	 *        New course id
	 * @param newId
	 *        boolean value that indicates if we want to replace
	 * @return ArrayList with reference to replaced paths
	 */
	public ArrayList<String> findResourceSource(String Data, String oldCourseId, String toSiteId, boolean newId)
	{
		try
		{
			ArrayList<String> rData = new ArrayList<String>();
			String findEntity = Data.substring(Data.indexOf("/access") + 7);
			Reference ref = EntityManager.newReference(findEntity);
			String ref_id = ref.getId();
			String checkReferenceId = null;
			if (ref.getType().equals("sakai:meleteDocs"))
			{
				ref_id = ref_id.substring(ref_id.indexOf("/content") + 8);
				if (newId) checkReferenceId = ref_id.replace(oldCourseId, toSiteId);
			}
			else if (ref.getType().equals("sakai:content") && ref.getId().startsWith("/group"))
			{
				// for site resources item
				if (newId)
				{
					checkReferenceId = ref_id.replace(oldCourseId, toSiteId + "/uploads");
					checkReferenceId = checkReferenceId.replace("/group/", "/private/meleteDocs/");
				}
			}
			else
				return null;
			rData.add(ref_id);
			if (newId) rData.add(checkReferenceId);
			return rData;
		}
		catch (Exception e)
		{
			logger.debug("melete util : check reference" + e.toString());
			return null;
		}
	}

	/**
	 * Read from file into a byte array
	 * 
	 * @param contentfile
	 *        File object
	 * @return byte array of file contents
	 * @throws Exception
	 */
	public byte[] readFromFile(File contentfile) throws Exception
	{

		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(contentfile);

			byte buf[] = new byte[(int) contentfile.length()];
			fis.read(buf);
			return buf;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			if (fis != null) fis.close();
		}
	}

	/**
	 * In a string replace one substring with another
	 * 
	 * @param s
	 *        Main string
	 * @param one
	 *        String to replace
	 * @param another
	 *        String to replace with
	 * @return Changed string
	 */
	public String replace(String s, String one, String another)
	{
		if (s.equals("")) return "";
		if ((one == null) || (one.length() == 0))
		{
			return s;
		}
		String res = "";
		int i = s.indexOf(one, 0);
		int lastpos = 0;
		while (i != -1)
		{
			res += s.substring(lastpos, i) + another;
			lastpos = i + one.length();
			i = s.indexOf(one, lastpos);
		}
		res += s.substring(lastpos); // the rest
		return res;
	}

	/**
	 * In a string replace one substring with another, uses replace method
	 * 
	 * @param s
	 *        Main string
	 * @param one
	 *        String to replace
	 * @param another
	 *        String to replace with
	 * @return Replaced string
	 */
	public String replaceaPath(String s, String one, String another)
	{
		if (s.equals("")) return "";
		if ((one == null) || (one.length() == 0)) return s;
		String checkOne = "=\"" + one + "\"";
		another = "=\"" + another + "\"";

		if (s.indexOf(checkOne) != -1)
		{
			s = replace(s, checkOne, another);
			return s;
		}
		checkOne = "='" + one + "'";
		if (s.indexOf(checkOne) != -1)
		{
			s = replace(s, checkOne, another);
			return s;
		}
		checkOne = "=" + one;
		if (s.indexOf(checkOne) != -1)
		{
			s = replace(s, checkOne, another);
			return s;
		}

		return s;
	}

	/**
	 * In a string replace one substring with another, does equals check
	 * 
	 * @param s
	 *        Main string
	 * @param one
	 *        String to replace
	 * @param another
	 *        String to replace with
	 * @return Replaced string
	 */
	public String replacePath(String s, String one, String another)
	{
		try
		{
			if (s.equals("")) return "";
			if ((one == null) || (one.length() == 0)) return s;

			another = "=\"" + another + "\"";

			Pattern p = Pattern.compile("=\\s*[\"|']" + one + "[\"|']", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			Matcher m = p.matcher(s);
			StringBuffer sb = new StringBuffer();
			while (m.find())
			{
				m.appendReplacement(sb, another);
			}
			m.appendTail(sb);
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return s;
	}
}
