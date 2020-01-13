package za.ac.unisa.lms.tools.lmsmis.actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import za.ac.unisa.lms.tools.lmsmis.dao.misSakaiQueryDAO;
import za.ac.unisa.lms.tools.lmsmis.forms.misForm;
import za.ac.unisa.utils.CodeProfiler;

public class misAction extends LookupDispatchAction{

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
	    map.put("misoverview", "misoverview");
	    map.put("misAdminMonthly", "misAdminMonthly");
	    map.put("misAdminAnnual", "misAdminAnnual");
	    map.put("misTeachMonthly", "misTeachMonthly");
	    map.put("misTeachMonthlyCurrent","misTeachMonthlyCurrent");
	    map.put("misTeachAnnual", "misTeachAnnual");
	    map.put("misAdminMonthlyCurrent","misAdminMonthlyCurrent");

	    return map;
	}

	public ActionForward misoverview(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			Integer currentYear;
			Integer previousYear;
			String unisaDate1;
			Calendar rightNow = Calendar.getInstance();

			int mm = rightNow.get(Calendar.MONTH)+1;
			String Month;
			if (mm <= 9){
				Month = "0"+mm;
			}else{
				Month = ""+mm;
			}
			//enter a 0 if date less dan 9
			int dd = rightNow.get(Calendar.DAY_OF_MONTH);
			String Date;
			if (dd <= 9){
				Date = "0"+dd;
			}else{
				Date = ""+dd;
			}
			unisaDate1 = new Integer(rightNow.get(Calendar.YEAR)).toString()+"/"+Month+"/".toString()+Date.toString();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misform.setUnisaDate(unisaDate1);
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);

			String category = "gateway";
			String[] forYear = {previousYear.toString(), currentYear.toString()};

			String userType = "S"; //student

			// STUDENT JOIN
			ArrayList report = new ArrayList();
			try {
				profiler.start(request);
				misForm.setStudentsJoin(db.getAnnualStats("joined.students",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStats:Students join");
			}catch (Exception e){
				System.out.println("ERROR OCCURED "+e);
			}

			// STUDENT LOGIN
			userType = "S";
			try {
				profiler.start(request);
				misForm.setStudentsVisits(db.getAnnualStats("user.login",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStats:Students login");
			}catch (Exception e){
				System.out.println("ERROR OCCURED "+e);
			}

		/*	//Unique student logins
			userType = "S";
			try {
				profiler.start(request);
				misForm.setStudentsLogin(db.getAnnualStats("user.login.unique.annual",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStats:Unique student login");
			}catch (Exception e){
				System.out.println("ERROR OCCURED "+e);
			}*/
			//Unique student logins
			userType = "S";
			try {
				profiler.start(request);
				misForm.setActiveStudents(db.getAnnualStats1("active.students",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStatsz:Active student login");
			}catch (Exception e){
				System.out.println("ERROR OCCURED "+e);
			}
			//Total of lecturers visiting myUnisa
			userType = "L";
			try {
				profiler.start(request);
				misForm.setLecturerVisits(db.getAnnualStats("user.login",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStats:Lecturer visits");
			}catch (Exception e) {
				System.out.println("ERROR OCCURED "+e);
			}
			//Unique lecturer logins
			userType = "L";
			try {
				profiler.start(request);
				misForm.setLecturerLogins(db.getAnnualStats1("user.login.unique",forYear,category,userType));
				profiler.stop(request, "unisa.lmsmis misAction: getAnnualStats1:Unique lecturer login");
			}catch (Exception e) {
				System.out.println("ERROR OCCURED "+e);
			}
			return mapping.findForward("misoverview");
	}

	public ActionForward misAdminMonthly(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			Integer currentYear;
			Integer previousYear;
			Integer selectedYear;
			String unisaDate1;
			Calendar rightNow = Calendar.getInstance();
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			//unisaDate1 = new Integer(rightNow.get(Calendar.YEAR)).toString()+"/"+new Integer (rightNow.get(Calendar.MONTH)).toString()+"/"+new Integer (rightNow.get(Calendar.DATE)).toString();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			selectedYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			//misform.setUnisaDate(unisaDate1);
			misform.setSelectedYear(selectedYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;

			String category ="admin";
			String[] forYear = {previousYear.toString()};

			//myUnisa password changes
			try {
				profiler.start(request);
				misForm.setPasswordChanges(db.getMonthlyStats("changepassword.change",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:myUnisa password changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of registration details
			try {
				profiler.start(request);
				misForm.setRegistrationDetails(db.getMonthlyStats("regdetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of registration details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course addition transaction
			try {
				profiler.start(request);
				misForm.setAddTransactions(db.getMonthlyStats("regdetails.addition",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course addition transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course Cancellation transaction
			try {
				profiler.start(request);
				misForm.setCancelTransactions(db.getMonthlyStats("regdetails.cancellation",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course cancellation transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course semester change transaction
			try {
				profiler.start(request);
				misForm.setSemesterChanges(db.getMonthlyStats("regdetails.semesterexchange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course semester change transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of emails send to lectures
			try {
				profiler.start(request);
				misForm.setEmailsLecturer(db.getMonthlyStats("studentemail.sendmail",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of emails send to lecturers");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of classlist updates
			try {
				profiler.start(request);
				misForm.setClassUpdates(db.getMonthlyStats("classlist.update",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of classlist updates");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//View of academic record
			try {
				profiler.start(request);
				misForm.setAcademicRecord(db.getMonthlyStats("acadhistory.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:View of academic record");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of biographical detail
			try {
				profiler.start(request);
				misForm.setBioDetails(db.getMonthlyStats("biodetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of biographical detail");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of direct address changes
			try {
				profiler.start(request);
				misForm.setDirectAddress(db.getMonthlyStats("biodetails.addresschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of direct changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of indirect address changes (via workflow)
			try {
				profiler.start(request);
				misForm.setIndirectAddress(db.getMonthlyStats("biodetails.addresschangeworkflow",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of indirect address changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of contact detial changes
			try {
				profiler.start(request);
				misForm.setContactChanges(db.getMonthlyStats("biodetails.contactdetailschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of contact detial chnages");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of correspondence option changes
			try {
				profiler.start(request);
				misForm.setOptionChanges(db.getMonthlyStats("biodetails.correspondencechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of correspondence option changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of exam center changes
			try {
				profiler.start(request);
				misForm.setCentreChanges(db.getMonthlyStats("biodetails.examcentrechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of exam center changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of credit card payments
			try {
				profiler.start(request);
				misForm.setCardPayments(db.getMonthlyStats("creditcard.payment",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of credit card payments");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of current financial details
			try {
				profiler.start(request);
				misForm.setCurrentFinancial(db.getMonthlyStats("payments.viewcurrent",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of current financial details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of historic financial details
			try {
				profiler.start(request);
				misForm.setHistoricFinancial(db.getMonthlyStats("payments.viewhistory",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of historic financial details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of study fee quotation
			try {
				profiler.start(request);
				misForm.setFeeQuotation(db.getMonthlyStats("studyquotation.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of study fee quotation");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of parcel information
			try {
				profiler.start(request);
				misForm.setParcelInformation(db.getMonthlyStats("trackandtrace.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of parcel information");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop
			try {
				profiler.start(request);
				misForm.setEbookShop(db.getMonthlyStats("ebookshop.view",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop additions
			try {
				profiler.start(request);
				misForm.setEbookshopAdditions(db.getMonthlyStats("ebookshop.add",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop additions");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop edits
			try {
				profiler.start(request);
				misForm.setShopEdits(db.getMonthlyStats("ebookshop.edit",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop edits");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop deleted
			try {
				profiler.start(request);
				misForm.setShopDeleted(db.getMonthlyStats("ebookshop.delete",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop deleted");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster cd's written 
			try {
				profiler.start(request);
				misForm.setToasterWrites(db.getMonthlyStats("freedomtoaster.write",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: Number of toaster cd's written ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster attempts failed  
			try {
				profiler.start(request);
				misForm.setToasterNoWrites(db.getMonthlyStats("freedomtoaster.nowrite",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: number of toaster attempts failed ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster attempts started but not finished
			try {
				profiler.start(request);
				misForm.setToasterCancels(db.getMonthlyStats("freedomtoaster.cancel",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: Number of toaster attempts started but not finished ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}

			return mapping.findForward("misadminmonthly");
	}
	public ActionForward misAdminMonthlyCurrent(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			Integer currentYear;
			Integer previousYear;
			Integer selectedYear;
			Calendar rightNow = Calendar.getInstance();
			selectedYear = new Integer(rightNow.get(Calendar.YEAR));
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setSelectedYear(selectedYear.toString());
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;

			String category ="admin";
			String[] forYear = {currentYear.toString(),previousYear.toString()};
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			try {
				profiler.start(request);
				misForm.setPasswordChanges(db.getMonthlyStats("changepassword.change",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Password changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of registration details
			try {
				profiler.start(request);
				misForm.setRegistrationDetails(db.getMonthlyStats("regdetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of registration details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course addition transaction
			try {
				profiler.start(request);
				misForm.setAddTransactions(db.getMonthlyStats("regdetails.addition",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course addition transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course Cancellation transaction
			try {
				profiler.start(request);
				misForm.setCancelTransactions(db.getMonthlyStats("regdetails.cancellation",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course Cancellation transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course semester change transaction
			try {
				profiler.start(request);
				misForm.setSemesterChanges(db.getMonthlyStats("regdetails.semesterexchange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Course semester change transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of emails send to lectures
			try {
				profiler.start(request);
				misForm.setEmailsLecturer(db.getMonthlyStats("studentemail.sendmail",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of e-mails send to lectures");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of classlist updates
			try {
				profiler.start(request);
				misForm.setClassUpdates(db.getMonthlyStats("classlist.update",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of classlist updates");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//View of academic record
			try {
				profiler.start(request);
				misForm.setAcademicRecord(db.getMonthlyStats("acadhistory.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:View of academic record");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of biograpical detail
			try {
				profiler.start(request);
				misForm.setBioDetails(db.getMonthlyStats("biodetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of biographical detail");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of direct address changes
			try {
				profiler.start(request);
				misForm.setDirectAddress(db.getMonthlyStats("biodetails.addresschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of direct address changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of indirect address changes (via workflow)
			try {
				profiler.start(request);
				misForm.setIndirectAddress(db.getMonthlyStats("biodetails.addresschangeworkflow",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of indirect address changes(via workflow");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of contact detial changes
			try {
				profiler.start(request);
				misForm.setContactChanges(db.getMonthlyStats("biodetails.contactdetailschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of contact detail changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of correspondence option changes
			try {
				profiler.start(request);
				misForm.setOptionChanges(db.getMonthlyStats("biodetails.correspondencechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of correspondence option changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of exam center changes
			try {
				profiler.start(request);
				misForm.setCentreChanges(db.getMonthlyStats("biodetails.examcentrechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of exam centre changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of credit card payments
			try {
				profiler.start(request);
				misForm.setCardPayments(db.getMonthlyStats("creditcard.payment",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Number of credit card payments");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of current financial details
			try {
				profiler.start(request);
				misForm.setCurrentFinancial(db.getMonthlyStats("payments.viewcurrent",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of current financial details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of historic financial details
			try {
				profiler.start(request);
				misForm.setHistoricFinancial(db.getMonthlyStats("payments.viewhistory",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of historic financial details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of study fee quotation
			try {
				profiler.start(request);
				misForm.setFeeQuotation(db.getMonthlyStats("studyquotation.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of study quotation");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of parcel information
			try {
				profiler.start(request);
				misForm.setParcelInformation(db.getMonthlyStats("trackandtrace.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of parcel information");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop
			try {
				profiler.start(request);
				misForm.setEbookShop(db.getMonthlyStats("ebookshop.view",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop additions
			try {
				profiler.start(request);
				misForm.setEbookshopAdditions(db.getMonthlyStats("ebookshop.add",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop additions");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop edits
			try {
				profiler.start(request);
				misForm.setShopEdits(db.getMonthlyStats("ebookshop.edit",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop edits");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop deleted
			try {
				profiler.start(request);
				misForm.setShopDeleted(db.getMonthlyStats("ebookshop.delete",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats:Views of ebookshop deleted");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster cd's written 
			try {
				profiler.start(request);
				misForm.setToasterWrites(db.getMonthlyStats("freedomtoaster.write",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: Number of toaster cd's written ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster attempts failed  
			try {
				profiler.start(request);
				misForm.setToasterNoWrites(db.getMonthlyStats("freedomtoaster.nowrite",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: number of toaster attempts failed ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//number of toaster attempts started but not finished
			try {
				profiler.start(request);
				misForm.setToasterCancels(db.getMonthlyStats("freedomtoaster.cancel",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getMonthlyStats: Number of toaster attempts started but not finished ");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			return mapping.findForward("misadminmonthly");
	}
	public ActionForward misAdminAnnual(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			Integer currentYear;
			Integer previousYear;
			Calendar rightNow = Calendar.getInstance();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;

			String category ="admin";
			String[] forYear = {previousYear.toString(), currentYear.toString()};
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			//myUnisa password changes
			try {
				profiler.start(request);
				misForm.setPasswordChanges(db.getmisAdminAnnual("changepassword.change",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of ebookshop deleted");
			}catch (Exception e){
			System.out.println("ERROR OCCURED"+e);
			}
			//Views of registration details
			try {
				profiler.start(request);
				misForm.setRegistrationDetails(db.getmisAdminAnnual("regdetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of registration details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course addition transaction
			try {
				profiler.start(request);
				misForm.setAddTransactions(db.getmisAdminAnnual("regdetails.addition",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Course of registration derails");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course Cancellation transaction
			try {
				profiler.start(request);
				misForm.setCancelTransactions(db.getmisAdminAnnual("regdetails.cancellation",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Course cancellation transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Course semester change transaction
			try {
				profiler.start(request);
				misForm.setSemesterChanges(db.getmisAdminAnnual("regdetails.semesterexchange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Course semester change transaction");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of emails send to lectures
			try {
				profiler.start(request);
				misForm.setEmailsLecturer(db.getmisAdminAnnual("studentemail.sendmail",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of e-mail send to lectures");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of classlist updates
			try {
				profiler.start(request);
				misForm.setClassUpdates(db.getmisAdminAnnual("classlist.update",forYear,""));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of classlist updates");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//View of academic record
			try {
				profiler.start(request);
				misForm.setAcademicRecord(db.getmisAdminAnnual("acadhistory.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:View of academic record");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of biograpical detail
			try {
				profiler.start(request);
				misForm.setBioDetails(db.getmisAdminAnnual("biodetails.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of biographical detail");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of direct address changes
			try {
				profiler.start(request);
				misForm.setDirectAddress(db.getmisAdminAnnual("biodetails.addresschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of direct address changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of indirect address changes (via workflow)
			try {
				profiler.start(request);
				misForm.setIndirectAddress(db.getmisAdminAnnual("biodetails.addresschangeworkflow",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of indirect address changes(via workflow)");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of contact detial changes
			try {
				profiler.start(request);
				misForm.setContactChanges(db.getmisAdminAnnual("biodetails.contactdetailschange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of contact details changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of correspondence option changes
			try {
				profiler.start(request);
				misForm.setOptionChanges(db.getmisAdminAnnual("biodetails.correspondencechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of correspondence option changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of exam center changes
			try {
				profiler.start(request);
				misForm.setCentreChanges(db.getmisAdminAnnual("biodetails.examcentrechange",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of exam center changes");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of credit card payments
			try {
				profiler.start(request);
				misForm.setCardPayments(db.getmisAdminAnnual("creditcard.payment",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Number of credit card payments");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of current financial details
			try {
				profiler.start(request);
				misForm.setCurrentFinancial(db.getmisAdminAnnual("payments.viewcurrent",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of current financial details");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of historic financial details
			try {
				profiler.start(request);
				misForm.setHistoricFinancial(db.getmisAdminAnnual("payments.viewhistory",forYear,category));
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of study fee quotation
			try {
				misForm.setFeeQuotation(db.getmisAdminAnnual("studyquotation.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of study fee quotation");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of parcel information
			try {
				profiler.start(request);
				misForm.setParcelInformation(db.getmisAdminAnnual("trackandtrace.view",forYear,category));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of parcel information");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop
			try {
				profiler.start(request);
				misForm.setEbookShop(db.getmisAdminAnnual("ebookshop.view",forYear,"gateway"));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of ebookshop");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop additions
			try {
				profiler.start(request);
				misForm.setEbookshopAdditions(db.getmisAdminAnnual("ebookshop.add",forYear,"gateway"));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of ebookshop additions");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop edits
			try {
				profiler.start(request);
				misForm.setShopEdits(db.getmisAdminAnnual("ebookshop.edit",forYear,"gateway"));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of ebooksop edits");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Views of ebookshop deleted
			try {
				profiler.start(request);
				misForm.setShopDeleted(db.getmisAdminAnnual("ebookshop.delete",forYear,"gateway"));
				profiler.stop(request, "unisa.lmsmis misAction: getmisAdminAnnual:Views of ebookshop deleted");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			return mapping.findForward("misadminannual");
	}
	public ActionForward misTeachMonthly(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			misform.setAction(misform.getAction());
			Integer currentYear;
			Integer previousYear;
			Integer selectedYear;
			Calendar rightNow = Calendar.getInstance();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			selectedYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misform.setSelectedYear(selectedYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;
			String[] forYear = {previousYear.toString(), currentYear.toString()};
			//Create an ArrayList
			ArrayList statsList = new ArrayList();
			ArrayList tmp2al = new ArrayList();
			ArrayList tmp3al = new ArrayList();
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			//Number message posted
			try{
				profiler.start(request);
				tmp2al = db.getmisTeachMonthly("discussionforums.addreply",forYear);
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number message posted");
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
				}
			try{
				profiler.start(request);
				tmp3al = db.getmisTeachMonthly("disc.new",forYear);
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:");
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
			}
			for (int i = 0; i < tmp2al.size(); i++) {
				LabelValueBean tmp2 = (LabelValueBean)tmp2al.get(i);
				LabelValueBean tmp3 = (LabelValueBean)tmp3al.get(i);

				String[] tmp2split = tmp2.getValue().split(" ");
				String trimmedString = "";
				for (int j = 0; j < tmp2split.length; j++){
				 trimmedString = trimmedString + tmp2split[j];
				}
				int tmp2i = Integer.parseInt(trimmedString);

				String[] tmp3split = tmp3.getValue().split(" ");
				trimmedString = "";
				for (int j = 0; j < tmp3split.length; j++){
				 trimmedString = trimmedString + tmp3split[j];
				}

				int tmp3i = Integer.parseInt(trimmedString);
				int totali = tmp2i+tmp3i;
				String s = "";
				String total = "";
				if (totali > 999) {
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
					s = formatter.format(totali);  // -001235
					total = s.replace(',',' ');
				} else {
					total = totali+"";
				}
				statsList.add(new org.apache.struts.util.LabelValueBean("disc",total));
			}
			misForm.setMessagePosted(statsList);

			//Number of FAQ items
			try {
				profiler.start(request);
				misForm.setItemsAdded(db.getmisTeachMonthly("faqs.contentadd",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of FAQ items");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of annoucement added
			try {
				profiler.start(request);
				misForm.setAnnouceAdded(db.getmisTeachMonthly("annc.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of announcement added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of bulk emails sent
			try {
				profiler.start(request);
				misForm.setEmailsSent(db.getmisTeachMonthly("bulkemail.send",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of bulk e-mails send");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of addtional calendar items added
			try {
				profiler.start(request);
				misForm.setCalendarItems(db.getmisTeachMonthly("calendar.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of additional calendar items added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list compiled
			try {
				profiler.start(request);
				misForm.setListCompiled(db.getmisTeachMonthly("studentlist.display",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list compiled");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list downloaded
			try {
				profiler.start(request);
				misForm.setListsDownloaded(db.getmisTeachMonthly("studentlist.download",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list downloaded");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of welcome message updated
			try {
				profiler.start(request);
				misForm.setMessageUpdated(db.getmisTeachMonthly("welcome.update",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of welcome message updated");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resources added
			try {
				profiler.start(request);
				misForm.setFileAdded(db.getmisTeachMonthly("content.new",forYear ));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of resources added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resources revised
			try {
				profiler.start(request);
				misForm.setWebsitesAdded(db.getmisTeachMonthly("content.revise",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of resources revised");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resource read
			try {
				profiler.start(request);
				misForm.setResourceRead(db.getmisTeachMonthly("content.read",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of resource read");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			return mapping.findForward("misteachmonthly");
	}

	public ActionForward misTeachMonthlyCurrent(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			misform.setAction(misform.getAction());
			Integer currentYear;
			Integer previousYear;
			Integer selectedYear;
			Calendar rightNow = Calendar.getInstance();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			selectedYear = new Integer(rightNow.get(Calendar.YEAR));
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misform.setSelectedYear(selectedYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;
			String[] forYear = {currentYear.toString(),previousYear.toString()};
			//Create an ArrayList
			ArrayList statsList = new ArrayList();
			ArrayList tmp2al = new ArrayList();
			ArrayList tmp3al = new ArrayList();
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			//Number message posted
			try{
				tmp2al = db.getmisTeachMonthly("discussionforums.addreply",forYear);
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
				}
			try{
				tmp3al = db.getmisTeachMonthly("disc.new",forYear);
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
			}
			for (int i = 0; i < (tmp2al.size()-1); i++) {
				LabelValueBean tmp2 = (LabelValueBean)tmp2al.get(i);
				LabelValueBean tmp3 = (LabelValueBean)tmp3al.get(i);

				String[] tmp2split = tmp2.getValue().split(" ");
				String trimmedString = "";
				for (int j = 0; j < tmp2split.length; j++){
				 trimmedString = trimmedString + tmp2split[j];
				}
				int tmp2i = 0;
				if ((trimmedString == null)||(trimmedString.length()== 0)) {
					tmp2i=0;
				} else {
					tmp2i = Integer.parseInt(trimmedString);
				}

				String[] tmp3split = tmp3.getValue().split(" ");
				trimmedString = "";
				for (int j = 0; j < tmp3split.length; j++){
				 trimmedString = trimmedString + tmp3split[j];
				}

				int tmp3i = 0;
				if ((trimmedString == null)||(trimmedString.length()== 0)) {
					tmp3i=0;
				} else {
					tmp3i = Integer.parseInt(trimmedString);
				}

				int totali = tmp2i+tmp3i;
				String s = "";
				String total ="";
				if (totali > 999) {
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
					s = formatter.format(totali);  // -001235
					total = s.replace(',',' ');
				}else{
					total = totali+"";
				}
				String currentMonth = "";
				currentMonth = new Integer (rightNow.get(Calendar.MONTH)).toString();
				if (new Integer(forYear[0]).intValue() == rightNow.get(Calendar.YEAR)){
					if (i > new Integer(currentMonth).intValue()+1){
						total = "";
					}
				}
				statsList.add(new org.apache.struts.util.LabelValueBean("disc",total));
			}
			misForm.setMessagePosted(statsList);
			//Number of FAQ items
			try {
				profiler.start(request);
				misForm.setItemsAdded(db.getmisTeachMonthly("faqs.contentadd",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of FAQ items");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of annoucement added
			try {
				profiler.start(request);
				misForm.setAnnouceAdded(db.getmisTeachMonthly("annc.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of annoucement added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of bulk emails sent
			try {
				profiler.start(request);
				misForm.setEmailsSent(db.getmisTeachMonthly("bulkemail.send",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of bulk e-mails send");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of addtional calendar items added
			try {
				profiler.start(request);
				misForm.setCalendarItems(db.getmisTeachMonthly("calendar.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of additional calendar items added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list compiled
			try {
				profiler.start(request);
				misForm.setListCompiled(db.getmisTeachMonthly("studentlist.display",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list compiled");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list downloaded
			try {
				profiler.start(request);
				misForm.setListsDownloaded(db.getmisTeachMonthly("studentlist.download",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list downloaded");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of welcome message updated
			try {
				profiler.start(request);
				misForm.setMessageUpdated(db.getmisTeachMonthly("welcome.update",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of welcome message updated");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resources added
			try {
				profiler.start(request);
				misForm.setFileAdded(db.getmisTeachMonthly("content.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of reources added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resources revised
			try {
				profiler.start(request);
				misForm.setWebsitesAdded(db.getmisTeachMonthly("content.revise",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of reources revised");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resource read
			try {
				profiler.start(request);
				misForm.setResourceRead(db.getmisTeachMonthly("content.read",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of resources read");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			return mapping.findForward("misteachmonthly");
	}
	public ActionForward misTeachAnnual(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			misForm misform = (misForm) form;
			Integer currentYear;
			Integer previousYear;
			Calendar rightNow = Calendar.getInstance();
			currentYear = new Integer(rightNow.get(Calendar.YEAR));
			previousYear = new Integer(rightNow.get(Calendar.YEAR)-1);
			misform.setCurrentYear(currentYear.toString());
			misform.setPreviousYear(previousYear.toString());
			misSakaiQueryDAO db = new misSakaiQueryDAO();
			misForm misForm = (misForm)form;
			String[] forYear = {previousYear.toString(), currentYear.toString()};
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			//Create an ArrayList
			ArrayList statsList = new ArrayList();
			ArrayList tmp2al = new ArrayList();
			ArrayList tmp3al = new ArrayList();
			//Number message posted
			try{
				tmp2al = db.getmisTeachAnnual("discussionforums.addreply",forYear);
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
				}
			try{
				tmp3al = db.getmisTeachAnnual("disc.new",forYear);
				}catch (Exception e) {
					System.out.println("ERROR OCCURED"+e);
			}
			for (int i = 0; i < tmp2al.size(); i++) {
				LabelValueBean tmp2 = (LabelValueBean)tmp2al.get(i);
				LabelValueBean tmp3 = (LabelValueBean)tmp3al.get(i);

				String[] tmp2split = tmp2.getValue().split(" ");
				String trimmedString = "";
				for (int j = 0; j < tmp2split.length; j++){
				 trimmedString = trimmedString + tmp2split[j];
				}
				int tmp2i = Integer.parseInt(trimmedString);

				String[] tmp3split = tmp3.getValue().split(" ");
				trimmedString = "";
				for (int j = 0; j < tmp3split.length; j++){
				 trimmedString = trimmedString + tmp3split[j];
				}

				int tmp3i = Integer.parseInt(trimmedString);
				int totali = tmp2i+tmp3i;
				String s = "";
				String total = "";
				if (totali > 999) {
					NumberFormat formatter = new DecimalFormat("###,###,###,###");
					s = formatter.format(totali);  // -001235
					total = s.replace(',',' ');
				}else{
					total =totali+"";
				}
				statsList.add(new org.apache.struts.util.LabelValueBean("disc",total));
			}
			misForm.setMessagePosted(statsList);
			//Number of FAQ items
			try {
				profiler.start(request);
				misForm.setItemsAdded(db.getmisTeachAnnual("faqs.contentadd",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachAnnual:Number of FAQ items");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of annoucement added
			try {
				profiler.start(request);
				misForm.setAnnouceAdded(db.getmisTeachAnnual("annc.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of annoucements added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of bulk emails sent
			try {
				profiler.start(request);
				misForm.setEmailsSent(db.getmisTeachAnnual("bulkemail.send",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of bulk e-mails send");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of additional calendar items added
			try {
				profiler.start(request);
				misForm.setCalendarItems(db.getmisTeachAnnual("calendar.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of additional calendar items added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list compiled
			try {
				profiler.start(request);
				misForm.setListCompiled(db.getmisTeachAnnual("studentlist.display",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list compiled");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of student list downloaded
			try {
				profiler.start(request);
				misForm.setListsDownloaded(db.getmisTeachAnnual("studentlist.download",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of student list downloaded");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of welcome message updated
			try {
				profiler.start(request);
				misForm.setMessageUpdated(db.getmisTeachAnnual("welcome.update",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of welcome message updated");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
//			Number of resources added
			try {
				profiler.start(request);
				misForm.setFileAdded(db.getmisTeachAnnual("content.new",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of resources added");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resources revised
			try {
				profiler.start(request);
				misForm.setWebsitesAdded(db.getmisTeachAnnual("content.revise",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of reources revised");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			//Number of resource read
			try {
				profiler.start(request);
				misForm.setResourceRead(db.getmisTeachAnnual("content.read",forYear));
				profiler.stop(request, "unisa.lmsmis misAction: getmisTeachMonthly:Number of reources read.");
			}catch (Exception e){
				System.out.println("ERROR OCCURED"+e);
			}
			return mapping.findForward("misteachannual");
	}
}