/**
 * 
 */
package com.wonders.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.service.FjshService;
import com.wonders.attach.util.ActionWriter;
import com.wonders.page.util.PageResultSet;
import com.wonders.page.util.PageVo;
import com.wonders.pdfPreview.util.StringUtil;

/** 
 * @ClassName: FjshAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月18日 下午9:25:52 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/attach")
@Component("fjshAction")
@Scope("prototype")
public class FjshAction extends ActionSupport implements ModelDriven<PageVo>{
	private PageVo vo = new PageVo();
	private PageResultSet<AttachFile> pageResultSet;
	public PageVo getVo() {
		return vo;
	}
	public void setVo(PageVo vo) {
		this.vo = vo;
	}
	private FjshService fjshService;
	private List<File> fileupload;//这里的"fileName"一定要与表单中的文件域名相同  
    private List<String> fileuploadContentType;//格式同上"fileName"+ContentType  
    private List<String> fileuploadFileName;//格式同上"fileName"+FileName  
    public List<File> getFileupload() {
		return fileupload;
	}
	public void setFileupload(List<File> fileupload) {
		this.fileupload = fileupload;
	}
	public List<String> getFileuploadContentType() {
		return fileuploadContentType;
	}
	public void setFileuploadContentType(List<String> fileuploadContentType) {
		this.fileuploadContentType = fileuploadContentType;
	}
	public List<String> getFileuploadFileName() {
		return fileuploadFileName;
	}
	public void setFileuploadFileName(List<String> fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	private ActionWriter aw = new ActionWriter(response);
	
	public FjshService getFjshService() {
		return fjshService;
	}
	@Autowired(required=false)
	public void setFjshService(@Qualifier("fjshService")FjshService fjshService) {
		this.fjshService = fjshService;
	}
	
	@Action("list")
	public String list(){
		this.pageResultSet = this.fjshService.list(vo);
		aw.writeJson(pageResultSet);
		return null;
	}
	
	@Action("add")
	public String add(){
		Map<String,String> result = new HashMap<String,String>();
		String fileName = "";
		String fileExtName = "";
		String fileAllName = "";
		List<File> files=getFileupload();  
		for(int i=0;i<files.size();i++){  
			fileAllName = StringUtil.getNotNullValueString(getFileuploadFileName().get(i));
			result.put(fileAllName, "上传失败！");
			if (fileAllName.lastIndexOf(".") >= 0){   
                fileName = fileAllName.substring(0,fileAllName.indexOf("."));
				fileExtName = fileAllName.substring(fileAllName.indexOf(".")+1,fileAllName.length());
				long b = this.fjshService.uploadFile(files.get(i), fileName, fileExtName);
				if(b > 0){
					result.put(fileAllName, "上传成功");
				}
			} 
		}
		
		aw.writeJson(result);
		return null;
	}
	
	@Action(value="downloadFile")
	public String downloadFile() throws UnsupportedEncodingException{
		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		
		AttachFile bo = this.fjshService.load(Long.parseLong(fileId==""?"0":fileId));
		String path = bo.getPath();					// 文件所在磁盘路径
		String fileName = bo.getFileName()+"."+bo.getFileExtName();		// 真实文件名
		String saveFileName = bo.getSaveFileName();	// 磁盘上的文件名
		String version = bo.getVersion();
		fileName = URLEncoder.encode(fileName, "utf-8");
		fileName = fileName.replace("+", "%20"); // encode后替换  解决空格问题
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream");
		try {
			InputStream is = new FileInputStream(path + saveFileName);
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = is.read(buffer, 0, 1024)) > 0) {
				os.write(buffer, 0, n);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
	* @Title: getModel 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public PageVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
	
}
