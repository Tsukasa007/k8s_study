#!/bin/sh

if [ -f "/usr/local/nps-client/npc.conf" ]; then
  /usr/local/nps-client/npc -config=/usr/local/nps-client/npc.conf
fi