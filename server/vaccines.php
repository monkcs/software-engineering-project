<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {

    $statement = $connection->prepare("SELECT * FROM vaccines");
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(400);
        echo "No vaccines available\n";
    } else {
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
        echo "\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
}