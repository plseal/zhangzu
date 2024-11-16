## Oracle cloud环境构筑
#### ●计划使用oracle cloud(ampere arm chip)
#### ●绑定的是yodobashi card
#### ●系统是ubuntu 20,用户名ubuntu
#### ●密钥c:\work\ssh-key-2024-11-15.key
#### ●python jupyternotebook 
https://speedysense.com/install-jupyter-notebook-on-ubuntu-20-04/#:~:text=How%20to%20Install%20Jupyter%20Notebook%20on%20Ubuntu%2020.04,6%20Step%206%20Run%20Jupyter%20Notebook%20...%20%E3%81%9D%E3%81%AE%E4%BB%96%E3%81%AE%E3%82%A2%E3%82%A4%E3%83%86%E3%83%A0
#### ●设置SSH隧道(端口转发)使用git bash
`ssh -L 8888:localhost:8888 ubuntu@168.138.33.150`
#### ●mysql
https://www.layerstack.com/resources/tutorials/How-to-install-MySQL-MariaDB-MongoDB-on-Ubuntu-ARM-server#:~:text=Connect%20to%20your%20server%20via%20SSH.%20Log%20in,version%20using%20the%20following%20command.%20%23%20mysql%20-v
```
sudo mysql -v
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 8.0.40
```
#### 安装phpadmin
问claude

我启动了一个ubuntu的vm在oracle的cloud上，ip是168.138.33.150，系统是ubuntu，芯片是ampere（arm）我在它上面启动了一个mysql
我想使用web页面
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


#### ●设置SSH隧道(端口转发)使用git bash
`ssh -L 80:localhost:80 ubuntu@168.138.33.150`

访问phpMyAdmin:打开浏览器,访问:(root/558)

http://127.0.0.1/phpmyadmin

