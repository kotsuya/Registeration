<?php
  $con = mysqli_connect("127.0.0.1", "root", "1111", "my_db");

  // Check connection
  if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userGender = $_POST["userGender"];
  $userMajor = $_POST["userMajor"];
  $userEmail = $_POST["userEmail"];

  $checkedPassword = password_hash($userPassword, PASSWORD_DEFAULT);
  $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?)");
  mysqli_stmt_bind_param($statement, "sssss", $userID, $checkedPassword, $userGender, $userMajor, $userEmail);
  $response = array();
  $response["success"] = false;

  if (mysqli_stmt_execute($statement)) {
    $response["success"] = true;
  }

  echo json_encode($response);
 ?>
