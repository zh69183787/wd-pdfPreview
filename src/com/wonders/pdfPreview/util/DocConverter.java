package com.wonders.pdfPreview.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.wonders.attach.util.FileUpProperties;

/**
 * doc docx格式转换
 */
public class DocConverter {
	private static final int environment = 1;// 环境 1：windows 2:linux
	private String fileString;// (只涉及pdf2swf路径问题)
	private String outputPath = "";// 输入路径 ，如果不设置就输出在默认的位置
	private String fileName;
	private File pdfFile;
	private File swfFile;
	private File docFile;
	//private File odtFile;
	
	public DocConverter(String fileString,String ext) {
		ini(fileString,ext);
	}

	/**
	 * 重新设置file
	 * 
	 * @param fileString
	 */
//	public void setFile(String fileString) {
//		ini(fileString);
//	}

	/**
	 * 初始化
	 * 
	 * @param fileString
	 */
	private void ini(String fileString,String ext) {
		 //	            this.fileString = fileString;  
		//	            fileName = fileString.substring(0, fileString.lastIndexOf("/"));  
		//	            docFile = new File(fileString);  
		//	          
		//	            fileName = fileName + "/" + s;  
		//	            // 用于处理TXT文档转化为PDF格式乱码,获取上传文件的名称（不需要后面的格式）  
		//	            String txtName = fileString.substring(fileString.lastIndexOf("."));  
		//	            // 判断上传的文件是否是TXT文件  
		//	            if (txtName.equalsIgnoreCase(".txt")) {  
		//	                // 定义相应的ODT格式文件名称  
		//	                odtFile = new File(fileName + ".odt");  
		//	                // 将上传的文档重新copy一份，并且修改为ODT格式，然后有ODT格式转化为PDF格式  
		//	                FileUtils.copyFile(docFile, odtFile);  
		//	                pdfFile = new File(fileName + ".pdf"); // 用于处理PDF文档  
		//	            } else if (txtName.equals(".pdf") || txtName.equals(".PDF")) {  
		//	                pdfFile = new File(fileName + ".pdf"); 
		//	                pdfFile = new File(fileName + ".pdf");  
		//	            	 if(!pdfFile.exists()){
		//	            		 FileUtils.copyFile(docFile, pdfFile);  
		//	            	 }
		//	                //FileUtils.copyFile(docFile, pdfFile);  
		//	            } else {  
		//	            	 pdfFile = new File(fileName + ".pdf");  
		//	            	 if(!pdfFile.exists()){
		//	            		 FileUtils.copyFile(docFile, pdfFile);  
		//	            	 }
		//	            }  
			 	this.fileString = fileString;  
		        fileName = fileString.substring(0, fileString.lastIndexOf("."));  
		        String s = fileString.substring(fileString.lastIndexOf("/") + 1,fileString.lastIndexOf("."));  
		        swfFile = new File(FileUpProperties.getValueByKey("file_path")+s+".swf");  
		        
		        File temp = new File(fileString);
				 if (true) {
					 docFile = new File(FileUpProperties.getValueByKey("file_path")+s+"."+ext);  
					 try {
						FileUtils.copyFile(temp, docFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			        pdfFile = new File(FileUpProperties.getValueByKey("file_path")+s + ".pdf");  	        	
				}
		        
		       // swfFile = new File(FileUpProperties.getValueByKey("file_path")+s+".swf");  
		       
	}
	
	/**
	 * 转为PDF
	 * 
	 * @param file
	 */
	private void doc2pdf() throws Exception {
		//if (!swfFile.exists()) {
		if (true) {
			if (docFile.exists()) {
				if (!pdfFile.exists()) {
					OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
					try {
						connection.connect();
						DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
						converter.convert(docFile, pdfFile);
						// close the connection
						connection.disconnect();
						System.out.println("****pdf转换成功，PDF输出：" + pdfFile.getPath()+ "****");
						if (docFile.exists()) {
							docFile.delete();
						}
					} catch (java.net.ConnectException e) {
						e.printStackTrace();
						System.out.println("****swf转换器异常，openoffice服务未启动！****");
						throw e;
					} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
						e.printStackTrace();
						System.out.println("****swf转换器异常，读取转换文件失败****");
						throw e;
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				} else {
					System.out.println("****已经转换为pdf，不需要再进行转化****");
				}
			} else {
				System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
			}
		} else {
			System.out.println("****swf已经存在不需要转换****");
		}
	}
	
	/**
	 * 转换成 swf
	 */
	@SuppressWarnings("unused")
	private void pdf2swf() throws Exception {
		Runtime r = Runtime.getRuntime();
		//if (!swfFile.exists()) {
		if (true) {
			if (pdfFile.exists()) {
				if (environment == 1) {// windows环境处理
					try {
						//Process p = r.exec("D:/SWFTools/pdf2swf.exe "+ pdfFile.getPath() + " -o "+ swfFile.getPath() + " -T 9");
						
						Process p = r.exec("C:/SWFTools/pdf2swf.exe "+ pdfFile.getPath() + " -o "+ swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.out.print(loadStream(p.getInputStream()));
						System.err.println("****swf转换成功，文件输出："
								+ swfFile.getPath() + "****");
						if (pdfFile.exists()) {
							pdfFile.delete();
						}

					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					}
				} else if (environment == 2) {// linux环境处理
					try {
						Process p = r.exec("pdf2swf " + pdfFile.getPath()
								+ " -o " + swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.err.println("****swf转换成功，文件输出："
								+ swfFile.getPath() + "****");
						if (pdfFile.exists()) {
							pdfFile.delete();
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			} else {
				System.out.println("****pdf不存在,无法转换****");
			}
		} else {
			System.out.println("****swf已经存在不需要转换****");
		}
	}

	static String loadStream(InputStream in) throws IOException {

		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();

		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}

		return buffer.toString();
	}
	/**
	 * 转换主方法
	 */
	@SuppressWarnings("unused")
	public boolean conver() {

		//if (swfFile.exists()) {
		if (false) {
			System.out.println("****swf转换器开始工作，该文件已经转换为swf****");
			return true;
		}

		if (environment == 1) {
			System.out.println("****swf转换器开始工作，当前设置运行环境windows****");
		} else {
			System.out.println("****swf转换器开始工作，当前设置运行环境linux****");
		}
		try {
			doc2pdf();
			pdf2swf();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (swfFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回文件路径
	 * 
	 * @param s
	 */
	public String getswfPath() {
		if (swfFile.exists()) {
			String tempString = swfFile.getPath();
			tempString = tempString.replaceAll("\\\\", "/");
			return tempString;
		} else {
			return "";
		}

	}
	/**
	 * 设置输出路径
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
		if (!outputPath.equals("")) {
			String realName = fileName.substring(fileName.lastIndexOf("/"),
					fileName.lastIndexOf("."));
			if (outputPath.charAt(outputPath.length()) == '/') {
				swfFile = new File(outputPath + realName + ".swf");
			} else {
				swfFile = new File(outputPath + realName + ".swf");
			}
		}
	}

}
