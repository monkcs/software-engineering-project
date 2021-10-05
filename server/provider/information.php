<?php

require 'authenticate.php';

if ($_SERVER['REQUEST_METHOD'] == "GET") {
        header('Content-type: application/json');

        $statement = $connection->prepare("SELECT name, email FROM provider WHERE provider.id = ?");
        $statement->bind_param("i", $identity);
        $statement->execute();
        $result = $statement->get_result();
        $statement->close();

        echo json_encode(($result->fetch_array(MYSQLI_ASSOC)));
} else {
        http_response_code(405);
        echo "Send request using HTTP get\n";
        exit;
}
