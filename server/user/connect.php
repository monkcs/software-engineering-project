<?php

error_reporting(E_ALL);
ini_set("display_errors", 1);

function connect()
{
    $hostname = "localhost:3306";
    $username = "charhabo100";
    $password = "ooyuaz9KaiVutheiSug7phohshenai";
    $database = "charhabo100_4";

    $connection = mysqli_connect($hostname, $username, $password, $database);

    if ($connection) {
        return $connection;
    } else {
        http_response_code(503);
        exit;
    }
};

$connection = connect();
$connection->set_charset("utf8");
