<?php

	$tID=$_POST["UniversityRegNum"];
	if(isset($tID))
	{
		$con=mysql_connect("localhost","root","");
		if($con)
		{
			mysql_select_db("vps",$con);
			$q="select vehicleOwnerID, name, phone, email, vehicleNumber, moneyShare 
				from users, travellers
				where tID='$tID' AND travellers.vehicleOwnerID=users.uId";
			$r=mysql_query($q);
			$row=mysql_fetch_assoc($r);
			$row=implode("|",$row);
			
			if($row="")
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
	else
	{
		echo "0";
	}

?>