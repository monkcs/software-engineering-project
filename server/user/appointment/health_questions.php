<?php

require 'authenticate.php';

$question1 = $_POST["question1"];
$question2 = $_POST["question2"];
$question3 = $_POST["question3"];
$question4 = $_POST["question4"];
$question5 = $_POST["question5"];

function insert($connection, $question)
{
    $appointment = $_POST["appointment"];
    $provider = $_POST["provider"];
    $statement = $connection->prepare("INSERT INTO pending(pending.provider, appointment, question) VALUES(?, ?, ?)");
    $statement->bind_param("iii", $provider, $appointment, $question);
    $statement->execute();
    $statement->close();
}
if($question1 != 0){

    insert($connection, $question1);
}
if($question2 != 0){
  
    insert($connection, $question2);
}    
if($question3 != 0){

    insert($connection, $question3);
}
if($question4 != 0){
  
    insert($connection, $question4);
}
if($question5 != 0){
   
    insert($connection, $question5);
}

