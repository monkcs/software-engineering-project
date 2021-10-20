<?php

require 'authenticate.php';

if($_SERVER['REQUEST_METHOD'] == "POST"){
    /*Here the increment/decrement will take place 
    SQL something like: "UPDATE vaccines SET quantity=quantity + [INPUT_VALUE] where name is [SELECTED_NAME]"*/
    $selected_id = $_POST["selected_id"];
    $input_value = $_POST["input_value"];

    $statement = $connection->prepare("UPDATE vaccines SET vaccines.quantity=vaccines.quantity+? where vaccines.id = ?");
    $statement->bind_param("is", $input_value, $selected_id);
    $statement->execute();
    $statement->close();
   
}
else {
    http_response_code(405);
    echo "Send request using HTTP GET or POST\n";
}