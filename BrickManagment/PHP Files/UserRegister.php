<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$username=$_POST['username'];
	$user_mobile=$_POST['user_mobile'];
	$user_pass=$_POST['user_pass'];
	
	if (empty($_POST['username'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into user(username,user_mobile,user_pass,user_type,user_valid)values('$username','$user_mobile','$user_pass','user','0')");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "User Data Inserted";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		