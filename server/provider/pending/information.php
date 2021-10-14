<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "POST") {

        $statement = $connection->prepare("SELECT pending.appointment, text FROM pending INNER JOIN question ON pending.question = question.id WHERE  pending.provider = ?");
        $statement->bind_param("i", $identity);
        $statement->execute();
        $result = $statement->get_result();
        $statement->close();

        if ($result->num_rows == 0) {
                http_response_code(404);
        } else {
                header('Content-type: application/json');
                echo json_encode($result->fetch_all(MYSQLI_ASSOC));
                echo "\n";
        }
} else {
        http_response_code(405);
        echo "Send request using HTTP post\n";
        exit;
}
