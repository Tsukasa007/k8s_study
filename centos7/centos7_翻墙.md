找一台开飞机

右键允许其他设备接入


ip注意替换


vim /etc/profile


http_proxy=http://10.1.12.72:1080/
https_proxy=http://10.1.12.72:1080/


:x



export http_proxy
export https_proxy


source /etc/profile


curl www.google.com
