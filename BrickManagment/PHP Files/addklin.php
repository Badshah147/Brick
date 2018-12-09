<?php 
	include 'connect.php';
	header('Content-type: application/json');
	
	$klin_name=$_POST['klin_name'];
	$user_mobile=$_POST['user_mobile'];
	$klin_addr=$_POST['klin_addr'];
	
	
	if (empty($_POST['user_mobile'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into Klin(klin_name,userId,klin_addr)values('$klin_name',(select Id from user where user_mobile='$user_mobile'),'$klin_addr')");
	
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "Klin added";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		