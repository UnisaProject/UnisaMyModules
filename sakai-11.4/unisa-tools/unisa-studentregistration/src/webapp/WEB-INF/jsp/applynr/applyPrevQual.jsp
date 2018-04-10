<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.HistoryUnisa"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style type="text/css">
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	        /*font-size: 12px !important;*/
			font-weight: normal !important;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		    display: table-cell;
		}
		table { 
		    border-spacing: 2px;
		    <!-- border-collapse: separate; -->
		    /*font-size: 12px !important;
			font-weight: bold !important;*/
		}
		table.itemSummary td, td.itemSummaryLite {
			padding: .1em;
			/*font-size: 12px !important;*/
			font-weight: normal !important;
		}
		td { 
		    padding: 2px;
		}
		select {
		    width: 100%;
		}
		/*
		.nav-tabs { 
			border-bottom: none;
			/*display:block;
			white-space: nowrap;/
		}
		.nav-tabs > li {
			float: none;
		    position:relative;
		    color : white;
		    background-color:#ededed;
		    padding : 2px 2px;
		    border-radius: 5px 5px 0px 0px ;
		    border:0;
		    display: inline-block;
		}
		.nav-tabs > li .close {
			/*float: none; 
			display: inline-block;/
		    margin: -3px 0 0 10px;
		    font-size: 18px;
		    padding: 5px 0;
		    float: right;
		}
		.nav-tabs > li > a	{
		    /* adjust padding for height/
		    padding-top: 4px; 
		    padding-bottom: 4px;
		}
		.nav-tabs > li a[data-toggle=tab] {
		    float: left!important;
		}*/
		
		.nav-tabs > li {
		    position:relative;
		    color : white;
		    background-color:#ededed;
		    padding : 5px 5px;
		    border-radius: 10px 10px 0px 0px ;
		    border:0;
		    display: inline-block;
		}
		.nav-tabs > li > a {
		    display:inline-block;
		}
		.nav-tabs > li > span {
		    display:none;
		    cursor:pointer;
		    position:absolute;
		    right: 6px;
		    top: 8px;
		    color: red;
		}
		.nav-tabs > li:hover > span {
		    display: inline-block;
		}

		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
		}

	</style>

	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  

		$(document).ready(function() {
			
			//Page Default ID = 2
			var id = 2;
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			//Reset all Radio Buttons
			$('input:radio[name="studentApplication.radioComplete"]').removeAttr('checked');

			//Get Saved data
			populateOtherQualifications();
			
			//Check data on Back
			getFromBack();
			
			//Configure Page Tabs
			$(".nav-tabs").on("click", "a", function (e) {
				e.preventDefault();
				if (!$(this).hasClass('add-tab')) {
					$(this).tab('show');
				}
			}).on("click", "span", function () {
					var anchor = $(this).siblings('a');
			        $(anchor.attr('href')).remove();
			        $(this).parent().remove();
			        var tabCount = $('#pageTab > li').length;
			        //alert(tabCount);
			        newID = 0;
				    $('#pageTab > li').each(function() {
				       	var pageId = $(this).children('a').attr('href');
			        	//alert(pageId);
				       	if (pageId == "#tab_1" || pageId == "#Add") {
				         	  return true;
				       	}
				        newID++;
				       	$(this).children('a').html('Qualification ' + newID +
				            ' ');  
				    });
				    var prevID = newID-1;
			        //alert(prevID);
			        $('.nav-tabs li:nth-child(' + prevID + ') a').click();
			    });
			
			$('.add-tab').click(function (e) {
				//$.blockUI({ message: "<img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' />" });
				e.preventDefault();
				id = $(".nav-tabs").children().length; //think about it ;)
				var tabId = 'tab_' + id;
				//alert("add Tab - "+id+" - This="+this);
				$(this).closest('li').before('<li id="showTab_'+id+'"><a href="#tab_'+id+'">Qualification '+id+' </a></li>');
				
				$('.nav-tabs li:nth-child('+id+') a').click();
				//alert("add Tab "+id+" - Done");
				//$.unblockUI();
			});
		
		});
		
		function populateOTHERYear(subNo, firstYear, selectedYear){
			//alert("populateOTHERYear: subNo="+subNo+" firstYear="+firstYear+" selectedYear="+selectedYear);
			var startYear = 1960;
			var thisYear = (new Date()).getFullYear();
			var minOffset = 0;
			var options = "";
			var ddItems = [];
			var maxOffset = thisYear - startYear;
			
			if (firstYear == null || firstYear == 0 || firstYear == "0" || firstYear == ""){
				firstYear = $("select[name='qualOther.historyOTHERYearStart"+subNo+"']").find("option:selected").val();
			}
			if (firstYear != null || firstYear != 0 || firstYear != "0" || firstYear != "" || firstYear != "-1"){
				maxOffset = thisYear - firstYear;
			}
			//alert("populateOTHERYear: subNo="+subNo+" firstYear="+firstYear);
			
			ddItems.push("<option value='-1' selected='selected'>Select Year</option>");
			
			//alert ("Year="+thisYear+", maxOffset="+maxOffset);
			for (var i = minOffset; i <= maxOffset; i++) {
				var year = thisYear - i;
				if (selectedYear != null && selectedYear != "" && selectedYear != "undefined" && selectedYear != "-1" && selectedYear == year){
					ddItems.push("<option value='"+year+"' selected='selected'>"+year+"</option>");
				}else{
					ddItems.push("<option value='"+year+"'>"+year+"</option>");
				}
			}
			$("select[name='qualOther.historyOTHERYearEnd"+subNo+"']").empty();
			$("select[name='qualOther.historyOTHERYearEnd"+subNo+"']").html(ddItems);
			//alert("Done");
		}
		
		function getFromBack(){
			for (var n = 2; n < 16; n++){
				var tab_no = n;
				//Populate Tab Content
				//Show correct University Detail
				var checkCountry = $("select[name='qualOther.historyOTHERCountry"+tab_no+"']").find("option:selected").val();
				if (checkCountry === "1015"){
					$("#historyOTHERUniv"+tab_no).show();
					var checkUniv = $("select[name='qualOther.historyOTHERUniv"+tab_no+"']").find("option:selected").val();
					if (checkUniv === "OTHR"){
						$("#historyOTHERUnivText"+tab_no).show();
					}else{
						$("#historyOTHERUnivText"+tab_no).hide();
					}
				}else{
					$("#historyOTHERUniv"+tab_no).hide();
					$("#historyOTHERUnivText"+tab_no).show();
				}
					
				//setup completed year according to first year
				var firstYear = $("select[name='qualOther.historyOTHERYearStart"+ tab_no +"']").find("option:selected").val();
				var lastYear = $("select[name='qualOther.historyOTHERYearEnd"+ tab_no +"']").find("option:selected").val();
				if (lastYear != null && lastYear != "" && lastYear != " " && lastYear != "undefined" && lastYear != "-1" ){
					populateOTHERYear(tab_no, firstYear, lastYear);
				}else{
					populateOTHERYear(tab_no, firstYear, "");
				}
			}
			//Add new Tabs automatically for populated tabs
			for (var i = 3; i < 16; i++){
				var tabCountry = $("select[name='qualOther.historyOTHERCountry"+i+"']").find("option:selected").val();
				//alert("tabCountry - qualOther.historyOTHERCountry"+i+"=["+tabCountry+"]");
				if (tabCountry != null && tabCountry != "" && tabCountry != "undefined" && tabCountry != "-1"){
					//if ($("#showTab_"+i).css('display') == 'none' || $("#showTab_"+i).css('visibility') === 'hidden')) {
					if( $("#showTab_"+i).length ) { 
						//alert("Tab No="+i+" is Visible");
					}else{
						setTimeout(function(){ $(".add-tab").click()}, 100);
					}
				}
			}
			//Make sure that first tab is clicked after autopopulate
			setTimeout(function(){ $(".tabber1").click()}, 100);
		}
			
		function populateOtherQualifications(){
			
			//alert("Start populateOtherQualifications");
			//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Populating qualifications...</strong>" });
			var otherErr = false;
			var count = 0;
			var url = 'applyForStudentNumber.do?act=populateSTUPREV';
			
			//$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				//data.sort(SortByCode);
				cache : false,
				$.each(data, function(key, keyValue) {
					//alert("Subject Key="+key);
					if (key === "Error"){
						showError("Error", keyValue);
						otherErr = true;
						return false;
					}else if (key === "Empty"){
						//showError("Empty", keyValue); //No reason to show if empty as it may be
						//otherErr = true;
						$.unblockUI();
						return false;
					}else if (key === "PREVQUAL"){
						var tab_no = 2;
						$.each(keyValue, function(key2, keyValue2) {
							//Clear Tab Content
							//$('#tab_'+tab_no).empty();
							//alert("Key2="+key2+" & "+keyValue2.toUpperCase());
							var splitSEQ = keyValue2.split('@');
							//alert("SEQ="+splitSEQ[0]+", Detail="+splitSEQ[1]+" - TAB="+tab_no);
							var splitDetail = splitSEQ[1].split('~');
							
							//Populate Tab Content
							//Show correct University Detail
							var checkCountry = $("select[name='qualOther.historyOTHERCountry"+tab_no+"']").find("option:selected").val();
							if (checkCountry === "1015"){
								$("#historyOTHERUniv"+tab_no).show();
								var checkUniv = $("select[name='qualOther.historyOTHERUniv"+tab_no+"']").find("option:selected").val();
								if (checkUniv === "OTHR"){
									$("#historyOTHERUnivText"+tab_no).show();
								}else{
									$("#historyOTHERUnivText"+tab_no).hide();
								}
							}else{
								$("#historyOTHERUniv"+tab_no).hide();
								$("#historyOTHERUnivText"+tab_no).show();
							}
								
							//setup completed year according to first year
							populateOTHERYear(tab_no, splitDetail[4], splitDetail[5]);
							//populate last/completed year
							//$("select[name='qualOther.historyOTHERYearEnd"+ tab_no +"']").val(splitDetail[5]); //Populate End Year
							
							//Set up hidden values
								//alert("TAB: "+tab_no+" - FOREIGN_IND="+splitDetail[8]);
							//$("input[name='qualOther.historyOTHERForeign"+ tab_no +"']").val(splitDetail[8]);
							if (checkCountry === "1015"){
								$("input[name='qualOther.historyOTHERForeign"+ tab_no +"']").val("Y");
							}else{
								$("input[name='qualOther.historyOTHERForeign"+ tab_no +"']").val("N");
							}
								
								//alert("TAB: "+tab_no+" - LOCK_UPDATE_IND="+splitDetail[9]);
							$("input[name='qualOther.historyOTHERLock"+ tab_no +"']").val(splitDetail[9]);
								
							//If details come from DB, then only allow update to End Year and Complete
							var checkDBVal = $("input[name='qualOther.historyOTHERYearEndDB"+ tab_no +"']").val();
							if (checkDBVal != null && checkDBVal != "" && checkDBVal != "undefined" && checkDBVal != NaN ){ 
								$("select[name='qualOther.historyOTHERCountry"+ tab_no +"'] option:not(:selected)").prop("disabled", true); 	
								$("select[name='qualOther.historyOTHERUniv"+ tab_no +"'] option:not(:selected)").prop("disabled", true); 								//Disable 
								$("input[name='qualOther.historyOTHERUnivText"+ tab_no +"']").prop('readonly', true);
								$("input[name='qualOther.historyOTHERStudnr"+ tab_no +"']").prop('readonly', true);
								$("input[name='qualOther.historyOTHERQual"+ tab_no +"']").prop('readonly', true);
								$("select[name='qualOther.historyOTHERYearStart"+ tab_no +"'] option:not(:selected)").prop("disabled", true); 

								//Change locked Background colour
								$("select[name='qualOther.historyOTHERCountry"+ tab_no +"']").css("background-color","#FFFEEE");
								$("select[name='qualOther.historyOTHERUniv"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERUnivText"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERStudnr"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERQual"+ tab_no +"']").css("background-color","#FFFEEE");
								$("select[name='qualOther.historyOTHERYearStart"+ tab_no +"']").css("background-color","#FFFEEE");
							}
							
							//If DB input has been processed, don't allow any updates.
							if (splitDetail[9] == "Y"){ //Lock
								$("select[name='qualOther.historyOTHERCountry"+ tab_no +"'] option:not(:selected)").prop("disabled", true); 	
								$("select[name='qualOther.historyOTHERUniv"+ tab_no +"'] option:not(:selected)").prop("disabled", true); 								//Disable 
								$("input[name='qualOther.historyOTHERUnivText"+ tab_no +"']").prop('readonly', true);
								$("input[name='qualOther.historyOTHERStudnr"+ tab_no +"']").prop('readonly', true);
								$("input[name='qualOther.historyOTHERQual"+ tab_no +"']").prop('readonly', true);
								$("input[name='qualOther.historyOTHERComplete"+ tab_no +"']").prop("disabled", true);
								$("select[name='qualOther.historyOTHERYearStart"+ tab_no +"'] option:not(:selected)").prop("disabled", true);
								$("select[name='qualOther.historyOTHERYearEnd"+ tab_no +"'] option:not(:selected)").prop("disabled", true);

								//Change locked Background colour
								$("select[name='qualOther.historyOTHERCountry"+ tab_no +"']").css("background-color","#FFFEEE");
								$("select[name='qualOther.historyOTHERUniv"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERUnivText"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERStudnr"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERQual"+ tab_no +"']").css("background-color","#FFFEEE");
								$("input[name='qualOther.historyOTHERComplete"+ tab_no +"']").css("background-color","#FFFEEE");
								$("select[name='qualOther.historyOTHERYearStart"+ tab_no +"']").css("background-color","#FFFEEE");
								$("select[name='qualOther.historyOTHERYearEnd"+ tab_no +"']").css("background-color","#FFFEEE");
							}
								
								//alert("Saved Data Tab="+tab_no+" - Done");
							count++;
							tab_no++;
						});
						//Add new Tabs automatically for populated tabs
						for (var i = 3; i <16; i++){
							var tabCountry = $("select[name='qualOther.historyOTHERCountry"+i+"']").find("option:selected").val();
							//alert("tabCountry - qualOther.historyOTHERCountry"+i+"=["+tabCountry+"]");
							if (tabCountry != null && tabCountry != "" && tabCountry != "undefined" && tabCountry != "-1"){
								//$('.add-tab').click();
								setTimeout(function(){ $(".add-tab").click()}, 100);
							}
						}
						//Make sure that first tab is clicked after autopopulate
						setTimeout(function(){ $(".tabber1").click()}, 100);
					}
				});
			});
			$.unblockUI();
		}
		//"0":"1@OTHR~CAPE TECHNICON~12345~WHOKNOWS~8~2009~2011~~N~N~N"
		//"0":"1@OTHR~CAPE TECHNICON~12345~WHOKNOWS~8~2009~2011~~Y~N~N"
		
		function checkCountry(tabNo){
			var checkC = $("select[name='qualOther.historyOTHERCountry"+tabNo+"']").find("option:selected").val();
			if (checkC === "1015"){
				$("#historyOTHERUniv"+tabNo).show();
				var checkU = $("select[name='qualOther.historyOTHERUniv"+tabNo+"']").find("option:selected").val();
				if (checkU === "OTHR"){
					$("#historyOTHERUnivText"+tabNo).show();
				}else{
				$("#historyOTHERUnivText"+tabNo).hide();
				}
			}else{
				$("#historyOTHERUniv"+tabNo).hide();
				$("#historyOTHERUnivText"+tabNo).show();
			}
		}
		function checkUniv(tabNo){
			var checkUN = $("select[name='qualOther.historyOTHERUniv"+tabNo+"']").find("option:selected").val();
			if (checkUN === "OTHR"){
				$("#historyOTHERUnivText"+tabNo).show();
			}else{
				$("#historyOTHERUnivText"+tabNo).hide();
			}
		}
		
		function validateSelect(){
			//alert("In Validate");
			var validUnisa = $("input[name='unisaFound']").val();
			var checkOTHERCountry2 = $("select[name='qualOther.historyOTHERCountry2']").find("option:selected").val();
			var checkOTHERCountry3 = $("select[name='qualOther.historyOTHERCountry3']").find("option:selected").val();
			if (validUnisa != "true" && ((checkOTHERCountry2 == null || checkOTHERCountry2 == "" || checkOTHERCountry2 == "undefined" || checkOTHERCountry2 == "-1") && (checkOTHERCountry3 == null || checkOTHERCountry3 == "" || checkOTHERCountry3 == "undefined" || checkOTHERCountry3 == "-1"))){
				showError("Error","Please enter at least one degree or diploma for post-graduate studies");
				return false;
			}
			for (var i = 2; i <16; i++){
				var validCountry = $("select[name='qualOther.historyOTHERCountry"+i+"']").find("option:selected").val();
				//alert("qualOther.historyOTHERCountry"+i+"=["+validCountry+"]");
				if (validCountry != null && validCountry != "" && validCountry != "undefined" && validCountry != "-1"){
					var validUniv = $("select[name='qualOther.historyOTHERUniv"+i+"']").find("option:selected").val();
					//alert("qualOther.historyOTHERUniv"+i+"=["+validUniv+"]");
					var validText = $("input[name='qualOther.historyOTHERUnivText"+i+"']").val();
					//alert("qualOther.historyOTHERUnivText"+i+"=["+validText+"]");
					if (validCountry == "1015" && validUniv == "-1"){
						showError("Error","Please select the Institution or University for the Qualification on Tab "+i);
						return false;
					}
					if (validCountry != "1015" && (validText == null || validText == "" || validText == "undefined")){
						showError("Error","Please enter the Institution name in the field provided for the Qualification on Tab "+i);
						return false;
					}
					if (validUniv === "OTHR" && (validText == null || validText == "" || validText == "undefined")){
						showError("Error","You selected Other as the Institution. Please enter the Institution name in the field provided for the Qualification on Tab "+i);
						return false;
					}
					var validQual = $("input[name='qualOther.historyOTHERQual"+i+"']").val();
					//alert("qualOther.historyOTHERQual"+i+"=["+validQual+"]");
					if (validQual == null || validQual == "" || validQual == "undefined"){
						showError("Error","Please enter the Qualification code or Description for the Qualification on Tab "+i);
						return false;
					}
					var validStart = $("select[name='qualOther.historyOTHERYearStart"+i+"']").find("option:selected").val();
					//alert("qualOther.historyOTHERYearStart"+i+"=["+validStart+"]");
					if (validStart == null || validStart == "" || validStart == "undefined" || validStart == "-1"){
						showError("Error","Please enter the year you first registered for the Qualification on Tab "+i);
						return false;
					}
					var validEnd = $("select[name='qualOther.historyOTHERYearEnd"+i+"']").find("option:selected").val();
					//alert("qualOther.historyOTHERYearEnd"+i+"=["+validEnd+"]");
					if (validEnd == null || validEnd == "" || validEnd == "undefined" || validEnd == "-1"){
						showError("Error","Please enter the year of your last registration or when you completed the Qualification on Tab "+i);
						return false;
					}
					if (validStart > validEnd){
						showError("Error","The last registration or completion year be before the first year for the Qualification on Tab "+i);
						return false;
					}
					var radioComplete = $("input:radio[name='qualOther.historyOTHERComplete"+i+"']:checked").val();	
					//alert("Final - radioComplete="+radioComplete);
					if (radioComplete == null || radioComplete == "" || radioComplete == "undefined"){
						showError("Error", "Please indicate if you have completed the Qualification on Tab "+i);
						return false;
					}else{
						$("input[name='qualOther.historyOTHERHiddenComplete"+i+"']").val([radioComplete]);
					}
					//alert("Final - qualOther.historyOTHERHiddenComplete"+i+"="+radioComplete);
				}
			}
			doSubmit("Continue");
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepPrevQual';
			}else if (button === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (button === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}
			document.studentRegistrationForm.submit();
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
		
	</script>

</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden name="studentRegistrationForm" property="page" value="applyPrevQual"/>
	
	<input type="hidden" id="unisaFound" name="unisaFound" value="<bean:write name='studentRegistrationForm' property='qualUnisaFound' />" />
	<html:hidden name='studentRegistrationForm' property='student.number' />
	
	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.post.header"/></h3>
				</div>
				<div class="panel-body">
			        <ul id="pageTab" class="nav nav-tabs" role="tablist" style="border-bottom:0px">
			            <li class="active" id="showTab_1">
			            	<a href="#tab_1" class="tabber1">Unisa Qualification(s)</a>
			            </li>
			            <li id="showTab_2">
			            	<a href="#tab_2">Qualification 2</a>
			            </li>
			            <li>
			            	<a href="#Add" class="add-tab">+ Add Qualification</a>
			            </li>
			        </ul>
			        <div id="my-tab-content" class="tab-content">
			        	<div class="tab-pane active" id="tab_1">
			        		<br/>
			        		<strong><fmt:message key="page.studentnr.apply.tertiary.unisa"/></strong><br/><br/>
			        		<%-- List of completed qualifications --%>
			        		<logic:empty name="studentRegistrationForm" property="qualUnisa">
			        			<br/><br/>
			        			<fmt:message key="page.studentnr.apply.tertiary.unisaNone"/>
			        			<br/><br/><br/><br/>
			        		</logic:empty>
							<logic:notEmpty name="studentRegistrationForm" property="qualUnisa">
								<sakai:flat_list>
									<tr>
										<th align="left"><fmt:message key="page.last.ha1"/>&nbsp;</th>
										<th align="left"><fmt:message key="page.last.ha3a"/>&nbsp;</th>
										<th align="left"><fmt:message key="page.last.ha3"/>&nbsp;</th>
										<th align="left"><fmt:message key="page.last.start"/>&nbsp;</th>
										<th align="left"><fmt:message key="page.last.end"/>&nbsp;</th>
									</tr>
									<logic:iterate name="studentRegistrationForm" property="qualUnisa" id="record" indexId="i">
										<tr>
											<td><bean:write name="record" property="histUniv"/>&nbsp;</td>
											<td><bean:write name="record" property="histQual"/>&nbsp;</td>
											<td><bean:write name="record" property="histQualDesc"/>&nbsp;</td>
											<td><bean:write name="record" property="histYear"/>&nbsp;</td>
											<td>
												<logic:notEqual name="record" property="histComplete" value="0">
													<bean:write name="record" property="histComplete"/>&nbsp;
												</logic:notEqual>
												<logic:equal name="record" property="histComplete" value="0">
													&nbsp;
												</logic:equal>
											</td>
										</tr>
									</logic:iterate>
								</sakai:flat_list>
								<br/><br/>
								<fmt:message key="page.studentnr.apply.tertiary.unisaInfo" />
								<br/>
							</logic:notEmpty>
			        	</div>
           				<div class="tab-pane" id="tab_2">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 2 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry2" onchange="checkCountry('2');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv2" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv2" onchange="checkUniv('2');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText2" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText2"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText2"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr2"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr2"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual2"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual2"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete2" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete2" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart2" onchange="populateOTHERYear('2', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd2">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ2" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB2" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock2" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign2" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete2" />
						</div>
						
						<div class="tab-pane" id="tab_3">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 3 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry3" onchange="checkCountry('3');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv3" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv3" onchange="checkUniv('3');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText3" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText3"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText3"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr3"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr3"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual3"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual3"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete3" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete3" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart3" onchange="populateOTHERYear('3', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd3">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ3" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB3" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock3" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign3" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete3" />
						</div>
						
						<div class="tab-pane" id="tab_4">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 4 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry4" onchange="checkCountry('4');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv4" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv4" onchange="checkUniv('4');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText4" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText4"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText4"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr4"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr4"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual4"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual4"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete4" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete4" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart4" onchange="populateOTHERYear('4', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd4">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ4" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB4" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock4" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign4" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete4" />
						</div>
						
						<div class="tab-pane" id="tab_5">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 5 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry5" onchange="checkCountry('5');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv5" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv5" onchange="checkUniv('5');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText5" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText5"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText5"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr5"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr5"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual5"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual5"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete5" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete5" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart5" onchange="populateOTHERYear('5', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd5">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ5" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB5" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock5" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign5" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete5" />
						</div>
						
						<div class="tab-pane" id="tab_6">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 6 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry6" onchange="checkCountry('6');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv6" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv6" onchange="checkUniv('6');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText6" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText6"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText6"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr6"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr6"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual6"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual6"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete6" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete6" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart6" onchange="populateOTHERYear('6', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd6">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ6" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB6" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock6" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign6" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete6" />
						</div>
						
						<div class="tab-pane" id="tab_7">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 7 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry7" onchange="checkCountry('7');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv7" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv7" onchange="checkUniv('7');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText7" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText7"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText7"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr7"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr7"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual7"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual7"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete7" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete7" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart7" onchange="populateOTHERYear('7', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd7">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ7" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB7" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock7" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign7" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete7" />
						</div>
						
						<div class="tab-pane" id="tab_8">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 8 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry8" onchange="checkCountry('8');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv8" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv8" onchange="checkUniv('8');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText8" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText8"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText8"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr8"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr8"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual8"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual8"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete8" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete8" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart8" onchange="populateOTHERYear('8', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd8">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ8" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB8" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock8" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign8" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete8" />
						</div>
						
						<div class="tab-pane" id="tab_9">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 9 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry9" onchange="checkCountry('9');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv9" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv9" onchange="checkUniv('9');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText9" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText9"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText9"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr9"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr9"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual8"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual8"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete9" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete9" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart9" onchange="populateOTHERYear('9', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd9">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ9" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB9" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock9" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign9" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete9" />
						</div>
						
						<div class="tab-pane" id="tab_10">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 10 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry10" onchange="checkCountry('10');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv10" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv10" onchange="checkUniv('10');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText10" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText10"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText10"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr10"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr10"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual9"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual9"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete10" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete10" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart10" onchange="populateOTHERYear('10', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd10">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ10" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB10" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock10" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign10" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete10" />
						</div>
						
						<div class="tab-pane" id="tab_11">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 11 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry11" onchange="checkCountry('11');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv11" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv11" onchange="checkUniv('11');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText11" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText11"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText11"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr11"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr11"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual11"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual11"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete11" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete11" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart11" onchange="populateOTHERYear('11', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd11">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ11" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB11" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock11" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign11" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete11" />
						</div>
						
						<div class="tab-pane" id="tab_12">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 12 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry12" onchange="checkCountry('12');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv12" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv12" onchange="checkUniv('12');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText12" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText12"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText12"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr12"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr12"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual12"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual12"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete12" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete12" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart12" onchange="populateOTHERYear('12', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd12">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ12" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB12" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock12" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign12" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete12" />
						</div>
						
						<div class="tab-pane" id="tab_13">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 13 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry13" onchange="checkCountry('13');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv13" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv13" onchange="checkUniv('13');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText13" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText13"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText13"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr13"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr13"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual13"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual13"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete13" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete13" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart13" onchange="populateOTHERYear('13', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd13">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ13" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB13" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock13" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign13" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete13" />
						</div>
						
						<div class="tab-pane" id="tab_14">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 14 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry14" onchange="checkCountry('14');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv14" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv14" onchange="checkUniv('14');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText14" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText14"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText14"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr14"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr14"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual14"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual14"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete14" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete14" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart14" onchange="populateOTHERYear('14', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd14">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ14" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB14" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock14" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign14" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete14" />
						</div>
						
						<div class="tab-pane" id="tab_15">
           					<br/>
							<strong><fmt:message key="page.studentnr.apply.tertiary"/></strong><br/>	
							Qualification 15 details:<br/>
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr><tr>
									<td><fmt:message key="page.last.country"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERCountry15" onchange="checkCountry('15');">
					 						<html:options collection="countrylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUniv15" style="display: none;">
									<td><fmt:message key="page.last.ha1"/>&nbsp;<fmt:message key="page.last.hb1"/>&nbsp;<fmt:message key="page.last.hc1"/>&nbsp;</td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERUniv15" onchange="checkUniv('15');">
					 						<html:options collection="universitylistShort" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
								<tr id="historyOTHERUnivText15" style="display: none;">
									<td><fmt:message key="page.last.ha2"/>&nbsp;<fmt:message key="page.last.hb2"/>&nbsp;<fmt:message key="page.last.hc2"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERUnivText15"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERUnivText15"/>" maxlength="50" size="21"/></td>
								</tr>
								<tr>
									<td><fmt:message key="page.last.ha6"/><fmt:message key="page.last.hb6"/><fmt:message key="page.last.hc6"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERStudnr15"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERStudnr15"/>" maxlength="20" size="15"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.ha3"/>&nbsp;<fmt:message key="page.last.hb3"/>&nbsp;<fmt:message key="page.last.hc3"/>&nbsp;</td>
									<td><input class="stretch" type="text" name="qualOther.historyOTHERQual15"  value="<bean:write name="studentRegistrationForm" property="qualOther.historyOTHERQual15"/>" maxlength="50" size="21"/></td>
								</tr><tr>
									<td><fmt:message key="page.last.complete"/></td>
									<td>
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete15" value="Y"/>&nbsp;&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:radio name="studentRegistrationForm" property="qualOther.historyOTHERComplete15" value="N"/>&nbsp;&nbsp;<fmt:message key="page.no"/>&nbsp;
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.first"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearStart15" onchange="populateOTHERYear('15', '', '');">
					 						<html:options collection="yearStartlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr><tr>
									<td><fmt:message key="page.last.last"/></td>
									<td>
										<html:select name="studentRegistrationForm" property="qualOther.historyOTHERYearEnd15">
					 						<html:options collection="yearEndlist" name="studentRegistrationForm" property="value" labelProperty="label"/>
										</html:select>
									</td>
								</tr>
							</table>
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERSEQ15" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERYearEndDB15" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERLock15" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERForeign15" />
							<html:hidden name="studentRegistrationForm" property="qualOther.historyOTHERHiddenComplete15" />
						</div>
			        </div>
			    </div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="validateSelect();">Continue</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');">Back</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>