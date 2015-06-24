/**
 * 
 */
package com.wonders.meeting.service;

import com.wonders.meeting.vo.MeetingVo;

/** 
 * @ClassName: MeetingService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月18日 下午7:46:59 
 *  
 */
public interface MeetingService {
	public MeetingVo getInfo(String day,String offset);
}
