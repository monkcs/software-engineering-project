<?php

require 'authenticate.php';


function approve($connection, $account, $identity)
{
    $statement = $connection->prepare("UPDATE appointment INNER JOIN available ON appointment.available = available.id SET appointment.pending = 0 WHERE appointment.account = ? AND available.provider = ?");
    $statement->bind_param("ii", $account, $identity);
    $statement->execute();
    $statement->close();

    $statement = $connection->prepare("DELETE FROM appointment WHERE appointment.account = ?");
    $statement->bind_param("ii", $account, $identity);
    $statement->execute();
    $statement->close();
}

function notify($connection, $account, $appointment, $response)
{
    $reason = $connection->prepare("INSERT INTO inbox (account, appointment, response)
    VALUES (?,?,?)");
    $reason->bind_param("iis", $account, $appointment, $response);
    $reason->execute();
    $reason->close();
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $account = $_POST["account"];
    $message = $_POST["message"];

    approve($connection, $account, $identity);

    $statement = $connection->prepare("SELECT * FROM account INNER JOIN appointment WHERE account.id = appointment.account AND appointment.pending = 0 AND account.id = ?");
    $statement->bind_param("i", $account);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(404);
    } else {
        http_response_code(204);
        notify($connection, $account, $result->fetch_assoc()["available"], $message);
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP get\n";
    exit;
}
