<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>附件上传</title>
<link rel="stylesheet" href="../css/formalize.css" />
<link rel="stylesheet" href="../css/page.css" />
<link rel="stylesheet" href="../css/default/imgs.css" />
<link rel="stylesheet" href="../css/reset.css" />
<!--[if IE 6.0]>
           <script src="js/iepng.js" type="text/javascript"></script>
           <script type="text/javascript">
                EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
           </script>
       <![endif]-->
        <script src="../js/html5.js"></script>
        <script src="../js/jquery-1.7.1.js"></script>
       <link rel="stylesheet" href="./js/uploadify.css" />
		<script src="./js/jquery.uploadify.3.2.edited.js"></script>
		<script src="../js/jquery.formalize.js"></script>
		<!--<script src="../js/switchDept.js"></script>-->
		
		
		<script type="text/javascript">
		
         $(function(){
        	 $("#fileupload").uploadify({ 
				
             	//开启调试
       		 	'debug' : false,
        		//是否自动上传
        		'auto':false,
        		'multi': true,
                /*注意前面需要书写path的代码*/ 
                //浏览按钮的宽度
        		'width':'100',
        		//浏览按钮的高度
        		'height':'15',
               	'method' : "post",  
                'swf'       : './js/uploadify.swf', 
                'uploader'         : '<%=path %>/attach/add.action', 
                'cancelImg'      : './js/cancel.png', 
		       	'buttonText' : '选择文件',           //按钮名称  
		       	'fileObjName'    :'fileupload',  
      
                'simUploadLimit' : 5, //一次同步上传的文件数目 
                //'sizeLimit'      : 10000000, //设置单个文件大小限制  单位byte
                'fileTypeDesc'       : '支持格式:*.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
                'fileTypeExts'        : '*.xls;',//允许的格式    '*.jpg;*.bmp;*.png;*.gif'
                'fileSizeLimit' : '20000KB' ,    //文件大小   置单个文件大小限制  默认单位byte
        		'queueSizeLimit' : 5,//上传数量
        		'removeTimeout' : '1',//成功后移除 处理框
       			'removeCompleted' : true, 
       			'requeueErrors' : false,
		       'onUploadSuccess' : function(file, data, response){
		    	   alert(data);
		    	   var result = eval('(' + data + ')'); 
		    	   $("#upButton").prop("disabled",true);
		    	   
		    	   //alert(1);
		    	   //window.location.href = "<%=path%>/attach/add.jsp";
		        } ,
		        'onCancel': function(){
		        	if(this.queueData.queueLength == 0){
		        		$("#upButton").prop("disabled",true);
		        	}
		        },
		      //选择上传文件后调用
		  		'onSelect' : function(file) {
		  			$("#upButton").prop("disabled",false);
		  			//$(".uploadify").css("display","inline");
					// $(".uploadify-button ").css("display","inline");
					// $(".uploadify-queue").css("display","inline");
		     },
	         //返回一个错误，选择文件的时候触发
	        'onSelectError':function(file, errorCode, errorMsg){
	        	//alert(errorCode);
	            switch(errorCode) {
	                case -100:
	                    this.queueData.errorMsg = "上传的文件数量已经超出系统限制的"+$('#fileupload').uploadify('settings','queueSizeLimit')+"个文件！";
	                    break;
	                case -110:
	                    //alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#fileupload').uploadify('settings','fileSizeLimit')+"大小！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 大小超出系统限制的"+$('#fileupload').uploadify('settings','fileSizeLimit')+"大小！";
	                    break;
	                case -120:
	                    //alert("文件 ["+file.name+"] 大小异常！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 大小异常！";
	                   break;
	                case -130:
	                    //alert("文件 ["+file.name+"] 类型不正确！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 类型不正确！";
	                    break;
	            }
	           
	        }
				 }); 
			 
		});
		
		function shut(){
		  window.opener=null;
		  window.open("","_self");
		  window.close();
		}
		
		
		
        </script>
       </head>

<body style="font-size:15px;">
	<div class="main">
    	<!--Ctrl-->
		<div class="ctrl clearfix">
        	<div class="fl"></div>
            <div class="posi fl">
            	<ul>
            		<li class="fin" style="font-size:15px;">附件上传</li>
                </ul>
            </div>
   		</div>
        <!--Ctrl End-->
        <!--Filter--><!--Filter End-->
        <!--Table-->
        <div class="mb10 pt45">
        
          <table width="100%"  class="table_1" align="center">
           <thead>
            <th colspan="4" class="t_r">
       	      &nbsp;</th>
                </thead>
            <tbody>
            <tr>
             <td class="t_r lableTd">注意事项：</td>
              <td  colspan="3">
             	1、视频会议excel文件上传时格式：视频会议_2014-01-13.xls;<br>
             	2、文件格式中日期为此周的周一日期（例：2014-01-13 《一》至 2014-01-19《日》，则文件名为：视频会议_2014-01-13）;<br>
             	3、当上传的excel文件与服务器上同名，则此周的日程安排将以新上传的excel文件为准。
              </td>
              </tr>
              
              <tr>
             <td class="t_r lableTd">模板下载：</td>
              <td colspan="3">
             	<a href="<%=path %>/attach/downloadFile.action?fileId=5002942" target="_blank" >视频会议_XXXX-XX-XX</a>
              </td>
              </tr>
             <tr>
             <td class="t_r lableTd">附件上传：</td>
              <td colspan="3">
             <input type="file" id="fileupload" name="fileupload" />
             <input style="display:inline;"  disabled="disabled" type="button" onclick="$('#fileupload').uploadify('upload','*');" id="upButton" value="上传" class="btn" title="附件最大不允许超过20M" />
             </td>
              </tr>
              
             
           
            </tbody>
            <tr class="tfoot">
              <td colspan="4" class="t_r"></td></tr>
           
            
          </table>
           
      </div>
        <!--Table End-->
</div>
</body>
</html>
