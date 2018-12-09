<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$emp_name=$_POST['emp_name'];
	
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }

 $stmt = $conn->prepare("SELECT Id,emp_name,emp_age,emp_cell,emp_addr,klinid,emp_Salary from employee where klinid=(select Id from klin where klin_name='$klin_name') AND emp_name LIKE '$emp_name'%");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Id,$emp_name,$emp_age,$emp_cell,$emp_addr,$klinid,$emp_Salary);
 $products = array(); 

 //traversing through all the result 
 while($stmt->fetch()){
  $temp = array();
 $temp['Id'] = $Id; 
  $temp['emp_name'] = $emp_name; 
   $temp['emp_age'] = $emp_age; 
    $temp['emp_cell'] = $emp_cell; 
	 $temp['emp_addr'] = $emp_addr; 
	  $temp['klinid'] = $klinid; 
	   $temp['emp_Salary'] = $emp_Salary; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
  header('Content-type:application/json;charset=utf-8');

 echo json_encode($products);
 mysqli_close();
 ?>