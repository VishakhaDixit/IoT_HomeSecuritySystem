<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['car1Door']) && isset($_POST['car2Door'])) 
    {
    if ($db->dbConnect()) 
    {
        if ($db->updateGrDoors("garagedoors", $_POST['car1Door'], $_POST['car2Door'])) 
        {
            echo "Garage Door Status Update Success";
        } 
        else echo "Garage Door Status Update Failed";
    } 
    else echo "Error: Database connection";
} 
else echo "All fields are required";
?>