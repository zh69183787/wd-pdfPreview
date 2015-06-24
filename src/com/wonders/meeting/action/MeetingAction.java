/**
 * 
 */
package com.wonders.meeting.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wonders.attach.util.ActionWriter;
import com.wonders.meeting.service.MeetingService;
import com.wonders.meeting.vo.MeetingVo;
import com.wonders.pdfPreview.action.AbstractParamAction;
import com.wonders.pdfPreview.util.StringUtil;

/** 
 * @ClassName: MeetingAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月18日 下午7:46:36 
 *  
 */
@ParentPackage("struts-default")
@Namespace(value="/meeting")
@Component("meetingAction")
@Scope("prototype")
public class MeetingAction extends AbstractParamAction{

	private MeetingService service;
	private ActionWriter aw = new ActionWriter(this.response);
	public MeetingService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("meetingService")MeetingService service) {
		this.service = service;
	}


	@Action("getInfo")
	public String getInfo(){
		String day = StringUtil.getNotNullValueString(this.request.getParameter("day"));
		String offset = StringUtil.getNotNullValueString(this.request.getParameter("offset"));
		MeetingVo vo = this.service.getInfo(day, offset);
		aw.writeJson(vo);
		return null;
	}
}
