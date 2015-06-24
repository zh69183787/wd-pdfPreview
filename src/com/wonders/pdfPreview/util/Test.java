/**
 * 
 */
package com.wonders.pdfPreview.util;

/**
 * @ClassName: Test 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月21日 下午4:34:50 
 *  
 */
public class Test {
//	public static void main(String[] args) throws Exception{
//		File a = new File("D://烦烦烦.txt");
//		System.out.println(a.getPath());
//	}

    public static void main(String[] args) {
        System.out.println(new Test().test());
    }
    static int test()
    {
        int x = 1;
        try
        {
            return x;
        }
        finally
        {
            ++x;
            return x;
        }
    }
}
