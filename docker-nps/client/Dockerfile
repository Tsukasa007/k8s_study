FROM alpine:3.9

WORKDIR /
ENV NPS_VERSION 0.23.2

RUN set -x  \
	&& wget --no-check-certificate https://github.com/cnlh/nps/releases/download/v${NPS_VERSION}/linux_amd64_client.tar.gz \
	&& mkdir /usr/local/nps-client  \
	&& tar xzvf linux_amd64_client.tar.gz -C /usr/local/nps-client \
	&& rm -rf /linux_amd64_client.tar.gz 

WORKDIR /usr/local/nps-client
ADD start.sh ./start.sh
RUN mv nps/npc ./ \
    && rm -fr ./nps \
    && chmod +x ./start.sh \
    && chmod +x ./npc

ADD npc.conf ./npc.conf

CMD ["/usr/local/nps-client/start.sh"]