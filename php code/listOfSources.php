<?php
	$con=mysql_connect("localhost","root","");
	if ($con)
	{
		mysql_select_db("vps",$con);
		$q="select * from source_details";
		$r=mysql_query($q);
		$list="";
		while($row = mysql_fetch_assoc($r)) 
		{
			$row=implode('|',$row);
			$list=$list.$row."|";
		}
		echo "$list";
	}
	mysql_close($con);
?>