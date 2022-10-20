<?php
require "DataBase.php";
$db = new DataBase();
$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') 
    {
        if ($db->dbConnect()) 
        {
            $res = $db->getLightStatus("lights");
            if ($res) 
            {
                $response['error']= false;
                $response['message'] = $res;
            } 
            else 
            {
                $response['error']= true;
                $response['message'] = "Failed to read Status of Lights!";
            }
        } 
        else echo "Error: Database connection";
    } 

    echo json_encode($response);
?>