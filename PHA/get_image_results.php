<?php
    require_once dirname(__FILE__).'/connection.php';
    $db = new connect_DB();
    $conn = $db->connect();

    $result = mysqli_query($conn, "SELECT * FROM item WHERE item_id = {$_POST['item_id']}");
    $resopnce = array();
    if($result->num_rows>0){
    $r = $result->fetch_assoc();

    $resopnce=$r;
    $resopnce['error'] = false;
   
    } else{
        $resopnce['error'] = true;
        $resopnce['msg'] = "No Invalid Image Cred";
    }
    echo json_encode($resopnce);
