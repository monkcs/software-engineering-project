<?php

require 'connect.php';

if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $email = $_POST["email"];

    $statement = $connection->prepare("SELECT account.password from account
                                        where account.email = ?");
      $statement->bind_param("s", $email);
      $statement->execute();
      $result1 = $statement->get_result();
      $statement->close();
      if ($result1->num_rows == 0) {
          http_response_code(400);
          echo "No account registerd\n";
      }
      else{
      http_response_code(200); 
        echo json_encode($result1->fetch_array(MYSQLI_ASSOC));
        echo "\n";
    }  
} else {
    http_response_code(405);
    echo "Send request using HTTP post\n";
    exit;
}