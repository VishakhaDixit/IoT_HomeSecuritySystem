import sys
import os
import socket
import nmap

HOST = '127.0.0.1'
PORT = 65129

ip_addr = {}
ip = '192.168.10.'
server_ip = None
active_ips_detected = ""

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))

for client in range(99, 104):
	nm = nmap.PortScanner()
	print("Scanning", ip+str(client))
	nm.scan(ip+str(client), '79-3310', '-Pn')
	if nm.all_hosts():
		ip_addr[ip+str(client)] = nm
		
for ip,nm in ip_addr.items():  
    for host in nm.all_hosts():
        for proto in nm[host].all_protocols():
            print('----------')
            print("IP: ",ip)
            print('Protocol : {0}'.format(proto))
            
            lport = list(nm[host][proto].keys())
            lport.sort()
            for port in lport:
                print('port : {0}\tstate : {1}\tservice : {2}'.format(port, nm[host][proto][port]["state"], nm[host][proto][port]["name"]))
                if "mysql" in nm[host][proto][port]["name"]:
                    server_ip = ip
                    if len(active_ips_detected) == 0:
                        active_ips_detected += server_ip
                    else:
                        active_ips_detected += ("," + server_ip)
print("\n\n\n")                    
print("Server Ip: ",server_ip)
print("Active IPs detected: ", active_ips_detected)

# Creating a local server that has the ip address of the HTTPD Apache server

while True:
    server_socket.listen()
    conn, addr = server_socket.accept()
    with conn:
        print("Recieved conn",addr)
        conn.sendall(active_ips_detected.encode('utf-8'))
