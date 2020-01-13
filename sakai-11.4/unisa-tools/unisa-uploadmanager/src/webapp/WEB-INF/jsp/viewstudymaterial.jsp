<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<%response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", -1);
response.setHeader("Pragma", "no-cache");
%>


<fmt:setBundle basename="za.ac.unisa.lms.tools.uploadmanager.ApplicationResources"/>

<sakai:html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.10.2.min.js"></script>     
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery-ui-1.10.2.css">


<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.dataTables.min.js"></script>     

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery.dataTables.css">

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

<!-- 	<link href="dist/css/theme.default.min.css" rel="stylesheet">
	<script src="dist/js/jquery.tablesorter.min.js"></script>
	<script src="dist/js/jquery.tablesorter.widgets.min.js"></script> -->



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
<%-- 	   <div id="hcm_busy_div" style="padding:10px; text-align: center; display: none; height: 140px">	
 <div style="font: bold;color: blue;">Loading,please wait </div>
	<div><img src='${pageContext.request.contextPath}/images/ajax-loader.gif' alt="busy"/></div>
</div>  --%>

<%@ include file="busybar.jsp"%>
	
		  <script type="text/javascript">
		  
	(function() {
		  
	  var _$busyDiv=_$dialogContainer= undefined;
 		var _initBusyDiv = function() {

			
			_$busyDiv = $("#hcm_busy_div");
			_$busyDiv.dialog({
				height : 50,
				modal : true,
				autoOpen : false
			}).dialog("widget").find(".ui-dialog-titlebar").hide();
	}; 
		
		_initBusyDiv();

	   _$busyDiv.dialog("open");
	})();
	
/* 	$(document).ready(function() {
	    $('#example').dataTable();
	} ); */
	


	  </script>
	  




  	  <sakai:heading><fmt:message key="uploadmanager.heading"/>&nbsp;&nbsp;<b> <bean:write name="uploadManagerForm" property="courseCode"/> </b></sakai:heading><br>
  	  	<sakai:messages/>
    <sakai:messages message="true"/>
<!--     <div id="busycontainer"></div> -->
<c:choose>
      <c:when test="${not empty uploadManagerForm.materialList}">
      <table  cellspacing="0" width="100%">
 	 	 <thead>
 	 
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
				<%-- <c:out value="${not empty d.itemfulldesc ? d.itemfulldesc : ''}" /> --%>

		
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
				 <!--  c:if test="${d.documenttype != 'MO'}"-->
				<bean:write name="d" property="documenttype"/>
				<!--  /c:if-->
			</td>	
			<td style="text-align:center">
			
			<logic:equal name="d" property="filestatus" value="true">
			
					  <c:choose>
    <c:when test="${(d.docAvailability == 'true' || (uploadManagerForm.from == 'staff' && uploadManagerForm.userprivilege == 'Administration Staff') || uploadManagerForm.from == 'myUnisa')}">
			
			<html:link href="javascript:void(0);" onclick="loadIframe('uploadmanager.do?act=view&amp;itembarcode=${d.itembarcode}&amp;itemshortdesc=${d.itemshortdesc}&module=${d.module}')">
			<html:img page="/images/view_Pdf.png" border="0" align="center" width="20" height="20" title="View" style="text-align:center"/>
			</html:link>
			
			&nbsp;&nbsp;
			<html:link href="uploadmanager.do?act=email&amp;itembarcode=${d.itembarcode}&amp;itemshortdesc=${d.itemshortdesc}&module=${d.module}">
			
			<html:img page="/images/send_Pdf.png" border="0" align="center" width="20" height="20" title="Email" style="text-align:center"/>
			
			</html:link>&nbsp;&nbsp;
			<html:link href="uploadmanager.do?act=download&amp;itembarcode=${d.itembarcode}&amp;itemshortdesc=${d.itemshortdesc}&module=${d.module}">
			
			
<html:img page="/images/download_Pdf.png" border="0" align="center" width="20" height="20" title="Download" style="text-align:center"/>
			
			</html:link>
			
    </c:when>
    <c:otherwise>
       <html:img page="/images/blocked_to_access.png" border="0" align="center" width="20" height="20" title="File only visible on/after the From Date" style="text-align:center"/>
    </c:otherwise>
</c:choose>
			</logic:equal>
			
			<logic:notEqual name="d" property="filestatus" value="true">
			<html:img page="/images/NotAvailable_Pdf.png" border="0" align="center" width="20" height="20" title="File not available" style="text-align:center"/>
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
<c:if test="${requestScope['org.apache.struts.action.ERROR'] == null}">
	<div>There is no study material found</div>
	</c:if>
	
      </c:otherwise>
</c:choose>

	 <sakai:actions>
	      <html:submit property="act">
		        <fmt:message key="button.back"/>
		  </html:submit>
	</sakai:actions>
	
		  <!--  <iframe  id="printFrame" /> -->

   </html:form>
   <div id="detailID" title="Study Material Preview" style="overflow:scroll;display: none;height: 900px;"><iframe  style="width:100%;height:900px;" src="" id="iframeId"></iframe></div>
     

       
      <script>
   
      var loadIframe = function (url) {
    	   var $iframe = $('#iframeId');
    	    if ( $iframe.length ) {
    	        $iframe.attr('src',url);
    	      
    	        $('#detailID')
    	        .dialog({ 
    	          autoOpen: true, 
    	          width: 700, 
    	          height: 700, 
    	          position: 'center', 
    	          resizable: true, 
    	          draggable: true
    	         });
    	        
    	        return false;
    	    } 
    	    return true;
    	}

      $("#hcm_busy_div").dialog("close");
      
/*   	$(function(){
		$('table').tablesorter({
			widgets        : ['zebra', 'columns'],
			usNumberFormat : false,
			sortReset      : true,
			sortRestart    : true
		});
	}); */
	
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
