package com.lx.weixin.httpClient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上传servlet
 */
public class FileUploadUtil extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件上传
	 * @param filePath	文件上传路径
	 */
	public void fileUpload(HttpServletRequest request, String filePath, String uploadPath) {
		
		try {
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setSizeThreshold(4069);	// 设置缓冲区大小，这里是4kb
			fileItemFactory.setRepository(new File(filePath));	// 设置缓冲区目录
			
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			fileUpload.setSizeMax(4194304); // 限制文件大小最大为4M
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			Iterator<FileItem> iterator = fileItems.iterator();
			while(iterator.hasNext()) {
				
				FileItem fileItem = iterator.next();
				String fileName = fileItem.getName();
				if(fileName != null) {
					File fullFile = new File(fileItem.getName());
					File saveFile = new File(uploadPath, fullFile.getName());
					fileItem.write(saveFile);
				}
			}
			System.out.println("upload succeed");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param uploadPath
	 */
	public void processUpload(HttpServletRequest request, HttpServletResponse response, String uploadPath) {
		
		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		
		System.out.println("Come on, baby .......");
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		//response.setContentLength((int) uploadFile.length());	//服务端在处理之后，可以在Header中设置返回给客户端的简单信息。如果返回客户端是一个流的话，流的大小必须提前设置！
		
		//检测是不是存在上传文件
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//指定在内存中缓存数据大小,单位为byte,这里设为1Mb
			factory.setSizeThreshold(1024*1024);
			
			//设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
			factory.setRepository(new File("D:\\test"));
			
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			// 指定单个上传文件的最大尺寸,单位:字节，这里设为50Mb
			upload.setFileSizeMax(50 * 1024 * 1024);
			
			//指定一次上传多个文件的总尺寸,单位:字节，这里设为50Mb
			upload.setSizeMax(50 * 1024 * 1024);
			upload.setHeaderEncoding("UTF-8");
			
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);	// 解析request请求
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			if(items!=null){
				
				Iterator<FileItem> iter = items.iterator();	//解析表单项目
				while (iter.hasNext()) {
					
					FileItem item = iter.next();
					if (item.isFormField()) {	//如果是普通表单属性
						
						String name = item.getFieldName();	//相当于input的name属性   <input type="text" name="content">
						String value = item.getString();	//input的value属性
						
						System.out.println("属性:" + name + " 属性值:" + value);
					} else {	//如果是上传文件
						
						String fieldName = item.getFieldName();	//属性名
						
						String fileName = item.getName();	//上传文件路径
						fileName = fileName.substring(fileName.lastIndexOf("/") + 1);// 获得上传文件的文件名
						
						try {
							item.write(new File(uploadPath, fileName));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		response.addHeader("token", "hello");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadPath = "";
		String filePath = "";
		fileUpload(request, filePath, uploadPath);
	}

}
