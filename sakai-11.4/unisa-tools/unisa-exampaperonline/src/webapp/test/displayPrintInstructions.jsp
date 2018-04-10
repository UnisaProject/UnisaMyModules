<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>
 	


<%@page import="za.ac.unisa.lms.db.StudentSystemDAO"%>
<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineLog"%><sakai:html> 	
	<style>		
		@media Screen
		{
			h1 {font-size:14pt;width:100%;font-family:Verdana,Arial,Helvetica}
			h2 {font-size:12pt;font-weight:bold;font-family:Verdana,Arial,Helvetica}
			h3 {font-size:10pt;width:100%;font-family:Verdana,Arial,Helvetica}		
			h1, h2, h3, h4, h5, h6 { page-break-after:avoid;page-break-inside:avoid }
			table { page-break-inside:avoid;font-family:Verdana,Arial,Helvetica;font-size:9pt }
			body {background: white}
			#content #container, #container2  {width: 100%; margin: 0; float: none;}
		}	
		@media Print
		{	body {font-size:9pt;background: white}
			h1 {font-size:14pt;width:100%;font-family:Verdana,Arial,Helvetica}
			h2 {font-size:12pt;font-weight:bold;font-family:Verdana,Arial,Helvetica}
			h3 {font-size:10pt;width:100%;font-family:Verdana,Arial,Helvetica}
			td {height:20pt}
			h1, h2, h3, h4, h5, h6 { page-break-after:avoid;page-break-inside:avoid }
			table {page-break-inside:avoid;font-family:Verdana,Arial,Helvetica;font-size:9pt !important}
			.act {{display:none}
			.portletBody {padding: 0}
			#content #container, #container2  {width: 100%; margin: 0; float: none;}			
		}	
	</style>		
	<html:form action="/printInstruction">		
	<%@page import="java.util.*" %>
	<%@page import="java.util.List"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.dao.XtlogDao"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.Annexure"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.Xtloge"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.Xtlog"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.CoverDocket"%>
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.actions.PrintInstructionAction"%>	
	<%@page import="za.ac.unisa.lms.dao.StudentSystemGeneralDAO"%>	
	<%  
		List annexureList = new ArrayList();
	    ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
	    XtlogDao daoXtlog = new XtlogDao();
	    Xtloge log = new Xtloge();
	    PrintInstructionAction action = new PrintInstructionAction();
	    
	    //dateToPP = dao.getDateSendToPP();	
	    String dateToPP = " ";	
		String system="";		
		String paperColour=" ";
		String quantity=" ";
		String quantityMiscalc=" ";
		String dateReceivedDSAA=" ";
		String secondDateToPP=" ";
		String miscalcDateToPP=" ";
		String dateReceivedPP=" ";
		String studyUnit=" ";
		String paperNo="0";
		String year="0";
		String period="0";
		String periodDesc=" ";
		List listEquivalents;
		String strEquivalents=" ";
		String remarks=" ";
		if (request.getParameter("SYSTEM")==null || request.getParameter("SYSTEM").equalsIgnoreCase("XPO")){
			system="";
			%>
			<bean:define id="yearBean" name="examPaperOnlineForm" property="examYear"/>
			<bean:define id="periodBean" name="examPaperOnlineForm" property="examPeriod"/>
			<bean:define id="studyUnitBean" name="examPaperOnlineForm" property="studyUnit"/>
			<bean:define id="paperNoBean" name="examPaperOnlineForm" property="paperNo"/>
			<bean:define id="periodDescBean" name="examPaperOnlineForm" property="examPeriodDesc"/>
			<%
			studyUnit=studyUnitBean.toString();
			paperNo=paperNoBean.toString();
			year=yearBean.toString();
			period=periodBean.toString();
			periodDesc=periodDescBean.toString();
			
			
		} else {
			system=request.getParameter("SYSTEM").toString();
			year=request.getParameter("YEAR").toString();
			period=request.getParameter("PERIOD").toString();
			studyUnit=request.getParameter("MODULE").toString();
			paperNo=request.getParameter("NO").toString();			
			StudentSystemGeneralDAO daoStudent = new StudentSystemGeneralDAO();
			periodDesc = daoStudent.getExamPeriodDesc(Short.parseShort(period),"long");
			
		}
		annexureList = dao.getPrintAnnexures(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
		log = daoXtlog.getXtloge(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
		CoverDocket coverDocket = new CoverDocket();		
		coverDocket = dao.getCoverDocket(year, 
				period,
				studyUnit,
				paperNo);
		listEquivalents = action.getEquivalents(year,period,studyUnit,paperNo);
		for (int i=0; i < listEquivalents.size(); i++){
			if (i > 0){
				strEquivalents = strEquivalents.trim() + ";";
			}
			strEquivalents = strEquivalents.trim() + " " + listEquivalents.get(i).toString().trim();
		}
		if (coverDocket!=null && coverDocket.getPaperColour()!=null){
			paperColour=coverDocket.getPaperColour();
		}
		if (log.getExamYear()!=null){ //xtloge record exists
			if (log.getDateToPrint()!=null && !log.getDateToPrint().equalsIgnoreCase("0001-01-01")){
				dateToPP=log.getDateToPrint();	
				if (!log.getQuantToPrint().equalsIgnoreCase("0")){
					quantity=log.getQuantToPrint();
				}
			}
			if (log.getDate2ToPrint()!=null && !log.getDate2ToPrint().equalsIgnoreCase("0001-01-01")){
				secondDateToPP=log.getDate2ToPrint();	
				if (!log.getQuant2ToPrint().equalsIgnoreCase("0")){
					quantity=log.getQuant2ToPrint();
				}
			}
			if (log.getDate3ToPrint()!=null && !log.getDate3ToPrint().equalsIgnoreCase("0001-01-01")){
				miscalcDateToPP=log.getDate3ToPrint();	
				if (!log.getQuant3ToPrint().equalsIgnoreCase("0")){
					quantityMiscalc=log.getQuant3ToPrint();
				}
			}
			if (log.getDateReceived()!=null && !log.getDateReceived().equalsIgnoreCase("0001-01-01")){
				dateReceivedDSAA=log.getDateReceived();
			}
			//Integer seqNr = dao.getLastLogSeqNrDocSendnotRetractedToRole(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo),"QUESTION","PRINT_PROD");
			if (log.getElectronicFlag()!=null && log.getElectronicFlag().equalsIgnoreCase("Y")){
				Integer seqNr = dao.getLastLogSeqNrForLastActionRole(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo),"QUESTION","SEND","PRINT_PROD","ACTIONED");
				ExamPaperOnlineLog xpoLog = new ExamPaperOnlineLog();
				if (seqNr!=0){
					xpoLog = dao.getExamPaperOnlineLog(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo),"QUESTION",seqNr);
					if (xpoLog!=null && xpoLog.getMessage()!=null){
						if (xpoLog.getMessage().length()>501){
							remarks = xpoLog.getMessage().substring(0,500);
						}else{
							remarks = xpoLog.getMessage();
						}		
					}	
				}
				
				seqNr = dao.getLastLogSeqNrForLastActionRole(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo),"QUESTION","RETRIEVE","PRINT_PROD","CURRENT");
				if (seqNr!=0){
					xpoLog = dao.getExamPaperOnlineLog(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo),"QUESTION",seqNr);
					if (xpoLog!=null && xpoLog.getUpdatedOn()!=null && !xpoLog.getUpdatedOn().equalsIgnoreCase("")){
						dateReceivedPP = xpoLog.getUpdatedOn().substring(0,16);
					}
				}
			} 
//				else {
//				Xtlog xtlog = new Xtlog();
//				xtlog = daoXtlog.getXtlog(Short.parseShort(year), Short.parseShort(period), studyUnit);
//				if (xtlog.getExamYear()!=null){ //xtlog exists
//					if (xtlog.getRemarks()!=null && !xtlog.getRemarks().trim().equalsIgnoreCase("")){
//						remarks = xtlog.getRemarks().trim();
//					}					
//					if (xtlog.getRemarks2()!=null && !xtlog.getRemarks2().trim().equalsIgnoreCase("")){
//						remarks = remarks + "<BR>" + xtlog.getRemarks2().trim();
//					}
//					if (xtlog.getRemarks3()!=null && !xtlog.getRemarks3().trim().equalsIgnoreCase("")){
//						remarks = remarks + "<BR>" + xtlog.getRemarks3().trim();
//					}
//					if (xtlog.getRemarks4()!=null && !xtlog.getRemarks4().trim().equalsIgnoreCase("")){
//						remarks = remarks + "<BR>" + xtlog.getRemarks4().trim();
//					}
//					if (xtlog.getRemarks5()!=null && !xtlog.getRemarks5().trim().equalsIgnoreCase("")){
//						remarks = remarks + "<BR>" + xtlog.getRemarks5().trim();
//					}
//					if (xtlog.getRemarks6()!=null && !xtlog.getRemarks6().trim().equalsIgnoreCase("")){
//						remarks = remarks + "<BR>" + xtlog.getRemarks6().trim();
//					}
//				}			
//			}
		}
	%>			
	<html:hidden property="currentPage" value="printInstructions"/>
	<html:hidden property="xpoAction" value=""/>
	<sakai:messages/>
	<sakai:messages message="true"/>	
	 <!-- <table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
		<tr>
			<td colspan="3" style="background-color:#C0C0C0"" align="center"><fmt:message key="printInstr.heading"/></td>
		</tr>
		<tr>
			<td style="width:40%">
				<table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
					<tr>
						<td style="width:50%"><fmt:message key="prompt.examination"/></td>
						<td style="width:50%"><%=periodDesc%>&nbsp;<%=year%>&nbsp;</td>
					</tr>
					<tr>
						<td style="width:50%"><fmt:message key="prompt.questionPaper"/></td>
						<td style="width:50%"><%=studyUnit%>&nbsp;<fmt:message key="prompt.paper"/>&nbsp;<%=paperNo%></td>
					</tr>
				</table>
			</td>
			<td style="width:10%">&nbsp;</td>
			<td style="width:40%">
				<table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
					<tr>
						<td style="width:50%"><fmt:message key="printInstr.quantity"/></td>
						<td style="width:50%"><%=quantity%>&nbsp;</td>
					</tr>
					<tr>
						<td style="width:50%"><fmt:message key="printInstr.dateToPP"/></td>
						<td style="width:50%"><%=dateToPP%>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>-->
	
	
	<table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
		<tr>
			<td colspan="4" style="background-color:#C0C0C0"" align="center"><fmt:message key="printInstr.heading"/></td>
		</tr>
		<tr>		
			<td style="width:25%"><fmt:message key="prompt.examination"/></td>
			<td colspan="3" style="width:75%"><%=periodDesc%>&nbsp;<%=year%>&nbsp;</td>			
		</tr>
		<tr>
			<td><fmt:message key="prompt.questionPaper"/></td>
			<td colspan="3" style="width:75%"><%=studyUnit%>&nbsp;<fmt:message key="prompt.paper"/>&nbsp;<%=paperNo%></td>			
		</tr>
		<tr>
			<td><fmt:message key="printInstr.combinedPapers"/></td>
			<td colspan="3" style="width:75%"><%=strEquivalents%>&nbsp;</td>			
		</tr>		
		<tr>
			<td><fmt:message key="printInstr.remarks"/><BR></BR>&nbsp;</td>
			<td colspan="3"><%=remarks%>&nbsp;</td>			
		</tr>		
		<tr>
			<td style="width:25%"><fmt:message key="printInstr.dateReceivedDSAA"/></td>
			<td style="width:25%"><%=dateReceivedDSAA%>&nbsp;</td>
			<td style="width:25%"><fmt:message key="printInstr.paperColour"/></td>
			<td style="width:25%"><%=paperColour%>&nbsp;</td>
		</tr>
		<tr>
			<td style="width:25%"><fmt:message key="printInstr.dateToPP"/></td>
			<td style="width:25%"><%=dateToPP%>&nbsp;</td>
			<td style="width:25%"><fmt:message key="printInstr.quantity"/></td>
			<td style="width:25%"><%=quantity%>&nbsp;</td>
		</tr>
		<tr>
			<td style="width:25%"><fmt:message key="printInstr.2ndDateToPP"/></td>
			<td style="width:25%"><%=secondDateToPP%>&nbsp;</td>
			<td style="width:25%"><fmt:message key="printInstr.miscalcDateToPP"/></td>
			<td style="width:25%"><%=miscalcDateToPP%>&nbsp;</td>
		</tr>
		<tr>
			<td style="width:25%"><fmt:message key="printInstr.dateReceivedAtPP"/></td>
			<td style="width:25%"><%=dateReceivedPP%>&nbsp;</td>
			<td style="width:25%"><fmt:message key="printInstr.quantityMiscalc"/></td>
			<td style="width:25%"><%=quantityMiscalc%>&nbsp;</td>
		</tr>			
	</table>
	<br></br>
	<table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
		<tr>
			<td colspan="4" style="background-color:#C0C0C0"" align="center"><fmt:message key="printInstr.annexures.heading"/></td>
		</tr>
		<!-- 
		<tr>
			<td colspan="2" style="background-color:#C0C0C0"" align="center"><fmt:message key="printInstr.annexures.specialMaterial"/></td>
			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.annexures.nrSheets"/></td>
		</tr> -->
			
		<%for(int i=0; i < annexureList.size(); i++) {
			Annexure annexure = new Annexure();
			annexure = (Annexure)annexureList.get(i);
		%>
			<tr>
				<td style="width:4%"><%=annexure.getPosition()%>&nbsp;</td>
				<td style="width:46%"><%=annexure.getDescription()%>&nbsp;</td>
				<td style="width:10%"><%=annexure.getResponsibility()%>&nbsp;</td>
				<td style="width:40%" align="center"><%=annexure.getAveragePerStudent()%>&nbsp;</td>
			</tr>
		<%}%>
			
	</table> 
	<br></br>
   	<table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
   		<tr>
   			<td rowspan="3" style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.PP"/></td>
   			<td rowspan="2" style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.camera"/></td>
   			<td rowspan="2" style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.printedBy"/></td>
   			<td colspan="4" style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.pages"/></td>
   			<td colspan="3" style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.quantity"/></td>
   		</tr><tr>	
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.film"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.paperTotal"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.docutech"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.total"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.d"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.s"/></td>
   			<td style="background-color:#C0C0C0" align="center"><fmt:message key="printInstr.collatingBy"/></td>
   		</tr><tr>	
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>	   			
   		</tr><tr>	
   			<td style="width:10%;background-color:#C0C0C0" align="center"><fmt:message key="printInstr.reprint"/></td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   			<td style="width:10%">&nbsp;</td>
   		</tr>
   </table>
   <br></br>
   <table border cellspacing="0" cellpadding="2" style="width:100%;border-style:solid;border-color:black">
		<tr>
				<td colspan="5" style="background-color:#C0C0C0" align="center">Quality Assurance at Print Production</td>
		</tr>
		<tr>
			<td colspan="2" style="width:50%;background-color:#C0C0C0" align="center">Activity</td>
			<td style="width:20%;background-color:#C0C0C0" align="center">Responsible person</td>
			<td style="width:10%;background-color:#C0C0C0" align="center">Date</td>
			<td style="width:20%;background-color:#C0C0C0" align="center">Signature</td>
		</tr>
		<tr>
			<td style="width:4%">1.</td>
			<td>Quality assurance procedures in place at PP (Quality of print work, collating of document, print volumes.)</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
		</tr>
		<tr>
			<td>2.</td>
			<td>Quality assurance at start of print run</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
		</tr>
		<tr>
			<td>3.</td>
			<td>Quality assurance during print run</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
		</tr>
		<tr>
			<td>4.</td>
			<td>Quality assurance on completion of print run</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
		</tr>
		<tr>
			<td>5.</td>
			<td>Quality assurance on completion of collating of question papers</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
		</tr>
	</table>
	<sakai:actions>	
		<html:button property="action" onclick="window.print()">
			<fmt:message key="button.printPage" />
		</html:button>
	   	<% if (system.equalsIgnoreCase("")){ %>
	   			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
				</html:submit>			
		<%}%>
	</sakai:actions>	
	<script language="javascript">			
		function onClickSetXpoActionDefault(){				
			document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
		}		
	</script>		
  	</html:form>
 </sakai:html>
  