<?php

require 'authenticate.php';


function update($connection, $minimum_age, $id)
{
    $statement = $connection->prepare("UPDATE available SET minimum_age=? WHERE available.id=?");
    $statement->bind_param("ii", $minimum_age, $id);
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
        $id = $row['id'];
        $minimum_age = $row['minimum_age'];

        update($connection, $minimum_age, $id);
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
