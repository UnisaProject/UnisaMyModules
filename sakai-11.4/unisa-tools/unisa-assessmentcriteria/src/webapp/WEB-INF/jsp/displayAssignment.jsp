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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="https://code.jquery.com/jquery-1.9.1.js"></script>
<script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

 <script>
 function  setAssignmentType(Form,Radio,index){	
		for (var i=0;i<Form[Radio].length;i++){
			if (Form[Radio][i].checked){					
				if (Form[Radio][i].value=="PF" || Form[Radio][i].value=="PC" || Form[Radio][i].value=="PJ"){
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"].length;j++){
							document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"][j].checked=false; 
						if (document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"][j].value=="Y"){
							document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"][j].checked=true; 							
						}			    			    			    	
					}
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].disabled=false;
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["assignment.optionality"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].disabled=false; 
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].checked=false; 
						if (document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].value=="M"){
							document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].checked=true;
						} 			    			    			    	
					}						
					i=Form[Radio].length; 		    			    			    	
				}	
				if (Form[Radio][i].value=="I" || Form[Radio][i].value=="G" || Form[Radio][i].value=="T" || Form[Radio][i].value=="R" || Form[Radio][i].value=="S" || Form[Radio][i].value=="O"){
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["yearMarkContributionYesNoFlag"][j].disabled=false; 									    			    			    	
					}	
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].disabled=false;
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["assignment.optionality"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].disabled=false; 			    			    			    	
					}						
					i=Form[Radio].length; 		    			    			    	
				}	
			}		   	
	    }
	}		
	function  setYearMarkContribution(Form,Radio,index){	
		for (var i=0;i<Form[Radio].length;i++){
			if (Form[Radio][i].checked){
				if (Form[Radio][i].value=="N"){
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].disabled=true;
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].disabled=true;
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].disabled=true;
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["assignment.optionality"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].checked=false; 
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].disabled=true; 			    			    			    	
					}
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["selfAssesmentYesNoFlag"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["selfAssesmentYesNoFlag"][j].checked=false; 
						document.forms["assessmentCriteriaForm"].elements["selfAssesmentYesNoFlag"][j].disabled=false; 			    			    			    	
					}	
					i=Form[Radio].length; 		    			    			    	
				}	
				if (Form[Radio][i].value=="Y"){
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].disabled=false;
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].value="0";
					document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].disabled=false;
					for (var j=0;j<document.forms["assessmentCriteriaForm"].elements["assignment.optionality"].length;j++){
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].checked=false; 
						document.forms["assessmentCriteriaForm"].elements["assignment.optionality"][j].disabled=false; 			    			    			    	
					}						
             	i=Form[Radio].length; 		    			    			    	
				}	
			}		   	
	    }
	}	
	
	function setGroup(Form,Radio,index){
		
		//Get the assessment group
		var group ="";
		for (var i=0;i<document.getElementsByName("assignment.group").length;i++){		  
		  	if (document.getElementsByName("assignment.group")[i].checked){			  
			  group =document.getElementsByName("assignment.group")[i].value;
			}					
	  	}
		//Reset Assignment Format, enable all buttons
		for (var i=0;i<document.getElementsByName("assignment.format").length;i++) 
			{
				document.getElementsByName("assignment.format")[i].disabled=false;
				document.getElementsByName("assignment.format")[i].checked=false;
				if (group=="S"){
					if (document.getElementsByName("assignment.format")[i].value=="A"){  
						document.getElementsByName("assignment.format")[i].disabled=true; //Disable Discussion Forum								
					}		
					if (document.getElementsByName("assignment.format")[i].value=="BL"){  
						document.getElementsByName("assignment.format")[i].disabled=true; //Disable Discussion Forum								
					}		
					if (document.getElementsByName("assignment.format")[i].value=="DF"){  
						document.getElementsByName("assignment.format")[i].disabled=true; //Disable Discussion Forum								
					}		
				}
			}	
		//Reset Assignment Type, enable all buttons
		for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{
				document.getElementsByName("assignment.type")[i].disabled=false;
				document.getElementsByName("assignment.type")[i].checked=false;
			}	
		
	}
	
	function setFormat(Form,Radio,index){
		//Get the assessment group
		var group ="";
		for (var i=0;i<document.getElementsByName("assignment.group").length;i++){		  
		  	if (document.getElementsByName("assignment.group")[i].checked){			  
			  group =document.getElementsByName("assignment.group")[i].value;
			}					
	  	}
		//Reset Assignment Type, enable all buttons
		for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{
				document.getElementsByName("assignment.type")[i].disabled=false;
				document.getElementsByName("assignment.type")[i].checked=false;
			}			
		var format="";
		for (var i=0;i<Form[Radio].length;i++){
			if (Form[Radio][i].checked){					
				format = Form[Radio][i].value;
				setDefaultTypes(format,group);					
			}					
		}
	}
	
	function setDefaultTypes(format,group){
		if (format=="A"){  //Multiple choice
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{	
				if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}				
				if (document.getElementsByName("assignment.type")[i].value=="R"){             //Disable Peer Assignment report
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="S"){             //Disable Peer Assignment
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}
				if (document.getElementsByName("assignment.type")[i].value=="O"){             //Disable Online Examination
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}				
				if (group=="S"){
					if (document.getElementsByName("assignment.type")[i].value=="I"){         //Disable Individual
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
					if (document.getElementsByName("assignment.type")[i].value=="G"){         //Disable Group
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
					if (document.getElementsByName("assignment.type")[i].value=="T"){         //Disable Test
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}
				}				
			}						
		}
		if (format=="H"){ //Written
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{							
				if (document.getElementsByName("assignment.type")[i].value=="R"){             //Disable Peer Assignment report
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="S"){             //Disable Peer Assignment
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}
				if (document.getElementsByName("assignment.type")[i].value=="O"){             //Disable Online Examination
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}	
				if (group=="F"){
					if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}
					if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}
					if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}				
				}
				if (group=="S"){
					if (document.getElementsByName("assignment.type")[i].value=="I"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
					if (document.getElementsByName("assignment.type")[i].value=="G"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
					if (document.getElementsByName("assignment.type")[i].value=="T"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
				}				
			}			
		}
		if (format=="DF" || format=="BL"){  //Discusson Forum or Blog
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{	
				if (document.getElementsByName("assignment.type")[i].value=="G"){             //Disable Group Assessment
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="T"){             //Disable Test
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="R"){             //Disable Peer Assignment report
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="S"){             //Disable Peer Assignment
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}
				if (document.getElementsByName("assignment.type")[i].value=="O"){             //Disable Online Examination
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}
				if (group=="S"){
					if (document.getElementsByName("assignment.type")[i].value=="I"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}					
				}
			}
		}
		if (format=="SA" || format=="XA"){  //Blog or Samigo or External Assessment
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{	
				if (document.getElementsByName("assignment.type")[i].value=="G"){             //Disable Group Assessment
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="T"){             //Disable Test
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="R"){             //Disable Peer Assignment report
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="S"){             //Disable Peer Assignment
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}					
				if (group=="F"){
					if (document.getElementsByName("assignment.type")[i].value=="O"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
					
				}	
				if (group=="S"){
					if (document.getElementsByName("assignment.type")[i].value=="I"){        //Disable individual
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}
				}		
			}
		}
		if (format=="MS" ){  //myUnisa Submission
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{						
				if (document.getElementsByName("assignment.type")[i].value=="T"){             //Test
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}	
				if (group=="F"){
					if (document.getElementsByName("assignment.type")[i].value=="O"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}	
					if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}
					if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}
					if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
						document.getElementsByName("assignment.type")[i].disabled=true;							
					}				
				}
				if (group=="S"){
					if (document.getElementsByName("assignment.type")[i].value=="I"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}	
					if (document.getElementsByName("assignment.type")[i].value=="G"){  
						document.getElementsByName("assignment.type")[i].disabled=true;								
					}	
				}	
			}
		}
		if (format=="OR"){  //Discusson Forum 
			for (var i=0;i<document.getElementsByName("assignment.type").length;i++) 
			{	
				if (document.getElementsByName("assignment.type")[i].value=="G"){             //Disable Group Assessment
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PF"){            //Disable Portfolio
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PC"){            //Disable Practical
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="PJ"){            //Disable Project
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="T"){             //Disable Test
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="R"){             //Disable Peer Assignment report
					document.getElementsByName("assignment.type")[i].disabled=true;							
				}
				if (document.getElementsByName("assignment.type")[i].value=="S"){             //Disable Peer Assignment
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}
				if (document.getElementsByName("assignment.type")[i].value=="O"){             //Disable Online Examination
					document.getElementsByName("assignment.type")[i].disabled=true;								
				}							
			}
		}
	}
	
	function onChangeNormalWeight(){
		document.forms["assessmentCriteriaForm"].elements["assignment.repeatWeight"].value=document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].value;
		document.forms["assessmentCriteriaForm"].elements["assignment.aegrotatWeight"].value=document.forms["assessmentCriteriaForm"].elements["assignment.normalWeight"].value;
	}
	
  $(function() {
   $( "#datepicker" ).datepicker({dateFormat: 'yymmdd'});
  });
  
  $(document).ready(function(){	  
	  if (document.getElementsByName("assignmentAction").value!="view"){
		  var group="";
		  var format="";
		  for (var i=0;i<document.getElementsByName("assignment.group").length;i++){		  
			  	if (document.getElementsByName("assignment.group")[i].checked){			  
				  group =document.getElementsByName("assignment.group")[i].value;
				}					
		  }
		  for (var i=0;i<document.getElementsByName("assignment.format").length;i++){		  
			  	if (document.getElementsByName("assignment.format")[i].checked){			  
				  format =document.getElementsByName("assignment.format")[i].value;	
				  setDefaultTypes(format,group);
				}					
		  }
	  }
  });
 </script>

<sakai:html>	
	<html:form action="/assessmentCriteria">
		<!--<html:hidden property="currentPage" value="assignment"/>-->
		<!--<html:hidden property="currentPage" value="assignment"/>-->
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
			<fmt:message key="heading.assignment.sectionA"/>
		</sakai:heading>
		<sakai:group_table>	
		<tr>
			<td width="150"><fmt:message key="page.assignment.number"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			<td>
				<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="add">
					<html:text name="assessmentCriteriaForm" property="assignment.number" size="3" maxlength="2"/>
				</logic:equal>
				<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="add">
					<html:text name="assessmentCriteriaForm" property="assignment.number" size="3" maxlength="2" disabled="true"/>
				</logic:notEqual>
			</td>
		</tr>	
		<logic:equal name="assessmentCriteriaForm" property="survey" value="true">
			<tr>
				<td width="150"><fmt:message key="page.assignment.format"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<logic:iterate name="assessmentCriteriaForm" property="listAssignmentFormat" id="record" indexId="index">
					<logic:equal name="record" property="code" value="A">
						<td><bean:write name="record" property="engDescription"/></td>	
						<html:hidden property="assignment.format" value="A"/>
						<html:hidden property="assignment.type" value="I"/>
					</logic:equal>
				</logic:iterate>				
			</tr>
		</logic:equal>		
		<logic:equal name="assessmentCriteriaForm" property="survey" value="false">
			<tr>
				<td width="150"><fmt:message key="page.assignment.group"/>&nbsp;<fmt:message key="page.mandatory"/></td>
					<td>				
						<sakai:group_table>
							<tr>
								<logic:iterate name="assessmentCriteriaForm" property="listAssignmentGroup" id="record" indexId="index">
									
										<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<td><html:radio property="assignment.group" idName="record" value="code" onclick='<%="javascript:setGroup(this.form,this.name," + index.toString() +");"%>'></html:radio></td>
										</logic:notEqual>
										<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
											<td><html:radio property="assignment.group" idName="record" value="code"  disabled="true"></html:radio></td>
										</logic:equal>
										<td><bean:write name="record" property="engDescription"/></td>								
								</logic:iterate>
							</tr>
						</sakai:group_table>
					</td>
			</tr>					
			<tr>
				<td width="150"><fmt:message key="page.assignment.format"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td>				
					<sakai:group_table>
						<logic:iterate name="assessmentCriteriaForm" property="listAssignmentFormat" id="record" indexId="index">
							<tr>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<logic:equal name="assessmentCriteriaForm" property="assignment.captureOnStudentSystem" value="false">
										<td><html:radio property="assignment.format" idName="record" value="code" onclick='<%="javascript:setFormat(this.form,this.name," + index.toString() +");"%>'></html:radio></td>
									</logic:equal>	
									<logic:equal name="assessmentCriteriaForm" property="assignment.captureOnStudentSystem" value="true">
										<td><html:radio property="assignment.format" idName="record" value="code" disabled="true"></html:radio></td>
									</logic:equal>	
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">								
									<td><html:radio property="assignment.format" idName="record" value="code" disabled="true"></html:radio></td>
								</logic:equal>							
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>	
					</sakai:group_table>				
				</td>
			</tr>		
			<tr>
				<td width="150"><fmt:message key="page.assignment.type"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:iterate name="assessmentCriteriaForm" property="listAssignmentType" id="record" indexId="index">
							<tr>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<td><html:radio property="assignment.type" idName="record" value="code" onclick='<%="javascript:setAssignmentType(this.form,this.name," + index.toString() +");"%>'></html:radio></td>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<td><html:radio property="assignment.type" idName="record" value="code" disabled="true"></html:radio></td>
								</logic:equal>
									<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>
			</tr>
		</logic:equal>	
		<tr>
			<td><fmt:message key="page.assignment.dueDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
				<sakai:instruction>
					<fmt:message key="page.note5"/>
				</sakai:instruction>
			</td>
		</tr>	
		<tr>
			<td><fmt:message key="page.assignment.chooseDueDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			<td>
				<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm"  property="assignment.dueDate" styleId="datepicker" ></html:text><fmt:message key="page.dateFormat"/>
				</logic:notEqual>
				<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm"  property="assignment.dueDate" styleId="datepicker" disabled="true"></html:text><fmt:message key="page.dateFormat"/>
				</logic:equal>
			</td>
		</tr>		
		<logic:equal name="assessmentCriteriaForm" property="survey" value="false">
			<tr>
				<td colspan="2"><fmt:message key="page.assignment.contributeYearmark"/>&nbsp;<fmt:message key="page.mandatory"/></td>	
			</tr>
			<tr>
				<td>
					<logic:iterate name="assessmentCriteriaForm" property="listYesNo" id="record" indexId="index" >						
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:radio property="yearMarkContributionYesNoFlag" idName="record" value="code" onclick='<%="javascript:setYearMarkContribution(this.form,this.name," + index.toString() +");"%>'></html:radio>
						</logic:notEqual>
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:radio property="yearMarkContributionYesNoFlag" idName="record" value="code" disabled="true"></html:radio>
						</logic:equal>
						<bean:write name="record" property="engDescription"/>						
					</logic:iterate>
				</td>					
			</tr>
			<!--<tr>
				<td colspan="2"><fmt:message key="page.assignment.selfAssessment"/>&nbsp;</td>	
			</tr>
			<tr>
				<td>
					<logic:iterate name="assessmentCriteriaForm" property="listSelfAssessment" id="record" indexId="index" >								
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<logic:notEqual name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="Y">
								<html:radio property="selfAssesmentYesNoFlag" idName="record" value="code" onclick='<%="javascript:clickedNegativeMarking(this.form,this.name," + index.toString() +");"%>'></html:radio>
							</logic:notEqual>
							<logic:equal name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="Y">
								<html:radio property="selfAssesmentYesNoFlag" idName="record" value="code" disabled="true"></html:radio>
							</logic:equal>
						</logic:notEqual>					
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:radio property="selfAssesmentYesNoFlag" idName="record" value="code" disabled="true"></html:radio>
						</logic:equal>
						<bean:write name="record" property="engDescription"/>						
					</logic:iterate>
				</td>					
			</tr>-->
			<tr>
				<td colspan="2"><fmt:message key="page.instruction4"/>&nbsp;</td>	
			</tr>
		</logic:equal>		
		</sakai:group_table>	
		<logic:equal name="assessmentCriteriaForm" property="survey" value="false">
			<sakai:heading>
				<fmt:message key="heading.assignment.sectionB"/>
			</sakai:heading>
			<sakai:group_table>	
			<tr>
				<td width="150"><fmt:message key="page.assignment.weight"/>&nbsp;</td>
				<td>
				<sakai:group_table>
					<tr>
						<td><fmt:message key="page.assignment.weight.normal"/></td>
						<td>
							<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<logic:notEqual name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.normalWeight" size="3" maxlength="3" onchange="javascript:onChangeNormalWeight();"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.normalWeight" size="3" maxlength="3" disabled="true"/>
								</logic:equal>
							</logic:notEqual>
							<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<html:text name="assessmentCriteriaForm" property="assignment.normalWeight" size="3" maxlength="3" disabled="true"/>
							</logic:equal>
						</td>
						<td>
							<fmt:message key="page.assignment.weight.normal.note"/>
						</td>
					</tr>
					<tr>
						<td>
						<fmt:message key="page.assignment.weight.repeat"/></td>
						<td>
							<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<logic:notEqual name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.repeatWeight" size="3" maxlength="3"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.repeatWeight" size="3" maxlength="3" disabled="true"/>
								</logic:equal>
							</logic:notEqual>
							<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<html:text name="assessmentCriteriaForm" property="assignment.repeatWeight" size="3" maxlength="3" disabled="true"/>
							</logic:equal>
						</td>
						<td>
							<fmt:message key="page.assignment.weight.repeat.note"/>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="page.assignment.weight.aegrotat"/></td>
						<td>
							<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<logic:notEqual name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.aegrotatWeight" size="3" maxlength="3"/>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
									<html:text name="assessmentCriteriaForm" property="assignment.aegrotatWeight" size="3" maxlength="3" disabled="true"/>
								</logic:equal>
							</logic:notEqual>
							<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<html:text name="assessmentCriteriaForm" property="assignment.aegrotatWeight" size="3" maxlength="3" disabled="true"/>
							</logic:equal>
						</td>
						<td>
							<fmt:message key="page.assignment.weight.aegrotat.note"/>
						</td>
					</tr>
				</sakai:group_table>
				</td>
			</tr>
			<tr>
				<td width="150"><fmt:message key="page.assignment.optionality"/></td>
				<td>			
					<sakai:group_table>
						<logic:iterate name="assessmentCriteriaForm" property="listAssignmentOptionality" id="record">
							<tr>
								<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<logic:notEqual name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
										<td><html:radio property="assignment.optionality" idName="record" value="code"></html:radio></td>
									</logic:notEqual>
									<logic:equal name="assessmentCriteriaForm" property="yearMarkContributionYesNoFlag" value="N">
										<td><html:radio property="assignment.optionality" idName="record" value="code" disabled="true"></html:radio></td>
									</logic:equal>
								</logic:notEqual>
								<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
									<td><html:radio property="assignment.optionality" idName="record" value="code" disabled="true"></html:radio></td>
								</logic:equal>
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>
			</tr>
			</sakai:group_table>	
		</logic:equal>		
		<sakai:actions>
			<logic:notEqual name="assessmentCriteriaForm" property="fromSystem" value="STUDENT">
				<html:submit property="act">
						<fmt:message key="button.continue"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.cancel"/>
				</html:submit>		
			</logic:notEqual>	
			<logic:equal name="assessmentCriteriaForm" property="fromSystem" value="STUDENT">	
				<!--<logic:equal name="assessmentCriteriaForm" property="assignment.onscreenMarkFlag" value="Y">
					<html:submit property="act">
						<fmt:message key="button.continue"/>
					</html:submit>
				</logic:equal>-->			
				<html:submit property="act">
						<fmt:message key="button.back"/>
				</html:submit>			
			</logic:equal>		
		</sakai:actions>	
	</html:form>
</sakai:html>