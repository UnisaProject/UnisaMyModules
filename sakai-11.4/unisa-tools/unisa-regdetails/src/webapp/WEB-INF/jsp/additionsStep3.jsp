<%@ page import="za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm"%>
<%@ page import="za.ac.unisa.lms.tools.regdetails.forms.StudyUnit"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources" />

<sakai:html>
<head>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.12.4.min.js" />"></script>

	<script type="text/javascript">

		var totalCredits=0;
		var suCode = [];
		var suCredits = [];
		var suHEQF = [];
		var suListSize = 0;
		
		$(document).ready(function() {
			
			totalCredits = parseInt($('#CreditTotal').val(), 10);
			if (totalCredits > 0){
				$("#CreditDisplay").show();
			}
			suListSize = parseInt($('#suListSize').val(), 10);
			
			for (var i=0; i<suListSize; i++){
				var selectedSU = $('#selectedAdditionalStudyUnits'+i).find(":selected").val();
				if (selectedSU != null && selectedSU != "undefined" && selectedSU != "-1"){
				    var splitSelSu = selectedSU.split("@");
				    suCode[i]    = splitSelSu[0];
				    suHEQF[i] 	 = splitSelSu[1];
				    if (suHEQF[i] === "Y"){
				    	suCredits[i] = splitSelSu[2];
				    }else{
				    	suCredits[i] = 0;
				    }
				}else{
					suCode[i] = "None";
					suCredits[i] = 0;
					suHEQF[i] = false;
				}
			}
		});
		
	  	function checkDuplicateSU(code){
	  		for (var i=0; i<suListSize; i++){
				if (suCode[i] === code){
		  			return true;
	  			}
			}
  			return false;
	  	}
	  	
	  	function removeCredit(id){
	  		for (var i=0; i<10; i++){
	  			if (id === i && suHEQF[i]){
	  				totalCredits = Number(totalCredits) - Number(suCredits[i]);
		    		suCode[i] = "";
					suCredits[i] = 0;
					suHEQF[i] = false;
	  			}
	  		}
	  		$('#CreditTotal').val(totalCredits);
	  	}

		function getStudyUnitDetail(sel, id){
		    totalCredits = parseInt($('#CreditTotal').val(), 10);
		    var suSelect = sel.options[sel.selectedIndex].value;
		    var splitSu = suSelect.split("@");
	    	var splitValue  = splitSu[0];
	    	var splitCredit = splitSu[1];
	    	var splitHEQF = splitSu[2];
		    var duplicate = checkDuplicateSU(splitValue);
		    
		    if (duplicate || splitValue === "0" || splitValue === "" || splitValue === "-1" ){
		    	if (duplicate){
		    		alert("Study unit already selected");
		    	}
		    	removeCredit(id);
		    	sel.selectedIndex = 0;
		    	return false;
		    }
		    
		    for (var i=0; i<suListSize; i++){
		  		if (id === i){
			   		suCode[i] = splitValue;
		  		}
		  	}
	    	
		    if (splitHEQF === "Y"){
		    	for (var i=0; i<suListSize; i++){
		  			if (id === i){
		  				if (totalCredits != 0 && !isNaN(suCredits[i])){
		  					totalCredits = Number(totalCredits) - Number(suCredits[i]);
		  				}
						suCredits[i] = splitCredit;
						suHEQF[i] = true;
						totalCredits = Number(totalCredits) + Number(suCredits[i]); 
		  			}
		  		}
		    	
		    	if (totalCredits > 0){
		    		$("#CreditDisplay").show();
		    	}
		    	$('#CreditTotal').val(totalCredits);
		    }else{
		    	removeCredit(id);
		    }
		}
	</script>
</head>
<%
RegDetailsForm stuRegForm = (RegDetailsForm)session.getAttribute("regDetailsForm");
String hidStuNo = stuRegForm.getStudentNr();
ArrayList<StudyUnit> suList = (ArrayList<StudyUnit>)stuRegForm.getListStudyUnits();
ArrayList<String> suSelectedList = (ArrayList<String>)stuRegForm.getSelectedAdditionalStudyUnits();
%>

<html:form action="/additions">
	<html:hidden property="page" value="3" />
	<html:hidden property="goto" value="4" />
	<input type="hidden" name="stuNo" id="stuNo" value="<%=hidStuNo%>"/>
	<input type="hidden" id="suListType" value="<bean:write name="regDetailsForm" property="listType"/>" />
	<input type="hidden" id="suListSize" value="<bean:write name="regDetailsForm" property="numberOfUnits"/>" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page.heading.additions" />
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step3.instruction.2a"/>
			<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17029' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17029', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
			<fmt:message key="page.step3.instruction.2b"/></a>
			<logic:notEqual name="regDetailsForm" property="listType" value="T">
				<fmt:message key="page.step3.instruction.2c"/><br/>
			</logic:notEqual>
			<logic:equal name="regDetailsForm" property="listType" value="T">
				<fmt:message key="page.step3.instruction.typein"/><br/>
			</logic:equal>
	</sakai:instruction>
	<sakai:group_heading>
		<logic:notEqual name="regDetailsForm" property="listType" value="T">
			<fmt:message key="page.heading.step3" />
		</logic:notEqual>
		<logic:equal name="regDetailsForm" property="listType" value="T">
			<fmt:message key="page.heading.typein" />
		</logic:equal>
	</sakai:group_heading>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="page.qual" /></td>
			<td colspan='4'><bean:write name="regDetailsForm"
				property="newQual.qualCode" />&nbsp;-&nbsp;<bean:write
				name="regDetailsForm" property="newQual.qualDesc" /></td>
		</tr>
		<logic:equal name="regDetailsForm" property="newQual.specCode" value="">
			<tr>
				<td><fmt:message key="page.spec" /></td>
				<td colspan='4'><fmt:message key="page.additions.nospec" /></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="newQual.specCode" value="">
			<tr>
				<td><fmt:message key="page.spec" /></td>
				<td colspan='4'><bean:write name="regDetailsForm"
					property="newQual.specCode" />&nbsp;-&nbsp;<bean:write
					name="regDetailsForm" property="newQual.specDesc" /></td>
			</tr>
		</logic:notEqual>

		<logic:equal name="regDetailsForm" property="listType" value="T">
		<tr>
			<td colspan='5'><fmt:message key="page.heading.typeincode" /></td>
		</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="listType" value="T">
		<tr>
			<td colspan='5'><fmt:message key="page.step3.instruction1" /></td>
		</tr>
		</logic:notEqual>
		<logic:notEqual name="regDetailsForm" property="regPeriodOpen0" value="Y">
			<tr>
				<td colspan="5"><strong><fmt:message key="page.additions.closed0" /></strong></td>
			</tr>
		</logic:notEqual>
		<logic:notEqual name="regDetailsForm" property="regPeriodOpen1" value="Y">
			<tr>
				<td colspan="5"><strong><fmt:message key="page.additions.closed1" /></strong></td>
			</tr>
		</logic:notEqual>
		<logic:notEqual name="regDetailsForm" property="regPeriodOpen2" value="Y">
			<tr>
				<td colspan="5"><strong><fmt:message key="page.additions.closed2" /></strong></td>
			</tr>
		</logic:notEqual>
		<logic:notEqual name="regDetailsForm" property="regPeriodOpen6" value="Y">
			<tr>
				<td colspan="5"><strong><fmt:message key="page.additions.closed6" /></strong></td>
			</tr>
		</logic:notEqual>
	<logic:notEqual name="regDetailsForm" property="listType" value="T">
 		<logic:present name="regDetailsForm" property="selectedAdditionalStudyUnits">
 			<tr><td colspan="5">
 				<%for (int index = 0; index < stuRegForm.getNumberOfUnits(); index++) {%>
					<select id='selectedAdditionalStudyUnits<%=index%>' name='selectedAdditionalStudyUnits<%=index%>' onchange="getStudyUnitDetail(this,<%=index%>);" >
						<option value='-1'>Select a study unit</option>
				 		<%for (int i = 0; i < suList.size(); i++) {
				 			String suCode = suList.get(i).getCode();
				 			String suDesc = suList.get(i).getDesc();
				 			int suCredits = suList.get(i).getCredits();
				 			String suHEQF = suList.get(i).getHEQF();	%>
				 			
				 			<option value="<%=suCode%>@<%=suCredits%>@<%=suHEQF%>"
				 			<%
				 			for (int t = 0; t < suSelectedList.size(); t++){
				 				String selSU = suSelectedList.get(t);
				 				if (index == t && selSU.equalsIgnoreCase(suCode)){%>
				 					selected="selected"
				 				<%}%>
	 						<%} %>><%=suCode%> - <%=suDesc%><%if("Y".equalsIgnoreCase(suHEQF)){%> - (Credits:<%=suCredits%>)<%}%></option>
	 					<%} %>
					</select>
				<%}%>
			</td></tr>
		</logic:present>
	</logic:notEqual>
	<logic:equal name="regDetailsForm" property="listType" value="T">
	 	<logic:present name="regDetailsForm" property="selectedAdditionalStudyUnits">
	 	<logic:iterate name="regDetailsForm" property="selectedAdditionalStudyUnits" id="studyUnit" indexId="index">
			<tr><td colspan="5">
				<html:text property='<%="selectedAdditionalStudyUnits[" + index + "]" %>' size="10" maxlength="7"/>
			</td></tr>
		</logic:iterate>
		</logic:present>
	 </logic:equal>
	</sakai:group_table>


	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue" />
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.back" />
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.cancel" />
		</html:submit>
	</sakai:actions>
	<div id="CreditDisplay" align="right" style="display:none">
    	Credits Selected:&nbsp;<input type="text" id="CreditTotal" name="CreditTotal" value="<bean:write name="regDetailsForm" property="creditTotal"/>" readonly="readonly" size="4"/>
    </div>
</html:form>

</sakai:html>
