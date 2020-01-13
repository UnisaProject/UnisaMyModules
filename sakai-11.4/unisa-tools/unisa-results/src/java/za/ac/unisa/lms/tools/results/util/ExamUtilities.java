package za.ac.unisa.lms.tools.results.util;

import java.util.Calendar;

/**
 *
 * @author PRETOJ
 */
public class ExamUtilities {

	/** Creates a new instance of ExamUtilities */
	public short getDefaultExamPeriod() {
		int month;
		short ExamPeriod = 0;
		month = Calendar.getInstance().get(Calendar.MONTH);
		switch (month) {
		case 1:
		case 2:
		case 3:
			ExamPeriod = 1;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			ExamPeriod = 6;
			break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			ExamPeriod = 10;
			break;
		}
		return ExamPeriod;
	}

	public int getDefaultExamYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public boolean validStudentNr(String StudentNr) {
		if(StudentNr.equals("")) {
			return false;
		}
		int value = 0;
		int total = 0;
		int n = StudentNr.length();
		for (int i = 0; i <= (StudentNr.length() - 1); i++) {
			value = Integer.parseInt(StudentNr.charAt(i) + "") * n;
			total = total + value;
			n--;
		}
		if (total % 11 == 0) {
			return true;
		} else {
			return false;
		}
	}
}