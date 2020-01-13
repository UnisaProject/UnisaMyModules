<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.adobedownload.ApplicationResources"/>
<sakai:html>
<script language="javascript" type="text/javascript">
		
	   function onloading()
	   {
		   document.forms[0].countdown.style.visibility = "hidden";
  	  	   document.forms[0].downloadOtherReason.style.visibility = "hidden";
	       document.getElementById("0001").style.visibility = "hidden";
	       document.getElementById("0002").style.visibility = "hidden";
	   }
	
       function limitText(limitField, limitCount, limitNum) 
       {
             if (limitField.value.length > limitNum) 
             {
                    limitField.value = limitField.value.substring(0, limitNum);
             } 
             else 
             {
                    limitCount.value = limitNum - limitField.value.length;
             }
       }
       
       function hideTextBox(downloadReason)
       {
    	   var valFromDropDown = downloadReason.value;
    	   if(valFromDropDown == 'Other')
    	   {
    	  	 document.forms[0].countdown.style.visibility = "visible";
    	     document.forms[0].downloadOtherReason.style.visibility = "visible";
    	     document.getElementById("0001").style.visibility = "visible"; 
    	     document.getElementById("0002").style.visibility = "visible"; 
    	   } 
    	   else
    	   {
    	  	 document.forms[0].countdown.style.visibility = "hidden";
    	  	 document.forms[0].downloadOtherReason.style.visibility = "hidden";
	    	 document.getElementById("0001").style.visibility = "hidden";
	    	 document.getElementById("0002").style.visibility = "hidden"; 
    	   }
       }
       
       function valueAvailablity()
       {
    	   
    	   if(document.forms[0].downloadReason.value == 'Other')
    	   {
    	  	 if(document.forms[0].downloadOtherReason.value.length == 0)
    	  	 {		
    	  		document.forms[0].downloadOtherReason.style.borderColor = "red";
    	  		document.forms[0].downloadOtherReason.style.backgroundColor = "lightyellow";
    	  		return false;	
    	  	 }
    	   }
    	   
       }
      
</script>

<html:form action="/addcourse"> 
<sakai:messages/>
<sakai:messages message="true"/>

<sakai:instruction>
<fmt:message key="reason.info"/>
</sakai:instruction>
	<fmt:message key="required"/> <sakai:required/>
	<p>
	<table>
			<tr>
					<td><fmt:message key="download.reason"/> <sakai:required/></td>
					<td>
						<html:select property="downloadReason" onchange="javascript:hideTextBox(this)">
								<html:options collection="reasonsOptions" property="value" labelProperty="label"/>
						</html:select>
					</td>
				</tr>
				<p>
                    
                    
                </p>
                    <tr >   
                           <td id="0001"><fmt:message key="download.other.reason"/> <sakai:required/> </td>
                           <td><html:textarea property="downloadOtherReason" onkeydown="limitText(this.form.downloadOtherReason,this.form.countdown,100);"
                           onkeyup="limitText(this.form.downloadOtherReason,this.form.countdown,100);"/></td>

                           <td><html:text readonly="true" name="countdown" property="countdown" size="3" value="100"/></td>
                           <td id="0002"><fmt:message key="other.reason.characterremaining"/></td>
                    </tr> 
				
					
	</table>
	
	<sakai:actions>
		<html:submit property="action" onclick="valueAvailablity()">
		<fmt:message key="reason.button.ok" />
		</html:submit>
		<html:submit property="action">
		<fmt:message key="licence.button.back" />
		</html:submit>
	</sakai:actions>			
</html:form>	

<script language="javascript" type="text/javascript">
		
window.onload = function onloading()
	   {
	 	if(document.forms[0].downloadReason.value == 'Other')
		   {
		 	   document.forms[0].countdown.style.visibility = "visible";
	  	  	   document.forms[0].downloadOtherReason.style.visibility = "visible";
		       document.getElementById("0001").style.visibility = "visible";
		       document.getElementById("0002").style.visibility = "visible";
		       document.forms[0].downloadOtherReason.style.borderColor = "red";
   	  		   document.forms[0].downloadOtherReason.style.backgroundColor = "lightyellow";
		   }
	 	else
	 	   {
		 	   document.forms[0].countdown.style.visibility = "hidden";
	  	  	   document.forms[0].downloadOtherReason.style.visibility = "hidden";
		       document.getElementById("0001").style.visibility = "hidden";
		       document.getElementById("0002").style.visibility = "hidden";
		   }
	   }

</script>

</sakai:html>