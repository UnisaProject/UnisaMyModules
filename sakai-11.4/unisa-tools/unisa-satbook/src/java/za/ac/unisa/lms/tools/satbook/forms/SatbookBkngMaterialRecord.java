package za.ac.unisa.lms.tools.satbook.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 *  @author syzelle
 *
 * TABLE: SATBOOK_BKNG_MATERIAL;
+------------+------------------+------+-----+---------------------+-------+
| Field      | Type             | Null | Key | Default             | Extra |
+------------+------------------+------+-----+---------------------+-------+
| bkng_id    | int(10) unsigned |      | PRI | 0                   |       |
| mat_id     | int(2) unsigned  |      | PRI | 0                   |       |
| Cassette   | int(10) unsigned | YES  |     | 0                   |       |
| Time_in    | timestamp        | YES  |     | CURRENT_TIMESTAMP   |       |
| Time_inff  | char(2)          | YES  |     | NULL                |       |
| Time_out   | timestamp        | YES  |     | 0000-00-00 00:00:00 |       |
| Time_outff | char(2)          | YES  |     | NULL                |       |
| Duration   | timestamp        | YES  |     | 0000-00-00 00:00:00 |       |
| Dur_ff     | char(2)          | YES  |     | NULL                |       |
+------------+------------------+------+-----+---------------------+-------+

 */
public class SatbookBkngMaterialRecord extends ValidatorForm  {

	String bkngId;
	String materialId;
	private String materialDesc;
	private String cassette;
	private String timeIn;
	private String timeInHH;
	private String timeInMM;
	private String timeInSS;
	private String timeInFF;
	private String timeOut;
	private String timeOutHH;
	private String timeOutMM;
	private String timeOutSS;
	private String timeOutFF;
	private String duration;
	private String durationHH;
	private String durationMM;
	private String durationSS;
	private String durationFF;
	private String duplication;

	public String getBkngId() {
		return bkngId;
	}

	public void setBkngId(String bkngId) {
		this.bkngId = bkngId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getCassette() {
		return cassette;
	}

	public void setCassette(String cassette) {
		this.cassette = cassette;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeInHH() {
		return timeInHH;
	}

	public void setTimeInHH(String timeInHH) {
		this.timeInHH = timeInHH;
	}

	public String getTimeInMM() {
		return timeInMM;
	}

	public void setTimeInMM(String timeInMM) {
		this.timeInMM = timeInMM;
	}

	public String getTimeInSS() {
		return timeInSS;
	}

	public void setTimeInSS(String timeInSS) {
		this.timeInSS = timeInSS;
	}

	public String getTimeInFF() {
		return timeInFF;
	}

	public void setTimeInFF(String timeInFF) {
		this.timeInFF = timeInFF;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getTimeOutHH() {
		return timeOutHH;
	}

	public void setTimeOutHH(String timeOutHH) {
		this.timeOutHH = timeOutHH;
	}

	public String getTimeOutMM() {
		return timeOutMM;
	}

	public void setTimeOutMM(String timeOutMM) {
		this.timeOutMM = timeOutMM;
	}

	public String getTimeOutSS() {
		return timeOutSS;
	}

	public void setTimeOutSS(String timeOutSS) {
		this.timeOutSS = timeOutSS;
	}

	public String getTimeOutFF() {
		return timeOutFF;
	}

	public void setTimeOutFF(String timeOutFF) {
		this.timeOutFF = timeOutFF;
	}

	public String getDuration() {

		int inHH, inMM, inSS, inFF;
		int outHH, outMM, outSS, outFF;
		int dHH, dMM, dSS, dFF;

		if (timeInHH != null) {
			inHH = Integer.parseInt(timeInHH);
		} else {
			inHH = 0;
		}
		if (timeInMM != null) {
			inMM = Integer.parseInt(timeInMM);
		} else {
			inMM = 0;
		}
		if (timeInSS != null) {
			inSS = Integer.parseInt(timeInSS);
		} else {
			inSS = 0;
		}
		if (timeInFF != null) {
			inFF = Integer.parseInt(timeInFF);
		} else {
			inFF = 0;
		}
		if (timeOutHH != null) {
			outHH = Integer.parseInt(timeOutHH);
		} else {
			outHH = 0;
		}
		if (timeOutMM != null) {
			outMM = Integer.parseInt(timeOutMM);
		} else {
			outMM = 0;
		}
		if (timeOutSS != null) {
			outSS = Integer.parseInt(timeOutSS);
		} else {
			outSS = 0;
		}
		if (timeOutFF != null) {
			outFF = Integer.parseInt(timeOutFF);
		} else {
			outFF = 0;
		}

		dHH = outHH - inHH;
		dMM = outMM - inMM;
		dSS = outSS - inSS;
		dFF = outFF - inFF;

		duration = dHH+":"+dMM+":"+dSS+":"+dFF;

		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDurationHH() {
		return durationHH;
	}

	public void setDurationHH(String durationHH) {
		this.durationHH = durationHH;
	}

	public String getDurationMM() {
		return durationMM;
	}

	public void setDurationMM(String durationMM) {
		this.durationMM = durationMM;
	}

	public String getDurationSS() {
		return durationSS;
	}

	public void setDurationSS(String durationSS) {
		this.durationSS = durationSS;
	}
	public String getDurationFF() {
		return durationFF;
	}

	public void setDurationFF(String durationFF) {
		this.durationFF = durationFF;
	}

	public String getDuplication() {
		return duplication;
	}

	public void setDuplication(String duplication) {
		this.duplication = duplication;
	}
}
