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
<style>
.no-close .ui-dialog-titlebar-close {
  display: none;
 }
</style>
</head>

	<html:form action="/uploadmanager">

	
<%-- 	   <div id="hcm_busy_div" style="padding:10px; text-align: center; display: none; height: 140px">	
 <div style="font: bold;color: blue;">Loading,please wait </div>
	<div style="height: 140px;"><img src='${pageContext.request.contextPath}/images/ajax-loader.gif' alt="busy"/></div>
</div> --%>
<%@ include file="busybar.jsp"%>
	<html:hidden property="atStep" value="1"/>	
	
   <sakai:group_heading>Study Material Management</sakai:group_heading>
   	<p>
   
   	
	<sakai:messages/>
	<sakai:messages message="true"/>
    
<%-- <html:link href="javascript:void(0);" onclick="ajaxCall('uploadmanager.do?act=ajaxCall')">
callme
</html:link> --%>

	
	</p>
<sakai:group_table>
 <tr> 
 	 <td>Course Code&nbsp;<fmt:message key="prompt.mandatory"/></td>
 	 
 	 
 	 <td>
 	 <html:hidden property="from" value="${uploadManagerForm.from}"/>
 	 <html:hidden property="userId" value="${uploadManagerForm.userId}"/>
 	 <c:if test="${uploadManagerForm.from == 'staff'}">
 	 <bean:write name="uploadManagerForm" property="courseCode"/>
 	
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


</script>   
 	
 	 </c:if>
 	 
<%--  	 <logic:notEqual name ="uploadManagerForm" property="from" value="myUnisa">
                  <bean:write name="uploadManagerForm" property="courseCode"/>
 	 </logic:notEqual> --%>
 	  <c:if test="${uploadManagerForm.from == 'myUnisa'}">
 	<%--  <logic:equal name ="uploadManagerForm" property="from" value="myUnisa"> --%>
       <html:select name="uploadManagerForm" property="courseCode" style="width: 350px">
                 <html:option value="-1">Please Choose..</html:option>
				 <html:optionsCollection name="uploadManagerForm" property="lecturersModuleCodeList" value="value" label="label"/>
		   </html:select>  
		   </c:if>
<%--       </logic:equal> --%>
	 	</td>
  </tr> 
   <tr> 
 	   <td>Academic year&nbsp;<fmt:message key="prompt.mandatory"/></td>
       <td>
            <html:select name="uploadManagerForm" property="acadYear" style="width: 350px">
                 <html:option value="-1">Please Choose..</html:option>
				 <html:optionsCollection name="uploadManagerForm" property="fromYearList" value="value" label="label"/>
		    </html:select>  
	 	</td>
  </tr> 
   <tr> 
 	 <td>Period&nbsp;<fmt:message key="prompt.mandatory"/></td>
      <td><html:select property="period" style="width: 350px">
       <html:option value="-1">Please Choose..</html:option>
      <html:option value="1">First Semester</html:option>
      <html:option value="2">Second Semester</html:option>
      <html:option value="0">Year</html:option>
      </html:select> 
	 	</td>
  </tr> 
   <tr> 
 	 <td>Study material type</td>
     <td><html:select property="type" style="width: 350px">
      <html:option value="-1">All</html:option>
     <html:option value="TL">Tutorial Letters</html:option>
     <html:option value="GD">Study Guides</html:option>
     <html:option value="MA">Manuals</html:option>
     <html:option value="WB">Workbooks</html:option>
     <html:option value="QB">Question Bank</html:option>
     <!-- change florida module to online module -->
      <html:option value="MO">Online Modules</html:option>
     <html:option value="LB">Logbook</html:option>
     <html:option value="BL">Booklet</html:option>
     <html:option value="BB">Basic Business Calculations</html:option>
     <html:option value="HL">H Law Documents</html:option>
     <html:option value="RE">Reader</html:option>
     <html:option value="MG">Mentor Guides</html:option>	
     </html:select> 
	 	</td>
  </tr> 	
  <tr> 
 	 <td>Language&nbsp;<fmt:message key="prompt.mandatory"/></td>
     <td><html:select property="language" style="width: 350px">
     <html:option value="-1">Please Choose..</html:option>
     <html:option value="A">Afrikaans</html:option>
     <html:option value="E">English</html:option>
        <html:option value="B">Both</html:option>
           </html:select> 
	 	</td>
  </tr> 

  
</sakai:group_table>
<sakai:group_table>
     <tr><td>
		      <sakai:actions>
			      <html:submit styleClass="button" property="act" onclick="beforeSubmit();">
			           <fmt:message key="button.view"/>
			      </html:submit>
		     </sakai:actions>
		 </td>
		 <td>
		       <sakai:actions>
			       <html:submit styleClass="button" property="act" onclick="beforeSubmit();">
			             <fmt:message key="button.upload"/>
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
    <c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	   <c:forEach var="errorfield" items="${requestScope['org.apache.struts.action.ERROR'].get()}">
          <script type="text/javascript">
          unisa.uploadmanager.fielderrorFn('${errorfield.key}','${errorfield.values[0]}');
           </script>
        </c:forEach>
	</c:if>
	  <script>
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
		
/* 		(function() {
			
			ajaxCall = function(url) {
				 $.ajax({
	                  url: url,
	                  data:{},
	                  type: "get",
	                  contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
	                  success: function(data){
	                          //here response data will come and we need to update in the page.
	                       console.log("datadata"+data);   
	                          alert("datadata"+data);
	                  },
	                  beforeSend: function(){
	                        // it will execute before Ajax call start
	                                    },
	                    complete: function(){
	                 // this method will execute after success method is completed.                 
	                                    },
	                  error: function(){
	                              }
	                });
			}
			
		})(); */
		
		

	  </script>
	 
	</html:form>
</sakai:html> 