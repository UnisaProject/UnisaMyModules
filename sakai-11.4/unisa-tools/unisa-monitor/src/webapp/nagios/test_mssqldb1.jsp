<html>
<head><title>Enter to database</title></head>
<body>
<table>
<%@ page import="java.util.*" %>
<%@ page import="javax.sql.*;" %>
<% 

java.sql.Connection con;
java.sql.Statement s;
java.sql.ResultSet rs;
java.sql.PreparedStatement pst;

con=null;
s=null;
pst=null;
rs=null;
// Remember to change the next line with your own environment 
String url= 
"jdbc:jtds:sqlserver://umkn-sqldev.int.unisa.ac.za/libresource";
String id= "syzelle";
String pass = "unisa123";
try{

Class.forName("net.sourceforge.jtds.jdbc.Driver");
con = java.sql.DriverManager.getConnection(url, id, pass);

}catch(ClassNotFoundException cnfex){
cnfex.printStackTrace();

}
String sql = "SELECT TOP 1 TXTID, TEXTDESC, ENABLED FROM LIBTXT ORDER BY NEWID()";
try{
s = con.createStatement();
rs = s.executeQuery(sql);
%>

<%
while( rs.next() ){
%><tr>
<td><%= rs.getString("TXTID") %></td>
<td><%= rs.getString("TEXTDESC") %></td>
<td><%= rs.getString("ENABLED") %></td>
</tr>
<%
}
%>

<%

}
catch(Exception e){e.printStackTrace();}
finally{
if(rs!=null) rs.close();
if(s!=null) s.close();
if(con!=null) con.close();
}

%>

</body>
</html>