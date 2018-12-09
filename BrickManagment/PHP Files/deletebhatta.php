<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$user_mobile=$_POST['user_mobile'];
	
	
	if (empty($_POST['user_mobile'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"delete from Klin where klin_name='$klin_name' AND userId=(select Id from user where user_mobile='$user_mobile')");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "Bhatta deleted";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		