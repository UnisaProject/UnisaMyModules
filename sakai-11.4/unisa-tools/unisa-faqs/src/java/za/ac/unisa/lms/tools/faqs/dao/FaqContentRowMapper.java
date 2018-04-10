package za.ac.unisa.lms.tools.faqs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FaqContentRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		FaqContent faqContent = new FaqContent();
		faqContent.setContentId(new Integer(rs.getInt("Content_ID")));
		
		try {
		
		faqContent.setCategoryId(new Integer(rs.getInt("Category_ID")));
		faqContent.setQuestion(rs.getString("Question"));
		faqContent.setAnswer(rs.getString("Answer"));
		faqContent.setModifiedOn(rs.getTimestamp("Modified_On"));
		
		} catch (Exception e) {
		}
		
		return faqContent;
	}

}
