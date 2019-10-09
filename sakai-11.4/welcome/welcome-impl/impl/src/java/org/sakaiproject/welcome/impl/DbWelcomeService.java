/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.welcome.impl;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.db.api.SqlService;

public class DbWelcomeService extends BaseWelcomeService {
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(DbWelcomeService.class);

	/** Dependency: SqlService */
	protected SqlService m_sqlService = null;

	/**
	 * Dependency: SqlService.
	 * 
	 * @param service The SqlService.
	 */
	public void setSqlService(SqlService service) {
		m_sqlService = service;
	}

	protected Storage newStorage() {
		return new DbStorage();
	}

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init() {
		try {

			super.init();
		} catch (Exception t) {
			M_log.warn("init(): ", t);
		}
	}

	protected class DbStorage implements Storage {

		public String getWelcomeContent(String siteId) {

			String content = null;
			String sql = "select CONTENT " + "  from WELCOME_CONTENT " + " where SITE_ID = '" + siteId + "'";
			try {

				Iterator<?> i = m_sqlService.dbRead(sql).iterator();
				if (i.hasNext()) {
					while (i.hasNext()) {
						content = (String) i.next();
					}
				} else {
					sql = "select CONTENT" + "  from WELCOME_CONTENT " + " where SITE_ID = '!admin'";
					try {
						Iterator<?> j = m_sqlService.dbRead(sql).iterator();
						if (j.hasNext()) {
							while (j.hasNext()) {
								content = (String) j.next();
							}
						} else {
							content = "Default Welcome Message does not exist";
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} catch (Exception ex) {

			}
			return content;

		}

		public String saveWelcomeContent(String siteId, String content) {

			Calendar cal = Calendar.getInstance();
			boolean response = false;
			String returnstate = null;
			try {
				java.sql.Date currentDate = new java.sql.Date(cal.getTime().getTime());
				String sql = "select count(*) from WELCOME_CONTENT where SITE_ID = '" + siteId + "'";
				int count = (new Integer((String) m_sqlService.dbRead(sql).iterator().next())).intValue();
				if (count == 0) {
					//sql = "insert into WELCOME_CONTENT(SITE_ID, CONTENT, MODIFIED_ON) values(?,?,to_date('"+ currentDate + "','YYYY-MM-DD'))";
							
					sql = "insert into WELCOME_CONTENT(SITE_ID, CONTENT, MODIFIED_ON) values(?,?,str_to_date('"+currentDate+"','%Y-%m-%d'))";
					response = m_sqlService.dbWrite(sql, new Object[] { siteId, content });
				} else {
					//sql = "update WELCOME_CONTENT" + " set CONTENT = ?, " + "     MODIFIED_ON = to_date(?,'YYYY-MM-DD') where SITE_ID = ?";
					//sql = "update WELCOME_CONTENT" + " set CONTENT = ?, " + "     MODIFIED_ON = to_date('" + currentDate + "','YYYY-MM-DD')" + " where SITE_ID = ?";
				      sql = "update WELCOME_CONTENT" + " set CONTENT = ?, " + "     MODIFIED_ON = str_to_date('"+currentDate+"','%Y-%m-%d')" + " where SITE_ID = ?"; 
					response = m_sqlService.dbWrite(sql, new Object[] { content, siteId });
				}
				if (response) {
					returnstate = "success";
				}
			} catch (Exception ex) {
				returnstate = null;
			}
			return returnstate;
		}

		public String revertWelcomeContent(String siteId) {

			boolean response = false;
			String returnstate = null;
			try {
				String sql = "select count(*) from WELCOME_CONTENT where SITE_ID = '" + siteId + "'";
				int count = (new Integer((String) m_sqlService.dbRead(sql).iterator().next())).intValue();
				if (count > 0) {
					sql = "delete from WELCOME_CONTENT where SITE_ID =?";
					response = m_sqlService.dbWrite(sql, new Object[] { siteId });
				}
				if (response) {
					returnstate = "success";
				}
			} catch (Exception ex) {
				returnstate = null;
			}
			return returnstate;
		}

	}

}
