<%@ page import="za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>
<head>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.12.4.min.js" />"></script>

	<script type="text/javascript">
		
		$(document).ready(function() {
		
			$('input[name="deliveryType"]:radio').change(function(){
				var myRadio = $(this).attr("value");
				if (myRadio == "C" || myRadio == "N" || myRadio == "D" || myRadio == "M"){
		            alert('You will be notified, via SMS, as soon as the study material is available for collection at the campus you selected. \nYou have two working days to collect the study material. \nIf you do not collect the study material in the allotted time the study material will be posted to your postal address.');
		   	 	}else if (myRadio == "P"){
		        	alert('Should there be a SAPO strike, the University will have no other option but to courier your study material to your physical address. \nYou are requested to ensure that the details reflected on your courier address, as well as your contact numbers are correct.\nOnce you have received your confirmation of registration and have claimed your myLife e-mail address and myUnisa account, you will also be able to download your study material from myUnisa.');
		        }else {
		        	alert("If you select the courier dispatch option, please ensure that someone is available at the address to receive your study material. "
		    	  			+ "PLEASE NOTE that you are liable for any additional costs if you fail to provide the correct address information that may result in the non-delivery/incorrect delivery of study material. "
		    		        + "(Please note that the courier delivery of your first registration parcel is free of charge.)");
		        }
			});
		});
	
  	</script>  
</head>
	        
<%
RegDetailsForm stuRegForm = (RegDetailsForm)session.getAttribute("regDetailsForm");
String hidStuNo = stuRegForm.getStudentNr();
%>

<html:form action="/additions" >
	<html:hidden property="goto" value="5"/>
	<input type="hidden" name="stuNo" id="stuNo" value="<%=hidStuNo%>"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
<br/>
		<sakai:group_heading><fmt:message key="page.heading.step4"/></sakai:group_heading>
		<sakai:group_table>
		<tr><td><fmt:message key="page.qual"/></td>
			<td colspan='4'><bean:write name="regDetailsForm" property="newQual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.qualDesc"/></td>
		</tr>
		<logic:equal name="regDetailsForm" property="newQual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td colspan='4'><fmt:message key="page.additions.nospec"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="newQual.specCode" value="">
		<tr>
			<td><fmt:message key="page.spec"/></td>
			<td colspan='4'><bean:write name="regDetailsForm" property="newQual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.specDesc"/></td>
		</tr>
		</logic:notEqual>

	<logic:notEmpty name="regDetailsForm" property="additionalStudyUnits">
		<tr>
			<td><strong><fmt:message key="table.heading.code"/></strong></td>
			<td><strong><fmt:message key="table.heading.semester"/></strong></td>
			<td><strong><fmt:message key="table.heading.language"/></strong></td>
			<!-- <td><strong><fmt:message key="table.heading.ndp"/></strong></td>-->
			<td>&nbsp;</td>
		</tr>
		<logic:iterate name="regDetailsForm" property="additionalStudyUnits" id="additionalStudyUnits" indexId="index">
			 <logic:present name="additionalStudyUnits" property="code">
			 	<tr>
			 		<td><bean:write name="additionalStudyUnits" property="code"/>&nbsp;-&nbsp;<bean:write name="additionalStudyUnits" property="desc"/>&nbsp;</td>
					<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>' value="closed">
			 				<td><fmt:message key="page.step3.noopen"/></td>
			 				<td colspan="2">&nbsp;</td>
			 		</logic:equal>
			 		<logic:notEqual name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>' value="closed">
			 		<td>
			 			<logic:notEqual name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
			 				<html:select property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>'>
			 					<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod1" %>' value="true">
									<html:option value="1">1st semester</html:option>
								</logic:equal>
								<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod2" %>' value="true">
									<html:option value="2">2nd semester</html:option>
								</logic:equal>
								<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod0" %>' value="true">
									<html:option value="0">Year module</html:option>
								</logic:equal>
								<!-- 
									<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod6" %>' value="true">
										<html:option value="6">Cycle 2</html:option>
									</logic:equal>
								 -->
							</html:select>&nbsp;
						</logic:notEqual>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
								<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod1" %>' value="true">
									1st semester
								</logic:equal>
								<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod2" %>' value="true">
									2nd semester
								</logic:equal>
								<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod0" %>' value="true">
									Year module
								</logic:equal>
								<!-- 
									<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod6" %>' value="true">
										Cycle 2
									</logic:equal>
								 -->
						</logic:equal>
					<td>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="E2">English</html:option>
								<html:option value="A2">Afrikaans</html:option>
							</html:select>&nbsp;
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="E">
							English
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="A">
							Afrikaans
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="E2">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="E2">English</html:option>
								<html:option value="A2">Afrikaans</html:option>
							</html:select>&nbsp;
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="A2">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="A2">Afrikaans</html:option>
								<html:option value="E2">English</html:option>
							</html:select>&nbsp;
						</logic:equal>
					</td>
				<!-- 
					<td>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].ndp" %>' value="N">
							<html:select property='<%= "additionalStudyUnits[" + index + "].ndp" %>'>
								<html:option value="N">No</html:option>
								<html:option value="Y">Yes</html:option>
							</html:select>&nbsp;
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].ndp" %>' value="Y">
							<html:select property='<%= "additionalStudyUnits[" + index + "].ndp" %>'>
								<html:option value="Y">Yes</html:option>
								<html:option value="N">No</html:option>
							</html:select>&nbsp;
						</logic:equal>
					</td>
				-->
					</logic:notEqual>
					<td><html:submit property='<%= "action.remove[" + index + "]" %>'><fmt:message key="button.remove"/></html:submit></td>
				</tr>
			</logic:present>
		</logic:iterate>
	</logic:notEmpty>
	<tr>
		<td colspan="5"><fmt:message key="page.step3.complete"/></td>
	</tr><tr>
		<td colspan="5"><html:radio property="completeQual" value="N"/><fmt:message key="page.no"/>&nbsp;&nbsp;<html:radio property="completeQual" value="Y"/><fmt:message key="page.yes"/></td>
	</tr><tr>
		<td colspan="5"><fmt:message key="page.step4.delivery"/></td>
	</tr>
	<tr>
		<td colspan="5">
			<html:radio property="deliveryType" value="C"/><fmt:message key="page.counterSunnyside"/><br/>
	   		<html:radio property="deliveryType" value="N"/><fmt:message key="page.counterFlorida"/><br/>
	   		<html:radio property="deliveryType" value="D"/><fmt:message key="page.counterDurban"/><br/>
	   		<html:radio property="deliveryType" value="M"/><fmt:message key="page.counterPietermaritzburg"/><br/>
			<html:radio property="deliveryType" value="P" /><fmt:message key="page.postal"/><br/>
	   		<html:radio property="deliveryType" value="O"/><fmt:message key="page.courier"/><br/>
	   	</td>
	</tr>

 
</sakai:group_table>


	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
</html:form>

</sakai:html>