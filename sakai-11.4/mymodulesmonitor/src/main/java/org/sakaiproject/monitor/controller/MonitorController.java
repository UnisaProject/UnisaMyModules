package org.sakaiproject.monitor.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import za.ac.unisa.lms.db.SakaiDAO;

@Controller
public class MonitorController extends SakaiDAO {

	public MonitorController() {

	}

	@RequestMapping(value="testplain", method = RequestMethod.GET)
	public String testPlainView() {
		return "plain";
	}

	@RequestMapping(value = "/testSakaiDB", method = RequestMethod.GET)
	public String testSakaiDB() throws Exception {
		if (testSakaiStatement() == "success") {
			return "testSakaiDB";
		} else {
			return "sakaiDBFail";
		}

	}

	public String testSakaiStatement() throws Exception {

		String actStatus = "fail";

		try {

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			try {
				actStatus = (String) jdt.queryForObject("select USER_ID from SAKAI_USER where user_id = ?",
						new Object[] {"admin"}, String.class);
 
				if (actStatus.equals(null) || actStatus.equals("") || actStatus.equals(" ")) {
					return actStatus;
				} else {
					actStatus = "success";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return actStatus;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return actStatus;
		}

		return actStatus;
	}

}
