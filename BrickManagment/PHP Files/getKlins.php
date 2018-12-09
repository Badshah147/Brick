<?php 
	include 'connect.php';
	header('Content-type: application/json');
	
	$user_mobile=$_POST['user_mobile'];
	
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT klin_name from Klin where userId=(select Id from user where user_mobile='$user_mobile')");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($klin_name);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['klin_name'] = $klin_name; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>