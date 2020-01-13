<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.String"%>
<%@ page import="za.ac.unisa.lms.dao.Gencod"%>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>

<sakai:html>	
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" media="all" type="text/css" href="jquery-ui-timepicker-addon.css" />
<script src="https://code.jquery.com/jquery-1.9.1.js"></script>
<script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.ui.timepicker.addon/1.4.5/jquery-ui-timepicker-addon.min.js"></script>

 <style>	
	.datetimepickerOpen{
	}	
	.datetimepickerFinal{
	}
	.datepicker{
	}	
</style>
 <script> 
  $(function() {  
	    $(".datetimepickerOpen").datetimepicker({dateFormat: 'yymmdd',timeFormat: 'HH:mm',hour: '00',minute: '00'});  
  }); 
  $(function() {  
	    $(".datetimepickerFinal").datetimepicker({dateFormat: 'yymmdd',timeFormat: 'HH:mm', hour: '23',minute: '59'});  
  });
  $(function() {  
	    $(".datepicker").datepicker({dateFormat: 'yymmdd'});  
  });
 </script>

	<html:form action="/assessmentCriteria">
		<!--<html:hidden property="currentPage" value="onscreenMarking"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.assignment"/>
			</logic:notEqual>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.view.assignment"/>
			</logic:equal>			
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.code"/></td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="academicYear"/>/<bean:write name="assessmentCriteriaForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignment"/>&nbsp;</td>
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="assignment.number"/>&nbsp;&nbsp;(<fmt:message key="page.assignment.dueDate"/>:&nbsp;<bean:write name="assessmentCriteriaForm" property="assignment.dueDate"/>)</td>	
			</tr>
			<tr>
				<td><fmt:message key="page.status"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="statusDesc"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.onlineMethod"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="onlineMethod"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<sakai:heading>
				<fmt:message key="heading.assignment.sectionC"/>
		</sakai:heading>		
		<sakai:group_table>
			<bean:define id="type" name="assessmentCriteriaForm" property="assignment.type"/>
			<bean:define id="format" name="assessmentCriteriaForm" property="assignment.format"/>	
			<bean:define id="group" name="assessmentCriteriaForm" property="assignment.group"/>
				<%
				String assType = type.toString().trim();
				String assFormat = format.toString().trim();
				String assGroup = group.toString().trim();%>
				<logic:equal name="assessmentCriteriaForm" property="submissionDateIndicator" value="date">
					<tr>
							<td><fmt:message key="page.assignment.pfOpenDate"/>&nbsp;</td>
						</tr>			
						<tr>	
							<td>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.pfOpenDate" styleClass="datepicker"></html:text><fmt:message key="page.dateFormat"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.pfOpenDate" disabled="true" styleClass="datepicker"></html:text><fmt:message key="page.dateFormat"/>
								</logic:equal>
							</td>
						</tr>	
						<tr>
							<td><fmt:message key="page.assignment.finalSubmissionDate"/>&nbsp;</td>
						</tr>			
						<tr>	
							<td>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.finalSubmissionDate" styleClass="datepicker"></html:text><fmt:message key="page.dateFormat"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.finalSubmissionDate" disabled="true" styleClass="datepicker"></html:text><fmt:message key="page.dateFormat"/>
								</logic:equal>
							</td>
						</tr>			
				</logic:equal>
				<logic:equal name="assessmentCriteriaForm" property="submissionDateIndicator" value="dateTime">
					<tr>
							<td><fmt:message key="page.assignment.pfOpenDate"/>&nbsp;</td>
						</tr>			
						<tr>	
							<td>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.pfOpenDate" styleClass="datetimepickerOpen"></html:text><fmt:message key="page.dateTimeFormat"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.pfOpenDate" disabled="true" styleClass="datetimepickerOpen"></html:text><fmt:message key="page.dateTimeFormat"/>
								</logic:equal>
							</td>
						</tr>	
						<tr>
							<td><fmt:message key="page.assignment.finalSubmissionDate"/>&nbsp;</td>
						</tr>			
						<tr>	
							<td>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.finalSubmissionDate" styleClass="datetimepickerFinal"></html:text><fmt:message key="page.dateTimeFormat"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<html:text name="assessmentCriteriaForm"  property="assignment.finalSubmissionDate" disabled="true" styleClass="datetimepickerFinal"></html:text><fmt:message key="page.dateTimeFormat"/>
								</logic:equal>
							</td>
					</tr>			
				</logic:equal>	
				<%		
				if (assGroup.equalsIgnoreCase("S")){%>
					<logic:equal name="assessmentCriteriaForm"  property="studyUnit.postGraduateFlag" value="Y">	
						<logic:equal name="assessmentCriteriaForm"  property="studyUnit.researchFlag" value="P">
							<tr>
								<td><fmt:message key="page.assignment.releaseAssignment"/>&nbsp;</td>
							</tr>
							<tr>				
								<td>
									<logic:iterate name="assessmentCriteriaForm" property="listYesNo" id="record" indexId="index" >						
										<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<html:radio property="assignment.releaseFlag" idName="record" value="code" onclick='<%="javascript:setReleaseFlag(this.form,this.name," + index.toString() +");"%>'></html:radio>										
										</logic:notEqual>
										<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<html:radio property="assignment.releaseFlag" idName="record" value="code" disabled="true"></html:radio>
										</logic:equal>
										<bean:write name="record" property="engDescription"/>						
									</logic:iterate>
								</td>					
							</tr>
							<tr>
								<td><fmt:message key="page.assignment.summative.releaseDate"/>&nbsp;</td>
							</tr>
							<tr>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<logic:notEqual name="assessmentCriteriaForm" property="assignment.releaseFlag" value="N">
										<td><html:text name="assessmentCriteriaForm"  property="assignment.fileReleaseDate" styleClass="datepicker" ></html:text><fmt:message key="page.dateFormat"/></td>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="assignment.releaseFlag" value="N">
										<td><html:text name="assessmentCriteriaForm"  property="assignment.fileReleaseDate" styleClass="datepicker" disabled="true"></html:text><fmt:message key="page.dateFormat"/></td>
									</logic:equal>	
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">									
										<td><html:text name="assessmentCriteriaForm"  property="assignment.fileReleaseDate" styleClass="datepicker" disabled="true"></html:text><fmt:message key="page.dateFormat"/></td>
								</logic:equal>
							</tr>
						</logic:equal>	
					</logic:equal>
				<% } %>	
				<% if (!assGroup.equalsIgnoreCase("S")){%>	
					<tr>
						<td><fmt:message key="page.assignment.formative.releaseDate"/>&nbsp;</td>
					</tr>
					<tr>
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<td><html:text name="assessmentCriteriaForm"  property="assignment.fileReleaseDate" styleClass="datepicker"></html:text><fmt:message key="page.dateFormat"/></td>
						</logic:notEqual>
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<td><html:text name="assessmentCriteriaForm"  property="assignment.fileReleaseDate" styleClass="datepicker" disabled="true"></html:text><fmt:message key="page.dateFormat"/></td>
						</logic:equal>
					</tr>
				<% } %>				
			<tr>
				<td><fmt:message key="page.assignment.fileFormats"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			</tr>
			<tr>
				<td>
					<sakai:group_table>									
						<logic:iterate name="assessmentCriteriaForm" property="listFileFormat" id="rec" indexId="index">
							<tr>
								<td>
									<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:multibox property="indexNrSelectedFormat"><bean:write name="index"/></html:multibox>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:multibox property="indexNrSelectedFormat" disabled="true"><bean:write name="index"/></html:multibox>
									</logic:equal>
									<bean:write name="rec" property="engDescription"/>
								</td>	
							</tr>	 
						</logic:iterate>						
					</sakai:group_table>
				</td>
			</tr>				
			<tr>
				<td><fmt:message key="page.assignment.otherFileFormats"/></td>
			</tr>
			<tr>
				<td>
					<sakai:group_table>
						<bean:size id="colSize" name="assessmentCriteriaForm" property="listOtherFileFormat"/>
						<% int iteration=0; %>					
						<logic:iterate name="assessmentCriteriaForm" property="listOtherFileFormat" id="record" indexId="index">					
							<tr>
								<td>
									<fmt:message key="page.assignment.fileFormat"/>
								</td>
								<td>
									<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">								
										<html:text property='<%="listOtherFileFormat["+index+"].extention"%>' size="6" maxlength="4"></html:text>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">								
										<html:text property='<%="listOtherFileFormat["+index+"].extention"%>' size="6" maxlength="4" disabled="true"></html:text>
									</logic:equal>
								</td>
								<td>
									<fmt:message key="page.assignment.fileFormatDesc"/>
								</td>
								<td>
									<html:text property='<%="listOtherFileFormat["+index+"].description"%>' size="40" maxlength="32"></html:text>
									<%iteration = iteration + 1; 
									if (iteration==colSize){%>
										<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<html:submit property="act">
													<fmt:message key="button.moreFormats"/>
											</html:submit>	
										</logic:notEqual>	
										<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<html:submit property="act" disabled="true">
													<fmt:message key="button.moreFormats"/>
											</html:submit>	
										</logic:equal>
									<%}%>	
								</td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				 </td>	
			</tr>
			<tr>
				<td><fmt:message key="page.assignment.maxFileSize"/></td>
			</tr>
			<tr>
				<td>File Size :&nbsp;&nbsp;
					<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
						<html:text name="assessmentCriteriaForm" property="assignment.maxFileSize" size="3" maxlength="2"/>
					</logic:notEqual>
					<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
						<html:text name="assessmentCriteriaForm" property="assignment.maxFileSize" size="3" maxlength="2" disabled="true"/>
					</logic:equal>&nbsp;&nbsp;Mb
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="page.assignment.studentSpecifyLanguage"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			</tr>	
			<tr>				
				<td>
					<logic:iterate name="assessmentCriteriaForm" property="listYesNo" id="record" indexId="index" >						
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:radio property="assignment.studentSpecifyLang" idName="record" value="code" onclick='<%="javascript:setLangSpec(this.form,this.name," + index.toString() +");"%>'></html:radio>
						</logic:notEqual>
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:radio property="assignment.studentSpecifyLang" idName="record" value="code" disabled="true"></html:radio>
						</logic:equal>
						<bean:write name="record" property="engDescription"/>						
					</logic:iterate>
				</td>					
			</tr>
			</sakai:group_table>
			<sakai:group_table>
			<tr>
				<td><fmt:message key="page.assignment.languages"/></td>
			</tr>
			<tr>
				<td>	
					<sakai:group_table>			
						<logic:iterate name="assessmentCriteriaForm" property="listLanguage" id="rec" indexId="index">
							<logic:equal name="rec" property="engDescription" value="ENGLISH">
								<html:hidden property="langEngIndex" value='<%=index.toString()%>' styleId="langEngIndex" />
							</logic:equal>
							<tr>
								<td>
									<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<logic:notEqual name="assessmentCriteriaForm" property="assignment.studentSpecifyLang" value="N">
											<html:multibox property="indexNrSelectedLanguage"><bean:write name="index"/></html:multibox>
										</logic:notEqual>	
										<logic:equal name="assessmentCriteriaForm" property="assignment.studentSpecifyLang" value="N">
											<html:multibox property="indexNrSelectedLanguage" disabled="true"><bean:write name="index"/></html:multibox>
										</logic:equal>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:multibox property="indexNrSelectedLanguage" disabled="true"><bean:write name="index"/></html:multibox>
									</logic:equal>
									<bean:write name="rec" property="engDescription"/>									
								</td>								
							</tr>			 
						</logic:iterate>
					</sakai:group_table>
				</td>	
			</tr>
			<tr>
				<td><fmt:message key="page.assignment.otherLanguages"/></td>
			</tr>
			<tr>
				<td>
					<sakai:group_table>
						<bean:size id="colSize" name="assessmentCriteriaForm" property="listOtherLanguage"/>
						<html:hidden property="listOtherLangSize" value='<%=colSize.toString()%>' styleId="listOtherLangSize" />
						<% int iteration=0; %>					
						<logic:iterate name="assessmentCriteriaForm" property="listOtherLanguage" id="record" indexId="index" >					
							<tr>
								<td>
									<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:text property='<%="listOtherLanguage["+index+"]"%>' size="20" maxlength="20"></html:text>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:text property='<%="listOtherLanguage["+index+"]"%>' size="20" maxlength="20" disabled="true"></html:text>
									</logic:equal>
									<%iteration = iteration + 1; 
									if (iteration==colSize){%>
									<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:submit property="act" styleId="moreLangButton">
											<fmt:message key="button.moreLanguage"/>
										</html:submit>	
									</logic:notEqual>	
									<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
										<html:submit property="act" styleId="moreLangButton" disabled="true">
											<fmt:message key="button.moreLanguage"/>
										</html:submit>	
									</logic:equal>	
									<%}%>	
								</td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>
			</tr>
			</sakai:group_table>
		<sakai:actions>
			<logic:notEqual name="assessmentCriteriaForm" property="fromSystem" value="STUDENT">
				<html:submit property="act">
						<fmt:message key="button.continue"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.back"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.cancel"/>
				</html:submit>		
			</logic:notEqual>	
			<logic:equal name="assessmentCriteriaForm" property="fromSystem" value="STUDENT">				
				<html:submit property="act">
						<fmt:message key="button.back"/>
				</html:submit>			
			</logic:equal>		
		</sakai:actions>
		
		<script>
		function setReleaseFlag(Form,Radio,index){
			for (var i=0;i<Form[Radio].length;i++){
				if (Form[Radio][i].checked){
					if(Form[Radio][i].value=="Y"){	
						document.forms["assessmentCriteriaForm"].elements["assignment.fileReleaseDate"].disabled=false; 						
					}
					if(Form[Radio][i].value=="N"){	
						document.forms["assessmentCriteriaForm"].elements["assignment.fileReleaseDate"].value=""; 
						document.forms["assessmentCriteriaForm"].elements["assignment.fileReleaseDate"].disabled=true; 						
					}
				}
			}	
		}
		
		function setLangSpec(Form,Radio,index){	
			for (var i=0;i<Form[Radio].length;i++){
				if (Form[Radio][i].checked){
					if(Form[Radio][i].value=="Y"){		
						for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"].length;j++){
							document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].disabled=false; 
							document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].checked=false;	
							if (document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].value==document.getElementById("langEngIndex").value)
							{								
								document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].checked=true;
							}	
						}	
						document.getElementById("moreLangButton").disabled = false;	
						for (var j=0;j<document.getElementById("listOtherLangSize").value;j++){							
							document.forms["assessmentCriteriaForm"].elements["listOtherLanguage["+j+"]"].disabled=false;
						}				
					}
					if(Form[Radio][i].value=="N"){
						for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"].length;j++){							
							document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].checked=false; 
							document.forms["assessmentCriteriaForm"].elements["indexNrSelectedLanguage"][j].disabled=true; 			    			    			    	
						}
						document.getElementById("moreLangButton").disabled = true;						
						for (var j=0;j<document.getElementById("listOtherLangSize").value;j++){
							document.forms["assessmentCriteriaForm"].elements["listOtherLanguage["+j+"]"].value=""; 
							document.forms["assessmentCriteriaForm"].elements["listOtherLanguage["+j+"]"].disabled=true;
						}
					}	
				}
			}
		}
	</script>
		
	</html:form>
</sakai:html>