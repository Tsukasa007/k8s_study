#!/bin/bash

if [ $EUID -ne 0 ];then
    echo "You must be root (or sudo) to run this script"
    exit 1
fi

if [ $# != 1 ] ; then
    echo "Usage: $0 [master-hostname | master-ip-address]"
    echo " e.g.: $0 api.k8s.hiko.im"
    exit 1;
fi

token=`kubeadm token create`
cert_hash=`openssl x509 -pubkey -in /etc/kubernetes/pki/ca.crt | openssl rsa -pubin -outform der 2>/dev/null | openssl dgst -sha256 -hex | sed 's/^.* //'`

echo "Refer the following command to join kubernetes cluster:"
echo "kubeadm join $1:6443 --token ${token} --discovery-token-ca-cert-hash sha256:${cert_hash}"
