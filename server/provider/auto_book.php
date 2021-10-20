<?php

require 'authenticate.php';
//Lägg till rätt vaccin typ när insertar to appointment
function insert($connection, $appointment, $vaccine)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("INSERT INTO appointment VALUES (?, ?, 2, 0, ?)");
    $statement->bind_param("iii", $appointment, $user, $vaccine);
    $statement->execute();
    $statement->close();
}

function delete_dose($connection)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("UPDATE vaccines INNER JOIN appointment ON 
                                        vaccines.id = appointment.vaccine SET vaccines.quantity=vaccines.quantity-1 
                                        where appointment.account = ?");
    $statement->bind_param("i", $user);
    $statement->execute();
    $statement->close();
}

function get_datetime($connection)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("SELECT * FROM available INNER JOIN appointment
                                        ON available.id=appointment.available WHERE appointment.account=?");
    $statement->bind_param("i", $user);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();
    return $result->fetch_assoc()["datetime"];
}

function get_provider($connection)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("SELECT * FROM available INNER JOIN appointment
                                        ON available.id=appointment.available WHERE appointment.account=?");
    $statement->bind_param("i", $user);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();
    return $result->fetch_assoc()["provider"];
}

function get_vaccine($connection)
{
    $user = $_POST["ID"];
    $statement = $connection->prepare("SELECT * FROM appointment WHERE appointment.account=?");
    $statement->bind_param("i", $user);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();
    return $result->fetch_assoc()["vaccine"];
}

function create_appointment($connection, $datetime, $provider)
{
    $statement = $connection->prepare("INSERT INTO available (datetime, provider, minimum_age) VALUES (DATE_ADD(?, INTERVAL 28 DAY), ?, 0)");
    $statement->bind_param("si", $datetime, $provider);
    $statement->execute();
    $statement->close();
}

function get_new_time($connection, $datetime)
{
    $statement = $connection->prepare("SELECT * FROM available WHERE datetime=DATE_ADD(?, INTERVAL 28 DAY)");
    $statement->bind_param("s", $datetime);
    $statement->execute();
    $result = $statement->get_result();
    $statement->close();
    return $result->fetch_assoc()["id"];
}

$statement = $connection->prepare("SELECT * FROM  available 
                                    where datetime >= DATE_ADD(curdate(), INTERVAL 28 DAY)
                                    and available.id NOT IN (SELECT appointment.available FROM appointment)                           
                                    LIMIT 1");
//$statement->bind_param("i", $identity);
$statement->execute();
$result = $statement->get_result();
if ($result->num_rows == 0) {
    /*If no available time exists */
    /*1. get information about currently booked appointment for specified user (datetime, provider) */
    /*2. create appointment to be booked 28 days later and assign it with an ID, time should preferebly be the same as booking 1*/ 
    /*3. get the id of the created time*/ 
    /*4. insert that id into appointments (book the time)*/ 
    $currdate = get_datetime($connection);
    //$newdate = DATE_ADD($currdate, INTERVAL 28 DAY);
    $provider = get_provider($connection);
    create_appointment($connection, $currdate, $provider);

    $new_time_id = get_new_time($connection, $currdate);
    


    $vaccine = get_vaccine($connection);
    echo $vaccine;
    insert($connection, $new_time_id, $vaccine);
    delete_dose($connection);
    exit;
}
else{
    $value = $result->fetch_object();
    $statement->close();

    $id = $value->id;
    echo "ID APPOINTMENT\n";
    echo $id;
    $vaccine = get_vaccine($connection);
    insert($connection, $id, $vaccine);
}
$user = $_POST["ID"];
    $statement = $connection->prepare("SELECT * from appointment where appointment.account = ?");
    $statement->bind_param("i", $user);
    $statement->execute();
    
    $result = $statement->get_result();
    $statement->close();
    if ($result->num_rows == 0) {
        http_response_code(402);
        echo "Failed to book second dose\n";
        exit;
    }
    else{
        //if appointment exists, remove one dose since it will be rebooked
        delete_dose($connection);
    }