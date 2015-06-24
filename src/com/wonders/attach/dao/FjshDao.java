package com.wonders.attach.dao;


import java.io.File;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.page.util.PageResultSet;
import com.wonders.page.util.PageVo;


public interface FjshDao{

	HibernateTemplate getHibernateTemplate();
	public AttachFile load(long id);
	public long save(AttachFile bo);
	public void delete(long id);
	public long existFile(String fileName,String fileExtName);
	public String getVersion(String fileName,String fileExtName);
	public void renameFile(String fileName,String fileExtName,String version);
	public void saveFile(File sourceFile,String fileName,String fileExtName);
	
	public PageResultSet<AttachFile> list(PageVo vo);
}
