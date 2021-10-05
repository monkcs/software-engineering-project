<?php

require 'connect.php';

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    header('Content-type: application/json');

    $provider = $_POST["provider"];
    if ($provider == "") {
        http_response_code(400);
        echo "No provider id specified\n";
        exit;
    }

    $statement = $connection->prepare("SELECT * FROM available where NOT EXISTS (SELECT * FROM appointment WHERE appointment.available = available.id) AND available.provider = ?");
    $statement->bind_param("i", $provider);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows > 0) {
        http_response_code(200);
        echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
    } else {
        http_response_code(409);
        echo "No appointments available\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
