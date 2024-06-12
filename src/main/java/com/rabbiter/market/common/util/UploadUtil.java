package com.rabbiter.market.common.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件上传工具
 */
public class UploadUtil {
	//阿里域名
	private static final String ALI_DOMAIN = "https://zyl-9.oss-cn-chengdu.aliyuncs.com/";
	private static final String ENDPOINT = "http://oss-cn-chengdu.aliyuncs.com";
	private static final String BUCKET_NAME = "zyl-9";

	private static final String ACCESS_KEY_ID = "LTAI5tGxrbz4oXKzQ63LZzQn";
	private static final String ACCESS_KEY_SECRET = "Do4mpXNQ4bOTzpNqrUhTeQSSwrXfHe";
	private static  final String path = "wolf2w-70";
	//MultipartFile 对象
//	public static String uploadAli(MultipartFile file,String path_suffix) throws Exception {
//		//生成文件名称
//		String uuid = UUID.randomUUID().toString();
//		String orgFileName =file.getOriginalFilename();//获取真实文件名称 xxx.jpg
//		String ext= "." + FilenameUtils.getExtension(orgFileName);//获取拓展名字.jpg
//		String fileName =path+path_suffix+"/"+uuid + ext;//xxxxxsfsasa.jpg
//		// 创建OSSClient实例。
//		OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
//		// 上传文件流。
//		ossClient.putObject(BUCKET_NAME, fileName, file.getInputStream());
//		// 关闭OSSClient。
//		ossClient.shutdown();
//		return ALI_DOMAIN + fileName;
//	}

}
























