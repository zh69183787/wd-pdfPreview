/**
 * 
 */
package com.wonders.pdfPreview.action;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.bo.DeptCode;
import com.wonders.attach.service.FjshService;
import com.wonders.pdfPreview.util.DocConverter;
import com.wonders.pdfPreview.util.Itext;
import com.wonders.pdfPreview.util.StringUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/** 
 * @ClassName: PdfPreviewAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月23日 上午10:06:20 
 *  
 */

@ParentPackage("struts-default")
@Namespace(value="/preview")
@Component("pdfPreviewAction")
@Scope("prototype")
public class PdfPreviewAction extends AbstractParamAction{

	private static final long serialVersionUID = 7633322988758911721L;
	private FjshService fjshService; 
	public FjshService getFjshService() {
		return fjshService;
	}
	
	@Autowired(required=false)
	public void setFjshService(@Qualifier("fjshService")FjshService fjshService) {
		this.fjshService = fjshService;
	}


    @Action(value="/specialDd")
    public String specialDd() throws UnsupportedEncodingException{
        String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
        AttachFile upFile = this.fjshService.loadFileById(fileId);
        //规范性文件状态
        Map<String,String> map = this.fjshService.getValidStatus(fileId);
        //String path = upFile.getPath().replace("e:", "w").replace("E:", "w");
        if(upFile != null && fileId.length() > 0 && "pdf".equals(StringUtil.getNotNullValueString(upFile.getFileExtName()))){
            String path = upFile.getPath();
            String fileName = upFile.getFileName();
            String saveFileName = upFile.getSaveFileName();
            String filePathName = path+saveFileName;
            fileName += "."+upFile.getFileExtName();
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20"); // encode后替换  解决空格问题
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");
            try {
                OutputStream os = response.getOutputStream();
                Itext.getXX(filePathName, "",map.get("status"),
                        map.get("time") == null ? null : map.get("time").substring(0,10),os);
                os.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


	@Action(value="/download")
	public String download() throws UnsupportedEncodingException{
		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		String codeId = StringUtil.getNotNullValueString(request.getParameter("codeId"));
		AttachFile upFile = this.fjshService.loadFileById(fileId);
		DeptCode deptCode = this.fjshService.loadCodeById(codeId);
        //规范性文件状态
        Map<String,String> map = this.fjshService.getValidStatus(fileId);
		//String path = upFile.getPath().replace("e:", "w").replace("E:", "w");
		if(upFile != null && deptCode!=null 
				&&fileId.length() > 0 && codeId.length() > 0 
				 && "pdf".equals(StringUtil.getNotNullValueString(upFile.getFileExtName()))){
			String path = upFile.getPath();
			String fileName = upFile.getFileName();
			String saveFileName = upFile.getSaveFileName();
			String filePathName = path+saveFileName;	
			fileName += "."+upFile.getFileExtName();
			fileName = URLEncoder.encode(fileName, "utf-8");
			fileName = fileName.replace("+", "%20"); // encode后替换  解决空格问题
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentType("application/octet-stream");
			try {
				OutputStream os = response.getOutputStream();
				Itext.getXX(filePathName, StringUtil.getNotNullValueString(deptCode.getCode()),map.get("status"),
                        map.get("time") == null ? null : map.get("time").substring(0,10),os);
				os.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Action(value="/preview",results={
			@Result(name="preview",location="/preview/documentView.jsp")})
	public String preivew() throws UnsupportedEncodingException{
		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		AttachFile upFile = this.fjshService.loadFileById(fileId);
		if(upFile != null && fileId.length() > 0 ){
			//String path = upFile.getPath().replace("e:", "w:").replace("E:", "w:");
			String path = upFile.getPath();
			//String fileName = upFile.getFileName();
			String saveFileName = upFile.getSaveFileName();
			String filePathName = path+saveFileName;	
			DocConverter dc = new DocConverter(filePathName,upFile.getFileExtName());
			dc.conver();
		//	request.setAttribute("swfFilePath", dc.getswfPath().replace("e:", "w:").replace("E:", "w:").substring(dc.getswfPath().indexOf("swf")));
			
			request.setAttribute("swfFilePath", dc.getswfPath().substring(dc.getswfPath().indexOf("swf")));
			return "preview";
		}
		return null;
	}
	

}
