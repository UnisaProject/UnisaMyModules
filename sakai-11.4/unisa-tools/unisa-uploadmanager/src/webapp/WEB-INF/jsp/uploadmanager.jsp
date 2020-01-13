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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery-ui-1.10.2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fieldValidation.js"></script> 
<!-- ui theme stylesheet - contents shown below -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.jui.css">
<!-- jQuery UI theme (cupertino example here) -->
<!-- <link rel="stylesheet" href="css/jquery-ui.min.css"> -->

<!-- tablesorter plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.tablesorter.js"></script>
<!-- tablesorter widget file - loaded after the plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.tablesorter.widgets.js"></script>
<style type="text/css">

.tablesorter-jui {
    border-collapse: separate;
    border-spacing: 2px;
    font-size: 0.9em;
}
.no-close .ui-dialog-titlebar-close {
  display: none;
 }


</style> 
</head>
	<html:form action="/uploadmanager">
 	 <html:hidden property="from" value="${uploadManagerForm.from}"/>
 	 <html:hidden property="userId" value="${uploadManagerForm.userId}"/>
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
	
	<sakai:messages/>
    <sakai:messages message="true"/>

  	  <sakai:heading><fmt:message key="uploadmanager.heading"/>&nbsp;&nbsp;<b> <bean:write name="uploadManagerForm" property="courseCode"/> </b></sakai:heading><br>

<c:choose>
      <c:when test="${not empty uploadManagerForm.materialList}">
<table  cellspacing="0" width="100%">
 	 	 <thead>
 	 	  
 	<div id="dialog-reload" title="Permission Denied to Reload" style="display: none;">You don't have privilege to Reload file.</div>
 	
 	<div id="dialog-upload" title="Permission Denied to Upload" style="display: none;">You don't have privilege to Upload file.</div>
 	 	  

	 	   	<th><fmt:message key="uploadmanager.status"/></th>
	 	   	<th><fmt:message key="uploadmanager.barcode"/></th>
	 	   	<th><fmt:message key="uploadmanager.itemdescr"/></th>
	 	   	<th><fmt:message key="uploadmanager.itemlongdescr"/></th>
	 	   	<th><fmt:message key="uploadmanager.period"/></th>
	 	    <th><fmt:message key="uploadmanager.lang"/></th>
	 	   	<th><fmt:message key="uploadmanager.type"/></th>
	 	   	<th><fmt:message key="uploadmanager.action"/></th>
	 	   	<th><fmt:message key="uploadmanager.date"/></th>
	 	   	<th><fmt:message key="uploadmanager.user"/></th>
	 	   	</thead>
	 	   		
	 	  
	 	  <logic:notEmpty name="uploadManagerForm" property="materialList">
		  <logic:iterate name="uploadManagerForm" property="materialList" id="d" indexId="dindex">
			<tbody>
			<tr>
			<td>
			<logic:equal name="d" property="docAvailability" value="true">
			<html:img page="/images/yes.gif" border="0" />  
				</logic:equal>
				<logic:notEqual name="d" property="docAvailability" value="true">
			<html:img page="/images/no.gif" border="0" />  
				</logic:notEqual>
			</td>	
			<td>
				<bean:write name="d" property="itembarcode"/>
			</td>	
			<td>
				<bean:write name="d" property="itemshortdesc"/>
			</td>	
			<td>
				<%-- <bean:write name="d" property="itemfulldesc"/> --%>
		      <c:if test="${d.itemfulldesc != 'null'}">
                    <c:out value="${d.itemfulldesc}" />
              </c:if>
			</td>	
			<td>
				<bean:write name="d" property="academicperiod"/>
			</td>
			<td>
				<bean:write name="d" property="language"/>
			</td>
				<td>
				<bean:write name="d" property="documenttype"/>
			</td>	
			<td style="text-align: center;">
          <logic:equal name="d" property="filestatus" value="true">
				
			<c:choose>
               <c:when test="${uploadManagerForm.from == 'myUnisa'  || (uploadManagerForm.from == 'staff' && uploadManagerForm.userprivilege == 'Administration Staff')}">
                   <html:link href="uploadmanager.do?act=reload&amp;barcode=${d.itembarcode}&amp;filename=${d.itemshortdesc}&amp;filesize=${d.filesize}&amp;itemDesc=${d.itemshortdesc}&amp;docType=${d.documenttype}&amp;module=${d.module}&amp;lang=${d.language}&amp;itemfulldesc=${d.itemfulldesc}">
                   <html:img page="/images/1432737060_reload.png" border="0"  align="center" width="20" height="20" title="Reload"/>
                   </html:link>&nbsp;&nbsp;
                </c:when>
                <c:otherwise>
                 <a href="javascript:void(null);" onclick="permissionDeniedReload();"><html:img page="/images/reload_Pdf.png" border="0" align="center" width="20" height="20" title="Reload"/> </a>
                </c:otherwise>
            </c:choose>
			
		  </logic:equal>
			
			<logic:notEqual name="d" property="filestatus" value="true">
				
			 <c:choose>
               
               <c:when test="${uploadManagerForm.from == 'myUnisa'  || (uploadManagerForm.from == 'staff' && uploadManagerForm.userprivilege == 'Administration Staff')}">
                   <html:link href="uploadmanager.do?act=upload&amp;barcode=${d.itembarcode}&amp;filename=${d.itemshortdesc}&amp;filesize=${d.filesize}&amp;itemDesc=${d.itemshortdesc}&amp;docType=${d.documenttype}&amp;module=${d.module}&amp;lang=${d.language}&amp;itemfulldesc=${d.itemfulldesc}">
                   <html:img page="/images/upload_Pdf.png" border="0" align="center" width="20" height="20" title="Upload" style="text-align:center"/></html:link>&nbsp;&nbsp;
                </c:when>

                <c:otherwise>
                  <a href="javascript:void(null);" onclick="permissionDeniedUpload();">
                  
                  <html:img page="/images/upload_Pdf.png" border="0" align="center" width="20" height="20" title="Upload" style="text-align:center"/>
                  
                  </a>
                </c:otherwise>
                </c:choose>
                 
                   
                   
		      </logic:notEqual>
			</td>
			<td>
				<bean:write name="d" property="dateavailable"/>
			</td>
			<td>
				<bean:write name="d" property="lecturer"/>
			</td>									
	        </tr>			
		</logic:iterate>				
	</logic:notEmpty>
</tbody>
	 
		 </table>
 </c:when>

      <c:otherwise>

	<div>There is no study material found</div>
	
      </c:otherwise>
</c:choose>



        <sakai:actions>
	      <html:submit property="act">
		        <fmt:message key="button.back"/>
		  </html:submit>
	   </sakai:actions>

   </html:form>
   <script>
   
   $(function() {
       $("#dialog-reload").dialog({
          autoOpen: false, 
          modal: true,
          buttons: {
             OK: function() {$(this).dialog("close");}
          },
       });
       $("#dialog-upload").dialog({
           autoOpen: false, 
           modal: true,
           buttons: {
              OK: function() {$(this).dialog("close");}
           },
        });
    });
  var permissionDeniedReload = function() {
	  $("#dialog-reload").dialog("open");
  };
  var permissionDeniedUpload = function() {
	  $("#dialog-upload").dialog("open");
  }
  
	$(function() {

		  // Extend the themes to change any of the default class names
		  $.extend($.tablesorter.themes.jui, {
		    // change default jQuery uitheme icons - find the full list of icons at
		    // http://jqueryui.com/themeroller/ (hover over them for their name)
		    table        : 'ui-widget ui-widget-content ui-corner-all', // table classes
		    caption      : 'ui-widget-content',
		    // header class names
		    header       : 'ui-widget-header ui-corner-all ui-state-default', // header classes
		    sortNone     : '',
		    sortAsc      : '',
		    sortDesc     : '',
		    active       : 'ui-state-active', // applied when column is sorted
		    hover        : 'ui-state-hover',  // hover class
		    // icon class names
		    icons        : '', // icon class added to the <i> in the header
		    iconSortNone : '', // class name added to icon when column is not sorted
		    iconSortAsc  : '', // class name added to icon when column has ascending sort
		    iconSortDesc : '', // class name added to icon when column has descending sort
		    filterRow    : '',
		    footerRow    : '',
		    footerCells  : '',
		    even         : 'ui-widget-content', // even row zebra striping
		    odd          : 'ui-state-default'   // odd row zebra striping
		  });

		  // call the tablesorter plugin and apply the ui theme widget
		  $("table").tablesorter({

		    theme : 'jui', // theme "jui" and "bootstrap" override the uitheme widget option in v2.7+

		    headerTemplate : '{content} {icon}', // needed to add icon for jui theme

		    // widget code now contained in the jquery.tablesorter.widgets.js file
		    widgets : ['uitheme', 'zebra'],

		    widgetOptions : {
		      // zebra striping class names - the uitheme widget adds the class names defined in
		      // $.tablesorter.themes to the zebra widget class names
		      zebra   : ["even", "odd"],

		      // set the uitheme widget to use the jQuery UI theme class names
		      // ** this is now optional, and will be overridden if the theme name exists in $.tablesorter.themes **
		      // uitheme : 'jui'
		    }

		  });

		});
  
  </script>
   </sakai:html>
