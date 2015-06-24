package com.wonders.attach.service;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.bo.DeptCode;
import com.wonders.page.util.PageResultSet;
import com.wonders.page.util.PageVo;

import java.io.File;
import java.util.Map;

public interface FjshService {
	
	public DeptCode loadCodeById(String id);
	public AttachFile loadFileById(String id);
	public AttachFile load(long id);
	public long uploadFile(File sourceFile,String fileName,String fileExtName);
	
	public PageResultSet<AttachFile> list(PageVo vo);

    public Map<String,String> getValidStatus(String fileId);
}
