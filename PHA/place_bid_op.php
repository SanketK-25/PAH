<?php

require_once dirname(__FILE__).'/place_bid.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['u_id']) and isset($_POST['a_id']) and isset($_POST['bid_price']))
    {
        $db = new place_bid();
        $responce = $db->bid($_POST['u_id'],$_POST['a_id'],$_POST['bid_price']);        

    }
    else{
        $responce['error'] = true;
        $responce['msg'] = "not all data provided";
    }

}else{
    $responce['error'] = true;
    $responce['msg'] = "Request to Server Failed";
}

echo json_encode($responce);
  