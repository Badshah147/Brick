<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$raw_mat_name=$_POST['raw_mat_name'];
	$p_date=$_POST['p_date'];
	$p_price=$_POST['p_price'];	
	$p_current_qty=$_POST['p_current_qty'];	
	$klin_name=$_POST['klin_name'];	
	$p_by=$_POST['p_by'];	
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 
	mysqli_query($conn,"insert into purchaseorder(raw_mat_name,p_date,p_price,p_current_qty,klinid,p_by)values('$raw_mat_name','$p_date','$p_price','$p_current_qty',(select Id from Klin where klin_name='$klin_name'),'$p_by')");
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