<?php

//require 'authenticate.php';
require 'connect.php';

function prioritizations($connection, $provider, $age)
{
    $statement = $connection->prepare("SELECT prioritizations.forthcoming FROM prioritizations WHERE prioritizations.not_younger <= ? AND prioritizations.provider = ? ORDER BY not_younger DESC LIMIT 1");
    $statement->bind_param("ii", $age, $provider);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows > 0) {
        return $result->fetch_assoc()["forthcoming"];
    } else {
        http_response_code(409);
        echo "No appointments available because user is not yet eligible\n";
        exit;
    }
}

function age($connection, $identity)
{
    $statement = $connection->prepare("SELECT person.birthdate FROM person WHERE person.account = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    $timezone  = new DateTimeZone('Europe/Stockholm');
    return DateTime::createFromFormat('Y-m-d', $result->fetch_assoc()["birthdate"], $timezone)->diff(new DateTime('now', $timezone))->y;
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    header('Content-type: application/json');

    $provider = $_POST["provider"];
    if ($provider == "") {
        http_response_code(400);
        echo "No provider id specified\n";
        exit;
    }

    //$after = prioritizations($connection, $provider, age($connection, 71));

    $statement = $connection->prepare("SELECT * FROM available WHERE NOT EXISTS (SELECT * FROM appointment WHERE appointment.available = available.id) AND available.provider = ? AND available.datetime > CURRENT_DATE");
    $statement->bind_param("i", $provider);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows > 0) {
        http_response_code(200);
        echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
        echo "\n";
    } else {
        http_response_code(409);
        echo "No appointments available\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
