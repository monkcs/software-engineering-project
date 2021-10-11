<?php


require 'authenticate.php';
function dosage($connection, $identity)
{
    $second = $connection->prepare("SELECT * FROM dose_two WHERE dose_two.person = ?");
    $second->bind_param("i", $identity);
    $second->execute();
    $result_second = $second->get_result();
    $second->close();

    if ($result_second->num_rows == 0) {

        $first = $connection->prepare("SELECT * FROM dose_one WHERE dose_one.person = ?");
        $first->bind_param("i", $identity);
        $first->execute();
        $result_first = $first->get_result();
        $first->close();

        if ($result_first->num_rows == 0) {
            return 1;
        } else {
            return 0;
        }
    } else {
        return 2;
    }
}
$statement = $connection->prepare("SELECT appointment.available as Date from  appointment
                where appointment.account = ?"); 

$statement->bind_param("i", $identity);        
$statement->execute();
$result = $statement->get_result();
if ($result->num_rows == 0) {
    http_response_code(400);
    echo "No appointment booked\n";
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

    //$statement1 = $connection->prepare("DELETE from appointment where appointment.accunt = ?");
}
else if($current_dose == 2){
    echo "inserting into dose 2";
    $statement = $connection->prepare("INSERT INTO dose_two VALUES (?, ?)");
    $statement->bind_param("ii", $identity, $date);
    $statement->execute();
    $statement->close();
}
else{
    http_response_code(401);
    echo "No dose taken\n";
}

