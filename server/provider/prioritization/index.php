<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    header('Content-type: application/json');

    $statement = $connection->prepare("SELECT * FROM prioritizations WHERE prioritizations.provider = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
    echo "\n";
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
