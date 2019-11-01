/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-impl/src/java/org/etudes/component/app/melete/SpecialAccessDB.java $
 * $Id: SpecialAccessDB.java 3641 2012-12-02 21:43:44Z ggolden $
 ***********************************************************************************
 *
 * Copyright (c) 2010 Etudes, Inc.
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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.etudes.api.app.melete.SpecialAccessObjService;
import org.etudes.api.app.melete.SpecialAccessService;

public class SpecialAccessDB
{
	private HibernateUtil hibernateUtil;
	private Log logger = LogFactory.getLog(SpecialAccessDB.class);

	/**
	 * default constructor
	 */
	public SpecialAccessDB()
	{

	}

	/**
	 * Delete special accesses specified in the list from the db
	 * 
	 * @param saList
	 *        List of special accesses
	 * @throws Exception
	 */
	public void deleteSpecialAccess(List saList) throws Exception
	{
		Transaction tx = null;
		SpecialAccess sa = null;
		String delAccessIds = null;
		StringBuffer allAccessIds = new StringBuffer("(");
		for (Iterator saIter = saList.iterator(); saIter.hasNext();)
		{
			Integer accessId = (Integer) saIter.next();
			allAccessIds.append(accessId.toString() + ",");
		}
		if (allAccessIds.lastIndexOf(",") != -1) delAccessIds = allAccessIds.substring(0, allAccessIds.lastIndexOf(",")) + " )";
		String delSpecialAccessStr = "delete SpecialAccess sa where sa.accessId in " + delAccessIds;
		try
		{
			Session session = getHibernateUtil().currentSession();
			tx = session.beginTransaction();
			int deletedEntities = session.createQuery(delSpecialAccessStr).executeUpdate();
			tx.commit();
		}
		catch (HibernateException he)
		{
			if (tx != null) tx.rollback();
			logger.error(he.toString());
			throw he;
		}
		catch (Exception e)
		{
			if (tx != null) tx.rollback();
			logger.error(e.toString());
			throw e;
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
				throw he;
			}
		}
	}

	/**
	 * @return Returns the hibernateUtil.
	 */
	public HibernateUtil getHibernateUtil()
	{
		return hibernateUtil;
	}

	/**
	 * Get special access list of this module
	 * 
	 * @param moduleId
	 *        module id
	 * @return List of special accesses
	 */
	public List<SpecialAccessService> getSpecialAccess(int moduleId)
	{
		List<SpecialAccessService> saList = new ArrayList<SpecialAccessService>();
		try
		{
			Session session = getHibernateUtil().currentSession();
			Query q = session.createQuery("from SpecialAccess sa where sa.moduleId =:moduleId");
			q.setParameter("moduleId", moduleId);
			saList = q.list();

		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return saList;

	}

	/**
	 * Gets a list of module ids from the special access table for this course
	 * 
	 * @param courseId
	 *        Course id
	 * @return list of module ids
	 */
	public List getSpecialAccessModuleIds(String courseId)
	{
		List saList = new ArrayList();
		try
		{
			Session session = getHibernateUtil().currentSession();
			Query q = session.createQuery("select sa.moduleId from SpecialAccess sa where sa.module.coursemodule.courseId =:courseId");
			q.setParameter("courseId", courseId);
			saList = q.list();

		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return saList;

	}

	/**
	 * @param hibernateUtil
	 *        The hibernateUtil to set.
	 */
	public void setHibernateUtil(HibernateUtil hibernateUtil)
	{
		this.hibernateUtil = hibernateUtil;
	}

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
	public void setSpecialAccess(SpecialAccess sa) throws Exception
	{
		Transaction tx = null;
		try
		{
			Session session = hibernateUtil.currentSession();
			tx = session.beginTransaction();

			Query q = session.createQuery("select sa1 from SpecialAccess as sa1 where sa1.accessId =:accessId");
			q.setParameter("accessId", sa.getAccessId());
			SpecialAccess find_sa = (SpecialAccess) q.uniqueResult();

			if (find_sa == null)
			{
				session.save(sa);
			}
			else
			{
				
				find_sa.setUsers(sa.getUsers());
				find_sa.setStartDate(sa.getStartDate());
				find_sa.setEndDate(sa.getEndDate());
				find_sa.setAllowUntilDate(sa.getAllowUntilDate());
				find_sa.setOverrideStart(sa.isOverrideStart());
				find_sa.setOverrideEnd(sa.isOverrideEnd());
				find_sa.setOverrideAllowUntil(sa.isOverrideAllowUntil());
				session.update(find_sa);
			}
			tx.commit();

		}
		catch (StaleObjectStateException sose)
		{
			if (tx != null) tx.rollback();
			logger.error("stale object exception" + sose.toString());
			throw sose;
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
			he.printStackTrace();
			throw he;
		}
		catch (Exception e)
		{
			if (tx != null) tx.rollback();
			logger.error(e.toString());
			throw e;
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
				throw he;
			}
		}
	}

}
