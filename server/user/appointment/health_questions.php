<?php

require 'authenticate.php';

$appointment = $_POST["appointment"];
$question1 = $_POST["question1"];
$question2 = $_POST["question2"];
$question3 = $_POST["question3"];
$question4 = $_POST["question4"];
$question5 = $_POST["question5"];

function insert($question)
{
    $statement = $connection->prepare("INSERT INTO pending VALUES(?, ?)");
    $statement->bind_param("ii", $appointment, $question);
    $statement->execute();
    $statement->close();
}
if($question1 != 0){
    insert($question1);
}
if($question2 != 0){
    insert($question2);
}    
if($question3 != 0){
    insert($question3);
}
if($question4 != 0){
    insert($question4);
}
if($question5 != 0){
    insert($question5);
}

