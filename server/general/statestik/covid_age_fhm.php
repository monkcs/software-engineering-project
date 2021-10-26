<?php

if ($_SERVER['REQUEST_METHOD'] == "GET") {

    header('Content-type: application/json');

    require 'connect.php';

    $statement = $connection->prepare("SELECT * FROM covid_age_fhm");
    $statement->execute();
    
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows > 0) {
        http_response_code(200);
        echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
        echo "\n";
    } else {
        http_response_code(409);
        echo "cant exces the data\n";
    }

} else {
    http_response_code(405);
    echo "Send request using HTTP GET\n";
}