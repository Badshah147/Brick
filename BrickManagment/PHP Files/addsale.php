<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$s_brick_qty=$_POST['s_brick_qty'];
	$s_price=$_POST['s_price'];
	$s_date=$_POST['s_date'];	
	$s_by=$_POST['s_by'];	
	$s_to=$_POST['s_to'];	
	$klin_name=$_POST['klin_name'];	
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 
	mysqli_query($conn,"insert into salesorder(s_brick_qty,s_price,s_date,s_by,s_to,klinid)values('$s_brick_qty','$s_price','$s_date','$s_by','$s_to',(select Id from Klin where klin_name='$klin_name'))");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "Item Added";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		