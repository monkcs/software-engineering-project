<?php

require 'authenticate.php';

function delete($connection, $available)
{
    $statement = $connection->prepare("DELETE FROM appointment WHERE appointment.available = ?");
    $statement->bind_param("i", $available);
    $statement->execute();
    $statement->close();

    $statement = $connection->prepare("DELETE FROM available WHERE available.id = ?");
    $statement->bind_param("i", $available);
    $statement->execute();
    $statement->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $available = $_POST["available"];
    if ($available == "") {
        http_response_code(400);
        echo "No available-id specified\n";
        exit;
    }

    delete($connection, $available);
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
