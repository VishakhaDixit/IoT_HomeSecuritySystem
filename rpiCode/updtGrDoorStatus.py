#import the GPIO and time package
import RPi.GPIO as GPIO
import time
import requests
import json
import sys
import socket
import signal

    
server_ip = None
HOST = '127.0.0.1'
PORT = 65129

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST,PORT))
    server_ip_rcvd = s.recv(1024).decode('utf-8')
    ip_list = server_ip_rcvd.split(",")
    
    input_msg = "Choose server ip to connect: \n"
    for i, item in enumerate(ip_list):
        input_msg += f'{i+1}){item}\n'
        
    input_msg += 'Your Choice: '
    value = input(input_msg)
    
    server_ip = ip_list[int(value)-1]

print("Server IP", server_ip)
if len(sys.argv) < 3:
	print("Use python3 filename.py 0,1")
	print("first argument corresponds to sensor0")
	print("second argument corresponds to sensor1")
	exit(0)
	
a=str(sys.argv[1])
b=str(sys.argv[2])

ip = "http://"+server_ip
url = ip+'/LoginRegister/updateGrDoorStatus.php'

payload = {'car1Door' : a, 'car2Door' : b}
r = requests.post(url,data = payload)
print(r.text)
