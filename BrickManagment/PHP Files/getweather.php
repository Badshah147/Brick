<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT Id,w_value,w_degree,loc_name,w_day,klinid from weather_rep where klinid=(select Id from Klin where klin_name='$klin_name')");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Id,$w_value,$w_degree,$loc_name,$w_day,$klinid);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['Id'] = $Id; 
  $temp['w_value'] = $w_value; 
   $temp['w_degree'] = $w_degree; 
    $temp['loc_name'] = $loc_name; 
	 $temp['w_day'] = $w_day; 
	  $temp['klinid'] = $klinid; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>