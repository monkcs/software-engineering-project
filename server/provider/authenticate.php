<?php

require 'connect.php';

function authenticate($connection)
{
    if (!isset($_SERVER['PHP_AUTH_USER'])) {
        mysqli_close($connection);

        header('WWW-Authenticate: Basic');
        http_response_code(401);
        exit;
    } else {
        $username = $_SERVER['PHP_AUTH_USER'];
        $password = $_SERVER['PHP_AUTH_PW'];

        $statement = $connection->prepare("SELECT * FROM provider WHERE provider.email = ? AND provider.password = ?");
        $statement->bind_param("ss", $username, $password);
        $statement->execute();
        $result = $statement->get_result();

        if ($result->num_rows > 0) {
            return $result->fetch_assoc()['id'];
        } else {
            mysqli_close($connection);

            http_response_code(403);
            exit;
        }
    }
}

$identity = authenticate($connection);
