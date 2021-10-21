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

    $datetime = $_POST["datetime"];
    $minimum_age = $_POST["minimum_age"];
    if ($datetime == "" || $minimum_age == "") {
        http_response_code(400);
        echo "No datetime specified\n";
        exit;
    }

    insert($connection, $datetime, $identity, $minimum_age);
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
