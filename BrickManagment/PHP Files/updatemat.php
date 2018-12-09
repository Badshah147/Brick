<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$mat_name=$_POST['mat_name'];
	$klin_name=$_POST['klin_name'];
	$mat_type=$_POST['mat_type'];
	$qty_perc=$_POST['qty_perc'];

	if (empty($_POST['mat_type'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"update rawmaterial set mat_type='$mat_type',qty_perc='$qty_perc' where mat_name='$mat_name' AND klinid=(select Id from Klin where klin_name='$klin_name')");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "material updated";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		