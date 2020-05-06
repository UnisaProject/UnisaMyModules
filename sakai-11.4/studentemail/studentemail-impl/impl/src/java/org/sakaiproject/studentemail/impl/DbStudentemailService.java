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

package org.sakaiproject.studentemail.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import org.sakaiproject.db.api.SqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbStudentemailService extends BaseStudentemailService {
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(DbStudentemailService.class);

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

		 
		public String getEmail(String site) {
			// TODO Auto-generated method stub
			return null;
		}
	 
		public boolean checkEmail(String site) {
			boolean email = false;
			String statement = "Select VALUE from SAKAI_SITE_PROPERTY where SITE_ID = '"+site+"' and NAME = 'contact-email'";
			try {

				Iterator<?> i = m_sqlService.dbRead(statement).iterator();
				if (i.hasNext()) {
					while (i.hasNext()) {
						if(i.next().toString().length()>0) {
							email = true;
						}else {
							clearNullContactRecord(site);
						}
					}
				}
			} catch (Exception ex) {

			}
			return email;
		}

 

	public void clearNullContactRecord(String site) {
		Connection dbConnection = null;
		ResultSet rs = null;
		try {
			dbConnection = m_sqlService.borrowConnection();
			String statement = "delete from SAKAI_SITE_PROPERTY where  NAME = 'contact-email' AND VALUE IS NULL and SITE_ID = ?";
			PreparedStatement pstmt = dbConnection.prepareStatement(statement);
        	pstmt.setString(1, site);			 
			pstmt.executeUpdate();			 
			pstmt.close();
		} catch (SQLException e) {
			M_log.error(this+" Error on clearNullContactRecord siteId "+site+ " and error "+e.getMessage());
			e.printStackTrace();
		} finally {
			m_sqlService.returnConnection(dbConnection);
		}
		
	}

	 
	}
}
 
