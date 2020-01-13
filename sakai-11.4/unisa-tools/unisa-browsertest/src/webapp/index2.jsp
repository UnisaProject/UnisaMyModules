<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
	<head>
		<title>Browser Check</title>
		<% 
			String serverName = request.getServerName();
			
			if(serverName.equals("my.unisa.ac.za") | serverName.equals("mydev.unisa.ac.za")) {
			
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

		<script type="text/javascript">
			var supported = [
				/* Array of browsers that are supported.  */
				[ 
					"Microsoft Internet Explorer",	/* Identifying Name (As described in appName) */
					6.0,				/* Version (As described in either appVersion of userAgent) */
					"ftp://ftp.unisa.ac.za/pub/browsers/ie/6.0/ie6setup.exe",
					"http://www.microsoft.com/windows/ie/default.asp", /* Home url for browser */
					""
				],
				[
					"Netscape", 
					7.1, 
					"ftp://ftp.unisa.ac.za/pub/browsers/ns/NS71Setup-Full.exe", 
					"http://www.netscape.com",
					"Netscape 7.1"
				],
				[
					"Firefox",
					1,
					"ftp://ftp.unisa.ac.za/pub/browsers/firefox/Firefox Setup 1.0.7.exe ",
					"http://www.firefox.com",
					"Firefox"
				]
			];

		</script>
	

		<script type="text/javascript">
			var browserIdents = [
				"MSIE", "Mozilla/Netscape", "Netscape6/, Firefox"
			];

			function browserType() {
				return navigator.appName;
			};

			function printBrowserType() {
				document.write("<font class=\"value\">"+browserType()+"</font>");
			};

			function versionString() {
				var version = navigator.appVersion + " " + navigator.userAgent;
				for (var x = browserIdents.length-1; x>=0; x--) {
					if(version.indexOf(browserIdents[x]) > -1) {
						version = version.substring(version.indexOf(browserIdents[x])+browserIdents[x].length, version.length);
					}
				}
				return version;
			};				
			
			function browserMajorVersion() {
				var version = versionString();
				return parseInt(version);
			};

			function browserMinorVersion() {
				var version = versionString();
				version = version.substring(version.indexOf(".")+1, version.length);
				return parseInt(version);
			};

			function browserVersion() {
				return browserMajorVersion() + "." + browserMinorVersion();
			};

			function printBrowserVersion() {
				document.write("<font class=\"value\">"+browserVersion()+"</font>");
			};

			function isValid() {
				var valid = 0;
				var x = 0;
				
				for (x = 0; x < supported.length; x++) {
					if (browserType() == supported[x][0]) { valid = -1; };
					if ((browserType() == supported[x][0])&&(browserVersion() >= supported[x][1])) { valid = 1; };
				}

				return valid;
			};

			function printVersion() {
				document.write(browserType()+" version "+ browserVersion());
			};

			function printSupported() {
				var x = 0;
				for (x = 0; x < supported.length; x++) {
					document.write("<li><a target=\""+supported[x][2]+"\" href=\""+supported[x][2]+"\">");
					document.write(supported[x][0]+"</a>");
					document.write(" version "+supported[x][1]+" and above. ");
					if (supported[x][4] != "") {
						document.write("("+supported[x][4]+")");
					}
				}
			};

			function getMinVersion(b) {
				var i = 0;
				for (i = 0; i < supported.length; i++) {
					if (b == supported[i][0]) {
						return supported[i][1];
					}					
				}
				return "";
			};

			function getUpgradeLink(b) {
				var i = 0;
				for (i = 0; i < supported.length; i++) {
					if (b == supported[i][0]) {
						return supported[i][3];
					}					
				}
				return "";
			};

			function printValid() {
				switch (isValid()) {
					case 1:
						document.write("<font class=\"supported\">");
						document.write("This site was successfully tested with your version of "+browserType());
                        document.write("<br>No update is necessary.<br>");
						document.write("</font>");
						break;
					case 0:
						document.write("<font class=\"unknown\">");
						document.write("This site has not been tested on "+browserType());
						document.write("</font>");
						break;
					case -1:
						document.write("<font class=\"notsupported\">");
						document.write("This site was tested with a later  version of "+browserType()+".<br> ");
						document.write("<a href=\""+getUpgradeLink(browserType())+ "\">");
						document.write("Click here");
						document.write("</a> to upgrade to ");
                        document.write(browserType()+" version ");
						document.write(getMinVersion(browserType())+".");
						document.write("</font>");
						break;
				}
			};

			function printValue(param) {
				var output;
					output = eval(param);
	
				document.write(param + " = ");
				if (output == null) {
					document.write("<font color=\"red\">");
				} else {
					document.write("<font color=\"green\">");
				}
				document.writeln(output + "</font><br>");
			}

			function printFields() {
/*				printValue("navigator.appName");
				printValue("navigator.appCodeName");
				printValue("navigator.appVersion");
				printValue("navigator.userAgent");
				printValue("versionString()"); */
			}
				
		</script>
	</head>
<body>
	<p><script type="text/javascript">printValid();</script></p>
	<p><font class="note">Supported Browsers: </font></p>
		<p/>
		<script type="text/javascript">printSupported();</script>
		
	<hr/>
	<table border="0" cellspacing="0" cellpadding="3">
		<tr>
			<td class="name">Browser Name: </td>
			<td class="value"><script type="text/javascript">printBrowserType(); </script></td>
		</tr>
		<tr>
			<td class="name">Version: </td>
			<td class="value"><script type="text/javascript">printBrowserVersion(); </script></td>
		</tr>
	</table>
	<p><font class="note">Note: If the above fields are empty, your browser probably does not support JavaScript, 
	which means that this site will most likely not function correctly.</font></p>
	<hr/>
		
		<script type="text/javascript">printFields();</script>
		
	
</body>
</html>