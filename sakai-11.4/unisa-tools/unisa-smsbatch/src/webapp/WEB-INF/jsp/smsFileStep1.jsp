<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>
<sakai:html>
<script type="text/JavaScript" language="JavaScript">		
			function textCounter(field, maxlimit) {
				if (field.value.length > maxlimit)
					field.value = field.value.substring(0, maxlimit);
			}
			
			function isValidateSMSChar(evt){
				var keyCode = evt.charCode || evt.keyCode;	
				return (keyCode >=48 && keyCode<=57 ||		// allow 0-9
						keyCode >=97 && keyCode<=122 ||		// allow a-z 
						keyCode >=65 && keyCode<=90 ||		// allow A-Z
						keyCode >=40 && keyCode<=47 ||		// allow ( ) * + , - . / 
						keyCode >=58 && keyCode<=63 ||		// allow : ; < = > ?
						keyCode >=35 && keyCode<=36 ||		// allow #  $
						keyCode >=32 && keyCode<=33 ||		// allow SP !
						keyCode ==64 || 					// allow @
						keyCode ==37 ||						// allow %
						keyCode ==8 || 						//backspace
						keyCode ==9 ); 						//tab
			}
			
			function validateSMSChar(key){
				
				if(key.match(/[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/)) {			
					key=key.replace(/[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/g,'');			
				}	
				return key;
			}
			
			function setValidSMSChar(textfield){
			    var regex = /[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/gi;
			    if(textfield.value.search(regex) > -1) {
			                    alert('Please note! Your message contains invalid characters which will be removed. Please verify that message is correct before continuing.');
			                    textfield.value = textfield.value.replace(regex, "");
				}
			}
		</script>
  
  <html:form action="/smsFile" enctype="multipart/form-data">
  <sakai:heading><fmt:message key="page.file.heading"/></sakai:heading>   
  	<html:hidden property="page" value="fileStep1"/>
	<sakai:messages/>
	<sakai:messages message="true"/>	
  	<table>
  		<tr>
  			<td><i><fmt:message key="page.instruction2"/></i></td></tr>
    	<tr>
    </table>
  	<table>
  		<tr>
			<td valign="top"><fmt:message key="page.control.cell"/>&nbsp;</td>
			<td colspan="2">
				<logic:iterate name="smsBatchForm" property="controlCellNumberList" id="record" indexId="index">
					<html:text name="smsBatchForm" property='<%= "controlCellNumberList[" + index.toString() + "]"%>' size="25" maxlength="20"/><br/>
				</logic:iterate>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2"><fmt:message key="page.cell.example"/>&nbsp;</td>
		</tr>
		<logic:equal name="smsBatchForm" property="department" value="ICT">
		<tr>
			<td><fmt:message key="page.request.reason"/>&nbsp;</td>
			<td  colspan="2"><html:select property="reasonGc27">
					<html:options collection="reasonList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		</logic:equal>		
		<tr>
			<td><fmt:message key="page.message"/>&nbsp;</td>
			<td><html:textarea name="smsBatchForm" property="message" cols="45" rows="4"  onkeypress="javascript:return isValidateSMSChar(event);"  onkeyup="javascript:return isValidateSMSChar(event);" 
  				onchange="javascript:setValidSMSChar(this.form.message);"/>
			</td>
			<!--<td><html:textarea name="smsBatchForm" property="message" cols="45" rows="4"  onkeypress="javascript:this.value=validateSMSChar(this.value);"  onkeyup="javascript:this.value=validateSMSChar(this.value);"
  				onchange="javascript:setValidSMSChar(this.form.message);"/>
			</td>  -->
			<td><i><fmt:message key="page.note.message"/></i></td>
		</tr>		
  		<tr>
  			<td><fmt:message key="page.fileContent.criteria"/></td><td>&nbsp;</td>
  		</tr>
  		<logic:equal name="smsBatchForm" property="dcmUser" value="true">
	  		<tr>
				<td>&nbsp;</td><td colspan="2"><html:radio property="fileContentType" value="STUDNUM"/><fmt:message key="page.studentNumbers"/></td>
			</tr><tr>	
				<td>&nbsp;</td><td  colspan="2"><html:radio property="fileContentType" value="CELLNUM"/><fmt:message key="page.cellNumbers"/></td>
			</tr><tr>
				<td><fmt:message key="page.dcm.upload.file"/></td>
				<td colspan="2"><html:file name="smsBatchForm"  property="theFile"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td  colspan="2"><fmt:message key="page.dcm.upload.file.note"/>&nbsp;</td>
			</tr>
		</logic:equal>
		<logic:equal name="smsBatchForm" property="dcmUser" value="false">
			<html:hidden property="fileContentType" value="STUDNUM"/>
	  		<tr>
				<td><fmt:message key="page.upload.file"/></td>
				<td colspan="2"><html:file name="smsBatchForm"  property="theFile"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td  colspan="2"><fmt:message key="page.upload.file.note"/>&nbsp;</td>
			</tr>
		</logic:equal>
  	</table>
  	<br/>
  	<html:submit property="act"><fmt:message key="button.upload"/></html:submit>
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>	
  </html:form>
</sakai:html>
