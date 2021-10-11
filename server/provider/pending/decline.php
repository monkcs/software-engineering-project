<?php

require 'authenticate.php';


function decline($connection, $account)
{
    $statement = $connection->prepare("DELETE FROM appointment WHERE appointment.account = ?");
    $statement->bind_param("i", $account);
    $statement->execute();
    $statement->close();
}

function notify($connection, $account, $response)
{
    $reason = $connection->prepare("INSERT INTO inbox (account, appointment, response) VALUES (?, -1, ?)");
    $reason->bind_param("is", $account, $response);
    $reason->execute();
    $reason->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $account = $_POST["account"];
    $message = $_POST["message"];

    $statement = $connection->prepare("SELECT * FROM account INNER JOIN appointment WHERE account.id = appointment.account AND appointment.pending = 1 AND account.id = ?");
    $statement->bind_param("i", $account);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(404);
    } else {
        http_response_code(204);
        decline($connection, $account);
        notify($connection, $account, $message);
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
