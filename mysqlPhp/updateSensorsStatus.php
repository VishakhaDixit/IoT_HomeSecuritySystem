<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['lrSensor1']) && isset($_POST['lrSensor2']) && isset($_POST['lrMd']) && isset($_POST['kitSensor1']) && isset($_POST['kitSensor2']) && isset($_POST['kitMd']) && 
    isset($_POST['mb1Sensor1']) && isset($_POST['mb1Sensor2']) && isset($_POST['mb1Md']) && isset($_POST['mb2Sensor1']) && isset($_POST['mb2Sensor2']) && isset($_POST['mb2Md'])) 
    {
    if ($db->dbConnect()) 
    {
        if ($db->updateSensors("sensors", $_POST['lrSensor1'], $_POST['lrSensor2'], $_POST['lrMd'], $_POST['kitSensor1'], $_POST['kitSensor2'], $_POST['kitMd'],
        $_POST['mb1Sensor1'], $_POST['mb1Sensor2'], $_POST['mb1Md'], $_POST['mb2Sensor1'], $_POST['mb2Sensor2'], $_POST['mb2Md'])) 
        {
            echo "Sensors Status Update Success";
        } 
        else echo "Sensors Status Update Failed";
    } 
    else echo "Error: Database connection";
} 
else echo "All fields are required";
?>