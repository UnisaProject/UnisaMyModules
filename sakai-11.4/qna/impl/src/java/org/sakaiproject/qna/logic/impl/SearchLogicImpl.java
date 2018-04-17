/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.logic.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.genericdao.api.finders.ByPropsFinder;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.SearchLogic;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;

public class SearchLogicImpl implements SearchLogic {

	private static Log log = LogFactory.getLog(SearchLogicImpl.class);
	private PermissionLogic permissionLogic;
	private ExternalLogic externalLogic;
	private QnaDao dao;

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setDao(QnaDao dao) {
		this.dao = dao;
	}

	/**
	 * @see SearchLogic#getAnswers(String)
	 */
	
	public List<QnaAnswer> getAnswers(String search) {
		log.debug("SearchLogicImpl::getAnswers");

		//String currentLocationId = externalLogic.getCurrentLocationId();

		if (search.length() > 0) {
			search = "%"+search+"%";
		}

		String currentLocationId = externalLogic.getCurrentLocationId();

		return dao.getSearchAnswers(search, currentLocationId);
	}

	/**
	 * @see SearchLogic#getCategories(String)
	 */
	@SuppressWarnings("unchecked")
	public List<QnaCategory> getCategories(String search) {
		log.debug("SearchLogicImpl::getCategories");

		String currentLocationId = externalLogic.getCurrentLocationId();

		if (search.length() > 0) {
			search = "%"+search+"%";
		}

		List<QnaCategory> findByProperties = dao.findByProperties(
			QnaCategory.class,
			new String[] {"location", "categoryText"},
			new Object[] {currentLocationId, search},
			new int[] {ByPropsFinder.EQUALS, ByPropsFinder.LIKE}
		);
		return findByProperties;
	}

	/**
	 * @see SearchLogic#getQuestions(String)
	 */
	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getQuestions(String search) {
		log.debug("SearchLogicImpl::getQuestions");

		String currentLocationId = externalLogic.getCurrentLocationId();

		if (search.length() > 0) {
			search = "%"+search+"%";
		}

		List<QnaQuestion> findByProperties = dao.findByProperties(
			QnaQuestion.class,
			new String[] {"location", "questionText"},
			new Object[] {currentLocationId, search},
			new int[] {ByPropsFinder.EQUALS, ByPropsFinder.LIKE}
		);
		return findByProperties;
	}

}