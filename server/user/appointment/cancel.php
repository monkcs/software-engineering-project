<?php

require 'authenticate.php';

$statement = $connection->prepare("SELECT * FROM appointment WHERE appointment.account = ?");
$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
$statement->close();

if ($result->num_rows > 0) {
    $remove = $connection->prepare("DELETE FROM appointment WHERE appointment.account = ?");
    $remove->bind_param("i", $identity);
    $remove->execute();
    $statement->close();

    http_response_code(204);
} else {
    http_response_code(400);
    echo "No appointment pending\n";
}
