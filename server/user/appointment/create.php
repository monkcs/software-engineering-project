<?php

require 'authenticate.php';

function pending($connection, $identity)
{
    $statement = $connection->prepare("SELECT * FROM appointment WHERE appointment.account = ?");
    $statement->bind_param("i", $identity);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows > 0) {
        return true;
    } else {
        return false;
    }
}

function dosage($connection, $identity)
{
    $second = $connection->prepare("SELECT * FROM dose_two WHERE dose_two.person = ?");
    $second->bind_param("i", $identity);
    $second->execute();
    $result_second = $second->get_result();
    $second->close();

    if ($result_second->num_rows == 0) {

        $first = $connection->prepare("SELECT * FROM dose_one WHERE dose_one.person = ?");
        $first->bind_param("i", $identity);
        $first->execute();
        $result_first = $first->get_result();
        $first->close();

        if ($result_first->num_rows == 0) {
            return 1;
        } else {
            return 0;
        }
    } else {
        return 2;
    }
}

function insert($connection, $appointment, $identity, $dose)
{
    $statement = $connection->prepare("INSERT INTO appointment VALUES (?, ?, ?, 0)");
    $statement->bind_param("iii", $appointment, $identity, $dose);
    $statement->execute();
    $statement->close();
}

function valid($connection, $appointment)
{
    $statement = $connection->prepare("SELECT available.id FROM available WHERE available.id NOT IN (SELECT appointment.available FROM appointment) AND available.id = ?");
    $statement->bind_param("i", $appointment);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        return false;
    } else {
        return true;
    }
}

if ($_SERVER['REQUEST_METHOD'] == "POST") {

    $appointment_pending = pending($connection, $identity);

    if (!$appointment_pending) {


        $current_dosage = dosage($connection, $identity);

        $appointment = (int)$_POST["appointment"];
        if ($appointment == "") {
            http_response_code(400);
            echo "No appointment id specified\n";
            exit;
        }

        if (valid($connection, $appointment)) {

            if ($current_dosage == 0) {
                insert($connection, $appointment, $identity, 1);
            } else if ($current_dosage == 1) {
                insert($connection, $appointment, $identity, 2);
            } else {
                http_response_code(409);
                echo "All dosages already administered\n";
            }
        } else {
            http_response_code(400);
            echo "Meeting id is not valid\n";
        }
    } else {
        http_response_code(409);
        echo "Appointment already pending\n";
    }
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
}
