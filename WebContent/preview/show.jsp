<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.io.*" %>
    <%@ page import="com.wonders.attach.util.*" %>
<%!
public byte[] file_get_contents(String file) {
	byte[] con = {0};
	if(file == null || file == "")
		return con;
	try {
		File f = new File(file);
		if(!f.isFile() || !f.canRead())
			return con;
		FileInputStream fstream = new FileInputStream(file);
		con = new byte[(int) f.length()];
		fstream.read(con);
		fstream.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return con;
}
%>


<%
String path = FileUpProperties.getValueByKey("file_path");
String swf = (String)request.getParameter("swf");
if(swf != null && swf.length()>0){
	swf = swf.replace("swf/","");
}
//System.out.println(swf);
 response.reset();//如果在weblogic底下同样要加上此句
BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
response.setContentType("application/x-shockwave-flash");
response.setHeader("Accept-Ranges", "bytes");
byte[] content = file_get_contents(path+swf);
response.setContentLength(content.length);
outs.write(content);
outs.flush();
	outs.close();
//	out.clear();
	//out = pageContext.pushBody();
%>