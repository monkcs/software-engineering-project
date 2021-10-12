<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    $statement = $connection->prepare("SELECT appointment.available, firstname, surname, telephone, available.datetime, dose FROM person INNER JOIN appointment
                                    ON person.account = appointment.account
                                    INNER JOIN available
                                    ON appointment.available = available.id
                                    AND appointment.pending = 1
                                    AND available.provider = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(404);
    } else {
        header('Content-type: application/json');
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
        echo "\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
