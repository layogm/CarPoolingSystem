<?php

	$con=mysql_connect("localhost","root","");
	if ($con)
	{
		mysql_select_db("vps",$con);
		$r=mysql_query("TRUNCATE TABLE travellers");
		echo "1";
	}
	else  
	{
		echo "0";
	}
	mysql_close($con);

?>