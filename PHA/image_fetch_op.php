<?php

require_once dirname(__FILE__).'/image_fetch.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['image_name'])){

        $db = new image_fetch();

        $responce = $db->fetch($_POST['image_name']);

    }
    else{
        $responce['error'] = true;
        $responce['msg'] = "Provide Both Username and Password";
    }

}else{
    $responce['error'] = true;
    $responce['msg'] = "Request to Server Failed";
}

echo json_encode($responce);