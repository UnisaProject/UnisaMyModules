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
<%
response.setDateHeader("Expires",0);
response.setDateHeader("Date",0);
response.setHeader("PRAGMA","NO-CACHE");
response.setHeader("Cache-Control","no-cache");
%>
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
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.blockUI.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script> 
	
	<script type="text/javascript">

	 	$(document).ready(function(){
	 		
	 		$("input:radio").click(function(){
	            if($(this).attr('class')=='siteTypeTerm') {
	                $('#selectTerm').attr('disabled','')
	            }
	            else {
	                $('#selectTerm').attr('disabled','disabled');
	                $('#selectTerm  option')[0].selected = true;
	                
	            }
	        })
	 	});

	
	    function setAction() {
			document.trackingForm.action = 'tracking.do?act=search';
			document.trackingForm.submit();
		}		
	    
	    
		function frmSubmitSearch(){
			var searchRadio = $("input[name='searchRadio']").val();
			var searchString = $("input[name='searchString']").val();
			alert("frmSubmitSearch - searchRadio="+searchRadio+", searchString="+searchString);
			
			var consignmentList = "";
			var consignmentDate = "";
			var content = "";
			var contentCon = "";
			var contentDct = "";
			var contentDctSub = "";
			var contentDctAssSub = "";
			var contentUnqAss = "";
			var contentUnqAssSub = "";
			var contentErr = false;
			var dctLiCol;
			var assLiCol;
			var unqAssLiCol;
			var countDct=1;
			var countDctAss=1;
			var countUnqAss=1;
			var url = "tracking.do?act=searchDetail&searchString="+searchString+"&searchRadio="+searchRadio;
			
			//jsonObject={"Consignment":"92053:10/29/2015 12:25:52 AM","Dockets":{"1":"1:11342600:78280729:558452"}}
			
			//$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Processing Request: "+$("input[name='searchString']").val()+"</b>" });
			$.getJSON(url, function(data) {
				cache : false,
				$.each(data, function(conKey, conVal) {
					alert("conKey="+conKey);
					if (conKey === "Error"){
						$.unblockUI();
						showError("Error", conVal);
						contentErr = true;
						return false;
					}else{
						if (conKey === "Consignment"){
							alert("Consignment="+conVal);
							var splitConString = conVal.split(':');
							alert("Consignment Number="+splitConString[0]+", Consignment Date="+splitConString[1]);
							consignmentList = splitConString[0];
							consignmentDate = splitConString[1];
						}

						if (conKey === "Dockets"){	
							alert("Processing Dockets");
							var countDct = 1;
							contentDct = "";
							contentDctAssSub = "";
							$.each(conVal, function(dctKey, dctVal) {
								alert("Cover Docket ID="+dctKey);
								if (countDct % 2 === 0) {
									dctLiCol = "#DDDDDD";
					        	}else{
					        		dctLiCol = "#EEEEEE";
					        	}
								contentDctAssSub = "";
								var splitString = dctVal.split(':');
								alert("Cover Docket="+splitString[1]+", Dct StudentNumber="+splitString[2]+", Dct AssignmentNumber="+splitString[3]);
						       	if (countDctAss % 2 === 0) {
						       		assLiCol = "#BEEFDE"; 
						       	}else{
						       		assLiCol = "#EEFAF6";
						       	}
						        contentDctAssSub += "<li class='conDctAssList"+splitString[1]+"' style='background:"+assLiCol+"'><span><label for='chkConDctAssignment:"+splitString[1]+":"+splitString[2]+":"+splitString[3]+"'>&nbsp;&nbsp;"+splitString[2]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[3]+"&nbsp;</label></span></li>";
						        countDctAss++;
								
								contentDctSub += "<ul class='conDocketList' id='conDocketList:"+countDct+"' style='background:"+dctLiCol+"'><li><span><label for='chkConDocket"+splitString[1]+"'>&nbsp;"+splitString[1]+"&nbsp;</label></span><ul class='conDctAssignmentList' id='conDctAssignmentList:"+splitString[1]+"'>"+contentDctAssSub+"</ul></li></ul>";
								countDct++;
								
							});
							contentDct += contentDctSub;
						}
						if (conKey === "Assignments"){	
							//alert("Processing Assignments");
							$.each(conVal, function(unqKey, unqVal) {
								var splitString = unqVal.split(':');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
					        	if (countUnqAss % 2 === 0) {
					        		unqAssLiCol = "#BEEFDE"; 
					        	}else{
					        		unqAssLiCol = "#EEFAF6";
					        	}
					        	contentUnqAssSub += "<li style='background:"+unqAssLiCol+"' class='uniqueConAssignmentList' id='conUniqueAssignmentList"+splitString[0]+splitString[1]+"'><span>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</span></li>";
					            countUnqAss++;
							});
							contentUnqAss = "<li><span>&nbsp;Student Assignments:&nbsp;</span><ul>"+contentUnqAssSub+"</ul></li>";
							
						}
					}

				});
			    content = "<ul name='parentConsignment' id='parentConsignment' class='parent'><li><span>&nbsp;Consignment: "+consignmentList+", Date: "+consignmentDate+"&nbsp;</span><ul class='consignmentsList"+consignmentList+"' id='consignmentsList"+consignmentList+"'>"+ contentDct + contentUnqAss+"</ul></li></ul>";

				if (contentErr === false){
					//alert("No Error");
					$("#div_consignments").append(content);
					$.unblockUI();
					$("input[name='searchString']").focus();
				}
			});
			
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
				<table>
					<tr>
						<td style="width:50px"><html:radio property="searchRadio" value="Consignment"/></td>
						<td><fmt:message key="search.info.consignment"/></td>
					</tr><tr>
						<td style="width:50px"><html:radio property="searchRadio" value="Docket"/></td>
						<td><fmt:message key="search.info.docket"/></td>
					</tr><tr>
						<td style="width:50px"><html:radio property="searchRadio" value="Assignment"/></td>
						<td><fmt:message key="search.info.assignment"/></td>
					</tr>
					<!-- <tr>
						<td style="width:50px"><html:radio property="searchRadio" value="Person"/></td>
						<td><fmt:message key="search.info.person"/></td>
					</tr>-->
				</table>
				<hr>
				<table>
					<tr>
						<td><bean:message key="label.search.searchString"/>:&nbsp;&nbsp;</td>
						<td><html:text property="searchString" size="30" maxlength="60" /></td>
					</tr>
				</table>
				<hr>
				<!--  Shipping Lists Entered by User -->
				<table style="width:100%">
					<tr>
						<td style="width:35%"></td>
						<td style="width:65%">
							<div id="div_consignments"></div>
						</td>
					</tr>
	       		</table>
			</div>
		</div>
	</div>
	


	<!-- Actions -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
		  	<div class="table-responsive">
			<table style="width:100%">	
			    <tr>
			 		<td>
						<sakai:actions>
							<html:submit property="act" style="width: 70px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.clear" /></html:submit>&nbsp;
							<html:button property="search" style="width: 100px; height: 80px" onclick="frmSubmitSearch();"><fmt:message key="button.searchDetail" /></html:button>&nbsp;
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