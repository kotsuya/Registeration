<?php
  $con = mysqli_connect("127.0.0.1", "root", "1111", "my_db");
  $result = mysqli_query($con, "SELECT * FROM NOTICE ORDER BY noticeDate DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)) {
    array_push($response, array("noticeContent"=>$row[0], "noticeName"=>$row[1], "noticeDate"=>$row[2]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);
 ?>
