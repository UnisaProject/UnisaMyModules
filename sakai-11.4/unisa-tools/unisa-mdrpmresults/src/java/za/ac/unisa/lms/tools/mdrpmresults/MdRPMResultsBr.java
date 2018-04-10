package za.ac.unisa.lms.tools.mdrpmresults;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.mdrpmresults.dao.MdRPMResultsQueryDAO;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Qualification;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Staff;

public class MdRPMResultsBr {


	/**
	 * This method checks if the logged in user is the supervisor of the student
	 * @param supervisors
	 * 		  The supervisors
	 * @param loggedInUser
	 * 		  The logged in user
	 * @return
	 * 		<code>true</code> OR
	 * 		<code>false</code>
	 */
	public static boolean isStudentSupervisor(
										ArrayList<Staff> supervisors, 
										String loggedInUser)
	{
		Staff supervisor =  null;
		if (supervisors != null && !supervisors.isEmpty())
		{
			Iterator<Staff> it =  supervisors.iterator();
			while(it.hasNext())
			{
				supervisor = it.next();
				
				if (supervisor.getStaffNr().equals(loggedInUser) &&
					supervisor.getIsSupervisor() != null && 
					Constants.YES_CODE.equals(supervisor.getIsSupervisor()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * This method returns results options to be displayed as check-boxes
	 * 
	 * @return
	 * 		ArrayList<LabelValueBean>
	 */
	public static ArrayList<LabelValueBean> getResults()
	{
		ArrayList<LabelValueBean> results = new ArrayList<LabelValueBean>();
		
		results.add(0, new LabelValueBean(Constants.COMPLIED_RESULTS_DESC, 
									      Constants.COMPLIED_RESULTS_CODE));
		results.add(1, new LabelValueBean(Constants.NOT_COMPLIED_RESULTS_DESC,
										  Constants.NOT_COMPLIED_RESULTS_CODE));
		results.add(2, new LabelValueBean(Constants.FAILED_RESULTS_DESC, 
										  Constants.FAILED_RESULTS_CODE));
		results.add(3, new LabelValueBean(Constants.NOACTIVITY_RESULTS_DESC,
										  Constants.NOACTIVITY_RESULTS_CODE));
		
		return results;
	}
	
	/**
	 * This method returns final sign-off or review options to be displayed as 
	 * checkboxes
	 * 
	 * @return
	 * 		ArrayList<LabelValueBean>
	 */
	public static ArrayList<LabelValueBean> getReviewOrFinalValues()
	{
		ArrayList<LabelValueBean> values = new ArrayList<LabelValueBean>();
		
		values.add(0, new LabelValueBean(Constants.REVIEW_DESC, 
									      Constants.REVIEW_CODE));
		values.add(1, new LabelValueBean(Constants.FINAL_SIGNOFF_DESC,
										  Constants.FINAL_SIGNOFF_CODE));
		
		return values;
	}
	
	public static final String getFinalSignOffCode(String selectedResult)
	{
		String finalSginoff = null;
		
		if (Constants.COMPLIED_RESULTS_CODE.equals(selectedResult))
		{
			finalSginoff = Constants.COMPLIED_FINAL_SIGNOFF_CODE;			
		}else if (Constants.NOT_COMPLIED_RESULTS_CODE.equals(selectedResult))
		{
			finalSginoff = Constants.NOT_COMPLIED_FINAL_SIGNOFF_CODE;
		}else if (Constants.FAILED_RESULTS_CODE.equals(selectedResult))
		{
			finalSginoff = Constants.FAILED_FINAL_SIGNOFF_CODE;
		}else if (Constants.NOACTIVITY_RESULTS_CODE.equals(selectedResult))
		{
			finalSginoff = Constants.NOACTIVITY_FINAL_SIGNOFF_CODE;
		}
		
		return finalSginoff;
		
	}
	
	public static final String getSignOffResultDesc(String statusCode)
	{
		String statusDesc = null;
		
		if (Constants.COMPLIED_RESULTS_CODE.equals(statusCode))
		{
			statusDesc = Constants.COMPLIED_RESULTS_DESC;			
		}else if (Constants.NOT_COMPLIED_RESULTS_CODE.equals(statusCode))
		{
			statusDesc = Constants.NOT_COMPLIED_RESULTS_DESC;
		}else if (Constants.FAILED_RESULTS_CODE.equals(statusCode))
		{
			statusDesc = Constants.FAILED_RESULTS_DESC;
		}else if (Constants.NOACTIVITY_RESULTS_CODE.equals(statusCode))
		{
			statusDesc = Constants.NOACTIVITY_RESULTS_DESC;
		}
		else
		{
			statusDesc = statusCode;
		}
		
		return statusDesc;
		
	}

	/**
	 * Checks if the logged in user is in the routing list for the specified 
	 * qualification
	 * 
	 * @param staffNr
	 * 			The staff number of the logged in user
	 * @param qualification
	 * 			Student's qualification details
	 * @return
	 */
	public static boolean isUserInRoutingList(
								String staffNr, 
								Qualification qualification) 
	{
		MdRPMResultsQueryDAO dao = new MdRPMResultsQueryDAO();
		Staff staff = dao.getRoutingRecord(staffNr, qualification);
		
		if (staff != null){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the logged in user is the final signer for the specified 
	 * qualification
	 * 
	 * @param staffNr
	 * 			The staff number of the logged in user
	 * @param qualification
	 * 			Student's qualification details
	 * @return
	 */
	public static boolean isFinalSignOff(
								String staffNr, 
								Qualification qualification) 
	{
		MdRPMResultsQueryDAO dao = new MdRPMResultsQueryDAO();
		Staff staff = dao.getRoutingRecord(staffNr, qualification);
		
		if (staff != null && Constants.FINAL_FLAG.equals(staff.getFinalFlag())){
			return true;
		}
		return false;
	}
	
	public static Staff getNextRecipient(ArrayList<Staff> routingList, boolean isReviewSelected, Staff loggedInUser )
	{
		String nextFinalFlag = null;
		boolean decrement = false;
		
		if (isReviewSelected)
		{
			decrement = true;
		}
		else {
			decrement = false;
		}

		nextFinalFlag = getNextFinalFlag(routingList.size(), loggedInUser.getFinalFlag(), decrement);
		Staff nextStaff = getNextRoutingRecipient(routingList, nextFinalFlag); 
		return nextStaff;
	}
	
	private static String getNextFinalFlag(int routingListSize, String currentFinalFlag, boolean decrement)
	{		
		int val = 0;
		if (isNumeric(currentFinalFlag))
		{
			val = Integer.parseInt(currentFinalFlag);
			if (decrement)
			{
				val -= val;
			}
			else
			{
				val += val;
			}
		}else{

			if (decrement)
			{
				val = routingListSize - 1;
			}
		}		
		
		return String.valueOf(val);
	}
	
	private static boolean isNumeric(String testString){

		boolean result = true;

		try{
			Integer.parseInt(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;

	}
	
    /**
     * Returns the next staff on the routing list to refer the application to.
     * 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user finalFlag
     * @return Staff
     * 		Next staff to refer the application to 
     */
    private static Staff getNextRoutingRecipient(ArrayList<Staff> routingList, String nextFinalFlag)
    {   
    	    	
    	Staff nextStaff = null;
    	for (Staff staff: routingList)
    	{
    		if (staff.getFinalFlag().equals(nextFinalFlag))
    		{    			
    			nextStaff = staff;    			
    			break;
    		}
    	}    
    	return nextStaff;
    }
	
	public static boolean isPrevResultsExists(
								Staff prevSignOffRecord)
	{
		if (prevSignOffRecord != null && 
			prevSignOffRecord.getSignOffStatus() != null)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isReviewSelected(String selectedReviewOrFinal)
	{
		if (selectedReviewOrFinal != null && 
			Constants.REVIEW_CODE.equalsIgnoreCase(selectedReviewOrFinal))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isFinalSelected(String selectedReviewOrFinal)
	{
		if (selectedReviewOrFinal != null && 
			Constants.FINAL_SIGNOFF_CODE.equalsIgnoreCase(selectedReviewOrFinal))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isUserSameAsPrevSignee(
										Staff prevSignOffRecord, 
										Staff loggedInuser)
	{
		
		if (loggedInuser != null && prevSignOffRecord != null &&
			loggedInuser.getStaffNr() != null && 
			prevSignOffRecord.getStaffNr().equals(loggedInuser.getStaffNr()))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isCurrentResultSameAsPrevResults(
			Staff prevSignOffRecord, 
			String currentResult)
	{
	
		if (prevSignOffRecord != null &&
			prevSignOffRecord.getSignOffStatus() != null && 
			prevSignOffRecord.getSignOffStatus().equals(currentResult))
		{
			return true;
		}
		return false;
	}
}

