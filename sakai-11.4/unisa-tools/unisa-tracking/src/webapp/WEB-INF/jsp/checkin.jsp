<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Consignment"%>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Docket"%>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">

	<title>Assignment Tracking</title>

	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.min.css' />" rel="stylesheet"  type="text/css" />

    <!-- Custom styles for this template 
    <link href="<c:url value="/resources/css/theme.css" />" rel="stylesheet"  type="text/css" />
	-->

	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
    
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
	    .paddRightMax{
	    	padding-right: 80px; /*used to align list with checkboxes */
	    }
	    .paddRightSmall{
	    	padding-right: 5px; /*used to align list with checkboxes */
	    }
	    .topAlign{
	    	vertical-align:top;
	    	background-color: #FBFBFB;
	    	/*text-align:center;*/
	    }
		.parent {
			background-color: #FBFBFB;
			/*padding: 0;
			margin: 0;*/
		}

		li div { 
			text-align:right; 
		}
		li div span { 
			float:left; 
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
		label {
		    font-weight: normal !important;
		}
		.dialog .ui-icon {
		    background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		.ui-state-default .ui-icon {
        	background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		/* Override jQuery UI theme's padding on buttons: */
		.ui-button-text-only .ui-button-text {
		        padding: 0.2em 0.5em;
		}
		
		.container {
		   min-height: 600px; 
		   height:auto !important; 
		   height: 600px; 
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
	
	 	<!-- Browser Version -->
 	<script type="text/javascript" src="<c:url value='/resources/js/ua-parser.js' />"></script>
	
	<script type="text/javascript">
		
		var countMainAssign = 0;
		var countConListAssign = 0;
		
		$(document).ready(function(){
			
			//Allow move of Focus for Barcode scanners where no enter or tab is pressed
			$(function() {
				$("input[name='enteredConsignmentNumber']").bind('paste', function(event) {
					$("input[name='addConsignment']").focus();
	      		});
				$("input[name='docketNumber']").bind('paste', function(event) {
					$("input[name='addDocket']").focus();
	      		});
				$("input[name='studNo']").bind('paste', function(event) {
					$("input[name='uniqueAssignmentNr']").focus();
	      		});
				$("input[name='uniqueAssignmentNr']").bind('paste', function(event) {
					$("input[name='addAssignment']").focus();
	      		});
			});			
			
			//Expand and Collapse major groups
			$('#titleTextImgCon').click(function(){
			    $(this).closest('table').find('#contentDivImgCon').toggle();
			    if ($('#imageConPlus').css('display') == 'none') {
			    	$("#imageConPlus").show();
			    	$("#imageConMinus").hide();
			    	$.cookie('imageConPlus', "show");
			   	}else{
			   		$("#imageConPlus").hide();
			   		$("#imageConMinus").show();
			   		$.cookie('imageConPlus', "hide");
			   	}
			});
			$('#titleTextImgDct').click(function(){
			    $(this).closest('table').find('#contentDivImgDct').toggle();
			    if ($('#imageDctPlus').css('display') == 'none') {
			    	$("#imageDctPlus").show();
			    	$("#imageDctMinus").hide();
			    	$.cookie('imageDctPlus', "show");
			   	}else{
			   		$("#imageDctPlus").hide();
			   		$("#imageDctMinus").show();
			   		$.cookie('imageDctPlus', "hide");
			   	}
			});
			$('#titleTextImgAss').click(function(){
			    $(this).closest('table').find('#contentDivImgAss').toggle();
			    if ($('#imageAssPlus').css('display') == 'none') {
			    	$("#imageAssPlus").show();
			    	$("#imageAssMinus").hide();
			    	$.cookie('imageAssPlus', "show");
			   	}else{
			   		$("#imageAssPlus").hide();
			   		$("#imageAssMinus").show();
			   		$.cookie('imageAssPlus', "hide");
			   	}
			});
			
		});
		
		//Expand and Collapse minor groups
		function expandConsignments(conList){
	    	$(".consignmentsList"+conList).toggle();
		    if ($("#imageConsignmentsPlus"+conList).css('display') == 'none') {
		    	$("#imageConsignmentsPlus"+conList).show();
		    	$("#imageConsignmentsMinus"+conList).hide();
		   	}else{
		   		$("#imageConsignmentsPlus"+conList).hide();
		   		$("#imageConsignmentsMinus"+conList).show();
		   	}
		}
		function expandConDockets(dct){
	    	//$('.dctAssList'+key).toggle();
		    if ($("#imageConUniqueDctPlus"+dct).css('display') == 'none') {
		    	$("#imageConUniqueDctPlus"+dct).show();
		    	$("#imageConUniqueDctMinus"+dct).hide();
		    	$(".conDctAssList"+dct).hide();
		   	}else{
		   		$("#imageConUniqueDctPlus"+dct).hide();
		   		$("#imageConUniqueDctMinus"+dct).show();
		    	$(".conDctAssList"+dct).show();
		   	}
		}
		function expandDockets(dct){
	    	//$('.dctAssList'+key).toggle();
		    if ($("#imageUniqueDctPlus"+dct).css('display') == 'none') {
		    	$("#imageUniqueDctPlus"+dct).show();
		    	$("#imageUniqueDctMinus"+dct).hide();
		    	$(".dctAssList"+dct).hide();
		   	}else{
		   		$("#imageUniqueDctPlus"+dct).hide();
		   		$("#imageUniqueDctMinus"+dct).show();
		    	$(".dctAssList"+dct).show();
		   	}
		}
		function expandAssignments(count){
	    	$('.uniqueAssignmentList').toggle();
		    if ($('#imageUniqueAssignmentPlus').css('display') == 'none') {
		    	$("#imageUniqueAssignmentPlus").show();
		    	$("#imageUniqueAssignmentMinus").hide();
		   	}else{
		   		$("#imageUniqueAssignmentPlus").hide();
		   		$("#imageUniqueAssignmentMinus").show();
		   	}
		}
		function expandConUniqueAssignments(){
	    	$('.uniqueConAssignmentList').toggle();
		    if ($('#imageConUniqueAssignmentPlus').css('display') == 'none') {
		    	$("#imageConUniqueAssignmentPlus").show();
		    	$("#imageConUniqueAssignmentMinus").hide();
		   	}else{
		   		$("#imageConUniqueAssignmentPlus").hide();
		   		$("#imageConUniqueAssignmentMinus").show();
		   	}
		}
		
		function showError(errorTitle, errorText) {

		    // show the actual error modal
		    $('#dialogContent').html(errorText);
			$('#dialogHolder').dialog({
			  autoOpen: true,
			  title: errorTitle,
			  modal: true,
			  buttons: {
			    "Ok": function() {
			      $(this).dialog("close");
			    }
			  }
			})
		}
		
		function validateDocketNumber(){
			//alert("validateDocketNumber");
			var contentDct="";
			var contentAss="";
			var contentDctSub="";
			var contentAssSub="";
			var DctLiCol;
			var AssLiCol;
			var countDct=1;
			var liCount=1;
			var url = "tracking.do?act=validateDocketNumber&docketNumber="+$("input[name='docketNumber']").val();
			
			$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Validating Cover Docket: "+$("input[name='docketNumber']").val()+"</b>" });
			$.getJSON(url, function(data) {
				cache : false,
					$.each(data, function(key, data2) {
						//alert("DocketID="+key);
						if (key === "Error"){
							$.unblockUI();
							showError("Error", data2);
							$("input[name='docketNumber']").focus();
							return false;
						}else if (key === "Empty"){
							$.unblockUI();
							showError("Empty", data2);
							$("input[name='docketNumber']").focus();
							return false;
						}else if (key === "RemoveAssignments"){	
							//alert("Processing Removal of Unique Assignments as they are already in Cover Docket list");
							$.each(data2, function(unqKey, unqVal) {
								var splitString = unqVal.split('~');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
								$.blockUI({ message: "Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" as it already exists in Consignment list" });
								$("#uniqueAssignmentList"+splitString[0]+splitString[1]).remove();
								countMainAssign = countMainAssign -1;
								//alert("countMainAssign="+countMainAssign);
								if (countMainAssign === 0){
									//alert("Removing uniqueAssignmentDiv");
									$("#uniqueAssignmentDiv").remove();
								}
							});
						}else if (key === "RemoveConAssignments"){	
							//alert("Processing Removal of Consignment Unique Assignments as they are already in Cover Docket list");
							$.each(data2, function(unqKey, unqVal) {
								var splitString2 = unqVal.split('~');
								//alert("StudentNumber="+splitString2[0]+", AssignmentNumber="+splitString2[1]);
								$.blockUI({ message: "Removing StudentNumber="+splitString2[0]+", AssignmentNumber="+splitString2[1]+" from Consignment list as it already exists in Cover Docket list" });
								$("#conUniqueAssignmentList"+splitString2[0]+splitString2[1]).remove();
								countConListAssign = countConListAssign -1;
								//alert("countConListAssign="+countConListAssign);
								if (countConListAssign === 0){
									//alert("Removing uniqueConAssignmentDiv");
									$("#conUniqueAssignmentList").remove();
								}
							});
						}else{
							//alert("key="+key);
							if (countDct % 2 === 0) {
								DctLiCol = "#DDDDDD";
				        	}else{
				        		DctLiCol = "#EEEEEE";
				        	}
							
							$.each(data2, function(key2, val2) {
								var splitString = val2.split('~');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
						       	if (liCount % 2 === 0) {
						       		AssLiCol = "#BEEFDE"; 
						       	}else{
						       		AssLiCol = "#EEFAF6";
						       	}
						        contentAssSub += "<li class='dctAssList"+key+"' id='dctAssList"+key+"' style='background:"+AssLiCol+"'><input type='checkbox' class='childCheckBox' name='chkDctAssignment' value='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"' id='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span><label for='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"'>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</label></span></li>";
						        liCount++;
							});
							contentDctSub += "<div id='docketList"+key+"'><ul class='docketList' style='background:"+DctLiCol+"'><li id='liChkDocket~"+key+"'><input type='checkbox' class='parentCheckBox' name='chkDocket' value='chkDocket~"+key+"' id='chkDocket~"+key+"' checked='checked'/><span><label for='chkDocket~"+key+"'>&nbsp;"+key+"&nbsp;</label></span><img id='imageUniqueDctPlus"+key+"'' style='display:none' alt='Expand' src='<c:url value='/resources/images/bullet-toggle-plus-icon32.png' />' onclick='expandDockets("+key+");'><img id='imageUniqueDctMinus"+key+"'' alt='Collapse' src='<c:url value='/resources/images/bullet-toggle-minus-icon32.png' />' onclick='expandDockets("+key+");'><ul class='dctAssignmentList' id='dctAssignmentList~"+key+"'>"+contentAssSub+"</ul></li></ul></div>";
							countDct++;
						}
						
					});
					contentDct = contentDctSub;
					
					$("#div_dockets").append(contentDct);
					$("input[name='docketNumber']").val("");
					$.unblockUI(); 
					$("input[name='docketNumber']").focus();
			});
			
		}

		function validateStudentUniqueNumber(){
			var content = "";
			var contentAss = "";
			var contentErr = false;
	    	var liCol;
	    	var liCount=1;
			var url = "tracking.do?act=validateStudentUniqueNumber&studNo="+$("input[name='studNo']").val()+"&uniqueAssignmentNr="+$("input[name='uniqueAssignmentNr']").val();
			$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Validating Unique Assignment: "+$("input[name='studNo']").val()+" / "+$("input[name='uniqueAssignmentNr']").val()+"</b>" });

			$.getJSON(url, function(data) {
				cache : false,
				$.each(data, function(key, val) {
					//alert("StudentNumber="+key+", AssignmentNumber="+val);
					if (key === "Error"){
						$.unblockUI();
						showError("Error", val);
						contentErr = true;
						$("input[name='studNo']").val("");
						$("input[name='uniqueAssignmentNr']").val("");
						$("input[name='studNo']").focus();
						return false;
					}
					var splitString = val.split('~');
					//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
					countMainAssign = liCount;
					countConListAssign = liCount;
					//alert(countMainAssign);
		        	if (liCount % 2 === 0) { /* we are even */ 
		        		liCol = "#BEEFDE"; 
		        	}else{
		        		liCol = "#EEFAF6";
		        	}
		            content += "<li style='background:"+liCol+"' id='uniqueAssignmentList"+splitString[0]+splitString[1]+"'><input type='checkbox' class='childCheckBox' name='chkUniqueAssignment' value='"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span>&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</span></li>";
		            liCount++;
				});
				if (contentErr === false){
					contentAss = "<div id='uniqueAssignmentDiv'><ul name='parentAssignment' id='parentAssignment' class='parent'><li><input type='checkbox' class='parentCheckBox' name='chkAssignmentList' value='chkAssignmentList' checked='checked' /><span>&nbsp;Student Assignments:&nbsp;</span><img id='imageUniqueAssignmentPlus' style='display:none' alt='Expand' src='<c:url value='/resources/images/bullet-toggle-plus-icon32.png' />' onclick='expandAssignments();'><img id='imageUniqueAssignmentMinus' alt='Collapse' src='<c:url value='/resources/images/bullet-toggle-minus-icon32.png' />' onclick='expandAssignments();'><ul class='uniqueAssignmentList' id='uniqueAssignmentList"+liCount+"'>"+content+"</ul></li></ul>";
					$("#div_assignments").empty();
					$("#div_assignments").append(contentAss);
					$("input[name='studNo']").val("");
					$("input[name='uniqueAssignmentNr']").val("");
					$.unblockUI();
					$("input[name='studNo']").focus();
				}
			});
		}

		function validateConsignmentList(){
			//alert("validateConsignmentList="+$("input[name='enteredConsignmentNumber']").val());
			$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Validating Consignment List: "+$("input[name='enteredConsignmentNumber']").val()+"</b>" });

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

			var url = "tracking.do?act=validateConsignmentList&enteredConsignmentNumber="+$("input[name='enteredConsignmentNumber']").val();
			
			$.getJSON(url, function(data) {
				cache : false,
				$.each(data, function(conKey, conVal) {
					if (conKey === "Error"){
						$.unblockUI();
						showError("Error", conVal);
						contentErr = true;
						$("input[name='enteredConsignmentNumber']").val("");
						$("input[name='enteredConsignmentNumber']").focus();
						return false;
					}else{
						if (conKey === $.trim($("input[name='enteredConsignmentNumber']").val())){
							//alert("Consignment="+conKey);
							//contentCon += "<br>Consignment="+conKey+", Date="+conVal+"<br>";
							consignmentList = conKey;
							consignmentDate = conVal;
						}
						
						if (conKey === "Dockets"){	
							//alert("Processing Dockets");
							var countDct = 1;
							contentDct = "";
							contentDctAssSub = "";
							$.each(conVal, function(dctKey, dctVal) {
								if (countDct % 2 === 0) {
									dctLiCol = "#DDDDDD";
					        	}else{
					        		dctLiCol = "#EEEEEE";
					        	}
								contentDctAssSub = "";
								$.each(dctVal, function(assKey, assVal) {
									var splitString = assVal.split('~');
									//alert("Dct StudentNumber="+splitString[0]+", Dct AssignmentNumber="+splitString[1]);
							       	if (countDctAss % 2 === 0) {
							       		assLiCol = "#BEEFDE"; 
							       	}else{
							       		assLiCol = "#EEFAF6";
							       	}
							        contentDctAssSub += "<li class='conDctAssList"+dctKey+"' style='background:"+assLiCol+"'><input type='checkbox' class='childCheckBox' name='chkConDctAssignment' value='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"' id='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span><label for='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"'>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</label></span></li>";
							        countDctAss++;
								});
								
								contentDctSub += "<ul class='conDocketList' id='conDocketList~"+countDct+"' style='background:"+dctLiCol+"'><li><input type='checkbox' class='childCheckBox' name='chkConDocket' value='chkConDocket~"+dctKey+"' id='chkConDocket"+dctKey+"' checked='checked'/><span><label for='chkConDocket"+dctKey+"'>&nbsp;"+dctKey+"&nbsp;</label></span><img id='imageConUniqueDctPlus"+dctKey+"'' style='display:none' alt='Expand' src='<c:url value='/resources/images/bullet-toggle-plus-icon32.png' />' onclick='expandConDockets("+dctKey+");'><img id='imageConUniqueDctMinus"+dctKey+"'' alt='Collapse' src='<c:url value='/resources/images/bullet-toggle-minus-icon32.png' />' onclick='expandConDockets("+dctKey+");'><ul class='conDctAssignmentList' id='conDctAssignmentList~"+dctKey+"'>"+contentDctAssSub+"</ul></li></ul>";
								countDct++;
								
							});
							contentDct += contentDctSub;
						}
						if (conKey === "Assignments"){	
							//alert("Processing Assignments");
							$.each(conVal, function(unqKey, unqVal) {
								var splitString = unqVal.split('~');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
					        	if (countUnqAss % 2 === 0) {
					        		unqAssLiCol = "#BEEFDE"; 
					        	}else{
					        		unqAssLiCol = "#EEFAF6";
					        	}
					        	contentUnqAssSub += "<li style='background:"+unqAssLiCol+"' class='uniqueConAssignmentList' id='conUniqueAssignmentList"+splitString[0]+splitString[1]+"'><input type='checkbox' class='childCheckBox' name='chkConUniqueAssignment' value='chkConUniqueAssignment~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</span></li>";
					            countUnqAss++;
							});
							contentUnqAss = "<li><input type='checkbox' class='childCheckBox' name='chkConAssignmentList' value='chkConAssignmentList' checked='checked' /><span>&nbsp;Student Assignments:&nbsp;</span><img id='imageConUniqueAssignmentPlus' style='display:none' alt='Expand' src='<c:url value='/resources/images/bullet-toggle-plus-icon32.png' />' onclick='expandConUniqueAssignments();'><img id='imageConUniqueAssignmentMinus' alt='Collapse' src='<c:url value='/resources/images/bullet-toggle-minus-icon32.png' />' onclick='expandConUniqueAssignments();'><ul>"+contentUnqAssSub+"</ul></li>";
							
						}
						if (conKey === "RemoveDockets"){	
							//alert("Processing Removal of Unique Dockets as they are already in Consignment list");
							$.each(conVal, function(unqKey, unqVal) {
								$.blockUI({ message: "Removing Unique Cover Docket="+unqVal+" As it already exists in Consignment list" });
								$("#docketList"+unqVal).remove();
							});
						}
						if (conKey === "RemoveAssignments"){	
							//alert("Processing Removal of Unique Assignments as they are already in Consignment list");
							$.each(conVal, function(unqKey, unqVal) {
								var splitString = unqVal.split('~');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
								$.blockUI({ message: "Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" As it already exists in Consignment list" });
								$("#uniqueAssignmentList"+splitString[0]+splitString[1]).remove();
								countMainAssign = countMainAssign -1;
								//alert("countMainAssign="+countMainAssign);
								if (countMainAssign === 0){
									//alert("Removing uniqueAssignmentDiv");
									$("#uniqueAssignmentDiv").remove();
								}
							});
						}
					}

				});
			    content = "<ul name='parentConsignment' id='parentConsignment' class='parent'><li><input type='checkbox' class='parentCheckBox' id='chkConsignmentList"+consignmentList+"' value='chkConsignmentList"+consignmentList+"' checked='checked' /><span>&nbsp;Consignment: "+consignmentList+", Date: "+consignmentDate+"&nbsp;</span><img id='imageConsignmentsPlus"+consignmentList+"' style='display:none' alt='Expand' src='<c:url value='/resources/images/bullet-toggle-plus-icon32.png' />' onclick='expandConsignments("+consignmentList+");'><img id='imageConsignmentsMinus"+consignmentList+"' alt='Collapse' src='<c:url value='/resources/images/bullet-toggle-minus-icon32.png' />' onclick='expandConsignments("+consignmentList+");'><ul class='consignmentsList"+consignmentList+"' id='consignmentsList"+consignmentList+"'>"+ contentDct + contentUnqAss+"</ul></li></ul>";

				if (contentErr === false){
					//alert("No Error");
					$("#div_consignments").append(content);
					$("input[name='enteredConsignmentNumber']").val("");
					$("input[name='enteredConsignmentNumber']").prop('disabled', true);
					$("input[name='addConsignment']").prop('disabled', true);
					$.unblockUI();
					$("input[name='enteredConsignmentNumber']").focus();
				}
			});
			
		}
		

		function frmSubmitBookIN(){
			//showValues(); /*Debug*/
		    $.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Processing Book IN information</b>" });
			var url = "tracking.do?act=processInput&process=BookIN";
		    var data = $("Form").serialize();
            $.getJSON(url, data, function(result){
            	cache : false,
            	$.each(result, function(key, val) {
					//alert("Key="+key+", Value="+val);
					if (key === "Error"){
						$.unblockUI();
						showError("Error", val);
						return false;
					}else if (key === "Success"){
						$.unblockUI();
						//showError("Success", val);
						var splitFinalString = val.split('~');
						var url = "tracking.do?act=report";
						//If IE, use Download instead of Open. Open doesn't work in IE.
						//Detect for IE and Edge
						var parser = new UAParser();
						var bName = parser.getResult().browser.name;
						var bVersion = parser.getResult().browser.version;
						if (bName === "IE"){
							var myWindow = window.open(url, '_blank');
					    	myWindow.focus();
						}else{
						window.location.href =url;
					}
					}
            	});
            });		    
		}
		
		function frmSubmitSameWin(){
			var buttonCheck = $("input[name='act']").val();
			if (buttonCheck.toUpperCase() === "CLEAR"){
		        $('#contentDivImgAdr').show();
		        $("#imageAdrPlus").hide();
		        $("#imageAdrMinus").show();
		        $('#contentDivImgCon').show();
		        $("#imageConPlus").hide();
		        $("#imageConMinus").show();
		        $('#contentDivImgDct').show();
		        $("#imageDctPlus").hide();
		        $("#imageDctMinus").show();
		        $('#contentDivImgAss').show();
		        $("#imageAssPlus").hide();
		        $("#imageAssMinus").show();
			}
			document.trackingForm.target = '';
		}

		function showValues() {
		    var str = $("form").serialize();
		    $( "#results" ).text( str );
		}
		
	</script>
</head>
<body>

<html:form action="/tracking">
	
<!-- showValues Debug -->
<div id="results"></div>
	
<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
</p>
	
<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
<div class="container">

	<!--  Consignment Lists -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">
			<tbody id="titleTextImgCon">
				<tr>
					<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Consignment.title"/></td>
					<td bgcolor="lightgray" align="right"><img id="imageConPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageConMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
				</tr>
			</tbody>
			<tbody id="contentDivImgCon">
				<tr>
					<td colspan="2" style="height:5px;"></td>
				</tr><tr>
					<td><fmt:message key="consignment.list"/>&nbsp;&nbsp;</td>
					<td align="right"> 
						<html:text property="enteredConsignmentNumber" size="10" maxlength="10" ></html:text>
						<html:button property="addConsignment" style="width: 50px; height: 80px" onclick="validateConsignmentList();"><fmt:message key="button.addCon" /></html:button>
					</td>		
				</tr>
				
				<!--  Shipping Lists Entered by User -->
				<tr>
					<td colspan="2" style="height:5px;"></td>
				</tr><tr>
					<td colspan="2">
						<table style="width:100%">
							<tr>
								<td style="width:35%"></td>
								<td style="width:65%">
										<div id="div_consignments"></div>
								</td>
							</tr>
			       		</table>
			       	</td>
			    </tr>
			</tbody>
			</table>
			</div>
		</div>
	</div>
	

	<!-- Dockets -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">	
				<tbody id="titleTextImgDct">
					<tr>
						<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Dockets.title"/></td>
						<td bgcolor="lightgray" align="right"><img id="imageDctPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageDctMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
					</tr>
				</tbody>
				<tbody id="contentDivImgDct">
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td><fmt:message key="docket.number"/></td>
						<td align="right"><html:text property="docketNumber" size="10" maxlength="10" ></html:text> 
							<html:button property="addDocket" style="width: 50px; height: 80px" onclick="validateDocketNumber();"><fmt:message key="button.addDct"  /></html:button> 
						</td>
					</tr>
	
					<!-- List Unique Dockets Added -->		
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td colspan="2">
							<table style="width:100%">
								<tr>
									<td style="width:50%"></td>
									<td style="width:50%">
										<div id="div_dockets"></div>
									</td>
								</tr>
			           		</table>
			           	</td>
			        </tr>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	
	<!-- Unique Assignments -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">
				<tbody id="titleTextImgAss">
					<tr>
						<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Single.title"/></td>
						<td bgcolor="lightgray" align="right"><img id="imageAssPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageAssMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
					</tr>
				</tbody>
				<tbody id="contentDivImgAss">
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td valign="top">
							<table style="width:100%">
								<tr><td>&nbsp;</td></tr>
								<tr><td><fmt:message key="add.singleassignment"/>&nbsp;</td></tr>
							</table>
						</td>
						<td valign="top">
							<table style="width:100%">
								<tr>
									<td style="width:50%" align="center"><fmt:message key="pers.studno"/></td>
									<td style="width:50%" align="center"><fmt:message key="pers.uniqueassignmentnr"/></td>
								</tr><tr>	
									<td align="center"><html:text property="studNo" size="20" maxlength="10" ></html:text></td>
									<td align="right"><html:text property="uniqueAssignmentNr" size="20" maxlength="10" ></html:text>
										<html:button property="addAssignment" style="width: 50px; height: 80px" onclick="validateStudentUniqueNumber();"><fmt:message key="button.addAss" /></html:button>
									</td>		
								</tr>			        
							</table>
						</td>
					</tr>
					<!-- List Unique Assignments Added -->	
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td colspan="2">
							<table style="width:100%">
								<tr>
									<td style="width:34%"></td>
									<td style="width:66%">
										<div id="div_assignments"></div>
									</td>
								</tr>
			           		</table>
						</td>
					</tr>
				</tbody>
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
							<html:button property="bookIN" style="width: 100px; height: 80px" onclick="frmSubmitBookIN();"><fmt:message key="button.in" /></html:button>&nbsp;
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
</body>
<script>

		//Parent&Sibling Check/unCheck for Lists
	    $(function() {
	        // Click is better than change because of IE.
	        $('body').on('change', 'input[type="checkbox"]', function() {
	        	//alert("Checked");
	            var checked = $(this).prop("checked"),
	                container = $(this).parent(),
	                siblings = container.siblings();

	            container.find('input[type="checkbox"]').prop({
	                indeterminate: false,
	                checked: checked
	            });

	            function checkSiblings(el) {
	                var parent = el.parent().parent(),
	                    all = true;

	                el.siblings().each(function() {
	                    return all = ($(this).children('input[type="checkbox"]').prop("checked") === checked);
	                });

	                if (all && checked) {
	                    parent.children('input[type="checkbox"]').prop({
	                        indeterminate: false,
	                        checked: checked
	                    });
	                    checkSiblings(parent);
	                } else if (all && !checked) {
	                    parent.children('input[type="checkbox"]').prop("checked", checked);
	                    parent.children('input[type="checkbox"]').prop("indeterminate", (parent.find('input[type="checkbox"]:checked').length > 0));
	                    checkSiblings(parent);
	                } else {
	                    el.parents("li").children('input[type="checkbox"]').prop({
	                        indeterminate: true,
	                        checked: false
	                    });
	                }
	            }

	            checkSiblings(container);
	        });
	    });
		
</script>
</sakai:html>