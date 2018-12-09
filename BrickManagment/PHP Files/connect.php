<?php
$servername = "localhost";
$dbname = "brickmanagment";
$usrname = "root";
$pasword = "";
// Create connection
$conn = new mysqli($servername,$usrname, $pasword, $dbname);
// Check connection
if ($conn->connect_error) {
die("Connection failed: " . $conn->connect_error);
}else{
	//echo "hellow";
}
?>
