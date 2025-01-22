## 公网IP
http://141.147.145.18/

## SSH(apache2静态页面用)
`ssh -L 80:localhost:80 ubuntu@141.147.145.18 -i C:\work\ssh-key-2025-01-01.key`

## phpMyAdmin:打开浏览器,访问:(root/558)
http://127.0.0.1/phpmyadmin

## Oracle cloud环境构筑
#### ●使用oracle cloud(ampere arm chip)每月4CPU免费，25GB内存免费
#### ●绑定的是yodobashi card
#### ●系统是ubuntu 20,用户名ubuntu
#### ●密钥c:\work\ssh-key-2025-01-01.key
#### ●python jupyternotebook 
https://speedysense.com/install-jupyter-notebook-on-ubuntu-20-04/#:~:text=How%20to%20Install%20Jupyter%20Notebook%20on%20Ubuntu%2020.04,6%20Step%206%20Run%20Jupyter%20Notebook%20...%20%E3%81%9D%E3%81%AE%E4%BB%96%E3%81%AE%E3%82%A2%E3%82%A4%E3%83%86%E3%83%A0
#### ●设置SSH隧道(端口转发)使用git bash
`ssh -L 8888:localhost:8888 ubuntu@138.2.48.236`
#### ●mysql
https://www.layerstack.com/resources/tutorials/How-to-install-MySQL-MariaDB-MongoDB-on-Ubuntu-ARM-server#:~:text=Connect%20to%20your%20server%20via%20SSH.%20Log%20in,version%20using%20the%20following%20command.%20%23%20mysql%20-v
```
sudo mysql -v
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 8.0.40
```
#### ●java tomcat
```
https://ja.linux-console.net/?p=16823

# tomcat status
ss -ltn

## ●修改tomcat端口到80
`sudo nano /etc/tomcat9/server.xml`
```

--------------------------------------------------------------------


#### 安装phpadmin
问claude
```
我启动了一个ubuntu的vm在oracle的cloud上，ip是168.138.33.150，系统是ubuntu，芯片是ampere（arm）我在它上面启动了一个mysql
我想使用web页面
```
```
sudo apt install apache2 php php-mysql php-mbstring php-zip php-gd php-json php-curl
sudo apt install phpmyadmin
安装过程中:

选择Apache2作为Web服务器
选择Yes配置数据库
设置phpMyAdmin管理员密码（558）
配置Apache使其加载phpMyAdmin:
sudo ln -s /usr/share/phpmyadmin /var/www/html/phpmyadmin
重启Apache服务:
sudo systemctl restart apache2
```
pas:558




#### ●设置tomcat 端口默认的8080
问的chatgpt
```prompt
我想在ubuntu里安装tomcat。该怎么做
从这个link
https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.33/bin/apache-tomcat-10.1.33.tar.gz
```

#### ●关闭防火墙
```
ubuntu@instance-20241115-2125:~$ sudo iptables -P INPUT ACCEPT
ubuntu@instance-20241115-2125:~$ sudo iptables -P FORWARD ACCEPT
ubuntu@instance-20241115-2125:~$ sudo iptables -P OUTPUT ACCEPT
ubuntu@instance-20241115-2125:~$ sudo iptables -F
```

#### ●申请固定IP V4

【springboot项目，从开发环境部署到测试环境（oracle cloud云服务器+宝塔面板+javaweb项目）---VM实例创建配置环境】https://www.bilibili.com/video/BV15R4y1L7hj?vd_source=e024c89f590784eeb986f69f27bcfa20
