<?php
require "connect.php";
header('Content-Type: application/json');
$date =  date('Y-m-d');
$mysqli = new mysqli($server_name,$mysql_username,$mysql_password,$db_name);
$myArray = array();
if ($result = $mysqli->query("select * from album_information where Album_Date ='". $date ."';")) {

    while($row = $result->fetch_array(MYSQLI_ASSOC)) {
            $myArray[] = $row;
    }
    echo json_encode($myArray);
}

$result->close();
$mysqli->close();
?>