package za.ac.unisa.lms.tools.graduationceremony.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import za.ac.unisa.lms.tools.graduationceremony.forms.Gradcerem;

/**
 * @author Tnonyack
 * 
 *         This class is used to create Prepared Statement instances which
 *         represent the pre-compiled version of the sql query
 *         {@WEBCER_INSERT_QRY} as defined below.
 */
public class WebCerInsertPreparedStatementCreator implements
		PreparedStatementCreator {

	private static final String WEBCER_INSERT_QRY = "insert into webcer (student_nr ,qualification_code, graduation_ceremon, language0, "
			+ " present_flag, no_of_guests, comment0, accept_name, accept_date) "
			+ " values(?,?,?,?,?,?,?,?, to_date(?,'YYYY-MM-DD'))";

	private String student_nr;
	private String lang;
	private String present;
	private Gradcerem gradcerem;

	/**
	 * Constructor
	 * 
	 * @param student_nr
	 *            Student number for the graduate
	 * @param lang
	 *            Preferred Language
	 * @param present
	 * 			Present for graduation or not.
	 * @param gradcerem
	 * 			Graduation ceremony details
	 */
	public WebCerInsertPreparedStatementCreator(String student_nr, String lang,
			String present, Gradcerem gradcerem) {
		this.student_nr = student_nr;
		this.lang = lang;
		this.present = present;
		this.gradcerem = gradcerem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.jdbc.core.PreparedStatementCreator#
	 * createPreparedStatement(java.sql.Connection)
	 */
	public PreparedStatement createPreparedStatement(Connection connection)
			throws SQLException {

		PreparedStatement ps = connection.prepareStatement(WEBCER_INSERT_QRY);
		ps.setString(1, student_nr);
		ps.setString(2, gradcerem.getQualification());
		ps.setString(3, gradcerem.getCeremonynr());
		ps.setString(4, lang);
		ps.setString(5, present);
		ps.setString(6, gradcerem.getGuests());
		ps.setString(7, gradcerem.getPunctuation());
		ps.setString(8, gradcerem.getAcceptname());
		ps.setString(9, gradcerem.getAcceptdate());
		return ps;
	}
}