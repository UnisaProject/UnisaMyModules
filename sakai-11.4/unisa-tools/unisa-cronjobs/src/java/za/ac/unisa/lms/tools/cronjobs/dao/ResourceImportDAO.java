package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ResourceImportDAO extends StudentSystemDAO {
	
	private Log log;
	
	public ResourceImportDAO() {
		log = LogFactory.getLog(this.getClass());
	}
	
	public boolean isPartOfCollection(
		String acadyear,
		String acadprd,
		String name,
		String type,
		String nr,
		String lang
	) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select count(mk_study_unit_code) "+
        "from sunstm "+
        "where fk_annstmacadyear = ? "+
        "and fk_annstmacadprd = ? "+
        "and fk_annstmname = ? "+
        "and fk_annstm_stmtyp = ? "+
        "and fk_annstmnr = ? "+
        "and fk_annstm_lang = ? ";
		log.debug("Executing query: "+query);
		int count = jdt.queryForInt(
			query,
			new Object[] {
				new Integer(acadyear),
				new Integer(acadprd),
				name,
				type,
				nr,
				lang
			},
			new int[] {
				Types.NUMERIC,
				Types.NUMERIC,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR
			}
		);
		log.debug("Query executed successfully");
		if (count > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<?> getMaterial(String code, String year, String period) {
		int acadyear = new Integer(year).intValue();
		acadyear = acadyear+2000;
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select * from annstm a, sunstm s, webstm w "+
			"where a.academic_year = s.fk_annstmacadyear "+
			"and a.academic_period = s.fk_annstmacadprd "+
			"and a.name = s.fk_annstmname "+
			"and a.nr = s.fk_annstmnr "+
			"and a.din_language = s.fk_annstm_lang "+
			"and a.fk_stmtypcode = s.fk_annstm_stmtyp "+
			"and a.fk_webid = w.webid(+) "+
			"and s.mk_study_unit_code = '"+code+"' "+
			"and a.academic_year = "+acadyear+" ";
		if (period.equalsIgnoreCase("0")) {
			query += "and "+
				"(a.academic_period = 0 or "+
				"a.academic_period = 4 or "+
				"a.academic_period = 7) ";
		} else if (period.equalsIgnoreCase("1")) {
			query += "and "+
				"(a.academic_period = 1 or "+
				"a.academic_period = 3 or "+
				"a.academic_period = 4) ";
		} else if (period.equalsIgnoreCase("2")) {
			query += "and "+
				"(a.academic_period = 2 or "+
				"a.academic_period = 3 or "+
				"a.academic_period = 4) ";
		} else if (period.equalsIgnoreCase("6")) {
			query += "and "+
				"(a.academic_period = 4 or "+
				"a.academic_period = 6 or "+
				"a.academic_period = 7) ";
		}
		query += "and (a.fk_stmtypcode = 'TL' "+
			"or a.fk_stmtypcode = 'GD') "+
			"and w.master_copy = 'y' "+
			"and s.implementation_dat <= SYSDATE "+
			"and w.expiration_date >= SYSDATE "+
			"order by a.fk_stmtypcode desc, a.academic_period, a.nr, a.din_language";
		log.debug("Executing query: "+query);
		List<?> material = jdt.queryForList(query);
		log.debug("Query executed successfully");
		return material;
	}
}
