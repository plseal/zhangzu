## 环境构筑
#### ●计划使用oracle cloud
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
