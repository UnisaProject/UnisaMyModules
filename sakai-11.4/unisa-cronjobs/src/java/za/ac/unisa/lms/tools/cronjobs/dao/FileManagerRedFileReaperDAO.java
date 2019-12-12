package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class FileManagerRedFileReaperDAO extends StudentSystemDAO {
	
	private Log log;
	
	public FileManagerRedFileReaperDAO() {
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
		//log.debug("Checking for collection.");
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select count(mk_study_unit_code) "+
        "from sunstm "+
        "where fk_annstmacadyear = ? "+
        "and fk_annstmacadprd = ? "+
        "and fk_annstmname = ? "+
        "and fk_annstm_stmtyp = ? "+
        "and fk_annstmnr = ? "+
        "and fk_annstm_lang = ? ";
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
		if (count > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<?> getMaterial(String code, String year) {
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
			"and a.fk_webid is not null "+
			"and s.mk_study_unit_code = '"+code+"' "+
			"and a.academic_year >= "+(acadyear-1)+" "+
			"and (a.fk_stmtypcode = 'TL' "+
			"or a.fk_stmtypcode = 'GD') "+
			"order by a.nr, a.academic_year, a.academic_period, a.din_language ";
		//log.debug("Executing query: "+query);
		List<?> material = jdt.queryForList(query);
		log.debug("Code : "+code+" returned "+material.size()+" records.");
		//log.debug("Query executed successfully");
		return material;
	}
	
	/*public boolean deleteWebTraces(int webid) {
		DataSource ds = getDataSource();
		try {
			ds.getConnection().setAutoCommit(false);
			Savepoint sp = ds.getConnection().setSavepoint();
			JdbcTemplate jdt = new JdbcTemplate(ds);
			List resource = jdt.queryForList(
				"select * from annstm where fk_webid = ?",
				new Object[] {
					new Integer(webid)
				},
				new int[] {
					Types.NUMERIC
				}
			);
			Iterator i = resource.iterator();
			while (i.hasNext()) {
				HashMap record = (HashMap)i.next();
				Hashtable annstm = new Hashtable();
				annstm.put("ACADEMIC_YEAR", record.get("ACADEMIC_YEAR"));
				annstm.put("ACADEMIC_PERIOD", record.get("ACADEMIC_PERIOD"));
				annstm.put("NAME", record.get("NAME"));
				annstm.put("FK_STMTYPCODE", record.get("FK_STMTYPCODE"));
				annstm.put("NR", record.get("NR"));
				annstm.put("DIN_LANGUAGE", record.get("DIN_LANGUAGE"));
				if (updateSunstmRecord(jdt, annstm)) {
					
				}
				
			}
			ds.getConnection().rollback(sp);
		} catch (SQLException e) {
			log.error(e);
		}
		return false;
	}
	
	public boolean updateSunstmRecord(JdbcTemplate jdt, Hashtable annstm) {
		Calendar cal = Calendar.getInstance();
		cal.set(9999, 12, 31);
		int update = jdt.update(
			"update sunstm"+
			"set implementation_dat = ? "+
			"where fk_annstmacadyear = ? "+
			"and fk_annstmacadprd = ? "+
			"and fk_annstmname = ? "+
			"and fk_annstm_stmtyp = ? "+
			"and fk_annstmnr = ? "+
			"and fk_annstm_lang = ?",
			new Object[] {
				new Date(cal.getTimeInMillis()),
				new Integer(annstm.get("ACADEMIC_YEAR").toString()),
				new Integer(annstm.get("ACADEMIC_PERIOD").toString()),
				new String(annstm.get("NAME").toString()),
				new String(annstm.get("FK_STMTYPCODE").toString()),
				new String(annstm.get("NR").toString()),
				new String(annstm.get("DIN_LANGUAGE").toString())
			},
			new int[] {
				Types.DATE,
				Types.NUMERIC,
				Types.NUMERIC,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR
			}
		);
		if (update > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateAnnstmRecord(JdbcTemplate jdt, Hashtable annstm) {
		int update = jdt.update(
			"update annstm "+
			"set fk_webid = ? "+
			"where fk_webid = ?",
			new Object[] {
				new Object(),
				new Integer(annstm.get("ACADEMIC_PERIOD").toString())
			},
			new int[] {
				Types.NULL,
				Types.NUMERIC
			}
		);
		return false;
	}*/
}
