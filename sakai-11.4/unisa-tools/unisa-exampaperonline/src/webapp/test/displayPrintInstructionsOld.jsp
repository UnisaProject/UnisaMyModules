<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>
 	


<%@page import="za.ac.unisa.lms.db.StudentSystemDAO"%><sakai:html> 	
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
	<%@page import="za.ac.unisa.lms.tools.exampaperonline.actions.PrintInstructionAction"%>	
	<%@page import="za.ac.unisa.lms.dao.StudentSystemGeneralDAO"%>	
	<%  
		String dateToPP;
		List annexureList = new ArrayList();
	    ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
	    XtlogDao daoXtlog = new XtlogDao();
	    Xtloge log = new Xtloge();
	    PrintInstructionAction action = new PrintInstructionAction();
	    
	    //dateToPP = dao.getDateSendToPP();	
	    dateToPP = " ";	
		String system="";		
		String quantity=" ";
		String studyUnit=" ";
		String paperNo="0";
		String year="0";
		String period="0";
		String periodDesc=" ";
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
			annexureList = dao.getPrintAnnexuresOld(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
			log = daoXtlog.getXtloge(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
			
		} else {
			system=request.getParameter("SYSTEM").toString();
			year=request.getParameter("YEAR").toString();
			period=request.getParameter("PERIOD").toString();
			studyUnit=request.getParameter("MODULE").toString();
			paperNo=request.getParameter("NO").toString();
			annexureList = dao.getPrintAnnexuresOld(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
			log = daoXtlog.getXtloge(Short.parseShort(year), Short.parseShort(period), studyUnit, Short.parseShort(paperNo));
			StudentSystemGeneralDAO daoStudent = new StudentSystemGeneralDAO();
			periodDesc = daoStudent.getExamPeriodDesc(Short.parseShort(period),"long");
		}	
		if (log!=null && !log.getDateToPrint().equalsIgnoreCase("00010101")){
			if (log.getDate2ToPrint().equalsIgnoreCase("00010101")){
				dateToPP=log.getDateToPrint();	
				if (!log.getQuantToPrint().equalsIgnoreCase("0")){
					quantity=log.getQuantToPrint();
				}
			}
			if (!log.getDate2ToPrint().equalsIgnoreCase("00010101")){
				dateToPP=log.getDate2ToPrint();				
			}	
			if (!log.getQuant2ToPrint().equalsIgnoreCase("0")){
				quantity=log.getQuant2ToPrint();
			}
		} 		
		
	%>			
	<html:hidden property="currentPage" value="printInstructions"/>
	<html:hidden property="xpoAction" value=""/>
	<sakai:messages/>
	<sakai:messages message="true"/>		
	<h3><fmt:message key="printInstr.heading"/></h3>
	<table style="width:100%">		
		<tr>
			<td style="width:40%"><fmt:message key="prompt.examination"/>&nbsp;:&nbsp;<%=periodDesc%>&nbsp;<%=year%>&nbsp;</td>
			<td style="width:20%">&nbsp;</td>
			<td style="width:20%" align="right"><fmt:message key="printInstr.quantity"/>&nbsp;</td>
			<td style="width:20%"><table style="border-style:solid;border-color:#C0C0C0" cellspacing="0" cellpadding="2" width="80%"><tr><td><%=quantity%>&nbsp;</td></tr></table></td>
		</tr>
			<tr>
			<td style="width:40%"><fmt:message key="prompt.questionPaper"/>&nbsp;:&nbsp;<%=studyUnit%>&nbsp;<fmt:message key="prompt.paper"/>&nbsp;<%=paperNo%></td>
			<td style="width:20%">&nbsp;</td>
			<td style="width:20%" align="right"><fmt:message key="printInstr.dateToPP"/>&nbsp;</td>
			<td style="width:20%"><table style="border-bottom-style:solid;border-bottom-color:#C0C0C0" cellspacing="0" cellpadding="0" width="80%"><tr><td><%=dateToPP%>&nbsp;</td></tr></table></td>
			</tr>
	</table>
	<h2><fmt:message key="printInstr.annexures"/></h2>
	<table style="width:600">
			
		<%for(int i=0; i < annexureList.size(); i++) {
			Annexure annexure = new Annexure();
			annexure = (Annexure)annexureList.get(i);
		%>
			<tr>
				<td style="width:20px"><%=annexure.getPosition()%>&nbsp;</td>
				<td style="width:380px"><%=annexure.getDescription()%>&nbsp;</td>
				<td style="width:200px">
					<table style="width:200px" border cellspacing="0" cellpadding="2" >
						<tr>
							<td style="width:100px;background-color:#C0C0C0" align="center"><%=annexure.getResponsibility()%>&nbsp;</td>
							<td style="width:100px" align="center"><%=annexure.getAveragePerStudent()%>&nbsp;</td>
						</tr>
					</table>
				</td>
				<!--<td width="15%"><%=annexure.getResponsibility()%></td>
				<td width="20%"><%=annexure.getAveragePerStudent()%></td>  -->
			</tr>
			<%}%>
			
	</table>
 	<!-- <table style="black;border-top-style:solid;border-top-color:#C0C0C0;black;border-bottom-style:solid;border-bottom-color:#C0C0C0" cellspacing="0" cellpadding="2" width="100%">
   	<tr><td></td></tr>
   	</table> -->
   	<hr></hr>
   	<hr></hr>
   	<br></br>
   	<table border cellspacing="0" cellpadding="2" width="100%">
   		<tr>
   			<th rowspan="3"><fmt:message key="printInstr.PP"/></th>
   			<th rowspan="2"><fmt:message key="printInstr.camera"/></th>
   			<th rowspan="2"><fmt:message key="printInstr.printedBy"/></th>
   			<th colspan="4"><fmt:message key="printInstr.pages"/></th>
   			<th colspan="3"><fmt:message key="printInstr.quantity"/></th>
   		</tr><tr>	
   			<th><fmt:message key="printInstr.film"/></th>
   			<th><fmt:message key="printInstr.paperTotal"/></th>
   			<th><fmt:message key="printInstr.docutech"/></th>
   			<th><fmt:message key="printInstr.total"/></th>
   			<th><fmt:message key="printInstr.d"/></th>
   			<th><fmt:message key="printInstr.s"/></th>
   			<th><fmt:message key="printInstr.collatingBy"/></th>
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
   			<th style="width:10%"><fmt:message key="printInstr.reprint"/></th>
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
   	<table style="width:100%">
		<tr>
			<td style="width:20%">&nbsp;</td>
			<td style="width:20%">&nbsp;</td>
			<td style="width:20%">&nbsp;</td>
			<td style="width:20%" align="right"><fmt:message key="printInstr.dateCompleted"/>&nbsp;</td>
			<td style="width:20%"><table style="border-bottom-style:solid;border-bottom-color:#C0C0C0"" cellspacing="0" cellpadding="0" width="80%"><tr><td>&nbsp;</td></tr></table></td>
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
  