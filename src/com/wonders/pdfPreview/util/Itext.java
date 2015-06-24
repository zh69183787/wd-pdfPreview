/**
 * 
 */
package com.wonders.pdfPreview.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

/** 
 * @ClassName: Itext 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月11日 下午4:15:09 
 *  
 */
public class Itext {
	public static void main(String[] args) throws IOException, DocumentException {
         System.out.println(Itext.class.getClassLoader().getResource("red.png").getPath());
        //创建一个pdf读入流  
        PdfReader reader = new PdfReader("D://edoc2 LiteV2 SDK开发手册.pdf");
        //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.  
        PdfStamper stamper = new PdfStamper(reader,  
          new FileOutputStream("D://aaaa.pdf"));

        BaseFont bf = BaseFont.createFont("STSong-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
        //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
        Font font = new Font(bf,32);
        font.setStyle(Font.BOLD);
        font.setColor(BaseColor.BLACK);
        font.getBaseFont();

        //画图
        String time = "2014-10-10";
        String status = "失效";
        String code="2222";
        Image img = null;
        BaseColor baseColor = null;
        if("部分有效".equals(status)){
            img = Image.getInstance(Itext.class.getClassLoader().getResource("blue.png").getPath());
            baseColor = BaseColor.BLUE;
        }else if("失效".equals(status)){
            img = Image.getInstance(Itext.class.getClassLoader().getResource("red.png").getPath());
            baseColor = BaseColor.RED;
        }
        if(img != null) {
            Document document = new Document(reader.getPageSize(1));
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            PdfContentByte over1 = stamper.getOverContent(1);
            width = width - img.getWidth();
            height = height - img.getHeight();
            System.out.println("width:" + width);
            System.out.println("height:" + height);
            img.setAbsolutePosition(width, height);
            img.setAlignment(Image.ALIGN_RIGHT);
            over1.addImage(img);
            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 30);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 15 + ("部分有效".equals(status)? 0 : 30), height + img.getHeight() - 40);
            //要输出的text
            over1.showText(status);
            over1.endText();

            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 12);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 50, height + img.getHeight() - 65);
            //要输出的text
            over1.showText(time);
            over1.endText();

            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 10);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 35, height + img.getHeight() - 55);
            //要输出的text
            over1.showText("相关备注参见附件");
            over1.endText();

        }

        //页数是从1开始的
        for (int i=1; i<=reader.getNumberOfPages(); i++){

            //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
            PdfContentByte over = stamper.getOverContent(i);
            //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.
            PdfDictionary p = reader.getPageN(i);
            //拿到mediaBox 里面放着该页pdf的大小信息.
            PdfObject po =  p.get(new PdfName("MediaBox"));
            System.out.println(po.isArray());
            //po是一个数组对象.里面包含了该页pdf的坐标轴范围.
            PdfArray pa = (PdfArray) po;
            System.out.println(pa.size());
            //看看y轴的最大值.
            System.out.println(pa.getAsNumber(pa.size()-1));
            //开始写入文本
            over.beginText();
            //设置字体和大小
            over.setFontAndSize(font.getBaseFont(), 18);
            over.setColorFill(BaseColor.BLACK);
            //设置字体的输出位置
            PdfNumber y = pa.getAsNumber(pa.size()-1);
            over.setTextMatrix(55,y.floatValue()-55);
            //要输出的text
            over.showText(code);
            over.endText();
            break;
        }

        stamper.close();

    }  
	
	public static void getXX(String src,String code,String status,String time,java.io.OutputStream out) throws IOException, Exception{
		  
        //创建一个pdf读入流  
        PdfReader reader = new PdfReader(src);   
        //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.  
        PdfStamper stamper = new PdfStamper(reader,  
          out);   
          
        //这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.  
        BaseFont bf = BaseFont.createFont("STSong-Light",   
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font  
        //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.  
        Font font = new Font(bf,32);  
        font.setStyle(Font.BOLD);  
        font.setColor(BaseColor.BLACK);
        font.getBaseFont();

        //画图

        Image img = null;
        BaseColor baseColor = null;
        if("部分有效".equals(status)){
            img = Image.getInstance(Itext.class.getClassLoader().getResource("blue.png").getPath());
            baseColor = BaseColor.BLUE;
        }else if("失效".equals(status) || "废止".equals(status)){
            img = Image.getInstance(Itext.class.getClassLoader().getResource("red.png").getPath());
            baseColor = BaseColor.RED;
        }
        if(img != null) {
            Document document = new Document(reader.getPageSize(1));
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            PdfContentByte over1 = stamper.getOverContent(1);
            width = width - img.getWidth();
            height = height - img.getHeight();
            System.out.println("width:" + width);
            System.out.println("height:" + height);
            img.setAbsolutePosition(width, height);
            img.setAlignment(Image.ALIGN_RIGHT);
            over1.addImage(img);
            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 30);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 15 + ("部分有效".equals(status)? 0 : 30), height + img.getHeight() - 40);
            //要输出的text
            over1.showText(status);
            over1.endText();

            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 12);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 50, height + img.getHeight() - 65);
            //要输出的text
            over1.showText(time);
            over1.endText();

            //开始写入文本
            over1.beginText();
            //设置字体和大小
            over1.setFontAndSize(font.getBaseFont(), 10);
            over1.setColorFill(baseColor);
            over1.setTextMatrix(width + 35, height + img.getHeight() - 55);
            //要输出的text
            over1.showText("相关备注参见附件");
            over1.endText();

        }

        //页数是从1开始的  
        for (int i=1; i<=reader.getNumberOfPages(); i++){  
  
           //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.  
            PdfContentByte over = stamper.getOverContent(i);  
            //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.  
            PdfDictionary p = reader.getPageN(i);  
            //拿到mediaBox 里面放着该页pdf的大小信息.  
           PdfObject po =  p.get(new PdfName("MediaBox"));  
           System.out.println(po.isArray());  
           //po是一个数组对象.里面包含了该页pdf的坐标轴范围.  
           PdfArray pa = (PdfArray) po;  
           System.out.println(pa.size());  
           //看看y轴的最大值.  
           System.out.println(pa.getAsNumber(pa.size()-1));  
            //开始写入文本  
            over.beginText();  
            //设置字体和大小  
            over.setFontAndSize(font.getBaseFont(), 18);   
            over.setColorFill(BaseColor.BLACK);
            //设置字体的输出位置  
            PdfNumber y = pa.getAsNumber(pa.size()-1);
            over.setTextMatrix(55,y.floatValue()-55);   
            //要输出的text  
            over.showText(code);    
            over.endText();  
            break;
        }  
  
        stamper.close();  
  
	}
}
