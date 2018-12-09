<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$bricks_qty=$_POST['bricks_qty'];
	$brick_new_qty=$_POST['brick_new_qty'];
	$datee=$_POST['datee'];	
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into bricks_kachi(klinid,bricks_qty,brick_new_qty,datee)values((select Id from Klin where klin_name='$klin_name'),'$bricks_qty','$brick_new_qty','$datee')");
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