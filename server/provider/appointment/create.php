<?php

require 'authenticate.php';


function insert($connection, $datetime, $provider, $minimum_age)
{
    $statement = $connection->prepare("INSERT INTO available (datetime, provider, minimum_age) VALUES (?, ?, ?)");
    $statement->bind_param("sii", $datetime, $provider, $minimum_age);
    $statement->execute();
    $statement->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $body = file_get_contents('php://input');

    $array = json_decode($body, true);

    if ($array == null) {
        http_response_code(400);
        echo "No json array submitted\n";
        exit;
    }

    foreach ($array as $row) {
        $datetime = $row['datetime'];
        $minimum_age = $row['minimum_age'];

        insert($connection, $datetime, $identity, $minimum_age);
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
