<?php

require 'connect.php';

function badrequest()
{
    http_response_code(400);
    exit;
}

function validate()
{
    if ($_POST["email"] == "") {
        echo "No email address is provided";
        badrequest();
    }
}

function existing($connection)
{
    if ($_SERVER['REQUEST_METHOD'] == "POST") {

        validate();

        $email = $_POST["email"];

        $statement = $connection->prepare("SELECT * FROM account WHERE account.email = ?");
        $statement->bind_param("s", $email);
        $statement->execute();
        $result = $statement->get_result();
        $statement->close();

        if ($result->num_rows > 0) {
            http_response_code(409);
            echo "Username already exists\n";
            exit;
        } else {
            return false;
        }
    } else {
        http_response_code(405);
        echo "Send request using HTTP post\n";
        exit;
    }
}

function insert_account($connection, $email, $password)
{
    $statement = $connection->prepare("INSERT INTO account (email, password) VALUES(?, ?)");
    $statement->bind_param("ss", $email, $password);
    $statement->execute();
    $statement->close();
}

function get_identity($connection, $email)
{
    $statement = $connection->prepare("SELECT id FROM account WHERE account.email = ?");
    $statement->bind_param("s", $email);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();
    return $result->fetch_assoc()['id'];
}

function insert_address($connection, $id)
{
    $street = $_POST["street"];
    $postalcode = $_POST["postalcode"];
    $city = $_POST["city"];
    $country = $_POST["country"];

    $statement = $connection->prepare("INSERT INTO address (id, street, postalcode, city, country) VALUES(?, ?, ?, ?, ?)");
    $statement->bind_param("isisi", $id, $street, $postalcode, $city, $country);
    $statement->execute();
    $statement->close();
}

function insert_person($connection, $id)
{
    $firstname = $_POST["firstname"];
    $surname = $_POST["surname"];
    $telephone = $_POST["telephone"];
    $birthdate = strtotime($_POST["birthdate"]);

    echo "birthdate :";
    echo date($birthdate);

    $statement = $connection->prepare("INSERT INTO person (account, firstname, surname, telephone, birthdate) VALUES(?, ?, ?, ?, FROM_UNIXTIME(?))");
    $statement->bind_param("isssi", $id, $firstname, $surname, $telephone, $birthdate);
    $statement->execute();
    $statement->close();
}


if (!existing($connection)) {

    $email = $_POST["email"];
    $password = $_POST["password"];


    insert_account($connection, $email, $password);
    $id = get_identity($connection, $email);

    echo "id number: ";
    echo $id;
    echo "\n";

    insert_address($connection, $id);
    insert_person($connection, $id);
}
