<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $prioritization = $_POST["prioritization"];
    if ($prioritization == "") {
        http_response_code(400);
        echo "No prioritization id specified\n";
        exit;
    }

    $remove = $connection->prepare("DELETE FROM prioritizations WHERE prioritizations.provider = ? AND prioritizations.id = ?");
    $remove->bind_param("ii", $identity, $prioritization);
    $remove->execute();
    $remove->close();

    http_response_code(204);
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
    exit;
}
