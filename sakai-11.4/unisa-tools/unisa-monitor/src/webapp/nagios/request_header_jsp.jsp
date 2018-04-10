 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd" > 

<HTML>
<HEAD>
    <TITLE>Information about request header</TITLE>
</HEAD>
<BODY bgcolor="#ffffcc">
  <font size="+3" color="green"><br>Welcome in www.roseindia.net !</font>
  <font size="+2" color="#7E354D"><br>Information about request header</font>
  <br>
    <TABLE style="background-color: #ECE5B6;" WIDTH="30%" border="1">
	<tr>
	    <th>method used to send request</th>

		<!-- getMethod() returns the name of the HTTP method with which this request was made,
		   for example, GET, POST, or PUT -->
		<td><%= request.getMethod() %></td>
	</tr>
	<tr>
	    <th>URI of the request</th>

		<!-- getRequestURI() returns the part of this request's URL -->
		<td><%= request.getRequestURI() %></td>
	</tr>
	<%
	    /*This method returns an enumeration of all the header names this 
		request contains.*/	    
		 java.util.Enumeration names = request.getHeaderNames();
         while (names.hasMoreElements()) {
			 String hname = (String)names.nextElement();
	%>
	<tr>
	    <th> <%= hname %> </th>

		<!-- This method returns the value of the specified request header as a String. -->
		<td><%= request.getHeader(hname) %></td>
	</tr>
   <%
        }
   %>
     <tr>
	<td> RemoteAddr </td>
	<td> <%= request.getRemoteAddr() %>
   </table>


 </body> 
</html>
