<%@page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Browser Check</title>
<%
	String serverName = request.getServerName();
	if(serverName.equals("mydev.unisa.ac.za") | serverName.equals("my.unisa.ac.za")) {
%>

	<!-- stylesheet for lms environment  -->
	<link href="http://www.unisa.ac.za/myUnisa/stylesheets/lms_unisa_content.css" rel="stylesheet" type="text/css"/>
<%
	}else {
%>
	<!-- stylesheet for staff environment -->
	<link href="http://www.unisa.ac.za/cmsys/staff/stylesheets/staff.css" rel="stylesheet" type="text/css"/>
<%
			}
%>
  
	</head>
	
	<body>
<%

    String replaced ="";
	boolean isSupported = false;
	boolean big3 = true;
	boolean securityWarning=false;
	boolean isIE =false;
	boolean isTrident =false;
	String supportMessage = "This site was successfully tested with your version of ";
	String noSupportMessage = "This site was successfully tested with your version of ";
	String browserName="";
	String browserVersion="";
	String strippedBrowserName="";
	String agent = request.getHeader("User-Agent");
	String versionTrident = "1";
	int vNumber=0;


	//MSIE check

	isIE = agent.indexOf("MSIE") != -1 || agent.indexOf("Trident") !=-1;

   	if(null != agent &&  isIE) {
       	
   	   try
   	   {
   		
   			
   			
   			if(agent.contains("MSIE"))
   			{
   			browserName = agent.substring(agent.indexOf("MSIE"), agent.indexOf("MSIE") + 8);
     		isSupported = true;
     	
     		
   			}
   			else if(agent.contains("Trident/7.0"))
   			{
   				isSupported = true;
   				browserName = agent.substring(agent.indexOf("Trident"), agent.indexOf("Trident") + 9);
   				isTrident = true; 
   				
   			}
   			else
   			{
   				browserName = "MSIE";
   				
   			}
     	
   		
   		
   	    
   	   }
   	   catch(Exception ex)
   	   {
   		browserName = "MSIE";
     	
   	   }
   	}
     			
   	//FireFox check
	else if(agent != null && agent.indexOf("Firefox") != -1) {
   		if(agent.indexOf("Firefox/1") != -1 | agent.indexOf("Firefox/2") != -1 | agent.indexOf("Firefox/3") != -1) {
   			browserName = agent.substring(agent.indexOf("Firefox"));
   			isSupported = true;
   			if(agent.indexOf("Firefox/1.0.7") != -1) {
   				securityWarning=true;
   			}
   		}else {
   			browserName = agent.substring(agent.indexOf("Firefox"));
   			isSupported = false;
   		}
   	
   	}
   			
   	//Netscape Check
	else if(agent != null && agent.indexOf("Netscape") != -1) {
   		if(agent.indexOf("Netscape7") != -1 | agent.indexOf("Netscape8") != -1 | agent.indexOf("Netscape9") != -1) {
	   		browserName = agent.substring(agent.indexOf("Netscape"));
   			isSupported = true;
   		}else  {
			browserName = agent.substring(agent.indexOf("Netscape", agent.indexOf("Firefox")));
   			isSupported = false;
   		}
   		
   	}
	else if(agent !=null && agent.indexOf("Chrome") != -1)
	{
		browserName = agent.substring(agent.indexOf("Chrome"));
			if(agent.indexOf("Chrome/3") != -1 )
			{
				browserName = agent.substring(agent.indexOf("Chrome"));
	   			isSupported = true;
	   		
	   			
			}
			else
			{
				isSupported = true;
				browserName = agent.substring(agent.indexOf("Chrome", agent.indexOf("Firefox")));
				
			}
			
			
	}
	else if(agent !=null && agent.indexOf("Safari") != -1)
	{
   	

		
	}
   	//All other browsers will display ALL user-agent details.
   	else {
   		browserName = agent;
   		isSupported=false;
   		big3=false;
   	
   		
   	}	
   
   	
   	if(browserName.contains("MSIE"))
   	{
   		isTrident = false;
   	}
   	
   	browserName = browserName.trim();
   	browserName = browserName.replaceAll(" ", "");
   	char[] characters = browserName.toCharArray();
   			
   	for(int i =0; i < characters.length; i++) {
   		if(Character.isDigit(characters[i])) {
   			browserVersion += characters[i];
   		}
   		if(characters[i] == '.') {
   			browserVersion += '.';
   		}
   		if (characters[i] == '/') {
   			browserVersion += '/';
   		}
   		if (characters[i] == '(') {
   			break;
   		}
   		if((!Character.isDigit(characters[i])) & (characters[i] != '.') & (characters[i] != '/')) {
   			strippedBrowserName += characters[i];
   		}
   	}
   	
   	
  
    browserVersion = browserVersion.replace("/", "");	
   	browserVersion = browserVersion.trim();
  

   	
   	if(isIE)
   	{
   		strippedBrowserName = "MSIE";
   	}
   	if((strippedBrowserName.toUpperCase()).contains("CHROME"))
   	{
   		strippedBrowserName = "Chrome";

   	}
   	
 
   
   	
   	try
   	{
   	if("MSIE".equals(strippedBrowserName.toUpperCase()))
   	{
   	   
   		if(isTrident)
   	   	{
   	   	
   			
   			vNumber  = Integer.parseInt(browserVersion);
           if(vNumber == 5)
   	   		{
   	   			
   	   			browserVersion = "9";
   	   		}
   	   		else if(vNumber == 6)
   	   		{
   	   		
   	   			browserVersion = "10";
   	   		}
   	   	    if(vNumber == 7)
   	   		{
   	   			browserVersion = "11";
   	   		
   	   		}
   	   		else
   	   		{
   	   			browserVersion = String.format("%s", vNumber);
   	   			
   	   		}
  
   	   	}
   		
   		int versionNo =  (int) Double.parseDouble(browserVersion);	
   		
   		if(!(versionNo == 9 || versionNo == 10))
   		{
   			isSupported = false;
   		}
   		
   	}
   	else if("FIREFOX".equals(strippedBrowserName.toUpperCase()))
   	{
   		
   		  int versionNo =  (int) Double.parseDouble(browserVersion);	

  		// replaced = browserVersion.replaceAll("[-+^:,/]",""); 
  		
  		if(versionNo < 28 )
  		{
  			
  			isSupported = false;
  		}
  		
   		browserVersion = String.format("%s",versionNo);
   		
   	}
   	else if("CHROME".contains(strippedBrowserName.toUpperCase()))
   	{
			
			int versionNo=0;
			
		   if(browserVersion.length() >= 2)
			{
				browserVersion = browserVersion.substring(0,2);
				versionNo = Integer.parseInt(browserVersion);
				if(versionNo < 34)
				{
					isSupported = false;
					
				}
			}
			
   	}
   	else
   	{
   		
   		isSupported = false;
   		
   	}
   	}
   	catch(Exception ex)
   	{
   	
   		
   		
   		
   	}
   	
  
   
   	
  //
   	
   	
   	
%>	
   		<p>
   			<font class="supported">
   				<%= supportMessage + strippedBrowserName%>
   				<br/>
<%
	if((isSupported) & (big3)) {
				if(securityWarning) {
%>
					You are currently using Firefox 1.0.7. This version is suitable for my.unisa.ac.za, however there are <a href ="http://searchsecurity.techtarget.com/originalContent/0,289142,sid14_gci1180286,00.html?track=sy160">vulnerabilities</a> associated with this version. They have subsequently been patched in version 1.5.0.2. You can download it from <a href=" http://www.mozilla.com/firefox/ ">Mozilla's site</a> or by clicking the Firefox link below.
<%		
				}else {
%>
   				<font color="green">No update is neccessary.</font> 
<%
   				}
 %> 				
   			</font>
   		</p>
<%
   	}else if(!isSupported & big3) {
%>		
	   			<p><font color="red"><%= strippedBrowserName%> Version <%= browserVersion%> 
   				is not supported.</font> </p>
   				<br/>
   				<p>
	     		<font class="note">
	   			Click on one of links to download a 'suggested browser'
	   			</font> </p>
   			
   			 
<%
	}else if(!isSupported & !big3) {
   		if(strippedBrowserName.indexOf("Opera") != -1) {
   			strippedBrowserName = "Opera";
   		}
%>
				<p><font color="red"><%= strippedBrowserName%> is not supported.</font> </p>
				<br/> 
				<br/>
				<p>
	     		<font class="note">
   				Click on one of links to download a 'suggested browser'
   				</font> </p>
   		
<%
	}else {
%>			
   				<p><font color="red">Your browser is not suggested.</font> </p>
   				<br/>
   				<p>
	     		<font class="note">
   				Click on one of links to download a 'suggested browser'
   				</font> </p> <br/>
   		
<%
   	}
%>
		<p>
			<font class="note">
				Suggested browsers: 
			</font>
		</p>
   		<ul>
  			<li><a href="http://www.microsoft.com/en-za/download/internet-explorer-9-details.aspx" target="_blank">Microsoft Internet Explorer</a> version 9</li>
   			<li><a href="http://ftp.mozilla.org/pub/mozilla.org/firefox/releases/31.0/win32/en-US/Firefox%20Setup%2031.0.exe" target="_blank">Firefox</a>  version above 31+ </li>
   			<li><a href="https://www.google.com/intl/en/chrome/browser/" target="_blank">Chrome</a>  version above 34+ </li>
   			<li>Browsing / registration via cellphone not actively supported </li>
   		</ul>	
   		<hr/>
		<p>
			<font class="name">
				Browser Name: <%= strippedBrowserName %><br/>
				Browser Version: <%= browserVersion %><br/>
				Browser Agent: <%= agent %>
			</font>
		</p>
		<hr/>
	</body>
</html>