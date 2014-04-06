<?php

	function calculateDist($loc)
	{
		$q="select distance from source_details where location='$loc' ";
		$r=mysql_query($q);
		$distance=mysql_fetch_row($r);
		return $distance[0];
	}
	
	function findGender ($id)
	{
		$q="select gender from users where uID='$id'";
		$r=mysql_query($q);
		$gender=mysql_fetch_row($r);
		return $gender[0];
	}

	$ID=$_POST['UniversityRegNum'];
	$source=$_POST['source'];
	$time=$_POST['time'];
	$genpref=$_POST['GenderPrefs'];
	//$ID="01";
	//$source="AIIMS";
	//$genpref="All from same gender";
	//$time="6:00:00";
	if(isset($ID))
	{
		$con=mysql_connect("localhost","root","");
		if ($con)
		{
			mysql_select_db("vps",$con);
			$dist=calculateDist($source);
			$listOfTravellers="";
			$q="SELECT tID,name,gender,genderPreference,travellers.source,distance,time
				FROM travellers, users, source_details 
				WHERE source_details.distance <= $dist AND vehicleOwnerID='No' AND travellers.time > '$time' AND source_details.location=travellers.source AND users.uID=travellers.tID";
			$r=mysql_query($q);
			$gender=findGender($ID);
			if($genpref=="All from same gender")
			{
				while($row=mysql_fetch_assoc($r))
				{
					if($row['gender']==$gender)
					{
						$row=implode('|',$row);
						$listOfTravellers=$listOfTravellers.$row."|";
					}
				}
			}
			else
			{
				while($row=mysql_fetch_assoc($r))
				{
					if($row['genderPreference']=="All from same gender")
					{
						if($row['gender']==$gender)
						{
							$row=implode('|',$row);
							$listOfTravellers=$listOfTravellers.$row."|";
						}
					}
					else
					{
						$row=implode('|',$row);
						$listOfTravellers=$listOfTravellers.$row."|";
					}
				}
			}			
			
			if($listOfTravellers=="")
			{
				echo "2";
			}
			else
			{
				echo "1|$listOfTravellers";
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