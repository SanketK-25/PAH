<?php

    class upload_op{
        private $responce = array();
        private $conn;
        private $file_path = 'C:/xampp/htdocs/PHA/Upload';
        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }
        
        function upload($painting_name, $artist_name, $duration, $description, $base_price, $u_id, $image){
            $iamge_path = $this->file_path.'/'.$painting_name.".jpeg";
            if($this->isImageExist($iamge_path)){
                if(file_put_contents($iamge_path, base64_decode($image))){
                    $this->responce['error'] = $iamge_path;
                    mysqli_query($this->conn, "INSERT INTO `item` (`item_id`, `painting`, `painting_name`, `painting_description`, `artist`) 
                                VALUES (NULL, '{$iamge_path}', '{$painting_name}', '{$description}', '{$artist_name}');");

                    $item_id = $this->conn->insert_id;
                    $this->place_auction($u_id, $item_id, $duration, $base_price);

                }else{
                    $this->responce['error'] = "he error he";

                }           
            }else{
                $this->responce['error'] = true;
                $this->responce['msg'] = "Painting Name Already Exists";
            }
            return $this->responce;
        }

        function place_auction($u_id, $item_id, $duration, $base_price){
            require_once dirname(__FILE__).'/place_auction.php';
            $db = new place_auction();
            $responce = $db->put_auction($u_id, $item_id, $duration, $base_price);
        }

        function isImageExist($iamge_path){
            if( file_get_contents($iamge_path)){
                return false;
            }else{
                return true;
            }

        }

    }
