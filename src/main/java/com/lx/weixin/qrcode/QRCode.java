package com.lx.weixin.qrcode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lx.weixin.util.FileUtil;

/**
 * 通过zxing 生成 二维码 及 解析二维码
 */
public class QRCode {
	
	public static void main(String[] args) {
		
		String resRealPath = FileUtil.getResRealPath();
		resRealPath = resRealPath.endsWith("/") ? resRealPath : resRealPath + "/"; 
		
		String fileName = "qr_"+new Date().getTime()+".png";
		String qrFilePath = resRealPath + FileUtil.UPLOAD_PATH + "/"+ fileName;
		/*File file = new File(filePath);
		if(!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
		}*/
		
		JSONObject json = new JSONObject();  
        json.put("zxing", "http://weixin.qq.com/r/BUWlvSDE_X2GrWTe9xAX");  
        json.put("author", "lixin");  
        String qrContent = json.toString();	// 内容
		
		generateQRImage(qrContent, qrFilePath);
		
//		String filePath = "D://Workspaces//eclipse-jee-luna-SR2//workspace1//weixin//src//main//webapp//incoming//qr1.png";
//		parseQRCode(filePath);;
	}

	/**
	 * 通过 zxing 将从微信平台换取到的二维码信息 生成 二维码图片
	 * @param qrContent	二维码内容信息
	 * @param qrFilePath	二维码图片地址
	 */
	public static void generateQRImage(String qrContent, String qrFilePath) {
		try {
			
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = new QRCodeWriter().encode(qrContent, BarcodeFormat.QR_CODE, 256, 256, hints);	// 生成矩阵
			int width = bitMatrix.getWidth();	// 图像宽度
			int height = bitMatrix.getHeight();	// 图像高度 
			String format = "png";	// 图像类型
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			    }
			}
			ImageIO.write(image, format, new File(qrFilePath));	// 输出图像
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 解析二维码图像 
     * @param qrCodeImgFilePath	需要解析的二维码图片路径
     */  
    public static void parseQRCode(String qrCodeImgFilePath) {  
          
        BufferedImage image;  
        try {  
            image = ImageIO.read(new File(qrCodeImgFilePath));  
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
            JSONObject content = JSONObject.fromObject(result.getText());
            System.out.println("图片中内容：  ");  
            System.out.println("author： " + content.getString("author"));  
            System.out.println("zxing：  " + content.getString("zxing"));  
            System.out.println("图片中格式：  ");  
            System.out.println("encode： " + result.getBarcodeFormat());  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (NotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
}
