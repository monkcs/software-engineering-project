<?php


require 'connect.php';
$identity = $_POST["ID"];
$current_dose = $_POST["dose"];


function delete_appointment(){
    $statement = $connection->prepare("DELETE from appointment where appointment.accunt = ?");
    $statement->bind_param("i", $identity);        
    $statement->execute();
    $statement->close();
}
$statement = $connection->prepare("SELECT appointment.available as Date from  appointment
                where appointment.account = ?"); 

$statement->bind_param("i", $identity);        
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
$current_dose = dosage($connection, $identity);
if($current_dose == 1){
    echo "inserting into dose 1";
    $statement = $connection->prepare("INSERT INTO dose_one VALUES (?, ?)");
    $statement->bind_param("ii", $identity, $date);
    $statement->execute();
    $statement->close();

    delete_appointment();
}
else if($current_dose == 2){
    echo "inserting into dose 2";
    $statement = $connection->prepare("INSERT INTO dose_two VALUES (?, ?)");
    $statement->bind_param("ii", $identity, $date);
    $statement->execute();
    $statement->close();
    delete_appointment();
}
else{
    http_response_code(401);
    echo "No dose taken\n";
}

