#!/bin/bash

echo "=== Network Interfaces ==="
ip addr show | grep -E "^[0-9]:|inet "

echo -e "\n=== Testing mcp-server connectivity ==="
if ping -c 1 -W 2 mcp-server &>/dev/null; then
    echo "✅ Can reach mcp-server"
    echo "IP address: $(getent hosts mcp-server | awk '{ print $1 }')"
else
    echo "❌ Cannot reach mcp-server"
fi

echo -e "\n=== DNS Resolution Test ==="
nslookup mcp-server 2>&1 || echo "DNS lookup failed"
