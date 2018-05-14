<?php
$con = mysqli_connect("127.0.0.1", "root", "1111", "my_db");

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$userID = $_POST["userID"];

$statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID=?");
mysqli_stmt_bind_param($statement, "s", $userID);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
//mysqli_stmt_bind_result($statement, $userID);

$response = array();
$response["success"] = true;
$response["userID"] = $userID;

while(mysqli_stmt_fetch($statement)){
  $response["success"] = false;
  $response["userID"] = $userID;
}

// $error = json_last_error();
// var_dump($response, $error === JSON_ERROR_UTF8);
echo json_encode($response);
?>
