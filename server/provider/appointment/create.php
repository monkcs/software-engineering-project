<?php

require 'authenticate.php';


function insert($connection, $datetime, $provider)
{
    $statement = $connection->prepare("INSERT INTO available (datetime, provider) VALUES (?, ?)");
    $statement->bind_param("si", $datetime, $provider);
    $statement->execute();
    $statement->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $datetime = $_POST["datetime"];
    if ($datetime == "") {
        http_response_code(400);
        echo "No datetime specified\n";
        exit;
    }

    insert($connection, $datetime, $identity);
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
