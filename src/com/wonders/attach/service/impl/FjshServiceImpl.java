package com.wonders.attach.service.impl;

import com.wonders.attach.dao.FjshDao;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.bo.DeptCode;
import com.wonders.attach.service.FjshService;
import com.wonders.attach.util.AttachUtil;
import com.wonders.page.util.PageResultSet;
import com.wonders.page.util.PageVo;
import com.wonders.pdfPreview.util.StringUtil;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("fjshService")
@Transactional(value="txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class FjshServiceImpl implements FjshService{

	private FjshDao fjshDao;

    public String getStatusCn(String status){
        String result = "";
        if("2".equals(status)){
            result= "部分有效";
        }else if("3".equals(status)){
            result = "失效";
        }else if("4".equals(status)){
            result = "废止";
        }
        return result;
    }

    /**
     * 得到文件状态
     * @param fileId
     * @return
     */
    public Map<String,String> getValidStatus(String fileId){
        Map<String,String> map = new HashMap<String, String>();
        String sql = "select v.status,v.operate_time time from t_attach a,t_doc_send s ,t_valid_file v \n" +
                "where a.removed=0 and s.removed=0 and v.removed='0'\n" +
                "and a.groupid=s.content_att_main and s.id=v.main_id\n" +
                "and a.id = :fileId";
        Query query = this.fjshDao.getHibernateTemplate().getSessionFactory().getCurrentSession().
                createSQLQuery(sql).addScalar("status",Hibernate.STRING).addScalar("time",Hibernate.STRING);
        query.setParameter("fileId",fileId, Hibernate.STRING);
        try {
            List list = query.list();
            if(list!=null && list.size() > 0){
                Object[] str = (Object[]) list.get(0);
                map.put("status",getStatusCn(StringUtil.getNotNullValueString(str[0])));
                map.put("time",StringUtil.getNotNullValueString(str[1]));
            }
        }catch(Exception e){}
        return map;
    }

	public AttachFile loadFileById(String id){
		AttachFile bo = (AttachFile)this.fjshDao.getHibernateTemplate().get(AttachFile.class,new Long(id));
		return bo;
	}
	
	public DeptCode loadCodeById(String id){
		DeptCode bo = (DeptCode)this.fjshDao.getHibernateTemplate().get(DeptCode.class,id);
		return bo;
	}
	
	public AttachFile load(long id){
		return this.fjshDao.load(id);
	}
	
	public long uploadFile(File sourceFile,String fileName,String fileExtName){
		long flag = this.fjshDao.existFile(fileName, fileExtName);
		if(flag > 0){
			//
			String version = this.fjshDao.getVersion(fileName, fileExtName);
			this.fjshDao.renameFile(fileName, fileExtName, version);
			this.fjshDao.delete(flag);
			this.fjshDao.saveFile(sourceFile, fileName, fileExtName);
		}else{
			//首次上传
			this.fjshDao.saveFile(sourceFile, fileName, fileExtName);
		}
		AttachFile bo = new AttachFile();
		bo.setRemoved(0);
		bo.setAppName(AttachUtil.APP_NAME);
		bo.setFileName(fileName);
		bo.setFileExtName(fileExtName);
		bo.setFileSize(sourceFile.length());
		bo.setGroupId(AttachUtil.GROUP_ID);
		bo.setPath(AttachUtil.SAVE_PATH+AttachUtil.GROUP_ID+"//");
		bo.setUploader("ADMIN");
		bo.setUploadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		bo.setSaveFileName(fileName+"."+fileExtName);
		bo.setUploaderLoginName("ADMIN");
		bo.setMemo("附件");
		bo.setVersion("1");
		bo.setStatus(AttachUtil.STATUS_UPLOAD);
		long result = this.fjshDao.save(bo);
		return result;
	}
	
	
	public PageResultSet<AttachFile> list(PageVo vo){
		return this.fjshDao.list(vo);
	}
	
	public FjshDao getFjshDao() {
		return fjshDao;
	}
	
	@Autowired(required=false)
	public void setFjshDao(@Qualifier("fjshDao")FjshDao fjshDao) {
		this.fjshDao = fjshDao;
	}
	
}
