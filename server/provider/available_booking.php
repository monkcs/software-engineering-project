<?php
if ($_SERVER['REQUEST_METHOD'] == "GET") {

header('Content-type: application/json');

require 'authenticate.php';

$statement = $connection->prepare("SELECT * FROM available WHERE NOT EXISTS (SELECT * FROM appointment WHERE appointment.available = available.id)");
$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
$statement->close();

if ($result->num_rows > 0) {
    http_response_code(200);
    echo json_encode(($result->fetch_all(MYSQLI_ASSOC)));
    echo "\n";
} else {
    http_response_code(409);
    echo "No appointments available\n";
}
} else {
http_response_code(405);
echo "Send request using HTTP GET\n";
}