// Function that opens bookmark window
function OpenBookmarkWindow(section_id, section_title, from_page, from_module_id, from_module_seq_no, windowName)
{
	 var _info = navigator.userAgent;
  var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
	var windowDefaults = "status=no, menubar=no, location=no, scrollbars=yes, resizeable=yes, width=700, height=600, left=500, top=20";
	var newWindow;
	if(!_ie) newWindow = window.open('add_bookmark.jsf?sectionId='+section_id+'&sectionTitle='+section_title+'&fromPage='+from_page+'&fromModuleId='+from_module_id+'&fromModuleSeqNo='+from_module_seq_no,windowName,windowDefaults);
	else newWindow = window.open('add_bookmark.jsf?sectionId='+section_id+'&sectionTitle='+section_title+'&fromPage='+from_page+'&fromModuleId='+from_module_id+'&fromModuleSeqNo='+from_module_seq_no,null,windowDefaults);
if (window.focus) { newWindow.focus(); } ; // force the window to the front if the browser supports it
return newWindow;

}

//Function that opens print window
function OpenPrintWindow(print_id, windowName)
{
	
  var _info = navigator.userAgent;
  var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
	var windowDefaults = "status=no, menubar=no, location=no, scrollbars=yes, resizeable=yes, width=800, height=700, left=20, top=20";
	var newWindow;
	if(!_ie) newWindow = window.open('print_module.jsf?printModuleId='+print_id,windowName,windowDefaults);
	else newWindow = window.open('print_module.jsf?printModuleId='+print_id,null,windowDefaults);
if (window.focus) { newWindow.focus(); } ; // force the window to the front if the browser supports it
return newWindow;

}


//Function that opens print window
function OpenViewModulePrintWindow(print_url, windowName)
{
	
  var _info = navigator.userAgent;
  var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
	var windowDefaults = "status=no, menubar=no, location=no, scrollbars=yes, resizeable=yes, width=800, height=700, left=20, top=20";
	var newWindow;
	if(!_ie) newWindow = window.open(print_url,windowName,windowDefaults);
	else newWindow = window.open(print_url,null,windowDefaults);
if (window.focus) { newWindow.focus(); } ; // force the window to the front if the browser supports it
return newWindow;

}
