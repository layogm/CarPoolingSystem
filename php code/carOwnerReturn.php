<?php

	$vOwnerID=$_POST["carOwner"];
	$travellerID=$_POST["travellers"];
	$fares=$_POST["fares"];
	$carno=$_POST["carno"];
	$travellerID=explode("|",$travellerID);
	$fare=explode("|",$fare);
	$i=0;
	$con=mysql_connect("localhost","root","");
	if($con)
	{
		mysql_select_db("vps",$con);
		foreach ($travellerID as $traveller)
		{
			$q="select vehicleOwnerID from travellers where tID='$traveller'";
			$r=mysql_query($q);
			$row=mysql_fetch_row($r);
			if($row)
			{
				$q1="insert into travellers(vehicleOwnerID,moneyShare,vehiclenumber) values('$vOwnerID','$fares[i]','$carno')";
				$r1=mysql_query($q1);
			}
			$i++;
		}
	}
	else
	{
		echo "0";
	}
?>