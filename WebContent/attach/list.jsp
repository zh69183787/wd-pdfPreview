<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8" />
<title>附件列表</title>
<link rel="stylesheet" href="../css/formalize.css" />
<link rel="stylesheet" href="../css/page.css" />
<link rel="stylesheet" href="../css/default/imgs.css" />
<link rel="stylesheet" href="../css/reset.css" />
<!--[if IE 6.0]>
           <script src="../js/iepng.js" type="text/javascript"></script>
           <script type="text/javascript">
                EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
           </script>
       <![endif]-->
        <script src="../js/html5.js"></script>
        <script src="js/jquery-1.10.2.min.js"></script>
        <script src="js/jquery-migrate-1.2.1.min.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/jquery.form.js"></script>
		<script src="../js/jquery.formalize.js"></script>
		<!--<script src="../js/switchDept.js"></script>-->
			
	<script type="text/javascript">
	jQuery.notNull = function(options){
			if (typeof(options) == "undefined" || options== null){
				return "";
			}else {
				return options;
			}
		}
	

		var listOptions = {
			cache:false,
			dataType:'json',
			type:'post',
			callback:null,
			url:'/pdfPreview/attach/list.action?random='+Math.random(),
		    success:function(data){
			    	var p = data.pageInfo;
					var temp = "";
					if(data!=null && data.list.length >0){
						var l = data.list;
						
						for(var i=0;i<l.length;i++){
							temp += "<tr>";
							temp += "<td class='t_c'>"+(i+1) +"</td>";
							temp += "<td class='t_l'>"+$.notNull(l[i].fileName)+"."+ $.notNull(l[i].fileExtName)+"</td>";
							temp += "<td class='t_c'><a class='editA' href='javascript:void(0);' attachId='"+l[i].id+"'>下载</a></td>";
							temp += "</tr>";
						}
						$("#totalPage_out").val(p.totalPage);
						$("#totalPage").html("当前显示"+(((p.currentPage-1)*p.pageSize)+1)+"-"+(((p.currentPage-1)*p.pageSize)+l.length)+"条记录，"+"总记录："+p.totalRow+"条");	
						var totalOption = "";
						for(var i=1;i<=p.totalPage;i++){
							totalOption += "<option value='"+i+"'>"+i+"</option>";						
							$("#page_out").html(totalOption);
						}
						$("#page_out").val(p.currentPage);
						$("#pageSize_out option:last").val(p.totalRow);
					}else{
						$("#totalPage_out").val(0);
						$("#totalPage").html("当前显示0条记录，总记录：0条");
						$("#page_out").html("<option value='0'>0</option>");
					}
					
					$("#mytable>tbody").eq(0).html(temp);
					//var t = $("#show>tbody").eq(0).find("tr:first").html();
					//console.log($("#show>tbody").eq(0).html());
					//alert(index);
					
					$.unblockUI();
				return false;
		    }
		};
		
		function list(){
			$.blockUI({ message: $('#domMessage') }); 
			 $("#list").ajaxSubmit(listOptions);
		}
		
		
		
	 	$(function(){
	 		$("#addButton").click(function(){
	 			window.open("/pdfPreview/attach/add.jsp")
	 		})
	 		
	 		$(document).on("click",".editA",function(){
	 			window.open("/pdfPreview/attach/downloadFile.action?fileId="+$(this).attr("attachId"),$(this).attr("attachId"));
	 			return false;
	 		})
	 		
	 		$("#prePage").click(function(){
	 			if($("#page_out").val() == 1 || $("#page_out").val() == 0){}
	 			else{
	 				$("#page_out").val(parseInt($("#page_out").val()-1));
	 				$("#page").val($("#page_out").val());
	 	        	list();
	 			}
	 			
	 		})
	 		
	 		$("#nextPage").click(function(){
	 			if(($("#page_out").val() == $("#totalPage_out").val()) || $("#page_out").val() ==0){}
	 			else{
	 				$("#page_out").val(parseInt($("#page_out").val())+1);
	 				$("#page").val($("#page_out").val());
	 	        	list();
	 			}
	 			
	 		})
	 	
	 		
	 	$("#submit").click(function(){
       		 list();
       	 })
       	
        $(document).on("change","#page_out",function(){
        	$("#page").val($(this).val());
        	list();
        })
        
        $(document).on("change","#pageSize_out",function(){
        	if($("#page_out").val()!=0){
	        	$("#pageSize").val($(this).val());
	        	list();
         	}
        })
       
		
           list();
           
		})	
    </script>

</head>

<body>
<div id="domMessage" style="display:none;"> 
    <h1>请稍后</h1> 
</div> 	
<%-- 操作页面--%>
		
	<div class="main">
    	<!--Ctrl-->
		<div class="ctrl clearfix">
			<div class="fl"></div>
            <div class="posi fl">
            	<ul>
                	<li class="fin">附件列表</li>
                </ul>
            </div>
            <div style="display:none;" class="fr lit_nav nwarp">
            	<ul>
                    <li class="selected"><a class="print" href="#">打印</a></li>
                    <li><a class="express" href="#">导出数据</a></li>
                    <li class="selected"><a class="table" href="#">表格模式</a></li>
                    <li><a class="treeOpen" href="#">打开树</a></li>
                    <li><a class="filterClose" href="#">关闭过滤</a></li>
                </ul>
            </div>
   		</div>
        <!--Ctrl End-->
      <div class="pt45">
        <!--Filter-->
      <div class="filter">
        	<div class="query">
        	<div class="p8 filter_search">
        	<form id="list">
        	<input type="hidden" name="pageSize" id="pageSize" value=""/>
        	
        	<input type="hidden" name="page" id="page" value=""/>
        	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
        	    <tr>
        	      <td colspan="6" class="t_c">
                  	<input id="submit" type="button" value="检 索" />&nbsp;&nbsp;
                  	<input type="reset" value="重 置"/></td>
				</tr>
      	    </table>
      	    </form>
      	    </div>
        	</div>     
		      <div class="fn clearfix">
		                  <h5 class="fl"><a href="#" class="colSelect fl">附件列表</a></h5>
		             &nbsp;<input type="button" name="addButton" id="addButton" value="新 增" class="fr">
		            </div>
		      </div>


      
        <!--Filter End-->
        <!--Table-->
        <div class="mb10" id="listDiv">
        	<table width="100%"  class="table_1" id="mytable">
        				<thead>
                              <tr class="tit">
                                <!-- <td><input type="checkbox" id="test_checkbox_1" name="test_checkbox_1" /></td>-->
                                <th class="t_c">序号</th>
                                <th class="t_l">附件名称</th>
                               <th class="t_c">下载</th>
                                </tr>
                              
                              </thead>
                              <tbody>
                             
                              </tbody>
                              <tr class="tfoot">
                                <td colspan="11"><div class="clearfix">
                                <input type="hidden" id="totalPage_out">
                                <span id="totalPage" class="fr">当前显示1-10条记录，总记录：100</span>
                           		<ul class="fl clearfix pager">
		                             <li class="fl" id="nextPage"><a href="javascript:void(0)">下一页</a></li>
		                             <li class="fl" id="prePage"><a href="javascript:void(0)">上一页</a> </li>
		                        </ul>
		                            <span class="fl">
									&nbsp; 跳转至：
	                             	<select id="page_out"></select>
	                             	
	                             	</span>
	                             	<span class="fl">&nbsp;每页显示条数：	</span>
	                             	<select id="pageSize_out">
                                <option value="10">10</option>
                                <option value="15">15</option>
                                <option value="20">20</option>
                                <option value="30">30</option>
                                <option value="-1">显示全部</option>
                                </select>
                            
                        </div>
                                </td>
                              </tr>
                            </table>

      </div>
        <!--Table End-->
</div>
</div>
</body>
</html>