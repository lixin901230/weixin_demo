package com.lx.weixin.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lx.weixin.util.FileUtil;
 
/**
 * 通过zxing生成/解析 二维码（带logo的）
 * @author lixin
 */
public class ZXingQRCodeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZXingQRCodeUtil.class);
	
	// 二维码存放目录绝对路径
	private static String fileDirPath = FileUtil.getIncomingDirPath();
	
    /**
     * 测试
     * @param args
     * @throws WriterException
     */
    public static void main(String[] args) {
    	
        String content = "http://www.baidu.com";	//扫描二维码时访问的地址
        
        String qrFilePath = fileDirPath + "qrcode_new.jpg";	//无LOGO二维码图片路径
        String logoFilePath = fileDirPath + "logo.png";	//LOGO图片路径
        String logoQRFilePath = fileDirPath + "qrcode_logo.png";	//LOGO图片路径
 
        ZXingQRCodeUtil zp = new ZXingQRCodeUtil();
        
        // 生成二维码图片
        zp.generateQRCodeImage(qrFilePath, content);
        
        // 生成含LOGO的二维码
        zp.generateLogoQRCodeImage(logoQRFilePath, content, logoFilePath);

        // 解析二维码图片
        parseQRCodeImage(fileDirPath + "qrcode_new.jpg");
        //System.out.println("解析后的二维码信息：\n"+qrCodeInfo);
    }
    
    /**
     * 生成不含logo的二维码
     * @param filePath
     * 				二维码图片路径
     * @param content
     * 				二维码图片内容
     */
    public void generateQRCodeImage(String filePath, String content) {
    	generateLogoQRCodeImage(filePath, content, null);
    }
    
    /**
     * 生成含logo的二维码
     * @param filePath
     * 				二维码图片路径
     * @param content
     * 				二维码信息
     * @param logoFilePath
     * 				二维码图片LOGO文件路径；<br/>
     * 	注意：参数logoFilePath（LOGO图片路径）不为空时，则生成含LOGO的二维码图片；为空则生成无LOGO的二维码
     */
    public void generateLogoQRCodeImage(String filePath, String content, String logoFilePath) {
    	
    	logger.info(">>>>>>开始生成二维码图片...");
    	try {
    		File file = new File(filePath);
    		
    		String fileDirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
    		String fileName = file.getName();
    		
    		// 1、若文件存在则给新文件加时间戳后缀
            if (file.exists()) {
            	fileName = fileName.substring(0, fileName.lastIndexOf(".")) +"_"+ new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
            	file = new File(fileDirPath, fileName);
            }
            
            // 2、生成无LOGO的二维码图片
            ZXingQRCodeUtil zp = new ZXingQRCodeUtil();
            BufferedImage bim = zp.getQRCodeBufferedImage(content, BarcodeFormat.QR_CODE, 300, 300, zp.getDecodeHintType());
            ImageIO.write(bim, "jpeg", file);
            
			// 3、给二维码图片添加LOGO（注意：此处默认将生成的含logo的二维码文件覆盖第2步中生成的无logo的二维码图片）
			if(StringUtils.isNotEmpty(logoFilePath)) {
				File qrLogoFile = new File(logoFilePath);
				BufferedImage image = addLogoToQRCode(file, qrLogoFile, new LogoConfig());
				
				// 写出含logo的二维码图片文件，默认覆盖生成的无logo的二维码图片
	            ImageIO.write(image, "jpeg", new File(fileDirPath, fileName));
			}
            
            logger.info(">>>>>>生成二维码图片完成！");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * 二维码的解析
     * 
     * @param filePath	需要解析的二维码图片绝对路径
     */
	public static void parseQRCodeImage(String filePath) {
		
		logger.info(">>>>>>开始解析二维码图片...");
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
 
            File file = new File(filePath);
            if (!file.exists()) {
            	throw new Exception("文件"+file.getAbsolutePath()+"不存在");
            }
 
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
 
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            Result result = formatReader.decode(binaryBitmap, hints);
 
            logger.info(">>>>>>解析二维码图片完成！");
            
            System.out.println("result = " + result.toString());
            System.out.println("resultFormat = " + result.getBarcodeFormat());
            System.out.println("resultText = " + result.getText());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 给二维码图片添加Logo
     * 
     * @param qrPic		二维码图片
     * @param logoPic	logo图片
     */
    public BufferedImage addLogoToQRCode(File qrPic, File logoPic, LogoConfig logoConfig) {
    	
    	logger.info(">>>>>>开始添加二维码的LOGO图片...");
        try  {
            if (!qrPic.isFile()) {
                throw new Exception("file not find！ msg：二维码图片不存在；["+qrPic.getAbsolutePath()+"]");
            }
            if (!logoPic.isFile()) {
            	throw new Exception("file not find！ msg：二维码的Logo图片不存在；["+logoPic.getAbsolutePath()+"]");
            }
            
            // 读取二维码图片，并构建绘图对象
            BufferedImage image = ImageIO.read(qrPic);
            Graphics2D g = image.createGraphics();
            
            // 读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);
            
            int widthLogo = logo.getWidth();
            int heightLogo = logo.getHeight();
            
            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - logo.getHeight()) / 2;
            
            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);
            
            g.dispose();
            
            // 写出含logo的二维码图片文件，默认覆盖生成的无logo的二维码图片
            //ImageIO.write(image, "jpeg", new File(qrPic.getAbsolutePath()));
            logger.info(">>>>>>添加二维码的LOGO完成...");
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 将二维码生成为文件
     * 
     * @param bm
     * @param imageFormat
     * @param file
     */
    public void decodeQRCode2ImageFile(BitMatrix bm, String imageFormat, File file) {
        try {
            if (null == file || file.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("文件异常，或扩展名有问题！");
            }
            
            BufferedImage bi = fileToBufferedImage(bm);
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将二维码生成为输出流
     * 
     * @param content
     * @param imageFormat
     * @param os
     */
    public void decodeQRCode2OutputStream(BitMatrix bm, String imageFormat, OutputStream os) {
        try {
            BufferedImage image = fileToBufferedImage(bm);
            ImageIO.write(image, imageFormat, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 构建初始化二维码
     * 
     * @param bm
     * @return
     */
    public BufferedImage fileToBufferedImage(BitMatrix bm) {
        BufferedImage image = null;
        try {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
 
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
 
    /**
     * 生成二维码bufferedImage图片
     * 
     * @param content
     *            编码内容
     * @param barcodeFormat
     *            编码类型
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param hints
     *            设置参数
     * @return
     */
    public BufferedImage getQRCodeBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
 
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
 
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }
 
    /**
     * 设置二维码的格式参数
     * 
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);
 
        return hints;
    }
}
 
/**
 * Logo配置信息
 */
class LogoConfig {
	
	// logo默认边框颜色
	public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
	// logo默认边框宽度
	public static final int DEFAULT_BORDER = 2;
	// logo大小默认为照片的1/5
	public static final int DEFAULT_LOGOPART = 5;
	
	private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;
 
    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }
 
    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }
 
    public int getBorder() {
        return border;
    }
 
    public int getLogoPart()  {
        return logoPart;
    }
}
