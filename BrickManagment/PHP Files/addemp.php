<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$emp_name=$_POST['emp_name'];
	$emp_age=$_POST['emp_age'];
	$emp_cell=$_POST['emp_cell'];
	$emp_addr=$_POST['emp_addr'];
	$klin_name=$_POST['klin_name'];
	$emp_salary=$_POST['emp_salary'];	
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into employee(emp_name,emp_age,emp_cell,emp_addr,klinid,emp_salary)values('$emp_name','$emp_age','$emp_cell','$emp_addr',(select Id from Klin where klin_name='$klin_name'),'$emp_salary')");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "employee added";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		