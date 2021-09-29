<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {

    $statement = $connection->prepare("SELECT * FROM available where NOT EXISTS (SELECT * FROM appointment WHERE appointment.id = available.id)");
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(400);
        echo "No appointments available\n";
    } else {
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
        echo "\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
}