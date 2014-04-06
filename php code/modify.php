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
	if(isset($userID))
	{
		$con=mysql_connect("localhost","root","");
		if($con)
		{
			mysql_select_db("vps",$con);
			$q="UPDATE users SET uID = '$userID', name = '$name', gender = '$gender', category = '$category', email = '$email', genderPreference = '$genderP', phone = '$phoneno', password = '$password', source = '$dsource', destination = '$ddest', securityQ = '$securityq', securityA = '$securitya', vehicle_no = '$vno', vehicle_mileage = '$vmil' WHERE uID='$userID'";
			$r=mysql_query($q);
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