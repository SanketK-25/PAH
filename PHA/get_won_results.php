<?php
    require_once dirname(__FILE__).'/connection.php';
    $db = new connect_DB();
    $conn = $db->connect();
    $result = mysqli_query($conn, "SELECT * FROM place_auction WHERE winner_id = {$_POST['u_id']}");
    $resopnce = array();
    while($r = $result->fetch_assoc()){
        require_once dirname(__FILE__).'/image_fetch.php';
        $if = new image_fetch();
        $temp = $if->fetch($r['item_id']);
        
        $r = array_merge($r,$temp);

        array_push($resopnce,$r);
    }
    echo json_encode($resopnce);
