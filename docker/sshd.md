在用户目录下新建一个.ssh目录，并将其目录权限改为700（仅用户自身有读写操作权限）：
mkdir .ssh
chmod 700 .ssh
进入.ssh目录，使用ssh-keygen命令生成rsa密钥对：
#一路回车即可
ssh-keygen -t rsa -b 4096 -C "biabia123456@126.com"
这时生成了两个文件：id_rsa和id_rsa.pub，其中前一个为私钥，后一个为公钥，公钥须保留在服务器上，私钥拷贝到客户端机器上
在.ssh目录中新建一个文件名为：authorized_keys，将公钥内容拷贝到这个文件中，并将文件权限改为600（仅用户自身有读写权限）：
touch authorized_keys
cat id_rsa.pub >> authorized_keys
chmod 600 authorzied_keys
修改sshd_config配置如下：
vi /etc/ssh/sshd_config
#禁用root账户登录，如果是用root用户登录请开启
PermitRootLogin yes

#是否让 sshd 去检查用户家目录或相关档案的权限数据，
#这是为了担心使用者将某些重要档案的权限设错，可能会导致一些问题所致。
#例如使用者的 ~.ssh/ 权限设错时，某些特殊情况下会不许用户登入
StrictModes on

#是否允许用户自行使用成对的密钥系统进行登入行为，仅针对 version 2。
#至于自制的公钥数据就放置于用户家目录下的 .ssh/authorized_keys 内

RSAAuthentication yes
PubkeyAuthentication yes
AuthorizedKeysFile      .ssh/authorized_keys
#有了证书登录了，就禁用密码登录吧，安全要紧
PasswordAuthentication no
