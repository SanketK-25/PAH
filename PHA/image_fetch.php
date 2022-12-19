<?php

    class image_fetch{
        private $conn;

        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }

        function fetch($item_id){
            $result = mysqli_query($this->conn, "SELECT painting FROM item WHERE item_id= {$item_id}");
            $r = $result->fetch_assoc();
            $img = file_get_contents($r['painting']);
            $data = base64_encode($img);
            $responce = array();
            $responce['image_data'] = $data;
            return $responce;
        }
    }