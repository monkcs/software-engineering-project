<?php

require 'authenticate.php';

function randomstring(){
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWZYZ?-_!@';
    $length = 25;
    $random_string = '';
    for($i = 0; $i < $length; $i++)
    {
        $random_string .= $characters[rand(0, strlen($characters) - 1)];
    }
    //echo $random_string;
    return $random_string; 
}
function dose2_date($connection, $user)
{
    $statement = $connection->prepare("SELECT appointment.available as Date from  appointment
                where appointment.account = ?"); 
    $statement->bind_param("i", $user);        
    $statement->execute();
    $result = $statement->get_result();
    if ($result->num_rows == 0) {
        http_response_code(400);
        echo "No appointment booked\n";
        exit;
    }
    else{
        $value = $result->fetch_object();
        $statement->close();
    
        $date1 = $value->Date;
        return $date1;
    }            
}
function insert($random, $connection){
    $user = $_POST["ID"];
    $date = dose2_date($connection, $user);
    $statement = $connection->prepare("INSERT INTO passport(person, dose2, qrcode) VALUES (?, ?, ?)");
    $statement->bind_param("iis", $user, $date, $random);
    $statement->execute();
    $statement->close();
}
function check_if_uniqe($connection)
{
    $random = randomstring();
    $statement = $connection->prepare("SELECT * from passport where passport.qrcode = ?");
    $statement->bind_param("s", $random);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        insert($random, $connection);
        exit;
    } else {
        check_if_uniqe();    
    }
}
//if ($_SERVER['REQUEST_METHOD'] == "POST"){
    
    check_if_uniqe($connection);
    
    
/*}else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}*/
