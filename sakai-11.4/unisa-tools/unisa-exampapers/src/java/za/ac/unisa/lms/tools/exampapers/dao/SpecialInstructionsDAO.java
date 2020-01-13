package za.ac.unisa.lms.tools.exampapers.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class SpecialInstructionsDAO extends StudentSystemDAO {
	
	public ArrayList getSpecialInstructionsCombined() throws Exception {
		
		List specialInstructionsList = new ArrayList();
		ArrayList specialInstructions = new ArrayList();
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		specialInstructionsList = jdt.queryForList("select FK_DOKCODE,SEQUENCE_NUMBER,TEXT from DOKLYN where FK_DOKCODE>=8700 and FK_DOKCODE<=8799 and FK_DOKMK_LANGUAGE='E' order by FK_DOKCODE,SEQUENCE_NUMBER");
		if (specialInstructionsList.size()> 0) {
			String code = "";
			String text = "";
			String previousCode = "";
			String label = "";
			for (int i=0; i<specialInstructionsList.size();i++){
				ListOrderedMap test = (ListOrderedMap) specialInstructionsList.get(i);
				code = (String) test.get("FK_DOKCODE").toString();
				text = (String) test.get("TEXT").toString();
				if (code.equals(previousCode) || "".equals(previousCode)) {
					label=label + text;
					previousCode=code;
					}
				else {
					String instCode = (String) previousCode.substring(2,4);
					specialInstructions.add(new LabelValueBean(label,previousCode));
					label=text;
					previousCode=code;
					}						
			}
			String instCode = (String) previousCode.substring(2,4);
			specialInstructions.add(new LabelValueBean(label,previousCode));
		}
		return specialInstructions;
	}
public ArrayList getSpecialInstructionsAll() throws Exception {
		
		List specialInstructionsList = new ArrayList();
		ArrayList specialInstructions = new ArrayList();
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		specialInstructionsList = jdt.queryForList("select FK_DOKCODE,SEQUENCE_NUMBER,TEXT from DOKLYN where FK_DOKCODE>=8700 and FK_DOKCODE<=8799 and FK_DOKMK_LANGUAGE='E' order by FK_DOKCODE,SEQUENCE_NUMBER");
		if (specialInstructionsList.size()> 0) {
			String code = "";
			String text = "";
			String previousCode = "";
			String label = "";
			for (int i=0; i<specialInstructionsList.size();i++){
				ListOrderedMap test = (ListOrderedMap) specialInstructionsList.get(i);
				code = (String) test.get("FK_DOKCODE").toString();
				label = (String) test.get("TEXT").toString();
				specialInstructions.add(new LabelValueBean(label,code));						
			}
		}
		return specialInstructions;
	}

}
