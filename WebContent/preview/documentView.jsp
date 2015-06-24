<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String swfFilePath=(String)request.getAttribute("swfFilePath");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/flexpaper.js"></script>
<script type="text/javascript" src="../js/flexpaper_handlers.js"></script>
<style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
			#flashContent { display:none; }
        </style> 

<title>文档在线预览系统</title>
</head>
<body> 
        <div style="position:absolute;left:50px;top:10px;">
	       <div id="documentViewer" class="flexpaper_viewer" style="width:900px;height:700px"></div>
 
	        <script type="text/javascript"> 

	        var startDocument = "Paper";

	        $('#documentViewer').FlexPaperViewer(
	                { config : {

	                    SWFFile : '<%=request.getContextPath()+"/"+swfFilePath%>',

	                    Scale : 1,
	                    ZoomTransition : 'easeOut',
	                    ZoomTime : 0.5,
	                    ZoomInterval : 0.2,
	                    FitPageOnLoad : true,
	                    FitWidthOnLoad : true,
	                    FullScreenAsMaxWindow : false,
	                    ProgressiveLoading : false,
	                    MinZoomSize : 0.2,
	                    MaxZoomSize : 5,
	                    SearchMatchAll : false,
	                    InitViewMode : 'Portrait',
	                    RenderingOrder : 'flash',
	                    StartAtPage : '',

	                    ViewModeToolsVisible : true,
	                    ZoomToolsVisible : true,
	                    NavToolsVisible : true,
	                    CursorToolsVisible : true,
	                    SearchToolsVisible : true,
	                    WMode : 'window',
	                    localeChain: 'en_US'
	                }}
	        );
	   
	        </script>            
        </div>
</body>
</html>