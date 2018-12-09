<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$user_mobile=$_POST['user_mobile'];

	
	if (empty($_POST['user_mobile'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"update user set user_valid='1' where user_mobile='$user_mobile'");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "User Valid Now";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		