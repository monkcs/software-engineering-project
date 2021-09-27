<?php

require 'connect.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    header('Content-type: application/json');

    $statement = $connection->prepare("SELECT id, name FROM provider");
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();


    echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
