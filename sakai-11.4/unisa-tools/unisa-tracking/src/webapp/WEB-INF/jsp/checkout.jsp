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
	    	/*background-color: #FBFBFB;*/
	    	/*text-align:center;*/
	    }
		.parent {
			background-color: #FBFBFB;
			/*padding: 0;
			margin: 0;*/
		}

		hr {
			padding: 0px;
   			margin: 0.5em auto;  
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
		.container{
		   min-height: 600px; 
		   height:auto !important; 
		   height: 600px; 
		}

		/*
		input[type="text"] {
     width: 100%; 
     box-sizing: border-box;
     -webkit-box-sizing:border-box;
     -moz-box-sizing: border-box;
}
		*/
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
			
			//Reset all Radio Buttons (Twice...)
			$('input:radio[name="addressType"]').removeAttr('checked');
			$("input:radio[name='addressType']").each(function(i) {
			       this.checked = false;
			});
			
			//Set Tool Tips for some input elements
			
			$("input[name='savedAddress']").prop('title', 'Retrieve previously used Address');
			$("input[name='searchUser']").prop('title', 'Search for Personnel');
			$("input[name='searchCSDMUser']").prop('title', 'Search for Personnel with the criteria selected');
			$("input[name='searchBuildingUser']").prop('title', 'Search for Personnel in the Building selected');
			
			$("input[name='searchUserAddress']").prop('title', 'Retrieve Personnel member\'s Address');
			$("input[name='searchCSDMUserAddress']").prop('title', 'Retrieve Personnel member\'s Address');
			$("input[name='searchBuildingUserAddress']").prop('title', 'Retrieve Personnel member\'s Address');

			//Hide unused button for field alignment
			$("input[name='hiddenUserPostal']").css("visibility", "hidden");

			
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
			$('#titleTextImgAdr').click(function(){
			    $(this).closest('table').find('#contentDivImgAdr').toggle();
			    if ($('#imageAdrPlus').css('display') == 'none') {
			    	$("#imageAdrPlus").show();
			    	$("#imageAdrMinus").hide();
			   	}else{
			   		$("#imageAdrPlus").hide();
			   		$("#imageAdrMinus").show();
			   	}
			});
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
					    

			/** Check previous selected value **/
			var radioSelected = $("[name='addressSaved']").val(); 
			if (radioSelected!=""){
				//alert("RadioSelected: "+radioSelected);
				showHide(radioSelected);
			}
			
			$('input[name="addressType"]:radio').change(function(){
				var addressType = $(this).attr("value");
				//alert("RadioChanged: "+addressType);
				showHide(addressType);

				if (addressType==="other"){ //Populate Colleges
					populateCollege("","","","");
				} //populateColleges
				
				if (addressType==="province"){ //Populate Provinces
					populateProvince("","");
				} //populateProvinces
				
				if (addressType==="building"){ //Populate Buildings
					populateBuilding("");
				} //populateBuildings
				
			}); //addressType
			
			//Change User dependant on Building
			$("select[name='building']").change(function(){
				populateBuildingUser();
			});//Change User
			
			//Change School dependant on College
			$("select[name='college']").change(function(){
				var college = $(this).val();
				populateSchool(college,"","","");
			});//Change College

			//Change Department dependant on School
			$("select[name='school']").change(function(){
				var school = $(this).val();
				populateDepartment("",school,"","");
			});//Change Department
			
			//Change Module dependant on Department
			$("select[name='department']").change(function(){
				var department = $(this).val();
				populateModule("","",department,"");
			});//Change Module
			
			//Change User dependant on Module
			$("select[name='module']").change(function(){
				var module = $(this).val();
				populateCSDMUser();
			});//Change User

			//Change Regional Office dependant on Province
			$("select[name='province']").change(function(){
				var province = $("select[name='province']").val();
				//alert("Province Changed="+province);
				populateRegion(province,"");
			});//Change Regional Office

			//Find User by College/School/Department/Module
			$("input[name='searchCSDMUser']").click(function(){
				var college 	= $("select[name='college']").val();
				var school 		= $("select[name='school']").val();
				var department 	= $("select[name='department']").val();
				var module 		= $("select[name='module']").val();
				
				var searchString= "";
				var CSDMUserErr = false;
				var checkString = false;
				
				if (college === "0"){
					college=""
				}else{
					checkString = true;
				}
				if (school === "0"){
					school = "";
				}else{
					checkString = true;
				}
				if (department === "0"){
					department = "";
				}else{
					checkString = true;
				}
				if (module === "0"){
					module = "";
				}else{
					checkString = true;
				}
				searchString = "&searchCheck=College&college="+college+"&school="+school+"&department="+department+"&module="+module;
				
				if (!checkString){
					//alert("searchString="+searchString);
					showError("Error", "No Search criteria defined. Please select at least a College");
					return false;
				}
				
				//Clear previous dropdown content
				$("select[name='csdmUsers']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='csdmUsers']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow

				searchString = searchString.replace(/ /g, "_");
				var url = 'tracking.do?act=displayUserList'+searchString;
				
				$.getJSON(url, function(data) {
					$("select[name='csdmUsers']").empty(); //Remove all previous options again (Remove temp/loading option above)
					var ddItems = [];
					ddItems.push('<option value="0">Select Person</option>');
					cache : false,
					$.each(data, function(key, data2) {
						//alert("Users Key="+key);
						if (key === "Error"){
							showError("Error", data2);
							CSDMUserErr = true;
							return false;
						}
						if (key === "Empty"){
							showError("Empty", data2);
							CSDMUserErr = true;
							return false;
						}
						if (key === "Users"){
							$.each(data2, function(key2, val2) {
								//alert("Users="+key2+"~"+val2.toUpperCase());
								var splitString = val2.split('~');
								//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+" - "+splitString[4].toUpperCase()+" ("+splitString[3].toUpperCase()+")</option>");
							});
						}
					});
					if (!CSDMUserErr){
						$("select[name='csdmUsers']").html(ddItems);
					}
				});
						
			});
						
			//Find User by Building
			$("input[name='searchBuildingUser']").click(function(){
				var building 	= $("select[name='building']").val();
				
				var searchString= "";
				var buildingUserErr = false;
				
				if (building !== null && building !== "0"){
					searchString = "&searchCheck=building&building="+building;
				}else{
					showError("Error", "No Search criteria defined. Please select a Building");
					return false;
				}
				//Clear previous dropdown content
				$("select[name='buildingUsers']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='buildingUsers']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow

				searchString = searchString.replace(/ /g, "_");
				var url = 'tracking.do?act=displayUserList'+searchString;
				
				$.getJSON(url, function(data) {
					$("select[name='buildingUsers']").empty(); //Remove all previous options again (Remove temp/loading option above)
					var ddItems = [];
					ddItems.push('<option value="0">Select Person</option>');
					cache : false,
					$.each(data, function(key, data2) {
						//alert("Users Key="+key);
						if (key === "Error"){
							showError("Error", data2);
							buildingUserErr = true;
							return false;
						}
						if (key === "Empty"){
							showError("Empty", data2);
							buildingUserErr = true;
							return false;
						}
						if (key === "Users"){
							$.each(data2, function(key2, val2) {
								//alert("Users="+key2+"~"+val2.toUpperCase());
								var splitString = val2.split('~');
								//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+" - "+splitString[4].toUpperCase()+" ("+splitString[3].toUpperCase()+")</option>");
							});
						}
					});
					if (!buildingUserErr){
						$("select[name='buildingUsers']").html(ddItems);
					}
				});
						
			});
			
			
			
			$("input[name='searchUserAddress'], input[name='searchCSDMUserAddress'], input[name='searchBuildingUserAddress']").click(function () {
				
			    if (this.name === 'searchUserAddress') {
			        var userVal = $("select[name='users']").val();
			        var userText = $("select[name='users'] option:selected").text();
			    }else if (this.name == 'searchCSDMUserAddress') {
			        var userVal = $("select[name='csdmUsers']").val();
			        var userText = $("select[name='csdmUsers'] option:selected").text();
			    }else if (this.name == 'searchBuildingUserAddress') {
			        var userVal = $("select[name='buildingUsers']").val();
			        var userText = $("select[name='buildingUsers'] option:selected").text();
			    }
			    
			    $.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Retrieving address details for: <br/>"+userText+"</b>" });

			
			    //alert("Search User Address=" +userVal+", Detail="+userText);
			    $(".manualAddress").hide();
				$("input[name='userAddress1']").val("");
				$("input[name='userAddress2']").val("");
				$("input[name='userAddress3']").val("");
				$("input[name='userAddress4']").val("");
				$("input[name='userPostal']").val("");
				$("input[name='userEmail']").val("");
				
				var searchString= "";
				var searchAdrErr = false;
				if (userVal !== null && userVal !== "0"){
					searchString = "&user="+userVal;
				}else{
					showError("Error", "No Search criteria defined. Please select a Person to search.");
					return false;
				}
				searchString = searchString.replace(/ /g, "_");
				var url = 'tracking.do?act=displayUserAddress'+searchString;
				
				$.getJSON(url, function(data) {
					var ddItems = [];
					cache : false,
					$.each(data, function(key, data2) {
						//alert("Users Key="+key);
						if (key === "Error"){
							$.unblockUI();
							showError("Error", data2);
							searchAdrErr = true;
							return false;
						}
						if (key === "Empty"){
							$.unblockUI();
							showError("Empty", data2);
							searchAdrErr = true;
							return false;
						}
						if (key === "Address"){
							$.each(data2, function(key2, val2) {
								//alert("Address="+key2+"~"+val2.toUpperCase());
								var splitString = val2.split('~');
								var value1 = splitString[0];
								var value2 = splitString[1];
								var value3 = splitString[2];
								//alert("Option:"+key2+", Value1="+value1+", Value2="+value2+", Value3="+value3);
								$(".manualAddress").show();
								if (value1 != null && value1 != "" && value1 != " " && value1 != "N/A" && value2 != "N/A" && value3 != "N/A"){
									$("input[name='userAddress1']").val(userText.toUpperCase());
									$("input[name='userAddress1']").prop("readonly", true);
								}else{
									$("#userAddress1").hide();
								}
								if (value1 != null && value1 != "" && value1 != " " && value1 != "N/A"){
									$("input[name='userAddress2']").val(value1.toUpperCase());
									$("input[name='userAddress2']").prop("readonly", true);
								}else{
									$("#userAddress2").hide();
								}
								if (value2 != null && value2 != "" && value2 != " " && value2 != "N/A"){
									$("input[name='userAddress3']").val(value2.toUpperCase());
									$("input[name='userAddress3']").prop("readonly", true);
								}else{
									$("#userAddress3").hide();
								}
								if (value3 != null && value3 != "" && value3 != " " && value3 != "N/A"){
									$("input[name='userEmail']").val(value3.toUpperCase());
									$("input[name='userEmail']").prop("readonly", true);
								}else{
									$("#userEmail").hide();
								}
								$("#userAddress4").hide();
								$("#userPostal").hide();
							});
							$.unblockUI();
						}
					});
				});
			});
					
		}); //OnReady
		
		//Find User by Surname
		function populateUser(persNo){
			var surname = "";
			var searchString= "";
			var searchVal = "";
			var userErr = false;
			
			if (persNo == null || persNo == "" || persNo == "undefined"){
				surname 	= $("input[name='surname']").val();
				searchString = "&searchCheck=Surname&surname="+surname;
			}else{
				searchVal = persNo.replace(/^0+/, ''); //Remove leading "0" (Zero)
				searchString = "&searchCheck=PersNo&searchPersNo="+searchVal;
			}
			//alert("searchString="+searchString);
			
			//Clear previous dropdown content
			$("select[name='users']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='users']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow

			searchString = searchString.replace(/ /g, "_");
			var url = 'tracking.do?act=displayUserList'+searchString;
			
			$.getJSON(url, function(data) {
				$("select[name='users']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Person</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Users Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						userErr = true;
						return false;
					}
					if (key === "Empty"){
						showError("Empty", data2);
						userErr = true;
						return false;
					}
					if (key === "Users"){
						$.each(data2, function(key2, val2) {
							//alert("Users="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if(splitString[0] === $.trim(searchVal)) {
								ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+" - "+splitString[4].toUpperCase()+" ("+splitString[3].toUpperCase()+")</option>");
						    }else{
						    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+" - "+splitString[4].toUpperCase()+" ("+splitString[3].toUpperCase()+")</option>");
						    }
							
						});
					}
				});
				if (!userErr){
					$("select[name='users']").html(ddItems);
					$("input[name='surname']").val("");
				}
			});
		}
		
		function populateProvince(province, region){
			var provinceErr = false;

			//Clear previous dropdown content
			$("select[name='province']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='province']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayProvinceInformation&province='+province;
			$.getJSON(url, function(data) {
				$("select[name='province']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Province</option>');
				//ddItems.push('<option value="-1">All Provinces</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Province Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						provinceErr = true;
						return false;
					}
					if (key === "Provinces"){
						$.each(data2, function(key2, val2) {
							//alert("Province="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (province == null || province == "undefined" || province == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceProv = province.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceProv) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
							
						});
					}
				});
				if (!provinceErr){
					$("select[name='province']").html(ddItems);
					$("select[name='region']").html('<option value="0">Select Regional Office</option>');
					//$("select[name='users']").html('<option value="0">Select Person</option>');
					if (province != null && province != "undefined" && province != ""){
						populateRegion(province, region);
					}
				}
			});
		}
		
		function populateRegion(province, region){
			var regionErr = false;

			//Clear previous dropdown content
			$("select[name='region']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='region']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayRegionalOfficeInformation&province='+province;
			$.getJSON(url, function(data) {
				$("select[name='region']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Regional Office</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Regions Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						regionErr = true;
						return false;
					}
					if (key === "RegionalOffices"){
						$.each(data2, function(key2, val2) {
							//alert("Regions="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (region == null || region == "undefined" || region == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceReg = region.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceReg) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!regionErr){
					$("select[name='region']").html(ddItems);
					//$("select[name='users']").html('<option value="0">Select Person</option>');
				}
			});
		}
		
		function populateBuilding(building){
			var buildingErr = false;
			
			//Clear previous dropdown content
			$("select[name='building']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='building']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayBuildingInformation&building='+building;
			$.getJSON(url, function(data) {
				$("select[name='building']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Building</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Building Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						buildingErr = true;
						return false;
					}
					if (key === "Buildings"){
						$.each(data2, function(key2, val2) {
							//alert("Building="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (building == null || building == "undefined" || building == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceBld = building.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceBld) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!buildingErr){
					$("select[name='building']").html(ddItems);
					$("select[name='buildingUsers']").html('<option value="0">Select Person</option>');
				}
			});
		}
		
		function populateBuildingUser(){
			$("select[name='buildingUsers']").html('<option value="0">Select Person</option>');
		}
		
		function populateCollege(college, school, department, module){
			var collegeErr = false;
			
			//Clear previous dropdown content
			$("select[name='college']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='college']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayCollegeInformation';
			$.getJSON(url, function(data) {
				$("select[name='college']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select College</option>');
				//ddItems.push('<option value="-1">All Colleges</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("College Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						collegeErr = true;
						return false;
					}
					if (key === "Colleges"){
						$.each(data2, function(key2, val2) {
							//alert("College="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (college == null || college == "undefined" || college == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceCol = college.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceCol) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!collegeErr){
					$("select[name='college']").html(ddItems);
					$("select[name='school']").html('<option value="0">Select School</option>');
					$("select[name='department']").html('<option value="0">Select School</option>');
					$("select[name='module']").html('<option value="0">Select Module</option>');
					$("select[name='csdmUsers']").html('<option value="0">Select Person</option>');
					if (college != null && college != "undefined" && college != ""){
						populateSchool(college, school, department, module);
					}
				}
			});
			
		}
		
		function populateSchool(college, school, department, module){
			var schoolErr = false;
			
			//Clear previous dropdown content
			$("select[name='school']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='school']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displaySchoolInformation&college='+college;
			$.getJSON(url, function(data) {
				$("select[name='school']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select School</option>');
				//ddItems.push('<option value="-1">All Schools</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("School Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						schoolErr = true;
						return false;
					}
					if (key === "Schools"){
						$.each(data2, function(key2, val2) {
							//alert("School="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (school == null || school == "undefined" || school == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceSchool = school.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceSchool) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!schoolErr){
					$("select[name='school']").html(ddItems);
					$("select[name='department']").html('<option value="0">Select School</option>');
					$("select[name='module']").html('<option value="0">Select Module</option>');
					$("select[name='csdmUsers']").html('<option value="0">Select Person</option>');
					if (college != null && college != "undefined" && college != "" && school != null && school != "undefined" && school != ""){
						populateDepartment(college, school, department, module);
					}
				}
			});
		}
		
		function populateDepartment(college, school, department, module){
			var departmentErr = false;
			
			//Clear previous dropdown content
			$("select[name='department']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='department']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayDepartmentInformation&school='+school;
			$.getJSON(url, function(data) {
				$("select[name='department']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Department</option>');
				//ddItems.push('<option value="-1">All Departments</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Department Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						departmentErr = true;
						return false;
					}
					if (key === "Departments"){
						$.each(data2, function(key2, val2) {
							//alert("School="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (department == null || department == "undefined" || department == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceDepartment = department.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceDepartment) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!departmentErr){
					$("select[name='department']").html(ddItems);
					$("select[name='module']").html('<option value="0">Select Module</option>');
					$("select[name='csdmUsers']").html('<option value="0">Select Person</option>');
					if (college != null && college != "undefined" && college != "" && school != null && school != "undefined" && school != "" && department != null && department != "undefined" && department != ""){
						populateModule(college, school, department, module);
					}
				}
			});
		}
		
		function populateModule(college, school, department, module){
			var moduleErr = false;
			
			//Clear previous dropdown content
			$("select[name='module']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='module']").append('<option value="">Loading....</option>'); //Temp option to show if web service retrieval is slow
			
			var url = 'tracking.do?act=displayModuleInformation&department='+department;
			$.getJSON(url, function(data) {
				$("select[name='module']").empty(); //Remove all previous options again (Remove temp/loading option above)
				var ddItems = [];
				ddItems.push('<option value="0">Select Module</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Modules Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						moduleErr = true;
						return false;
					}
					if (key === "Modules"){
						$.each(data2, function(key2, val2) {
							//alert("Module="+key2+"~"+val2.toUpperCase());
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if (module == null || module == "undefined" || module == ""){
								ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							}else{
								var noSpaceData = splitString[0].replace(/ /g,'');
								var noSpaceModule = module.replace(/ /g,'');
								if (noSpaceData.indexOf(noSpaceModule) > -1) {
									ddItems.push("<option value='"+splitString[0] + "' selected=selected>"+splitString[1].toUpperCase()+"</option>");
							    }else{
							    	ddItems.push("<option value='"+splitString[0]+"'>"+splitString[1].toUpperCase()+"</option>");
							    }
							}
						});
					}
				});
				if (!moduleErr){
					$("select[name='module']").html(ddItems);
					$("select[name='csdmUsers']").html('<option value="0">Select Person</option>');
				}
			});
		}
		
		function populateCSDMUser(){
			$("select[name='csdmUsers']").html('<option value="0">Select Person</option>');
		}
		
		function showHide(addressType){
			//alert("Radio: "+addressType);
		  	if(addressType=="dsaa"){
		        $(".provinceAddress").hide();
		        $(".personAddress").hide();
		        $(".otherAddress").hide();
		        $(".manualAddress").hide();
		        $(".buildingAddress").hide();
		  	}else if(addressType=="dispatch"){
		        $(".provinceAddress").hide();
		        $(".personAddress").hide();
		        $(".otherAddress").hide();
		        $(".manualAddress").hide();
		        $(".buildingAddress").hide();
		    }else if(addressType=="person"){
		      	$(".provinceAddress").hide();
		       	$(".personAddress").show();
		        $(".otherAddress").hide();
		        $(".manualAddress").hide();
		        $(".buildingAddress").hide();
		    }else if(addressType=="other"){
		       	$(".provinceAddress").hide();
		       	$(".personAddress").hide();
		        $(".otherAddress").show();
		        $(".manualAddress").hide();
		        $(".buildingAddress").hide();
		    }else if(addressType=="province"){
			    $(".provinceAddress").show();
			    $(".personAddress").hide();
			    $(".otherAddress").hide();
			    $(".manualAddress").hide();
			    $(".buildingAddress").hide();
		    }else if(addressType=="building"){
		       	$(".provinceAddress").hide();
		       	$(".personAddress").hide();
		       	$(".otherAddress").hide();
		        $(".manualAddress").hide();
		        $(".buildingAddress").show();
		    }else if(addressType=="manual"){
		       	$(".provinceAddress").hide();
		       	$(".personAddress").hide();
		       	$(".otherAddress").hide();
		        $(".manualAddress").show();
		        $(".buildingAddress").hide();
		        $("#userAddress1").show();
				$("#userAddress2").show();
				$("#userAddress3").show();
				$("#userAddress4").show();
				$("#userPostal").show();
				$("#userEmail").show();
		        $("input[name='userAddress1']").val("");
				$("input[name='userAddress2']").val("");
				$("input[name='userAddress3']").val("");
				$("input[name='userAddress4']").val("");
				$("input[name='userPostal']").val("");
				$("input[name='userEmail']").val("");
		    }
		}

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
		    $.unblockUI();		    
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
			});
		}
		
		function retrieveSavedAddress() { //Populate Previous Saved Address
			
			$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Retrieving Previously Saved Address</b>" });

		    $(".manualAddress").hide();
			$("input[name='userAddress1']").val("");
			$("input[name='userAddress2']").val("");
			$("input[name='userAddress3']").val("");
			$("input[name='userAddress4']").val("");
			$("input[name='userPostal']").val("");
			$("input[name='userEmail']").val("");
			
			var searchString= "";
			var searchAdrErr = false;

			var url = 'tracking.do?act=getSavedAddress';
			$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				var ddItems = [];
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Address Key="+key);
					if (key === "Error"){
						$.unblockUI();
						showError("Error", data2);
						searchAdrErr = true;
						return false;
					}
					if (key === "Empty"){
						$.unblockUI();
						showError("Empty", "No Previously saved Address found");
						searchAdrErr = true;
						return false;
					}
					
					if (key === "Address"){
						$.each(data2, function(key2, val2) {
							//alert("Address="+key2+"~"+val2.toUpperCase());
							var manual = false;
							var splitVal = val2.split('~');
							//alert("Option="+key2+", Value1="+splitVal[0]+", Value2="+splitVal[1]+", Value3="+splitVal[2]+", Value4="+splitVal[3]+", Value5="+splitVal[4]+", Value6="+splitVal[5]);
							var value1 = splitVal[0];
							var value2 = splitVal[1];
							var value3 = splitVal[2];
							var value4 = splitVal[3];
							var value5 = splitVal[4];
							var value6 = splitVal[5];
							
							if (value1 != null && value1 != "" && value1 != "undefined"){
								value1 = value1.toUpperCase();
							}
							if (value2 != null && value2 != "" && value2 != "undefined"){
								value2 = value2.toUpperCase();
							}
							if (value3 != null && value3 != "" && value3 != "undefined"){
								value3 = value3.toUpperCase();
							}
							if (value4 != null && value4 != "" && value4 != "undefined"){
								value4 = value4.toUpperCase();
							}
							if (value6 != null && value6 != "" && value6 != "undefined"){
								value6 = value6.toUpperCase();
							}
							
							if (value1.indexOf("|") > -1) {
								var splitString = value1.split('|');
								var splitVal1Key = splitString[0];
								var splitVal1Value = splitString[1];
								//alert("splitVal1Key="+splitVal1Key+", splitVal2Value="+splitVal1Value);
								if(splitVal1Key.toUpperCase() === "DSAA"){
									$('input[name="addressType"][value="dsaa"]').prop('checked', true);
									$("input[name='userAddress1']").val(splitVal1Value.toUpperCase());
									$(".provinceAddress").hide();
							        $(".personAddress").hide();
							        $(".otherAddress").hide();
							        $(".manualAddress").hide();
							        $(".buildingAddress").hide();
								}else if(splitVal1Key.toUpperCase() === "DISPATCH"){
									$('input[name="addressType"][value="dispatch"]').prop('checked', true);
									$("input[name='userAddress1']").val(splitVal1Value.toUpperCase());
									$(".provinceAddress").hide();
							        $(".personAddress").hide();
							        $(".otherAddress").hide();
							        $(".manualAddress").hide();
							        $(".buildingAddress").hide();
								}else if(splitVal1Key.toUpperCase() === "COLLEGE"){
									$('input[name="addressType"][value="other"]').prop('checked', true);
									var school = "";
									var department = "";
									var module = "";
									var schoolData = value2;
									var departmentData = value3;
									var moduleData = value4;
									if (schoolData.indexOf("|") > -1) {
										var splitSchoolData = schoolData.split('|');
										var splitSchoolKey = splitSchoolData[0];
										var splitSchoolValue = splitSchoolData[1];
										school=splitSchoolValue;
									}
									if (departmentData.indexOf("|") > -1) {
										var splitDepartmentData = departmentData.split('|');
										var splitDepartmentKey = splitDepartmentData[0];
										var splitDepartmentValue = splitDepartmentData[1];
										department=splitDepartmentValue;
									}
									if (moduleData.indexOf("|") > -1) {
										var splitModuleData = moduleData.split('|');
										var splitModuleKey = splitModuleData[0];
										var splitModuleValue = splitModuleData[1];
										module=splitModuleValue;
									}
									populateCollege(splitVal1Value,school,department,module);
									$(".provinceAddress").hide();
							        $(".personAddress").hide();
							        $(".otherAddress").show();
							        $(".manualAddress").hide();
							        $(".buildingAddress").hide();
								}else if(splitVal1Key.toUpperCase() === "PROVINCE"){
									$('input[name="addressType"][value="province"]').prop('checked', true);
									var region = "";
									var regData = value2;
									if (regData.indexOf("|") > -1) {
										var splitData = regData.split('|');
										var splitRegKey = splitData[0];
										var splitRegValue = splitData[1];
										region=splitRegValue;
									}
									populateProvince(splitVal1Value,region);
									$(".provinceAddress").show();
							        $(".personAddress").hide();
							        $(".otherAddress").hide();
							        $(".manualAddress").hide();
							        $(".buildingAddress").hide();
								}else if(splitVal1Key.toUpperCase() === "BLD"){
									$('input[name="addressType"][value="building"]').prop('checked', true);
									populateBuilding(splitVal1Value);
									$(".provinceAddress").hide();
							        $(".personAddress").hide();
							        $(".otherAddress").hide();
							        $(".manualAddress").hide();
							        $(".buildingAddress").show();
								}else if(splitVal1Key.toUpperCase() === "PERSON"){
									$('input[name="addressType"][value="person"]').prop('checked', true);
									populateUser(splitVal1Value);
									$(".provinceAddress").hide();
							        $(".personAddress").show();
							        $(".otherAddress").hide();
							        $(".manualAddress").hide();
							        $(".buildingAddress").hide();
								}
							}else {
								//alert("Saved Address was Manual input");
								$('input[name="addressType"][value="manual"]').prop('checked', true);
								$(".provinceAddress").hide();
						        $(".personAddress").hide();
						        $(".otherAddress").hide();
						        $(".manualAddress").show();
						        $(".buildingAddress").hide();
						        
								if (value1 != null && value1 != ""){
									$("input[name='userAddress1']").val(value1);
								}
								if (value2 != null && value2 != ""){
									$("input[name='userAddress2']").val(value2);
								}
								if (value3 != null && value3 != ""){
									$("input[name='userAddress3']").val(value3);
								}
								if (value4 != null && value4 != ""){
									$("input[name='userAddress4']").val(value4);
								}
								if (value5 != null && value5 != "" && value5 != "0"){
									$("input[name='userPostal']").val(value5);
								}
								if (value6 != null && value6 != ""){
									$("input[name='userEmail']").val(value6);
								}
							}
						});
					}
				});
			});
			$.ajaxSetup( { "async": true } );
			$.unblockUI();
		} //populatePrevious
		
		
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
								$.unblockUI();
								showError("Information","Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" as it already exists in Cover Docket list" );
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
								$.unblockUI();
								showError("Information","Removing StudentNumber="+splitString2[0]+", AssignmentNumber="+splitString2[1]+" from Consignment list as it already exists in Cover Docket list" );
								$("#conUniqueAssignmentList"+splitString2[0]+splitString2[1]).remove();
								countConListAssign = countConListAssign -1;
								//alert("countConListAssign="+countConListAssign);
								if (countConListAssign === 0){
									//alert("Removing uniqueConAssignmentDiv");
									$("#conUniqueAssignmentList").remove();
								}
							});
						}else{
							//alert("key~"+key);
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
								$.unblockUI();
								showError("Information","Removing Unique Cover Docket="+unqVal+" As it already exists in Consignment list" );
								$("#docketList"+unqVal).remove();
							});
						}
						if (conKey === "RemoveAssignments"){	
							//alert("Processing Removal of Unique Assignments as they are already in Consignment list");
							$.each(conVal, function(unqKey, unqVal) {
								var splitString = unqVal.split('~');
								//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
								$.unblockUI();
								showError("Information","Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" As it already exists in Consignment list");
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
					$.unblockUI();
					$("input[name='enteredConsignmentNumber']").focus();
				}
			});
			
		}
		

		function frmSubmitBookOUT(){
			//showValues(); /*Debug*/
		    $.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Processing Book OUT information</b>" });
			var addressLabels="";
				addressLabels += "&labelProvince="+$("select[name='province']").children(':selected').text();
				addressLabels += "&labelRegion="+$("select[name='region']").children(':selected').text();
				
				addressLabels += "&labelCollege="+$("select[name='college']").children(':selected').text();
				addressLabels += "&labelSchool="+$("select[name='school']").children(':selected').text();
				addressLabels += "&labelDepartment="+$("select[name='department']").children(':selected').text();
				addressLabels += "&labelModule="+$("select[name='module']").children(':selected').text();
				
				addressLabels += "&labelBuilding="+$("select[name='building']").children(':selected').text();
				
				addressLabels += "&labelUsers="+$("select[name='users']").children(':selected').text();
				addressLabels += "&labelCsdmUsers="+$("select[name='csdmUsers']").children(':selected').text();
				addressLabels += "&labelBuildingUsers="+$("select[name='buildingUsers']").children(':selected').text();
				
			
			var url = "tracking.do?act=processInput&process=BookOUT"+addressLabels;
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
			var butCheck = $("input[name='act']").val();
			if (butCheck.toUpperCase() === "CLEAR"){
				$('input:radio[name="addressType"]').removeAttr('checked');
				$(".provinceAddress").hide();
		        $(".personAddress").hide();
		        $(".otherAddress").hide();
		        $(".manualAddress").hide();
		        $(".buildingAddress").hide();
		        
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

<html:form action="/tracking">

<!-- showValues Debug -->
<div id="results"></div>
	
<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
</p>
	
<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
<div class="container">
	<div class="panel panel-default">
	  	<div class="panel-body">
		  	<div class="table-responsive">
				<table style="width:100%">
					<tbody id="titleTextImgAdr">
						<tr>
							<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.destination"/></td>
							<td bgcolor="lightgray" align="right"><img id="imageAdrPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageAdrMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
						</tr>
					</tbody>
					<tbody id="contentDivImgAdr">
						<tr>
							<td colspan="2">
								<table style="width:100%">
									<tr>
										<td style="width:30%" class="topAlign">
											<table style="width:100%">
												<tr>
													<td style="width:20px"><html:radio property="addressType" value="dsaa"/></td>
													<td><fmt:message key="address.check.info.dsaa"/></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td style="width:20px"><html:radio property="addressType" value="dispatch"/></td>
													<td><fmt:message key="address.check.info.dispatch"/></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td style="width:20px"><html:radio property="addressType" value="province"/></td>
													<td><fmt:message key="address.check.info.province"/></td>
												</tr>
										 	</table>
							 			</td>
										<td valign="top" style="width:40%">
											<table style="width:100%">
												<tr>
													<td style="width:20px"><html:radio property="addressType" value="other"/></td>
													<td><fmt:message key="address.check.info.csd"/></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td style="width:20px"><html:radio property="addressType" value="building"/></td>
													<td><fmt:message key="address.check.info.building"/></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td style="width:20px"><html:radio property="addressType" value="person"/></td>
													<td><fmt:message key="address.check.info.person"/></td>
												</tr>
											</table>
							 			</td>
							 			<td valign="top" style="width:30%">
											<table style="width:100%">
												<tr>
													<td style="width:20px"><html:radio property="addressType" value="manual"/></td>
													<td><fmt:message key="address.check.info.manual"/></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td colspan="2" style="height:5px;"></td>
												</tr><tr>
													<td colspan="2"><html:button property="savedAddress" onclick="retrieveSavedAddress();"><fmt:message key="button.displaySaved" /></html:button></td>
												</tr>
											</table>
							 			</td>
							 		</tr>
								</table>
							</td>
						</tr><tr>
							<td colspan="2">
								<table style="width:100%">
									<tbody class="provinceAddress" style="display:none">
										<tr>
											<td colspan="2"><hr></td>
										</tr><tr>
											<td colspan="2">
												<table style="width:100%">
													<tr>
														<td style="width:30%"><fmt:message key="province.disc"/></td>
														<td style="width:70%">
															<html:select property="province" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:99%">
																<html:option value="0">Select Province</html:option>
															</html:select>
														</td>
														<td>&nbsp;</td>
													</tr><tr>
														<td colspan="3" style="height:5px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="regionaloffice.disc"/></td>
														<td style="width:70%">
															<html:select property="region" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:99%">
																<html:option value="0">Select Regional Office</html:option>
															</html:select>
														</td>
														<td>
															<!--<html:button property="searchProvinceUser"><fmt:message key="button.searchUser" /></html:button>-->
														</td>
													</tr><tr>
														<td colspan="3" style="height:5px;"></td>
													</tr>
													<!-- <tr>
														<td style="width:30%"><fmt:message key="user.disc"/></td>
														<td style="width:70%">
															<html:select property="provinceUsers" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:99%">
																<html:option value="0">Select Person</html:option>
															</html:select>
														</td>
														<td>
															<html:button property="searchProvinceUserAddress"><fmt:message key="button.displayAddress" /></html:button>
														</td>
													</tr>  -->
												</table>
											</td>
										</tr>
									</tbody>

									<tbody class="personAddress" style="display:none"> 
										<tr>
											<td colspan="2"><hr></td>
										</tr><tr>
											<td colspan="2">
												<table style="width:100%">
													<tr>
														<td style="width:30%"><fmt:message key="pers.surname"/></td>
														<td style="width:70%">
															<html:text property="surname" style="width:98%" maxlength="60" ></html:text> 
														</td>
														<td style="width:80px">
															<html:button property="searchUser" onclick="populateUser('');"><fmt:message key="button.search" /></html:button>
														</td>
													</tr><tr>
														<td colspan="3" style="height:5px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="user.disc"/></td>
														<td style="width:70%">
															<html:select property="users" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Person</html:option>
															</html:select>
														</td>
														<td>
															<html:button property="searchUserAddress"><fmt:message key="button.displayAddress" /></html:button>
														</td> 
													</tr>
												</table>
											</td>
										</tr>
									</tbody>
							
									<tbody class="otherAddress" style="display:none">
										<tr>
											<td colspan="2"><hr></td>
										</tr><tr>
											<td colspan="2">
												<table style="width:100%">
													<tr>
														<td style="width:30%"><fmt:message key="college.disc"/></td>
														<td style="width:70%">
															<html:select property="college" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select College</html:option>
															</html:select>
														</td> 
														<td>&nbsp;</td>
													</tr><tr>
														<td colspan="3" style="height:10px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="school.disc"/></td>
														<td style="width:70%">
															<html:select property="school" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select School</html:option>
															</html:select>
														</td> 
														<td>&nbsp;</td>
													</tr><tr>
														<td colspan="3" style="height:10px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="dept.disc"/></td>
														<td style="width:70%">
															<html:select property="department" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Department</html:option>
															</html:select>
														</td> 
														<td>&nbsp;</td>
													</tr><tr>
														<td colspan="3" style="height:5px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="module.disc"/></td>
														<td style="width:70%">
															<html:select property="module" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Module</html:option>
															</html:select>
														</td> 
														<td>
															<html:button property="searchCSDMUser"><fmt:message key="button.searchUser" /></html:button>
														</td> 
													</tr> 
													<tr>
														<td colspan="3" style="height:5px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="user.disc"/></td>
														<td style="width:70%">
															<html:select property="csdmUsers" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Person</html:option>
															</html:select>
														</td>
														<td>
															<html:button property="searchCSDMUserAddress"><fmt:message key="button.displayAddress" /></html:button>
														</td>
													</tr> 
												</table>
											</td>
										</tr>
									</tbody>
							
									<tbody class="buildingAddress" style="display:none">
										<tr>
											<td colspan="2"><hr></td>
										</tr><tr>
											<td colspan="2">
												<table style="width:100%">
													<tr>
														<td style="width:30%"><fmt:message key="building.disc"/></td>
														<td style="width:70%">
															<html:select property="building" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Building</html:option>
															</html:select>
														</td> 
														<td>
															<html:button property="searchBuildingUser"><fmt:message key="button.searchUser" /></html:button>
														</td> 
													</tr> 
													<tr>
														<td colspan="3" style="height:5px;"></td>
													</tr><tr>
														<td style="width:30%"><fmt:message key="user.disc"/></td>
														<td style="width:70%">
															<html:select property="buildingUsers" errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:98%">
																<html:option value="0">Select Person</html:option>
															</html:select>
														</td>
														<td>
															<html:button property="searchBuildingUserAddress"><fmt:message key="button.displayAddress" /></html:button>
														</td>
													</tr> 
												</table>
											</td>
										</tr>
									</tbody>
								
									<tbody class="manualAddress" style="display:none">
										<tr>
											<td colspan="2"><hr></td>
										</tr><tr>
											<td colspan="2">
												<table style="width:100%">
													<tbody id="userAddress1">
														<tr>
															<td style="width:30%"><fmt:message key="pers.userAddress"/></td>
															<td style="width:70%"><html:text property="userAddress1" style="width:98%" maxlength="28" /></td>
															<td>
																<html:button property="hiddenUserPostal"><fmt:message key="button.displayHidden" /></html:button>
															</td>
														</tr>
													</tbody>
													<tbody id="userAddress2">
														<tr>
															<td colspan="2" style="height:10px;"></td>
														</tr><tr>
															<td style="width:30%">&nbsp;</td>
															<td style="width:70%"><html:text property="userAddress2" style="width:98%" maxlength="28" /></td>
															<td>&nbsp;</td>
														</tr>
													</tbody>
													<tbody id="userAddress3">
															<tr>
															<td colspan="2" style="height:10px;"></td>
														</tr><tr>
															<td style="width:30%">&nbsp;</td>
															<td style="width:70%"><html:text property="userAddress3" style="width:98%" maxlength="28" /></td>
															<td>&nbsp;</td>
														</tr>
													</tbody>
													<tbody id="userAddress4">
														<tr>
															<td colspan="2" style="height:10px;"></td>
														</tr><tr>
															<td style="width:30%">&nbsp;</td>
															<td style="width:70%"><html:text property="userAddress4" style="width:98%" maxlength="28" /></td>
															<td>&nbsp;</td>
														</tr>
													</tbody>
													<tbody id="userPostal">
														<tr>
															<td colspan="2" style="height:10px;"></td>
														</tr><tr>
															<td style="width:30%"><fmt:message key="pers.userPostal"/></td>
															<td style="width:70%"><html:text property="userPostal" style="width:98%" maxlength="4" /></td>
															<td>&nbsp;</td>
														</tr>
													</tbody>
													<tbody id="userEmail">
														<tr>
															<td colspan="2" style="height:10px;"></td>
														</tr><tr>
															<td style="width:30%"><fmt:message key="pers.userEmail"/></td>
															<td style="width:70%"><html:text property="userEmail" style="width:98%" maxlength="60"/></td>
															<td>&nbsp;</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

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
						<html:button property="addConsignment" onclick="validateConsignmentList();"><fmt:message key="button.addCon" /></html:button>
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
							<html:button property="addDocket" onclick="validateDocketNumber();"><fmt:message key="button.addDct"  /></html:button> 
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
						<td bgcolor="lightgray" align="right"><img id="imageAssPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageAssMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png'/>"></td>
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
										<html:button property="addAssignment" onclick="validateStudentUniqueNumber();"><fmt:message key="button.addAss" /></html:button>
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
							<html:button property="bookOUT" style="width: 100px; height: 80px" onclick="frmSubmitBookOUT();"><fmt:message key="button.out" /></html:button>&nbsp;
							<html:submit property="act" style="width: 55px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.back" /></html:submit>
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