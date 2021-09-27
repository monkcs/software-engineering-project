<?

require 'authenticate.php'

$sql = "SELECT * FROM booking WHERE booking.person = $identity";

$result = mysqli_query($connection, $sql);

if(mysqli_fetch_row($result)>0)
    $exists = '1';
mysqli_close();

if($exists == '1')
{
    $query = "DELETE FROM booking WHERE booking.person = $identity";
    mysqli_query($connection, $query);
    http_response_code(200);
    echo "Time canceled\n";
}
else{
    http_response_code(400);
    echo "Time not canceled\n";
}
mysqli_close();
?>