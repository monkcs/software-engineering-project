<?
require 'authenticate.php'

$datetime = $_POST['datetime'];

$sql = "SELECT * FROM dose_one WHERE dose_one.person = $identity";

$result = mysqli_query($connection, $sql);
if(mysqli_fetch_row($result) > 0)
    $dose = '2';
else
    $dose = '1';
mysqli_close();

$query = "SELECT * FROM booking WHERE booking.person = $identity";

$result1 = mysqli_query($connection, $query);
if(mysqli_fetch_row($result1)>0)
{
    http_response_code(400);
    echo "Already have a time booked\n";
    mysqli_close();
    exit;
}
mysqli_close();
else
{
    $insert = "INSERT INTO booking VALUES('$datetime', '$identity', '$dose')";
    $result2 = mysqli_query($connection, $insert);
    mysqli_close();
} 
$sql_check = "SELECT * from booking WHERE booking.person = $identity";
$result3 = mysqli_query($connection, $sql_check);
if (mysqli_fetch_row($result3) > 0)
{
    http_response_code(200);
    echo "Time succesfully booked\n";
}
else
{
    http_response_code(400);
    echo "Time not booked\n";
} 
mysqli_close();  

?>