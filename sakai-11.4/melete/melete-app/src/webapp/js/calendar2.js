// Title: Tigra Calendar
// URL: http://www.softcomplex.com/products/tigra_calendar/
// Version: 3.2 (American date format)
// Date: 10/14/2002 (mm/dd/yyyy)
// Note: Permission given to use this script in ANY kind of applications if
//    header lines are left unchanged.
// Note: Script consists of two files: calendar?.js and calendar.html

// if two digit year input dates after this year considered 20 century.
var NUM_CENTYEAR = 30;
// is time input control required by default
var BUL_TIMECOMPONENT = true;
// are year scrolling buttons required by default
var BUL_YEARSCROLL = true;

var calendars = [];
var RE_NUM = /^\-?\d+$/;

function calendar2(obj_target,hrs,mins,ampm) {

	// assigning methods
	this.gen_date = unisa_server_format_date; //ambrosia_format_date; //cal_gen_date2;
	this.gen_time = ambrosia_format_time; // cal_gen_time2;
	this.gen_tsmp = cal_gen_tsmp2;
	this.prs_date = ambrosia_parse_date; // cal_prs_date2;
	this.prs_time = ambrosia_parse_time; // cal_prs_time2;
	this.prs_tsmp = ambrosia_parse_timeStamp; // cal_prs_tsmp2;
	this.prs_ampm = ambrosia_parse_am_pm; // cal_prs_ampm2;
	this.popup    = cal_popup2;
	this.month_names = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
	this.gen_now = ambrosia_now;

	// validate input parameters
	if (!obj_target)
		return cal_error("Error calling the calendar: no target control specified");
	if (obj_target.value == null)
		return cal_error("Error calling the calendar: parameter specified is not valid target control");
	this.target = obj_target;
	this.time_comp = BUL_TIMECOMPONENT;
	this.year_scroll = BUL_YEARSCROLL;
	
	// register in global collections
	this.id = calendars.length;
	calendars[this.id] = this;
	// Adding these to set times for start and end dates differently (SAK-301)
	this.sethrs = hrs;
	this.setmins = mins;
	this.ampm = ampm;
}

function ambrosia_now()
{
	var rv = new Date();
	rv.setHours(this.sethrs);
	rv.setMinutes(this.setmins);
	rv.setSeconds(0);
	rv.setMilliseconds(0);
	return rv;
}

function cal_popup2 (str_datetime,ampm_val) {

	this.dt_current = this.prs_tsmp(str_datetime ? str_datetime : this.target.value);
	if (!this.dt_current) return;
	
	if (!str_datetime)
	{
	  this.ampm_val = this.prs_ampm(this.target.value);
      if (!this.ampm_val) this.ampm_val = this.ampm;
	}
    else
	{
	  this.ampm_val = ampm_val;
	} 

	var obj_calwindow = window.open(
		'/etudes-melete-tool/calendar.html?datetime=' + this.dt_current.valueOf()+ '&ampmval=' + this.ampm_val +
		'&id=' + this.id,
		'Calendar', 'width=250,height='+(this.time_comp ? 250 : 190)+
		',status=no,resizable=no,top=200,left=200,dependent=yes,alwaysRaised=yes'
	);
	obj_calwindow.opener = window;
	obj_calwindow.focus();
}

// timestamp generating function
function cal_gen_tsmp2 (dt_datetime) {
return(this.gen_date(dt_datetime) + ' ' + this.gen_time(dt_datetime));

}

function unisa_server_format_date(timeStamp)
{
        //10 Apr 2013 1:00 AM  //Oct 16, 2013 8:00 AM
       
        var rv = timeStamp.getDate();
        rv += " ";
        rv += this.month_names[timeStamp.getMonth()];
        rv += " ";
        rv += timeStamp.getFullYear();
        return rv;
}

function ambrosia_format_date(timeStamp)
{
	var rv = this.month_names[timeStamp.getMonth()];
	rv += " ";
	rv += timeStamp.getDate();
	rv += ", ";
	rv += timeStamp.getFullYear();
	return rv;
}

function ambrosia_format_time(timeStamp)
{
	var hours = timeStamp.getHours();
	//if (hours == 0) hours = 12;
	var rv = hours + ":";
	rv += (timeStamp.getMinutes() < 10 ? "0" : "") + timeStamp.getMinutes();
	return rv;
}

function ambrosia_parse_timeStamp(displayStr)
{
	if (displayStr == null) return this.gen_now();

	var time = Number(displayStr); //parseInt(displayStr, 10); // Changed the javascript function from parseInt To Number in order to check the entire date string! ETM
	if (!isNaN(time) && (time >= 0)) return new Date(time);

	var displayParts = displayStr.split(" ");
	if (displayParts.length != 5) this.gen_now();

	//var datePart = displayParts[0] + " " + displayParts[1] + " " + displayParts[2];
	var datePart = displayParts[1] + " " + displayParts[0] + " " + displayParts[2];
	var timePart = displayParts[3] + " " + displayParts[4];
	return this.prs_time(timePart, this.prs_date(datePart));
}

function ambrosia_parse_am_pm(displayStr)
{
	if (displayStr == null) return this.gen_now();

	var time = parseInt(displayStr, 10);
	if (!isNaN(time) && (time >= 0)) return new Date(time);
	datePart
	var displayParts = displayStr.split(" ");
	return displayParts[displayParts.length-1];
}

function ambrosia_parse_date(displayStr)
{
	var displayParts = displayStr.split(" ");
	var rv = this.gen_now();
	//var rv = new Date();
	if (displayParts.length == 3)
	{
		var month = -1;
		for (var i = 0; i <= 11; i++)
		{
			if (this.month_names[i].toLowerCase() == displayParts[0].toLowerCase())
			{
				month = i;
				break;
			}
		}
		if (month == -1) return rv;

		var day = parseInt(displayParts[1], 10);
		if (isNaN(day)) return rv;
		
		var year = parseInt(displayParts[2], 10);
		if (isNaN(year)) return rv;

		//rv.setYear(year);
		//rv.setMonth(month);
		//rv.setDate(day);
		rv.setFullYear(year,month,day);
	}

	return rv;
}

function ambrosia_parse_time(displayStr, timeStamp)
{
	var displayParts = displayStr.split(":");
	if ((displayParts.length == 2) || (displayParts.length == 3))
	{
		var hour = parseInt(displayParts[0], 10);
		if (isNaN(hour)) return timeStamp;
		if ((hour < 0) || (hour > 12)) return timeStamp;
		//if (hour == 12) hour = 0;
		
		var minute = parseInt(displayParts[1], 10);
		if (isNaN(minute)) return timeStamp;
		if ((minute < 0) || (minute > 59)) return timeStamp;
		
		timeStamp.setHours(hour);
		timeStamp.setMinutes(minute);
		timeStamp.setSeconds(0);
		timeStamp.setMilliseconds(0);		
	}
	
	return timeStamp;
}


function cal_error (str_message) {
	//alert (str_message);
	return null;
}

function showCal(string2,hrs,mins,ampm)
{
  var string2val = document.getElementById(string2).value;
  var dt;
  if((null == string2val) || (string2val.length == 0))
  {
    dt = new Date();
  }	
  else dt = new Date(string2val);
  
  if (!isNaN(dt))
  { 
    var cal2 = new calendar2(document.getElementById(string2),hrs,mins,ampm);
    cal2.popup();
    document.getElementById(string2).select();
  }
  else
  {
    alert("Date is in an invalid format. Enter date in this format MM/DD/YYYY HH:MM AM/PM");
    document.getElementById(string2).select();
  }
}

function showHideTable(string2, show)
{
 var string2ele = document.getElementById(string2);
  // show the box
  if(string2ele != undefined && string2ele != null && string2ele.style.display == "none" && show.match("true")) 
	{
	string2ele.setAttribute("aria-hidden", "false");
	string2ele.tabIndex = -1;
	string2ele.style.display = "block";
	string2ele.style.visibility = "visible";
	string2ele.focus();	
	}
 else if(string2ele != undefined && string2ele != null && string2ele.style.display == "block" && !show.match("true"))
	{
	string2ele.setAttribute("aria-hidden", "true");
	string2ele.tabIndex = 0;
	string2ele.style.display = "none";
	string2ele.style.visibility = "hidden";	
	}
}

function showInvalid(string2, invIcon)
{
	var string2ele = document.getElementById(string2);
	dt = new Date(document.getElementById(string2).value);
	if (!isNaN(dt))
	{
		if (dt.getFullYear() > 9999)
		{
	      var invIconEle = document.getElementById(invIcon);
	      invIconEle.style.visibility = "visible";
		}
	}	
}

