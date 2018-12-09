<?php 
	include 'connect.php';
	header('Content-type: application/json');
	
	$user_mobile=$_POST['user_mobile'];
	$user_pass=$_POST['user_pass'];
	
	if (empty($_POST['user_mobile']) || empty($_POST['user_pass'])) {    
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 
	$query="select * from user where user_mobile='$user_mobile' AND user_pass='$user_pass'";
 if(mysqli_query($conn,$query)){
	 $result = $conn->query($query);
if ($result->num_rows > 0) {
	$response["success"] = 1; 
	$response["message"] = "You have been successfully Login";
	}else{
	$response["success"] = 0; 
	$response["message"] = "Invalid Credentials";
	}
}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		