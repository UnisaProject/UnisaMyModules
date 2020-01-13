<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<sakai:html>
<script type="text/JavaScript" language="JavaScript">

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

  <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
  <html:form action="/smsbatch">
  	<html:hidden property="page" value="2"/>

  	<table>
  		<tr>
  			<td><i><fmt:message key="page.instruction2"/></i></td></tr>
    	<tr>
    </table>

  	<p><font color="red"><html:errors/></font></p>

  	<table>
  		<tr>
			<td style="white-space:nowrap;"><fmt:message key="page.academic.year"/>&nbsp;</td>
			<td colspan="2"><html:text name="smsBatchForm" property="academicYear"/></td>
		</tr>
		<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
		<tr>
    		<td><fmt:message key="page.registration.period"/>&nbsp;</td>
			<td colspan="2"><html:select property="registrationPeriod">
					<html:options collection="regPeriodList" property="value" labelProperty="label"/>
				</html:select>
			</td>
    	</tr>
    	</logic:equal>  		
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
			<td colspan="2"><html:select property="reasonGc27">
					<html:options collection="reasonList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="page.message"/>&nbsp;</td>
			<td><html:textarea name="smsBatchForm" property="message" cols="45" rows="4"  onkeypress="javascript:return isValidateSMSChar(event);"  onkeyup="javascript:return isValidateSMSChar(event);"
  onchange="javascript:setValidSMSChar(this.form.message);" />	
			<!-- <td><html:textarea name="smsBatchForm" property="message" cols="45" rows="4"  onkeypress="javascript:this.value=validateSMSChar(this.value);"  onkeyup="javascript:this.value=validateSMSChar(this.value);"
  onchange="javascript:setValidSMSChar(this.form.message);" /> -->			
  <!--<td><html:textarea name="smsBatchForm" property="message" cols="45" rows="4"  onkeypress="if(this.value.match(/[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/)) this.value=this.value.replace(//[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/g,'')"
 onkeyup   ="if(this.value.match(/[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/)) this.value=this.value.replace(/[^A-Za-z 0-9!@#$%()-:;,./?*+<>=]/g,'')"
  onchange="javascript:setValidSMSChar(this.form.message);" />  -->
			</td>
			<td><i><fmt:message key="page.note.message"/></i></td>
		</tr>
		<!-- reg criteria -->
		<tr>
    		<td colspan="3"><strong><fmt:message key="page.registration.criteria"/>&nbsp;</strong></td>
    	</tr>
		<logic:equal name="smsBatchForm" property="regCriteriaType" value="A">
		<tr>
    		<td colspan="3"><fmt:message key="page.all"/></td>
    	</tr>
    	</logic:equal>
    	<logic:notEqual name="smsBatchForm" property="regCriteriaType" value="A">
    		<tr>
    			<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
    				<td colspan="3"><i><fmt:message key="page.instruction.qualification"/></i>
    			</logic:equal>
    			<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
    				<td colspan="3"><i><fmt:message key="page.instruction.module"/></i>
    			</logic:equal>
    		</tr>
    		<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
    			<tr>
    				<td colspan="3"><i><fmt:message key="page.note.qualification"/></i></td>    			
    			</tr>
    		</logic:equal>
    		<tr>
    			<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
    				<td style="white-space:nowrap;"><fmt:message key="page.qualification.code"/>&nbsp;
    			</logic:equal>
    			<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
    				<td  style="white-space:nowrap;"><fmt:message key="page.module.code"/>&nbsp;
    			</logic:equal>
    			<td  colspan="2"><html:text name="smsBatchForm" property="selectedItems" size="60" maxlength="160"/>
				</td>				
    		</tr>    		
    		<tr>
    			<td>&nbsp;</td>
    			<td colspan="2"><html:submit property="act"><fmt:message key="button.searchModule"/></html:submit></td>
    		</tr>
    	</logic:notEqual>
		<!-- geo criteria -->
    	<tr>
    		<td colspan="3"><strong><fmt:message key="page.geo.criteria"/>&nbsp;</strong></td>
    	</tr>
    	<logic:equal name="smsBatchForm" property="geoCriteriaType" value="A">
    	<tr>
    		<td colspan="3"><fmt:message key="page.all"/></td>
    	</tr>
    	</logic:equal>
    	<logic:equal name="smsBatchForm" property="geoCriteriaType" value="S">
    	<tr>
    		<td colspan="3"><fmt:message key="page.all.rsa"/></td>
    	</tr>
    	</logic:equal>
    	<logic:notEqual name="smsBatchForm" property="geoCriteriaType" value="A">
    	<logic:notEqual name="smsBatchForm" property="geoCriteriaType" value="S">
    		<tr>
    			<td colspan="3"><i><fmt:message key="page.instruction3"/></i></td>
    		</tr><tr>
    			<logic:equal name="smsBatchForm" property="geoCriteriaType" value="C">
    				<td  style="white-space:nowrap;"><fmt:message key="page.countries"/>&nbsp;</td>
    			</logic:equal>
    			<logic:equal name="smsBatchForm" property="geoCriteriaType" value="R">
    				<td  style="white-space:nowrap;"><fmt:message key="page.regions"/></td>
				</logic:equal>
    			<logic:equal name="smsBatchForm" property="geoCriteriaType" value="M">
    				<td  style="white-space:nowrap;"><fmt:message key="page.mag.districts"/></td>
    			</logic:equal>
    			<td colspan="2"><html:select property="geoSelection" multiple="true"  size="5">
						<html:options collection="geoList" property="value" labelProperty="label"/>
					</html:select>
				<td>
			</tr>
		</logic:notEqual>
		</logic:notEqual>
  	</table>
  	<br/>
  	<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
	<html:submit property="act"><fmt:message key="button.back"/></html:submit>
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
  </html:form>
</sakai:html>
