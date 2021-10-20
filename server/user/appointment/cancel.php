<?php

require 'authenticate.php';

function add_dose($connection, $id)
{
    $statement = $connection->prepare("UPDATE vaccines INNER JOIN appointment ON 
                                        vaccines.id = appointment.vaccine SET vaccines.quantity=vaccines.quantity+1 
                                        where appointment.account = ?");
    $statement->bind_param("i", $id);
    $statement->execute();
    $statement->close();
}

$statement = $connection->prepare("SELECT * FROM appointment WHERE appointment.account = ?");
$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
$statement->close();

if ($result->num_rows > 0) {
    add_dose($connection, $identity);

    $remove = $connection->prepare("DELETE FROM appointment WHERE appointment.account = ?");
    $remove->bind_param("i", $identity);
    $remove->execute();
    $statement->close();
    
    http_response_code(204);
} else {
    http_response_code(400);
    echo "No appointment pending\n";
}
