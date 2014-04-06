<?php
	$name = $_POST["Name"];
	$gender = $_POST["Gender"];
	$category = $_POST["Category"];
	$email = $_POST["Email"];
	$genderP = $_POST["GenderPrefs"];
	$phoneno = $_POST["PhoneNumber"];
	$userID=$_POST["UniversityRegNum"];
	$password=$_POST["Password"];
	$dsource=$_POST["DefaultSource"];
	$ddest=$_POST["DefaultDestination"];
	$securityq=$_POST["SecQues"];
	$securitya=$_POST["SecAns"];
	$vno1=$_POST["VehicleNumber1"];
	$vno2=$_POST["VehicleNumber2"];
	$vno3=$_POST["VehicleNumber3"];
	$vno= $vno1."|".$vno2."|".$vno3;
	$vmil1=$_POST["VehicleMileage1"];
	$vmil2=$_POST["VehicleMileage2"];
	$vmil3=$_POST["VehicleMileage3"];
	$vmil=$vmil1."|".$vmil2."|".$vmil3;
	//vinfo
	if(isset($userID)&&isset($password))
	{
		$con=mysql_connect("localhost","root","");
		
		if($con)
		{
			mysql_select_db("vps",$con);
			//same university reg no
			$q = "select uID from users where uID = '$userID'";
			$r = mysql_query($q);
			$row = mysql_fetch_assoc($r);
			if($row)
			{
				echo "2";
				return;
			}
			//same phone no
			$q = "select phone from users where phone = '$phoneno'";
			$r = mysql_query($q);
			$row = mysql_fetch_assoc($r);
			if($row)
			{
				echo "3";
				return;
			}
			//same email id
			$q = "select email from users where email = '$email'";
			$r = mysql_query($q);
			$row = mysql_fetch_assoc($r);
			if($row)
			{
				echo "4";
				return;
			}
			//save values in the database
			$q = "insert into users values('$userID','$name','$gender','$category','$email','$genderP','$phoneno','$password','$dsource','$ddest','$securityq','$securitya','$vno','$vmil')";
			$r = mysql_query($q);
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