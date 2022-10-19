<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['lrBulb1']) && isset($_POST['lrBulb2']) && isset($_POST['kitBulb1']) && isset($_POST['kitBulb2']) && 
    isset($_POST['mb1Bulb1']) && isset($_POST['mb1Bulb2']) && isset($_POST['mb2Bulb1']) && isset($_POST['mb2Bulb2'])) 
    {
    if ($db->dbConnect()) 
    {
        if ($db->updateLights("lights", $_POST['lrBulb1'], $_POST['lrBulb2'], $_POST['kitBulb1'], $_POST['kitBulb2'], 
        $_POST['mb1Bulb1'], $_POST['mb1Bulb2'], $_POST['mb2Bulb1'], $_POST['mb2Bulb2'])) 
        {
            echo "Lights Status Update Success";
        } 
        else echo "Lights Status Update Failed";
    } 
    else echo "Error: Database connection";
} 
else echo "All fields are required";
?>