<?php

require_once dirname(__FILE__).'/image_upload.php';

$responce = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if( isset($_POST['painting_name']) and isset($_POST['artist_name']) and isset($_POST['duration']) 
            and isset($_POST['description']) and isset($_POST['base_price']) and isset($_POST['u_id']) and isset($_POST['image']))
    {
        $db = new upload_op();
        $responce = $db->upload($_POST['painting_name'], $_POST['artist_name'], $_POST['duration'], 
                                    $_POST['description'], $_POST['base_price'], $_POST['u_id'],$_POST['image']);
        

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
  
