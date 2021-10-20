<?php

require 'connect.php';

 
if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $QR = $_POST["qrcode"];

    $statement = $connection->prepare("SELECT person.firstname, person.surname, person.birthdate from person inner join passport
                                        on person.account = passport.person
                                        and passport.qrcode = ?");
      $statement->bind_param("s", $QR);
      $statement->execute();
      $result1 = $statement->get_result();
      $statement->close();
      if ($result1->num_rows == 0) {
          http_response_code(400);
          echo "No passport available\n";
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
