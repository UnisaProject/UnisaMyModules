<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.uploadmanager.ApplicationResources"/>


<sakai:html>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.10.2.min.js"></script>     
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery-ui-1.10.2.css">
<!--[if IE ]>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.tooltipFix.js"></script>
 <![endif]-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fieldValidation.js"></script> 

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<style>

 .no-close .ui-dialog-titlebar-close {
  display: none;
 }

</style>

<!-- <style>

 .ui-widget-content {
	border: 1px solid #dddddd;
	background: #eeeeee url("${pageContext.request.contextPath}/images/ui-bg_highlight-soft_100_eeeeee_1x100.png") 50% top repeat-x;
	color: #333333;
} 
</style> -->

            
<script type="text/javascript">
    
    $(document).ready(function () {
    	  $("span.question").hover(function () {  
    	    $(this).append('<div class="tooltip"><p>Enter the first three letter of course code.</p></div>');
    	  }, function () {
    	    $("div.tooltip").remove();
    	  });
    	});

	  var _$busyDiv=_$dialogContainer= undefined;
		var _initBusyDiv = function() {
			_$busyDiv = $("#hcm_busy_div");
			_$busyDiv.dialog({
				height : 50,
				modal : true,
				autoOpen : false
			}).dialog("widget").find(".ui-dialog-titlebar").hide();
		};
		
		var beforeSubmit = function() {
			_initBusyDiv();
			_$busyDiv.dialog("open");
			return true;
		};


</script>
    


	<html:form action="/uploadmanager">
	<%-- <html:hidden property="from" value="staff"/>	 --%>
	<html:hidden property="atStep" value="3"/>	
	 	 <html:hidden property="from" value="${uploadManagerForm.from}"/>
 	 <html:hidden property="userId" value="${uploadManagerForm.userId}" />
	 <%@ include file="busybar.jsp"%>
	
<%-- 		<jsp:include page="/jsp/busybar.jsp"></jsp:include> --%>
	
<%-- 		   <div id="hcm_busy_div" style="padding:10px; text-align: center; display: none; height: 140px">	
 <div style="font: bold;color: blue;">Loading,please wait </div>
	<div style="height: 140px;"><img src='${pageContext.request.contextPath}/images/ajax-loader.gif' alt="busy"/></div>
</div> --%>

   <sakai:group_heading>Study Material Management</sakai:group_heading>
   	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
<sakai:group_table>
 <tr> 
 	 <td>Enter first part of course :</td>
 </tr>
   <tr> 
 	 <td><html:text property="searchCode" style="width: 125px"/> &nbsp;<span class="question">?</span>
 	 
 	 &nbsp;&nbsp;&nbsp;
 	 
 	 <sakai:actions>
 	   <html:submit styleClass="button" property="act" onclick="beforeSubmit();">
	    <fmt:message key="button.search"/>
       </html:submit></sakai:actions>
 	 </td>
 	 </tr>
 	 <logic:equal name ="uploadManagerForm" property="search" value="Yes">
   <tr> 
 	 <td>Click on the course code you want to select</td>
 	 </tr>
 	 <tr>
      <td><html:select property="selectedCourseCode" size="6" style="height:70;width: 150px">
      <html:options  collection="courseCodeList" property="value" labelProperty="label" />
      </html:select> 
	 	</td>
  </tr> 
   </logic:equal>
  
</sakai:group_table>
 <logic:equal name ="uploadManagerForm" property="search" value="Yes">
<sakai:group_table>
     <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act" onclick="beforeSubmit();">
			    <fmt:message key="button.next"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		     <td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act" onclick="beforeSubmit();">
			    <fmt:message key="button.cancel"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
  </sakai:group_table>
  </logic:equal>
  
      <c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	   <c:forEach var="errorfield" items="${requestScope['org.apache.struts.action.ERROR'].get()}">
          <script type="text/javascript">
             unisa.uploadmanager.fielderrorFn('${errorfield.key}','${errorfield.values[0]}');
           </script>
        </c:forEach>
	</c:if>
	
	
	
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
 
var alertReload = function() {
	  $("#dialog-timeout-reload").dialog("open");
};
 
</script>  
 	
 	 </c:if>
 
	
	<script type="text/javascript">
    
    $(document).ready(function () {
    	  $("span.question").hover(function () {  
    	    $(this).append('<div class="tooltip"><p>Enter the first three letter of course code.</p></div>');
    	  }, function () {
    	    $("div.tooltip").remove();
    	  });
    	});

	  var _$busyDiv=_$dialogContainer= undefined;
		var _initBusyDiv = function() {
			_$busyDiv = $("#hcm_busy_div");
			_$busyDiv.dialog({
				height : 50,
				modal : true,
				autoOpen : false
			}).dialog("widget").find(".ui-dialog-titlebar").hide();
		};
		
		var beforeSubmit = function() {
			_initBusyDiv();
			_$busyDiv.dialog("open");
			return true;
		};


</script>
	
	</html:form>
</sakai:html> 