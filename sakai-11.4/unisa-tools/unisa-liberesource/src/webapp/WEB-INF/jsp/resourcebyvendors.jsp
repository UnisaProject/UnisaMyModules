<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.liberesource.ApplicationResources"/>

<sakai:html>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<body>
<html:form action="/resource"> 
<!--link rel="stylesheet" type="text/css" href="http://www.unisa.ac.za/stylesheets/lib_e_resources.css" /-->	
<link rel="stylesheet" type="text/css" href="https://my.unisa.ac.za/stylesheets/lib_e_resources.css" />

	<table class="pda_main_table" width="100%">
		<tr>
			<td>	
				<ul id="tabs">
					<logic:notEmpty name="libmainForm" property="tabs">
						<logic:iterate name="libmainForm" property="tabs" id="record" indexId="i">
							<c:if test="${record.selected == 'false'}"> 
								<li>
									<a href="${record.link}">
									  	<span><bean:write name="record" property="name" /></span>
									</a>
								</li>
							</c:if>
							<c:if test="${record.selected == 'true'}"> 
								<li class="selectedTab">
									<a href="${record.link}">
									  	<span><bean:write name="record" property="name" /></span>
									</a>
									
								</li>
							</c:if>
						</logic:iterate>
					</logic:notEmpty>
			  	  <li>
			  	  <form name="guideform">
			  	  <select style="top: 68; left: 643; position: absolute; z-index: 1" onChange="document.location.href=this.options[this.selectedIndex].value" name="guidelinks"> 
					  <option selected="">--more--</option>
					  	<logic:notEmpty name="libmainForm" property="moreTabsList">
						<logic:iterate name="libmainForm" property="moreTabsList" id="record" indexId="i">
						<option value="${record.link}">
						<bean:write name="record" property="name" />
						</option>
						</logic:iterate>
						</logic:notEmpty>
					</select>
					</form>
			  	  </li>
			  	</ul>
		   </td>
	  	</tr> 
	
	
	<tr>
    <td>
    	<div class="heading">
         <fmt:message key="info2"/>
         </div>
         <div class="instruction">
         Click on the Subject to view the e-resources for that subject
         </div>
     </td>
     </tr>
       	
	  	
	<tr>
	<td>
    	<div class="subject-list">
        	<ul>
            	<logic:notEmpty name="libmainForm" property="vendordataList">
				<logic:iterate name="libmainForm" property="vendordataList" id="record" indexId="i">
                     <li><html:link href="resource.do?act=eresourcebySpecific&amp;status=main&amp;place=vend&amp;heading=${record.vendorName}&amp;selectedVendorId=${record.vendorId}"><bean:write name="record" property="vendorName"/></html:link></li>
                </logic:iterate>
				</logic:notEmpty>
             </ul>
        </div>
     </td>
	</tr>
	  	
		

	</table>
</html:form>
</body>
</sakai:html>