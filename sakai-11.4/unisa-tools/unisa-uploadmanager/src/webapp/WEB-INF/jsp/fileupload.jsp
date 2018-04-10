<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.uploadmanager.ApplicationResources"/>

<sakai:html>
 <head>
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.10.2.min.js"></script>    
<!--[if IE ]>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.tooltipFix.js"></script>
 <![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery-ui-1.10.2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fieldValidation.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
 <style type="text/css">
  .browseFile input {
    border: 1px solid black;
  }
  
ul {
 list-style: none;
 margin-left: 0;
 padding-left: 1em;
 text-indent: 2em;
 }
 
 ul li:before {
 content: "\0BB \020";
 }
 
 li {
    margin: 6px;
}

.no-close .ui-dialog-titlebar-close {
  display: none;
 }

</style> 
      
    </head>

	<html:form action="/fileuploadmanager.do" enctype="multipart/form-data" method="POST">
	<html:hidden property="atStep" value="${uploadManagerForm.method}"/>

	<sakai:messages/>
    <sakai:messages message="true"/>

  <sakai:heading><b>Upload/Reload Screen</b></sakai:heading><br>
  <tr>
  
  
<%--   <td>Module code <bean:write name="uploadManagerForm" property="mod_code"/></td><br>
  <td>Item barcode: <bean:write name="uploadManagerForm" property="barcode"/></td><br>
  <td>Item description:  <bean:write name="uploadManagerForm" property="file_name"/></td><br>
  <td>Item Long Description:  <bean:write name="uploadManagerForm" property="itemfulldesc"/></td><br>
  <td>Type: <bean:write name="uploadManagerForm" property="doc_type"/></td><br>
  <td>Period: <bean:write name="uploadManagerForm" property="materialPeriod"/></td><br>
  <td>Language: <bean:write name="uploadManagerForm" property="lang"/></td><br><br>
  <tr><td></td> --%>
  
  <ul>
  <li>Module code: <bean:write name="uploadManagerForm" property="mod_code"/></li>
  
  <li>Item barcode: <bean:write name="uploadManagerForm" property="barcode"/></li>
  
  <li>Item description:  <bean:write name="uploadManagerForm" property="file_name"/></li>
  
  <li>Item Long Description:  <bean:write name="uploadManagerForm" property="itemfulldesc"/></li>
  
  <li>Type: <bean:write name="uploadManagerForm" property="doc_type"/></li>
  
  <li>Period: <bean:write name="uploadManagerForm" property="materialPeriod"/></li>
  
  <li>Language: <bean:write name="uploadManagerForm" property="lang"/></li>
  
  </ul>
  
  
  </tr>
  
  
  
  <sakai:group_table>
   <tr><td>
 

      <div class="browseFile">
      <label class="">Select a document (200MB Max) &nbsp;<fmt:message key="prompt.mandatory"/></label>
      
      <div><html:file property="file"/></div>
      
      </div>
 
 </td></tr>
</sakai:group_table>
  	  <sakai:group_table>
 
  	   <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.upload"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		     <td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.cancel"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
  	   	 <html:hidden property="from" value="${uploadManagerForm.from}"/>
 	 <html:hidden property="userId" value="${uploadManagerForm.userId}"/>
  	  </sakai:group_table>
  	  
	<c:if test="${uploadManagerForm.from == 'staff'}">
	<div id="dialog-timeout-reload" title="Page Reload Alert" style="display: none;">Tool is refreshing after idle for long time</div> 
 	 	<script type="text/javascript">
var idleTime = 0;
$(document).ready(function () {
    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
});

function timerIncrement() {
    idleTime = idleTime + 1;
    if (idleTime > 8) { // 8 minutes
    	var str = window.location;
        var lastPos = String(str).lastIndexOf("/");
        var url = String(str).substring(0, lastPos+1);
       $("#dialog-timeout-reload").dialog("open");
       setTimeout(function(){ window.location = url+"default.do?userId="+document.getElementsByName("userId")[0].value+"&from=staff"; }, 2000);
    }
}


$(function() {
    $("#dialog-timeout-reload").dialog({
       autoOpen: false, 
       dialogClass: "no-close",
       modal: true,
       height : 50
    }).dialog("widget").find(".ui-dialog-titlebar").hide();;
    
 });


</script>   
 	
 	 </c:if> 
  	  
   </html:form>
   </sakai:html>