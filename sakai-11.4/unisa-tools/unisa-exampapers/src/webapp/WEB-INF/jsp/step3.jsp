<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>
<sakai:html>
	<style>
		@media Print
		{
			h3 {font-size:9pt;width:100%}
			h2 {font-size:9pt;font-weight:bold;font-family:Verdana,Arial,Helvetica}
			h1, h2, h3, h4, h5, h6 { page-break-after:avoid;page-break-inside:avoid }
			table { page-break-inside:avoid }
			body {background: white}
			#content #container, #container2  {width: 100%; margin: 0; float: none;}
			.pagestart{page-break-before:always;}
		}
		table.prt {padding : .4em;}
		table.prt td {padding:3px;}
		table.prt2 {border-collapse: collapse; padding : 0}
		table.prt2 td {padding:3px;}
	</style>
	<script language="javascript">
		function printFriendly(url){
			window.open(url,'mywindow');
			}
	</script>
	<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">	
		<logic:equal name="examPaperCoverDocketForm" property="displayLetterHeadRights" value="true">
			<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
				<sakai:tool_bar>
					<bean:write  name="examPaperCoverDocketForm" property="examPaperLetterHead" filter="false"/>
				</sakai:tool_bar>
			</logic:equal>
		</logic:equal>	
	</logic:notEqual>
	<html:form action="/examPaperCoverDocket">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<h2 align="center">
				<fmt:message key="print.documentHeading"/>
			</h2>			
		</logic:equal>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
				<sakai:instruction>
					<fmt:message key="page.closed.instruction"/>
				</sakai:instruction>
			</logic:equal>
			<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">
				<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="true">
					<sakai:instruction>
						<fmt:message key="page.update.instruction"/>
					</sakai:instruction>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="false">
					<sakai:instruction>
						<fmt:message key="page.instruction"/>
					</sakai:instruction>
				</logic:equal>
				<sakai:group_heading>
					<fmt:message key="step3.groupheading"/> 
				</sakai:group_heading>
			</logic:notEqual> 
			<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnit" /></td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnitDesc" /></td>	
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperNo" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examPeriodDesc"/>&nbsp;<bean:write name="examPaperCoverDocketForm" property="exampaper.examYear"/></td>
				<td>
					<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
					(<bean:write name="examPaperCoverDocketForm" property="semesterType"/>,&nbsp;						
						<fmt:message key="page.tuitionType"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="tuitionType"/>,&nbsp;
						<fmt:message key="page.acadLevel"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="acadLevel"/>)
					</logic:equal>
					&nbsp;
				</td>				
			</tr>
			<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.uniqueNo" value="0">
				<tr>
					<td><fmt:message key="page.uniqueNo"/>&nbsp;</td>			
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.uniqueNo"/></td>
					<td>&nbsp;</td>					
				</tr>
			</logic:notEqual>
		</sakai:group_table>	
		</logic:notEqual>	
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">	
		<table class='prt2'>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnit" /></td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnitDesc" /></td>	
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperNo" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examPeriodDesc"/>&nbsp;<bean:write name="examPaperCoverDocketForm" property="exampaper.examYear"/></td>
				<td>
					(<bean:write name="examPaperCoverDocketForm" property="semesterType"/>,&nbsp;						
						<fmt:message key="page.tuitionType"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="tuitionType"/>,&nbsp;
						<fmt:message key="page.acadLevel"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="acadLevel"/>)
					&nbsp;
				</td>				
			</tr>
			<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.uniqueNo" value="0">
				<tr>
					<td><fmt:message key="page.uniqueNo"/>&nbsp;</td>			
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.uniqueNo"/></td>
					<td>&nbsp;</td>					
				</tr>
			</logic:notEqual>
		</table>	
		</logic:equal>
		<hr/>		
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">	
			<sakai:group_table>	
				<tr>
					<td><fmt:message key="page.examDate"/></td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examDate"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examTime"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.duration"/></td>
					<td>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationHours" value="0">
					<bean:write name="examPaperCoverDocketForm" property="exampaper.durationHours"/>&nbsp;<fmt:message key="page.hours"/>&nbsp;&nbsp;
					</logic:notEqual>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationMin" value="0">
						<bean:write name="examPaperCoverDocketForm" property="exampaper.durationMin"/>&nbsp;<fmt:message key="page.minutes"/>
					</logic:notEqual>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationDays" value="0">
						<bean:write name="examPaperCoverDocketForm" property="exampaper.durationDays"/>&nbsp;<fmt:message key="page.days"/>
					</logic:notEqual>
					</td>
				</tr>
			</sakai:group_table>
		</logic:notEqual>	
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">	
			<table class='prt'>	
				<tr>
					<td><fmt:message key="page.examDate"/></td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examDate"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examTime"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.duration"/></td>
					<td>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationHours" value="0">
					<bean:write name="examPaperCoverDocketForm" property="exampaper.durationHours"/>&nbsp;<fmt:message key="page.hours"/>&nbsp;&nbsp;
					</logic:notEqual>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationMin" value="0">
						<bean:write name="examPaperCoverDocketForm" property="exampaper.durationMin"/>&nbsp;<fmt:message key="page.minutes"/>
					</logic:notEqual>
					<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationDays" value="0">
						<bean:write name="examPaperCoverDocketForm" property="exampaper.durationDays"/>&nbsp;<fmt:message key="page.days"/>
					</logic:notEqual>
					</td>
				</tr>
			</table>
		</logic:equal>	
		
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.combinedPapers"/></th>
				<th align="left"><fmt:message key="page.examDate"/></th>
				<th align="left"><fmt:message key="page.duration"/></th>
			</tr>
			<logic:empty name="examPaperCoverDocketForm" property="exampaper.combinedPapers">
				<tr>
					<td><fmt:message key="page.none"/></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.combinedPapers">
				<logic:iterate name="examPaperCoverDocketForm" property="exampaper.combinedPapers" id="record">
					<tr>
						<td>
						<bean:write name="record" property="studyUnit"/>&nbsp;&nbsp;
						<logic:notEqual name="record" property="uniqueNo" value="0">
							(<bean:write name="record" property="uniqueNo"/>)
						</logic:notEqual>
						</td>						
						<td>
							<bean:write name="record" property="examDate"/>&nbsp;&nbsp;
							<bean:write name="record" property="examTime"/>
						</td>
						<td>
							<logic:notEqual name="record" property="durationHours" value="0">
								<bean:write name="record" property="durationHours"/>&nbsp;<fmt:message key="page.hours"/>&nbsp;&nbsp;
							</logic:notEqual>
							<logic:notEqual name="record" property="durationMin" value="0">
								<bean:write name="record" property="durationMin"/>&nbsp;<fmt:message key="page.minutes"/>&nbsp;&nbsp;
							</logic:notEqual>
							<logic:notEqual name="record" property="durationDays" value="0">
								<bean:write name="record" property="durationDays"/>&nbsp;<fmt:message key="page.days"/>&nbsp;&nbsp;
							</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<logic:equal name="examPaperCoverDocketForm" property="educationFaculty" value="true">
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.examPanel"/></th>
			</tr>
			</sakai:flat_list>
		</logic:equal>
		<logic:equal name="examPaperCoverDocketForm" property="educationFaculty" value="false">
			<sakai:flat_list>
				<tr>
					<th align="left"><fmt:message key="page.firstExaminers"/></th>
				</tr>
				<logic:empty name="examPaperCoverDocketForm" property="exampaper.firstExaminers">
					<tr>
						<td><fmt:message key="page.none"/></td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.firstExaminers">
					<logic:iterate name="examPaperCoverDocketForm" property="exampaper.firstExaminers" id="record">
						<tr>
							<td><bean:write name="record"/></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>	
			</sakai:flat_list>
			<sakai:flat_list>
				<tr>
					<th align="left"><fmt:message key="page.secondExaminers"/></th>
				</tr>
				<logic:empty name="examPaperCoverDocketForm" property="exampaper.secondExaminers">
					<tr>
						<td><fmt:message key="page.none"/></td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.secondExaminers">
					<logic:iterate name="examPaperCoverDocketForm" property="exampaper.secondExaminers" id="record">
						<tr>
							<td><bean:write name="record"/></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>	
			</sakai:flat_list>
			<sakai:flat_list>
				<tr>
					<th align="left"><fmt:message key="page.externalExaminers"/></th>
				</tr>
				<logic:empty name="examPaperCoverDocketForm" property="exampaper.externalExaminers">
					<tr>
						<td><fmt:message key="page.none"/></td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.externalExaminers">
					<logic:iterate name="examPaperCoverDocketForm" property="exampaper.externalExaminers" id="record">
						<tr>
							<td><bean:write name="record"/></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>	
			</sakai:flat_list>
		</logic:equal>
		<sakai:group_table></sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingA"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<tr>
					<td><fmt:message key="page.markReading"/>&nbsp;</td>
					<td><bean:write name="mcqDesc"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="page.fillInPaper"/>&nbsp;</td>
					<td><bean:write name="fillInDesc"/>&nbsp;
						<logic:equal name="examPaperCoverDocketForm" property="fillInPaper" value="F">
							(<fmt:message key="page.specialFrontPage"/>)
						</logic:equal>	
						<logic:equal name="examPaperCoverDocketForm" property="fillInPaper" value="P">
							<logic:equal name="examPaperCoverDocketForm" property="exampaper.markReadingCode" value="P">
								(<fmt:message key="page.specialFrontPage"/>)
							</logic:equal>
						</logic:equal>					
					</td>
				</tr>	
				<logic:equal name="examPaperCoverDocketForm" property="declaration" value="Y">
					<tr><td><fmt:message key="page.declaration"/></td></tr>
				</logic:equal>
				<tr>
					<td><fmt:message key="page.writtenTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.writtenTotal"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.mcqTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.mcqTotal"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.paperTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperTotal"/></td>
				</tr>
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="true">
					<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">
						<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
							<tr>
								<td><fmt:message key="page.totalPages"/>&nbsp;</td>
								<td><html:text name="examPaperCoverDocketForm" property="exampaper.totalPages" size="3" maxlength="3"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.rackSpacePages"/>&nbsp;</td>
								<td><html:text name="examPaperCoverDocketForm" property="exampaper.rackSpacePages" size="3" maxlength="3"/></td>
							</tr>
						</logic:notEqual>				
					</logic:notEqual>
				</logic:equal>				
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="true">
					<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
					<tr>
						<td><fmt:message key="page.totalPages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.totalPages"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.rackSpacePages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.rackSpacePages"/></td>
					</tr>
					</logic:equal>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="false">
					<tr>
						<td><fmt:message key="page.totalPages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.totalPages"/></td>
					</tr>
				</logic:equal>
				<tr>
					<td><fmt:message key="page.annexurePages"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="annexurePages"/></td>
				</tr>
			</sakai:group_table>	
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<tr>
					<td><fmt:message key="page.markReading"/>&nbsp;</td>
					<td><bean:write name="mcqDesc"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="page.fillInPaper"/>&nbsp;</td>
					<td><bean:write name="fillInDesc"/>&nbsp;
						<logic:equal name="examPaperCoverDocketForm" property="fillInPaper" value="F">
							(<fmt:message key="page.specialFrontPage"/>)
						</logic:equal>	
						<logic:equal name="examPaperCoverDocketForm" property="fillInPaper" value="P">
							<logic:equal name="examPaperCoverDocketForm" property="exampaper.markReadingCode" value="P">
								(<fmt:message key="page.specialFrontPage"/>)
							</logic:equal>
						</logic:equal>					
					</td>
				</tr>	
				<logic:equal name="examPaperCoverDocketForm" property="declaration" value="Y">
					<tr><td><fmt:message key="page.declaration"/></td></tr>
				</logic:equal>
				<tr>
					<td><fmt:message key="page.writtenTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.writtenTotal"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.mcqTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.mcqTotal"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.paperTotal"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperTotal"/></td>
				</tr>				
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="true">
					<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">					
						<tr>
							<td><fmt:message key="page.totalPages"/>&nbsp;</td>
							<td><bean:write name="examPaperCoverDocketForm" property="exampaper.totalPages"/></td>
						</tr>
						<tr>
							<td><fmt:message key="page.rackSpacePages"/>&nbsp;</td>
							<td><bean:write name="examPaperCoverDocketForm" property="exampaper.rackSpacePages"/></td>
						</tr>
					</logic:notEqual>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="true">
					<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
					<tr>
						<td><fmt:message key="page.totalPages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.totalPages"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.rackSpacePages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.rackSpacePages"/></td>
					</tr>
					</logic:equal>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="updateNrOfPagesRights" value="false">
					<tr>
						<td><fmt:message key="page.totalPages"/>&nbsp;</td>
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.totalPages"/></td>
					</tr>
				</logic:equal>
				<tr>
					<td><fmt:message key="page.annexurePages"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="annexurePages"/></td>
				</tr>
			</table>	
		</logic:equal>
		
		<sakai:heading>
			<fmt:message key="step2.headingB"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<logic:iterate id="records" name="selectedSpecialMaterials">
					<tr>
						<td><bean:write name="records" property="description"/></td>
						<td><bean:write name="records" property="average"/></td>
					</tr>
				</logic:iterate>
			</sakai:group_table>
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<logic:iterate id="records" name="selectedSpecialMaterials">
					<tr>
						<td><bean:write name="records" property="description"/></td>
						<td><bean:write name="records" property="average"/></td>
					</tr>
				</logic:iterate>
			</table>
		</logic:equal>
		<sakai:heading>
			<fmt:message key="step2.headingC"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<logic:iterate id="records" name="selectedBooks">
					<tr>
						<td><bean:write name="records" property="description"/></td>
						<td><bean:write name="records" property="average"/></td>
					</tr>
				</logic:iterate>
			</sakai:group_table>
			<sakai:group_table>
			<logic:equal name="examPaperCoverDocketForm" property="noBookRequired" value="true">
				<tr><td><fmt:message key="page.noBookRequired"/></td></tr>
			</logic:equal>
			</sakai:group_table>
		</logic:notEqual>	
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<logic:iterate id="records" name="selectedBooks">
					<tr>
						<td><bean:write name="records" property="description"/></td>
						<td><bean:write name="records" property="average"/></td>
					</tr>
				</logic:iterate>
			</table>
			<table class='prt'>
			<logic:equal name="examPaperCoverDocketForm" property="noBookRequired" value="true">
				<tr><td><fmt:message key="page.noBookRequired"/></td></tr>
			</logic:equal>
			</table>
		</logic:equal>			
		<sakai:heading>
			<fmt:message key="step2.headingD"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<tr>
					<td><fmt:message key="page.openBook"/>&nbsp;</td>
					<td><bean:write name="openBookDesc"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="page.keepPaper"/>&nbsp;</td>
					<td><bean:write name="keepPaperDesc"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.calcPermit"/>&nbsp;</td>
					<td><bean:write name="calcPermitDesc"/></td>
				</tr>	
			</sakai:group_table>			
			<sakai:group_table>
				<logic:equal name="instructionsSelected" value="true">
					<tr>
						<td><sakai:heading><fmt:message key="page.selected"/></sakai:heading></td>
					</tr>
					<logic:iterate id="records" name="selectedCalcInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
					<logic:iterate id="records" name="selectedInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
				</logic:equal>
			</sakai:group_table>
			<sakai:group_table>
				<logic:notEmpty name="selectedAutoInstructions">
					<tr>
						<td><sakai:heading><fmt:message key="page.autoInstructions"/></sakai:heading></td>
					</tr>	
					<logic:iterate id="records" name="selectedAutoInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</sakai:group_table>
		</logic:notEqual>	
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<tr>
					<td><fmt:message key="page.openBook"/>&nbsp;</td>
					<td><bean:write name="openBookDesc"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="page.keepPaper"/>&nbsp;</td>
					<td><bean:write name="keepPaperDesc"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.calcPermit"/>&nbsp;</td>
					<td><bean:write name="calcPermitDesc"/></td>
				</tr>	
			</table>
			<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">		
			<!-- Page break -->
			<p class='pagestart'/>				
			<!-- END PAGE 1 -->			
		</logic:equal>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<h2 align="center">
				<fmt:message key="print.documentHeading"/>
			</h2>
			<table class='prt2'>	
				<tr>
					<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnit" /></td>
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnitDesc" /></td>
				</tr>
				<tr>
					<td><fmt:message key="page.paperNumber"/>&nbsp;</td>			
					<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperNo" /></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><fmt:message key="page.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examPeriodDesc"/>&nbsp;<bean:write name="examPaperCoverDocketForm" property="exampaper.examYear"/></td>
				<td>		
					(<bean:write name="examPaperCoverDocketForm" property="semesterType"/>,&nbsp;						
						<fmt:message key="page.tuitionType"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="tuitionType"/>,&nbsp;
						<fmt:message key="page.acadLevel"/>&nbsp;:&nbsp;<bean:write name="examPaperCoverDocketForm" property="acadLevel"/>)
					&nbsp;
				</td>		
				</tr>
				<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.uniqueNo" value="0">
					<tr>
						<td><fmt:message key="page.uniqueNo"/>&nbsp;</td>			
						<td><bean:write name="examPaperCoverDocketForm" property="exampaper.uniqueNo"/></td>
						<td>&nbsp;</td>
					</tr>
				</logic:notEqual>
			</table>	
			<hr/>
		</logic:equal>	
			<table class='prt'>
				<logic:equal name="instructionsSelected" value="true">
					<tr>
						<td><sakai:heading><fmt:message key="page.selected"/></sakai:heading></td>
					</tr>
					<logic:iterate id="records" name="selectedCalcInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
					<logic:iterate id="records" name="selectedInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
				</logic:equal>
			</table>
			<table class='prt'>
				<logic:notEmpty name="selectedAutoInstructions">
					<tr>
						<td><sakai:heading><fmt:message key="page.autoInstructions"/></sakai:heading></td>
					</tr>	
					<logic:iterate id="records" name="selectedAutoInstructions">
						<tr>
							<td><bean:write name="records"/></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>			
		<sakai:heading>
			<fmt:message key="step2.headingE"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<logic:iterate id="records" name="selectedLanguages">
					<tr>
						<td><bean:write name="records" property="engDescription"/></td>
					</tr>
				</logic:iterate>
			</sakai:group_table>
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<logic:iterate id="records" name="selectedLanguages">
					<tr>
						<td><bean:write name="records" property="engDescription"/></td>
					</tr>
				</logic:iterate>
			</table>
		</logic:equal>		
		<sakai:heading>
			<fmt:message key="step2.headingF"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<logic:iterate id="records" name="enteredInstructions">
					<logic:notEqual name="records" value="">
					<tr>
						<td><bean:write name="records"/></td>
					</tr>
					</logic:notEqual>
				</logic:iterate>
			</sakai:group_table>	
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<logic:iterate id="records" name="enteredInstructions">
					<logic:notEqual name="records" value="">
					<tr>
						<td><bean:write name="records"/></td>
					</tr>
					</logic:notEqual>
				</logic:iterate>
			</table>	
		</logic:equal>
		
		
		<!--<sakai:heading>
			<logic:equal name="examPaperCoverDocketForm" property="requestToProofRead" value="Y">
			<fmt:message key="page.headingGYes"/>
			</logic:equal>
			<logic:equal name="examPaperCoverDocketForm" property="requestToProofRead" value="N">
			<fmt:message key="page.headingGNo"/>
			</logic:equal>
		</sakai:heading>-->
		<sakai:group_table></sakai:group_table><sakai:heading>
			<logic:equal name="examPaperCoverDocketForm" property="memoIncluded" value="Y">
			<fmt:message key="page.headingGYes"/>
			</logic:equal>
			<logic:equal name="examPaperCoverDocketForm" property="memoIncluded" value="N">
			<fmt:message key="page.headingGNo"/>
			</logic:equal>
		</sakai:heading>
		<sakai:group_table></sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingH2"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>			
				<logic:equal name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="LIST">
					<tr>
						<td>
							<fmt:message key="page.examinerList"/>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="PANEL">
					<tr>
						<td>
							<fmt:message key="page.examinerPanel"/>
						</td>
					</tr>
				</logic:equal>
			</sakai:group_table>
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>			
				<logic:equal name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="LIST">
					<tr>
						<td>
							<fmt:message key="page.examinerList"/>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="PANEL">
					<tr>
						<td>
							<fmt:message key="page.examinerPanel"/>
						</td>
					</tr>
				</logic:equal>
			</table>
		</logic:equal>
		<sakai:heading>
			<fmt:message key="step2.headingI"/>
		</sakai:heading>		
			<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
				<logic:equal name="examPaperCoverDocketForm" property="updateRights" value="true">
					<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">
						<sakai:group_table>
							<tr>
								<td><fmt:message key="page.name"/></td>
								<td><bean:write name="contactName"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.cellNumber"/></td>
								<td><html:text name="examPaperCoverDocketForm" property="contact.cellNumber" size="20"  maxlength="20"/><fmt:message key="page.cellNumberEg"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.workNumber"/></td>
								<td><html:text name="examPaperCoverDocketForm" property="contact.workNumber" size="20"  maxlength="20"/></td>
							</tr>					
							<tr>
								<td><fmt:message key="page.homeNumber"/></td>
								<td><html:text name="examPaperCoverDocketForm" property="contact.homeNumber" size="20"  maxlength="20"/></td>
							</tr>	
							<tr>
								<td><fmt:message key="page.emailAddress"/></td>
								<td><bean:write name="examPaperCoverDocketForm" property="contact.emailAddress"/>
								<html:hidden name="examPaperCoverDocketForm" property="contact.emailAddress"/></td>
							</tr>	
						</sakai:group_table>
					</logic:notEqual>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="updateRights" value="true">
					<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
						<sakai:group_table>
							<tr>
								<td><fmt:message key="page.name"/></td>
								<td><bean:write name="contactName"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.cellNumber"/></td>
								<td><bean:write name="examPaperCoverDocketForm" property="contact.cellNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.cellNumber"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.workNumber"/></td>
								<td><bean:write name="examPaperCoverDocketForm" property="contact.workNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.workNumber"/></td>
							</tr>
							<tr>
								<td><fmt:message key="page.homeNumber"/></td>
								<td><bean:write name="examPaperCoverDocketForm" property="contact.homeNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.homeNumber"/></td>
							</tr>		
							<tr>
								<td><fmt:message key="page.emailAddress"/></td>
								<td><bean:write name="examPaperCoverDocketForm" property="contact.emailAddress"/>
								<html:hidden name="examPaperCoverDocketForm" property="contact.emailAddress"/></td>
							</tr>	
						 </sakai:group_table>	
					</logic:equal>
				</logic:equal>
				<logic:equal name="examPaperCoverDocketForm" property="updateRights" value="false">
					<sakai:group_table>
						<tr>
							<td><fmt:message key="page.name"/></td>
							<td><bean:write name="contactName"/></td>
						</tr>
						<tr>
							<td><fmt:message key="page.cellNumber"/></td>
							<td><bean:write name="examPaperCoverDocketForm" property="contact.cellNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.cellNumber"/></td>
						</tr>
						<tr>
							<td><fmt:message key="page.workNumber"/></td>
							<td><bean:write name="examPaperCoverDocketForm" property="contact.workNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.workNumber"/></td>
						</tr>
						<tr>
							<td><fmt:message key="page.homeNumber"/></td>
							<td><bean:write name="examPaperCoverDocketForm" property="contact.homeNumber"/><html:hidden name="examPaperCoverDocketForm" property="contact.homeNumber"/></td>
						</tr>	
						<tr>
							<td><fmt:message key="page.emailAddress"/></td>
							<td><bean:write name="examPaperCoverDocketForm" property="contact.emailAddress"/>
							<html:hidden name="examPaperCoverDocketForm" property="contact.emailAddress"/></td>
						</tr>
					</sakai:group_table>					
				</logic:equal>
			</logic:notEqual>
			<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
				<table class='prt'>
					<tr>
						<td><fmt:message key="page.name"/></td>
						<td><bean:write name="contactName"/></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><fmt:message key="page.cellNumber"/></td>
						<td><bean:write name="examPaperCoverDocketForm" property="contact.cellNumber"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.emailAddress"/></td>
						<td><bean:write name="examPaperCoverDocketForm" property="contact.emailAddress"/>
						<html:hidden name="examPaperCoverDocketForm" property="contact.emailAddress"/></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><fmt:message key="page.workNumber"/></td>
						<td><bean:write name="examPaperCoverDocketForm" property="contact.workNumber"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><fmt:message key="page.homeNumber"/></td>
						<td><bean:write name="examPaperCoverDocketForm" property="contact.homeNumber"/></td>
					</tr>
					
				</table>						
			</logic:equal>				
			
		<sakai:heading>
			<fmt:message key="step3.heading.production"/>
		</sakai:heading>
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:group_table>
				<logic:equal name="examPaperCoverDocketForm" property="attendanceRegister" value="true">
					<tr>
						<td><fmt:message key="page.attendanceRegister"/></td>
					</tr>
				</logic:equal>	
				<logic:equal name="examPaperCoverDocketForm" property="mcqInstructionSheet" value="true">
					<tr>
						<td><fmt:message key="page.mcqInstructionSheet"/></td>
					</tr>
				</logic:equal>	
			</sakai:group_table>
		</logic:notEqual>		
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<table class='prt'>
				<logic:equal name="examPaperCoverDocketForm" property="attendanceRegister" value="true">
					<tr>
						<td><fmt:message key="page.attendanceRegister"/></td>
					</tr>
				</logic:equal>	
				<logic:equal name="examPaperCoverDocketForm" property="mcqInstructionSheet" value="true">
					<tr>
						<td><fmt:message key="page.mcqInstructionSheet"/></td>
					</tr>
				</logic:equal>	
			</table>
		</logic:equal>			
		<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:heading>
				<fmt:message key="step3.heading.paperColour"/>
			</sakai:heading>
			<sakai:group_table>
				<tr>
					<td><bean:write name="examPaperCoverDocketForm" property="paperColour"/></td>
				</tr>
			</sakai:group_table>
		</logic:notEqual>
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
			<sakai:heading>
				<fmt:message key="step3.heading.paperColour"/>:&nbsp;<bean:write name="examPaperCoverDocketForm" property="paperColour"/>
			</sakai:heading>		
		</logic:equal>
		
		<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">
		
			<!-- PRINT SIGNATURES FOR SIGN-OFF DOCUMENT - PAGE 2 -->
			
			<hr/>
			<sakai:heading>
				<fmt:message key="print.signatureHeading"/>
			</sakai:heading>			
			<sakai:group_table>
				<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
				<tr>
					<td><fmt:message key="print.firstSign"/></td>
					<td><fmt:message key="print.secondSign"/></td>
				</tr>
				<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
				<tr>
					<td><fmt:message key="print.chairDeptSign"/></td>
					<td><fmt:message key="print.externalSign"/></td>
				</tr>				
			</sakai:group_table>
		
			<!-- END SIGN-OFF DOCUMENT HEADING - PAGE 2 -->
			
		</logic:equal>
		
		<sakai:actions>	
			<logic:notEqual name="examPaperCoverDocketForm" property="displayMedia" value="print">	
				<logic:equal name="examPaperCoverDocketForm" property="updateRights" value="true">
					<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">
						<html:submit property="action">
								<fmt:message key="button.submit"/>
						</html:submit>	
						<html:submit property="action">
								<fmt:message key="button.back"/>
						</html:submit>							
					</logic:notEqual>	
				</logic:equal>	
				<logic:equal name="examPaperCoverDocketForm" property="updateStatusClosedRights" value="true">
						<logic:notEqual name="examPaperCoverDocketForm" property="status" value="CLOSED">							
							<!--<html:submit property="action">
								<fmt:message key="button.submit"/>
							</html:submit>-->
							<html:submit property="action">
								<fmt:message key="button.close"/>
							</html:submit>							
						</logic:notEqual>
					</logic:equal>
					<logic:equal name="examPaperCoverDocketForm" property="updateStatusOpenRights" value="true">
						<logic:equal name="examPaperCoverDocketForm" property="status" value="CLOSED">
							<html:submit property="action">
								<fmt:message key="button.open"/>
							</html:submit>
						</logic:equal>
					</logic:equal>
				<html:submit property="action">
						<fmt:message key="button.cancel"/>
				</html:submit>
			</logic:notEqual>
			<logic:equal name="examPaperCoverDocketForm" property="displayMedia" value="print">								
				<input type=button value=Print onclick="window.print()">
			</logic:equal>
		</sakai:actions>
		</html:form>
</sakai:html>