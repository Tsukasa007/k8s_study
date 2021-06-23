
sed -i '/#!\/bin\/sh/a\# chkconfig: 2345 98 98\n# description:http server' /etc/init.d/httpd
