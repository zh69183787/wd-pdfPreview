<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="java.net.URLEncoder"%>
<%@page import="com.pdfPreview.util.Itext"%>
<%
response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("大声大声道.pdf","UTF-8"));
response.setContentType("application/octet-stream");
try {
	
	java.io.OutputStream os = response.getOutputStream();
	Itext.getXX(os);
	os.flush();
	os.close();
	out.clear();  
	out = pageContext.pushBody(); 
} catch (java.io.IOException e) {
	e.printStackTrace();
}
%>