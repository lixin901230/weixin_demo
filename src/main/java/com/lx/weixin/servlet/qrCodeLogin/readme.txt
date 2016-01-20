实现流程：
pc端：
	1:打开二维码登录网页index.html
	2:index.html调用GetQrCodeServlet
	3:GetQrCodeServlet干2件事
	　　a:生成随机的uuid,是一个唯一标识，该标识贯穿整个流程
	　　b:生成二维码图片，二维码信息：http://60.28.201.37:8380/QrCodeLoginPro/Login.html?uuid=" + uuid
	4:index页面展示二维码
	5:index页面调用LongConnectionCheckServlet进行长连接轮询操作，参数为uuid
	6:LongConnectionCheckServlet只干1件事
	　　a:拿到uuid后循环检查loginUserMap中uuid是否不为null。
	7:如果为null则代表没有登录，index.html将继续进行轮询
	　　ps: LongConnectionCheckServlet 一个长连接请求检测登录状态
	　　　　loginUserMap 是一个静态的map结构的登录池，uuid为key , 登录信息为value

手机端：
	1:扫描pc端的二维码
	2:打开二维码中的网页 http://60.28.201.37:8380/QrCodeLoginPro/Login.html?uuid=" + uuid
	3:登录，将uname upwd uuid 传递给登录程序PhoneLoginServlet
	4:PhoneLoginServlet干2件事
	　　a:检测登录
	　　b:登录成功后将登录信息插入到loginUserMap中去，uuid为key

pc端：
　　1:继续轮询检测uuid中是否为null
　　2:登录后的uuid中就不为null了，此时LongConnectionCheckServlet停止循环，返回登录状态。