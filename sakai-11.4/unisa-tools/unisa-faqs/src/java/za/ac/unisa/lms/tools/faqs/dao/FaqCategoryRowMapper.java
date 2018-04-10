package za.ac.unisa.lms.tools.faqs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FaqCategoryRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		FaqCategory faqCategory = new FaqCategory();
		faqCategory.setSiteId(rs.getString("SITE_ID"));
		faqCategory.setCategoryId(new Integer(rs.getInt("Category_ID")));
		faqCategory.setDescription(rs.getString("Description"));
		faqCategory.setModifiedOn(rs.getTimestamp("Modified_On"));
		faqCategory.setRemove(false);
		faqCategory.setExpanded(false);
		
		return faqCategory;
	}

}
