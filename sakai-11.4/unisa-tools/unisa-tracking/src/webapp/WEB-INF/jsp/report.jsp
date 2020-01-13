<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>

<sakai:html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">

	<title>Assignment Tracking</title>

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.min.css" />" rel="stylesheet"  type="text/css" />


    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/theme.css" />" rel="stylesheet"  type="text/css" />

	<!-- CSS code from Bootply.com editor -->
    <style type="text/css">
    	body {
		    padding:30px;
		}
		@media print {
		    .panel-heading {
		        display:none
		    }
		}
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		
		.panel {
	        /*height : 300px;*/
	        margin-bottom:0;
	        position: relative;
	    }
	
	    .panel .panel-footer {
	        position : absolute;
	        bottom : 0;
	        margin-bottom:1px;
	        width : 100%;
	    }
	    .paddRight{
	    	padding-right: 60px; /*used to align list with checkboxes */
	    }
	    .paddRightSmall{
	    	padding-right: 5px; /*used to align list with checkboxes */
	    }
	    .topAlign{
	    	vertical-align:top;
	    	/*text-align:center;*/
	    }
	    
	    #activity {  
	        text-align:center;border:1px solid #ccc;  
    	}  
    	#activity td{  
        	text-align:center;border:1px solid #ccc;  
    	}  
     	#footerExport td{  
	       	cursor:pointer;  
    		text-align:center;border:1px solid #ccc;  
       		border:none;  
    	}  

	    /* This is the equivalent of cellspacing="0" */
		table {
			border-collapse:collapse;
		}
 
		/* This is the equivalent of cellpadding="0" */
		td, th {
  			padding:0;
		}
 
		/* This is the equivalent of border="1" */
		td, th {
  			border:0;
		}
	</style>
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" />"></script> 
<!--  	<script type="text/javascript" src="<c:url value="/resources/js/jquery.table2excel.js" />"></script> -->
	<script type="text/javascript" src="<c:url value="/resources/js/tableExport.js" />"></script>    
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.base64.js" />"></script>   
	<script type="text/javascript" src="<c:url value="/resources/js/html2canvas.js" />"></script>   
	<script type="text/javascript" src="<c:url value="/resources/js/base64.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/sprintf.js" />"></script>   
	<script type="text/javascript" src="<c:url value="/resources/js/jspdf.js" />"></script>  
	
 	<script type="text/javascript">
	 	$(document).ready(function(){
	 		$.removeCookie("inputNum");
	 		$.removeCookie("input1");
	 		$.removeCookie("imageConPlus");
	 		$.removeCookie("imageDctPlus");
	 		$.removeCookie("imageAssPlus");
	 		$.removeCookie("inputNumOut");
	 		$.removeCookie("inputOut1");
	 		$.removeCookie("imageAdrPlusOut");
	 		$.removeCookie("imageConPlusOut");
	 		$.removeCookie("imageDctPlusOut");
	 		$.removeCookie("imageAssPlusOut");
	 		
	 		$("input:radio").click(function(){
	            if($(this).attr('class')=='siteTypeTerm') {
	                $('#selectTerm').attr('disabled','')
	            }
	            else {
	                $('#selectTerm').attr('disabled','disabled');
	                $('#selectTerm  option')[0].selected = true;
	                
	            }
	        });
      
        /**
        $('#table2excel').bind('click', function (e) 
			$("#table2excel").table2excel({ 
	            // exclude CSS class 
    	        exclude: ".noExl", //<tr class="noExl"></tr>
	            name: "Excel Document Name" 
			});  
        }); 
		**/
		
        $('#exportexcel').bind('click', function (e) {             
        	$('#activity').tableExport({ type: 'excel', escape: 'false' });  
        });  
        $('#exportpdf').bind('click', function (e) {             
        	$('#activity').tableExport({ type: 'pdf', escape: 'false' });  
        });  
        $('#exportimage').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'png', escape: 'false' });  
        });  
        $('#exportcsv').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'csv', escape: 'false' });  
        });  
        $('#exportppt').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'powerpoint', escape: 'false' });  
        });  
        $('#exportxml').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'xml', escape: 'false' });  
        });  
        $('#exportword').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'doc', escape: 'false' });  
        });  
        $('#exporttxt').bind('click', function (e) {  
        	$('#activity').tableExport({ type: 'txt', escape: 'false' });  
        });  

        /*Print*/
        $('#printButton').click(function () {
		    print()
		});
     });
	
	    function setAction() {
			document.trackingForm.action = 'tracking.do?act=report';
			document.trackingForm.submit();
		}		
	
		function frmSubmitSameWin(){
			document.trackingForm.target = '';
		}
	</script>
</head>
	
	<html:form action="/tracking" >
   
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
<div class="container">
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
				<table id="activity" >   
				    <thead>               
				        <tr>   
				            <th>Name</th>   
				            <th>Activity on Code Project (%)</th>   
				            <th>Activity on C# Corner (%)</th>   
				            <th>Activity on Asp Forum (%)</th>   
				        </tr>   
				    </thead>   
				    <tbody>   
				        <tr>   
				            <td>Sibeesh</td>   
				            <td>100</td>   
				            <td>98</td>   
				            <td>80</td>   
				        </tr>   
				        <tr>   
				            <td>Ajay</td>   
				            <td>90</td>   
				            <td>0</td>   
				            <td>50</td>   
				        </tr>   
				        <tr>   
				            <td>Ansary</td>   
				            <td>100</td>   
				            <td>20</td>   
				            <td>10</td>   
				        </tr>   
				        <tr>   
				            <td>Aghil</td>   
				            <td>0</td>   
				            <td>30</td>   
				            <td>90</td>   
				        </tr>   
				        <tr>   
				            <td>Arya</td>   
				            <td>0</td>   
				            <td>0</td>   
				            <td>95</td>   
				        </tr>          
				    </tbody>   
				</table>  
				<table>  
				    <tr id="footerExport">  
				        <td id="exportexcel"><img src="<c:url value='/resources/images/xls.png' />" title="Export to Excel" /></td>  
				        <td id="exportpdf"><img src="<c:url value='/resources/images/pdf.png' />" title="Export to PDF" /></td>  
				        <td id="exportimage"><img src="<c:url value='/resources/images/png.png' />" title="Export to PNG" /></td>  
				        <td id="exportcsv"><img src="<c:url value='/resources/images/csv.png' />" title="Export to CSV" /></td>  
				        <td id="exportword"><img src="<c:url value='/resources/images/word.png' />" title="Export to Word" /></td>  
				        <td id="exporttxt"><img src="<c:url value='/resources/images/txt.png' />" title="Export to TXT" /></td>  
				        <td id="exportxml"><img src="<c:url value='/resources/images/xml.png' />" title="Export to XML" /></td>  
				        <td id="exportppt"><img src="<c:url value='/resources/images/ppt.png' />" title="Export to PPT" /></td>  
				    </tr>  
				</table>  
				
			</div>
		</div>
	</div>
	
	<!--panel-->
	<div class="panel panel-primary">
	    <!-- Default panel contents -->
	    <div class="panel-heading">New Order
	        <button class="btn btn-sm pull-right btn-default" id="printButton"><img src="<c:url value='/resources/images/print.png' />" title="Print" /></button>
	    </div>
	    <div class="panel-body">
	        <!--table-->
	        <table class="table table-condensed">
	            <thead>
	                <tr>
	                    <th>#</th>
	                    <th>First Name</th>
	                    <th>Last Name</th>
	                    <th>Username</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td>1</td>
	                    <td>Mark</td>
	                    <td>Otto</td>
	                    <td>@mdo</td>
	                </tr>
	                <tr>
	                    <td>2</td>
	                    <td>Jacob</td>
	                    <td>Thornton</td>
	                    <td>@fat</td>
	                </tr>
	                <tr>
	                    <td>3</td>
	                    <td colspan="2">Larry the Bird</td>
	                    <td>@twitter</td>
	                </tr>
	            </tbody>
	        </table>
	        <!--end of table-->
	    </div>
	</div>
	<!--end of panel-->
	<!-- 
	<ul class="dropdown-menu " role="menu">
		<li><a href="#" onClick ="$('#customers').tableExport({type:'json',escape:'false'});"> <img src='icons/json.png' width='24px'> JSON</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'json',escape:'false',ignoreColumn:'[2,3]'});"> <img src='icons/json.png' width='24px'> JSON (ignoreColumn)</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'json',escape:'true'});"> <img src='icons/json.png' width='24px'> JSON (with Escape)</a></li>
		<li class="divider"></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'xml',escape:'false'});"> <img src='icons/xml.png' width='24px'> XML</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'sql'});"> <img src='icons/sql.png' width='24px'> SQL</a></li>
		<li class="divider"></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'csv',escape:'false'});"> <img src='icons/csv.png' width='24px'> CSV</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'txt',escape:'false'});"> <img src='icons/txt.png' width='24px'> TXT</a></li>
		<li class="divider"></li>				
				
		<li><a href="#" onClick ="$('#customers').tableExport({type:'excel',escape:'false'});"> <img src='icons/xls.png' width='24px'> XLS</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'doc',escape:'false'});"> <img src='icons/word.png' width='24px'> Word</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'powerpoint',escape:'false'});"> <img src='icons/ppt.png' width='24px'> PowerPoint</a></li>
		<li class="divider"></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'png',escape:'false'});"> <img src='icons/png.png' width='24px'> PNG</a></li>
		<li><a href="#" onClick ="$('#customers').tableExport({type:'pdf',pdfFontSize:'7',escape:'false'});"> <img src='icons/pdf.png' width='24px'> PDF</a></li>
						
	</ul>
	 -->
	<!-- Actions -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
		  	<div class="table-responsive">
			<table style="width:100%">	
			    <tr>
			 		<td>
						<sakai:actions>
							<html:submit property="act" style="width: 70px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.clear" /></html:submit>&nbsp;
							<html:submit property="act" style="width: 100px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.searchDetail" /></html:submit>&nbsp;
							<html:submit property="act" style="width: 50px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.back" /></html:submit>
			  			</sakai:actions>
			 		</td>
					<td align="right"><font color="gray"><fmt:message key="user.name"></fmt:message><bean:write name="trackingForm" property="novelUserId"/></font></td>
				</tr>
			</table>
			</div>
		</div>
	</div>
</div>
			
</html:form>

</sakai:html>