<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $fullname, $email, $username, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function updateLights($table, $lrBulb1, $lrBulb2, $kitBulb1, $kitBulb2, $mb1Bulb1, $mb1Bulb2, $mb2Bulb1, $mb2Bulb2)
    {
        $lrBulb1 = $this->prepareData($lrBulb1);
        $lrBulb2 = $this->prepareData($lrBulb2);
        $kitBulb1 = $this->prepareData($kitBulb1);
        $kitBulb2 = $this->prepareData($kitBulb2);
        $mb1Bulb1 = $this->prepareData($mb1Bulb1);
        $mb1Bulb2 = $this->prepareData($mb1Bulb2);
        $mb2Bulb1 = $this->prepareData($mb2Bulb1);
        $mb2Bulb2 = $this->prepareData($mb2Bulb2);

        $this->sql =
            "INSERT INTO " . $table . " (lrBulb1, lrBulb2, kitBulb1, kitBulb2, mb1Bulb1, mb1Bulb2, mb2Bulb1, mb2Bulb2) VALUES ('" . $lrBulb1 . "','" . $lrBulb2 . "','" . $kitBulb1 . "','" . $kitBulb2 . "','" . $mb1Bulb1 . "','" . $mb1Bulb2 . "','" . $mb2Bulb1 . "','" . $mb2Bulb2 . "')";
        
            if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } 
        else 
            return false;
    }

    // function getLightStatus($table)
    // {
    //     $this->sql = "select * from " . $table;
    //     $result = mysqli_query($this->connect, $this->sql);
    //     $row = mysqli_fetch_assoc($result);
    //     return $row;
    // }

}

?>
