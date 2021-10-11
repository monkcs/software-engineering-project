<?php

require 'authenticate.php'

$query = "SELECT available.datetime AS Date from available
            inner join appointment
            on available.id = appointment.available
            inner join account
            on appointment.account = account.id
            and account.id = $identity";

$result = mysqli_query($connection, $query);
$row = mysqli_fetch_row($result);
$date1 = $row[date];
$date2 = date("Y-m-d");
$diff = date_diff($date1,$date2);
echo $diff->format("%R%a days");
/*
if($diff >= '28')
{
    $sql = "INSERT INTO dose_one VALUES()";
    $sql1 = "DELETE FROM booking where booking.person = $identity";
}
else 
   exit;
*/


