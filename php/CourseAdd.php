<?php
$con = mysqli_connect("127.0.0.1", "root", "1111", "my_db");

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$userID = $_POST["userID"];
$courseID = $_POST["courseID"];

$statement = mysqli_prepare($con, "INSERT INTO SCHEDULE VALUES (?, ?)");
mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

// $error = json_last _error();
// var_dump($response, $error === JSON_ERROR_UTF8);
echo json_encode($response);
?>
