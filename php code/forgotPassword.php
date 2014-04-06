<?php
	//$uID=$_POST['UniversityRegNum'];
	$uid="01";
	
	if(isset($uid))
	{
		$con=mysql_connect("localhost","root","");
		if($con)
		{
			mysql_select_db("vps",$con);
			$q="select SecurityQ, SecurityA, password from users where uID='$uid'";
			$r=mysql_query($q);
			$row=mysql_fetch_assoc($r);
			$row=implode("|",$row);
			if($row=="")
			{
				echo "NIL";
			}
			else
			{
				echo "1|$row";
			}
		}
		else
		{
			echo "0";
		}
		mysql_close($con);
	}
?>