<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['frontDoor']) && isset($_POST['backDoor']) && isset($_POST['garageDoor'])) 
    {
    if ($db->dbConnect()) 
    {
        if ($db->updateLocks("locks", $_POST['frontDoor'], $_POST['backDoor'], $_POST['garageDoor'])) 
        {
            echo "Locks Status Update Success";
        } 
        else echo "Locks Status Update Failed";
    } 
    else echo "Error: Database connection";
} 
else echo "All fields are required";
?>