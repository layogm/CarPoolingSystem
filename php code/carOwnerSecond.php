<?php

	$vID=$_POST["UniversityRegNum"];
	if(isset($vID))
	{
		$con=mysql_connect("localhost","root","");
		if($con)
		{
			mysql_select_db("vps",$con);
			$q="select tID, name, phone, email, time, travellers.source, distance, moneyShare 
				from users, travellers, source_details
				where vehicleOwnerID='$vID' AND travellers.source=source_details.location AND travellers.tID=users.uId";
			$r=mysql_query($q);
			$string="";
			while($row=mysql_fetch_assoc($r))
			{
				$row=implode("|",$row);
				$string=$string.$row."|";
			}
			
			if($string="")
			{
				echo "NIL";
			}
			else
			{
				echo "1|$string";
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