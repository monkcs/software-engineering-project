<?php 

require 'init.php';

$user_name=$_POST["username"]; 
$user_password =$_POST["password"]; 
if (!empty($_POST)) 
{   
 
    if (empty($_POST['username']) || empty($_POST['password'])) 
    { // Create some data that will be the JSON response 
        $response["success"] = 0; 
        $response["message"] = "One or both of the fields are empty ."; 
        die(json_encode($response)); 
    }
    $sql =  "SELECT * FROM login WHERE login.email = '$user_name' and login.passwrd = '$user_password'";
           
    $result = mysqli_query($con, $sql);
     
    if (mysqli_fetch_row($result) > 0) 
    { 
        $response["success"] = 1; 
        $response["message"] = "You have been sucessfully login"; 
        die(json_encode($response));
    }
    else{
        $response["success"] = 0; 
        $response["message"] = "invalid username or password, $user_name, $user_password "; 
        die(json_encode($response));
    }   
}
else{
    $response["success"] = 0; 
        $response["message"] = "One or both of the fields are empty ."; 
        die(json_encode($response)); 
}

mysqli_close();
?>
