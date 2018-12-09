<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$p_date=$_POST['p_date'];
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT Id,raw_mat_name,p_date,p_price,p_current_qty,klinid,p_by from purchaseorder where klinid=(select Id from klin where klin_name='$klin_name') AND p_date='$p_date'");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Id,$raw_mat_name,$p_date,$p_price,$p_current_qty,$klinid,$p_by);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['Id'] = $Id; 
  $temp['raw_mat_name'] = $raw_mat_name; 
   $temp['p_date'] = $p_date; 
    $temp['p_price'] = $p_price; 
	 $temp['p_current_qty'] = $p_current_qty; 
	  $temp['klinid'] = $klinid; 
	   $temp['p_by'] = $p_by; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>