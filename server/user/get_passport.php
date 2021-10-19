<?php

require 'authenticate.php';

function send_values($connection, $identity)
{
    $statement = $connection->prepare("SELECT person.account, person.firstname, person.surname, person.birthdate, available.datetime as Date, vaccines.name, qrcode from passport
                                        inner join person
                                        on person.account = passport.person
                                        and person.account = ?
                                        inner join available
                                        on available.id = passport.dose_date
                                        inner join vaccines
                                        on vaccines.id = passport.vaccine 
                                        LIMIT 1");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result1 = $statement->get_result();
    $statement->close();
    if ($result1->num_rows == 0) {
        http_response_code(400);
        echo "No passport available\n";
    }
    else{
        http_response_code(200); 
        echo json_encode($result1->fetch_array(MYSQLI_ASSOC));
        echo "\n";
    }
}
$statement = $connection->prepare("SELECT available.datetime as Date from available
                                        inner join passport
                                        on available.id = passport.dose_date
                                        and passport.person = ?");    
$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();                                           
if ($result->num_rows == 0) {
    http_response_code(400);
    echo "Second dose has not been taken\n";
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
        send_values($connection, $identity); 
    }
    else 
    {
        echo "false";
    }
}   


 