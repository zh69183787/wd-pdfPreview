/**
 * 
 */
package com.wonders.attach.util;

/**
 * @ClassName: AttachUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月18日 下午8:06:06 
 *  
 */
public class AttachUtil {
	public static final String STATUS_UPLOAD = "upload";
	public static final String STATUS_DELETE = "delete";
	public static final String STATUS_OVERWRITE = "overwrite";
	
	public static final String APP_NAME = FileUpProperties.getValueByKey("appname");
	public static final String SAVE_PATH = FileUpProperties.getValueByKey("savepath");
	public static final String GROUP_ID = FileUpProperties.getValueByKey("groupid");
	public static final String TEMPLATE_NAME = FileUpProperties.getValueByKey("template");
	public static final String EXT_NAME = FileUpProperties.getValueByKey("ext");
	public static final String ERROR_FILE = FileUpProperties.getValueByKey("errorfile");
	public static final String PREFIX = FileUpProperties.getValueByKey("prefix");
	
}
