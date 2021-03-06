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

package org.sakaiproject.faqs.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.faqs.dataModel.FaqCategory;
import org.sakaiproject.faqs.dataModel.FaqContent;

public class DbFaqsService extends BaseFaqsService {
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(DbFaqsService.class);

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

 

		public List getFaqCategories(String siteId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select SITE_ID, Category_ID, Description, Modified_On from FAQ_CATEGORY "
						+ "where SITE_ID = ? order by Modified_On desc";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, siteId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						FaqCategory faqCategory = new FaqCategory();
						faqCategory.setSiteId(rs.getString("SITE_ID"));
						faqCategory.setCategoryId(new Integer(rs.getInt("Category_ID")));
						faqCategory.setDescription(rs.getString("Description"));
						faqCategory.setModifiedOn(rs.getTimestamp("Modified_On"));
						faqCategory.setRemove(false);
						faqCategory.setExpanded(false);
						results.add(faqCategory);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getFaqCategories for the site "+siteId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}

		public List getFaqCategory(int categoryId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select * from FAQ_CATEGORY where Category_Id = ? ";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, categoryId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						FaqCategory faqCategory = new FaqCategory();
						faqCategory.setSiteId(rs.getString("SITE_ID"));
						faqCategory.setCategoryId(new Integer(rs.getInt("Category_ID")));
						faqCategory.setDescription(rs.getString("Description"));
						faqCategory.setModifiedOn(rs.getTimestamp("Modified_On"));
						faqCategory.setRemove(false);
						faqCategory.setExpanded(false);
						results.add(faqCategory);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getFaqCategory for the categoryId "+categoryId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;
		}
		
		public List getFaqContents(Integer categoryId) {

			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select * from FAQ_CONTENT where Category_Id = ? order by Question";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, categoryId);
				rs = pstmt.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						FaqContent faqContent = new FaqContent();
						faqContent.setContentId(new Integer(rs.getInt("Content_ID")));
						faqContent.setCategoryId(new Integer(rs.getInt("Category_ID")));
						faqContent.setQuestion(rs.getString("Question"));
						faqContent.setAnswer(rs.getString("Answer"));
						faqContent.setModifiedOn(rs.getTimestamp("Modified_On"));
						results.add(faqContent);
					}
				}
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on getFaqContents for the FAQ categoryId "+categoryId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}

			return results;
		}
		
		public List getFaqContent(int contentId) {

			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select * from FAQ_CONTENT where Content_ID = ?";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, contentId);
				rs = pstmt.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						FaqContent faqContent = new FaqContent();
						faqContent.setContentId(new Integer(rs.getInt("Content_ID")));
						faqContent.setCategoryId(new Integer(rs.getInt("Category_ID")));
						faqContent.setQuestion(rs.getString("Question"));
						faqContent.setAnswer(rs.getString("Answer"));
						faqContent.setModifiedOn(rs.getTimestamp("Modified_On"));
						results.add(faqContent);
					}
				}
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on getFaqContent for the FAQ content_id "+contentId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}

			return results;
		}

		
		public List getFaqContentWithCategory(int contentId) {

			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select c.Content_ID Content_ID, c.Category_ID Category_ID,c.Question Question, c.Answer Answer,c.Modified_On Modified_On,CAT.DESCRIPTION  DESCRIPTION  from FAQ_CONTENT c, FAQ_CATEGORY cat where c.CATEGORY_ID=cat.CATEGORY_ID and c.CONTENT_ID = ?";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, contentId);
				rs = pstmt.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						FaqContent faqContent = new FaqContent();
						faqContent.setContentId(new Integer(rs.getInt("Content_ID")));
						faqContent.setCategoryId(new Integer(rs.getInt("Category_ID")));
						faqContent.setQuestion(rs.getString("Question"));
						faqContent.setAnswer(rs.getString("Answer"));
						faqContent.setModifiedOn(rs.getTimestamp("Modified_On"));
						faqContent.setCategoryDescription(rs.getString("DESCRIPTION"));
						results.add(faqContent);
					}
				}
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on getFaqContentWithCategory for the FAQ content_id "+contentId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}

			return results;
		}
		public void insertFaqCategory(String siteId, String categoryDesc) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "insert into FAQ_CATEGORY (Category_ID,SITE_ID,Description,Modified_On) values (NULL,?, ?, sysdate())";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, siteId);
				pstmt.setString(2, categoryDesc);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on insertFaqCategory for the FAQ siteId "+siteId+" categoryDesc "+categoryDesc+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void insertFaqContent(String question,String answer, String categoryId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "insert into FAQ_CONTENT (Content_ID,Question, Answer, Category_ID, Modified_On) values (NULL,?, ?, ?,sysdate())";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, question);
				pstmt.setString(2, answer);
				pstmt.setString(3, categoryId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on insertFaqContent for the FAQ Answer "+answer+" Category_ID "+categoryId+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
 
	
		public void updateFaqCategory(String categoryDesc, int categoryId) {
				Connection dbConnection = null;
				ResultSet rs = null;
				try {
					dbConnection = m_sqlService.borrowConnection();
					String statement = "update FAQ_CATEGORY set Description = ?, Modified_On = sysdate() where Category_Id = ? ";

					PreparedStatement pstmt = dbConnection.prepareStatement(statement);
					pstmt.setString(1, categoryDesc);
					pstmt.setInt(2, categoryId);
					pstmt.executeUpdate();
					pstmt.close();
				} catch (SQLException e) {
					M_log.error(this+" Error on updateFaqCategory for the FAQ categoryDesc "+categoryDesc+" categoryId "+categoryId+ " and error "+e.getMessage());
					e.printStackTrace();
				} finally {
					m_sqlService.returnConnection(dbConnection);
				}
			}
 

		public void deleteFaqCategory(Integer categoryId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "delete from FAQ_CONTENT where Category_Id = ? ";
				String statement2 = "delete from FAQ_CATEGORY where Category_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				PreparedStatement pstmt2 = dbConnection.prepareStatement(statement2);
				pstmt.setInt(1, categoryId);
				pstmt2.setInt(1, categoryId);
				pstmt.executeUpdate();
				pstmt2.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on deleteFaqCategory for the FAQ  categoryId "+categoryId+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
 
		public void deleteFaqContent(Integer contentId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "delete from FAQ_CONTENT where Content_Id = ? ";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
	        	pstmt.setInt(1, contentId);			 
				pstmt.executeUpdate();			 
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on deleteFaqContent for the FAQ  contentId "+contentId+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void updateFaqContent(FaqContent faqContent) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "update FAQ_CONTENT set Question = ?, Answer = ?, Category_Id = ?, Modified_On = sysdate() where Content_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, faqContent.getQuestion());
				pstmt.setString(2, faqContent.getAnswer());
				pstmt.setInt(3, faqContent.getCategoryId());
				pstmt.setInt(4, faqContent.getContentId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on updateFaqContent for the FAQ Content_Id "+faqContent.getContentId()+" categoryId "+faqContent.getCategoryId()+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}

	}

}
