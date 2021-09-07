<?php
$user_forename = $_POST["forename"];
$user_surname = $_POST["surname"];
$user_email = $_POST["e_mail"];
$user_password = $_POST["password"];
$user_number = $_POST["phone_number"];
$user_dateofbirth = $_POST["dateOfbirth"];
$user_street = $_POST["street"];
$user_zipcode = $_POST["zipcode"];
$user_city = $_POST["city"];
$user_country = $_POST["country"];

require 'init.php';
$response = array("error" => FALSE);

if($con)
{
    $sql = "select * from login where and login.email = '$user_email'";
    $result = mysqli_query($con, $sql);
    // check if user exists
     if(mysqli_fetch_row($result)>0)
        {
            echo "user already exists";
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed with " . $user_email;
            echo json_encode($response);
     else{
     $sql1 = "insert into person
            values('$user_forename', '$user_surname', '$user_number', '$user_dateofbirth', '$user_street', '$user_zipcode', '$user_city', '$user_country')";

     mysqli_query($con, $sql1);
     //check if success
     $sql2 = "select forename from person where person.MobilNr == '$user_number'";
     $result2 = mysqli_query($con, $sql2);
     $row = mysqli_fetch_assoc($result2);
     if($row != $user_forename)
     {
       $response["error"] = TRUE;
       $response["error_msg"] = "User not registerd";
       echo json_encode($response);
     }
     else{
         $sql2 = "insert into login (email, passwrd)
                     values('$user_email', '$user_password')";
         $sql3 = "update login set login.person = person.PID from login inner join person on person.MobilNr == '$user_number'";
                }}
mysqli_close($con);

?>