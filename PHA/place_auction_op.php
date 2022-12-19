<?php

require_once dirname(__FILE__).'/place_auction.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['user_id']) and isset($_POST['item_id']) and isset($_POST['duration']) and isset($_POST['base_price']))
    {
        $db = new place_auction();

        if($responce = $db->put_auction($_POST['user_id'],$_POST['item_id'], $_POST['duration'],$_POST['base_price'])){
        }
        else{
            $responce['error'] = true;
            $responce['msg'] = "Invalid Data";         
        }
    }
    else{
        $responce['error'] = true;
        $responce['msg'] = "Provide Ever Data Field";
    }

}else{
    $responce['error'] = true;
    $responce['msg'] = "Request to Server Failed";
}

echo json_encode($responce);