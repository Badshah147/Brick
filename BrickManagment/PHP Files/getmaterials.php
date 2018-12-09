<?php 
	include 'connect.php';
	header('Content-type: application/json');
	
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT Id,mat_name,mat_type,klinid FROM rawmaterial");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Id,$mat_name,$mat_type,$klinid);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['Id'] = $Id; 
 $temp['mat_name'] = $mat_name; 
 $temp['mat_type'] = $mat_type; 
 $temp['klinid'] = $klinid; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>