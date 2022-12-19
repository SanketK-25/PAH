<?php

require_once dirname(__FILE__).'/user_Login.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['username']) and isset($_POST['userpass']))
    {
        $db = new user_Login();

        if($responce = $db->login($_POST['username'],$_POST['userpass'])){
            $responce['error'] = false;
        }
        else{
            $responce['error'] = true;
            $responce['msg'] = "Invalid Username or Password";         
        }
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