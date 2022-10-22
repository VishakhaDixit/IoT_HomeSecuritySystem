<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['mfMode']) && isset($_POST['mfFan']) && isset($_POST['mfCurTemp']) && isset($_POST['mfConTemp']) && isset($_POST['upMode']) && isset($_POST['upFan']) && 
    isset($_POST['upCurTemp']) && isset($_POST['upConTemp'])) 
    {
    if ($db->dbConnect()) 
    {
        if ($db->updateThermostat("thermostat", $_POST['mfMode'], $_POST['mfFan'], $_POST['mfCurTemp'], $_POST['mfConTemp'], 
                                    $_POST['upMode'], $_POST['upFan'], $_POST['upCurTemp'], $_POST['upConTemp'])) 
        {
            echo "Thermostat Status Update Success";
        } 
        else echo "Thermostat Status Update Failed";
    } 
    else echo "Error: Database connection";
} 
else echo "All fields are required";
?>