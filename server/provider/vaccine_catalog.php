<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    /*Return entire list with quantities */
    $statement = $connection->prepare("SELECT * FROM vaccines");
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();

    if ($result->num_rows == 0) {
        http_response_code(400);
        echo "No vaccines available\n";
    } else {
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
        echo "\n";
    }
} 
else if($_SERVER['REQUEST_METHOD'] == "POST"){
    /*Here the increment/decrement will take place 
    SQL something like: "UPDATE vaccines SET quantity=quantity + [INPUT_VALUE] where name is [SELECTED_NAME]"*/
    $selected_name = $_POST["selected_name"];
    $input_value = $_POST["input_value"];

    $statement = $connection->prepare("UPDATE vaccines SET vaccines.quantity=vaccines.quantity+? where vaccines.name = ?");
    $statement->bind_param("is", $input_value, $selected_name);
    $statement->execute();
    $statement->close();
   
}
else {
    http_response_code(405);
    echo "Send request using HTTP GET or POST\n";
}