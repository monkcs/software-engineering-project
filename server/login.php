<?php
$user_name = $_POST["e_mail"];
$user_password = $_POST["password"];
require 'init.php';

if($con)
{
    $sql = "select forename, surname from person inner join login
            on login.person = person.PID
            and login.email = '$user_name'
            and login.passwrd = '$user_password'";

    $result = mysqli_query($con, $sql);

    if(mysqli_fetch_row($result)>0)
    {
        echo "login succesfull";
        $row = mysqli_fetch_assoc($result);
        $status = "ok";
        $result_code = 1;
        $name = $row['forename', 'surname'];
        echo json_encode(array('status'=>$status, 'result_code'=>$result_code, 'name'=>$name));
    }
    else
    {
        $status = "ok";
        $result_code = 0;
        echo json_encode(array('status'=>$status, 'result_code'=>$result_code));
    }
}
else
{
    $status = "failed";
    echo json_encode(array('status'=>$status), JSON_FORCE_OBJECT());
}
mysqli_close($con);


?>