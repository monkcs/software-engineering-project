<?php


require 'authenticate.php';

$statement = $connection->prepare("SELECT available.datetime AS Date from available
            inner join appointment
            on available.id = appointment.available
            inner join account
            on appointment.account = account.id
            and account.id = ?
            and available.datetime is not null");


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
    $dt = strtotime($date);
    $date_1 = date("Y-m-d", $dt);

    $date1 = date_create($date_1);
    $date_2 = date("Y-m-d");

    $date2 = date_create($date_2);
    $difference = date_diff($date1,$date2);

    $diff = $difference->format("%R%a days");
    if($diff >= 14)
    {
        echo "true";
    }
    else 
    {
        echo "false";
    }
}   

