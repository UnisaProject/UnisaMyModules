package za.ac.unisa.lms.domain.processDrivenSms;

import java.util.List;

public class SmsRequestResult {
	private int batchNr;
	private int userDeptCode;
	private int smsSendCount;
	private List<SmsRecipientResult> listRecipient;
	private String errMsg;
	
	public int getUserDeptCode() {
		return userDeptCode;
	}
	public void setUserDeptCode(int userDeptCode) {
		this.userDeptCode = userDeptCode;
	}
	public int getBatchNr() {
		return batchNr;
	}
	public void setBatchNr(int batchNr) {
		this.batchNr = batchNr;
	}
	public int getSmsSendCount() {
		return smsSendCount;
	}
	public void setSmsSendCount(int smsSendCount) {
		this.smsSendCount = smsSendCount;
	}	
	public List<SmsRecipientResult> getListRecipient() {
		return listRecipient;
	}
	public void setListRecipient(List<SmsRecipientResult> listRecipient) {
		this.listRecipient = listRecipient;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
