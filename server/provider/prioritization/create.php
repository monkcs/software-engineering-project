<?php

require 'authenticate.php';

function insert($connection, $identity, $not_younger, $forthcoming)
{
    $statement = $connection->prepare("INSERT INTO prioritizations (provider, not_younger, forthcoming) VALUES(?, ?, ?)");
    $statement->bind_param("iis", $identity, $not_younger, $forthcoming);
    $statement->execute();
    $statement->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $not_younger = $_POST["not_younger"];
    $forthcoming = $_POST["forthcoming"];

    if ($not_younger == "") {
        http_response_code(400);
        echo "No not_younger age (in years) specified\n";
        exit;
    }
    if ($forthcoming == "") {
        http_response_code(400);
        echo "No forthcoming date specified\n";
        exit;
    }

    insert($connection, $identity, $not_younger, $forthcoming);
    http_response_code(204);
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
    exit;
}
