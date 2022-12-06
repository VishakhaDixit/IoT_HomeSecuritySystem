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

print("Server Ip: ",server_ip)
    
ip = "http://"+server_ip
    
urlgrdoors = ip+'/LoginRegister/getGrDoorStatus.php'
urllights =  ip+'/LoginRegister/getLightsStatus.php'
urllocks =   ip+'/LoginRegister/getLocksStatus.php'
urlsensor =  ip+'/LoginRegister/getSensorsStatus.php'
urlthermostat = ip+'/LoginRegister/getThermostatStatus.php'


# Read initial status
rgaragedoorval = requests.post(urlgrdoors,).json()
rlightsval = requests.post(urllights,).json()
rlocks = requests.post(urllocks,).json()
rsensor = requests.post(urlsensor,).json()
rthermostat = requests.post(urlthermostat,).json()


# Initialize global variables
prevMsg = rlightsval['message']
prevLrBulb1Status = prevMsg['lrBulb1']
prevLrBulb2Status = prevMsg['lrBulb2']
prevKitBulb1Status = prevMsg['kitBulb1']
prevKitBulb2Status = prevMsg['kitBulb2']
prevMb1Bulb1Status = prevMsg['mb1Bulb1']
prevMb1Bulb2Status = prevMsg['mb1Bulb2']
prevMb2Bulb1Status = prevMsg['mb2Bulb1']
prevMb2Bulb2Status = prevMsg['mb2Bulb2']

prevMsg = rgaragedoorval['message']
prevCar1DoorStatus = prevMsg['car1Door']
prevCar2DoorStatus = prevMsg['car2Door']

prevMsg = rlocks['message']
prevFrontDoorStatus = prevMsg['frontDoor']
prevBackDoorStatus = prevMsg['backDoor']

prevMsg = rsensor['message']
prevLrSensor1Status = prevMsg['lrSensor1']
prevLrSensor2Status = prevMsg['lrSensor2']
prevLrMdStatus = prevMsg['lrMd']
prevKitSensor1Status = prevMsg['kitSensor1']
prevKitSensor2Status = prevMsg['kitSensor2']
prevKitMdStatus = prevMsg['kitMd']
prevMb1Sensor1Status = prevMsg['mb1Sensor1']
prevMb1Sensor2Status = prevMsg['mb1Sensor2']
prevMb1MdStatus = prevMsg['mb1Md']
prevMb2Sensor1Status = prevMsg['mb2Sensor1']
prevMb2Sensor2Status = prevMsg['mb2Sensor2']
prevMb2MdStatus = prevMsg['mb2Md']

prevMsg = rthermostat['message']
prevMfModeStatus = prevMsg['mfMode']
prevMfFanStatus = prevMsg['mfFan']
prevMfCurTempStatus = prevMsg['mfCurTemp']
prevMfConTempStatus = prevMsg['mfConTemp']
prevUpModeStatus = prevMsg['upMode']
prevUpFanStatus = prevMsg['upFan']
prevUpCurTempStatus = prevMsg['upCurTemp']
prevUpConTempStatus = prevMsg['upConTemp']

print("\n\nCurrent lights status:")
print("lrBulb1: ", prevLrBulb1Status)
print("lrBulb2: ", prevLrBulb2Status)
print("kitBulb1: ", prevKitBulb1Status)
print("kitBulb2: ", prevKitBulb2Status)
print("mb1Bulb1: ", prevMb1Bulb1Status)
print("mb1Bulb2: ", prevMb1Bulb2Status)
print("mb2Bulb1: ", prevMb2Bulb1Status)
print("mb2Bulb2: ", prevMb2Bulb2Status)

print("\nCurrent Locks status:")
print("frontDoor: ", prevFrontDoorStatus)
print("backDoor: ",prevBackDoorStatus)

print("\nCurrent Grarage Doors status:")
print("car1Door: ", prevCar1DoorStatus)
print("car2Door: ",prevCar2DoorStatus)

print("\nCurrent Sensors status:")
print("lrSensor1: ", prevLrSensor1Status)
print("lrSensor2: ", prevLrSensor2Status)
print("lrMd: ", prevLrMdStatus)
print("kitSensor1: ", prevKitSensor1Status)
print("kitSensor2: ", prevKitSensor2Status)
print("kitMd: ", prevKitMdStatus)
print("mb1Sensor1: ", prevMb1Sensor1Status)
print("mb1Sensor2: ", prevMb1Sensor2Status)
print("mb1Md: ", prevMb1MdStatus)
print("mb2Sensor1: ", prevMb2Sensor1Status)
print("mb2Sensor2: ", prevMb2Sensor2Status)
print("mb2Md: ", prevMb2MdStatus)

print("\nCurrent Thermostat status:")
print("mfMode: ", prevMfModeStatus)
print("mfFan: ", prevMfFanStatus)
print("mfCurTemp: ", prevMfCurTempStatus)
print("mfConTemp: ", prevMfConTempStatus)
print("upMode: ", prevUpModeStatus)
print("upFan: ", prevUpFanStatus)
print("upCurTemp: ", prevUpCurTempStatus)
print("upConTemp: ", prevUpConTempStatus)

while(True):
    rgaragedoorval = requests.post(urlgrdoors,).json()
    rlightsval = requests.post(urllights,).json()
    rlocks = requests.post(urllocks,).json()
    rsensor = requests.post(urlsensor,).json()
    rthermostat = requests.post(urlthermostat,).json()
    
    curMsg = rlightsval['message']
    curLrBulb1Status = curMsg['lrBulb1']
    curLrBulb2Status = curMsg['lrBulb2']
    curKitBulb1Status = curMsg['kitBulb1']
    curKitBulb2Status = curMsg['kitBulb2']
    curMb1Bulb1Status = curMsg['mb1Bulb1']
    curMb1Bulb2Status = curMsg['mb1Bulb2']
    curMb2Bulb1Status = curMsg['mb2Bulb1']
    curMb2Bulb2Status = curMsg['mb2Bulb2']

    curMsg = rgaragedoorval['message']
    curCar1DoorStatus = curMsg['car1Door']
    curCar2DoorStatus = curMsg['car2Door']

    curMsg = rlocks['message']
    curFrontDoorStatus = curMsg['frontDoor']
    curBackDoorStatus = curMsg['backDoor']

    curMsg = rsensor['message']
    curLrSensor1Status = curMsg['lrSensor1']
    curLrSensor2Status = curMsg['lrSensor2']
    curLrMdStatus = curMsg['lrMd']
    curKitSensor1Status = curMsg['kitSensor1']
    curKitSensor2Status = curMsg['kitSensor2']
    curKitMdStatus = curMsg['kitMd']
    curMb1Sensor1Status = curMsg['mb1Sensor1']
    curMb1Sensor2Status = curMsg['mb1Sensor2']
    curMb1MdStatus = curMsg['mb1Md']
    curMb2Sensor1Status = curMsg['mb2Sensor1']
    curMb2Sensor2Status = curMsg['mb2Sensor2']
    curMb2MdStatus = curMsg['mb2Md']

    curMsg = rthermostat['message']
    curMfModeStatus = curMsg['mfMode']
    curMfFanStatus = curMsg['mfFan']
    curMfCurTempStatus = curMsg['mfCurTemp']
    curMfConTempStatus = curMsg['mfConTemp']
    curUpModeStatus = curMsg['upMode']
    curUpFanStatus = curMsg['upFan']
    curUpCurTempStatus = curMsg['upCurTemp']
    curUpConTempStatus = curMsg['upConTemp']
    
    if (prevLrBulb1Status != curLrBulb1Status):
        print("\nlrBulb1 status update: ", curLrBulb1Status)
        prevLrBulb1Status = curLrBulb1Status
    elif (prevLrBulb2Status != curLrBulb2Status):
        print("\nlrBulb2 status update: ", curLrBulb2Status)
        prevLrBulb2Status = curLrBulb2Status
    elif (prevKitBulb1Status != curKitBulb1Status):
        print("\nKitBulb1 status update: ", curKitBulb1Status)
        prevKitBulb1Status = curKitBulb1Status
    elif (prevKitBulb2Status != curKitBulb2Status):
        print("\nKitBulb2 status update: ", curKitBulb2Status)
        prevKitBulb2Status = curKitBulb2Status
    elif (prevMb1Bulb1Status != curMb1Bulb1Status):
        print("\nMb1Bulb1 status update: ", curMb1Bulb1Status)
        prevMb1Bulb1Status = curMb1Bulb1Status
    elif (prevMb1Bulb2Status != curMb1Bulb2Status):
        print("\nMb1Bulb2 status update: ", curMb1Bulb2Status)
        prevMb1Bulb2Status = curMb1Bulb2Status
    elif (prevMb2Bulb1Status != curMb2Bulb1Status):
        print("\nMb2Bulb1 status update: ", curMb2Bulb1Status)
        prevMb2Bulb1Status = curMb2Bulb1Status
    elif (prevMb2Bulb2Status != curMb2Bulb2Status):
        print("\nMb2Bulb2 status update: ", curMb2Bulb2Status)
        prevMb2Bulb2Status = curMb2Bulb2Status
        
    if (prevCar1DoorStatus != curCar1DoorStatus):
        print("\ncar1Door status update:", curCar1DoorStatus)
        prevCar1DoorStatus = curCar1DoorStatus
    elif (prevCar2DoorStatus != curCar2DoorStatus):
        print("\ncar2Door status update:", curCar2DoorStatus)
        prevCar2DoorStatus = curCar2DoorStatus
        
    if (prevFrontDoorStatus != curFrontDoorStatus):
        print("\nFrontDoor status update:", curFrontDoorStatus)
        prevFrontDoorStatus = curFrontDoorStatus
    elif (prevBackDoorStatus != curBackDoorStatus):
        print("\nBackDoor status update:", curBackDoorStatus)
        prevBackDoorStatus = curBackDoorStatus

    if (prevLrSensor1Status != curLrSensor1Status):
        print("\nlrSensor1 status update: ", curLrSensor1Status)
        prevLrSensor1Status = curLrSensor1Status
    elif (prevLrSensor2Status != curLrSensor2Status):
        print("\nlrSensor2 status update: ", curLrSensor2Status)
        prevLrSensor2Status = curLrSensor2Status
    elif (prevLrMdStatus != curLrMdStatus):
        print("\nlrMd status update: ", curLrMdStatus)
        prevLrMdStatus = curLrMdStatus
    elif (prevKitSensor1Status != curKitSensor1Status):
        print("\nKitSensor1 status update: ", curKitSensor1Status)
        prevKitSensor1Status = curKitSensor1Status
    elif (prevKitSensor2Status != curKitSensor2Status):
        print("\nKitSensor2 status update: ", curKitSensor2Status)
        prevKitSensor2Status = curKitSensor2Status
    elif (prevKitMdStatus != curKitMdStatus):
        print("\nkitMd status update: ", curKitMdStatus)
        prevKitMdStatus = curKitMdStatus
    elif (prevMb1Sensor1Status != curMb1Sensor1Status):
        print("\nMb1Sensor1 status update: ", curMb1Sensor1Status)
        prevMb1Sensor1Status = curMb1Sensor1Status
    elif (prevMb1Sensor2Status != curMb1Sensor2Status):
        print("\nMb1Sensor2 status update: ", curMb1Sensor2Status)
        prevMb1Sensor2Status = curMb1Sensor2Status
    elif (prevMb1MdStatus != curMb1MdStatus):
        print("\nmb1Md status update: ", curMb1MdStatus)
        prevMb1MdStatus = curMb1MdStatus
    elif (prevMb2Sensor1Status != curMb2Sensor1Status):
        print("\nMb2Sensor1 status update: ", curMb2Sensor1Status)
        prevMb2Sensor1Status = curMb2Sensor1Status
    elif (prevMb2Sensor2Status != curMb2Sensor2Status):
        print("\nMb2Sensor2 status update: ", curMb2Sensor2Status)
        prevMb2Sensor2Status = curMb2Sensor2Status
    elif (prevMb2MdStatus != curMb2MdStatus):
        print("\nmb2Md status update: ", curMb2MdStatus)
        prevMb2MdStatus = curMb2MdStatus

    if (prevMfModeStatus != curMfModeStatus):
        print("\nmfMode status update: ", curMfModeStatus)
        prevMfModeStatus = curMfModeStatus
    elif (prevMfFanStatus != curMfFanStatus):
        print("\nmfFan status update: ", curMfFanStatus)
        prevMfFanStatus = curMfFanStatus
    elif (prevMfCurTempStatus != curMfCurTempStatus):
        print("\nmfCurTemp status update: ", curMfCurTempStatus)
        prevMfCurTempStatus = curMfCurTempStatus
    elif (prevMfConTempStatus != curMfConTempStatus):
        print("\nmfConTemp status update: ", curMfConTempStatus)
        prevMfConTempStatus = curMfConTempStatus
    elif (prevUpModeStatus != curUpModeStatus):
        print("\nupMode status update: ", curUpModeStatus)
        prevUpModeStatus = curUpModeStatus
    elif (prevUpFanStatus != curUpFanStatus):
        print("\nupFan status update: ", curUpFanStatus)
        prevUpFanStatus = curUpFanStatus
    elif (prevUpCurTempStatus != curUpCurTempStatus):
        print("\nupCurTemp status update: ", curUpCurTempStatus)
        prevUpCurTempStatus = curUpCurTempStatus
    elif (prevUpConTempStatus != curUpConTempStatus):
        print("\nupConTemp status update: ", curUpConTempStatus)
        prevUpConTempStatus = curUpConTempStatus
