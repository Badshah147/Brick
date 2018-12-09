<?php 
	include 'connect.php';
	header('Content-type: application/json');
	$mat_name=$_POST['mat_name'];
	$mat_type=$_POST['mat_type'];
	$klin_name=$_POST['klin_name'];
	$qty_perc=$_POST['qty_perc'];
	
	if (empty($_POST['klin_name'])) {  
 
	$response["success"] = 0; 
	$response["message"] = "One or more of the fields are empty ."; 
 
	die(json_encode($response)); 
	} 

	mysqli_query($conn,"insert into rawmaterial(mat_name,mat_type,klinid,qty_perc)values('$mat_name','$mat_type',(select Id from Klin where klin_name='$klin_name'),'$qty_perc')");
	$result=$conn->affected_rows;
 if ($result>=1){
	$response["success"] = 1; 
	$response["message"] = "material added";
	}else{
		$response["success"] = 0; 
		$response["message"] = "Error..!! Try Again";
	}
	die(json_encode($response)); 
 
	mysql_close(); 
	
	?>		