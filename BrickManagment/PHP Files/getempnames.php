<?php 
	include 'connect.php';
	header('Content-type: application/json');
	
	$klin_name=$_POST['klin_name'];
	
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT emp_name from employee where klinid=(select Id from Klin where klin_name='$klin_name')");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($emp_name);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['emp_name'] = $emp_name; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>