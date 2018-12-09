<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$emp_name=$_POST['emp_name'];
	$a_date=$_POST['a_date'];
	$a_val=$_POST['a_val'];
	$klin_name=$_POST['klin_name'];
	
	if (empty($_POST['emp_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into emp_attendance(emp_id,a_date,a_val,klinid)values((select Id from employee where emp_name='$emp_name'),'$a_date','$a_val',(select Id from Klin where klin_name='$klin_name'))");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "Attendance Marked";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		