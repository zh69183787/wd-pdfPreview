/**
 * 
 */
package com.wonders.meeting.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.attach.dao.FjshDao;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.util.AttachUtil;
import com.wonders.meeting.service.MeetingService;
import com.wonders.meeting.vo.MeetingVo;
import com.wonders.pdfPreview.util.DocConverter;

/** 
 * @ClassName: MeetingServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月18日 下午7:46:47 
 *  
 */
@Service("meetingService")
@Transactional(value="txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class MeetingServiceImpl implements MeetingService{
	private FjshDao dao;

	public FjshDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(FjshDao dao) {
		this.dao = dao;
	}
	
	public MeetingVo getInfo(String day,String offset){
		MeetingVo vo = new MeetingVo();
		String calDate = this.getCalDate(day, offset);
		String startDate = this.getFirstDayOfWeek(calDate);
		String endDate = this.getLastDayOfWeek(calDate);
		long fileId = this.dao.existFile(AttachUtil.TEMPLATE_NAME+startDate, AttachUtil.EXT_NAME);
		vo.setDay(calDate);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		if(fileId > 0){
			AttachFile attach = this.dao.load(fileId);
			String path = attach.getPath();
			String saveFileName = attach.getSaveFileName();
			String filePathName = path+saveFileName;	
			DocConverter dc = new DocConverter(filePathName,attach.getFileExtName());
			dc.conver();
			String swf = dc.getswfPath().substring(dc.getswfPath().indexOf("swf"));
			vo.setSwf(swf);			
		}else{
			long errorId = this.dao.existFile(AttachUtil.ERROR_FILE, AttachUtil.EXT_NAME);
			AttachFile attach = this.dao.load(errorId);
			String path = attach.getPath();
			String saveFileName = attach.getSaveFileName();
			String filePathName = path+saveFileName;	
			DocConverter dc = new DocConverter(filePathName,AttachUtil.EXT_NAME);
			dc.conver();
			vo.setSwf(dc.getswfPath().substring(dc.getswfPath().indexOf("swf")));
		}
		
		return vo;
	}
	
	public String getCalDate(String day,String offset){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(day.length() == 0){
			day = sdf.format(new Date());
		}
		String now = "";
		int val = 0;
		try{
			val = Integer.parseInt(offset);
		}catch(Exception e){
			val = 0;
		}
		if(val == 0){
			now = sdf.format(new Date());;
		}else{
			Date date = null;
			try {
				date = sdf.parse(day);
			} catch (ParseException e) {
				date = new Date();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
			c.setTime(date);
			c.add(Calendar.DATE, Integer.parseInt(offset) * 7);
			now = sdf.format(c.getTime());
		}
		return now;
	}
	
	public String getFirstDayOfWeek(String t){
		Date date = null;
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(t);
		} catch (ParseException e) {
			date = new Date();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK);
		if(week == 1){
			c.add(Calendar.DATE, -6);
		}
		c.set(Calendar.DAY_OF_WEEK, 2);
		result = sdf.format(c.getTime());
		//c.add(Calendar.DATE,6);
		//System.out.print(sdf.format(c.getTime()));
		
		return result;
	}
	
	public String getLastDayOfWeek(String t){
		Date date = null;
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(t);
		} catch (ParseException e) {
			date = new Date();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK);
		if(week == 1){
			c.add(Calendar.DATE, -6);
		}
		c.set(Calendar.DAY_OF_WEEK, 2);
		c.add(Calendar.DATE,6);
		result = sdf.format(c.getTime());
		
		return result;
	}
	
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(new Date());
		int week = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(week);
		if(week == 1){
			c.add(Calendar.DATE, -6);
		}
		c.set(Calendar.DAY_OF_WEEK, 2);
		//c.add(Calendar.DATE,6);
		System.out.println(sdf.format(c.getTime()));
	}
	
}
