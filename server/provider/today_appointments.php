<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    $statement = $connection->prepare("SELECT person.account, firstname, surname, telephone, available.datetime, dose from person inner join appointment
                                    on person.account = appointment.account
                                    inner join available
                                    on appointment.available = available.id
                                    and appointment.pending = 0
                                    and available.provider = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(400);
        echo "No appointments booked today\n";
    } else {
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
        echo "\n";
    }
}else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
