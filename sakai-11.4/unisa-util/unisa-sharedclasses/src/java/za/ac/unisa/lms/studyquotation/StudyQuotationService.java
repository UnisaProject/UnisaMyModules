package za.ac.unisa.lms.studyquotation;

import java.beans.PropertyVetoException;
import java.util.Vector;

import za.ac.unisa.exceptions.JavaProxyExceptionListener;
import Srrqn01h.Abean.Srrqn01sQuoteStudyFees;

public class StudyQuotationService {
	private int count;
	private int exitStateType;
	private String errorMessage;
	private String exitStateMessage;
	private StudyQuotation studyQuotation;
	private JavaProxyExceptionListener exception;
	private Srrqn01sQuoteStudyFees srrqn01sQuoteStudyFees;
	
	public static final int QUAL_CODE_MISSING = 0;
	public static final int COOLGEN_ERROR = 1;
	public static final int NO_ERRORS = 2;

	public StudyQuotationService() {
		exception = new JavaProxyExceptionListener();
		srrqn01sQuoteStudyFees = new Srrqn01sQuoteStudyFees();
		srrqn01sQuoteStudyFees.addExceptionListener(exception);
	}
	
	private void init() throws PropertyVetoException {
		srrqn01sQuoteStudyFees.clear();
		srrqn01sQuoteStudyFees.setInWsUserNumber(9999);
		srrqn01sQuoteStudyFees.setInWsWorkstationCode("internet");
		srrqn01sQuoteStudyFees.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		srrqn01sQuoteStudyFees.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		srrqn01sQuoteStudyFees.setInCsfClientServerCommunicationsAction("P");
		srrqn01sQuoteStudyFees.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		srrqn01sQuoteStudyFees.setInStudentAnnualRecordMkStudentNr(0);
		srrqn01sQuoteStudyFees.setInWsStudentSurname("A");
		srrqn01sQuoteStudyFees.setInWsStudentInitials("A");
		srrqn01sQuoteStudyFees.setInWsStudentMkCorrespondenceLanguage("E");
		srrqn01sQuoteStudyFees.setInWsAddressPostalCode((short) 1);
	}
	
	public int calculateStudyQuotation() throws PropertyVetoException, Exception {
		init();
		srrqn01sQuoteStudyFees.setInStudentAnnualRecordMkAcademicYear(studyQuotation.getAcademicYear());
		
		//if ((studyQuotation.getQualification().equalsIgnoreCase("99999")) && (studyQuotation.getQualificationCode().equalsIgnoreCase("00000"))) {
			if ((studyQuotation.getQualification().equalsIgnoreCase("99999")) && (studyQuotation.getQualificationCode().equalsIgnoreCase(""))) {
			studyQuotation.reset();
			return QUAL_CODE_MISSING;
		//} else if ((studyQuotation.getQualification().equalsIgnoreCase("99999")) && (!studyQuotation.getQualificationCode().equalsIgnoreCase("00000"))) {
		} else if ((studyQuotation.getQualification().equalsIgnoreCase("99999")) && (!studyQuotation.getQualificationCode().equalsIgnoreCase(""))) {
			srrqn01sQuoteStudyFees.setInStudentAcademicRecordMkQualificationCode(
				studyQuotation.getQualificationCode()
			);
			studyQuotation.setQualification(studyQuotation.getQualificationCode());
		} else {
			srrqn01sQuoteStudyFees.setInStudentAcademicRecordMkQualificationCode(
				studyQuotation.getQualification()
			);
		}
		
		srrqn01sQuoteStudyFees.setInWsCountryCode(studyQuotation.getCountryCode());
		srrqn01sQuoteStudyFees.setInSmartcardIefSuppliedFlag(studyQuotation.getLibraryCard().toString());
		srrqn01sQuoteStudyFees.setInMatrExemptionIefSuppliedFlag(studyQuotation.getMatricExemption());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(1, studyQuotation.getStudyCode1().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(2, studyQuotation.getStudyCode2().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(3, studyQuotation.getStudyCode3().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(4, studyQuotation.getStudyCode4().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(5, studyQuotation.getStudyCode5().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(6, studyQuotation.getStudyCode6().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(7, studyQuotation.getStudyCode7().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(8, studyQuotation.getStudyCode8().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(9, studyQuotation.getStudyCode9().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(10, studyQuotation.getStudyCode10().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(11, studyQuotation.getStudyCode11().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(12, studyQuotation.getStudyCode12().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(13, studyQuotation.getStudyCode13().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(14, studyQuotation.getStudyCode14().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(15, studyQuotation.getStudyCode15().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(16, studyQuotation.getStudyCode16().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(17, studyQuotation.getStudyCode17().toUpperCase());
		srrqn01sQuoteStudyFees.setInGStudentStudyUnitMkStudyUnitCode(18, studyQuotation.getStudyCode18().toUpperCase());
		srrqn01sQuoteStudyFees.execute();
		
		String errorMessage = srrqn01sQuoteStudyFees.getOutCsfStringsString500();
		if (!"Error reading study unit cost information".equalsIgnoreCase(errorMessage)){
				setErrorMessage(errorMessage);
			if ((errorMessage != null) && (!errorMessage.equals(""))) {
				studyQuotation.reset();
				return COOLGEN_ERROR;
			}
		}
	
		setCount(srrqn01sQuoteStudyFees.getOutGroupCount());
		setExitStateType(srrqn01sQuoteStudyFees.getExitStateType());
		setExitStateMessage(srrqn01sQuoteStudyFees.getExitStateMsg());
		
		if (exception.getException() != null) {
			throw exception.getException();
		} else if (getExitStateType() < 3) {
			throw new Exception(getExitStateMessage());
		}
		
		Vector studyUnits = new Vector();
		for(int i=0; i < (count-1) ; i++){			    		
	    	StudyUnit studyUnit = new StudyUnit();
	    	studyUnit.setStudyUnitcode(srrqn01sQuoteStudyFees.getOutGInternetWsStudyUnitCode(i));
	    	studyUnit.setDescription(srrqn01sQuoteStudyFees.getOutGInternetWsStudyUnitEngShortDescription(i));
	    	studyUnit.setFee(srrqn01sQuoteStudyFees.getOutGStudyUnitCostIefSuppliedTotalCurrency(i));
	    	studyUnits.add(studyUnit);
	    }
	    studyQuotation.setStudyUnits(studyUnits);
	    studyQuotation.setForeignLevy(srrqn01sQuoteStudyFees.getOutForeignLevyIefSuppliedTotalCurrency());
	    studyQuotation.setLibraryCardCost(srrqn01sQuoteStudyFees.getOutSmartcardIefSuppliedTotalCurrency());
	    studyQuotation.setMatricExemptionCost(srrqn01sQuoteStudyFees.getOutMatrExemptionIefSuppliedTotalCurrency());
	    studyQuotation.setTotalFee(srrqn01sQuoteStudyFees.getOutTotalIefSuppliedTotalCurrency());
	    studyQuotation.setPaymentDue(srrqn01sQuoteStudyFees.getOutRegPaymentIefSuppliedTotalCurrency());
	    studyQuotation.setPrescribedBooks(srrqn01sQuoteStudyFees.getOutWsPrescribedBooksAmount());
		
		return NO_ERRORS;
	}

	public StudyQuotation getStudyQuotation() {
		return studyQuotation;
	}
	public void setStudyQuotation(StudyQuotation studyQuotation) {
		this.studyQuotation = studyQuotation;
	}

	public JavaProxyExceptionListener getException() {
		return exception;
	}

	public Srrqn01sQuoteStudyFees getSrrqn01sQuoteStudyFees() {
		return srrqn01sQuoteStudyFees;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public String getExitStateMessage() {
		return exitStateMessage;
	}
	public void setExitStateMessage(String exitStateMessage) {
		this.exitStateMessage = exitStateMessage;
	}

	public int getExitStateType() {
		return exitStateType;
	}
	public void setExitStateType(int exitStateType) {
		this.exitStateType = exitStateType;
	}
}