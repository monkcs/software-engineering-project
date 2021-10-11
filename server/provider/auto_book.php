<?php

require 'authenticate.php';


function insert($connection, $appointment)
{
    $user = $_POST["ID"];
    $current_dose = $_POST["dose"];
    $statement = $connection->prepare("INSERT INTO appointment VALUES (?, ?, ?, 0)");
    $statement->bind_param("iii", $appointment, $user, $current_dose);
    $statement->execute();
    $statement->close();
}

$statement = $connection->prepare("SELECT * FROM  available 
                                    where datetime >= DATE_ADD(curdate(), INTERVAL 28 DAY)
                                    and available.id NOT IN (SELECT appointment.available FROM appointment)
                                    LIMIT 1");

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
    echo $id;
    insert($connection, $id);
}
