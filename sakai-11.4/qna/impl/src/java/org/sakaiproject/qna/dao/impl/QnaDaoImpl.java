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
package org.sakaiproject.qna.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.sakaiproject.genericdao.hibernate.HibernateGeneralGenericDao;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementations of any specialized DAO methods from the specialized DAO that allows the developer to extend the functionality of the
 * generic dao package
 *
 * @author Sakai App Builder -AZ
 */
@Slf4j
public class QnaDaoImpl extends HibernateGeneralGenericDao implements QnaDao {


    public void init() {
        log.debug("init");
    }

    /**
     * Get new questions for location
     * 
     * @param locationId unique id of location
     * @return {@link List} of new {@link QnaQuestion}
     */
    @SuppressWarnings("unchecked")
    public List<QnaQuestion> getNewQuestions(String locationId) {
    	String hql = "from QnaQuestion as q where q.location = '" + locationId + "' and q.published=false "
    	 + "and ((select count(*) from QnaAnswer as a where a.question = q and a.privateReply=true)=0)";

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
    	return query.list();
    }
    
    /**
     * Search answers
     * 
     * @param search search query
     * @param location unique id of location
     * @return {@link List} of {@link QnaAnswer}
     */
    @SuppressWarnings("unchecked")
    public List<QnaAnswer> getSearchAnswers(String search, String location) {
        
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(QnaAnswer.class);
    	criteria.add(Restrictions.ilike("answerText", search));
    	criteria.createAlias("question", "question", Criteria.LEFT_JOIN);
    	criteria.add(Restrictions.eq("question.location", location));
    	criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

    	return criteria.list();
    }
    
      
}
