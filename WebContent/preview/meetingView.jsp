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

<title>视屏会议安排情况</title>
</head>
<body> 
<div id="domMessage" style="display:none;"> 
    <h1>请稍后</h1> 
</div> 	
<br>
<div align="center">
	<div style="width:100%;float:left;">
		<div style="padding-left:20px;float:left;height:50px;"><b>日期：
		<span id="startDate">2014-01-13</span>
		 至
		<span id="endDate">2014-01-17</span>
		</b></div>
		<div style="padding-right:50px;float:right;height:50px;"><b>
		<span><a target="_blank" href="http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId=5003842">集团视频会议室管理工作组通讯录下载</a></span>
		</b></div>
		<div style="padding-right:50px;float:right;height:50px;"><b>
		<span><a target="_blank" href="http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId=5003841">集团视频会议申请表下载</a></span>
		</b></div>

	</div>

		<br>
		<div>
        	<input type="hidden" id="day">
        	<input type="button" id="prev" value="上一周">&nbsp;
        	<input type="button" id="now" value="本周">&nbsp;
        	<input type="button" id="next" value="下一周">&nbsp;
        	<input type="hidden" value="/swf/2014-01-17-00022.swf" id="swf">&nbsp;
        	<input style="display:none;" type="button" value="go" id="go">&nbsp;
        </div>
        <br>
        
        <div>
	       <div id="documentViewer" class="flexpaper_viewer" style="width:90%;height:900px;"></div>
 
	        <script type="text/javascript"> 
			$(function(){
				$("#go").click(function(){
					showSwf($("#swf").val());
				});
				$("#prev").click(function(){
					getInfo("-1");
				
				});
				$("#next").click(function(){
					getInfo("1");
					
				});
				$("#now").click(function(){
					getInfo("0");
					
				});
				
				$("#now").click();
				
			});
	       
			function getInfo(offset){
				$.blockUI({ message: $('#domMessage') }); 
				$.post(
						'<%=path%>/meeting/getInfo.action?random='+Math.random(),
						{
							"day": 	$("#day").val(),
							"offset": offset
						},
						function(obj, textStatus, jqXHR){
							if(obj != null){
								$("#day").val(obj.day);
								$("#startDate").html(obj.startDate);
								$("#endDate").html(obj.endDate);
								$("#swf").val(obj.swf);
							}
							showSwf($("#swf").val());
							$.unblockUI();
						},
						"json"
					).error(function() { alert("服务器连接失败，请稍后再试!"); });
			}
			
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