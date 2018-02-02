# Java的Web项目(SSM)框架实例 附带App加密接口通信

#### 备注：下边会有配置搭建运行步骤，本项目全是从网上学习后的结果，感谢互联网上不吝分享的coder。

## 1. 实现相关功能

后台登录/注册/用户管理/权限管理/菜单管理等等

## 2. 本项目局部效果截图：

<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/home.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/userInfo.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/UserManager.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/RolePermitM.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/RoleEdit.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/MenuManager.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/MenuAdd.png?raw=true">
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/ApiInterface.png?raw=true">

## 3. 安装

1. 创建程序存放目录

```
 bobo@migang:/$ mkdir /var/www/admin
 bobo@migang:/$ cd /var/www/admin
 bobo@migang:admin$ git clone https://github.com/BoBoGithub/JavaSSM.git
 bobo@migang:admin$ cd JavaSSM
```

2. 根据自己环境修改配置文件

```
 bobo@migang:admin$ vim profiles/dev.properties
```

<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/Config.png?raw=true">

3. 编译打包

```
 参数说明：-Pdev 开发环境包   | -Ptest 测试环境包  | -Ponline 线上环境包
 
 bobo@migang:admin$ mvn clean package -Pdev  -Dmaven.test.skip=true
 bobo@migang:admin$ 
 bobo@migang:admin$ cd target/
 bobo@migang:admin$ mv admin.war /usr/local/tomcat/webapps
```
<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/pack.png?raw=true">

4. 启动Tomcat服务器运行[或再用nginx做一个反向代理]
```
 bobo@migang:admin$ cd /usr/local/tomcat/bin/
 bobo@migang:admin$ ./startup.sh
```

5. 浏览器访问 http://localhost:8080/admin

<img src="https://github.com/BoBoGithub/JavaSSM/blob/master/image/Login.png?raw=true">


## 备注：
当前项目只是实现了后台基本的系统管理功能，能直接在此基础上进行业务开发，后续若有激情会再过来优化迭代更新[。](http://www.xgsddl.com)