FROM alpine:3.9

WORKDIR /
ENV NPS_VERSION 0.23.2

RUN set -x && \
	wget --no-check-certificate https://github.com/cnlh/nps/releases/download/v${NPS_VERSION}/linux_amd64_server.tar.gz && \ 
	tar xzf linux_amd64_server.tar.gz && \
	mv /nps /usr/local/ && \
    cd /usr/local/nps && \
	rm -rf /linux_amd64_server.tar.gz

WORKDIR /usr/local/nps
ADD start.sh /usr/local/nps/start.sh
RUN chmod +x /usr/local/nps/start.sh
ADD nps.conf /usr/local/nps/conf/
CMD /usr/local/nps/start.sh