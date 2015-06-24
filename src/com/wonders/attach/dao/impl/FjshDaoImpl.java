package com.wonders.attach.dao.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.attach.dao.FjshDao;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.util.AttachUtil;
import com.wonders.page.util.PageInfo;
import com.wonders.page.util.PageResultSet;
import com.wonders.page.util.PageVo;
import com.wonders.pdfPreview.util.StringUtil;


@Repository("fjshDao")
public class FjshDaoImpl implements FjshDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public long save(AttachFile bo){
		this.getHibernateTemplate().save(bo);
		return bo.getId();
	}
	
	public AttachFile load(long id){
		return this.getHibernateTemplate().get(AttachFile.class, id);
	}
	
	public void delete(long id){
		AttachFile bo = this.load(id);
		bo.setRemoved(1);
		this.getHibernateTemplate().update(bo);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public long existFile(String fileName,String fileExtName){
		long id = 0;
		String hql = "from AttachFile attach where " +
				"attach.appName=? and attach.fileName = ? "
				+ " and attach.fileExtName = ?"
				+ " and attach.status=? and "
				+ "attach.groupId=? and attach.removed=0 "
				+ " order by attach.uploadDate desc";
		Object[] params = new Object[]{AttachUtil.APP_NAME,fileName,fileExtName,AttachUtil.STATUS_UPLOAD,AttachUtil.GROUP_ID};
		@SuppressWarnings("unchecked")
		List<AttachFile> list = this.getHibernateTemplate().find(hql,params);
		if(list != null && list.size() > 0){
			id = list.get(0).getId();
		}
		return id;
		
	}
	
	public String getVersion(String fileName,String fileExtName){
		int count = 0;
		try{
			
				String sql = "select max(to_number(t.version)) count from t_attach t where t.appname=? and t.filename=? and t.fileextname=? and t.groupid=? and t.status != ?";
				
				SQLQuery query = 
						this.getHibernateTemplate().getSessionFactory().getCurrentSession().
						createSQLQuery(sql);
				query.setParameter(0, AttachUtil.APP_NAME);
				query.setParameter(1, fileName);
				query.setParameter(2, fileExtName);
				query.setParameter(3, AttachUtil.GROUP_ID);
				query.setParameter(4, AttachUtil.STATUS_DELETE);
				count = (Integer) query.addScalar("count", Hibernate.INTEGER).uniqueResult();
				count = count + 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return count+"";
	}
	
	public void renameFile(String fileName,String fileExtName,String version){
		File filePath = new File(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID);
		if(filePath.isDirectory()&&filePath.exists()){
			File file = new File(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID+"//"+fileName + "."+fileExtName);
			if(file.exists()){
				String newFileName = fileName;
				newFileName = newFileName+"_v"+version;
				File destFile = new File(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID+"\\"+newFileName+"."+fileExtName);
				file.renameTo(destFile);
			}
		}
	}
	
	public void saveFile(File sourceFile,String fileName,String fileExtName){
		try {
			File dfp = new File(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID);
			if(!dfp.exists()){
				dfp.mkdirs();
				dfp = null;
			}
			File destFile = new File(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID+"//"+fileName+"."+fileExtName);
			if(!destFile.exists()){
				destFile.createNewFile();
			}
			DataInputStream in = new DataInputStream(new FileInputStream(sourceFile));
			DataOutputStream out = new DataOutputStream(new FileOutputStream(destFile));
			byte[] c = new byte[(int) sourceFile.length()];
			in.read(c);
			out.write(c);
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public PageResultSet<AttachFile> list(PageVo vo){
		
		PageResultSet<AttachFile> result = new PageResultSet<AttachFile>();
		String hql = "from AttachFile a where a.removed=0 and a.appName = ? order by a.uploadDate desc";
		Query query = this.getHibernateTemplate().getSessionFactory().
		getCurrentSession().createQuery(hql);
		query.setParameter(0, AttachUtil.APP_NAME);
		
		int totalRows = query.list().size();
		PageInfo pageinfo = new PageInfo(totalRows, PageVo.getPageSize(vo.pageSize), PageVo.getPage(vo.page));	
		query.setFirstResult(pageinfo.getBeginIndex());
		query.setMaxResults(pageinfo.getPageSize());
		List<AttachFile> list = (List<AttachFile>)query.list();
		result.setList(list);
		result.setPageInfo(pageinfo);
		return result;
	}
	
	
}
