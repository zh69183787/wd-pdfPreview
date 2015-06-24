<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String swfFilePath=(String)request.getAttribute("swfFilePath");
	String path = request.getContextPath();
	response.setHeader("Pragma","No-cache");  
response.setHeader("Cache-Control","no-cache");  
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/formalize.css" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript" src="../js/flexpaper.js"></script>
<script type="text/javascript" src="../js/flexpaper_handlers.js"></script>
<style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
			#flashContent { display:none; }
        </style> 

<title>2014年度信息化项目实施推进计划</title>
</head>
<body> 
<div id="domMessage" style="display:none;"> 
    <h1>请稍后</h1> 
</div> 	
<br>
<div align="center">
		<div>
        	<input type="hidden" value="/swf/视频会议_2014-06-02.swf" id="swf">&nbsp;
        	<input style="display:none;" type="button" value="go" id="go">&nbsp;
        </div>
        <br>
        
        <div>
	       <div id="documentViewer" class="flexpaper_viewer" style="width:90%;height:900px;"></div>
 
	        <script type="text/javascript"> 
			$(function(){
			showSwf($("#swf").val());
				
			});
	       
		
			
			function showSwf(swf){
				//console.log('show.jsp?swf='+encodeURI(swf)+'&temp='+Math.random());
		        $('#documentViewer').FlexPaperViewer(
		                { config : {
	
		                    SWFFile : encodeURIComponent('show.jsp?swf='+swf+'&temp='+Math.random()),
	
		                    Scale : 1,
		                    ZoomTransition : 'easeOut',
		                    ZoomTime : 0.5,
		                    ZoomInterval : 0.2,
		                    FitPageOnLoad : false,
		                    FitWidthOnLoad : true,
		                    FullScreenAsMaxWindow : false,
		                    ProgressiveLoading : true,
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
			}
	   
	        </script>            
        </div>
        
</div>
</body>
</html>