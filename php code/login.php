<?php
	$userID=$_POST["UniversityRegNum"];
	$password=$_POST["Password"];
	//$userID="01";
	//$password="passwordj";
	if(isset($userID)&&isset($password))
	{
		$con=mysql_connect("localhost","root","");
		if ($con)
		{
			mysql_select_db("vps",$con);
			$q="select * from users where uID='$userID' AND password='$password'";
			$r=mysql_query($q);
			$row = mysql_fetch_assoc($r);
			if($row)
			{
				$details=implode('|',$row);
				echo "1|$details";
			}
			else
			{
				echo "0";
			}
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