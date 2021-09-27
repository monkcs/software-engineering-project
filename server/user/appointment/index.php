<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    $statement = $connection->prepare("SELECT * FROM appointment WHERE appointment.account = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(404);
        echo "No current appointment\n";
    } else {
        echo json_encode($result->fetch_array(MYSQLI_ASSOC));
        echo "\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
