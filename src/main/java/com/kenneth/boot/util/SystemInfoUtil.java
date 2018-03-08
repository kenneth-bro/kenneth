package com.kenneth.boot.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 系统信息
 * @author liq
 * @date 2017年10月9日
 */
public class SystemInfoUtil {
	
	public static long lastModifiedTime = 0l;
	
	public static void findLastModified(String pkgName , boolean isRecursive) {  
        List<Class<?>> classList = new ArrayList<Class<?>>();  
        ClassLoader loader = Thread.currentThread().getContextClassLoader();  
        try {  
            // 按文件的形式去查找  
            String strFile = pkgName.replaceAll("\\.", "/");  
            Enumeration<URL> urls = loader.getResources(strFile);  
            while (urls.hasMoreElements()) {  
                URL url = urls.nextElement();  
                if (url != null) {  
                    String protocol = url.getProtocol();  
                    String pkgPath = url.getPath();  
                    if ("file".equalsIgnoreCase(protocol)) {  
                        // 本地自己可见的代码  
                        findClassName(classList, pkgName, pkgPath, isRecursive);  
                    } else if ("jar".equalsIgnoreCase(protocol)) {  
                        // 引用第三方jar的代码  
                       findClassName(classList, pkgName, url, isRecursive);  
                    }  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        }           

    }  
    
    public static void findClassName(List<Class<?>> clazzList, String pkgName, String pkgPath, boolean isRecursive) {  
        if(clazzList == null){  
            return;  
        }  
        File[] files = filterClassFiles(pkgPath);// 过滤出.class文件及文件夹  
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");       
        if(files != null){  
            for (File file : files) {  
                String fileName = file.getName();  
                if (file.isFile()) {  
                    // .class 文件的情况                      
                    if (file.lastModified() > lastModifiedTime) {
                    	lastModifiedTime = file.lastModified();
					}
//                 System.out.println(file.getName() + " 修改时间 " + simpleDateFormat.format(new Date(file.lastModified())) );
                } else {  
                    // 文件夹的情况  
                    if(isRecursive){  
                        // 需要继续查找该文件夹/包名下的类  
                        String subPkgName = pkgName +"."+ fileName;  
                        String subPkgPath = pkgPath +"/"+ fileName;  
                        findClassName(clazzList, subPkgName, subPkgPath, true);  
                    }  
                }  
            }  
        }  
    }  
    
    private static File[] filterClassFiles(String pkgPath) {  
        if(pkgPath == null){  
            return null;  
        }  
        // 接收 .class 文件 或 类文件夹  
        return new File(pkgPath).listFiles(new FileFilter() {  
            @Override  
            public boolean accept(File file) {  
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();  
            }  
        });  
    }
    
    /** 
     * 第三方Jar类库的引用。<br/> 
     * @throws IOException  
     * */  
    public static void findClassName(List<Class<?>> clazzList, String pkgName, URL url, boolean isRecursive) throws IOException {  
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();  
        JarFile jarFile = jarURLConnection.getJarFile();  
        Enumeration<JarEntry> jarEntries = jarFile.entries();  
        while (jarEntries.hasMoreElements()) {  
            JarEntry jarEntry = jarEntries.nextElement();  
            String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class  
            String clazzName = jarEntryName.replace("/", ".");  
            int endIndex = clazzName.lastIndexOf(".");  
            String prefix = null;  
            if (endIndex > 0) {  
                String prefix_name = clazzName.substring(0, endIndex);  
                endIndex = prefix_name.lastIndexOf(".");  
                if(endIndex > 0){  
                    prefix = prefix_name.substring(0, endIndex);  
                }  
            }  
            if (prefix != null && jarEntryName.endsWith(".class")) {  
            	
                if (jarEntry.getLastModifiedTime().toMillis() > lastModifiedTime) {
                	lastModifiedTime = jarEntry.getLastModifiedTime().toMillis();
				}
                if(prefix.equals(pkgName)){  
                    //System.out.println("jar entryName:" + jarEntryName);  

                } else if(isRecursive && prefix.startsWith(pkgName)){  
                    // 遍历子包名：子类  
                    //System.out.println("jar entryName:" + jarEntryName +" isRecursive:" + isRecursive);  
                }  
            }  
        }  
    }  
    
}
