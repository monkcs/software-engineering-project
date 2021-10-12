<?php

require 'authenticate.php';

function insert($connection, $appointment)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("INSERT INTO appointment VALUES (?, ?, 2, 0)");
    $statement->bind_param("ii", $appointment, $user);
    $statement->execute();
    $statement->close();
}

$statement = $connection->prepare("SELECT * FROM  available 
                                    where datetime >= DATE_ADD(curdate(), INTERVAL 28 DAY)
                                    and available.id NOT IN (SELECT appointment.available FROM appointment)                           
                                    LIMIT 1");
//$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
if ($result->num_rows == 0) {
    http_response_code(400);
    echo "No times available\n";
    exit;
}
else{
    $value = $result->fetch_object();
    $statement->close();

    $id = $value->id;
    echo "ID APPOINTMENT\n";
    echo $id;
    insert($connection, $id);
}
$user = $_POST["ID"];
    $statement = $connection->prepare("SELECT * from appointment where appointment.account = ?");
    $statement->bind_param("i", $user);
    $statement->execute();
    
    $result = $statement->get_result();
    $statement->close();
    if ($result->num_rows == 0) {
        http_response_code(402);
        echo "Failed to book second dose\n";
        exit;
    }