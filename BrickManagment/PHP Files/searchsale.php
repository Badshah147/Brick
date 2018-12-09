<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$s_date=$_POST['s_date'];
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT Id,s_brick_qty,s_price,s_date,s_by,s_to,klinid from salesorder where klinid=(select Id from Klin where klin_name='$klin_name') AND s_date='$s_date'");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Id,$s_brick_qty,$s_price,$s_date,$s_by,$s_to,$klinid);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['Id'] = $Id; 
  $temp['s_brick_qty'] = $s_brick_qty; 
   $temp['s_price'] = $s_price; 
    $temp['s_date'] = $s_date; 
	 $temp['s_by'] = $s_by; 
	  $temp['s_to'] = $s_to; 
	   $temp['klinid'] = $klinid; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>