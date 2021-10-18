<?php

require 'authenticate.php';

$statement = $connection->prepare("SELECT appointment.pending from appointment
                                    where appointment.account = ?");

$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
$statement->close();
if ($result->num_rows == 0) {
    http_response_code(400);
    echo "No times available\n";
    exit;
}
else{
    echo json_encode($result->fetch_all(MYSQLI_ASSOC));
    echo "\n";
}
