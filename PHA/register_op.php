<?php

require_once dirname(__FILE__).'/user_Register.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['username']) and isset($_POST['userpass']) and isset($_POST['name'])
            and isset($_POST['contact']) and isset($_POST['address']))
    {
        $db = new user_Register();

        $responce = $db->register($_POST['username'],$_POST['userpass'],$_POST['name'],$_POST['contact'],$_POST['address']);

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