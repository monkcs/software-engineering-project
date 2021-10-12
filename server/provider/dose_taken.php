<?php

require 'authenticate.php';
$user = $_POST["ID"];
$current_dose = $_POST["dose"];


function delete_appointment($connection, $user){
    $statement = $connection->prepare("DELETE from appointment where appointment.account = ?");
    $statement->bind_param("i", $user);        
    $statement->execute();
    $statement->close();
}
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

    $date = $value->Date;
    echo $date;
    
}

if($current_dose == 1){
    echo "inserting into dose 1";
    $statement = $connection->prepare("INSERT INTO dose_one VALUES (?, ?)");
    $statement->bind_param("ii", $user, $date);
    $statement->execute();
    $statement->close();

    delete_appointment($connection, $user);
}
else if($current_dose == 2){
    echo "inserting into dose 2";
    $statement = $connection->prepare("INSERT INTO dose_two VALUES (?, ?)");
    $statement->bind_param("ii", $user, $date);
    $statement->execute();
    $statement->close();
    delete_appointment($connection, $user);
}
else{
    http_response_code(401);
    echo "No dose taken\n";
}

