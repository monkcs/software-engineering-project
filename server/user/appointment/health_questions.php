<?php

require 'authenticate.php';

$question1 = $_POST["question1"];
$question2 = $_POST["question2"];
$question3 = $_POST["question3"];
$question4 = $_POST["question4"];
$question5 = $_POST["question5"];

echo "\nQ1:"; 
echo $question1;
echo "\nQ2:"; 
echo $question2;
echo "\nQ3:"; 
echo $question3;
echo "\nQ4:"; 
echo $question4;
echo "\nQ5:"; 
echo $question5;

function insert($connection, $question)
{
    $appointment = $_POST["appointment"];
    $provider = $_POST["provider"];
    echo "\nin insert Q =";
    echo $question;
    $statement = $connection->prepare("INSERT INTO pending(pending.provider, appointment, question) VALUES(?, ?, ?)");
    $statement->bind_param("iii", $provider, $appointment, $question);
    $statement->execute();
    $statement->close();
}
if($question1 != 0){
    echo "question1 = true\n";
    insert($connection, $question1);
}
if($question2 != 0){
    echo "question2 = true\n";
    insert($connection, $question2);
}    
if($question3 != 0){
    echo "question3 = true\n";
    insert($connection, $question3);
}
if($question4 != 0){
    echo "question4 = true\n";
    insert($connection, $question4);
}
if($question5 != 0){
    echo "question5 = true\n";
    insert($connection, $question5);
}

