<?php

	$ID=$_POST['UniversityRegNum'];
	$source=$_POST['Source'];
	$time=$_POST['Time'];
	//$ID="04";
	//$source="abc";
	//$time="5";
	if(isset($ID))
	{
		$con=mysql_connect("localhost","root","");
		if($con)
		{
			mysql_select_db("vps",$con);
			$q1="insert into travellers values('$ID','$source','$time','no','','')";
			$r1=mysql_query($q1);
			echo "1";
		}
		else
		{
			echo "0";
		}
		mysql_close($con);
	}
	else
	{
	echo "0";
	}
?>