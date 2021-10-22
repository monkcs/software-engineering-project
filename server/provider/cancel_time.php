<?php

require 'authenticate.php';


if($_SERVER['REQUEST_METHOD'] == "POST"){

    $user = $_POST["ID"];

    $statement = $connection->prepare("DELETE from appointment where appointment.account = ?");
    $statement->bind_param("i", $user);        
    $statement->execute();
    $statement->close();
   
}
else {
    http_response_code(405);
    echo "Send request using HTTP POST\n";
}