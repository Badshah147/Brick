<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$klin_name=$_POST['klin_name'];
	$bricks_qty=$_POST['bricks_qty'];
	$datee=$_POST['datee'];	
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"update bricks_kachi set bricks_qty='$bricks_qty',datee='$datee' where klinid=(select Id from Klin where klin_name='$klin_name')");
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