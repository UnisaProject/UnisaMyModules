<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.adobedownload.ApplicationResources"/>

<sakai:html>

<script type="text/javascript">

function run()
{
	 setTimeout(disables, 1); 
}


function disables()
{

	if(document.getElementById("agree").checked) 
	{
		document.getElementById("agreeBtn").disabled=true;
		
	}
	else
	{
		document.getElementById("agreeBtn").disabled=false;
	}
}

</script>

<html:form action="/addcourse"> 


<sakai:messages/>
	<sakai:messages message="true"/>
<html:img src="/library/skin/unisa/images/unisa-logo.png" align="right" /><br/><br/>
<p  style="font-family:Calibri;font-size:20px;"><b>Software License Agreement</b></p>

<ul>
<li>The installation of the software will be done on one computer or laptop for UNISA official business use as determined by the users supervisor.</li>
<li>All installation sets remain the property of UNISA and may not be copied nor further distributed.</li>
<li>Access to the Software will be given to UNISA staff only, and any person(s) not associated with UNISA may not use the Software.</li>
<li>When the user is no longer employed by UNISA or if the software license is not renewed, the software will be uninstalled by the user and not be used for any purpose whatsoever.</li>
<li>In no event will UNISA be liable to the user for loss of profits or for incidental, special or consequential damages in connection with the Software or the delivery, installation, servicing, performance or use of it in combination with other computer software.</li>			
</ul>
<br/>
Any person with illegal or unauthorized programmes and software, or copies thereof, installed on their computers or computers under 
their control shall be liable in their personnel capacity and UNISA shall incur no liability and the undersigned indemnifies UNISA against any and all
claims that directly or indirectly arise from the activities and/or the conduct of the undersigned person in the event of loading directly or 
indirectly any computer of UNISA with illegal and unauthorized programmes and software or copies thereof.

<p style="font-family:Calibri;font-size:14px;"><b>Clicking Accept button will download the software into your machine.</b></p>


</p>

	<tr>
		<td>
			<html:radio styleId="agree" name="courseForm" property="agreeTerms" value="Y" >
				<fmt:message key="licence.radio.agree"/>
			</html:radio>
		</td>	
	</tr>		<br/>
	<tr>			
		<td>
			<html:radio  name="courseForm" property="agreeTerms" value="N">
				<fmt:message key="licence.radio.dontagree"/>
			</html:radio>
		</td>
	</tr>

	
  <br/>
 
 
<sakai:actions>

<div style="text-align:center"> 

  <!-- 	<html:submit property="action" onclick="disables()">
		<fmt:message key="licence.button.accept" />	
	</html:submit>  -->
	
 	 <html:submit styleId="agreeBtn" styleClass="active" property="action" onclick="run()" ><fmt:message key="licence.button.accept"/></html:submit>  
 
 		<!-- <input type="button" id="agreeBtn"  onclick="disables()" value="Accept and download"/> -->
	
	
	<html:submit property="action" >
		<fmt:message key="licence.button.cancel" />
	</html:submit>
	</div>
	
</sakai:actions>
	
</html:form>
<html:img src="/library/skin/unisa/images/unisa-footer.png" align="right" /><br/><br/>

</sakai:html>























