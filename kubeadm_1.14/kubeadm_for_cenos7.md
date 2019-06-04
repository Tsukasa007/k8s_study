# 所有主机：基本系统配置

## 关闭Selinux/firewalld
```bash
systemctl stop firewalld
systemctl disable firewalld
setenforce 0
sed -i "s/SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config
```


## 关闭交换分区
```bash
swapoff -a
yes | cp /etc/fstab /etc/fstab_bak
cat /etc/fstab_bak |grep -v swap > /etc/fstab

```

## 设置网桥包经IPTables，core文件生成路径
```bash
echo """
vm.swappiness = 0
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.conf.all.rp_filter = 1
net.ipv4.ip_forward = 1
net.bridge.bridge-nf-call-ip6tables = 1
""" > /etc/sysctl.conf
sysctl -p
```


## 同步时间
```bash
yum install -y ntpdate
ntpdate -u ntp.api.bz
```

## 升级内核
```bash
rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm ;yum --enablerepo=elrepo-kernel install kernel-ml-devel kernel-ml -y
```

## 检查默认内核版本是否大于4.14，否则请调整默认启动参数(grub2-set-default 0)
```bash
grub2-editenv list
```


##重启以更换内核
```bash
reboot
```

## 确认内核版本后，开启IPVS
```bash
uname -a
cat > /etc/sysconfig/modules/ipvs.modules <<EOF
```
##!/bin/bash
```bash
ipvs_modules="ip_vs ip_vs_lc ip_vs_wlc ip_vs_rr ip_vs_wrr ip_vs_lblc ip_vs_lblcr ip_vs_dh ip_vs_sh ip_vs_fo ip_vs_nq ip_vs_sed ip_vs_ftp nf_conntrack"
for kernel_module in \${ipvs_modules}; do
 /sbin/modinfo -F filename \${kernel_module} > /dev/null 2>&1
 if [ $? -eq 0 ]; then
 /sbin/modprobe \${kernel_module}
 fi
done
EOF
chmod 755 /etc/sysconfig/modules/ipvs.modules && bash /etc/sysconfig/modules/ipvs.modules && lsmod | grep ip_vs
```



###Kubernetes要求集群中所有机器具有不同的Mac地址、产品uuid、Hostname。可以使用如下命令查看Mac和uuid
####所有主机：检查UUID和Mac(必须不一样)
```bash
cat /sys/class/dmi/id/product_uuid
ip link
```

******

# 所有主机：安装配置docker

## 安装docker
```bash
yum install -y yum-utils device-mapper-persistent-data lvm2
yum-config-manager \
 --add-repo \
 https://download.docker.com/linux/centos/docker-ce.repo

yum makecache fast
yum install -y docker-ce
```

## 编辑systemctl的Docker启动文件和配置文件
```bash
sed -i "13i ExecStartPost=/usr/sbin/iptables -P FORWARD ACCEPT" /usr/lib/systemd/system/docker.service
mkdir -p /etc/docker
cat > /etc/docker/daemon.json <<EOF
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
  "max-size": "100m"
  },
    "storage-driver": "overlay2"
  }
EOF
```

## 启动docker
```bash
systemctl daemon-reload
systemctl enable docker
systemctl start docker
```


## 导入kube镜像
```bash
链接：https://pan.baidu.com/s/1FJiEGCUPPQ7dib5tVf7sDQ
提取码：5u12
docker load -i xxxx
```

```bash
k8s-v1.14.0-rpms.tgz 下载 并放在 /path/to/downloaded/file
mkdir -p /path/to/downloaded/file
链接：https://pan.baidu.com/s/180Kd3yKh_47CaGMenPLtKA
提取码：lhos
```

***

# master & node：安装kubernetes
## 依赖
```bash
yum install -y socat keepalived ipvsadm conntrack
cd /path/to/downloaded/file
tar -xzvf k8s-v1.14.0-rpms.tgz
cd k8s-v1.14.0
rpm -Uvh * --force
systemctl enable kubelet
kubeadm version -o short
```

```bash
{
    # master 集群启动
    kubeadm init --kubernetes-version=v1.14.0 --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=192.168.3.108

    # 别忘了启动log的命令
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config

    下载kube-flannel.yml
    链接：https://pan.baidu.com/s/1b-zGf60ZTtY09SsEnNe-ZA
    提取码：mi36

    # 运行 kubectl apply
    kubectl apply -f  kube-flannel.yml
}
或
{
    https://kubernetes.io/docs/setup/independent/create-cluster-kubeadm/
    找到对应版本flannel
    kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/a70459be0084506e4ec919aa1c114638878db11b/Documentation/kube-flannel.yml
}

```


## node join
```bash
kubeadm join 192.168.3.108:6443 --token ammyln.f8aq71go9x4zok7e --discovery-token-ca-cert-hash sha256:15a21f77deedc0e4492346f64e45536f2bfd3456e9f6da04730db5258a0c9be4
```


## 检查状态(在master执行)
```bash
kubectl get cs
kubectl get pods --all-namespaces
kubectl get nodes
```

##查看部署在哪台node

```bash
kubectl get pods --all-namespaces -o wide

kubectl get svc -n kube-system -n kube-system

kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/master/aio/deploy/recommended/kubernetes-dashboard-head.yaml
```



## 命令补全

```bash
yum install -y bash-completion
 locate bash_completion
 source /usr/share/bash-completion/bash_completion
 source <(kubectl completion bash)
   cat << EOF >> /root/.bash_profile
 source /usr/share/bash-completion/bash_completion
 source <(kubectl completion bash)
 EOF
 source /root/.bash_profile
```
