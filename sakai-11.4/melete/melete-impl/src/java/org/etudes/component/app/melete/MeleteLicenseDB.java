/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-impl/src/java/org/etudes/component/app/melete/MeleteLicenseDB.java $
 * $Id: MeleteLicenseDB.java 73855 2011-04-19 20:41:15Z rashmi@etudes.org $
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.etudes.api.app.melete.MeleteLicenseService;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class MeleteLicenseDB implements Serializable
{
	ArrayList licenseTypes;
	private HibernateUtil hibernateUtil;
	private Log logger = LogFactory.getLog(MeleteLicenseDB.class);

	/**
	 * default constructor
	 */
	public MeleteLicenseDB()
	{
	}

	/**
	 * Adds by default variations of Creative commons license
	 * 
	 * @param ccLicenses
	 *        List of meleteLicense objects
	 */
	public void createCcLicense(ArrayList ccLicenses)
	{
		saveData(ccLicenses);
	}

	/**
	 * Adds by default license choices
	 * 
	 * @param moduleLicense
	 *        ModuleLicense
	 */
	public void createMeleteLicense(ArrayList moduleLicenses)
	{
		saveData(moduleLicenses);

	}

	/**
	 * Get license name for creative commons and public domain license choice. Used mainly for export packages.
	 * 
	 * @param licenseUrl
	 * @return
	 */
	public String fetchCcLicenseName(String licenseUrl)
	{
		String licenseInfo = null;
		try
		{
			logger.debug("MeleteLicenseDB:fetchCcLicenseName");
			Session session = getHibernateUtil().currentSession();
			Query q = session.createQuery("select ccl.name from CcLicense ccl where ccl.url =:url");
			q.setParameter("url", licenseUrl);
			licenseInfo = (String) q.uniqueResult();
			getHibernateUtil().closeSession();
		}
		catch (Exception ex)
		{
			// ex.printStackTrace();
			logger.error(ex.toString());
		}
		return licenseInfo;
	}

	/**
	 * Get license URL. Used mainly for IMS import packages.
	 * 
	 * @param name
	 * @return
	 */
	public CcLicense fetchCcLicenseUrl(String name)
	{
		CcLicense licenseInfo = null;
		try
		{
			logger.debug("MeleteLicenseDB:fetchCcLicenseUrl " + name);
			Session session = getHibernateUtil().currentSession();
			Query q = session.createQuery("from CcLicense ccl1 where ccl1.name =:name");
			q.setParameter("name", name);
			licenseInfo = (CcLicense) q.uniqueResult();
			getHibernateUtil().closeSession();
		}
		catch (Exception ex)
		{
			// ex.printStackTrace();
			logger.error(ex.toString());
		}
		return licenseInfo;
	}

	/**
	 * called from backing bean addModulePage to get the license url and license name
	 * 
	 * @param reqAttr
	 * @param allowCmrcl
	 * @param allowMod
	 * @return
	 */
	public String[] fetchCcLicenseURL(Boolean reqAttr, Boolean allowCmrcl, Integer allowMod)
	{
		String[] licenseInfo = new String[2];
		try
		{

			Session session = getHibernateUtil().currentSession();
			CcLicense Cc = (CcLicense) session.createCriteria(CcLicense.class).add(
					Restrictions.sqlRestriction("req_attr=?", reqAttr, StandardBasicTypes.BOOLEAN)).add(
							Restrictions.sqlRestriction("allow_Cmrcl=?", allowCmrcl, StandardBasicTypes.BOOLEAN)).add(
									Restrictions.sqlRestriction("allow_Mod=?", allowMod, StandardBasicTypes.INTEGER)).list().get(0);
			licenseInfo[0] = Cc.getUrl();
			licenseInfo[1] = Cc.getName();
			getHibernateUtil().closeSession();
		}
		catch (Exception ex)
		{
			// ex.printStackTrace();
			logger.error(ex.toString());
		}
		return licenseInfo;
	}

	/**
	 * fetches ccLicense
	 * 
	 * @return
	 */
	public List getCcLicense()
	{

		List ccLicenceList = new ArrayList();
		try
		{
			Session session = getHibernateUtil().currentSession();

			StringBuffer query = new StringBuffer();
			query.append("from CcLicense");
			ccLicenceList = session.createQuery(query.toString()).list();
			getHibernateUtil().closeSession();
		}
		catch (HibernateException he)
		{
			logger.error(this + he.toString());
		}

		return ccLicenceList;
	}

	/**
	 * @return Returns the hibernateUtil.
	 */
	public HibernateUtil getHibernateUtil()
	{
		return hibernateUtil;
	}

	/**
	 * Get the licenses from db
	 * 
	 * @return a list of MeleteLicense objects
	 */
	public List<MeleteLicenseService> getLicenseTypes()
	{
		List<MeleteLicenseService> licenseTypes = new ArrayList<MeleteLicenseService>();
		try
		{
			Session session = getHibernateUtil().currentSession();
			StringBuffer query = new StringBuffer();
			query.append("from MeleteLicense");
			licenseTypes = session.createQuery(query.toString()).list();
			getHibernateUtil().closeSession();
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}

		return licenseTypes;
	}

	/**
	 * Adds to the database.
	 * 
	 * @param objs
	 *        List of licenses
	 */
	private void saveData(ArrayList objs)
	{
		Transaction tx = null;
		try
		{
			Session session = hibernateUtil.currentSession();

			tx = session.beginTransaction();
			for (int i = 0; i < objs.size(); i++)
			{
				Object obj = objs.get(i);
				session.save(obj);
			}
			tx.commit();
		}
		catch (HibernateException he)
		{
			try
			{
				if (tx != null) tx.rollback();
			}
			catch (HibernateException e)
			{
				logger.error(e.toString());
			}
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException e)
			{
				logger.error(e.toString());
			}
		}
	}

	/**
	 * @param hibernateUtil
	 *        The hibernateUtil to set.
	 */
	public void setHibernateUtil(HibernateUtil hibernateUtil)
	{
		this.hibernateUtil = hibernateUtil;
	}

}
